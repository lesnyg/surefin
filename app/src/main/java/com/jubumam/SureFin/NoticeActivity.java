package com.jubumam.SureFin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NoticeActivity extends BaseActivity {
    private AsyncTask<String, String, String> mTask;
    private String date;
    private String writer;
    private String title;
    private String contents;
    private int id;

    private String responsibility;      //직원명
    private TextView tv_noticetitle;
    private TextView tv_noticewriter;
    private TextView tv_noticedate;
    private TextView tv_noticecontents;

    //일정관리
    private String date3;
    private String date2;
    private String TAG = "PickerActivity";
    private AsyncTask<String, String, String> cTask;
    private String schedule_date;//일자
    private String scheduletime;//근무시간
    private String contracttime; //계약시간
    private String schedulename;//계약수급자명
    private String divisiontotal;
    private String divisiondate;
    private String divisiontime;
    private String division;//구분

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        activateToolbar();

        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        responsibility = commuteRecipient.getResponsibility();

        Intent intent = getIntent();

        tv_noticetitle = findViewById(R.id.tv_noticetitle);
        tv_noticewriter = findViewById(R.id.tv_noticewriter);
        tv_noticedate = findViewById(R.id.tv_noticedate);
        tv_noticecontents = findViewById(R.id.tv_noticecontents);

        id = intent.getExtras().getInt("id");

        mTask = new MySyncTask().execute();
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

    private void listQuery() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            ResultSet resultSetlist = statement.executeQuery("select * from Su_공지사항 where id='" + id + "' order by id desc");

            while (resultSetlist.next()) {
                date = resultSetlist.getString("일자");
                writer = resultSetlist.getString("작성자");
                title = resultSetlist.getString("제목");
                contents = resultSetlist.getString("내용");
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_noticetitle.setText(title);
                    tv_noticewriter.setText(writer);
                    tv_noticedate.setText(date);
                    tv_noticecontents.setText(contents);
                }
            });


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
