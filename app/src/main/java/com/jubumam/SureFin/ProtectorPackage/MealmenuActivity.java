package com.jubumam.SureFin.ProtectorPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jubumam.SureFin.R;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pl.polidea.view.ZoomView;

public class MealmenuActivity extends ProtectorBaseActivity {
    private AsyncTask<String, String, String> mTask;
    private String center;
    private String date;
    private Bitmap bitmap;
    private ImageView img_meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealmenu);

        activateToolbar();

        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_item, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMaxZoom(4f);// 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.

        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        container.addView(zoomView);

        Protector protector = Protector.getInstance();
        center = protector.getCenterName();

        img_meal = findViewById(R.id.img_meal);

        date = "2020-01-30";
        mTask = new MySyncTask().execute();
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
            ResultSet resultSetPhoto = statement.executeQuery("select * from Su_식단표 where 지점='" + center + "' and 일자 = '" + date + "'");
            byte b[];
            while (resultSetPhoto.next()) {
                Blob blob = resultSetPhoto.getBlob(3);
                b = blob.getBytes(1, (int) blob.length());
                bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        img_meal.setImageBitmap(bitmap);

                    }
                });
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
