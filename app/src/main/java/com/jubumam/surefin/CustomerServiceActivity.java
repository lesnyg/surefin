package com.jubumam.surefin;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceActivity extends BaseActivity {

    //수급자 요양사 정보
    private String name;        //이름
    private String gender;      //성별
    private String birth;       //생년원일
    private String rating;      //등급
    private String pastdisease;      //과거병력
    private String responsibility;      //직원명


    //리사이클러뷰
    private RecyclerView recyclerView;
    private List<Notice> list;

    //공지사항
    private AsyncTask<String, String, String> mTask;
    private String date;
    private String writer;
    private String title;
    private String contents;
    private String commute;
    private NoticeAdapter adapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);

        activateToolbar();

        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        commute = commuteRecipient.getCommute();
        name = commuteRecipient.getName();
        rating = commuteRecipient.getRating();
        responsibility = commuteRecipient.getResponsibility();
        Intent intent = getIntent();
//        if (commute == null) {
//            name = intent.getExtras().getString("name");
//            rating = intent.getExtras().getString("rating");
//            responsibility = intent.getExtras().getString("responsibility");
//        }



        mTask = new MySyncTask().execute();
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_ambrosia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "1688-0888"));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.btn_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerServiceActivity.this, QuestionActivity.class);
                intent.putExtra("responsibility", responsibility);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerServiceActivity.this, AnswerActivity.class);
                intent.putExtra("responsibility", responsibility);
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.recyclerview_notice);


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
            ResultSet resultSetlist = statement.executeQuery("select * from Su_공지사항 order by id desc");
            final List<Notice> list = new ArrayList<>();
            while (resultSetlist.next()) {
                id = resultSetlist.getInt("id");
                date = resultSetlist.getString("일자");
                writer = resultSetlist.getString("작성자");
                title = resultSetlist.getString("제목");
                contents = resultSetlist.getString("내용");

                list.add(new Notice(id, date, writer, title, contents));
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new NoticeAdapter(new NoticeAdapter.NoticeListener() {
                        @Override
                        public void setNoticeListener(Notice model) {
                            Intent intent = new Intent(CustomerServiceActivity.this, NoticeActivity.class);
                            intent.putExtra("id", model.getId());
                            startActivity(intent);
                        }
                    });
                    adapter.setItems(list);
                    recyclerView.setAdapter(adapter);
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

