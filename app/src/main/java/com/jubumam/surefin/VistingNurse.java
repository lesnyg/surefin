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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class VistingNurse extends BaseActivity implements View.OnClickListener {

//    Button vbtn1;
//    Button vbtn_start1;
//    Button vbtn_end1;
//    Button vbtn_start2;
//    Button vbtn_end2;
//    Button vbtn_end3;
//    Button vbtn_start4;
//    Button vbtn_end4;
//    Button vbtn_start5;
//    Button vbtn_end5;
//    Button vbtn_end6;
//    private long ciff3;
//    private long ciff4;
//    private long ciff5;
//    private long ciff6;
//
//    private long diff1;
//    private long tdiff1;
//    private long diff2;
//    private long tdiff2;
//    private long diff3;
//    private long tdiff3;
//    private long diff4;
//    private long tdiff4;
//    private long diff5;
//    private long tdiff5;
//    private long diff6;
//    private long tdiff6;
//
//    private String startTime;
//    private String endTime;
//    private String startTime1;
//    private String endTime1;
//    private String startTime2;
//    private String endTime2;
//    private String usingTime2;
//    private String startTime3;
//    private String endTime3;
//    private String usingTime3;
//    private String startTime4;
//    private String endTime4;
//    private String startTime5;
//    private String endTime5;
//    private String usingTime5;
//    private String startTime6;
//    private String endTime6;
//    private String usingTime6;
//    private String ntime;
//    private String ntime1;
//    private String ntime2;
//    private String ntime3;
//    private String ntime4;
//    private String ntime5;
//    private String ntime6;
//
//    private String totaltime;
//    private int hour;
//    private int min;
//    private int totalmin;
//    private int usingtimeint;
//    private String maintime;
//    private String totalt;
//    private TextView stimet;
//    private TextView etimet;
//    private TextView tv_sumTime;
//    private long ndiff;

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

    private TextView tv_phone1;
    private TextView tv_rating1;
    private SimpleDateFormat formatter;
    private int money;
    private int smoney;
    private String phone;
    private TextView tv_price;
    private TextView tv_date;
    private String TAG = "PickerActivity";
    private TextView vtxt1;
    private TextView vtxt2;
    private TextView vtxt2_1;
    private EditText vtxt3;
    private EditText vtxt4;
    private String date1;

    private Button vbtn2;
    private Button vbtn_start3;
    private Button vbtn_start6;

    private long diff;
    private long tdiff;
    private SimpleDateFormat timeformatter;
    private String clockusingTime;
    private String usingTime;
    private String usingTime1;
    private String usingTime4;
    private String bpressure;//최저혈압
    private String bpressure1;//최고혈압
    private String pressure; //맥박
    private String btemperature;//체온
    private String uniqueness;//특이사항
    private String name;
    private String organization;//기관명
    private String organizationId;//기관기호
    private String rating;//등급
    private String birth;//생일
    private String acceptnumber;//인정번호
    private String agency; //="의료기관";
    private String issuedate; //= "발급일자";
    private String edate; //= "유효기간";
    private String mlicense; //= "의사면허번호";
    private String vnumber;// = "방문횟수";
    private String ptime;
    private int totime;
    private int tmoney;
    private String responsibility;     //담당
    private String place;            //수급자 지점
    private String baseTime;        //수급자 기본시간
    private String gender;//성별
    private String division;//구분
    private String mhistory;//과거병력
    private String dateCheck;

    private String lastChar = "";

    private TextView tv_startTime;
    private TextView tv_endTime;
    private EditText v_time3;
    private EditText v_time6;
    private TextView et_etc;
    private SimpleDateFormat formatterScreen;
    private SimpleDateFormat utctime;
    private TextView tv_information;
    private Button btn_start;
    private Button btn_end;
    private TextView tv_time;


    private Date currentDate;
    private String strDate;
    private AsyncTask<String, String, String> mTask;
    private AsyncTask<String, String, String> aTask;
    private AsyncTask<String, String, String> dbCheckSyncTask;
    private AsyncTask<String, String, String> dateSyncTask;
    private TextView tv_name;
    private String title;
    private String totalnumber;
    private String strStartTime;    //시작시간
    private String strEndTime;      //종료시간

    private String ck_string1;
    private String ck_string2;
    private String ck_string3;
    private String ck_string4;
    private String ck_string5;
    private String ck_string6;
    private String ck_string7;
    private String ck_string8;
    private String ck_string9;
    private String ck_string10;
    private String ck_string11;
    private String ck_string12;

    private CheckBox ck_nurse1;
    private CheckBox ck_nurse2;
    private CheckBox ck_nurse3;
    private CheckBox ck_nurse4;
    private CheckBox ck_nurse5;
    private CheckBox ck_nurse6;
    private CheckBox ck_nurse7;
    private CheckBox ck_nurse8;
    private CheckBox ck_nurse9;
    private CheckBox ck_nurse10;
    private CheckBox ck_nurse11;
    private CheckBox ck_nurse12;

    private LinearLayout lin_recipi;

    private String dbCheck;
    private LottieAnimationView animationView;
    private LinearLayout lin_nurseAll;

    private String schedule_date;//일자
    private String scheduletime;//근무시간
    private String contracttime; //계약시간
    private String schedulename;//계약수급자명
    private String divisiontotal;
    private int number = 0;
    private int totaltime1 = 0;
    private String date3;
    private String date2;
    private String divisiondate;
    private String divisiontime;
    private ImageView img_back;


    private AsyncTask<String, String, String> cTask;
    private String commute;
    private String route;
    private String personId;
    private String recipiId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistringnurse);

        activateToolbar();

        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        recipiId = commuteRecipient.getRecipiId();
        name = commuteRecipient.getName();
        rating = commuteRecipient.getRating();
        responsibility = commuteRecipient.getResponsibility();
        commute = commuteRecipient.getCommute();
        route = commuteRecipient.getRoute();

        Login login = Login.getInstance();
        personId = login.getPersonId();

        aTask = new mSyncTask().execute();

        timeformatter = new SimpleDateFormat("HH:mm", Locale.KOREA);

        utctime = new SimpleDateFormat("HH:mm", Locale.KOREA);
        utctime.setTimeZone(TimeZone.getTimeZone("UTC"));


        tv_price = findViewById(R.id.tv_price);
        currentDate = new Date();
        formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        formatterScreen = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        date1 = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);


        vtxt1 = findViewById(R.id.vtxt1);
        vtxt2 = findViewById(R.id.vtxt2);
        vtxt2_1 = findViewById(R.id.vtxt2_1);
        vtxt3 = findViewById(R.id.vtxt3);
        vtxt4 = findViewById(R.id.vtxt4);

        v_time3 = findViewById(R.id.v_time3);
        v_time6 = findViewById(R.id.v_time6);
        et_etc = findViewById(R.id.et_etc);
        tv_information = findViewById(R.id.tv_information);
        tv_information.setText(name + "님의 정보");

        ck_nurse1 = findViewById(R.id.ck_nurse1);
        ck_nurse2 = findViewById(R.id.ck_nurse2);
        ck_nurse3 = findViewById(R.id.ck_nurse3);
        ck_nurse4 = findViewById(R.id.ck_nurse4);
        ck_nurse5 = findViewById(R.id.ck_nurse5);
        ck_nurse6 = findViewById(R.id.ck_nurse6);
        ck_nurse7 = findViewById(R.id.ck_nurse7);
        ck_nurse8 = findViewById(R.id.ck_nurse8);
        ck_nurse9 = findViewById(R.id.ck_nurse9);
        ck_nurse10 = findViewById(R.id.ck_nurse10);
        ck_nurse11 = findViewById(R.id.ck_nurse11);
        ck_nurse12 = findViewById(R.id.ck_nurse12);
        lin_nurseAll = findViewById(R.id.lin_nurseAll);
        btn_start = findViewById(R.id.btn_start);
        btn_end = findViewById(R.id.btn_end);
        tv_time = findViewById(R.id.tv_time);
        tv_startTime = findViewById(R.id.tv_startTime);
        tv_endTime = findViewById(R.id.tv_endTime);

        vbtn2 = findViewById(R.id.vbtn2);
        vbtn_start3 = findViewById(R.id.vbtn_start3);
        vbtn_start6 = findViewById(R.id.vbtn_start6);
        tv_name = findViewById(R.id.tv_name);
        tv_phone1 = findViewById(R.id.tv_mdiv);
        tv_rating1 = findViewById(R.id.tv_rating1);
        lin_recipi = findViewById(R.id.lin_recipi);

        LayoutInflater inflater = LayoutInflater.from(VistingNurse.this);
        final View view = inflater.inflate(R.layout.camera_image, null);
        dialog_imageview = view.findViewById(R.id.dialog_imageview);

        vtxt1.setText(formatterScreen.format(currentDate));


        if (commute == null) {
            lin_recipi.setVisibility(View.GONE);
        } else if (commute != null && commute.equals("true") && route.equals("방문간호")) {
            strStartTime = commuteRecipient.getStartTime();
            btn_start.setText(strStartTime);
            tv_startTime.setText(strStartTime);
        }

        vtxt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int digits = vtxt4.getText().toString().length();
                if (digits > 1)
                    lastChar = vtxt4.getText().toString().substring(digits - 1);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int digits = vtxt4.getText().toString().length();
                Log.d("LENGTH", "" + digits);
                if (!lastChar.equals("-")) {
                    if (digits == 2) {
                        vtxt4.append(".");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dateSyncTask = new DateSyncTask().execute();
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (commute != null) {
            img_back.setVisibility(View.GONE);
        }


        findViewById(R.id.lin_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VistingNurse.this, RecipientDetailActivity.class);
                intent.putExtra("name", name);
                title = "title";
                intent.putExtra("title", title);
                startActivity(intent);

            }
        });

        findViewById(R.id.lin_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(VistingNurse.this, ServiceListActivity.class);
                intent1.putExtra("name", name);
                startActivity(intent1);
            }
        });


        tv_name.setText(name);
        cal();

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_start.getText().equals("출근하기")) {
                    Intent intent = new Intent(VistingNurse.this, MainActivity.class);
                    intent.putExtra("route", "방문간호");
                    startActivity(intent);

                }
            }
        });
