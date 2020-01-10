package com.jubumam.SureFin;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DefaultDatabaseErrorHandler;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


public class MenuMain extends AppCompatActivity {
    private String name;        //이름
    private String gender;      //성별
    private String birth;       //생년원일
    private String rating;      //등급
    private String pastdisease;      //과거병력
    private String responsibility;      //직원명

    private String basetime;
    private float vistime;
    private Timer swipeTimer;


    private float tmoney;
    private float batime;
    private float totalhour1;
    private float hourmoney;
    private int totalhour;

    private SimpleDateFormat utctime;

    ImageView n1;
    ImageView n2;
    ImageView n3;
    ImageView n4;
    ImageView n6;
    ImageView n10;
    private Button btn_offwork;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;

    private AsyncTask<String, String, String> mTask;
    private AsyncTask<String, String, String> mRecipientTask;
    private AsyncTask<String, String, String> tTask;


    private Bitmap bitmap;

    private static ImageView mImageView1;
    private List<ImageModel> mList;
    private CirclePageIndicator indicator;

    private TextView tv_careTotalTime;
    private TextView tv_careSumTime;
    private TextView tv_bathTotalTime;
    private TextView tv_bathSumTime;
    private TextView tv_nurseTotalCount;
    private TextView tv_nurseSumCount;
    private TextView tv_nurseSumTotal;
    private TextView tv_nosupport;
    private TextView tv_startWork;
    private TextView tv_name;

    private String usingTime1;
    private String nursingCount;
    private String nursingTotal;
    private long sumUsingTimeDay = 0;
    private long sumUsingTimeMonth = 0;
    private int intNursingCount = 0;
    private int intNursingTotal = 0;

    private String count;
    private int bathCount = 0;


    private int nosupport = 0;
    private SimpleDateFormat timeformatter;
    private String startMon;
    private String endMon;
    private String startWork;
    private String stime;
    private String ttime;


    String imageString;
    String s3, s4;
    String ymd1, hms1;
    int s1, s2;

    final static int TAKE_PICTURE = 1;
    ImageView dialog_imageview;
    TextView nowTime;


    final String TAG = getClass().getSimpleName();
    private String recipientBasicTime;
    private String thisYear;
    private Date currentTime;
    private String date1;
    private String date2;
    private String TAG1 = "PickerActivity";
    private String schedule_date;//일자
    private String scheduletime;//근무시간
    private String contracttime; //계약시간
    private String schedulename;//계약수급자명
    private String division;
    private String divisiontotal;
    private String divisiondate;
    private String divisiontime;
    private AsyncTask<String, String, String> cTask;
    private String strThour;
    private String strTmin;
    private String strSumth;
    private String strSumtm;
    private String strNhour;
    private String strNmin;

