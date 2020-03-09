package com.jubumam.SureFin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jubumam.SureFin.NokPackage.Nok;

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

public class ServiceListActivity extends BaseActivity {
//    private NumberPicker picker1;
//    private NumberPicker picker2;
//    private String newString;
//    private String ratingPrice;
//    private String noCarService;
//    private String strNursingTime;
//    private String commute;
//    private String usingTime2;
//    private String usingTime3;
//    private String usingTime4;
//    private String usingTime5;
//    private String price;
//    private int sumPrice = 0;
//    private int serviceId;
//    private TextView tv_month2;
//    private Recipient recipient;


    private TextView tv_sumTime;
    private TextView tv_month;
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
    private TextView tv_noList;
    private ImageView img_left;
    private ImageView img_right;

    private AsyncTask<String, String, String> mTask;
    private AsyncTask<String, String, String> cTask;

    private SimpleDateFormat utctime;
    private SimpleDateFormat timeformatter;
    private Calendar cal;
    private List<Service> serviceList;
    private ServiceAdapter mServiceAdapter;
    private RecyclerView recyclerView;

    private String name;
    private String date;
    private String responsibility;
    private String commute;
    private String usingTime1;
    private String nursingCount;
    private String nursingTotal;
    private String rating;
    private String count;
    private String strDayCareIndivi;
    private String strDayCarePublic;
    private String recipientBasicTime;
    private String carService;
    private String startMon;
    private String endMon;
    private String strDate;
    private String thisMonth;
    private String sdf;
    private String thisYear;
    private String bath;
    private String strcount;
    private String nonPay;
    private String dateDay;
    private String schedule_date;//일자
    private String scheduletime;//근무시간
    private String contracttime; //계약시간
    private String schedulename;//계약수급자명
    private String divisiontotal;
    private String division;
    private String date2;
    private String date1;
    private String TAG = "PickerActivity";
    private String divisiondate;
    private String divisiontime;
    private String strThour;
    private String strTmin;
    private String strNhour;
    private String strNmin;
    private int intDayCareIndivi;
    private int intDayCarePublic;
    private int intnonPay = 0;
    private int bathcount = 0;
    private int thour = 0;
    private int tmin;
    private int nonPayServiceCount = 0;
    private int intNursingPrice = 0;
    private int intyearsupport;
    private int bathCount = 0;
    private int intNursingCount = 0;
    private int intTotalDayCareIndivi = 0;
    private int intTotalDayCarePublic = 0;
    private int intNursingTotal = 0;
    private long sumUsingTimeDay = 0;
    private long sumUsingTimeMonth = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        activateToolbar();
        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        name = commuteRecipient.getName();
        rating = commuteRecipient.getRating();
        responsibility = commuteRecipient.getResponsibility();
        commute = commuteRecipient.getCommute();

        Login login = Login.getInstance();
        responsibility = login.getResponsibility();
        String responsibilityID = login.getPersonId();
        String department = login.getDepartment();

        if(commute==null && department ==null) {
            Nok nok = Nok.getInstance();
            name = nok.getRecipientName();
            rating = nok.getRating();
        }else if(commute==null && department !=null){
            findViewById(R.id.lin_recipi).setVisibility(View.GONE);
        }



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
        tv_noList = findViewById(R.id.tv_noList);
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
                intnonPay = 0;
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
                intnonPay = 0;
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

