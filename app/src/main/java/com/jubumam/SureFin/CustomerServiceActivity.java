package com.jubumam.SureFin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
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
    private NoticeAdapter adapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);

        activateToolbar();

        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        name = commuteRecipient.getName();
        rating = commuteRecipient.getRating();
        responsibility = commuteRecipient.getResponsibility();

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
                startActivity(new Intent(CustomerServiceActivity.this,QuestionActivity.class));
            }
        });
        findViewById(R.id.btn_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerServiceActivity.this,AnswerActivity.class));
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
            Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();
            ResultSet resultSetlist = statement.executeQuery("select * from Su_공지사항 order by id desc");
            final List<Notice> list = new ArrayList<>();
            while (resultSetlist.next()) {
                id = resultSetlist.getInt("id");
                date = resultSetlist.getString("일자");
                writer = resultSetlist.getString("작성자");
                title = resultSetlist.getString("제목");
                contents = resultSetlist.getString("내용");

                list.add(new Notice(id,date,writer,title,contents));
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new NoticeAdapter(new NoticeAdapter.NoticeListener() {
                        @Override
                        public void setNoticeListener(Notice model) {
                            Intent intent = new Intent(CustomerServiceActivity.this,NoticeActivity.class);
                            intent.putExtra("id",model.getId());
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


    private static class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeHolder> {
        interface NoticeListener {
            void setNoticeListener(Notice model);
        }

        private NoticeListener mListener;
        private void setOnRecipientClickListener(NoticeListener listener) {
            mListener = listener;
        }

        private List<Notice> mItems = new ArrayList<>();

        public NoticeAdapter() {
        }

        public NoticeAdapter(NoticeListener listener) {
            mListener = listener;
        }

        public void setItems(List<Notice> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_notice, parent, false);
            final NoticeHolder viewHolder = new NoticeHolder(view);


            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final NoticeHolder holder, int position) {
            Notice item = mItems.get(position);
            holder.tv_title.setText(item.getTitle());
            holder.tv_date.setText(item.getDate());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        final Notice item = mItems.get(holder.getAdapterPosition());
                        mListener.setNoticeListener(item);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class NoticeHolder extends RecyclerView.ViewHolder {
            TextView tv_title;
            TextView tv_date;

            public NoticeHolder(@NonNull View itemView) {
                super(itemView);

                tv_title = itemView.findViewById(R.id.tv_title);
                tv_date = itemView.findViewById(R.id.tv_date);
            }
        }
    }

}