    private MyPagerAdapter adapter;
    private byte[] imageBytes;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);


        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        name = commuteRecipient.getName();
        rating = commuteRecipient.getRating();
        responsibility = commuteRecipient.getResponsibility();
        stime = commuteRecipient.getStartTime();


        currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String month = sdf.format(currentTime);
        startWork = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
        thisYear = new SimpleDateFormat("yyyy").format(currentTime);
        startMon = month + "-" + "01";
        endMon = month + "-" + "32";


        n1 = findViewById(R.id.n1);
        n2 = findViewById(R.id.n2);
        n3 = findViewById(R.id.n3);
        n4 = findViewById(R.id.n4);
        n6 = findViewById(R.id.n6);
        n10 = findViewById(R.id.n10);

        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(name + "님");
        tv_careTotalTime = findViewById(R.id.tv_careTotalTime);
        tv_careSumTime = findViewById(R.id.tv_careSumTime);
        tv_bathTotalTime = findViewById(R.id.tv_bathTotalTime);
        tv_bathSumTime = findViewById(R.id.tv_bathSumTime);
        tv_nurseTotalCount = findViewById(R.id.tv_nurseTotalCount);
        tv_nurseSumCount = findViewById(R.id.tv_nurseSumCount);
        tv_nurseSumTotal = findViewById(R.id.tv_nurseSumTotal);
        tv_nosupport = findViewById(R.id.tv_nosupport);
        tv_startWork = findViewById(R.id.tv_startWork);
        TextView tv_rating = findViewById(R.id.tv_rating);


        tv_rating.setText(rating);
        tv_startWork.setText(stime);


        utctime = new SimpleDateFormat("mm", Locale.KOREA);
        utctime.setTimeZone(TimeZone.getTimeZone("UTC"));

        imageModelArrayList = new ArrayList<>();

        LayoutInflater inflater = LayoutInflater.from(MenuMain.this);
        final View view = inflater.inflate(R.layout.camera_image, null);

        dialog_imageview = view.findViewById(R.id.dialog_imageview);

       // mTask = new BannerTask().execute();

        init();

        mRecipientTask = new RecipientTask().execute();

        findViewById(R.id.lin_care).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MenuMain.this, VisitingActivity.class);
                startActivity(i1);
            }
        });
        findViewById(R.id.lin_bath).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i10 = new Intent(MenuMain.this, VisitingBathActivity.class);
                startActivity(i10);
            }
        });
        findViewById(R.id.lin_nurse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(MenuMain.this, VistingNurse.class);
                startActivity(i3);
            }
        });

        findViewById(R.id.lin_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuMain.this, ServiceListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.lin_nonPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuMain.this, Non_Payment_Item.class);
                startActivity(intent);
            }
        });


        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MenuMain.this, VisitingActivity.class);
                startActivity(i1);

            }
        });

        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MenuMain.this, ServiceListActivity.class);
                startActivity(i2);
            }
        });

        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(MenuMain.this, VistingNurse.class);
                startActivity(i3);

            }
        });

        n4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4 = new Intent(MenuMain.this, MainActivity.class);
                i4.putExtra("route", "MenuMain");
                startActivity(i4);

            }
        });

        n6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i6 = new Intent(MenuMain.this, Non_Payment_Item.class);
                startActivity(i6);
            }
        });

        n10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i10 = new Intent(MenuMain.this, VisitingBathActivity.class);
                startActivity(i10);

            }
        });


        btn_offwork = (Button) findViewById(R.id.btn_offwork);
        btn_offwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);


            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }




    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
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



                    Intent i8 = new Intent(MenuMain.this, signActivity.class);
                    i8.putExtra("route","MenuMain");
                    startActivity(i8);

                }

                break;
        }

    }

    private void init() {
        mPager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(3 * density);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);

            }
        };
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {


            }
        });

    }

    private static class MyPagerAdapter extends PagerAdapter {
        private ArrayList<ImageModel> imageModelArrayList;
        private LayoutInflater inflater;
        private Context context;


        public MyPagerAdapter(Context context, ArrayList<ImageModel> imageModelArrayList) {
            this.context = context;
            this.imageModelArrayList = imageModelArrayList;
            inflater = LayoutInflater.from(context);
            notifyDataSetChanged();
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return imageModelArrayList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.fragment_image, view, false);

            assert imageLayout != null;
            mImageView1 = imageLayout.findViewById(R.id.image_frag);
            mImageView1.setImageBitmap(imageModelArrayList.get(position).getImageBitmap());
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }


    public class RecipientTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return (null);
            recipiquery();


            return null;
        }

        protected void onPostExecute(String result) {
        }


        protected void onCancelled() {
            super.onCancelled();
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

                        Schedule_dialog schedule_dialog = new Schedule_dialog(MenuMain.this);
                        schedule_dialog.callFunction(divisiondate, divisiontime, divisiontotal);
                    } else if (schedule_date == null) {
                        Toast.makeText(MenuMain.this, "선택하신 날짜에 일정이 없습니다", Toast.LENGTH_SHORT).show();

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


    public void query1() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");

            PreparedStatement ps = connection.prepareStatement("UPDATE Su_직원출퇴근정보 SET 퇴근BLOB = ?,퇴근시간 = ? where 직원명 = '"+responsibility+"' and 일자 = '" + startWork + "' and 출근시간 = '" + stime + "'");
            byte[] s5 = imageBytes;
            ps.setBytes(1,s5);
            ps.setString(2,ttime);
            ps.executeUpdate();
            ps.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private void recipiquery() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();


            ResultSet surs = statement.executeQuery("select * from Su_수급자기본정보 where 수급자명 = '" + name + "'");
            while (surs.next()) {
                basetime = surs.getString("기본시간");
            }

            ResultSet rs1 = statement.executeQuery("select * from Su_등급별재가월한도액 where 등급='" + rating + "' and 년도 ='" + thisYear + "'");
            while (rs1.next()) {
                tmoney = rs1.getInt("한도액");
            }

            ResultSet rs2 = statement.executeQuery("select* from Su_년도별금액 where 년도='" + thisYear + "' AND 구분='방문' and 상세구분 = '" + basetime + "'");
            while (rs2.next()) {
                hourmoney = Integer.parseInt(rs2.getString("금액"));
                batime = rs2.getFloat("기본시간");

            }

            ResultSet recipientRS = statement.executeQuery("select 기본시간 from Su_수급자기본정보 where 수급자명='" + name + "'");
            while (recipientRS.next()) {
                recipientBasicTime = recipientRS.getString("기본시간");


            }

            ResultSet serviceResultSetlist = statement.executeQuery("select COALESCE(A.수급자명,B.수급자명,C.수급자명,D.수급자명) AS 요양수급자,COALESCE(A.일자,B.일자,C.일자,D.서비스제공일자) AS 요양일자,(CONVERT(int,A.신체사용시간계))+(CONVERT(int,A.인지사용시간계))+(CONVERT(int,A.일상생활시간계))+(CONVERT(int,A.정서사용시간계))+(CONVERT(int,A.생활지원사용시간계)) AS 방문요양합계," +
                    "B.목욕여부,B.차량이용 ,C.방문횟수,C.총시간,D.서비스제공,D.서비스제공일자 from Su_방문요양급여정보 A FULL OUTER JOIN Su_방문목욕정보 B ON (A.일자=B.일자 AND A.수급자명=B.수급자명 AND A.번호=B.번호) " +
                    "FULL OUTER JOIN Su_방문간호정보 C ON (B.일자=C.일자 AND B.수급자명=C.수급자명 AND  B.번호=C.번호) or (A.일자=C.일자 AND A.수급자명=C.수급자명 AND  A.번호=C.번호)" +
                    "FULL OUTER JOIN Su_비급여신청자 D ON (D.서비스제공일자=A.일자 AND D.수급자명=A.수급자명 AND D.번호=A.번호) or (D.서비스제공일자=B.일자 AND D.수급자명=B.수급자명 AND D.번호=B.번호) or (D.서비스제공일자=C.일자 AND D.수급자명=C.수급자명 AND D.번호=C.번호)" +
                    "where ((A.일자 BETWEEN '" + startMon + "' AND '" + endMon + "') or (B.일자 BETWEEN '" + startMon + "' AND '" + endMon + "') or (C.일자 BETWEEN '" + startMon + "' AND '" + endMon + "') or (D.서비스제공일자 BETWEEN '" + startMon + "' AND '" + endMon + "')) AND (A.수급자명='" + name + "' or B.수급자명='" + name + "' or C.수급자명='" + name + "'or D.수급자명='" + name + "')" +
                    "order by 요양일자,A.번호");
            timeformatter = new SimpleDateFormat("mm");
            while (serviceResultSetlist.next()) {
                usingTime1 = serviceResultSetlist.getString("방문요양합계");
                count = serviceResultSetlist.getString("목욕여부");
                nursingCount = serviceResultSetlist.getString("방문횟수");
                nursingTotal = serviceResultSetlist.getString("총시간");
                if (usingTime1 == null || usingTime1.equals("") || usingTime1.equals("null")) {
                    usingTime1 = "0";
                    sumUsingTimeDay = 0 + sumUsingTimeDay;
                } else {
                    Date d1 = utctime.parse(usingTime1);
                    sumUsingTimeDay = d1.getTime() + sumUsingTimeDay;
                }
                sumUsingTimeMonth = sumUsingTimeMonth + sumUsingTimeDay;
                sumUsingTimeDay = 0;
                if (count != null && count.equals("TRUE")) {
                    bathCount++;
                }

                if (nursingTotal != null) {
                    intNursingTotal = Integer.parseInt(nursingTotal) + intNursingTotal;
                    intNursingCount++;
                }
            }

            ResultSet nonpayRS = statement.executeQuery("select * from Su_비급여신청자 where 수급자명 = '" + name + "' AND (일자 BETWEEN '" + startMon + "' AND '" + endMon + "')");
            while (nonpayRS.next()) {
                nosupport++;
            }

            ResultSet bannerResultSet = statement.executeQuery("select top 3 * from Su_배너이미지 order by id desc");
            byte b[];
            mList = new ArrayList<>();
            while (bannerResultSet.next()) {
                Blob blob = bannerResultSet.getBlob(2);
                b = blob.getBytes(1, (int) blob.length());
                bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                imageModelArrayList.add(new ImageModel(bitmap));

            }
            NUM_PAGES = imageModelArrayList.size();


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    totalhour1 = (tmoney / hourmoney) * batime;

                    //방문요양 사용
                    // 할 수 있는 총 시간
                    totalhour = (int) totalhour1;
                    int thour = totalhour / 60;
                    int tmin = totalhour % 60;
                    strThour = String.format("%02d", thour);
                    strTmin = String.format("%02d", tmin);
                    tv_careTotalTime.setText(strThour + ":" + strTmin);
                    vistime = sumUsingTimeMonth / 60000;


                    //방문요양 사용시간
                    int sumtime = (int) sumUsingTimeMonth / 60000;
                    int sumth = sumtime / 60;
                    int sumtm = sumtime % 60;
                    strSumth = String.format("%02d", sumth);
                    strSumtm = String.format("%02d", sumtm);
                    tv_careSumTime.setText(strSumth + ":" + strSumtm);

                    //방문목욕 사용 횟수
                    tv_bathSumTime.setText(bathCount + "");

                    //방문 간호 사용시간 및 횟수
                    tv_nurseSumCount.setText(intNursingCount + "회");
                    int nhour = intNursingTotal / 60;
                    int nmin = intNursingTotal % 60;
                    strNhour = String.format("%02d", nhour);
                    strNmin = String.format("%02d", nmin);
                    tv_nurseSumTotal.setText(strNhour + ":" + strNmin);
                    tv_nosupport.setText(nosupport + "");

                    mPager.setAdapter(new MyPagerAdapter(MenuMain.this, imageModelArrayList));
                    indicator.setViewPager(mPager);


                }
            });
            connection.close();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
                Intent intent = new Intent(MenuMain.this, MenuMain.class);
                startActivity(intent);
                break;
            case R.id.action_notice:
                Intent intent1 = new Intent(MenuMain.this, CustomerServiceActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_serviceEdit:
                Intent i5 = new Intent(MenuMain.this, EditRecipientActivity.class);
                i5.putExtra("route", "edit");
                startActivity(i5);
                break;
            case R.id.action_sign:
                Intent i8 = new Intent(MenuMain.this, signActivity.class);
                i8.putExtra("route","Recipi");
                startActivity(i8);
                break;

            case R.id.action_cal:
                final Calendar cal = Calendar.getInstance();
                Log.e(TAG1, cal.get(Calendar.YEAR) + "");
                Log.e(TAG1, cal.get(Calendar.MONTH) + 1 + "");
                Log.e(TAG1, cal.get(Calendar.DATE) + "");
                Log.e(TAG1, cal.get(Calendar.HOUR_OF_DAY) + "");
                Log.e(TAG1, cal.get(Calendar.MINUTE) + "");


                DatePickerDialog dialog = new DatePickerDialog(MenuMain.this, new DatePickerDialog.OnDateSetListener() {
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
    @Override
    protected void onPause() {
        swipeTimer.cancel();
        super.onPause();
    }

}