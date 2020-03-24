package com.jubumam.surefin.ProtectorPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jubumam.surefin.ImageModel;
import com.jubumam.surefin.MainActivity;
import com.jubumam.surefin.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.polidea.view.ZoomView;

public class MealmenuActivity extends ProtectorBaseActivity {
    private AsyncTask<String, String, String> mTask;
    private String center;
    private String date;
    private Bitmap bitmap;
    private ImageView img_meal;
    private List<ImageModel> mList;
    private ArrayList<ImageModel> imageModelArrayList;
    private ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private MyPagerAdapter adapter;
    private int currentmonth;

    private CirclePageIndicator indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealmenu);

        activateToolbar();

        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_item, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

//        ZoomView zoomView = new ZoomView(this);
//        zoomView.addView(v);
//        zoomView.setLayoutParams(layoutParams);
//        zoomView.setMaxZoom(4f);// 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
//
//
//        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
//        container.addView(zoomView);

        Protector protector = Protector.getInstance();
        center = protector.getCenterName();

        imageModelArrayList = new ArrayList<>();
        init();

        img_meal = findViewById(R.id.img_meal);

        date = new SimpleDateFormat("m").format(new Date());
        currentmonth = Integer.parseInt(date);
        mTask = new MySyncTask().execute();
    }

    private void init() {
        mPager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(3 * density);



        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
//                mPager.setCurrentItem(currentPage++, true);
//            }
//        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 2000, 2000);

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

    public class MySyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;

            listQuery();
            return null;

        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();
        }


    }

    private void listQuery() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            ResultSet resultSetPhoto = statement.executeQuery("select * from Su_식단표 order by 일자,순서");
            byte b[];
            mList = new ArrayList<>();
            while (resultSetPhoto.next()) {
                Blob blob = resultSetPhoto.getBlob(3);
                b = blob.getBytes(1, (int) blob.length());
                bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                imageModelArrayList.add(new ImageModel(bitmap));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        img_meal.setImageBitmap(bitmap);
                        adapter = new MyPagerAdapter(MealmenuActivity.this, imageModelArrayList);
                        mPager.setAdapter(adapter);
                        mPager.setCurrentItem(currentmonth,true);
                        indicator.setViewPager(mPager);
                    }
                });
            }
            NUM_PAGES = imageModelArrayList.size();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static class MyPagerAdapter extends PagerAdapter {


        private ArrayList<ImageModel> imageModelArrayList;
        private LayoutInflater inflater;
        private Context context;


        public MyPagerAdapter(Context context, ArrayList<ImageModel> imageModelArrayList) {
            this.context = context;
            this.imageModelArrayList = imageModelArrayList;
            inflater = LayoutInflater.from(context);
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
            View imageLayout = inflater.inflate(R.layout.zoom_item, view, false);

            assert imageLayout != null;
            ImageView imageView = imageLayout.findViewById(R.id.img_meal);


            imageView.setImageBitmap(imageModelArrayList.get(position).getImageBitmap());

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
}
