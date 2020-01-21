package com.jubumam.SureFin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AnswerActivity extends BaseActivity {

    private AsyncTask<String, String, String> mTask;
    private String responsibility;      //작성자(요양사)
    private RecyclerView recyclerview_answer;
    private AnswerAdapter answerAdapter;

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
        setContentView(R.layout.activity_answer);

        activateToolbar();

        recyclerview_answer = findViewById(R.id.recyclerview_answer);

        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        responsibility = commuteRecipient.getResponsibility();
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
            Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();
            ResultSet resultSetlist = statement.executeQuery("select * from Su_요양사문의 where 작성자='" + responsibility + "' order by id desc");
            final List<Answer> list = new ArrayList<>();
            while (resultSetlist.next()) {
                String date = resultSetlist.getString("일자");
                String title = resultSetlist.getString("제목");
                String contents = resultSetlist.getString("내용");
                String answer = resultSetlist.getString("답변");
                String answerDate = resultSetlist.getString("답변일자");
                if(answer==null || answerDate == null){
                    answer = "답변내역이 없습니다.";
                    answerDate = "";
                }

                list.add(new Answer(date, responsibility, title, contents, answer, answerDate));

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    answerAdapter = new AnswerAdapter(new AnswerAdapter.setAnswerClicked() {
                        @Override
                        public void AnswerClick(Answer model) {

                        }
                    });

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