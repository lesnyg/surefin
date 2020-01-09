package com.jubumam.SureFin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceActivity extends AppCompatActivity {

    private String name;        //이름
    private String gender;      //성별
    private String birth;       //생년원일
    private String rating;      //등급
    private String pastdisease;      //과거병력
    private String responsibility;      //직원명

    private RecyclerView recyclerView;
    private List<Notice> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get the ActionBar here to configure the way it behaves.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);

        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        name = commuteRecipient.getName();
        rating = commuteRecipient.getRating();
        responsibility = commuteRecipient.getResponsibility();


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

        list = new ArrayList<>();

        list.add(new Notice("암브로시아 요양서비스 오픈 축하1","2019.12.02","내용내용내용"));
        list.add(new Notice("암브로시아 요양서비스 오픈 축하2","2019.12.02","내용내용내용"));
        list.add(new Notice("암브로시아 요양서비스 오픈 축하3","2019.12.02","내용내용내용"));
        list.add(new Notice("암브로시아 요양서비스 오픈 축하4","2019.12.02","내용내용내용"));
        list.add(new Notice("암브로시아 요양서비스 오픈 축하5","2019.12.02","내용내용내용"));

        recyclerView = findViewById(R.id.recyclerview_notice);
        NoticeAdapter adapter = new NoticeAdapter();
        adapter.setItems(list);
        recyclerView.setAdapter(adapter);


    }

    private static class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeHolder> {
        interface NoticeListener {
            void setNoticeListener(Notice model);
        }

        private NoticeListener mListener;

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
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        final Notice item = mItems.get(viewHolder.getAdapterPosition());
                        mListener.setNoticeListener(item);
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {
            Notice item = mItems.get(position);
            holder.tv_title.setText(item.getTitle());
            holder.tv_date.setText(item.getDate());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_action,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CustomerServiceActivity.this, MenuMain.class);
                startActivity(intent);
                break;
            case R.id.action_notice:
                Intent intent1 = new Intent(CustomerServiceActivity.this, CustomerServiceActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_serviceEdit:
                Intent i5 = new Intent(CustomerServiceActivity.this, EditRecipientActivity.class);
                i5.putExtra("route", "edit");
                startActivity(i5);
                break;
            case R.id.action_sign:
                Intent i8 = new Intent(CustomerServiceActivity.this, signActivity.class);
                startActivity(i8);
                break;
        }
        return super.onOptionsItemSelected(item);


    }
}

