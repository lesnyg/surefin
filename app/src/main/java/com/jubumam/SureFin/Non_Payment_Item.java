package com.jubumam.SureFin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Non_Payment_Item extends BaseActivity {

//    private LinearLayout linblocho;
//    private LinearLayout lincloud;
//    private LinearLayout lincrane;
//    private LinearLayout lindeer;
//    private LinearLayout linmountain;
//    private LinearLayout linpinetree;
//    private LinearLayout linstone;
//    private LinearLayout linturtle;
//    private LinearLayout linwater;
//    private LinearLayout linsun;
//
//    private TextView suntext;
//    private TextView watertext;
//    private TextView turtletext;
//    private TextView stonetext;
//    private TextView pinetreetext;
//    private TextView mountaintext;
//    private TextView deertext;
//    private TextView cranetext;
//    private TextView cloudtext;
//    private TextView blochotext;
//    private String gender;      //성별
//    private String birth;       //생년원일
//    private String pastdisease;      //과거병력
//    private String div;
//    private TextView tv_mdiv;

    private String TAG = "PickerActivity";

    private AsyncTask<String, String, String> cTask;
    private AsyncTask<String, String, String> mTask;

    private String name;        //이름
    private String rating;      //등급
    private String responsibility;      //직원명
    private String classification; //대분류
    private String htitle; //비급여타이틀
    private String memberdivision; //회원구분
    private String utilization; //이용한도
    private String startMon;
    private String endMon;
    private String strDate;
    private String dbDate;
    private String schedule_date;//일자
    private String scheduletime;//근무시간
    private String contracttime; //계약시간
    private String schedulename;//계약수급자명
    private String divisiontotal;
    private String division;
    private String divisiondate;
    private String divisiontime;
    private String date2;
    private String date1;
    private String offer;
    private String offerDate;

    private Date date;

    private List<Non> nonList;
    private List<Non> nonUseList;
    private NonAdapter mNonAdapter;
    private nonOfferAdapter mNonOfferAdapter;
    private RecyclerView nonRecycler;
    private RecyclerView nonOfferRecycler;

    private TextView tv_name;
    private TextView tv_name1;
    private TextView tv_thisMonth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nonpaymentitem);

        activateToolbar();

        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        name = commuteRecipient.getName();
        rating = commuteRecipient.getRating();
        responsibility = commuteRecipient.getResponsibility();


//        suntext = findViewById(R.id.suntext);
//        watertext = findViewById(R.id.watertext);
//        turtletext = findViewById(R.id.turtletext);
//        stonetext = findViewById(R.id.stonetext);
//        pinetreetext = findViewById(R.id.pinetreetext);
//        mountaintext = findViewById(R.id.mountaintext);
//        deertext = findViewById(R.id.deertext);
//        cranetext = findViewById(R.id.cranetext);
//        cloudtext = findViewById(R.id.cloudtext);
//        blochotext = findViewById(R.id.blochotext);

//        linsun = findViewById(R.id.linsun);
//        linwater = findViewById(R.id.linwater);
//        linturtle = findViewById(R.id.linturtle);
//        linstone = findViewById(R.id.linstone);
//        linpinetree = findViewById(R.id.linpinetree);
//        linmountain = findViewById(R.id.linmountain);
//        lindeer = findViewById(R.id.lindeer);
//        lincrane = findViewById(R.id.lincrane);
//        lincloud = findViewById(R.id.lincloud);
//        linblocho = findViewById(R.id.linblocho);

//        linsun.setVisibility(View.GONE);
//        linwater.setVisibility(View.GONE);
//        linturtle.setVisibility(View.GONE);
//        linstone.setVisibility(View.GONE);
//        linpinetree.setVisibility(View.GONE);
//        linmountain.setVisibility(View.GONE);
//        lincrane.setVisibility(View.GONE);
//        lincloud.setVisibility(View.GONE);
//        linblocho.setVisibility(View.GONE);
//        lindeer.setVisibility(View.GONE);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_name = findViewById(R.id.tv_name);
        tv_name1 = findViewById(R.id.tv_name1);
