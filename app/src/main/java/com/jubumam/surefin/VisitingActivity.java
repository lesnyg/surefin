package com.jubumam.surefin;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class VisitingActivity extends BaseActivity implements View.OnClickListener {
//    private int mNumber = 0;
//    private int mNumber1 = 0;
//    private int mNumber2 = 0;
//    private int mNumber3 = 0;
//    private Button btn_end1;
//    private Button btn_end2;
//    private Button btn_end3;
//    private String clockusingTime;
//    private String maintime;
//    private SimpleDateFormat formatterScreen;
//    private String strDate;
//    String pastdisease;
//    private int rmoney;//시간별 금액
//    private int totalmin;
//    private int remainingTime;
//    private float rmoney1;
//    private float totalmin1;
//    private Button btn_end1_1;
//    private int minhour;
//    private String date3;
//    private TextView tv_date;

    //퇴근에 필요한 필드
    private final static int TAKE_PICTURE = 1;
    private AsyncTask<String, String, String> tTask;

    private String ttime;
    private ImageView dialog_imageview;
    private byte[] imageBytes;
    private String s3, s4;
    private String ymd1, hms1;
    private String imageString;
    private int s2;


    private float vistime;
    private String stime1_1;
    private int batime; //기본시간
    private int tmoney; //등급별 총액
    private int mNumber4 = 0;
    private int mNumber5 = 0;
    private TextView tv_number;
    private TextView tv_number1;
    private TextView tv_number1_1;
    private TextView tv_number2;
    private TextView tv_number3;
    private TextView tv_number4;
    private TextView tv_number5;
    private TextView tv_startTime;
    private TextView tv_endTime;
    private Button btn_start;
    private Button btn_end;
    private Button btn_start1;
    private Button btn_start2;
    private Button btn_start3;
    private Button btn_start1_1;
    private Button btn_pic;
    private LinearLayout lin_recipi;

    private CheckBox ck_bodyactiv1;
    private CheckBox ck_bodyactiv2;
    private CheckBox ck_bodyactiv3;
    private CheckBox ck_bodyactiv4;
    private CheckBox ck_bodyactiv5;
    private CheckBox ck_bodyactiv6;
    private CheckBox ck_housework1;
    private CheckBox ck_housework2;

    private RadioGroup rg_body;
    private RadioGroup rg_meal;
    private RadioGroup rg_cognitive;

    private String body;
    private String currentDate;
    private String usingTime;
    private String usingTime1;
    private String usingTime1_1;
    private String usingTime2;
    private String usingTime3;
    private String startMon;
    private String endMon;
    private String totalUsingTime;
    private String dateCheck;

    private String bodyactiv1;
    private String bodyactiv2;
    private String bodyactiv3;
    private String bodyactiv4;
    private String bodyactiv5;
    private String bodyactiv6;
    private String housework1;
    private String housework2;
    private String name = "";

    private AsyncTask<String, String, String> aTask;
    private AsyncTask<String, String, String> mTask;
    private AsyncTask<String, String, String> dbCheckSyncTask;
    private AsyncTask<String, String, String> dateSyncTask;


    private Date date;
    private String meal;
    private String cognitive;
    private String uniqueness;
    private SimpleDateFormat timeformatter;
    private SimpleDateFormat extratimeformatter;
    private SimpleDateFormat formatter;
    private long diff;
    private long tdiff;

    private TextView tv_time;
    private String strStartTime;    //시작시간
    private String strEndTime;      //종료시간
    private String thisYear;

    private String recipiid;
    private String gender;
    private String rating;
    private String acceptnumber; //인정번호
    private String organization;
    private String organizationId; //기관번호
    private String birth;
    private String responsibility;  //담당자
    private String stime;
    private String stime1;
    private String stime2;
    private String stime3;
    private String division;        //수급자 구분
    private String place;            //수급자 지점
    private String baseTime;        //수급자 기본시간


    private SimpleDateFormat utctime;
    private String phone;
    private TextView tv_rating;
    private TextView tv_phone;
    private TextView tv_name;
    private String title;
    private String totalnumber;
    private TextView var;


    private int totalhour;

    private int hourmoney;//시간별 금액
    private float tmoney1;

    private float batime1;
    private float totalhour1;

    private float hourmoney1;

    private TextView tv_sumTime;
    private String strThour;
    private String strTmin;
    private String strSumth;
    private String strSumtm;

    private TextView tv_remainingTime;

    private int time1Sum = 0;

    private int vistime1;
    private String dbCheck;
    private LottieAnimationView animationView;
    private LinearLayout lin_careAll;
    private int totaltime;
    private int number = 0;

    private String schedule_date;//일자
    private String scheduletime;//근무시간
    private String contracttime; //계약시간
    private String schedulename;//계약수급자명
    private String divisiontotal;
    private String divisiondate;
    private String divisiontime;
    private TextView tv_date;

    private String date2;
    private String date1;
    private String TAG = "PickerActivity";
    private float add_total; //가산 총금액
    private float add_time; //가산적용시간
    private float add_offertime; //총 급여 제공시간

    private AsyncTask<String, String, String> cTask;
    private TextView tv_price;
    private Date startTime;
    private Date endTime;
    private int extraTime;
    private int todayMoney;
    private String commute;
    private ImageView img_back;
    private String route;
    private String personId;
    private String department;
    private String strToday;
    private String button = "";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visiting);

        activateToolbar();

        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        recipiid = commuteRecipient.getRecipiId();
        name = commuteRecipient.getName();
        rating = commuteRecipient.getRating();
        commute = commuteRecipient.getCommute();
        route = commuteRecipient.getRoute();

        Login login = Login.getInstance();
        personId = login.getPersonId();
        responsibility = login.getResponsibility();
        department = login.getPersonId();

