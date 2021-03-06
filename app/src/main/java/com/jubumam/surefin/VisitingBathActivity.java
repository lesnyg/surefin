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
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
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

public class VisitingBathActivity extends BaseActivity implements View.OnClickListener {

//    private RadioButton rb_car;
//    private RadioButton rb_nocar;
//    private RadioButton rb_01;
//    private RadioButton rb_02;
//    private RadioButton rb_03;
//    private RadioButton rb_04;
//    private RadioButton rb_05;
//    private Button btn_price;
//    private LinearLayout lin_info;
//    private String car;             //차량이용
//    private String noCar;           //차량미이용
//    private int intCar;
//    private int intNoCar;
//    private String strProviding;
//    private long sec;
//    private long hour;
//    private long today;

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

    private long tdiff;
    private Switch aSwitch1;
    private LinearLayout bath_car;
    private LinearLayout bath_nocar;
    private LinearLayout lin_bathBefore;
    private LinearLayout lin_bathAfter;
    private LinearLayout lin_recipi;
    private SimpleDateFormat formatter;
    private SimpleDateFormat formatterScreen;
    private SimpleDateFormat timeformatter;
    private SimpleDateFormat utctime;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_rating;
    private TextView tv_date;
    private TextView tv_time;
    private TextView tv_price;
    private TextView tv_startTime;
    private TextView tv_endTime;
    private RadioGroup rg_car;
    private RadioGroup rg_nocar;
    private RadioGroup rg_providing;
    private CheckBox ck_before01;
    private CheckBox ck_before02;
    private CheckBox ck_before03;
    private CheckBox ck_after01;
    private CheckBox ck_after02;
    private CheckBox ck_after03;
    private EditText et_etc;
    private EditText et_carNumber;
    private RadioButton rb_providing;
    private Button btn_start;
    private Button btn_end;
    private Button btn_send;

    private long diff;
    private String usingTime;       //총시간
    private String strStartTime;    //시작시간
    private String strEndTime;      //종료시간
    private String carNumber;       //차량번호
    private String providing;       //제공방법
    private String beforeCK01;      //목욕전배뇨배변
    private String beforeCK02;      //목욕전욕창
    private String beforeCK03;      //목욕전 얼굴색 피부색
    private String afterCK01;       //목욕후 얼굴색 피부색
    private String afterCK02;       //목욕후 몸단장
    private String afterCK03;       //목욕후 주변정리
    private String etc;                 //특이사항
    private int price;                  //금액
    private int nonPayment;                  //비급여액

    private String recipiId;        //수급자코드
    private String name;
    private String phone;
    private String rating;
    private String gender;
    private String acceptnumber; //인정번호
    private String organization;
    private String organizationId; //기관번호
    private String birth;
    private String responsibility;     //담당
    private String division;        //수급자 구분
    private String place;            //수급자 지점
    private String baseTime;        //수급자 기본시간
    private String pastdisease;        //수급자 과거병력
    private int provide;            //제공금액

    private String carPrice;
    private String startMon;
    private String endMon;
    private String thisYear;

    private AsyncTask<String, String, String> bathSyncTask;
    private AsyncTask<String, String, String> dbCheckSyncTask;
    private AsyncTask<String, String, String> recipientSyncTask;
    private AsyncTask<String, String, String> priceSyncTask;
    private AsyncTask<String, String, String> dateSyncTask;
    private String strDate;

    private int intProviding;
    private String strCar;
    private String strNoCar;

    private String title;
    private TextView tv_information;
    private String totalnumber;


    private int intBathcount = 0;
    private TextView tv_sumTime;
    private TextView tv_remainingTime;
    private String dbCheck;
    private LottieAnimationView animationView;
    private LinearLayout lin_bathAll;
    private String dateCheck;
    private int number = 0;

    private String schedule_date;//일자
    private String scheduletime;//근무시간
    private String contracttime; //계약시간
    private String schedulename;//계약수급자명
    private String divisiontotal;
    private String divisiondate;
    private String divisiontime;