    ////////////////////////////////서비스 사용내역  리스트 시작
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
            Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();

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

            }

            ResultSet nursingPriceRS = statement.executeQuery("select A.방문횟수,B.금액 from Su_방문간호정보 as A left join Su_년도별금액 as B on B.상세구분=A.총시간이름 where B.년도='" + thisYear + "' AND (A.일자 BETWEEN '" + startMon + "' AND '" + endMon + "') AND A.수급자명='" + name + "'");
            while (nursingPriceRS.next()) {

                String yearNursingSupport = nursingPriceRS.getString("금액");
                int indiviYearNursingSupport = (int) Math.round(Integer.parseInt(yearNursingSupport) * 0.15);
                int publicYearNursingSupport = Integer.parseInt(yearNursingSupport) - indiviYearNursingSupport;

                intTotalDayCareIndivi = intTotalDayCareIndivi + indiviYearNursingSupport;
                intTotalDayCarePublic = intTotalDayCarePublic + publicYearNursingSupport;


            }

            ResultSet serviceResultSetlist = statement.executeQuery("select COALESCE(A.수급자명,B.수급자명,C.수급자명,D.수급자명) AS 요양수급자,COALESCE(A.일자,B.일자,C.일자,D.서비스제공일자) AS 요양일자,B.목욕여부,(CONVERT(int,A.신체사용시간계))+(CONVERT(int,A.인지사용시간계))+(CONVERT(int,A.일상생활시간계))+(CONVERT(int,A.정서사용시간계))+(CONVERT(int,A.생활지원사용시간계)) AS 방문요양합계," +
                    "B.차량이용 ,C.방문횟수,C.총시간,D.서비스제공,D.서비스제공일자 from Su_방문요양급여정보 A FULL OUTER JOIN Su_방문목욕정보 B ON (A.일자=B.일자 AND A.수급자명=B.수급자명 AND A.번호=B.번호) " +
                    "FULL OUTER JOIN Su_방문간호정보 C ON (B.일자=C.일자 AND B.수급자명=C.수급자명 AND  B.번호=C.번호) or (A.일자=C.일자 AND A.수급자명=C.수급자명 AND  A.번호=C.번호)" +
                    "FULL OUTER JOIN Su_비급여신청자 D ON (D.서비스제공일자=A.일자 AND D.수급자명=A.수급자명 AND D.번호=A.번호) or (D.서비스제공일자=B.일자 AND D.수급자명=B.수급자명 AND D.번호=B.번호) or (D.서비스제공일자=C.일자 AND D.수급자명=C.수급자명 AND D.번호=C.번호)" +
                    "where ((A.일자 BETWEEN '" + startMon + "' AND '" + endMon + "') or (B.일자 BETWEEN '" + startMon + "' AND '" + endMon + "') or (C.일자 BETWEEN '" + startMon + "' AND '" + endMon + "') or (D.서비스제공일자 BETWEEN '" + startMon + "' AND '" + endMon + "')) AND (A.수급자명='" + name + "' or B.수급자명='" + name + "' or C.수급자명='" + name + "'or D.수급자명='" + name + "')" +
                    "order by 요양일자,A.번호");

            serviceList = new ArrayList<>();

            timeformatter = new SimpleDateFormat("mm");


            while (serviceResultSetlist.next()) {
                date = serviceResultSetlist.getString("요양일자");
                usingTime1 = serviceResultSetlist.getString("방문요양합계");
                nursingCount = serviceResultSetlist.getString("방문횟수");
                nursingTotal = serviceResultSetlist.getString("총시간");
                carService = serviceResultSetlist.getString("차량이용");
                bath = serviceResultSetlist.getString("목욕여부");
                nonPay = serviceResultSetlist.getString("서비스제공");


                if (usingTime1 == null || usingTime1.equals("") || usingTime1.equals("null")) {
                    usingTime1 = "0";
                    sumUsingTimeDay = 0 + sumUsingTimeDay;
                } else {
                    Date d1 = utctime.parse(usingTime1);
                    sumUsingTimeDay = d1.getTime() + sumUsingTimeDay;
                }

                if (nonPay != null && nonPay.equals("제공")) {
                    intnonPay++;
                }
                if (bath != null && bath.equals("TRUE")) {
                    bathCount++;
                }

                if (nursingTotal == null || nursingTotal.equals("")) {
                    intNursingTotal = 0 + intNursingTotal;
                    nursingTotal = "0";
                } else {
                    int nursing = Integer.parseInt(nursingTotal);
                    intNursingTotal = nursing + intNursingTotal;
                    intNursingCount++;
                }

                if (date != null) {
                    Date dDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                    dateDay = new SimpleDateFormat("dd(EE)", Locale.KOREAN).format(dDate);
                }
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

                serviceList.add(new Service(name, dateDay, sumUsingTimeDay, bath, nursingCount, nursingTotal, nonPay));


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
                mServiceAdapter.setItems(serviceList);
                recyclerView.setAdapter(mServiceAdapter);

                //방문요양 총 사용 시간
                int totalhour = (int) sumUsingTimeMonth / 60000;
                if (totalhour != 0) {
                    thour = totalhour / 60;
                } else {
                    totalhour = 0;
                }
                tmin = totalhour % 60;
                strThour = String.format("%02d", thour);
                strTmin = String.format("%02d", tmin);
                tv_sumTime.setText(strThour + "시간" + strTmin + "분");

                //방문목욕 총 사용 횟수
                tv_bathCount.setText(bathCount + "");

                //방문간호 총 사용시간및 횟수
                tv_nursingCountTT.setText(intNursingCount + "회");
                int nhour;
                if (intNursingTotal != 0) {
                    nhour = intNursingTotal / 60;
                } else {
                    nhour = 0;
                }
                int nmin = intNursingTotal % 60;
                strNhour = String.format("%02d", nhour);
                strNmin = String.format("%02d", nmin);
                tv_nursingTotalTT.setText(strNhour + "시간" + strNmin + "분");


                //개인부담 비용 및 공단부담 비용
                tv_nonPayServiceCount.setText(intnonPay + "");
                DecimalFormat myFormatter = new DecimalFormat("###,###");
                tv_indiviPrice.setText(myFormatter.format(intTotalDayCareIndivi) + "원");
                tv_publicPrice.setText(myFormatter.format(intTotalDayCarePublic) + "원");

                if (intTotalDayCareIndivi == 0 && intTotalDayCarePublic == 0) {
                    tv_noList.setVisibility(View.VISIBLE);
                } else {
                    tv_noList.setVisibility(View.GONE);

                }


            }
        });

    }

////////////////////////////////서비스 사용내역  리스트 끝

}

