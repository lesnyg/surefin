package com.jubumam.SureFin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Locale;

public class Non_Payment_Item extends AppCompatActivity {

    private LinearLayout linblocho;
    private LinearLayout lincloud;
    private LinearLayout lincrane;
    private LinearLayout lindeer;
    private LinearLayout linmountain;
    private LinearLayout linpinetree;
    private LinearLayout linstone;
    private LinearLayout linturtle;
    private LinearLayout linwater;
    private LinearLayout linsun;

    private TextView suntext;
    private TextView watertext;
    private TextView turtletext;
    private TextView stonetext;
    private TextView pinetreetext;
    private TextView mountaintext;
    private TextView deertext;
    private TextView cranetext;
    private TextView cloudtext;
    private TextView blochotext;

    private String name;        //이름
    private String gender;      //성별
    private String birth;       //생년원일
    private String rating;      //등급
    private String pastdisease;      //과거병력
    private String responsibility;      //직원명
    private String classification; //대분류
    private String htitle; //비급여타이틀
    private String memberdivision; //회원구분
    private String utilization; //이용한도
    private String startMon;
    private String endMon;
    private String strDate;
    private AsyncTask<String, String, String> mTask;
    private TextView tv_name;
    private TextView tv_name1;
    private TextView tv_mdiv;
    private String div;

    private List<Non> nonList;
    private NonAdapter mNonAdapter;
    private RecyclerView nonRecycler;
    private String dbDate;
    private TextView tv_thisMonth;
    private Date date;

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
    private String TAG = "PickerActivity";


    private AsyncTask<String, String, String> cTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nonpaymentitem);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get the ActionBar here to configure the way it behaves.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);

        final Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        gender = intent.getExtras().getString("gender");
        rating = intent.getExtras().getString("rating");
        birth = intent.getExtras().getString("birth");
        pastdisease = intent.getExtras().getString("pastdisease");
        responsibility = intent.getExtras().getString("responsibility");