    private String date2;
    private String date1;
    private String TAG = "PickerActivity";


    private AsyncTask<String, String, String> cTask;
    private String bathTotalCount = "0";
    private String commute;
    private ImageView img_back;
    private String route;
    private String personId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visiting_bath);

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

        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String month = sdf.format(currentTime);

        startMon = month + "-" + "01";
        endMon = month + "-" + "32";

        recipientSyncTask = new RecipientSyncTask().execute();


        tv_information = findViewById(R.id.tv_information);
        aSwitch1 = findViewById(R.id.switch1);
        bath_car = findViewById(R.id.bath_car);
        bath_nocar = findViewById(R.id.bath_nocar);
        tv_date = findViewById(R.id.tv_date);
        btn_start = findViewById(R.id.btn_start);
        btn_end = findViewById(R.id.btn_end);
        tv_time = findViewById(R.id.tv_time);
        rg_car = findViewById(R.id.rg_car);
        rg_nocar = findViewById(R.id.rg_nocar);
        rg_providing = findViewById(R.id.rg_providing);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        ck_before01 = findViewById(R.id.checkBox1);
        ck_before02 = findViewById(R.id.checkBox2);
        ck_before03 = findViewById(R.id.checkBox3);
        ck_after01 = findViewById(R.id.checkBox4);
        ck_after02 = findViewById(R.id.checkBox5);
        ck_after03 = findViewById(R.id.checkBox6);
        et_etc = findViewById(R.id.et_etc);
        et_carNumber = findViewById(R.id.et_carNumber);
//        rb_01 = findViewById(R.id.rb_01);
//        rb_02 = findViewById(R.id.rb_02);
//        rb_03 = findViewById(R.id.rb_03);
//        rb_04 = findViewById(R.id.rb_04);
//        rb_05 = findViewById(R.id.rb_05);
        tv_price = findViewById(R.id.tv_price);
        tv_name = findViewById(R.id.tv_name);
        tv_phone = findViewById(R.id.tv_mdiv);
        tv_rating = findViewById(R.id.tv_rating1);
        tv_startTime = findViewById(R.id.tv_startTime);
        tv_endTime = findViewById(R.id.tv_endTime);
        tv_sumTime = findViewById(R.id.tv_sumTime);
        tv_remainingTime = findViewById(R.id.tv_remainingTime);
        lin_bathAll = findViewById(R.id.lin_bathAll);
        lin_bathBefore = findViewById(R.id.lin_bathBefore);
        lin_bathAfter = findViewById(R.id.lin_bathAfter);
        lin_recipi = findViewById(R.id.lin_recipi);

        LayoutInflater inflater = LayoutInflater.from(VisitingBathActivity.this);
        final View view = inflater.inflate(R.layout.camera_image, null);
        dialog_imageview = view.findViewById(R.id.dialog_imageview);

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
                Intent intent = new Intent(VisitingBathActivity.this, RecipientDetailActivity.class);
                intent.putExtra("name", name);
                title = "title";
                intent.putExtra("title", title);
                startActivity(intent);

            }
        });

        findViewById(R.id.lin_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(VisitingBathActivity.this, ServiceListActivity.class);
                startActivity(intent1);
            }
        });

        tv_name.setText(name);


        aSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {    //차량이용
                    bath_nocar.setVisibility(View.GONE);
                    bath_car.setVisibility(View.VISIBLE);
                    rg_nocar.clearCheck();
                    rg_car.clearCheck();
                    tv_price.setText("");
                } else {            //차량미이용
                    bath_car.setVisibility(View.GONE);
                    bath_nocar.setVisibility(View.VISIBLE);
                    et_carNumber.setText("");
                    rg_car.clearCheck();
                    rg_nocar.clearCheck();
                    tv_price.setText("");

                }


            }
        });

        tv_information.setText(name + "님의 정보");


        formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        formatterScreen = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        timeformatter = new SimpleDateFormat("HH:mm", Locale.KOREA);
        utctime = new SimpleDateFormat("mm", Locale.KOREA);
        utctime.setTimeZone(TimeZone.getTimeZone("UTC"));
        strDate = formatter.format(currentTime);
        thisYear = new SimpleDateFormat("yyyy").format(currentTime);
        String strDateScreen = formatterScreen.format(currentTime);
        tv_date.setText(strDateScreen);
        dateSyncTask = new DateSyncTask().execute();

        if (commute == null) {
            lin_recipi.setVisibility(View.GONE);
        } else if (commute != null && commute.equals("true") && route.equals("방문목욕")) {
            strStartTime = commuteRecipient.getStartTime();
            btn_start.setText(strStartTime);
            tv_startTime.setText(strStartTime);
        }

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_start.getText().equals("출근하기")) {
                    Intent intent = new Intent(VisitingBathActivity.this, MainActivity.class);
                    intent.putExtra("route", "방문목욕");
                    startActivity(intent);
//                    Date startTime = new Date();
//                    strStartTime = timeformatter.format(startTime);
//                    btn_start.setText(strStartTime);
//                    tv_startTime.setText(strStartTime);
                }
            }
        });
