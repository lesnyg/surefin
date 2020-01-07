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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class EditRecipientActivity extends AppCompatActivity {

    private String name;        //이름
    private String gender;      //성별
    private String birth;       //생년원일
    private String rating;      //등급
    private String pastdisease;      //과거병력
    private String responsibility;      //직원명
    private AsyncTask<String,String,String> rtTask;
    private AsyncTask<String,String,String> rTask;
    Button rbtn_update;
    Button rbtn_return;

    TextView r_name_insert;
    EditText r_phone_insert;
    TextView r_rating_insert;
    EditText r_birth_insert;
    EditText r_adress_insert;
    EditText r_guardian_insert;
    EditText r_relationship_insert;
    EditText r_gbirth_insert;
    EditText r_gnumber_insert;
    EditText r_gadress_insert;
    private byte[] imageBytes;
    private byte[] imgb;
    String r_name;
    String r_phone;
    String r_rating;
    String r_birth;
    String r_adress;
    String r_guardian;
    String r_relationship;
    String r_gbirth;
    String r_gnumber;
    String r_gadress;
    private ImageView img_person;
    private Bitmap bitmap;
    final static int TAKE_PICTURE = 1;
    final String TAG = getClass().getSimpleName();
    private String imageString;
    private ImageView img_camerainsert;
    private AsyncTask<String,String,String> caTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipient);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); 
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        gender = intent.getExtras().getString("gender");
        rating = intent.getExtras().getString("rating");
        birth = intent.getExtras().getString("birth");
        pastdisease = intent.getExtras().getString("pastdisease");
        responsibility = intent.getExtras().getString("responsibility");

        rbtn_update = (Button)findViewById(R.id.rbtn_update);
        rbtn_return = (Button)findViewById(R.id.rbtn_return);

        r_name_insert = findViewById(R.id.r_name_insert);
        r_phone_insert = (EditText)findViewById(R.id.r_phone_insert);
        r_rating_insert = findViewById(R.id.r_rating_insert);
        r_birth_insert = (EditText)findViewById(R.id.r_birth_insert);
        r_adress_insert = (EditText)findViewById(R.id.r_adress_insert);
        r_guardian_insert = (EditText)findViewById(R.id.r_guardian_insert);
        r_relationship_insert = (EditText)findViewById(R.id.r_relationship_insert);
        r_gbirth_insert = (EditText)findViewById(R.id.r_gbirth_insert);
        r_gnumber_insert = (EditText)findViewById(R.id.r_gnumber_insert);
        r_gadress_insert = (EditText)findViewById(R.id.r_gadress_insert);
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
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) {
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
               // Toast.makeText(EditRecipientActivity.this,"수정완료",Toast.LENGTH_SHORT).show();

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
                Toast.makeText(EditRecipientActivity.this,"입력완료",Toast.LENGTH_SHORT).show();



                rtTask = new RTAsyncTask().execute();
            }
        });

        rbtn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditRecipientActivity.this, MenuMain.class);
                intent.putExtra("name", name);
                intent.putExtra("gender", gender);
                intent.putExtra("rating", rating);
                intent.putExtra("birth", birth);
                intent.putExtra("pastdisease", pastdisease);
                intent.putExtra("responsibility", responsibility);
                startActivity(intent);

            }
        });


    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
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
                    img_person.setScaleType(ImageView.ScaleType.FIT_XY);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();

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





    public class RTAsyncTask extends AsyncTask<String,String,String> {

        protected void onPreExecute(){}

        @Override
        protected String doInBackground(String... strings) {
            if(isCancelled())
                return (null);
            query();

            return null;
        }

        protected  void onPostExecute(String result){}


        protected  void onCancelled(){
            super.onCancelled();
        }
    }



    public void query() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("UPDATE Su_수급자기본정보 SET 수급자명='"+r_name+"',hp ='"+r_phone+"',등급='"+r_rating+"',생년월일='"+r_birth+"',주소1 = '"+r_adress+"',보호자성명 = '"+r_guardian+"',관계 = '"+r_relationship+"',생년월일1 ='"+r_gbirth+"',hp2='"+r_gnumber+"',주소11='"+r_gadress+"' WHERE 수급자명='"+name+"'");

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
            ResultSet resultSet = statement.executeQuery("select * from Su_수급자기본정보 where 수급자명='"+name+"'");


            while (resultSet.next()) {

                r_name = resultSet.getString(2);
               // r_name_insert.setText(r_name);
                r_name_insert.setText(r_name);

                r_phone = resultSet.getString(8);
                r_phone_insert.setText(r_phone);

                r_rating = resultSet.getString(4);
                r_rating_insert.setText(r_rating);

                r_birth = resultSet.getString(14);
                r_birth_insert.setText(r_birth);

                r_adress  = resultSet.getString(9);
                r_adress_insert.setText(r_adress);

                r_guardian = resultSet.getString(18);
                r_guardian_insert.setText(r_guardian);

                r_relationship = resultSet.getString(25);
                r_relationship_insert.setText(r_relationship);

                r_gbirth = resultSet.getString(22);
                r_gbirth_insert.setText(r_gbirth);

                r_gnumber = resultSet.getString(19);
                r_gnumber_insert.setText(r_gnumber);

                r_gadress = resultSet.getString(23);
                r_gadress_insert.setText(r_gadress);

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
            Log.w("Error connection", "" + e.getMessage());
        }

    }
    public void camerainsertquery() {
        Connection connection = null;
        Blob blob = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();
            // ResultSet resultSet = statement.executeQuery("INSERT INTO  Su_사진(이름,BLOBData)VALUES ('"+ymd1+"','"+responsibility+"','"+hms1+"',convert(VARBINARY(max),'"+s4+"'))");
            // ResultSet resultSet = statement.executeQuery("UPDATE Su_사진 SET BLOBData = convert(VARBINARY(max),'"+imageString+"') WHERE 이름 = '"+name+"'");
            //ResultSet resultSet = statement.executeQuery("UPDATE Su_사진 SET BLOBData = convert(VARBINARY(max),'"+blob+"') WHERE 이름 = '"+name+"'");
            // ResultSet resultSet = statement.executeQuery("INSERT INTO Su_사진(BLOBData) VALUES(?);");

            // PreparedStatement ps = connection.prepareStatement("INSERT INTO Su_배너이미지(BLOBData) VALUES (?)");
            PreparedStatement ps = connection.prepareStatement("UPDATE Su_사진 SET (BLOBData) VALUES (?) WHERE 이름 ='" + name + "'");
            //  ResultSet resultSet = statement.executeQuery("UPDATE Su_사진 SET BLOBData ='"+blob+"'WHERE 이름 = '"+name+"'");

            byte[] buf = imageBytes;
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


    public class caAsyncTask extends AsyncTask<String,String,String> {

        protected void onPreExecute(){}

        @Override
        protected String doInBackground(String... strings) {
            if(isCancelled())
                return (null);
            camerainsertquery();

            return null;
        }

        protected  void onPostExecute(String result){}

        protected  void onCancelled(){
            super.onCancelled();

        }
    }



    public class RAsyncTask extends AsyncTask<String,String,String> {

        protected void onPreExecute(){}

        @Override
        protected String doInBackground(String... strings) {
            if(isCancelled())
                return (null);
            selectquery();


            return null;
        }

        protected  void onPostExecute(String result){}


        protected  void onCancelled(){
            super.onCancelled();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_action,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(EditRecipientActivity.this,MenuMain.class);
                intent.putExtra("name",name);
                intent.putExtra("gender", gender);
                intent.putExtra("rating", rating);
                intent.putExtra("birth", birth);
                intent.putExtra("pastdisease", pastdisease);
                intent.putExtra("responsibility", responsibility);
                startActivity(intent);
                break;
            case R.id.action_notice:
                Intent intent1 = new Intent(EditRecipientActivity.this, CustomerServiceActivity.class);
                intent1.putExtra("name", name);
                intent1.putExtra("responsibility", responsibility);
                intent1.putExtra("rating", rating);
                startActivity(intent1);
                break;
            case R.id.action_serviceEdit:
                Intent i5 = new Intent(EditRecipientActivity.this, EditRecipientActivity.class);
                i5.putExtra("name", name);
                i5.putExtra("rating", rating);
                i5.putExtra("responsibility", responsibility);
                startActivity(i5);
                break;
            case R.id.action_sign:
                Intent i8 = new Intent(EditRecipientActivity.this, signActivity.class);
                i8.putExtra("name", name);
                i8.putExtra("rating", rating);
                i8.putExtra("responsibility", responsibility);
                startActivity(i8);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
