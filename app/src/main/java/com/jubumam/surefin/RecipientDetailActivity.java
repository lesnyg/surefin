package com.jubumam.surefin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecipientDetailActivity extends AppCompatActivity {
    private EditText et_name;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_date;
    private TextView tv_rating;
    private TextView tv_kounggam;
    private TextView tv_number;
    private TextView tv_gongdanper;
    private TextView tv_gongdanprice;
    private TextView tv_individualper;
    private TextView tv_individualprice;
    private TextView tv_sum;
    private ImageView img_person;
    private SimpleDateFormat ymd;
    private SimpleDateFormat hm;

    private int personId;
    private String name;
    private String date;
    private String rating;
    private String kounggam;
    private String number;
    private String gongdanper;
    private String gongdanprice;
    private String individualper;
    private String individualprice;
    private String formattedGongPrice;
    private String formattedindividualPrice;
    private String formattedsum;

    private String gender;
    private String birth;
    private String pastdisease;
    private String responsibility;
    private String phoneNumber;

    private ResultSet resultSet;
    private ResultSet resultSetPhoto;
    private Connection connection;
    private Bitmap bitmap;
    Button btn_camera;
    TextView update;
    TextView list;
    private String title;

    TextView nowTime;
    final String TAG = getClass().getSimpleName();
    Button cameraBtn;
    Button button,button1,button2;
    String imageString;
    String s3,s4;
    private String ymd1,hms1;
    int s1,s2;
    byte[] s5;
    final static int TAKE_PICTURE = 1;
    ImageView dialog_imageview;


    private AsyncTask<String,String,String> caTask;
    private AsyncTask<String, String, String> mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
// Get the ActionBar here to configure the way it behaves.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);



        LayoutInflater inflater = LayoutInflater.from(RecipientDetailActivity.this);
        final View view = inflater.inflate(R.layout.camera_image, null);

        mTask = new MySyncTask().execute();

       dialog_imageview = view.findViewById(R.id.dialog_imageview);

        et_name = findViewById(R.id.et_name);
        tv_name = findViewById(R.id.tv_name_result);
        tv_phone = findViewById(R.id.tv_phone_result);
        tv_date = findViewById(R.id.tv_date_result);
        tv_rating = findViewById(R.id.tv_rating_result);
        tv_kounggam = findViewById(R.id.tv_kounggam_result);
        tv_number = findViewById(R.id.tv_number_result);
        tv_gongdanper = findViewById(R.id.tv_gongdanper_result);
        tv_gongdanprice = findViewById(R.id.tv_gongdanprice_result);
        tv_individualper = findViewById(R.id.tv_individualper_result);
        tv_individualprice = findViewById(R.id.tv_individualprice_result);
        tv_sum = findViewById(R.id.tv_sum_result);
        img_person = findViewById(R.id.img_person);
        btn_camera = (Button)findViewById(R.id.btn_camera);
        update = (TextView)findViewById(R.id.update);
        list = (TextView)findViewById(R.id.list);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        title = intent.getExtras().getString("title");
        if(title.equals("title")){
            btn_camera.setVisibility(View.INVISIBLE);
        }
        if(title.equals("main")){
            btn_camera.setVisibility(View.VISIBLE);
        }



        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent il = new Intent(RecipientDetailActivity.this,MainActivity.class);
                il.putExtra("responsibility",responsibility);
                startActivity(il);


            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iup = new Intent(RecipientDetailActivity.this,EditRecipientActivity.class);
                iup.putExtra("name", name);
                iup.putExtra("gender", gender);
                iup.putExtra("rating", rating);
                iup.putExtra("birth", birth);
                iup.putExtra("pastdisease", pastdisease);
                iup.putExtra("responsibility", responsibility);
                startActivity(iup);
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent ica= new Intent(RecipientDetailActivity.this,cameraActivity.class);
               // ica.putExtra("name", name);
               // ica.putExtra("gender", gender);
               // ica.putExtra("rating", rating);
               // ica.putExtra("birth", birth);
               // ica.putExtra("pastdisease", pastdisease);
               // ica.putExtra("responsibility", responsibility);
              //  startActivity(ica);

               // Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
               // startActivityForResult(cameraIntent, TAKE_PICTURE);

               // caTask = new caAsyncTask().execute();

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);

         /*       final CharSequence[] options = { "사진촬영","전송" };
                AlertDialog.Builder builder = new AlertDialog.Builder(RecipientDetailActivity.this);
                builder.setTitle("출근관리");

                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (options[item].equals("사진촬영")) {

                        } else if (options[item].equals("전송")) {

                        }
                    }
                });
                builder.show();


          */

