package com.jubumam.surefin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ServiceListActivity extends AppCompatActivity {
    private TextView tv_sumTime;
    private TextView tv_month;
    private TextView tv_month2;
    private TextView tv_bathCount;
    private TextView tv_nursingCountTT;
    private TextView tv_nursingTotalTT;
    private TextView tv_indiviPrice;
    private TextView tv_publicPrice;
    private TextView tv_nonPayServiceCount;
    private TextView tv_thisMonth1;
    private TextView tv_thisMonth2;
    private TextView tv_rating1;
    private TextView tv_name;

    private ImageView img_left;
    private ImageView img_right;

    private String name;
    private String date;
    private String responsibility;
    private String usingTime1;
    private String usingTime2;
    private String usingTime3;
    private String usingTime4;
    private String usingTime5;
    private String nursingCount;
    private String nursingTotal;
    private String rating;
    private long sumUsingTimeDay = 0;
    private long sumUsingTimeMonth = 0;

    private String price;
    private int sumPrice = 0;
    private String count;
    private int bathCount = 0;
    private String strDayCareIndivi;
    private String strDayCarePublic;
    private int intTotalDayCareIndivi = 0;
    private int intTotalDayCarePublic = 0;
    private int serviceId;
    private int intNursingCount = 0;
    private int intNursingTotal = 0;

    private List<Service> serviceList;
    private ServiceAdapter mServiceAdapter;
    private RecyclerView recyclerView;
    private Recipient recipient;
    private AsyncTask<String, String, String> mTask;
    private NumberPicker picker1;
    private NumberPicker picker2;
    private String startMon;
    private String endMon;
    private SimpleDateFormat timeformatter;
    private String newString;
    private int bathcount = 0;
    private String strDate;
    private String ratingPrice;
    private int nonPayServiceCount = 0;
    private String thisMonth;
    private String sdf;
    private int intNursingPrice = 0;
    private int intyearsupport;
    private String thisYear;
    private String recipientBasicTime;
    private String carService;
    private String noCarService;
    private String strNursingTime;
    private int intDayCareIndivi;
    private int intDayCarePublic;
    private String careDayTime;

    private SimpleDateFormat utctime;
    private Calendar cal;
    private int thour = 0;
    private String bath;
    private String strcount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
// Get the ActionBar here to configure the way it behaves.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        rating = intent.getExtras().getString("rating");
        responsibility = intent.getExtras().getString("responsibility");
//        responsibility = "김철수";
//        name = "홍길동";
//        rating = "1등급";


        utctime = new SimpleDateFormat("mm", Locale.KOREA);
        utctime.setTimeZone(TimeZone.getTimeZone("UTC"));

        recyclerView = findViewById(R.id.recyclerview_servicelist);
        mServiceAdapter = new ServiceAdapter();
        tv_sumTime = findViewById(R.id.tv_sumTime);
        tv_month = findViewById(R.id.tv_month);
        tv_bathCount = findViewById(R.id.tv_bathCount);
        tv_nursingCountTT = findViewById(R.id.tv_nursingCountTT);
        tv_nursingTotalTT = findViewById(R.id.tv_nursingTotalTT);
        tv_indiviPrice = findViewById(R.id.tv_indiviPrice);
        tv_publicPrice = findViewById(R.id.tv_publicPrice);
        tv_nonPayServiceCount = findViewById(R.id.tv_nonPayServiceCount);
        tv_thisMonth1 = findViewById(R.id.tv_thisMonth1);
        tv_thisMonth2 = findViewById(R.id.tv_thisMonth2);
        tv_rating1 = findViewById(R.id.tv_rating1);
        tv_name = findViewById(R.id.tv_name);
        img_left = findViewById(R.id.img_left);
        img_right = findViewById(R.id.img_right);
        tv_rating1.setText(rating);
        tv_name.setText(name + "님");

        tv_nonPayServiceCount = findViewById(R.id.tv_nonPayServiceCount);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Date date = new Date();
        final SimpleDateFormat calformater = new SimpleDateFormat("yyyy.MM");
        final SimpleDateFormat strDateFormater = new SimpleDateFormat("yyyy-MM");
        final SimpleDateFormat thisMonthFormater = new SimpleDateFormat("MM월");
        thisMonth = thisMonthFormater.format(date);
        strDate = strDateFormater.format(date);
        sdf = calformater.format(date);
        String todayMonth = tv_month.getText().toString();
        thisYear = todayMonth.substring(0, 4);

        startMon = strDate + "-" + "01";
        endMon = strDate + "-" + "32";

        tv_month.setText(sdf);
        tv_thisMonth1.setText(thisMonth);
        tv_thisMonth2.setText(thisMonth);
        cal = Calendar.getInstance();
        cal.setTime(date);
        mTask = new ServiceSyncTask().execute();


        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumUsingTimeMonth = 0;
                thour = 0;
                nonPayServiceCount = 0;
                bathCount = 0;
                intNursingCount = 0;
                intNursingTotal = 0;
                intTotalDayCareIndivi = 0;
                intTotalDayCarePublic = 0;
                cal.add(Calendar.MONTH, -1);
                String preMonthYear = tv_month.getText().toString();
                sdf = calformater.format(cal.getTime());
                strDate = strDateFormater.format(cal.getTime());
                thisMonth = thisMonthFormater.format(cal.getTime());
                thisYear = preMonthYear.substring(0, 4);
                tv_month.setText(sdf);
                tv_thisMonth1.setText(thisMonth);
                tv_thisMonth2.setText(thisMonth);
                startMon = strDate + "-" + "01";
                endMon = strDate + "-" + "32";
                mTask = new ServiceSyncTask().execute();
            }
        });

        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumUsingTimeMonth = 0;
                thour = 0;
                nonPayServiceCount = 0;
                bathCount = 0;
                intNursingCount = 0;
                intNursingTotal = 0;
                intTotalDayCareIndivi = 0;
                intTotalDayCarePublic = 0;
                cal.add(Calendar.MONTH, +1);
                String preMonthYear = tv_month.getText().toString();
                sdf = calformater.format(cal.getTime());
                strDate = strDateFormater.format(cal.getTime());
                thisMonth = thisMonthFormater.format(cal.getTime());
                thisYear = preMonthYear.substring(0, 4);
                tv_month.setText(sdf);
                tv_thisMonth1.setText(thisMonth);
                tv_thisMonth2.setText(thisMonth);
                startMon = strDate + "-" + "01";
                endMon = strDate + "-" + "32";
                mTask = new ServiceSyncTask().execute();
            }
        });


    }

    public class ServiceSyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled()) {
                return null;
            }
            servicelistQuery();
            return null;
        }
    }

    public void servicelistQuery() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();
