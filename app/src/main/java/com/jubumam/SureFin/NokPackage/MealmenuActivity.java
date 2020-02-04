package com.jubumam.SureFin.NokPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import com.jubumam.SureFin.QuestionActivity;
import com.jubumam.SureFin.R;
import com.ortiz.touchview.TouchImageView;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MealmenuActivity extends AppCompatActivity {
    private AsyncTask<String, String, String> mTask;
    private String center;
    private String date;
    private Bitmap bitmap;
    private ImageView img_meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealmenu);

        Nok nok = Nok.getInstance();
        center = nok.getCenterName();

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
            Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();
            ResultSet resultSetPhoto = statement.executeQuery("select * from Su_식단표 where 지점='" + center + "' and 일자 = '"+date+"'");
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