//        btn_end.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (btn_start.getText().equals("출근하기")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(VistingNurse.this);
//                    builder.setTitle("시작확인").setMessage("출근하기를 눌러주세요.");
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                } else {
//                    Date endTime = new Date();
//                    strEndTime = timeformatter.format(endTime);
//                    btn_end.setText(strEndTime);
//                    tv_endTime.setText(strEndTime);
//                    try {
//                        Date endtimes = timeformatter.parse(strEndTime);
//                        Date starttimes = timeformatter.parse(strStartTime);
//                        diff = endtimes.getTime() - starttimes.getTime();
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    if (tv_time.getText().equals("")) {
//                        clockusingTime = Long.toString(diff / (60 * 1000));
//                        tv_time.setText(clockusingTime);
//                    } else {
//                        try {
//                            totalnumber = tv_time.getText().toString();
//                            Date s1 = timeformatter.parse(totalnumber);
//                            tdiff = diff + s1.getTime();
//                            clockusingTime = Long.toString(diff / (60 * 1000));
//                            tv_time.setText(clockusingTime);
//
//                        } catch (Exception e) {
//
//                        }
//                    }
//                }
//            }
//        });
        vbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (btn_end.getText().equals("종료")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(VistingNurse.this);
//                    builder.setTitle("종료확인").setMessage("종료시간을 눌러주세요.");
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                } else {

                Date endTime = new Date();
                strEndTime = timeformatter.format(endTime);


                usingTime1 = v_time3.getText().toString();
                usingTime4 = v_time6.getText().toString();
                bpressure = vtxt2.getText().toString();
                bpressure1 = vtxt2_1.getText().toString();
                pressure = vtxt3.getText().toString();
                btemperature = vtxt4.getText().toString();
                uniqueness = et_etc.getText().toString();

                if (usingTime1.equals("")) {
                    usingTime1 = "0";
                }

                if (usingTime4.equals("")) {
                    usingTime4 = "0";
                }
                if (ck_nurse1.isChecked()) {
                    ck_string1 = "True";
                } else {
                    ck_string1 = "False";
                }
                if (ck_nurse2.isChecked()) {
                    ck_string2 = "True";
                } else {
                    ck_string2 = "False";
                }
                if (ck_nurse3.isChecked()) {
                    ck_string3 = "True";
                } else {
                    ck_string3 = "False";
                }
                if (ck_nurse4.isChecked()) {
                    ck_string4 = "True";
                } else {
                    ck_string4 = "False";
                }
                if (ck_nurse5.isChecked()) {
                    ck_string5 = "True";
                } else {
                    ck_string5 = "False";
                }
                if (ck_nurse6.isChecked()) {
                    ck_string6 = "True";
                } else {
                    ck_string6 = "False";
                }
                if (ck_nurse7.isChecked()) {
                    ck_string7 = "True";
                } else {
                    ck_string7 = "False";
                }
                if (ck_nurse8.isChecked()) {
                    ck_string8 = "True";
                } else {
                    ck_string8 = "False";
                }
                if (ck_nurse9.isChecked()) {
                    ck_string9 = "True";
                } else {
                    ck_string9 = "False";
                }
                if (ck_nurse10.isChecked()) {
                    ck_string10 = "True";
                } else {
                    ck_string10 = "False";
                }
                if (ck_nurse11.isChecked()) {
                    ck_string11 = "True";
                } else {
                    ck_string11 = "False";
                }
                if (ck_nurse12.isChecked()) {
                    ck_string12 = "True";
                } else {
                    ck_string12 = "False";
                }

                int totaltime;
                totaltime = Integer.parseInt(usingTime1) + Integer.parseInt(usingTime4);
                totime = Integer.parseInt(usingTime1) + Integer.parseInt(usingTime4);

                if (totime < 30) {
                    ptime = "30분미만";
                } else if (totime >= 60) {
                    ptime = "60분이상";
                } else if ((totime >= 30) && (totime < 60)) {
                    ptime = "30분~60분미만";
                }

                int thour = totaltime / 60;
                int tmin = totaltime % 60;
                Date dbDate = new Date();
                dbCheck = new SimpleDateFormat("yyyyMMddHHmmss").format(dbDate);

                tmoney = money - smoney;

                totaltime1 = Integer.parseInt(usingTime1) + Integer.parseInt(usingTime4);

                usingTime = Integer.toString(totaltime1);
                AlertDialog.Builder builder = new AlertDialog.Builder(VistingNurse.this);
                builder.setTitle("내용전송");
                builder.setMessage("총시간" + usingTime + "분을 전송하시겠습니까?");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                if (dateCheck != null && dateCheck.equals(date1)) {
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(VistingNurse.this);
                                    builder2.setTitle("방문간호");
                                    builder2.setMessage("오늘 방문간호는 이미 진행되었습니다. 그래도 전송하시겠습니까?");
                                    builder2.setPositiveButton("예",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    number++;
                                                    mTask = new MySyncTask().execute();
                                                    dbCheckSyncTask = new DbCheckSyncTask().execute();
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
                                    mTask = new MySyncTask().execute();
                                    dbCheckSyncTask = new DbCheckSyncTask().execute();
                                }

                            }

                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "전송이 취소되었습니다.", Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
                Toast.makeText(VistingNurse.this, ptime, Toast.LENGTH_LONG).show();
//                }
            }
        });

    }


    private void cal() {


        final Calendar cal = Calendar.getInstance();

        Log.e(TAG, cal.get(Calendar.YEAR) + "");
        Log.e(TAG, cal.get(Calendar.MONTH) + 1 + "");
        Log.e(TAG, cal.get(Calendar.DATE) + "");
        Log.e(TAG, cal.get(Calendar.HOUR_OF_DAY) + "");
        Log.e(TAG, cal.get(Calendar.MINUTE) + "");
    }

    private void query() {

        Connection con = null;


        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement sts = con.createStatement();
            ResultSet rs = sts.executeQuery("SELECT A.수급자명,A.성별,A.등급,A.지점,A.담당,A.인정번호1,A.구분,A.기본시간,A.생년월일,A.기본시간,A.과거병력,A.구분,A.성별,A.hp,B.기관명,B.기관번호 FROM SU_수급자기본정보 A, SU_요양기관등록정보 B WHERE A.지점=B.지점 and 수급자코드 ='" + recipiId + "'");


            while (rs.next()) {


                rating = rs.getString("등급");
                acceptnumber = rs.getString("인정번호1");
                organization = rs.getString("기관명");
                organizationId = rs.getString("기관번호");
                birth = rs.getString("생년월일");
                responsibility = rs.getString("담당");
                place = rs.getString("지점");
                baseTime = rs.getString("기본시간");
                gender = rs.getString("성별");
                division = rs.getString("구분");
                mhistory = rs.getString("과거병력");
                phone = rs.getString("hp");


            }

            ResultSet rst = sts.executeQuery("select * from Su_방문간호지시서 where 수급자코드 ='" + recipiId + "'");
            while (rst.next()) {
                agency = rst.getString("의료기관명칭");
                issuedate = rst.getString("발급일자");
                edate = rst.getString("유효기간");
                mlicense = rst.getString("의사면허번호");
                vnumber = rst.getString("방문횟수");

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_phone1.setText(phone);
                    tv_rating1.setText(rating);
                }
            });

            con.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());

        }
    }

    private void nurseQuery() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("insert into Su_방문간호정보(일자,수급자명,기관기호,기관명,등급,생년월일,인정번호,의료기관명칭,발급일자,유효기간," +
                    "의사면허번호,방문횟수,시작시간,종료시간,총시간,혈압,혈압2,맥박,체온," +
                    "간호시간계,건강시간계,특이사항,금액,지점,담당,기본시간,급여" +
                    ",성별,구분,주요질환,욕창관리,영양관리,통증관리,배설관리,당뇨발관리,호흡기간호,투석간호,구강간호,관절오그라듦예방,투약관리,기초건강관리,인지훈련,총시간이름,디비체크,번호,수급자코드) " +
                    "values('" + date1 + "','" + name + "','" + organizationId + "','" + organization + "','" + rating + "'," +
                    "'" + birth + "','" + acceptnumber + "','" + agency + "','" + issuedate + "','" + edate + "','" + mlicense + "'," +
                    "'" + vnumber + "','" + strStartTime + "','" + strEndTime + "','" + usingTime + "','" + bpressure + "','" + bpressure1 + "','" + pressure + "'," + "'" + btemperature + "'," +
                    "'" + usingTime1 + "','" + usingTime4 + "','" + uniqueness + "','" + smoney + "','" + place + "','" + responsibility + "','" + baseTime + "','" + tmoney + "','" + gender + "','" + division + "','" + mhistory + "'," +
                    "'" + ck_string1 + "','" + ck_string2 + "','" + ck_string3 + "','" + ck_string4 + "','" + ck_string5 + "','" + ck_string6 + "','" + ck_string7 + "','" + ck_string8 + "','" + ck_string9 + "','" + ck_string10 + "','" + ck_string11 + "','" + ck_string12 + "','" + ptime + "','" + dbCheck + "','" + number + "','"+recipiId+"')");
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();

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
            ResultSet dateRS = statement.executeQuery("select 일자 from Su_방문간호정보 WHERE 수급자코드 ='" + recipiId + "' AND 일자='" + date1 + "'");
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

    public class mSyncTask extends AsyncTask<String, String, String> {

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
            ResultSet dbCheckRS = statement.executeQuery("select 디비체크 from Su_방문간호정보 WHERE 수급자코드 ='" + recipiId + "' AND 일자='" + date1 + "'");
            while (dbCheckRS.next()) {
                final String dbCheck2 = dbCheckRS.getString("디비체크");
                if (dbCheck2 != null && dbCheck2.equals(dbCheck)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v_time3.getWindowToken(), 0);
                            animationView = (LottieAnimationView) findViewById(R.id.animation_view);
                            animationView.setVisibility(View.VISIBLE);
                            lin_nurseAll.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "전송완료", Toast.LENGTH_LONG).show();
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
            nurseQuery();
            return null;
        }

        protected void onPostExecute(String result) {

        }


        protected void onCancelled() {
            super.onCancelled();
        }
    }


    public void onClick(View v) {
        switch (v.getId()) {


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

                    //  Intent i8 = new Intent(MenuMain.this, SplashActivity.class);
                    //  startActivity(i8);
                    tTask = new TSyncTask().execute();


                    Intent i8 = new Intent(VistingNurse.this, signActivity.class);
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
