package com.jubumam.SureFin.NokPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.jubumam.SureFin.R;
import com.jubumam.SureFin.Recipient;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private AsyncTask<String, String, String> mTask;
    private List<Gallery> list;
    private Bitmap bitmap;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;

    private String name;        //이름
    private String rating;      //등급

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Nok nok = Nok.getInstance();
        name = nok.getRecipientName();
        rating = nok.getRating();
        recyclerView = findViewById(R.id.recyclerview_gallery);
        mTask = new MyAsyncTask().execute();
    }

    public class MyAsyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;
            query();
            return null;
        }

        protected void onPostExecute(String result) {


        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }

    private void query() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            final Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Su_직원출퇴근정보 where 수급자명='" + name + "'");
            byte bt[];
            list = new ArrayList<>();
            while (resultSet.next()){
                Blob bloblist = resultSet.getBlob("BLOBData");
                if (bloblist != null) {
                    bt = bloblist.getBytes(1, (int) bloblist.length());
                    bitmap = BitmapFactory.decodeByteArray(bt, 0, bt.length);
            }
                list.add(new Gallery(bitmap));
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter = new GalleryAdapter(new GalleryAdapter.galleryClickListener() {
                        @Override
                        public void galleryClicked(Gallery model) {
                            Intent intent = new Intent(GalleryActivity.this,GalleryDetailActivity.class);
                            intent.putExtra("pic",model.getBitmap());
                            startActivity(intent);
                        }
                    });
                    mAdapter.setItems(list);
                    recyclerView.setAdapter(mAdapter);
                }
            });

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
