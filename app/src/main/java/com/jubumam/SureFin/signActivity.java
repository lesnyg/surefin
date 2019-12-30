package com.jubumam.SureFin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class signActivity extends AppCompatActivity {

    private AsyncTask<String,String,String> caTask;

    SignaturePad signaturePad;
    Button btnsave;
    Button btnclear;
    Button btnok;
    Button btnre;
    String signimage;
    byte[] im;

    private String name;        //이름
    private String gender;      //성별
    private String birth;       //생년원일
    private String rating;      //등급
    private String pastdisease;      //과거병력
    private String responsibility;      //직원명






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signature);



        final Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        gender = intent.getExtras().getString("gender");
        rating = intent.getExtras().getString("rating");
        birth = intent.getExtras().getString("birth");
        pastdisease = intent.getExtras().getString("pastdisease");
        responsibility = intent.getExtras().getString("responsibility");


        signaturePad = (SignaturePad)findViewById(R.id.signaturePad);
        btnsave = (Button)findViewById(R.id.btnsave);
        btnclear = (Button)findViewById(R.id.btnclear);
        btnok = (Button)findViewById(R.id.btnok);
      //  btnre = (Button)findViewById(R.id.btnre);

        btnsave.setEnabled(false);
        btnclear.setEnabled(false);
        btnok.setEnabled(false);
//        btnre.setEnabled(false);




        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                btnsave.setEnabled(true);
                btnclear.setEnabled(true);
                btnok.setEnabled(true);
              //  btnre.setEnabled(true);
            }

            @Override
            public void onClear() {

                btnsave.setEnabled(false);
                btnclear.setEnabled(false);
                btnok.setEnabled(false);
              //  btnre.setEnabled(false);

            }

        });
/*
        btnre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });*/

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(signActivity.this, "저장 되었습니다.", Toast.LENGTH_SHORT).show();
                //Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                //Bitmap bitmap = (Bitmap)signaturePad.getSignatureBitmap();
                //signaturePad.setSignatureBitmap(bitmap);
                //signaturePad.setSignatureBitmap(bitmap);


                Bitmap bitmap = signaturePad.getSignatureBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                signimage = Base64.encodeToString(imageBytes, Base64.DEFAULT);


                im = imageBytes;
                caTask = new caAsyncTask().execute();

              //  Toast.makeText(signActivity.this,signimage,Toast.LENGTH_SHORT).show();
            }
        });

        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signActivity.this,MenuMain.class);
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

    public void query() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("INSERT INTO  Su_수급자서명정보(수급자명,BLOBData)VALUES('슈어핀',convert(VARBINARY(max),'"+signimage+"'))");


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
            query();


            return null;
        }

        protected  void onPostExecute(String result){}

        protected  void onCancelled(){
            super.onCancelled();
        }
    }


}
