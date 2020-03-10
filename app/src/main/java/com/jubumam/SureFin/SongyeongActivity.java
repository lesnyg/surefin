package com.jubumam.SureFin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SongyeongActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private String name = "";
    private String phoneNumber;
    private String address;
    private String quantity;
    private String orderPrice;
    private String paymentmethod = "";
    private String complete = "";
    private String orderTime;
    private String cpTime;

    private RecyclerView syRecyclerView;
    private SYOrderAdapter adapter;


    private AsyncTask<String, String, String> orderTask;
    private AsyncTask<String, String, String> completeTask;
    private Connection connection = null;
    private String strToday;

    private List<SongyeongOrder> list;
    private String responsibility;
    private String responsibilityID;
    private String area;
    private List<String> areaList;
    private String completeTime;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Timer timer;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songyeong);
        activateToolbar();

        Login login = Login.getInstance();
        responsibility = login.getResponsibility();
        responsibilityID = login.getPersonId();

        Date today = new Date();
        strToday = new SimpleDateFormat("yyyy-MM-dd").format(today);

        syRecyclerView = findViewById(R.id.rv_syorder);
        adapter = new SYOrderAdapter(new SYOrderAdapter.syOrderClickListener() {
            @Override
            public void syClicked(SongyeongOrder model) {

            }

            @Override
            public void syCompleteClicked(String orderTimeCheck) {
                completeTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
                completeTask = new CompleteSyncTask().execute(orderTimeCheck);

            }

            @Override
            public void onPhoneClick(String phone) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        orderTask = new OrderSyncTask().execute();

        timer = new Timer();

        TimerTask TT = new TimerTask() {
            @Override
            public void run() {
                orderTask = new OrderSyncTask().execute();
            }
        };
        timer.schedule(TT, 0, 60000); //Timer 실행

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();//타이머 종료
    }

    @Override
    public void onRefresh() {
        orderTask = new OrderSyncTask().execute();
        mSwipeRefreshLayout.setRefreshing(false);
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

        strToday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            list = new ArrayList<>();
            areaList = new ArrayList<>();

            ResultSet areaRS = statement.executeQuery("select * from Su_담당지역 where 담당자코드='" + responsibilityID + "'");
            while (areaRS.next()){
                String area = areaRS.getString("지역명");
                areaList.add(area);
            }

            for (int i = 0; i < areaList.size(); i++) {
                String area = areaList.get(i).toString();
                ResultSet resultSetlist = statement.executeQuery("select * from Su_주문리스트 where 일자='" + strToday + "' AND 지역 = '"+area+"' AND 배달확인 = '진행중' order by 주문시간 DESC");
                while (resultSetlist.next()) {
                    name = resultSetlist.getString("주문자");
                    phoneNumber = resultSetlist.getString("전화번호");
                    address = resultSetlist.getString("주소");
                    quantity = resultSetlist.getString("수량");
                    orderPrice = resultSetlist.getString("금액");
                    paymentmethod = resultSetlist.getString("계산방법");
                    complete = resultSetlist.getString("배달확인");
                    orderTime = resultSetlist.getString("주문시간");


                    list.add(new SongyeongOrder(name, phoneNumber, address, quantity, orderPrice, paymentmethod, complete, orderTime));
                }}

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.setItems(list);
                    syRecyclerView.setAdapter(adapter);
                }
            });
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public class CompleteSyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;

            completeQuery(strings[0]);
            return null;

        }
        protected void onPostExecute(String result) {
            Toast.makeText(SongyeongActivity.this, result, Toast.LENGTH_SHORT).show();


        }

        protected void onCancelled() {
            super.onCancelled();
        }

    }


    private void completeQuery(String strCPTime) {

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            PreparedStatement ps = connection.prepareStatement("UPDATE Su_주문리스트 SET 배달확인 = ?,배달완료시간 = ? where 일자 = '" + strToday + "' and 주문시간 = '" + strCPTime + "'");
            ps.setString(1, "완료");
            ps.setString(2, completeTime);
            ps.executeUpdate();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static class SYOrderAdapter extends RecyclerView.Adapter<SYOrderAdapter.SYOrderViewHolder> {
        interface syOrderClickListener {
            void syClicked(SongyeongOrder model);
            void syCompleteClicked(String completeTime);
            void onPhoneClick(String phone);
        }

        private syOrderClickListener mListener;

        private List<SongyeongOrder> mItems = new ArrayList<>();

        public SYOrderAdapter() {
        }

        public SYOrderAdapter(syOrderClickListener listener) {
            mListener = listener;
        }

        public void setItems(List<SongyeongOrder> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public SYOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_songyeng, parent, false);
            final SYOrderViewHolder viewHolder = new SYOrderViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        final SongyeongOrder item = mItems.get(viewHolder.getAdapterPosition());
                        mListener.syClicked(item);
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final SYOrderViewHolder holder, int position) {
            SongyeongOrder item = mItems.get(position);
            holder.tv_phoneNumber.setText(item.getPhoneNumber());
            holder.tv_address.setText( item.getAddress());
            holder.tv_quantity.setText("도시락 "+item.getQuantity() + " 개");
//            holder.tv_complete.setText(item.getComplete());
            holder.tv_complete.setText(item.getOrderTime());
            if (item.getComplete() != null && item.getComplete().equals("완료")) {
                holder.tv_complete.setText("배달완료");
                holder.btn_complete.setBackgroundColor(Color.GRAY);

            }
            holder.btn_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.syCompleteClicked(mItems.get(holder.getAdapterPosition()).getOrderTime());
                        holder.tv_complete.setText("배달완료");
                        holder.btn_complete.setBackgroundColor(Color.GRAY);
                    }
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.syClicked(mItems.get(holder.getAdapterPosition()));

                    }
                }
            });
            holder.img_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onPhoneClick(mItems.get(holder.getAdapterPosition()).getPhoneNumber());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class SYOrderViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_address;
            private TextView tv_phoneNumber;
            private TextView tv_quantity;
            private TextView tv_complete;
            private Button btn_complete;
            private ImageView img_phone;

            public SYOrderViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_phoneNumber = itemView.findViewById(R.id.tv_phoneNumber);
                tv_address = itemView.findViewById(R.id.tv_address);
                tv_quantity = itemView.findViewById(R.id.tv_quantity);
                tv_complete = itemView.findViewById(R.id.tv_complete);
                btn_complete = itemView.findViewById(R.id.btn_complete);
                img_phone = itemView.findViewById(R.id.img_phone);
            }
        }
    }
}