/*
                final AlertDialog.Builder alertadd = new AlertDialog.Builder(RecipientDetailActivity.this);

                LayoutInflater inflater = LayoutInflater.from(RecipientDetailActivity.this);
                final View view = inflater.inflate(R.layout.camera_image, null);
                alertadd.setView(view);
                alertadd.setNeutralButton("촬영", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {

                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, TAKE_PICTURE);

                    }
                });

                alertadd.setNegativeButton("전송", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        caTask = new caAsyncTask().execute();

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


                        Intent ica= new Intent(RecipientDetailActivity.this,MenuMain.class);
                        ica.putExtra("name", name);
                        ica.putExtra("gender", gender);
                        ica.putExtra("rating", rating);
                        ica.putExtra("birth", birth);
                        ica.putExtra("pastdisease", pastdisease);
                        ica.putExtra("responsibility", responsibility);
                        startActivity(ica);
                    }
                });


                alertadd.show();*/

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) {
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
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
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
                    byte[] imageBytes = baos.toByteArray();
                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    //s1 = 8;

                    s2 = 32;
                    s4 = imageString;
                    s5 = imageBytes;


                    try {

                        long now = System.currentTimeMillis();
                        Date date = new Date(now);

                       // SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd- HH:mm:ss");
                        ymd = new SimpleDateFormat("yyyy-MM-dd");
                        hm = new SimpleDateFormat("HH:mm");
                        //String nowDate = time.format(date);


                      //  nowTime = (TextView) findViewById(R.id.nowtime);
                      //  nowTime.setText(nowDate);
                      //  s3 = nowDate;
                        ymd1 = ymd.format(date);
                        hms1 = hm.format(date);

                       // Toast.makeText(RecipientDetailActivity.this,hms1,Toast.LENGTH_LONG).show();

                    } catch (Exception e) {

                    }



/*
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

  */

                    caTask = new caAsyncTask().execute();
                    Intent ica= new Intent(RecipientDetailActivity.this,MenuMain.class);
                    ica.putExtra("name", name);
                    ica.putExtra("gender", gender);
                    ica.putExtra("rating", rating);
                    ica.putExtra("birth", birth);
                    ica.putExtra("pastdisease", pastdisease);
                    ica.putExtra("responsibility", responsibility);
                    startActivity(ica);


/*
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


                    }*/

                }

                break;
        }
    }



    public class MySyncTask extends AsyncTask<String, String, String> {

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
    public void query1() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("INSERT INTO  Su_직원출퇴근정보(수급자명,일자,직원명,출근시간,BLOBData)VALUES ('"+name+"','"+ymd1+"','"+responsibility+"','"+hms1+"',convert(VARBINARY(max),'"+s4+"'))");


            while (resultSet.next()) {


            }
            connection.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());
        }

    }

    public class caAsyncTask extends AsyncTask<String,String,String> {

        protected void onPreExecute(){}

        @Override
        protected String doInBackground(String... strings) {
            if(isCancelled())
                return (null);
            query1();

            return null;
        }

        protected  void onPostExecute(String result){}

        protected  void onCancelled(){
            super.onCancelled();
        }
    }

    public void query() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();


            resultSet = statement.executeQuery("select * from Su_수급자기본정보 where 수급자명='" + name + "'");
            while (resultSet.next()) {

                personId = Integer.parseInt(resultSet.getString(1));
                date = resultSet.getString(3);
                rating = resultSet.getString(4);
                kounggam = resultSet.getString(5);
                number = resultSet.getString(6);
                gongdanper = resultSet.getString(36);
                gongdanprice = resultSet.getString(37);
                individualper = resultSet.getString(38);
                individualprice = resultSet.getString(39);
                phoneNumber = resultSet.getString(20);
                gender = resultSet.getString(11);
                birth = resultSet.getString(22);
                pastdisease = resultSet.getString("과거병력");
                responsibility = resultSet.getString("담당");

                final int gongPrice = Integer.parseInt(gongdanprice);
                final int indiviPrice = Integer.parseInt(individualprice);
                final int sum = gongPrice + indiviPrice;


                DecimalFormat myFormatter = new DecimalFormat("###,###");
                formattedGongPrice = myFormatter.format(gongPrice);
                formattedindividualPrice = myFormatter.format(indiviPrice);
                formattedsum = myFormatter.format(sum);




                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_name.setText(name);
                        tv_phone.setText(phoneNumber);
                        tv_date.setText(date);
                        tv_rating.setText(rating);
                        tv_kounggam.setText(kounggam);
                        tv_number.setText(number);
                        tv_gongdanper.setText(gongdanper + "%");
                        tv_gongdanprice.setText(formattedGongPrice + " 원");
                        tv_individualper.setText(individualper + "%");
                        tv_individualprice.setText(formattedindividualPrice + " 원");
                        tv_sum.setText(formattedsum + " 원");

                    }
                });
            }



            resultSetPhoto = statement.executeQuery("select * from Su_사진 where 이름='" + name + "'");
            byte b[];
            while (resultSetPhoto.next()) {
                Blob blob = resultSetPhoto.getBlob(4);
                b = blob.getBytes(1, (int) blob.length());
                bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        img_person.setImageBitmap(bitmap);


                    }
                });
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_action,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(RecipientDetailActivity.this, MenuMain.class);
                intent.putExtra("name", name);
                intent.putExtra("gender", gender);
                intent.putExtra("rating", rating);
                intent.putExtra("birth", birth);
                intent.putExtra("pastdisease", pastdisease);
                intent.putExtra("responsibility", responsibility);
                startActivity(intent);
                return true;
            case R.id.action_notice:
                Intent intent1 = new Intent(RecipientDetailActivity.this, CustomerServiceActivity.class);
                intent1.putExtra("name", name);
                intent1.putExtra("responsibility", responsibility);
                intent1.putExtra("rating", rating);
                startActivity(intent1);
                break;
            case R.id.action_serviceEdit:
                Intent i5 = new Intent(RecipientDetailActivity.this, EditRecipientActivity.class);
                i5.putExtra("name", name);
                i5.putExtra("rating", rating);
                i5.putExtra("responsibility", responsibility);
                startActivity(i5);
                break;
            case R.id.action_sign:
                Intent i8 = new Intent(RecipientDetailActivity.this, signActivity.class);
                i8.putExtra("name", name);
                i8.putExtra("rating", rating);
                i8.putExtra("responsibility", responsibility);
                startActivity(i8);
                break;
        }
        return super.onOptionsItemSelected(item);


    }
}