//
//            ResultSet resultRatingPay = statement.executeQuery("select * from Su_년도별금액 where 상세구분='" + rating + "'");
//
//            while(resultRatingPay.next()){
//                ratingPrice = resultRatingPay.getString("금액");
//            }
//            ResultSet serviceResultSetlist = statement.executeQuery("select A.*,B.목욕여부,B.날짜 from Su_방문요양급여정보 A FULL OUTER JOIN Su_방문목욕정보 B ON A.일자=B.날짜 where (A.수급자명='"+name+"' AND B.수급자명='"+name+"') AND (A.일자 BETWEEN '2019-12-01' AND '2019-12-32') ");
            ResultSet rs = statement.executeQuery("select * from Su_비급여신청자 where 수급자명 = '" + name + "' AND (일자 BETWEEN '" + startMon + "' AND '" + endMon + "')");
            while (rs.next()) {
                nonPayServiceCount++;
            }

            ResultSet recipientRS = statement.executeQuery("select 기본시간 from Su_수급자기본정보 where 수급자명='" + name + "'");
            while (recipientRS.next()) {
                recipientBasicTime = recipientRS.getString("기본시간");
            }

            ResultSet yearRS = statement.executeQuery("select 금액 from Su_년도별금액 where 년도='" + thisYear + "' and 상세구분='" + recipientBasicTime + "'");
            while (yearRS.next()) {
                String yearsupport = yearRS.getString("금액");
                intyearsupport = Integer.parseInt(yearsupport);
            }

            ResultSet bathPriceRS = statement.executeQuery("select A.목욕여부,B.금액 from Su_방문목욕정보 as A LEFT JOIN Su_년도별금액 as B ON A.차량이용=B.상세구분 where A.수급자명='" + name + "' AND (A.일자 BETWEEN '" + startMon + "' AND '" + endMon + "') AND B.년도='" + thisYear + "' ");
            while (bathPriceRS.next()) {
                count = bathPriceRS.getString("목욕여부");
                String yearBathSupport = bathPriceRS.getString("금액");
                int indiviYearBathSupport = (int) Math.round(Integer.parseInt(yearBathSupport) * 0.15);
                int publicYearBathSupport = Integer.parseInt(yearBathSupport) - indiviYearBathSupport;

                intTotalDayCareIndivi = (int) (intTotalDayCareIndivi + indiviYearBathSupport);
                intTotalDayCarePublic = (int) (intTotalDayCarePublic + publicYearBathSupport);

                if (count != null && count.equals("TRUE")) {
                    bathCount++;
                }
            }

            ResultSet nursingPriceRS = statement.executeQuery("select A.방문횟수,B.금액 from Su_방문간호정보 as A left join Su_년도별금액 as B on B.상세구분=A.총시간이름 where B.년도='" + thisYear + "' AND (A.일자 BETWEEN '" + startMon + "' AND '" + endMon + "') AND A.수급자명='" + name + "'");
            while (nursingPriceRS.next()) {
                nursingCount = nursingPriceRS.getString("방문횟수");
                String yearNursingSupport = nursingPriceRS.getString("금액");
                int indiviYearNursingSupport = (int) Math.round(Integer.parseInt(yearNursingSupport) * 0.15);
                int publicYearNursingSupport = Integer.parseInt(yearNursingSupport) - indiviYearNursingSupport;

                intTotalDayCareIndivi = intTotalDayCareIndivi + indiviYearNursingSupport;
                intTotalDayCarePublic = intTotalDayCarePublic + publicYearNursingSupport;

                if (nursingCount != null && nursingCount.equals("1")) {
                    intNursingCount++;
                }

            }

            ResultSet serviceResultSetlist = statement.executeQuery("select A.수급자명 AS 요양수급자,COALESCE(A.일자,B.일자,C.일자) AS 요양일자,A.신체사용시간계,A.인지사용시간계,A.일상생활시간계,A.정서사용시간계,A.생활지원사용시간계,B.목욕여부,B.차량이용 ,C.방문횟수,C.총시간 from Su_방문요양급여정보 A FULL OUTER JOIN Su_방문목욕정보 B ON (A.일자=B.일자 AND A.수급자명=B.수급자명)" +
                    "FULL OUTER JOIN Su_방문간호정보 C ON (B.일자=C.일자 AND B.수급자명=C.수급자명) or (A.일자=C.일자 AND A.수급자명=C.수급자명)" +
                    "where ((A.일자 BETWEEN '" + startMon + "' AND '" + endMon + "') or (B.일자 BETWEEN '" + startMon + "' AND '" + endMon + "') or (C.일자 BETWEEN '" + startMon + "' AND '" + endMon + "')) AND (A.수급자명='" + name + "' or B.수급자명='" + name + "' or C.수급자명='" + name + "')" +
                    "order by 요양일자");

