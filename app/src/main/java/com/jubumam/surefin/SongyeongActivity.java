package com.jubumam.surefin;

import androidx.annotation.NonNull;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
    private String address1;
    private String address2;
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
    private int count = 0;
    private String ordermethod;
    private String firstOrder;
    private int generalCount = 0;
    private int caremealcount = 0;

    private TextView tv_caremealCount;
    private TextView tv_generalCount;


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

        tv_caremealCount=findViewById(R.id.tv_caremealCount);
        tv_generalCount=findViewById(R.id.tv_generalCount);
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

        timer = new Timer();

        TimerTask TT = new TimerTask() {
            @Override
            public void run() {

                orderTask = new OrderSyncTask().execute();
            }
        };
        timer.schedule(TT, 0, 60000); //Timer ??????

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);



    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();//????????? ??????
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
            tv_caremealCount.setText("????????? ?????? : "+caremealcount+"???");
            tv_generalCount.setText("????????? ?????? : "+generalCount+"???");
            generalCount = 0;
            caremealcount = 0;
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

            ResultSet areaRS = statement.executeQuery("select * from Su_???????????? where ???????????????='" + responsibilityID + "'");
            while (areaRS.next()){
                String area = areaRS.getString("?????????");
                areaList.add(area);
            }

            for (int i = 0; i < areaList.size(); i++) {
                String area = areaList.get(i).toString();
                ResultSet resultSetlist = statement.executeQuery("select * from Su_??????????????? where ??????='" + strToday + "' AND ?????? = '"+area+"' AND ???????????? = '?????????' order by ???????????? DESC");
                while (resultSetlist.next()) {
                    name = resultSetlist.getString("?????????");
                    phoneNumber = resultSetlist.getString("????????????");
                    address1 = resultSetlist.getString("??????1");
                    address2 = resultSetlist.getString("??????2");
                    quantity = resultSetlist.getString("??????");
                    orderPrice = resultSetlist.getString("??????");
                    paymentmethod = resultSetlist.getString("????????????");
                    complete = resultSetlist.getString("????????????");
                    orderTime = resultSetlist.getString("????????????");
                    ordermethod = resultSetlist.getString("????????????");
                    firstOrder = resultSetlist.getString("?????????");

                    if(ordermethod!=null && ordermethod.equals("??????")) {
                        generalCount++;
                    }else if(ordermethod!=null &&ordermethod.equals("??????")){
                        caremealcount++;
                    }
                    count++;


                    address = address1 +" "+address2;
                    list.add(new SongyeongOrder(name, phoneNumber, address, quantity, orderPrice, paymentmethod, complete, orderTime, ordermethod, firstOrder));
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

        }

        protected void onCancelled() {
            super.onCancelled();
        }

    }


    private void completeQuery(String strCPTime) {

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            PreparedStatement ps = connection.prepareStatement("UPDATE Su_??????????????? SET ???????????? = ?,?????????????????? = ?,????????? = ? where ?????? = '" + strToday + "' and ???????????? = '" + strCPTime + "'");
            ps.setString(1, "??????");
            ps.setString(2, completeTime);
            ps.setString(3, "False");
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
            holder.tv_quantity.setText("????????? "+item.getQuantity() + " ???");
//            holder.tv_complete.setText(item.getComplete());
            if(item.getOrdermethod() == null){
                holder.tv_etc.setVisibility(View.GONE);
            }else if(item.getOrdermethod().equals("??????")){
                holder.tv_etc.setTextColor(Color.RED);
                holder.tv_etc.setText("( " + item.getOrdermethod() + " )");
            }else{
                holder.tv_etc.setText("( " +item.getOrdermethod()+ " )");
            }

            if(item.getFirstOrder().equals("True")){
                holder.tv_firstorder.setText("??? ?????? ?????? ?????????.");

            }else{
                holder.tv_firstorder.setVisibility(View.GONE);
            }
            holder.tv_complete.setText(item.getOrderTime());
            if (item.getComplete() != null && item.getComplete().equals("??????")) {
                holder.tv_complete.setText("????????????");
                holder.btn_complete.setBackgroundColor(Color.GRAY);
            }
            holder.btn_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.syCompleteClicked(mItems.get(holder.getAdapterPosition()).getOrderTime());
                        holder.tv_complete.setText("????????????");
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
            private TextView tv_firstorder;
            private TextView tv_etc;
            private Button btn_complete;
            private ImageView img_phone;

            public SYOrderViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_phoneNumber = itemView.findViewById(R.id.tv_phoneNumber);
                tv_address = itemView.findViewById(R.id.tv_address);
                tv_quantity = itemView.findViewById(R.id.tv_quantity);
                tv_complete = itemView.findViewById(R.id.tv_complete);
                tv_firstorder = itemView.findViewById(R.id.tv_firstorder);
                tv_etc = itemView.findViewById(R.id.tv_etc);
                btn_complete = itemView.findViewById(R.id.btn_complete);
                img_phone = itemView.findViewById(R.id.img_phone);
            }
        }
    }
}
