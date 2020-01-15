package com.jubumam.SureFin;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class EditRecipientActivity extends AppCompatActivity {

//    private String gender;      //성별
//    private String birth;       //생년원일
//    private String pastdisease;      //과거병력
//    private String imageString;
//    private Button cal_btn;
//    Button rbtn_return;
//    private TextView cal_txt;
//    private TextView cal_txt1;
    private AsyncTask<String, String, String> caTask;
    private AsyncTask<String, String, String> rTask;
    private AsyncTask<String, String, String> rtTask;
    private AsyncTask<String, String, String> cTask;

    final static int TAKE_PICTURE = 1;
    final String TAG = getClass().getSimpleName();
    private String TAG1 = "PickerActivity";
    private String name;        //이름
    private String rating;      //등급
    private String commute;        //출근체크
    private String responsibility;      //직원명
    private String r_name;
    private String r_phone;
    private String r_rating;
    private String r_birth;
    private String r_adress;
    private String r_guardian;
    private String r_relationship;
    private String r_gbirth;
    private String r_gnumber;
    private String r_gadress;
    private String date1;
    private String date2;
    private String schedule_date;//일자
    private String scheduletime;//근무시간
    private String contracttime; //계약시간
    private String schedulename;//계약수급자명
    private String division;
    private String divisiontotal;
    private String divisiondate;
    private String divisiontime;
    private int Idno;

    private Bitmap bitmap;
    private byte[] imgb;
    private byte[] imageBytes;

    private Button rbtn_update;
    private TextView r_name_insert;
    private TextView r_rating_insert;
    private EditText r_phone_insert;
    private EditText r_birth_insert;
    private EditText r_adress_insert;
    private EditText r_guardian_insert;
    private EditText r_relationship_insert;
    private EditText r_gbirth_insert;
    private EditText r_gnumber_insert;
    private EditText r_gadress_insert;
    private ImageView img_person;
    private ImageView img_camerainsert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipient);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        commute = commuteRecipient.getCommute();
        Intent intent = getIntent();
        String route = intent.getExtras().getString("route");
        if (commute == null) {
            name = intent.getExtras().getString("name");
            rating = intent.getExtras().getString("rating");
            responsibility = intent.getExtras().getString("responsibility");
        } else if (route.equals("detail")) {
            name = intent.getExtras().getString("name");
            rating = intent.getExtras().getString("rating");
            responsibility = intent.getExtras().getString("responsibility");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);
        } else {
            name = commuteRecipient.getName();
            rating = commuteRecipient.getRating();
            responsibility = commuteRecipient.getResponsibility();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);
        }

        rbtn_update = (Button) findViewById(R.id.rbtn_update);
        r_name_insert = findViewById(R.id.r_name_insert);
        r_phone_insert = (EditText) findViewById(R.id.r_phone_insert);
        r_rating_insert = findViewById(R.id.r_rating_insert);
        r_birth_insert = (EditText) findViewById(R.id.r_birth_insert);
        r_adress_insert = (EditText) findViewById(R.id.r_adress_insert);
        r_guardian_insert = (EditText) findViewById(R.id.r_guardian_insert);
        r_relationship_insert = (EditText) findViewById(R.id.r_relationship_insert);
        r_gbirth_insert = (EditText) findViewById(R.id.r_gbirth_insert);
        r_gnumber_insert = (EditText) findViewById(R.id.r_gnumber_insert);
        r_gadress_insert = (EditText) findViewById(R.id.r_gadress_insert);
        img_person = findViewById(R.id.img_person);
        img_camerainsert = findViewById(R.id.img_camerainsert);

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rTask = new RAsyncTask().execute();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }


        findViewById(R.id.img_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);

             /*   Intent intent = new Intent(EditRecipientActivity.this,cameraActivity.class);
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
                startActivity(intent);*/
            }
        });

        img_camerainsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                caTask = new caAsyncTask().execute();

                //  Toast.makeText(EditRecipientActivity.this,imgb.toString(),Toast.LENGTH_SHORT).show();
                Toast.makeText(EditRecipientActivity.this, "수정완료", Toast.LENGTH_SHORT).show();


            }
        });


        rbtn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r_name = r_name_insert.getText().toString();
                r_phone = r_phone_insert.getText().toString();
                r_rating = r_rating_insert.getText().toString();
                r_birth = r_birth_insert.getText().toString();
                r_adress = r_adress_insert.getText().toString();
                r_guardian = r_guardian_insert.getText().toString();
                r_relationship = r_relationship_insert.getText().toString();
                r_gbirth = r_gbirth_insert.getText().toString();
                r_gnumber = r_gnumber_insert.getText().toString();
                r_gadress = r_gadress_insert.getText().toString();

                Toast.makeText(EditRecipientActivity.this, "입력완료", Toast.LENGTH_SHORT).show();

                rtTask = new RTAsyncTask().execute();
                finish();
            }
        });
/*
        rbtn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

*/
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }


    // 카메라로 촬영한 영상을 가져오는 부분

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {

                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    img_person.setImageBitmap(bitmap);
                    //   img_person.setScaleType(ImageView.ScaleType.FIT_XY);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    imageBytes = baos.toByteArray();
                    //  byte[] imageBytes = baos.toByteArray();

                    imgb = imageBytes;
           /*         Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    img_person.setImageBitmap(bitmap);
                    img_person.setScaleType(ImageView.ScaleType.FIT_XY);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
*/
                    //s1 = 8;

                    //               s2 = 32;
                    //             s4 = imageString;
                    //        s5 = imageBytes;


                }
                break;
        }
    }


    public class RTAsyncTask extends AsyncTask<String, String, String> {

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


    public void query() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("UPDATE Su_수급자기본정보 SET 수급자명='" + r_name + "',hp ='" + r_phone + "',등급='" + r_rating + "',생년월일='" + r_birth + "',주소1 = '" + r_adress + "',보호자성명 = '" + r_guardian + "',관계 = '" + r_relationship + "',생년월일1 ='" + r_gbirth + "',hp2='" + r_gnumber + "',주소11='" + r_gadress + "' WHERE 수급자명='" + name + "'");

            while (resultSet.next()) {


            }
            connection.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());


        }

    }