//            ResultSet serviceResultSetlist = statement.executeQuery("select A.일자 AS 요양일자,B.일자 AS 목욕일자,신체사용시간계,인지사용시간계,일상생활시간계,정서사용시간계,생활지원사용시간계,목욕여부 from Su_방문요양급여정보 AS A JOIN Su_방문목욕정보 AS B " +
//                    "ON A.일자=B.일자 WHERE A.수급자명='"+name+"' AND B.수급자명='"+name+"' ORDER BY A.일자,B.일자" );

//            ResultSet serviceResultSetlist = statement.executeQuery("select A.수급자명 AS 요양수급자,A.일자 AS 요양일자,B.일자 AS 목욕일자,C.일자 AS 간호일자,A.신체사용시간계,A.인지사용시간계,A.일상생활시간계,A.정서사용시간계,A.생활지원사용시간계,B.목욕여부,B.차량이용 ,C.방문횟수,C.총시간,isnull(sum(convert(int,A.신체사용시간계)),'')+isnull(sum(convert(int,A.인지사용시간계)),'')+isnull(sum(convert(int,A.일상생활시간계)),'')+isnull(sum(convert(int,A.정서사용시간계)),'')+isnull(sum(convert(int,A.생활지원사용시간계)),'') AS 요양시간계 from Su_방문요양급여정보 as A FULL OUTER JOIN Su_방문목욕정보 as B ON (A.일자=B.일자 AND A.수급자명=B.수급자명)" +
//                    "FULL OUTER JOIN Su_방문간호정보 as C ON (B.일자=C.일자 AND B.수급자명=C.수급자명) or (A.일자=C.일자 AND A.수급자명=C.수급자명)" +
//                    "where ((A.일자 BETWEEN '2019-12-01' AND '2019-12-31') or (B.일자 BETWEEN '2019-12-01' AND '2019-12-31') or (C.일자 BETWEEN '2019-12-01' AND '2019-12-31')) AND (A.수급자명='홍길동' or B.수급자명='홍길동' or C.수급자명='홍길동') GROUP BY A.수급자명,A.일자, B.일자,C.일자,A.신체사용시간계,A.인지사용시간계,A.일상생활시간계,A.정서사용시간계,A.생활지원사용시간계,B.목욕여부,B.차량이용,C.방문횟수,C.총시간" +
//                    "order by 요양일자,목욕일자,간호일자");


            serviceList = new ArrayList<>();

            timeformatter = new SimpleDateFormat("mm");


            while (serviceResultSetlist.next()) {
                date = serviceResultSetlist.getString("요양일자");
                usingTime1 = serviceResultSetlist.getString("신체사용시간계");
                usingTime2 = serviceResultSetlist.getString("인지사용시간계");
                usingTime3 = serviceResultSetlist.getString("일상생활시간계");
                usingTime4 = serviceResultSetlist.getString("정서사용시간계");
                usingTime5 = serviceResultSetlist.getString("생활지원사용시간계");
//                careDayTime = serviceResultSetlist.getString("요양시간계");
                nursingCount = serviceResultSetlist.getString("방문횟수");
                nursingTotal = serviceResultSetlist.getString("총시간");
                carService = serviceResultSetlist.getString("차량이용");
                bath = serviceResultSetlist.getString("목욕여부");


                if (usingTime1 == null || usingTime1.equals("") || usingTime1.equals("null")) {
                    usingTime1 = "0";
                    sumUsingTimeDay = 0 + sumUsingTimeDay;
                } else {
                    Date d1 = utctime.parse(usingTime1);
                    sumUsingTimeDay = d1.getTime() + sumUsingTimeDay;
                }
                if (usingTime2 == null || usingTime2.equals("") || usingTime2.equals("null")) {
                    usingTime2 = "0";
                    sumUsingTimeDay = 0 + sumUsingTimeDay;
                } else {
                    Date d2 = utctime.parse(usingTime2);
                    sumUsingTimeDay = d2.getTime() + sumUsingTimeDay;
                }
                if (usingTime3 == null || usingTime3.equals("") || usingTime3.equals("null")) {
                    usingTime3 = "0";
                    sumUsingTimeDay = 0 + sumUsingTimeDay;
                } else {
                    Date d3 = utctime.parse(usingTime3);
                    sumUsingTimeDay = d3.getTime() + sumUsingTimeDay;
                }
                if (usingTime4 == null || usingTime4.equals("") || usingTime4.equals("null")) {
                    usingTime4 = "0";
                    sumUsingTimeDay = 0 + sumUsingTimeDay;
                } else {
                    Date d4 = utctime.parse(usingTime4);
                    sumUsingTimeDay = d4.getTime() + sumUsingTimeDay;
                }
                if (usingTime5 == null || usingTime5.equals("") || usingTime5.equals("null")) {
                    usingTime5 = "0";
                    sumUsingTimeDay = 0 + sumUsingTimeDay;
                } else {
                    Date d5 = utctime.parse(usingTime5);
                    sumUsingTimeDay = d5.getTime() + sumUsingTimeDay;
                }

//                if(careDayTime != null){
//                    sumUsingTimeDay = sumUsingTimeMonth+Integer.parseInt(careDayTime);
//                }


                if (nursingTotal != null && !nursingTotal.equals("")) {
                    int nursing = Integer.parseInt(nursingTotal);
                    intNursingTotal = nursing + intNursingTotal;
                }

                Date dDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                String dateDay = new SimpleDateFormat("dd(EE)", Locale.KOREAN).format(dDate);
                strDayCareIndivi = timeformatter.format(sumUsingTimeDay);
                if (sumUsingTimeDay != 0) {
                    intDayCareIndivi = (int) Math.round(intyearsupport * 0.15);
                    intDayCarePublic = intyearsupport - intDayCareIndivi;
                    intTotalDayCareIndivi = intTotalDayCareIndivi + intDayCareIndivi;
                    intTotalDayCarePublic = intTotalDayCarePublic + intDayCarePublic;
                }
                intDayCareIndivi = 0;
                intDayCarePublic = 0;

                sumUsingTimeMonth = sumUsingTimeMonth + sumUsingTimeDay;

                serviceList.add(new Service(name, dateDay, sumUsingTimeDay, bath, nursingCount, nursingTotal));

                sumUsingTimeDay = 0;


            }

            connection.close();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

