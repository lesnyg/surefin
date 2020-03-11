package com.jubumam.SureFin.ProtectorPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.jubumam.SureFin.BaseActivity;
import com.jubumam.SureFin.R;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends ProtectorBaseActivity {
    private AsyncTask<String, String, String> mTask;
    private List<Gallery> list;
    private Bitmap bitmap;
    private Bitmap bitmap2;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;

    private String name;        //이름
    private String rating;      //등급
    private String recipiId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        activateToolbar();

        Protector protector = Protector.getInstance();
        recipiId = protector.getRecipiId();
        name = protector.getRecipientName();
        rating = protector.getRating();
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
            ResultSet resultSet = statement.executeQuery("select * from Su_직원출퇴근정보 where 수급자코드 = '" + recipiId + "' order by 일자 DESC");
            byte bt[];
            byte bt2[];
            list = new ArrayList<>();
            while (resultSet.next()){
                Blob bloblist = resultSet.getBlob("BLOBData");
                if (bloblist != null) {
                    bt = bloblist.getBytes(1, (int) bloblist.length());
                    bitmap = BitmapFactory.decodeByteArray(bt, 0, bt.length);
                    list.add(new Gallery(bitmap));
                }

                Blob bloblist2 = resultSet.getBlob("퇴근BLOB");
                if (bloblist2 != null) {
                    bt2 = bloblist2.getBytes(1, (int) bloblist2.length());
                    bitmap2 = BitmapFactory.decodeByteArray(bt2, 0, bt2.length);
                    list.add(new Gallery(bitmap2));
                }
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
