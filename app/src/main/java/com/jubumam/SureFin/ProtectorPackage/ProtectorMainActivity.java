package com.jubumam.SureFin.ProtectorPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jubumam.SureFin.BaseActivity;
import com.jubumam.SureFin.ImageModel;
import com.jubumam.SureFin.R;
import com.jubumam.SureFin.ServiceListActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class ProtectorMainActivity extends ProtectorBaseActivity {

    final String TAG = getClass().getSimpleName();
    private String recipiId;        //수급자코드
    private String name;        //이름
    private String rating;      //등급
    private String responsibility;      //직원명
    private String basetime;
    private String imageString;
    private String s3, s4;
    private String ymd1, hms1;
    private String nursingCount;
    private String usingTime1;
    private String nursingTotal;
    private String count;
    private String startMon;
    private String endMon;

    private String thisYear;
    private String strThour;
    private String strTmin;
    private String strSumth;
    private String strSumtm;
    private String strNhour;
    private String strNmin;
    private long sumUsingTimeDay = 0;
    private long sumUsingTimeMonth = 0;
    private float tmoney;
    private float batime;
    private float totalhour1;
    private float hourmoney;
    private float vistime;
    private int intNursingCount = 0;
    private int intNursingTotal = 0;
    private int bathCount = 0;
    private int nosupport = 0;
    private int s1, s2;
    private int totalhour;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private SimpleDateFormat timeformatter;
    private SimpleDateFormat utctime;


    private static ViewPager mPager;


    private AsyncTask<String, String, String> mTask;
    private AsyncTask<String, String, String> mRecipientTask;
    private AsyncTask<String, String, String> tTask;
    private AsyncTask<String, String, String> cTask;
    private final static int TAKE_PICTURE = 1;

    private byte[] imageBytes;
    private Bitmap bitmap;
    private Timer swipeTimer;
    private Date currentTime;

    private ArrayList<ImageModel> imageModelArrayList;
    private List<ImageModel> mList;
    private CirclePageIndicator indicator;

    private TextView tv_careTotalTime;
    private TextView tv_careSumTime;
    private TextView tv_bathSumTime;
    private TextView tv_nurseSumCount;
    private TextView tv_nurseSumTotal;
    private TextView tv_nosupport;
    private TextView tv_startWork;
    private TextView tv_name;
    private TextView tv_bathTotalTime;
    private TextView tv_nurseTotalCount;
    private static ImageView mImageView1;
    private ImageView dialog_imageview;
    private Button btn_offwork;
    private String bathTotalCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protector_main);

        activateToolbar();

        Protector protector = Protector.getInstance();
        recipiId = protector.getRecipiId();
        name = protector.getRecipientName();
        rating = protector.getRating();

        currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String month = sdf.format(currentTime);
        thisYear = new SimpleDateFormat("yyyy").format(currentTime);
        startMon = month + "-" + "01";
        endMon = month + "-" + "32";

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
        TextView tv_rating = findViewById(R.id.tv_rating);


        tv_rating.setText(rating);


        utctime = new SimpleDateFormat("mm", Locale.KOREA);
        utctime.setTimeZone(TimeZone.getTimeZone("UTC"));

        imageModelArrayList = new ArrayList<>();

        LayoutInflater inflater = LayoutInflater.from(ProtectorMainActivity.this);
        final View view = inflater.inflate(R.layout.camera_image, null);

        dialog_imageview = view.findViewById(R.id.dialog_imageview);


        init();

        mRecipientTask = new RecipientTask().execute();


        findViewById(R.id.lin_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProtectorMainActivity.this, ServiceListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {           //서비스 사용내역
                startActivity(new Intent(ProtectorMainActivity.this,ProtectorServiceListActivity.class));
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {      //앨범
            @Override
            public void onClick(View v) {       //앨범
                startActivity(new Intent(ProtectorMainActivity.this,GalleryActivity.class));
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       //식단
                startActivity(new Intent(ProtectorMainActivity.this,MealmenuActivity.class));
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       //결제
                startActivity(new Intent(ProtectorMainActivity.this, ProtectorPaymentActivity.class));
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {      //입금내역
            @Override
            public void onClick(View v) {       //입금내역
                startActivity(new Intent(ProtectorMainActivity.this, ProtectorDepositActivity.class));
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {      //공지사항
            @Override
            public void onClick(View v) {       //공지사항
                startActivity(new Intent(ProtectorMainActivity.this, ProtectorCustomerServiceActivity.class));
            }
        });
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


    private void recipiquery() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();


            ResultSet surs = statement.executeQuery("select 기본시간,목욕횟수 from Su_수급자기본정보 where 수급자코드 = '" + recipiId + "'");
            while (surs.next()) {
                basetime = surs.getString("기본시간");
                bathTotalCount = surs.getString("목욕횟수");
            }

            ResultSet rs1 = statement.executeQuery("select 한도액 from Su_등급별재가월한도액 where 등급='" + rating + "' and 년도 ='" + thisYear + "'");
            while (rs1.next()) {
                tmoney = rs1.getInt("한도액");
            }

            ResultSet rs2 = statement.executeQuery("select 금액,기본시간 from Su_년도별금액 where 년도='" + thisYear + "' AND 구분='방문' and 상세구분 = '" + basetime + "'");
            while (rs2.next()) {
                hourmoney = Integer.parseInt(rs2.getString("금액"));
                batime = rs2.getFloat("기본시간");

            }

            ResultSet serviceResultSetlist = statement.executeQuery("select COALESCE(A.수급자명,B.수급자명,C.수급자명) AS 요양수급자,COALESCE(A.일자,B.일자,C.일자) AS 요양일자,(CONVERT(int,A.신체사용시간계))+(CONVERT(int,A.인지사용시간계))+(CONVERT(int,A.일상생활시간계))+(CONVERT(int,A.정서사용시간계))+(CONVERT(int,A.생활지원사용시간계)) AS 방문요양합계," +
                    "B.목욕여부,B.차량이용 ,C.방문횟수,C.총시간 from Su_방문요양급여정보 A FULL OUTER JOIN Su_방문목욕정보 B ON (A.일자=B.일자 AND A.수급자명=B.수급자명 AND A.번호=B.번호) " +
                    "FULL OUTER JOIN Su_방문간호정보 C ON (B.일자=C.일자 AND B.수급자명=C.수급자명 AND  B.번호=C.번호) or (A.일자=C.일자 AND A.수급자명=C.수급자명 AND  A.번호=C.번호)" +
                    "where ((A.일자 BETWEEN '" + startMon + "' AND '" + endMon + "') or (B.일자 BETWEEN '" + startMon + "' AND '" + endMon + "') or (C.일자 BETWEEN '" + startMon + "' AND '" + endMon + "')) AND (A.수급자코드 = '" + recipiId + "' or B.수급자코드 = '" + recipiId + "' or C.수급자코드 = '" + recipiId + "')" +
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

            ResultSet nonpayRS = statement.executeQuery("select 일자 from Su_비급여신청자 where 수급자코드 = '" + recipiId + "' AND (일자 BETWEEN '" + startMon + "' AND '" + endMon + "')");
            while (nonpayRS.next()) {
                nosupport++;
            }

            //배너이미지
            ResultSet bannerResultSet = statement.executeQuery("select * from Su_배너이미지 order by id desc");
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
                    //     Toast.makeText(MenuMain.this,Integer.toString((int)vistime),Toast.LENGTH_SHORT).show();

                    //방문요양 사용시간
                    int sumtime = (int) sumUsingTimeMonth / 60000;
                    int sumth = sumtime / 60;
                    int sumtm = sumtime % 60;
                    strSumth = String.format("%02d", sumth);
                    strSumtm = String.format("%02d", sumtm);
                    tv_careSumTime.setText(strSumth + ":" + strSumtm);

                    //방문목욕 사용 횟수
                    tv_bathTotalTime.setText(bathTotalCount);
                    tv_bathSumTime.setText(bathCount + "");

                    //방문 간호 사용시간 및 횟수
                    tv_nurseSumCount.setText(intNursingCount + "회");
                    int nhour = intNursingTotal / 60;
                    int nmin = intNursingTotal % 60;
                    strNhour = String.format("%02d", nhour);
                    strNmin = String.format("%02d", nmin);
                    tv_nurseSumTotal.setText(strNhour + ":" + strNmin);
                    tv_nosupport.setText(nosupport + "");

                    mPager.setAdapter(new MyPagerAdapter(ProtectorMainActivity.this, imageModelArrayList));
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
}
