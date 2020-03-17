package com.jubumam.surefin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class cameraActivity extends AppCompatActivity {

    TextView nowTime;
    final String TAG = getClass().getSimpleName();
    ImageView cameraimage;
    Button cameraBtn;
    Button button, button1, button2;
    String imageString;
    String s3, s4;
    String ymd1, hms1;
    int s1, s2;
    byte[] s5;
    final static int TAKE_PICTURE = 1;
    private String name;        //이름
    private String gender;      //성별
    private String birth;       //생년원일
    private String rating;      //등급
    private String pastdisease;      //과거병력
    private String responsibility;      //직원명


    private AsyncTask<String, String, String> caTask;
    private String recipiId;
    private String personId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.activity_camera1);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get the ActionBar here to configure the way it behaves.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);


//        Intent intent = getIntent();
//        name = intent.getExtras().getString("name");
//        gender = intent.getExtras().getString("gender");
//        rating = intent.getExtras().getString("rating");
//        birth = intent.getExtras().getString("birth");
//        pastdisease = intent.getExtras().getString("pastdisease");
//        responsibility = intent.getExtras().getString("responsibility");


        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        recipiId = commuteRecipient.getRecipiId();
        name = commuteRecipient.getName();
        rating = commuteRecipient.getRating();


        Login login = Login.getInstance();
        personId = login.getPersonId();
        responsibility = login.getResponsibility();



        // 레이아웃과 변수 연결
        //     cameraimage = findViewById(R.id.cameraimage);
        //button = findViewById(R.id.button);
        // button1 = findViewById(R.id.button1);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // btn1 = findViewById(R.id.btn1);
        // btn1.setOnClickListener(this);

        // 카메라 버튼에 리스터 추가
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cameraActivity.this, "전송완료", Toast.LENGTH_SHORT).show();
                caTask = new caAsyncTask().execute();

                Intent intent = new Intent(getApplication(), MenuMain.class);
                // intent.putExtra("time",s3);
                intent.putExtra("name", name);
                intent.putExtra("gender", gender);
                intent.putExtra("rating", rating);
                intent.putExtra("birth", birth);
                intent.putExtra("pastdisease", pastdisease);
                intent.putExtra("responsibility", responsibility);
                // intent.putExtra("ymd",ymd1);
                // intent.putExtra("hms",hms1);
                // Toast.makeText(cameraActivity.this,s3,Toast.LENGTH_SHORT).show();
                startActivity(intent);


            }
        });


        // 6.0 마쉬멜로우 이상일 경우에는 권한 체크 후 권한 요청
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

    // 버튼 onClick리스터 처리부분
    @Override
    /*
    public void onClick(View v) {




             // 카메라 앱을 여는 소스
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("이동");
                builder.setMessage("이동");
                builder.setPositiveButton("촬영", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, TAKE_PICTURE);

                    }
                });
                builder.setNegativeButton("전송", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Toast.makeText(MainActivity.this,s4,Toast.LENGTH_SHORT).show();
                        caTask = new caAsyncTask().execute();
                    }
                });

                builder.show();

            case R.id.btn1:


                Toast.makeText(MainActivity.this,s1+s2,Toast.LENGTH_SHORT).show();
                caTask = new caAsyncTask().execute();


    }
*/

    // 카메라로 촬영한 영상을 가져오는 부분

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {

                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    cameraimage.setImageBitmap(bitmap);
                    cameraimage.setScaleType(ImageView.ScaleType.FIT_XY);


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    //s1 = 8;

                    s2 = 32;
                    s4 = imageString;
                    s5 = imageBytes;


                    if (bitmap != null) {

                        try {

                            long now = System.currentTimeMillis();
                            Date date = new Date(now);

                            SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd- HH:mm:ss");
                            SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
                            String nowDate = time.format(date);

                            nowTime = (TextView) findViewById(R.id.nowtime);
                            nowTime.setText(nowDate);

                            s3 = nowDate;
                            ymd1 = ymd.format(date);
                            hms1 = hms.format(date);


                        } catch (Exception e) {

                        }


                    }

                }
                break;
        }
    }


    public void query() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("INSERT INTO  Su_직원출퇴근정보(일자,직원명,출근시간,BLOBData,직원코드)VALUES ('" + ymd1 + "','" + responsibility + "','" + hms1 + "',convert(VARBINARY(max),'" + s4 + "'),'"+ recipiId +"')");

            //ResultSet resultSet = statement.executeQuery("INSERT INTO  Su_사진1(BLOBData)VALUES ('"+s5+"')");
            // PreparedStatement pst = connection.prepareStatement("INSERT INTO  Su_사진1(BLOBData)VALUES ('"+imageString+"',convert(VARBINARY(max))");
            // pst.executeUpdate();

            //


            while (resultSet.next()) {


            }
            connection.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());
        }

    }

    public class caAsyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return (null);
            query();

            return null;
        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();
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
                Intent intent = new Intent(cameraActivity.this, MenuMain.class);
                intent.putExtra("name", name);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}