//        Toast.makeText(VisitingActivity.this, Integer.toString((int) vistime), Toast.LENGTH_SHORT).show();

        tv_date = findViewById(R.id.tv_date);

        TextView tv_information = findViewById(R.id.tv_information);
        Date currentTime = new Date();
        tv_information.setText(name + "님");


        String today = new SimpleDateFormat("yyyy.MM.dd").format(currentTime);
        thisYear = new SimpleDateFormat("yyyy").format(currentTime);

        //매달 해당 자료를 가져오기 위한 날짜 설정 단계
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String month = sdf.format(currentTime);
        startMon = month + "-" + "01";
        endMon = month + "-" + "32";

        TextView tv_date = findViewById(R.id.tv_date);
        tv_date.setText(today);


        aTask = new mSyncTask().execute();


//        minhour = 60;
        tv_remainingTime = findViewById(R.id.tv_remainingTime);
        tv_remainingTime.setText(totalhour + "분");

        String nim;
        var = findViewById(R.id.var);
        if(name==null){
            name = "";
            nim = "";
        }else {
            nim = " 님의 ";
        }
        var.setText(name + nim + "건강상태 체크");
        tv_startTime = findViewById(R.id.tv_startTime);
        tv_endTime = findViewById(R.id.tv_endTime);

        tv_number = findViewById(R.id.tv_number);
        tv_number1 = findViewById(R.id.tv_number1);
        tv_number1_1 = findViewById(R.id.tv_number1_1);
        tv_number2 = findViewById(R.id.tv_number2);
        tv_number3 = findViewById(R.id.tv_number3);
        tv_number4 = findViewById(R.id.tv_number4);
        tv_number5 = findViewById(R.id.tv_number5);
        ck_bodyactiv1 = findViewById(R.id.ck_bodyactiv1);
        ck_bodyactiv2 = findViewById(R.id.ck_bodyactiv2);
        ck_bodyactiv3 = findViewById(R.id.ck_bodyactiv3);
        ck_bodyactiv4 = findViewById(R.id.ck_bodyactiv4);
        ck_bodyactiv5 = findViewById(R.id.ck_bodyactiv5);
        ck_bodyactiv6 = findViewById(R.id.ck_bodyactiv6);
        ck_housework1 = findViewById(R.id.ck_housework1);
        ck_housework2 = findViewById(R.id.ck_housework2);
        lin_careAll = findViewById(R.id.lin_careAll);
        lin_recipi = findViewById(R.id.lin_recipi);
        btn_start = findViewById(R.id.btn_start);
        btn_end = findViewById(R.id.btn_end);
        tv_time = findViewById(R.id.tv_time);
        tv_information = findViewById(R.id.tv_information);
        tv_information.setText(name + "님의정보");

        dateSyncTask = new DateSyncTask().execute();
        findViewById(R.id.lin_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisitingActivity.this, ServiceListActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(commute != null){
            img_back.setVisibility(View.GONE);
        }
        findViewById(R.id.lin_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisitingActivity.this, RecipientDetailActivity.class);
                intent.putExtra("name", name);
                title = "title";
                intent.putExtra("title", title);
                startActivity(intent);

            }
        });

        findViewById(R.id.btn_decrease4).setOnClickListener(this);
        findViewById(R.id.btn_increase4).setOnClickListener(this);
        findViewById(R.id.btn_decrease5).setOnClickListener(this);
        findViewById(R.id.btn_increase5).setOnClickListener(this);
        findViewById(R.id.btn_send).setOnClickListener(this);
        tv_price = findViewById(R.id.tv_price);


        btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        btn_start1 = findViewById(R.id.btn_start1);
        btn_start1.setOnClickListener(this);

        btn_start1_1 = findViewById(R.id.btn_start1_1);
        btn_start1_1.setOnClickListener(this);

        btn_start2 = findViewById(R.id.btn_start2);
        btn_start2.setOnClickListener(this);

        btn_start3 = findViewById(R.id.btn_start3);
        btn_start3.setOnClickListener(this);

        tv_phone = findViewById(R.id.tv_mdiv);
        tv_name = findViewById(R.id.tv_name);
        tv_rating = findViewById(R.id.tv_rating1);
        tv_sumTime = findViewById(R.id.tv_sumTime);

        LayoutInflater inflater = LayoutInflater.from(VisitingActivity.this);
        final View view = inflater.inflate(R.layout.camera_image, null);
        dialog_imageview = view.findViewById(R.id.dialog_imageview);

        tv_name.setText(name);

        formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        timeformatter = new SimpleDateFormat("HH:mm", Locale.KOREA);
        extratimeformatter = new SimpleDateFormat("HHmm", Locale.KOREA);
        currentDate = commuteRecipient.getStartWork();
