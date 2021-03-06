package com.jubumam.surefin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jubumam.surefin.ProtectorPackage.Protector;

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
    private String schedule_date;//??????
    private String scheduletime;//????????????
    private String contracttime; //????????????
    private String schedulename;//??????????????????
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
    private String recipiId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        activateToolbar();
        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        recipiId = commuteRecipient.getRecipiId();
        name = commuteRecipient.getName();
        rating = commuteRecipient.getRating();
        commute = commuteRecipient.getCommute();

        Login login = Login.getInstance();
        responsibility = login.getResponsibility();
        String department = login.getDepartment();



        if(commute==null && department ==null) {
            Protector protector = Protector.getInstance();
            recipiId = protector.getRecipiId();
            name = protector.getRecipientName();
            rating = protector.getRating();
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
        tv_name.setText(name + "???");

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
        final SimpleDateFormat thisMonthFormater = new SimpleDateFormat("MM???");
        thisMonth = thisMonthFormater.format(date);
        strDate = strDateFormater.format(date);
        sdf = calformater.format(date);
        tv_month.setText(sdf);
        String todayMonth = tv_month.getText().toString();
        thisYear = todayMonth.substring(0, 4);

        startMon = strDate + "-" + "01";
        endMon = strDate + "-" + "32";


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

    ////////////////////////////////????????? ????????????  ????????? ??????
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

            ResultSet recipientRS = statement.executeQuery("select ???????????? from Su_????????????????????? where ???????????????='" + recipiId + "'");
            while (recipientRS.next()) {
                recipientBasicTime = recipientRS.getString("????????????");
            }

            ResultSet yearRS = statement.executeQuery("select ?????? from Su_????????????????????? where ??????='" + thisYear + "' and ????????????='" + recipientBasicTime + "'");
            while (yearRS.next()) {
                String yearsupport = yearRS.getString("??????");
                intyearsupport = Integer.parseInt(yearsupport);
            }

            ResultSet bathPriceRS = statement.executeQuery("select A.????????????,B.?????? from Su_?????????????????? as A LEFT JOIN Su_????????????????????? as B ON A.????????????=B.???????????? where A.???????????????='" + recipiId + "' AND (A.?????? BETWEEN '" + startMon + "' AND '" + endMon + "') AND B.??????='" + thisYear + "' ");
            while (bathPriceRS.next()) {
                count = bathPriceRS.getString("????????????");
                String yearBathSupport = bathPriceRS.getString("??????");
                int indiviYearBathSupport = (int) Math.round(Integer.parseInt(yearBathSupport) * 0.15);
                int publicYearBathSupport = Integer.parseInt(yearBathSupport) - indiviYearBathSupport;

                intTotalDayCareIndivi = (int) (intTotalDayCareIndivi + indiviYearBathSupport);
                intTotalDayCarePublic = (int) (intTotalDayCarePublic + publicYearBathSupport);

            }

            ResultSet nursingPriceRS = statement.executeQuery("select A.????????????,B.?????? from Su_?????????????????? as A left join Su_????????????????????? as B on B.????????????=A.??????????????? where B.??????='" + thisYear + "' AND (A.?????? BETWEEN '" + startMon + "' AND '" + endMon + "') AND A.???????????????='" + recipiId + "'");
            while (nursingPriceRS.next()) {

                String yearNursingSupport = nursingPriceRS.getString("??????");
                int indiviYearNursingSupport = (int) Math.round(Integer.parseInt(yearNursingSupport) * 0.15);
                int publicYearNursingSupport = Integer.parseInt(yearNursingSupport) - indiviYearNursingSupport;

                intTotalDayCareIndivi = intTotalDayCareIndivi + indiviYearNursingSupport;
                intTotalDayCarePublic = intTotalDayCarePublic + publicYearNursingSupport;


            }

            ResultSet serviceResultSetlist = statement.executeQuery("select COALESCE(A.????????????,B.????????????,C.????????????,D.????????????) AS ???????????????,COALESCE(A.??????,B.??????,C.??????,D.?????????????????????) AS ????????????,B.????????????,(CONVERT(int,A.?????????????????????))+(CONVERT(int,A.?????????????????????))+(CONVERT(int,A.?????????????????????))+(CONVERT(int,A.?????????????????????))+(CONVERT(int,A.???????????????????????????)) AS ??????????????????," +
                    "B.???????????? ,C.????????????,C.?????????,D.???????????????,D.????????????????????? from Su_???????????????????????? A FULL OUTER JOIN Su_?????????????????? B ON (A.??????=B.?????? AND A.????????????=B.???????????? AND A.??????=B.??????) " +
                    "FULL OUTER JOIN Su_?????????????????? C ON (B.??????=C.?????? AND B.????????????=C.???????????? AND  B.??????=C.??????) or (A.??????=C.?????? AND A.????????????=C.???????????? AND  A.??????=C.??????)" +
                    "FULL OUTER JOIN Su_?????????????????? D ON (D.?????????????????????=A.?????? AND D.????????????=A.???????????? AND D.??????=A.??????) or (D.?????????????????????=B.?????? AND D.????????????=B.???????????? AND D.??????=B.??????) or (D.?????????????????????=C.?????? AND D.????????????=C.???????????? AND D.??????=C.??????)" +
                    "where ((A.?????? BETWEEN '" + startMon + "' AND '" + endMon + "') or (B.?????? BETWEEN '" + startMon + "' AND '" + endMon + "') or (C.?????? BETWEEN '" + startMon + "' AND '" + endMon + "') or (D.????????????????????? BETWEEN '" + startMon + "' AND '" + endMon + "')) AND (A.???????????????='" + recipiId + "' or B.???????????????='" + recipiId + "' or C.???????????????='" + recipiId + "'or D.???????????????='" + recipiId + "')" +
                    "order by ????????????,A.??????");

            serviceList = new ArrayList<>();

            timeformatter = new SimpleDateFormat("mm");


            while (serviceResultSetlist.next()) {
                date = serviceResultSetlist.getString("????????????");
                usingTime1 = serviceResultSetlist.getString("??????????????????");
                nursingCount = serviceResultSetlist.getString("????????????");
                nursingTotal = serviceResultSetlist.getString("?????????");
                carService = serviceResultSetlist.getString("????????????");
                bath = serviceResultSetlist.getString("????????????");
                nonPay = serviceResultSetlist.getString("???????????????");


                if (usingTime1 == null) {
                    usingTime1 = "0";
                    sumUsingTimeDay = 0 + sumUsingTimeDay;
                } else {
                    Date d1 = utctime.parse(usingTime1);
                    sumUsingTimeDay = d1.getTime() + sumUsingTimeDay;
                }

                if (nonPay != null && nonPay.equals("??????")) {
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

                //???????????? ??? ?????? ??????
                int totalhour = (int) sumUsingTimeMonth / 60000;
                if (totalhour != 0) {
                    thour = totalhour / 60;
                } else {
                    totalhour = 0;
                }
                tmin = totalhour % 60;
                strThour = String.format("%02d", thour);
                strTmin = String.format("%02d", tmin);
                tv_sumTime.setText(strThour + "??????" + strTmin + "???");

                //???????????? ??? ?????? ??????
                tv_bathCount.setText(bathCount + "");

                //???????????? ??? ??????????????? ??????
                tv_nursingCountTT.setText(intNursingCount + "???");
                int nhour;
                if (intNursingTotal != 0) {
                    nhour = intNursingTotal / 60;
                } else {
                    nhour = 0;
                }
                int nmin = intNursingTotal % 60;
                strNhour = String.format("%02d", nhour);
                strNmin = String.format("%02d", nmin);
                tv_nursingTotalTT.setText(strNhour + "??????" + strNmin + "???");


                //???????????? ?????? ??? ???????????? ??????
                tv_nonPayServiceCount.setText(intnonPay + "");
                DecimalFormat myFormatter = new DecimalFormat("###,###");
                tv_indiviPrice.setText(myFormatter.format(intTotalDayCareIndivi) + "???");
                tv_publicPrice.setText(myFormatter.format(intTotalDayCarePublic) + "???");

                if (intTotalDayCareIndivi == 0 && intTotalDayCarePublic == 0) {
                    recyclerView.setVisibility(View.GONE);
                    tv_noList.setVisibility(View.VISIBLE);
                } else {
                    tv_noList.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                }


            }
        });

    }

////////////////////////////////????????? ????????????  ????????? ???

}