//        tv_mdiv = findViewById(R.id.tv_mdiv);
        tv_name.setText(name + "님");
        tv_name1.setText(name + "님");

        nonRecycler = findViewById(R.id.recycler_reservation);
        nonOfferRecycler = findViewById(R.id.recycler_historyofuse);
        mNonAdapter = new NonAdapter();
        mNonOfferAdapter = new nonOfferAdapter();

        date = new Date();
        String currentDate = new SimpleDateFormat("yyyy.MM", Locale.KOREAN).format(date);
        TextView vtxt1 = findViewById(R.id.vtxt1);
        TextView tv_currentDate = findViewById(R.id.tv_currentDate);
        vtxt1.setText(currentDate);
        tv_currentDate.setText(currentDate + "월");
        tv_thisMonth = findViewById(R.id.tv_thisMonth);
        strDate = new SimpleDateFormat("yyyy-MM").format(date);
        startMon = strDate + "-" + "01";
        endMon = strDate + "-" + "32";

        mTask = new MySyncTask().execute();
    }


    private void query() {
        Connection con = null;


        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement sts = con.createStatement();

            ResultSet resultSet = sts.executeQuery("select * from Su_비급여신청자 where 수급자명 = '" + name + "' AND (일자 BETWEEN '" + startMon + "' AND '" + endMon + "') order by id");

            nonList = new ArrayList<>();
            nonUseList = new ArrayList<>();

            while (resultSet.next()) {
                //일자

                dbDate = resultSet.getString("일자");
                classification = resultSet.getString("대분류"); //대분류
                htitle = resultSet.getString("Title"); //비급여타이틀
                memberdivision = resultSet.getString("회원구분"); //회원구분
                utilization = resultSet.getString("이용한도"); //이용한도
                offer = resultSet.getString("서비스제공");        //서비스제공
                offerDate = resultSet.getString("서비스제공일자");

                nonList.add(new Non(classification, htitle, memberdivision, utilization));
                if (offer.equals("제공")) {
                    nonUseList.add(new Non(classification, htitle, memberdivision, utilization, offerDate, "1회"));
                }

            }

            con.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());

        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNonAdapter.setItems(nonList);
                mNonOfferAdapter.setItems(nonUseList);
                nonRecycler.setAdapter(mNonAdapter);
                nonOfferRecycler.setAdapter(mNonOfferAdapter);
                if (dbDate == null) {
                    nonList.add(new Non("", "신청내역이 없습니다.", "", ""));
                    String d = new SimpleDateFormat("MM", Locale.KOREAN).format(date);
                    tv_thisMonth.setText(d + "월");
                } else {
                    String currentMonth = dbDate.substring(5, 7);
                    tv_thisMonth.setText(currentMonth + "월");
                }
            }
        });
    }


    public class MySyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled()) {
                return null;
            }
            query();
            return null;
        }

        protected void onPostExecute(String result) {
        }


        protected void onCancelled() {
            super.onCancelled();
        }
    }


    private static class NonAdapter extends RecyclerView.Adapter<NonAdapter.NonViewHolder> {
        public Non item;

        interface NonClickListener {
            void setNonClick(Non model);
        }

        private NonClickListener mListener;

        private List<Non> mItems = new ArrayList<>();

        public NonAdapter() {
        }

        public NonAdapter(NonClickListener listener) {
            mListener = listener;
        }

        public void setItems(List<Non> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public NonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_nonreservationlist, parent, false);
            final NonViewHolder viewHolder = new NonViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.setNonClick(mItems.get(viewHolder.getAdapterPosition()));
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull NonViewHolder holder, int position) {
            item = mItems.get(position);
            holder.tv_nontitle.setText(item.getHtitle());
            holder.tv_nonutilization.setText(item.getUtilization());
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class NonViewHolder extends RecyclerView.ViewHolder {
            TextView tv_nontitle;
            TextView tv_nonutilization;

            public NonViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_nontitle = itemView.findViewById(R.id.tv_nontitle);
                tv_nonutilization = itemView.findViewById(R.id.tv_nonutilization);
            }
        }
    }

    private static class nonOfferAdapter extends RecyclerView.Adapter<nonOfferAdapter.OfferViewHolder> {
        interface setNonOfferClicked {
            void nonOfferClick(Non model);
        }

        private setNonOfferClicked mListener;

        private List<Non> mItems = new ArrayList<>();

        public nonOfferAdapter() {
        }

        public nonOfferAdapter(setNonOfferClicked listener) {
            mListener = listener;
        }

        public void setItems(List<Non> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_nonlist, parent, false);
            final OfferViewHolder viewHolder = new OfferViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        final Non item = mItems.get(viewHolder.getAdapterPosition());
                        mListener.nonOfferClick(item);
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
            Non item = mItems.get(position);
            holder.tv_servicedate.setText(item.getOfferDate().substring(8) + "일");
            holder.tv_serviceTitle.setText(item.getHtitle());
            holder.tv_serviceTime.setText(item.getOfferTime());
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class OfferViewHolder extends RecyclerView.ViewHolder {
            TextView tv_servicedate;
            TextView tv_serviceTitle;
            TextView tv_serviceTime;

            public OfferViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_servicedate = itemView.findViewById(R.id.tv_servicedate);
                tv_serviceTitle = itemView.findViewById(R.id.tv_serviceTitle);
                tv_serviceTime = itemView.findViewById(R.id.tv_serviceTime);

            }
        }
    }
}