//        formatterScreen = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);

        utctime = new SimpleDateFormat("HH:mm", Locale.KOREA);
        utctime.setTimeZone(TimeZone.getTimeZone("UTC"));


        //strDate = formatter.format(currentDate);
        //  String strDateScreen = formatterScreen.format(currentDate);
        //   tv_date.setText(strDateScreen);


        rg_body = findViewById(R.id.rg_body);
        rg_meal = findViewById(R.id.rg_meal);
        rg_cognitive = findViewById(R.id.rg_cognitive);

        if (commute == null) {
            lin_recipi.setVisibility(View.GONE);
        } else if (commute != null && commute.equals("true") && route.equals("방문요양")) {
            strStartTime = commuteRecipient.getStartTime();
            btn_start.setText(strStartTime);
            tv_startTime.setText(strStartTime);
        }

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_start.getText().equals("출근하기")) {
                    startTime = new Date();
                    strStartTime = timeformatter.format(startTime);
                    long lgStartTime = startTime.getTime();

                    extraTime = Integer.parseInt(extratimeformatter.format(startTime));
                    if (extraTime >= 1800 && extraTime < 2200) {
                        todayMoney = (int) (hourmoney * 1.2);
                    } else if (extraTime >= 2200 || extraTime < 600) {
                        todayMoney = (int) (hourmoney * 1.3);
                    } else {
                        todayMoney = hourmoney;
                    }
                    tv_price.setText(new DecimalFormat("###,###").format(todayMoney) + "원");
                    Intent intent = new Intent(VisitingActivity.this, MainActivity.class);
                    intent.putExtra("route", "방문요양");
                    startActivity(intent);
                }
            }
        });
