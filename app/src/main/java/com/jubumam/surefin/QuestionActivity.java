package com.jubumam.surefin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jubumam.surefin.ProtectorPackage.Protector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuestionActivity extends BaseActivity {
    private EditText et_title;
    private EditText et_contents;
    private TextView tv_textnumber;
    private AsyncTask<String, String, String> mTask;

    private String date;        //작성날짜
    private String responsibility;      //작성자(요양사)
    private String title;               //문의제목
    private String contents;            //문의내용

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
    private String commute;//출근확인
    private String personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        activateToolbar();


        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        commute = commuteRecipient.getCommute();
        rating = commuteRecipient.getRating();

        Login login = Login.getInstance();
        personId = login.getPersonId();
        responsibility = login.getResponsibility();

        if(personId == null) {
            Protector protector = Protector.getInstance();
            personId = protector.getRecipiId();
        }


        et_title = findViewById(R.id.et_title);
        et_contents = findViewById(R.id.et_contents);
        tv_textnumber = findViewById(R.id.tv_textnumber);
        et_contents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String input = et_contents.getText().toString();
                tv_textnumber.setText(input.length() + "");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Date currentDate = new Date();
        date = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);


        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = et_title.getText().toString();
                contents = et_contents.getText().toString();
                if (title.equals("")) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(QuestionActivity.this);
                    builder.setTitle("").setMessage("제목을 입력하세요.");
                    android.app.AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    mTask = new MySyncTask().execute();
                    finish();
                }
            }
        });

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
            Intent intent = new Intent(QuestionActivity.this, AnswerActivity.class);
            intent.putExtra("responsibility", responsibility);
            startActivity(intent);
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
            ResultSet resultSetlist = statement.executeQuery("insert into Su_요양사문의(일자,작성자,제목,내용,직원코드) values('" + date + "','" + responsibility + "','" + title + "','" + contents + "','"+personId+"') ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
