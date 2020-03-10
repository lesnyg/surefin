package com.jubumam.SureFin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

    private String storeVersion;
    private String deviceVersion;


    private Button cal_btn;
    private String TAG = "PickerActivity";
    private String personId;
    private String rating;
    private AsyncTask<String, String, String> mTask;
    private AsyncTask<String, String, String> cTask;
    private List<Recipient> list;
    private EditText et_search;
    private String name = "출근전 입니다.";
    private RecipientAdapter mAdapter;
    private List<Recipient> arrayList;
    private RecyclerView recyclerView;
    private String commute;
    private Bitmap bitmap;
    private Connection connection;
    private ResultSet resultSetlist;
    private String responsibility ="";   //요양사 이름
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
    private String responsibilityID;
    private String department;

    //noti count
    private TextView smsCountTxt;
    private int pendingSMSCount = 10;
    private String route1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activateToolbar();

        Login login = Login.getInstance();
        responsibility = login.getResponsibility();
        responsibilityID = login.getPersonId();
        department = login.getPersonId();


        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        commute = commuteRecipient.getCommute();


        Intent intent = getIntent();
        route1 = intent.getExtras().getString("route");

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
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
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
                        intent.putExtra("route",route1);
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

    private class VersionCheck extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public class BackgroundThread extends Thread {
        @Override
        public void run() {

            // 패키지 네임 전달
            storeVersion = MarketVersionChecker.getMarketVersion(getPackageName());

            // 디바이스 버전 가져옴
            try {
                deviceVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            deviceVersionCheckHandler.sendMessage(deviceVersionCheckHandler.obtainMessage());
            // 핸들러로 메세지 전달
        }
    }

    private final DeviceVersionCheckHandler deviceVersionCheckHandler = new DeviceVersionCheckHandler(this);

    // 핸들러 객체 만들기
    private static class DeviceVersionCheckHandler extends Handler {
        private final WeakReference<MainActivity> mainActivityWeakReference;

        public DeviceVersionCheckHandler(MainActivity mainActivity) {
            mainActivityWeakReference = new WeakReference<MainActivity>(mainActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mainActivityWeakReference.get();
            if (activity != null) {
                activity.handleMessage(msg);
                // 핸들메세지로 결과값 전달
            }
        }
    }

    private void handleMessage(Message msg) {
        //핸들러에서 넘어온 값 체크
        Log.e(" storeVersion : ", storeVersion);
        Log.e(" deviceVersion : ", deviceVersion);
        if (storeVersion.compareTo(deviceVersion) > 0) {
            // 업데이트 필요

            AlertDialog.Builder alertDialogBuilder =
                    new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_Light));
            alertDialogBuilder.setTitle("업데이트");
            alertDialogBuilder
                    .setMessage("새로운 버전이 있습니다.\n보다 나은 사용을 위해 업데이트 해 주세요.")
                    .setPositiveButton("업데이트 바로가기", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);

                            intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                            startActivity(intent);
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();

        } else {
            // 업데이트 불필요

        }

    }


}