//        btn_end.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (btn_start.getText().equals("출근하기")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(VisitingActivity.this);
//                    builder.setTitle("시작확인").setMessage("출근하기를 눌러주세요.");
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                } else {
//
//                    endTime = new Date();
//                    strEndTime = timeformatter.format(endTime);
//
//                    try {
//                        Date endtimes = timeformatter.parse(strEndTime);
//                        Date starttimes = timeformatter.parse(strStartTime);
//
//                        //diff = timeformatter.parse(strEndTime).getTime() - timeformatter.parse(strStartTime).getTime();
//                        diff = endtimes.getTime() - starttimes.getTime();
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
////                    if (diff   < 1) {
//                    if ((diff / (60 * 1000)) < batime) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(VisitingActivity.this);
//                        builder.setTitle("시간확인").setMessage(batime + "분이 지나지 않았습니다.");
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
//
//                    } else {
//                        btn_end.setText(strEndTime);
//                        tv_endTime.setText(strEndTime);
//                        totalUsingTime = Long.toString(diff / (60 * 1000));
//                        tv_time.setText(totalUsingTime);
//                    }
//                }
//            }
//        });

        findViewById(R.id.btn_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VisitingActivity.this,MealCheckActivity.class));

            }
        });


        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (btn_end.getText().equals("종료")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(VisitingActivity.this);
//                    builder.setTitle("종료확인").setMessage("종료시간을 눌러주세요.");
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                } else {



                if (btn_start.getText().equals("출근하기")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VisitingActivity.this);
                    builder.setTitle("시작확인").setMessage("출근하기를 눌러주세요.");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    Date endTime = new Date();
                    strEndTime = timeformatter.format(endTime);
                    endTime = new Date();
                    strEndTime = timeformatter.format(endTime);

                    try {
                        Date endtimes = timeformatter.parse(strEndTime);
                        Date starttimes = timeformatter.parse(strStartTime);

                        //diff = timeformatter.parse(strEndTime).getTime() - timeformatter.parse(strStartTime).getTime();
                        diff = endtimes.getTime() - starttimes.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if ((diff / (60 * 1000)) < batime) {
//                    if (diff < 1000) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(VisitingActivity.this);
                        builder.setTitle("시간확인").setMessage(batime + "분이 지나지 않았습니다.");
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    } else {


                        btn_end.setText(strEndTime);
                        tv_endTime.setText(strEndTime);
                        totalUsingTime = Long.toString(diff / (60 * 1000));
                        tv_time.setText(totalUsingTime);

                        checkQuery();

                        add_total = 1;
                        add_time = 20;
                        add_offertime = 4;
                        String rst = "1";

                        int rint = Integer.parseInt(rst);
                        double torf = rint * 0.2 * add_time / add_offertime;

//                    Toast.makeText(getApplicationContext(), Integer.toString((int) torf) + Integer.toString(rint) + "/" + Integer.toString((int) add_offertime) + "/" + Integer.toString((int) add_total) + "/" + Integer.toString((int) add_offertime), Toast.LENGTH_SHORT).show();

                        totaltime = Integer.parseInt(usingTime) + Integer.parseInt(usingTime1) + Integer.parseInt(usingTime1_1) + Integer.parseInt(usingTime2) + Integer.parseInt(usingTime3);
                        int thour = totaltime / 60;
                        int tmin = totaltime % 60;
                        AlertDialog.Builder builder = new AlertDialog.Builder(VisitingActivity.this);
                        builder.setTitle("내용전송");
                        builder.setMessage("총시간" + totaltime + "분을 전송하시겠습니까?");
                        // builder.setMessage("총시간 " + totaltime + " 분을 전송하시겠습니까?");
                        builder.setPositiveButton("예",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        int bodyId = rg_body.getCheckedRadioButtonId();
                                        int mealId = rg_meal.getCheckedRadioButtonId();
                                        int cognitiveId = rg_cognitive.getCheckedRadioButtonId();
                                        String sr = Integer.toString(bodyId);
                                        String or = Integer.toString(mealId);
                                        String co = Integer.toString(cognitiveId);

                                        // Toast.makeText(VisitingActivity.this,sr+or+co,Toast.LENGTH_SHORT).show();

                                        if (sr.equals("-1")) {
                                            body = "";
                                        } else {
                                            body = ((RadioButton) findViewById(bodyId)).getText().toString();
                                        }

                                        if (or.equals("-1")) {
                                            meal = "";
                                        } else {
                                            meal = ((RadioButton) findViewById(mealId)).getText().toString();
                                        }

                                        if (co.equals("-1")) {
                                            cognitive = "";
                                        } else {
                                            cognitive = ((RadioButton) findViewById(cognitiveId)).getText().toString();
                                        }

                                        uniqueness = ((EditText) findViewById(R.id.et_uniqueness)).getText().toString();

                                        if (usingTime != null) {
                                            stime = "신체활동";
                                        } else if (usingTime.equals("0")) {

                                            stime = "";
                                            usingTime = "";
                                        }
                                        if (usingTime1 != null) {
                                            stime1 = "인지활동";

                                        } else if (usingTime1.equals("0")) {
                                            stime1 = "";
                                            usingTime1 = "";
                                        }

                                        if (usingTime1_1 != null) {
                                            stime1_1 = "일상생활";

                                        } else if (usingTime1.equals("0")) {
                                            stime1_1 = "";
                                            usingTime1_1 = "";
                                        }

                                        if (usingTime2 != null) {
                                            stime2 = "정서지원";

                                        } else if (usingTime2.equals("0")) {
                                            stime2 = "";
                                            usingTime2 = "";
                                        }
                                        if (usingTime3 != null) {
                                            stime3 = "생활지원";

                                        } else if (usingTime3.equals("0")) {
                                            stime3 = "";
                                            usingTime3 = "";
                                        }
                                        Date dbDate = new Date();
                                        dbCheck = new SimpleDateFormat("yyyyMMddHHmmss").format(dbDate);


                                        if (dateCheck != null && dateCheck.equals(currentDate)) {
                                            AlertDialog.Builder builder2 = new AlertDialog.Builder(VisitingActivity.this);
                                            builder2.setTitle("방문요양");
                                            builder2.setMessage("오늘 방문요양은 이미 진행되었습니다. 그래도 전송하시겠습니까?");
                                            // builder.setMessage("총시간 " + totaltime + " 분을 전송하시겠습니까?");
                                            builder2.setPositiveButton("예",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            number++;
                                                            button = "퇴근";
                                                            mTask = new MySyncTask().execute();

                                                        }
                                                    });
                                            builder2.setNegativeButton("아니오",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Toast.makeText(getApplicationContext(), "전송이 취소되었습니다.", Toast.LENGTH_LONG).show();
                                                        }
                                                    });

                                            builder2.show();
                                        } else {
                                            number = 1;
                                            button = "퇴근";
                                            mTask = new MySyncTask().execute();
                                            dbCheckSyncTask = new DbCheckSyncTask().execute();
                                        }
                                    }
                                });

                        builder.setNegativeButton("아니오",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        Toast.makeText(getApplicationContext(), "다시 입력해주세요.", Toast.LENGTH_LONG).show();


                                    }
                                });
                        builder.show();
                    }
                }
            }


