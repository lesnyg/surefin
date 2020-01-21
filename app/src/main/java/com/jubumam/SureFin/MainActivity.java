package com.jubumam.SureFin;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends BaseActivity {
//    private Button btn_check;
//    private String currentname;
//    private String indiviPay;
//    private String currentrating;
//    private String caregiverPhone;  //요양사 핸드폰
//    private String tname;
//    private TextView cal_txt1;
//    private String title;
//    private Button btn_search;
//    private TextView cal_txt;


    private Button cal_btn;
    private String TAG = "PickerActivity";
    private String personId;
    private String rating;
    private AsyncTask<String, String, String> mTask;
    private AsyncTask<String, String, String> cTask;
    private List<Recipient> list;
    private EditText et_search;
    private String name;
    private RecipientAdapter mAdapter;
    private List<Recipient> arrayList;
    private RecyclerView recyclerView;
    private String commute;
    private Bitmap bitmap;
    private Connection connection;
    private ResultSet resultSetlist;
    private String responsibility;   //요양사 이름
    private String s1;
    private String number;
    private String adress;

    private String date1;
    private String date2;
    private String schedule_date;//일자
    private String scheduletime;//근무시간
    private String contracttime; //계약시간
    private String schedulename;//계약수급자명
    private String division;
    private String divisiontotal;
    private String divisiondate;
    private String divisiontime;
    private String route;   //경로
    private String gender;

    //noti count
    private TextView smsCountTxt;
    private int pendingSMSCount = 10;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activateToolbar();


        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        responsibility = commuteRecipient.getResponsibility();
        commute = commuteRecipient.getCommute();


        final Intent intent = getIntent();
        if (commute == null) {
            name = intent.getExtras().getString("name");
            rating = intent.getExtras().getString("rating");
            responsibility = intent.getExtras().getString("responsibility");
        }

        mTask = new MySyncTask().execute();
        et_search = findViewById(R.id.et_search);

        recyclerView = findViewById(R.id.recyclerview);

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cal_btn = findViewById(R.id.cal_btn);

    }


    public class MySyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;

            listQuery();
            return null;

        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();
        }

    }


    public void listQuery() {

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();
            resultSetlist = statement.executeQuery("select * from Su_수급자기본정보 left join Su_사진 on Su_수급자기본정보.id=Su_사진.Idno where 담당='" + responsibility + "' order by Su_수급자기본정보.수급자명");
            //resultSetlist = statement.executeQuery("select * from Su_수급자기본정보 full outer join Su_사진 on Su_수급자기본정보.");


            byte bt[];
            list = new ArrayList<>();
            while (resultSetlist.next()) {
                personId = resultSetlist.getString("Idno");
                name = resultSetlist.getString("수급자명");
                adress = resultSetlist.getString("주소2");
                number = resultSetlist.getString("hp");
                s1 = resultSetlist.getString("담당");
                gender = resultSetlist.getString("성별");


                try {
                    if (s1.equals(responsibility)) {
                        Blob bloblist = resultSetlist.getBlob("BLOBData");
                        if (bloblist != null) {
                            bt = bloblist.getBytes(1, (int) bloblist.length());
                            bitmap = BitmapFactory.decodeByteArray(bt, 0, bt.length);
                        } else {
                            bitmap = null;
                        }
                        // list.add(new Recipient(bitmap, name, indiviPay, rating));

                        list.add(new Recipient(bitmap, name, number, adress, gender));


                    }
                } catch (Exception e) {

                }


            }


            arrayList = new ArrayList<>();
            arrayList.addAll(list);

            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new RecipientAdapter(new RecipientAdapter.OnRecipientClickListener() {

                    public void onRecipientClick(Recipient recipient) {
                        Intent intent = new Intent(MainActivity.this, RecipientDetailActivity.class);
                        intent.putExtra("responsibility", responsibility);
                        intent.putExtra("name", recipient.getName());
                        intent.putExtra("title", "main");
                        startActivity(intent);


                    }

                    public void onPhoneClick(String phone) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + phone));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }


                });


                et_search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String text = et_search.getText().toString();
                        search(text);
                    }
                });
                mAdapter.setitems(list);
                recyclerView.setAdapter(mAdapter);
            }
        });


    }

    private void filter(String text) {
        ArrayList<Recipient> filteredList = new ArrayList<>();
        for (Recipient item : list) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
    }


    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();


        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arrayList);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < arrayList.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arrayList.get(i).getName().toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arrayList.get(i));

                }

            }


        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        mAdapter.notifyDataSetChanged();
    }


}