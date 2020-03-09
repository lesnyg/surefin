package com.jubumam.SureFin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecipientDetailActivity extends BaseActivity {
//    private EditText et_name;
//    private byte[] imageBytes;
//    private Bitmap mBitmap;
//    private TextView nowTime;
//    private TextView cal_txt;
//    private TextView cal_txt1;
//    private Button cameraBtn;
//    private Button button, button1, button2;
//    private ImageView img_binery;
//    private ByteArrayOutputStream baos;
//    private String mImage;

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
    private TextView update;
    private TextView list;
    private TextView tv_sum;
    private ImageView img_person;
    private ImageView dialog_imageview;
    private Button btn_camera;

    private AsyncTask<String, String, String> cTask;
    private AsyncTask<String, String, String> caTask;
    private AsyncTask<String, String, String> mTask;

    final static int TAKE_PICTURE = 1;
    final String TAG = getClass().getSimpleName();

    private SimpleDateFormat ymd;
    private SimpleDateFormat hm;
    private String name;
    private String commute;
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
    private String title;
    private String imageString;
    private String s3, s4;
    private String ymd1, hms1;
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

    private int personId;
    private int s1, s2;

    private Bitmap bitmap;
    private byte[] s5;
    private String route;
    private Intent ica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_detail);

        activateToolbar();

        LayoutInflater inflater = LayoutInflater.from(RecipientDetailActivity.this);
        final View view = inflater.inflate(R.layout.camera_image, null);

        mTask = new MySyncTask().execute();

        dialog_imageview = view.findViewById(R.id.dialog_imageview);

        //et_name = findViewById(R.id.et_name);
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
            btn_camera = (Button) findViewById(R.id.btn_camera);
        update = (TextView) findViewById(R.id.update);
        list = (TextView) findViewById(R.id.list);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        commute = commuteRecipient.getCommute();
        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        route = intent.getExtras().getString("route");
        responsibility = intent.getExtras().getString("responsibility");
        if (commute == null) {
            //btn_camera.setVisibility(View.VISIBLE);
            btn_camera.setText("출근하기");
            btn_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, TAKE_PICTURE);
                }
            });
        } else {
            btn_camera.setText("이미 출근중입니다");
            btn_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainintent = new Intent(RecipientDetailActivity.this,MenuMain.class);
                    startActivity(mainintent);
                }
            });
            //btn_camera.setVisibility(View.INVISIBLE);
        }


//        if(title.equals("title")){
//            btn_camera.setVisibility(View.INVISIBLE);
//        }
//        if(title.equals("main")){
//            btn_camera.setVisibility(View.VISIBLE);
//        }


        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent il = new Intent(RecipientDetailActivity.this, MainActivity.class);
                il.putExtra("route", "RecipientDetail");
                il.putExtra("name", name);
                il.putExtra("rating", rating);
                il.putExtra("responsibility", responsibility);
                startActivity(il);


            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iup = new Intent(RecipientDetailActivity.this, EditRecipientActivity.class);
                iup.putExtra("name", name);
                iup.putExtra("rating", rating);
                iup.putExtra("responsibility", responsibility);
                iup.putExtra("route", "detail");

                startActivity(iup);


            }
        });

     /*   btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
        });*/

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
                    byte[] imageBytes = baos.toByteArray();
                    //imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    //s1 = 8;

                    s2 = 32;
                    s4 = imageString;
                    s5 = imageBytes;


                    try {

                        long now = System.currentTimeMillis();
                        Date date = new Date(now);

                        ymd = new SimpleDateFormat("yyyy-MM-dd");
                        hm = new SimpleDateFormat("HH:mm");

                        ymd1 = ymd.format(date);
                        hms1 = hm.format(date);


                    } catch (Exception e) {

                    }

                    caTask = new caAsyncTask().execute();
                    new CommuteRecipient(personId, name, rating, phoneNumber, responsibility, "true", hms1, ymd1,route);
                    if(route.equals("VisitingActivity")){
                    ica = new Intent(RecipientDetailActivity.this, VisitingActivity.class);
                    }else if(route.equals("VisitingBathActivity")){
                    ica = new Intent(RecipientDetailActivity.this, VisitingBathActivity.class);
                    }else if(route.equals("VistingNurse")){
                     ica = new Intent(RecipientDetailActivity.this, VistingNurse.class);
                    }

                    startActivity(ica);
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

    private void query1() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");

            PreparedStatement ps = connection.prepareStatement("INSERT INTO  Su_직원출퇴근정보(수급자명,일자,직원명,출근시간,BLOBData)VALUES (?,?,?,?,?)");

            ps.setString(1, name);
            ps.setString(2, ymd1);
            ps.setString(3, responsibility);
            ps.setString(4, hms1);
            ps.setBytes(5, s5);
            ps.executeUpdate();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public class caAsyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return (null);
            query1();

            return null;
        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }

    public void query() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery("select * from Su_수급자기본정보 where 수급자명='" + name + "'");
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
                phoneNumber = resultSet.getString("hp");
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


            ResultSet resultSetPhoto = statement.executeQuery("select * from Su_사진 where 이름='" + name + "'");
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
}