//            }
        });

    }

    private void checkQuery() {
        if (ck_bodyactiv1.isChecked()) {
            bodyactiv1 = "True";
        } else {
            bodyactiv1 = "False";
        }
        if (ck_bodyactiv2.isChecked()) {
            bodyactiv2 = "True";
        } else {
            bodyactiv2 = "False";
        }
        if (ck_bodyactiv3.isChecked()) {
            bodyactiv3 = "True";
        } else {
            bodyactiv3 = "False";
        }
        if (ck_bodyactiv4.isChecked()) {
            bodyactiv4 = "True";
        } else {
            bodyactiv4 = "False";
        }
        if (ck_bodyactiv5.isChecked()) {
            bodyactiv5 = "True";
        } else {
            bodyactiv5 = "False";
        }
        if (ck_bodyactiv6.isChecked()) {
            bodyactiv6 = "True";
        } else {
            bodyactiv6 = "False";
        }
        if (ck_housework1.isChecked()) {
            housework1 = "True";
        } else {
            housework1 = "False";
        }
        if (ck_housework2.isChecked()) {
            housework2 = "True";
        } else {
            housework2 = "False";
        }

        usingTime = tv_number.getText().toString();
        usingTime1_1 = tv_number1_1.getText().toString();
        usingTime1 = tv_number1.getText().toString();
        usingTime2 = tv_number2.getText().toString();
        usingTime3 = tv_number3.getText().toString();

        if (usingTime.equals("")) {
            usingTime = "0";
        }

        if (usingTime1.equals("")) {
            usingTime1 = "0";
        }

        if (usingTime1_1.equals("")) {
            usingTime1_1 = "0";
        }

        if (usingTime2.equals("")) {
            usingTime2 = "0";
        }

        if (usingTime3.equals("")) {
            usingTime3 = "0";
        }
    }




    public class DateSyncTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;
            dateQuery();

            return null;

        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }

    private void dateQuery() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            ResultSet dateRS = statement.executeQuery("select 일자 from Su_방문요양급여정보 WHERE 수급자코드 = '" + recipiid + "' AND 일자='" + currentDate + "'");
            while (dateRS.next()) {
                dateCheck = dateRS.getString("일자");
                number++;
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public class DbCheckSyncTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;
            dbCheckQuery();

            return null;

        }

        protected void onPostExecute(String result) {

        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }

    private void dbCheckQuery() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            ResultSet dbCheckRS = statement.executeQuery("select 디비체크 from Su_방문요양급여정보 WHERE 수급자코드 = '" + recipiid + "' AND 일자='" + currentDate + "'");
            while (dbCheckRS.next()) {
                final String dbCheck2 = dbCheckRS.getString("디비체크");
                if (dbCheck2 != null && dbCheck2.equals(dbCheck)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(tv_number.getWindowToken(), 0);
                            animationView = (LottieAnimationView) findViewById(R.id.animation_view);
                            animationView.setVisibility(View.VISIBLE);
                            lin_careAll.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "전송 완료", Toast.LENGTH_SHORT).show();
                            animationView.setAnimation("success.json");
                            animationView.playAnimation();
                            animationView.addAnimatorListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                            startActivityForResult(cameraIntent, TAKE_PICTURE);
                                        }
                                    }, 1000);

                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });

                        }
                    });
                }
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public class MySyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled()) {
                return null;
            }
            visitingQuery();
            return null;
        }

        protected void onPostExecute(String result) {
            dbCheckSyncTask = new DbCheckSyncTask().execute();
        }


        protected void onCancelled() {
            super.onCancelled();
        }
    }


    public class mSyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled()) {
                return null;
            }
            selectquery();
            return null;
        }

        protected void onPostExecute(String result) {
        }


        protected void onCancelled() {
            super.onCancelled();
        }

    }


    public void selectquery() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();

            ResultSet visitingRS = statement.executeQuery("select 신체사용시간계,인지사용시간계,일상생활시간계,정서사용시간계,생활지원사용시간계 from Su_방문요양급여정보 where 수급자코드 = '" + recipiid + "' and (일자 BETWEEN '" + startMon + "' AND '" + endMon + "')");
            while (visitingRS.next()) {
                String time1 = visitingRS.getString("신체사용시간계");
                String time2 = visitingRS.getString("인지사용시간계");
                String time3 = visitingRS.getString("일상생활시간계");
                String time4 = visitingRS.getString("정서사용시간계");
                String time5 = visitingRS.getString("생활지원사용시간계");

                if (time1 == null || time1.equals("") || time1.equals("null")) {
                    time1Sum = time1Sum + 0;
                } else {
                    time1Sum = time1Sum + Integer.parseInt(time1);
                }
                if (time2 == null || time2.equals("") || time2.equals("null")) {
                    time1Sum = time1Sum + 0;
                } else {
                    time1Sum = time1Sum + Integer.parseInt(time2);
                }
                if (time3 == null || time3.equals("") || time3.equals("null")) {
                    time1Sum = time1Sum + 0;
                } else {
                    time1Sum = time1Sum + Integer.parseInt(time3);
                }
                if (time4 == null || time4.equals("") || time4.equals("null")) {
                    time1Sum = time1Sum + 0;
                } else {
                    time1Sum = time1Sum + Integer.parseInt(time4);
                }
                if (time5 == null || time5.equals("") || time5.equals("null")) {
                    time1Sum = time1Sum + 0;
                } else {
                    time1Sum = time1Sum + Integer.parseInt(time5);
                }

            }


            ResultSet rs = statement.executeQuery("select A.수급자명,A.성별,A.등급,A.지점,A.담당,A.인정번호1,B.기관명,B.기관번호,A.생년월일,A.구분,A.기본시간,A.지점,A.담당,A.hp FROM SU_수급자기본정보 A,SU_요양기관등록정보 B WHERE  A.지점=B.지점 and A.수급자코드 = '" + recipiid + "'");
            while (rs.next()) {

                rating = rs.getString(3);
                gender = rs.getString(2);
                acceptnumber = rs.getString(6);
                organization = rs.getString(7);
                organizationId = rs.getString(8);
                birth = rs.getString(9);
                division = rs.getString(10);
                baseTime = rs.getString(11);
                place = rs.getString(12);
                responsibility = rs.getString(13);
                phone = rs.getString("hp");
            }
            ResultSet rs1 = statement.executeQuery("select * from Su_등급별재가월한도액 where 등급='" + rating + "' and 년도 ='" + thisYear + "'");
            while (rs1.next()) {
                tmoney = rs1.getInt("한도액");
                tmoney1 = rs1.getFloat("한도액");
            }


            ResultSet rs2 = statement.executeQuery("select* from Su_년도별적용급액 where 구분 = '방문' and 상세구분 ='" + baseTime + "' and 년도 = '" + thisYear + "'");

            while (rs2.next()) {
                batime = Integer.parseInt(rs2.getString("기본시간"));
                // hourmoney = rs2.getInt("금액");
                hourmoney = Integer.parseInt(rs2.getString("금액"));
                batime1 = rs2.getFloat("기본시간");
                hourmoney1 = rs2.getFloat("금액");

            }
            //     remainingTime = Integer.parseInt(baseTime) - time1Sum;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_phone.setText(phone);
                    tv_rating.setText(rating);

                    totalhour1 = (tmoney1 / hourmoney1) * batime1;
                    totalhour = (int) totalhour1;
                    int thour = totalhour / 60;
                    int tmin = totalhour % 60;

                    strThour = String.format("%02d", thour);
                    strTmin = String.format("%02d", tmin);
                    tv_sumTime.setText("총시간:" + strThour + ":" + strTmin);
                    //tv_sumTime.setText("총시간:" + Integer.toString(thour) + ":" + Integer.toString(tmin));
                    vistime1 = totalhour - (int) vistime;
                    int nhour = vistime1 / 60;
                    int nmin = vistime1 % 60;
                    strSumth = String.format("%02d", nhour);
                    strSumtm = String.format("%02d", nmin);
                    tv_remainingTime.setText("남은시간:" + strSumth + ":" + strSumtm);

                    tv_price.setText(new DecimalFormat("###,###").format(hourmoney) + "원");
                    //  tv_remainingTime.setText("남은시간:" + Integer.toString(nhour) + ":" + Integer.toString(nmin));


                }
            });
            connection.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());

        }

    }


    private void visitingQuery() {
        Connection connection;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery("insert into Su_방문요양급여정보(일자,수급자명,성별,등급,인정번호,생년월일,구분,기본시간,지점,담당," +
                    "기관명,기관기호,신체사용시간계,인지사용시간계,정서사용시간계,생활지원사용시간계," +
                    "신체기능,식사기능,인지기능,대변횟수,소변횟수,특이사항," +
                    "신체활동지원,인지활동지원,정서활동지원,생활활동지원," +
                    "일상생활시간계,개인위생,몸씻기도움,식사도움,체위변경,이동도움,화장실이용,식사청소정리세탁,개인활동지원," +
                    "디비체크,시작시간,종료시간,총시간,방문종류구분,번호,이용금액,수급자코드) " +
                    "values('" + currentDate + "','" + name + "','" + gender + "','" + rating + "','" + acceptnumber + "','" + birth + "','" + division + "','" + baseTime + "','" + place + "','" + responsibility + "'," +
                    "'" + organization + "','" + organizationId + "','" + usingTime + "','" + usingTime1 + "','" + usingTime2 + "','" + usingTime3 + "'," +
                    "'" + body + "','" + meal + "','" + cognitive + "','" + mNumber4 + "','" + mNumber5 + "','" + uniqueness + "'," +
                    "'" + stime + "','" + stime1 + "','" + stime2 + "','" + stime3 + "'," +
                    "'" + usingTime1_1 + "','" + bodyactiv1 + "','" + bodyactiv2 + "','" + bodyactiv3 + "','" + bodyactiv4 + "','" + bodyactiv5 + "','" + bodyactiv6 + "','" + housework1 + "','" + housework2 + "'," +
                    "'" + dbCheck + "','" + strStartTime + "','" + strEndTime + "','" + totaltime + "','요양','" + number + "','" + todayMoney + "','"+recipiid+"')");


            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_decrease4:
                if (mNumber4 > 0) {
                    mNumber4 = mNumber4 - 1;
                    tv_number4.setText(mNumber4 + "");
                }
                break;
            case R.id.btn_increase4:

                mNumber4 = mNumber4 + 1;
                tv_number4.setText(mNumber4 + "");

                break;
            case R.id.btn_decrease5:
                if (mNumber5 > 0) {
                    mNumber5 = mNumber5 - 1;
                    tv_number5.setText(mNumber5 + "");
                }
                break;
            case R.id.btn_increase5:
                mNumber5 = mNumber5 + 1;
                tv_number5.setText(mNumber5 + "");

                break;

        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case TAKE_PICTURE:

                if (resultCode == RESULT_OK && intent.hasExtra("data")) {


                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");

                    dialog_imageview.setImageBitmap(bitmap);
                    dialog_imageview.setScaleType(ImageView.ScaleType.FIT_XY);


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    imageBytes = baos.toByteArray();
//                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    //s1 = 8;

                    s2 = 32;
                    s4 = imageString;


                    try {

                        long now = System.currentTimeMillis();
                        Date date = new Date(now);

                        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd- HH:mm:ss");
                        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
                        String nowDate = time.format(date);
                        SimpleDateFormat hm = new SimpleDateFormat("HH:mm");

                        s3 = nowDate;
                        ymd1 = ymd.format(date);
                        hms1 = hms.format(date);
                        ttime = hm.format(date);


                    } catch (Exception e) {

                    }

                    tTask = new TSyncTask().execute();

                    Intent i8 = new Intent(VisitingActivity.this, signActivity.class);
                    i8.putExtra("route", "MenuMain");
                    startActivity(i8);

                }

                break;
        }

    }
    public class TSyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;
            query1();

            return null;

        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }

    public void query1() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");

            PreparedStatement ps = connection.prepareStatement("UPDATE Su_직원출퇴근정보 SET 퇴근BLOB = ?,퇴근시간 = ? where 직원코드 = '" + personId + "' and 일자 = '" + ymd1 + "' and 출근시간 = '" + strStartTime + "'");
            byte[] s5 = imageBytes;
            ps.setBytes(1, s5);
            ps.setString(2, ttime);
            ps.executeUpdate();
            ps.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