//        gender = intent.getExtras().getString("gender");
//        rating = intent.getExtras().getString("rating");
//        birth = intent.getExtras().getString("birth");
//        pastdisease = intent.getExtras().getString("pastdisease");
//        responsibility = intent.getExtras().getString("responsibility");


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
        mNonAdapter = new NonAdapter();

        date = new Date();
        String currentDate = new SimpleDateFormat("yyyy.MM", Locale.KOREAN).format(date);
        TextView vtxt1 = findViewById(R.id.vtxt1);
        vtxt1.setText(currentDate);
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
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement sts = con.createStatement();

            ResultSet resultSet = sts.executeQuery("select * from Su_비급여신청자 where 수급자명 = '" + name + "' AND (일자 BETWEEN '" + startMon + "' AND '" + endMon + "')");

            nonList = new ArrayList<>();

            while (resultSet.next()) {
                //일자

                dbDate = resultSet.getString("일자");
                classification = resultSet.getString("대분류"); //대분류
                htitle = resultSet.getString("Title"); //비급여타이틀
                memberdivision = resultSet.getString("회원구분"); //회원구분
                utilization = resultSet.getString("이용한도"); //이용한도

                nonList.add(new Non(classification, htitle, memberdivision, utilization));

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                        if (classification.equals("해-치유")){
//                            linsun.setVisibility(View.VISIBLE);
//                            suntext.setText("해 서비스 : "+htitle);
//                            tv_mdiv.setText(memberdivision+"  회원");
//                        }else if (classification.equals("산-운동")){
//                            linmountain.setVisibility(View.VISIBLE);
//                            mountaintext.setText("산 서비스 : "+htitle);
//                            tv_mdiv.setText(memberdivision+"  회원");
//                        }else if (classification.equals("물-수치료")){
//                            linwater.setVisibility(View.VISIBLE);
//                            watertext.setText("물 서비스 : "+htitle);
//                            tv_mdiv.setText(memberdivision+"  회원");
//                        }else if (classification.equals("돌-보안")){
//                            linstone.setVisibility(View.VISIBLE);
//                            stonetext.setText("돌 서비스 : "+htitle);
//                            tv_mdiv.setText(memberdivision+"  회원");
//                        }else if (classification.equals("구름-교육")){
//                            lincloud.setVisibility(View.VISIBLE);
//                            cloudtext.setText("구름 서비스 : "+htitle);
//                            tv_mdiv.setText(memberdivision+"  회원");
//                        }else if (classification.equals("학-여가")){
//                            lincrane.setVisibility(View.VISIBLE);
//                            cranetext.setText("학 서비스 : "+htitle);
//                            tv_mdiv.setText(memberdivision+"  회원");
//                        }else if (classification.equals("불로초-식사")){
//                            linblocho.setVisibility(View.VISIBLE);
//                            blochotext.setText("불로초 서비스 : "+htitle);
//                            tv_mdiv.setText(memberdivision+"  회원");
//                        }else if (classification.equals("거북-건강")){
//                            linturtle.setVisibility(View.VISIBLE);
//                            turtletext.setText("거북 서비스 : "+htitle);
//                            tv_mdiv.setText(memberdivision+"  회원");
//                        }else if (classification.equals("소나무-일상생활")){
//                            linpinetree.setVisibility(View.VISIBLE);
//                            pinetreetext.setText("소나무 서비스 : "+htitle);
//                            tv_mdiv.setText(memberdivision+"  회원");
//                        }else if (classification.equals("사슴-미용")){
//                            lindeer.setVisibility(View.VISIBLE);
//                            deertext.setText("사슴 서비스 : "+htitle);
//                            tv_mdiv.setText(memberdivision+"  회원");
//                        }
//                    }
//                });
//

            }

            con.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());

        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNonAdapter.setItems(nonList);
                nonRecycler.setAdapter(mNonAdapter);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_action, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(Non_Payment_Item.this, MenuMain.class);
                intent.putExtra("name", name);
                intent.putExtra("gender", gender);
                intent.putExtra("rating", rating);
                intent.putExtra("birth", birth);
                intent.putExtra("pastdisease", pastdisease);
                intent.putExtra("responsibility", responsibility);
                startActivity(intent);
                break;
            case R.id.action_notice:
                Intent intent1 = new Intent(Non_Payment_Item.this, CustomerServiceActivity.class);
                intent1.putExtra("name", name);
                intent1.putExtra("responsibility", responsibility);
                intent1.putExtra("rating", rating);
                startActivity(intent1);
                break;
            case R.id.action_serviceEdit:
                Intent i5 = new Intent(Non_Payment_Item.this, EditRecipientActivity.class);
                i5.putExtra("name", name);
                i5.putExtra("rating", rating);
                i5.putExtra("responsibility", responsibility);
                startActivity(i5);
                break;
            case R.id.action_sign:
                Intent i8 = new Intent(Non_Payment_Item.this, signActivity.class);
                i8.putExtra("name", name);
                i8.putExtra("rating", rating);
                i8.putExtra("responsibility", responsibility);
                startActivity(i8);
                break;
            case R.id.action_cal:
                final Calendar cal = Calendar.getInstance();
                Log.e(TAG, cal.get(Calendar.YEAR) + "");
                Log.e(TAG, cal.get(Calendar.MONTH) + 1 + "");
                Log.e(TAG, cal.get(Calendar.DATE) + "");
                Log.e(TAG, cal.get(Calendar.HOUR_OF_DAY) + "");
                Log.e(TAG, cal.get(Calendar.MINUTE) + "");
                DatePickerDialog dialog = new DatePickerDialog(Non_Payment_Item.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        date1 = String.format("%d-%d-%d", year, month + 1, date);
                        date2 = date1;
                        cTask = new CalSyncTask().execute();
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dialog.show();

                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public class CalSyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;
            calQuery();
            return null;

        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();
        }

    }


    public void calQuery() {

        Connection conn = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = conn.createStatement();
            ResultSet calres = statement.executeQuery("select * from Su_요양사일정관리 where 직원명 ='" + responsibility + "' and 일자 ='" + date2 + "';");

            while (calres.next()) {

                schedule_date = calres.getString("일자");//일자
                scheduletime = calres.getString("근무시간");//근무시간
                contracttime = calres.getString("계약시간"); //계약시간
                schedulename = calres.getString("수급자명");//계약수급자명
                division = calres.getString("구분");//구분

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    if (schedule_date != null) {

                        divisiontotal = "어르신 : " + schedulename + "  " + "일정 : " + division;
                        divisiondate = schedule_date + "일";
                        divisiontime = scheduletime + "(" + contracttime + ")";

                        Schedule_dialog schedule_dialog = new Schedule_dialog(Non_Payment_Item.this);

                        schedule_dialog.callFunction(divisiondate, divisiontime, divisiontotal);

                    } else if (schedule_date == null) {
                        Toast.makeText(Non_Payment_Item.this, "선택하신 날짜에 일정이 없습니다", Toast.LENGTH_SHORT).show();

                    }


                }
            });

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
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
}