//


                mServiceAdapter.setItems(serviceList);
                recyclerView.setAdapter(mServiceAdapter);
                int totalhour = (int) sumUsingTimeMonth / 60000;

                if (totalhour != 0) {
                    thour = totalhour / 60;
                } else {
                    totalhour = 0;
                }
                int tmin = totalhour % 60;

                tv_sumTime.setText(thour + "시간" + tmin + "");
                //tv_sumTime.setText(sumUsingTimeMonth / 60000 + "");
                tv_bathCount.setText(bathCount + "");
                tv_nursingCountTT.setText(intNursingCount + "회");
                int nhour;
                if (intNursingTotal != 0) {
                    nhour = intNursingTotal / 60;
                } else {
                    nhour = 0;
                }
                int nmin = intNursingTotal % 60;
                tv_nursingTotalTT.setText(nhour + "시간" + nmin + "분");
                //tv_nursingTotalTT.setText(intNursingTotal + "분");
                tv_nonPayServiceCount.setText(nonPayServiceCount + "");
                DecimalFormat myFormatter = new DecimalFormat("###,###");
                tv_indiviPrice.setText(myFormatter.format(intTotalDayCareIndivi) + "원");
                tv_publicPrice.setText(myFormatter.format(intTotalDayCarePublic) + "원");


            }
        });

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
                Intent intent = new Intent(ServiceListActivity.this, MenuMain.class);
                intent.putExtra("name", name);
//                intent.putExtra("gender", gender);
                intent.putExtra("rating", rating);
//                intent.putExtra("birth", birth);
//                intent.putExtra("pastdisease", mhistory);
                intent.putExtra("responsibility", responsibility);
                startActivity(intent);
                return true;
            case R.id.action_notice:
                Intent intent1 = new Intent(ServiceListActivity.this, CustomerServiceActivity.class);
                intent1.putExtra("name", name);
                startActivity(intent1);
                break;
            case R.id.action_serviceEdit:
                Intent i5 = new Intent(ServiceListActivity.this, EditRecipientActivity.class);
                i5.putExtra("name", name);
                i5.putExtra("rating", rating);
                startActivity(i5);
                break;
            case R.id.action_sign:
                Intent i8 = new Intent(ServiceListActivity.this, signActivity.class);
                i8.putExtra("name", name);
                i8.putExtra("rating", rating);
                startActivity(i8);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

