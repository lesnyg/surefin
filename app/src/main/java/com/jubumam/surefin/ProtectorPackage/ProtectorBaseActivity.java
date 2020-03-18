package com.jubumam.surefin.ProtectorPackage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jubumam.surefin.Dialog;
import com.jubumam.surefin.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProtectorBaseActivity extends AppCompatActivity {
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
    private String personId;
    private String recipiId;


    protected Toolbar activateToolbar() {
        SharedPreferences sharedPreferences = getSharedPreferences("file", 0);
        noti_id = sharedPreferences.getString("notiID", "");

        notiTask = new NotiSyncTask().execute();

        Protector protector = Protector.getInstance();
        recipiId = protector.getRecipiId();
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
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);

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
            ResultSet notiRS = statement.executeQuery("select id from Su_수급자공지사항 where id > '" + noti_id + "' order by id");
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.protector_appbar_action, menu);
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
                    Intent intent1 = new Intent(ProtectorBaseActivity.this, ProtectorCustomerServiceActivity.class);
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
                Intent intent = new Intent(this, ProtectorMainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_notice:
                Intent intent1 = new Intent(this, ProtectorCustomerServiceActivity.class);
                startActivity(intent1);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


}
