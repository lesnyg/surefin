package com.jubumam.SureFin.ProtectorPackage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.jubumam.SureFin.Answer;
import com.jubumam.SureFin.AnswerAdapter;
import com.jubumam.SureFin.BaseActivity;
import com.jubumam.SureFin.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProtectorAnswerActivity extends ProtectorBaseActivity {

    private AsyncTask<String, String, String> mTask;
    private String responsibility;      //작성자(요양사)
    private RecyclerView recyclerview_answer;
    private AnswerAdapter answerAdapter;

    private String date3;
    private String date2;
    private String commute;
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
    private String recipiId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        activateToolbar();

        recyclerview_answer = findViewById(R.id.recyclerview_answer);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Protector protector = Protector.getInstance();
        recipiId = protector.getRecipiId();

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
            ResultSet resultSetlist = statement.executeQuery("select * from Su_수급자문의 where 수급자코드='" + recipiId + "' order by id desc");
            final List<Answer> list = new ArrayList<>();
            while (resultSetlist.next()) {
                String date = resultSetlist.getString("일자");
                String title = resultSetlist.getString("제목");
                String contents = resultSetlist.getString("내용");
                String answer = resultSetlist.getString("답변");
                String answerDate = resultSetlist.getString("답변일자");
                if (answer == null || answerDate == null) {
                    answer = "답변내역이 없습니다.";
                    answerDate = "";
                }

                list.add(new Answer(date, responsibility, title, contents, answer, answerDate));

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    answerAdapter = new AnswerAdapter();
                    answerAdapter.setItems(list);
                    recyclerview_answer.setAdapter(answerAdapter);

                }
            });

            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}