//        btn_end.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (btn_start.getText().equals("출근하기")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(VisitingBathActivity.this);
//                    builder.setTitle("시작확인").setMessage("출근하기를 눌러주세요.");
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                } else {
//                    lin_bathBefore.setVisibility(View.GONE);
//                    lin_bathAfter.setVisibility(View.VISIBLE);
//                    Date endTime = new Date();
//                    strEndTime = timeformatter.format(endTime);
//
//                    try {
//                        Date endtimes = timeformatter.parse(strEndTime);
//                        Date starttimes = timeformatter.parse(strStartTime);
//                        diff = endtimes.getTime() - starttimes.getTime();
//
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    if (diff < 1) {
////                    if ((diff / (60 * 1000)) < 60) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(VisitingBathActivity.this);
//                        builder.setTitle("시간확인").setMessage("60분이 지나지 않았습니다.");
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
//
//                    } else {
//                        btn_end.setText(strEndTime);
//                        tv_endTime.setText(strEndTime);
//                        usingTime = Long.toString(diff / (60 * 1000));
//                        tv_time.setText(usingTime);
//                    }
//                }
//            }
//        });


        rg_nocar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rb_01) {
                    strNoCar = "가정내(이동식)욕조";
                    carNumber = "";
                    carPrice = "차량미이용";
                    strCar = "차량미이용";
                } else if (checkedId == R.id.rb_02) {
                    strNoCar = "장기요양기관";
                    carNumber = "차량미이용";
                    carPrice = "차량미이용";
                    strCar = "";
                } else {
                    strNoCar = "대중목욕탕";
                    carNumber = "";
                    carPrice = "차량미이용";
                    strCar = "차량미이용";
                }
                priceSyncTask = new PriceSyncTask().execute();
            }
        });

        rg_car.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rb_04) {
                    strCar = "차량이용(차량내)";
                    carPrice = "차량이용(차량내)";
                    strNoCar = "";
                } else {
                    strCar = "차량이용(가정내)";
                    carPrice = "차량이용(가정내)";
                    strNoCar = "";
                }
                priceSyncTask = new PriceSyncTask().execute();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_send:
