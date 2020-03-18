package com.jubumam.surefin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jubumam.surefin.databinding.ItemOrderBinding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private OrderSyncTask orderAsyncTask;

    private List<String> timeList;
    private List<Order> list;
    private String strToday;
    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private String origin5;
    private String origin6;
    private String origin7;
    private String origin8;
    private String origin9;
    private String origin4;
    private String origin3;
    private String origin2;
    private String origin1;
    private String strDayofWeek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView = findViewById(R.id.rv_order);
        adapter = new OrderAdapter();

        orderAsyncTask = (OrderSyncTask) new OrderSyncTask().execute();

        adapter = new OrderAdapter(new OrderAdapter.OrderClickListener() {
            @Override
            public void PhoneClicked(Order model) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "02-2051-3500"));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }

            @Override
            public void SimpleClicked(Order model) {
                Intent intent = new Intent(OrderActivity.this,OrderCheckActivity.class);
                intent.putExtra("mealTime",model.getTime());
                intent.putExtra("date",model.getDate());
                startActivity(intent);
            }
        });


    }

    public class OrderSyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;

            orderQuery();
            return null;

        }

        protected void onPostExecute(String result) {

        }

        protected void onCancelled() {
            super.onCancelled();
        }

    }

    private void orderQuery() {
        Connection connection = null;
        strToday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        strDayofWeek = new SimpleDateFormat("yyyy년 MM월 dd일 (EE요일)").format(new Date());


        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            list = new ArrayList<>();
            timeList = new ArrayList<>();
            byte bt[];

            timeList.add("아침");
            timeList.add("점심");
            timeList.add("저녁");


            for (int i = 0; i < timeList.size(); i++) {
                String time = timeList.get(i).toString();
                ResultSet resultSetlist = statement.executeQuery("select * from Su_365식단 where 일자='" + strToday + "' AND 시간 = '" + time + "' ");
                while (resultSetlist.next()) {


//                    Blob bloblist = resultSetlist.getBlob("식사후사진");
//                    if (bloblist != null) {
//                        bt = bloblist.getBytes(1, (int) bloblist.length());
//                        bitmap = BitmapFactory.decodeByteArray(bt, 0, bt.length);
//                    } else {
//                        bitmap = null;
//                    }

                    String date = resultSetlist.getString("일자");
                    String main = resultSetlist.getString("주요리");
                    String bab = resultSetlist.getString("밥");
                    String jug = resultSetlist.getString("죽");
                    String gug = resultSetlist.getString("국");
                    String banchan1 = resultSetlist.getString("반찬1");
                    String banchan2 = resultSetlist.getString("반찬2");
                    String banchan3 = resultSetlist.getString("반찬3");
                    String banchan4 = resultSetlist.getString("반찬4");
                    String snack = resultSetlist.getString("디저트");
                    String fruit = resultSetlist.getString("과일");

                    list.add(new Order(date, time, main, bab, jug, gug, banchan1, banchan2, banchan3, banchan4, snack, fruit));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView tv_date = findViewById(R.id.tv_date);
                            tv_date.setText(strDayofWeek);
                            adapter.setItems(list);
                            recyclerView.setAdapter(adapter);
                        }
                    });


                }

            }


            ResultSet originRS = statement.executeQuery("select * from Su_원산지 where 일자='" + strToday + "'");
            while (originRS.next()) {
                origin1 = originRS.getString("원산지1");
                origin2 = originRS.getString("원산지2");
                origin3 = originRS.getString("원산지3");
                origin4 = originRS.getString("원산지4");
                origin5 = originRS.getString("원산지5");
                origin6 = originRS.getString("원산지6");
                origin7 = originRS.getString("원산지7");
                origin8 = originRS.getString("원산지8");
                origin9 = originRS.getString("원산지9");

                TextView tv_origin = findViewById(R.id.tv_origin);
                if(origin4 ==null){
                    origin4 = "";
                }else {
                    origin4 = ", "+ origin4;
                }
                if(origin5 ==null){
                    origin5 = "";
                }else {
                    origin5 = ", "+ origin5;
                }
                if(origin6 ==null){
                    origin6 = "";
                }else {
                    origin6 = ", "+ origin6;
                }
                if(origin7 ==null){
                    origin7 = "";
                }else {
                    origin7 = ", "+ origin7;
                }
                if(origin8 ==null){
                    origin8 = "";
                }else {
                    origin8 = ", "+ origin8;
                }
                if(origin9 ==null){
                    origin9 = "";
                }else {
                    origin9 = ", "+ origin9;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        tv_origin.setText(origin1 +", "+ origin2 +", "+ origin3 + origin4 + origin5 + origin6 + origin7 + origin8 + origin9);
                    }
                });
            }

            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private static class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
        interface OrderClickListener {
            void PhoneClicked(Order model);

            void SimpleClicked(Order model);
        }

        private OrderClickListener mListener;

        private List<Order> mItems = new ArrayList<>();

        public OrderAdapter() {
        }

        public OrderAdapter(OrderClickListener listener) {
            mListener = listener;
        }

        public void setItems(List<Order> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_order, parent, false);
            final OrderHolder viewHolder = new OrderHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        final Order item = mItems.get(viewHolder.getAdapterPosition());
                        mListener.PhoneClicked(item);
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
            Order item = mItems.get(position);
            holder.binding.setOrder(item);
            holder.binding.btnPhoneOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.PhoneClicked(mItems.get(holder.getAdapterPosition()));
                    }
                }
            });
            holder.binding.btnSimpleOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.SimpleClicked(mItems.get(holder.getAdapterPosition()));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class OrderHolder extends RecyclerView.ViewHolder {
            ItemOrderBinding binding;

            public OrderHolder(@NonNull View itemView) {
                super(itemView);
                binding = ItemOrderBinding.bind(itemView);
            }
        }
    }
}
