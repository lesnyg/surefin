package com.jubumam.SureFin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    protected String phone;
    protected String manager;
    protected String rating;
    protected int id;
    private String pecipientName;
    private String responsibility;
    private String commute;
    private String schedule_date;//일자
    private String scheduletime;//근무시간
    private String scheduletime2;//근무시간
    private String contracttime; //계약시간
    private String schedulename;//계약수급자명
    private String divisiontotal;
    private String divisiondate;
    private String divisiontime;
    private String division;        //수급자 구분
    private String date2;
    private String date1;
    private String TAG = "PickerActivity";
    private AsyncTask<String, String, String> cTask;
    private AsyncTask<String, String, String> notiTask;
    private int listcount = 0;

    //noti count
    private TextView smsCountTxt;
    private MenuItem menuItem;
    private FrameLayout frame_noti;
    protected String noti_id = "0";
    private int notiCount = 0;
    private String strnotiID;
    private Date dialogdate;
    private List<Dialog> list;
    List<Map<String, String>> dialogItemList;
    private static final String TAG_TIME = "time";
    private static final String TAG_NAME = "name";
    private Map<String, String> itemMap;


    protected Toolbar activateToolbar() {
        SharedPreferences sharedPreferences = getSharedPreferences("file", 0);
        noti_id = sharedPreferences.getString("notiID", "");

        notiTask = new NotiSyncTask().execute();
        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        responsibility = commuteRecipient.getResponsibility();
        commute = commuteRecipient.getCommute();

        if (commute == null) {
            Login login = Login.getInstance();
            responsibility = login.getResponsibility();
        }

        return mToolbar;
    }

    public class NotiSyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;
            notiQuery();
            return null;

        }

        protected void onPostExecute(String result) {
            if (mToolbar == null) {
                mToolbar = findViewById(R.id.toolbar);
                if (mToolbar != null) {
                    setSupportActionBar(mToolbar);
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
                    actionBar.setDisplayShowTitleEnabled(false);
                    if (commute != null) {
                        actionBar.setDisplayHomeAsUpEnabled(true);
                        actionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);
                    }
                }
            }


        }

        protected void onCancelled() {
            super.onCancelled();
        }

    }

    private void notiQuery() {
        Connection conn = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = conn.createStatement();
            ResultSet notiRS = statement.executeQuery("select id from Su_공지사항 where id > '" + noti_id + "' order by id");
            while (notiRS.next()) {
                strnotiID = notiRS.getString("id");
                notiCount++;
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public class CalSyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;
            calQuery();
            return null;

        }

        protected void onPostExecute(String result) {
            if (dialogItemList.size() == 0) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(BaseActivity.this);
                builder.setTitle(divisiondate).setMessage("선택하신 날짜에 일정이 없습니다.");
                android.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                showAlertDialog();
            }
        }


        protected void onCancelled() {
            super.onCancelled();
        }

    }

    public void calQuery() {

        Connection conn = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = conn.createStatement();
            ResultSet calres = statement.executeQuery("select * from Su_서비스제공일정표 where (담당1 ='" + responsibility + "' or 담당2 ='" + responsibility + "') and 일자 ='" + divisiondate + "' order by 시간1");
            list = new ArrayList<>();
            dialogItemList = new ArrayList<>();
            while (calres.next()) {

//                schedule_date = calres.getString("일자");//일자
//                scheduletime = calres.getString("근무시간");//근무시간
//                contracttime = calres.getString("계약시간"); //계약시간
//                schedulename = calres.getString("수급자명");//계약수급자명
//                division = calres.getString("구분");//구분

                schedule_date = calres.getString("일자");//일자
                scheduletime = calres.getString("시간1");//근무시간
                scheduletime2 = calres.getString("시간2");//근무시간
                contracttime = calres.getString("서비스제공시간"); //서비스제공시간
                schedulename = calres.getString("수급자명");//계약수급자명
                division = calres.getString("재가구분");//구분

                itemMap = new HashMap<>();

                divisiontotal = "      " + schedulename + "     " + division;
                divisiontime = scheduletime + "~" + scheduletime2 + " (" + contracttime + "분)";

                itemMap.put(TAG_TIME, divisiontime);
                itemMap.put(TAG_NAME, divisiontotal);

                dialogItemList.add(itemMap);

            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_action, menu);
        menuItem = menu.findItem(R.id.action_notice);


        if (notiCount == 0) {
            menuItem.setActionView(null);

        } else {
            menuItem.setActionView(R.layout.noti_layout);
            View view = menuItem.getActionView();
            smsCountTxt = view.findViewById(R.id.notification_badge);
            frame_noti = view.findViewById(R.id.frame_noti);
            smsCountTxt.setText(String.valueOf(notiCount));

            frame_noti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = getSharedPreferences("file", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("notiID", strnotiID);
                    editor.commit();
                    Intent intent1 = new Intent(BaseActivity.this, CustomerServiceActivity.class);
                    startActivity(intent1);
                }
            });

        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MenuMain.class);
                startActivity(intent);
                return true;
            case R.id.action_notice:
                Intent intent1 = new Intent(this, CustomerServiceActivity.class);
                intent1.putExtra("responsibility", responsibility);
                startActivity(intent1);
                return true;
            case R.id.action_cal:
                final Calendar cal = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(BaseActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        date1 = String.format("%d-%02d-%02d", year, month + 1, date);
                        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-MM-dd");
                        try {
                            dialogdate = dt.parse(date1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        divisiondate = new SimpleDateFormat("yyyy년 MM월 d일 EE요일").format(dialogdate);
                        cTask = new CalSyncTask().execute();
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dialog.show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.schedule_dialog, null);
        builder.setView(view);

        final ListView listview = view.findViewById(R.id.listview_dialog);
        final TextView title = view.findViewById(R.id.tv_title);


        title.setText(divisiondate);

        final AlertDialog dialog = builder.create();
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        SimpleAdapter simpleAdapter = new SimpleAdapter(BaseActivity.this, dialogItemList,
                R.layout.item_dialog,
                new String[]{TAG_TIME, TAG_NAME},
                new int[]{R.id.schedule_time, R.id.schedule_name});

        listview.setAdapter(simpleAdapter);
        dialog.show();
    }
}