//                if (btn_end.getText().equals("종료")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(VisitingBathActivity.this);
//                    builder.setTitle("종료확인").setMessage("종료시간을 눌러주세요.");
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                } else {



                if (btn_start.getText().equals("출근하기")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VisitingBathActivity.this);
                    builder.setTitle("시작확인").setMessage("출근하기를 눌러주세요.");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    Date endTime = new Date();
                    strEndTime = timeformatter.format(endTime);

                    try {
                        Date endtimes = timeformatter.parse(strEndTime);
                        Date starttimes = timeformatter.parse(strStartTime);
                        diff = endtimes.getTime() - starttimes.getTime();


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if ((diff / (60 * 1000)) < 60) {
//                    if (diff < 1000) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(VisitingBathActivity.this);
                        builder.setTitle("시간확인").setMessage("60분이 지나지 않았습니다.");
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    } else {
                        btn_end.setText(strEndTime);
                        tv_endTime.setText(strEndTime);
                        usingTime = Long.toString(diff / (60 * 1000));
                        tv_time.setText(usingTime);


                        if (ck_before01.isChecked()) {
                            beforeCK01 = "True";
                        } else {
                            beforeCK01 = "False";
                        }
                        if (ck_before02.isChecked()) {
                            beforeCK02 = "True";
                        } else {
                            beforeCK02 = "False";
                        }
                        if (ck_before03.isChecked()) {
                            beforeCK03 = "True";
                        } else {
                            beforeCK03 = "False";
                        }
                        if (ck_after01.isChecked()) {
                            afterCK01 = "True";
                        } else {
                            afterCK01 = "False";
                        }
                        if (ck_after02.isChecked()) {
                            afterCK02 = "True";
                        } else {
                            afterCK02 = "False";
                        }
                        if (ck_after03.isChecked()) {
                            afterCK03 = "True";
                        } else {
                            afterCK03 = "False";
                        }

                        intProviding = rg_providing.getCheckedRadioButtonId();
                        rb_providing = findViewById(intProviding);
                        carNumber = et_carNumber.getText().toString();
                        if (strCar == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(VisitingBathActivity.this);
                            builder.setTitle("차량이용방법").setMessage("차량이용방법을 선택해 주세요.");
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else if (rb_providing == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(VisitingBathActivity.this);
                            builder.setTitle("목욕제공방법").setMessage("제공방법을 선택해 주세요.");
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else {
                            if (aSwitch1.isChecked() && carNumber.equals("")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(VisitingBathActivity.this);
                                builder.setTitle("차량번호").setMessage("차량번호를 입력해 주세요.");
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } else {

                                carNumber = et_carNumber.getText().toString();
                                etc = et_etc.getText().toString();
                                providing = ((RadioButton) findViewById(intProviding)).getText().toString();
                                Date dbDate = new Date();
                                dbCheck = new SimpleDateFormat("yyyyMMddHHmmss").format(dbDate);

                                AlertDialog.Builder builder = new AlertDialog.Builder(VisitingBathActivity.this);
                                builder.setTitle("내용전송");
                                builder.setMessage("총시간" + usingTime + "분을 전송하시겠습니까?");
                                // builder.setMessage("총시간 " + totaltime + " 분을 전송하시겠습니까?");
                                builder.setPositiveButton("예",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                if (dateCheck != null && dateCheck.equals(strDate)) {
                                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(VisitingBathActivity.this);
                                                    builder2.setTitle("방문목욕");
                                                    builder2.setMessage("오늘 방문목욕은 이미 진행되었습니다. 그래도 전송하시겠습니까?");
                                                    // builder.setMessage("총시간 " + totaltime + " 분을 전송하시겠습니까?");
                                                    builder2.setPositiveButton("예",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    number++;
                                                                    bathSyncTask = new BathSyncTask().execute();
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
                                                    bathSyncTask = new BathSyncTask().execute();
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
//                }
                    }
                }
                break;
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
            ResultSet dateRS = statement.executeQuery("select 일자 from Su_방문목욕정보 WHERE 수급자코드 = '" + recipiId + "' AND 일자='" + strDate + "'");
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

    private void dbCheckQuery() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            ResultSet dbCheckRS = statement.executeQuery("select 디비체크 from Su_방문목욕정보 WHERE 수급자코드 = '" + recipiId + "' AND 일자='" + strDate + "'");
            while (dbCheckRS.next()) {
                final String dbCheck2 = dbCheckRS.getString("디비체크");
                if (dbCheck2 != null && dbCheck2.equals(dbCheck)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(et_etc.getWindowToken(), 0);
                            animationView = (LottieAnimationView) findViewById(R.id.animation_view);
                            animationView.setVisibility(View.VISIBLE);
                            lin_bathAll.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "전송 완료", Toast.LENGTH_LONG).show();
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

    public class RecipientSyncTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;
            recipientQuery();

            return null;

        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }


    public class PriceSyncTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;
            priceQuery();

            return null;

        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }

    public class BathSyncTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;
            bathQuery();

            return null;

        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }

    private void recipientQuery() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select A.수급자명, A.성별, A.등급, A.인정번호1, A.생년월일, A.지점, A.담당, A.기본시간,A.목욕횟수, A.hp, A.구분, A.과거병력, B.기관명, B.기관번호 FROM SU_수급자기본정보 A, SU_요양기관등록정보 B WHERE A.지점 = B.지점 and A.수급자코드 = '" + recipiId + "'");
            while (rs.next()) {
                rating = rs.getString("등급");
                gender = rs.getString("성별");
                acceptnumber = rs.getString("인정번호1");
                organization = rs.getString("기관명");
                organizationId = rs.getString("기관번호");
                birth = rs.getString("생년월일");
                place = rs.getString("지점");
                responsibility = rs.getString("담당");
                phone = rs.getString("hp");
                baseTime = rs.getString("기본시간");
                division = rs.getString("구분");
                pastdisease = rs.getString("과거병력");
                bathTotalCount = rs.getString("목욕횟수");
            }

            ResultSet bathRS = statement.executeQuery("select 목욕여부 from Su_방문목욕정보 where 수급자코드 = '" + recipiId + "' and (일자 BETWEEN '" + startMon + "' AND '" + endMon + "')");
            while (bathRS.next()) {
                String bathcount = bathRS.getString("목욕여부");

                if (bathcount != null && bathcount.equals("TRUE")) {
                    intBathcount++;
                }
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_phone.setText(phone);
                    tv_rating.setText(rating);
                    tv_sumTime.setText(intBathcount + "회 사용");
                    tv_remainingTime.setText(Integer.parseInt(bathTotalCount) - intBathcount + "회 남음");
                }
            });


            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void bathQuery() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();


            String tr = "TRUE";

            ResultSet bathResultSet = statement.executeQuery("insert into Su_방문목욕정보(일자,수급자명,기관기호,기관명,등급,생년월일,인정번호," +
                    "시작시간,종료시간,총시간,차량이용,차량번호,차량미이용,제공방법,목욕전배뇨배변,목욕전욕창,목욕전얼굴색피부색," +
                    "목욕후얼굴색피부색,목욕후몸단장,목욕후주변정리,특이사항,금액," +
                    "지점,담당,기본시간,급여,주요질환,구분,성별,목욕여부,디비체크,번호,이용금액,수급자코드)" +
                    "VALUES('" + strDate + "','" + name + "','" + organizationId + "','" + organization + "','" + rating + "','" + birth + "','" + acceptnumber + "'," +
                    "'" + strStartTime + "','" + strEndTime + "','" + usingTime + "','" + strCar + "','" + carNumber + "','" + strNoCar + "','" + providing + "','" + beforeCK01 + "','" + beforeCK02 + "','" + beforeCK03 + "'," +
                    "'" + afterCK01 + "','" + afterCK02 + "','" + afterCK03 + "','" + etc + "','" + nonPayment + "'," +
                    "'" + place + "','" + responsibility + "','" + baseTime + "','" + provide + "','" + pastdisease + "','" + division + "','" + gender + "','" + tr + "','" + dbCheck + "','" + number + "','" + price + "','"+recipiId+"')");
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void priceQuery() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            ResultSet priceResultSet = statement.executeQuery("select * from Su_년도별적용급액 where 상세구분='" + carPrice + "' and 년도 = '" + thisYear + "'");
            while (priceResultSet.next()) {
                price = Integer.parseInt(priceResultSet.getString("금액"));
                nonPayment = Integer.parseInt(priceResultSet.getString("비급여액"));

                DecimalFormat myFormatter = new DecimalFormat("###,###");
                final String formattedPrice = myFormatter.format(price);
                final String formattednonPayment = myFormatter.format(nonPayment);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_price.setText(formattedPrice + " 원");
                        provide = price - nonPayment;
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


                    Intent i8 = new Intent(VisitingBathActivity.this, signActivity.class);
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