/*
    public void query() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("UPDATE Su_수급자기본정보 SET 수급자명='"+name+"',='"+g2+"',공단부담금액='"+g3+"',='"+g4+"',개인부담금 = '"+g5+"',식대 = '"+g6+"',경관유동식 = '"+g7+"',간식비 ='"+g8+"',침실1인='"+g9+"',침실2인='"+g10+"',이미용비='"+g11+"'WHERE 수급자명 = '"+name+"';");
            while (resultSet.next()) {

            }
            connection.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());

        }

    }*/


    public void selectquery() {

        Connection connection = null;


        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Su_수급자기본정보 where 수급자명='" + name + "'");


            while (resultSet.next()) {

                r_name = resultSet.getString(2);
                r_phone = resultSet.getString(8);
                r_rating = resultSet.getString(4);
                r_birth = resultSet.getString(14);
                r_adress = resultSet.getString(9);
                r_guardian = resultSet.getString(18);
                r_relationship = resultSet.getString(25);
                r_gbirth = resultSet.getString(22);
                r_gnumber = resultSet.getString(19);
                r_gadress = resultSet.getString(23);

                Idno = resultSet.getInt("id");

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    r_name_insert.setText(r_name);
                    r_phone_insert.setText(r_phone);
                    r_rating_insert.setText(r_rating);
                    r_birth_insert.setText(r_birth);
                    r_adress_insert.setText(r_adress);
                    r_guardian_insert.setText(r_guardian);
                    r_gbirth_insert.setText(r_gbirth);
                    r_gnumber_insert.setText(r_gnumber);
                    r_gadress_insert.setText(r_gadress);
                    r_relationship_insert.setText(r_relationship);
                }
            });

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
            Log.w("Error connection", "" + e.getMessage());
        }

    }

    public void camerainsertquery() {
        Connection connection = null;
        Blob blob = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");

            // PreparedStatement ps = connection.prepareStatement("INSERT INTO Su_배너이미지(BLOBData) VALUES (?)");
            //PreparedStatement ps = connection.prepareStatement("INSERT INTO Su_사진(Idno,이름,BLOBData) VALUES(?,?,?)");
            PreparedStatement ps = connection.prepareStatement("UPDATE Su_사진 SET BLOBData = ? where 이름 = '" + name + "'and Idno ='" + Idno + "'");
            //  ResultSet resultSet = statement.executeQuery("UPDATE Su_사진 SET BLOBData ='"+blob+"'WHERE 이름 = '"+name+"'");

            byte[] buf = imageBytes;
            //   ps.setString(1,"25");
            //  ps.setString(2,name);
            ps.setBytes(1, buf);
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        //  blob.setBytes(1,imgb);
        //  resultSet.updateBlob("BLOBData",blob);
        //  resultSet.updateBytes("BLOBData",imgb);

        //  while (resultSet.next()) {


        //  }

        //    connection.close();

        //    } catch (Exception e) {
        //    Log.w("Error connection", "" + e.getMessage());
        //  }

    }


    public class caAsyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return (null);
            camerainsertquery();

            return null;
        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();

        }
    }


    public class RAsyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return (null);
            selectquery();


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

                        Schedule_dialog schedule_dialog = new Schedule_dialog(EditRecipientActivity.this);
                        schedule_dialog.callFunction(divisiondate, divisiontime, divisiontotal);
                    } else if (schedule_date == null) {
                        Toast.makeText(EditRecipientActivity.this, "선택하신 날짜에 일정이 없습니다", Toast.LENGTH_SHORT).show();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (commute == null) {
            getMenuInflater().inflate(R.menu.baseappbar_action, menu);
        } else {
            getMenuInflater().inflate(R.menu.appbar_action, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(EditRecipientActivity.this, MenuMain.class);
                startActivity(intent);
                break;
            case R.id.action_notice:
                Intent intent1 = new Intent(EditRecipientActivity.this, CustomerServiceActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_serviceEdit:
                Intent i5 = new Intent(EditRecipientActivity.this, EditRecipientActivity.class);
                i5.putExtra("route", "edit");
                startActivity(i5);
                break;
            case R.id.action_sign:
                Intent i8 = new Intent(EditRecipientActivity.this, signActivity.class);
                i8.putExtra("route", "Recipi");
                startActivity(i8);
                break;
            case R.id.action_cal:
                final Calendar cal = Calendar.getInstance();
                Log.e(TAG1, cal.get(Calendar.YEAR) + "");
                Log.e(TAG1, cal.get(Calendar.MONTH) + 1 + "");
                Log.e(TAG1, cal.get(Calendar.DATE) + "");
                Log.e(TAG1, cal.get(Calendar.HOUR_OF_DAY) + "");
                Log.e(TAG1, cal.get(Calendar.MINUTE) + "");

                DatePickerDialog dialog = new DatePickerDialog(EditRecipientActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        date1 = String.format("%d-%02d-%02d", year, month + 1, date);
                        date2 = date1;
                        cTask = new CalSyncTask().execute();
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
