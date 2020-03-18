package com.jubumam.surefin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class signActivity extends AppCompatActivity {

    private AsyncTask<String, String, String> caTask;

    SignaturePad signaturePad;
    Button btnsave;
    Button btnclear;
    Button btnok;
    Button btnre;
    String signimage;


    private String name;        //이름
    private String gender;      //성별
    private String birth;       //생년원일
    private String rating;      //등급
    private String pastdisease;      //과거병력
    private String responsibility;      //직원명
    private String route;

    private Bitmap bitmap;
    private byte[] imageBytes;
    private ByteArrayOutputStream baos;
    private String startWork;
    private String stime;
    private String personId;
    private String recipiId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signature);

        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        recipiId = commuteRecipient.getRecipiId();
        name = commuteRecipient.getName();
        rating = commuteRecipient.getRating();
        responsibility = commuteRecipient.getResponsibility();
        stime = commuteRecipient.getStartTime();
        startWork = commuteRecipient.getStartWork();
        Intent intent = getIntent();
        route = intent.getExtras().getString("route");

        Login login = Login.getInstance();
        personId = login.getPersonId();


        signaturePad = (SignaturePad) findViewById(R.id.signaturePad);
        btnsave = (Button) findViewById(R.id.btnsave);
        btnclear = (Button) findViewById(R.id.btnclear);
        btnok = (Button) findViewById(R.id.btnok);
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

                Toast.makeText(signActivity.this, "저장 되었습니다.", Toast.LENGTH_SHORT).show();
                //Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                //Bitmap bitmap = (Bitmap)signaturePad.getSignatureBitmap();
                //signaturePad.setSignatureBitmap(bitmap);
                //signaturePad.setSignatureBitmap(bitmap);


                bitmap = signaturePad.getSignatureBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                imageBytes = baos.toByteArray();


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
     /*           Intent intent = new Intent(signActivity.this,MenuMain.class);
                intent.putExtra("name", name);
                intent.putExtra("gender", gender);
                intent.putExtra("rating", rating);
                intent.putExtra("birth", birth);
                intent.putExtra("pastdisease", pastdisease);
                intent.putExtra("responsibility", responsibility);
                startActivity(intent);*/
                finish();


            }
        });
    }

    private void BitmapToString(Bitmap bitmap) {
        baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);    //bitmap compress
        imageBytes = baos.toByteArray();
    }

    public void query() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            if (route.equals("MenuMain")) {
                PreparedStatement ps = connection.prepareStatement("update Su_직원출퇴근정보 set 요양사서명 = ? where 직원코드 ='" + personId + "' and 일자 = '" + startWork + "' and 출근시간 = '" + stime + "'");
                ps.setBytes(1, imageBytes);
                ps.executeUpdate();
                ps.close();
            } else {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO  Su_수급자서명정보(수급자명,일자,BLOBData,수급자코드)VALUES (?,?,?,?)");
                ps.setString(1, name);
                ps.setString(2, startWork);
                ps.setBytes(3, imageBytes);
                ps.setString(4, recipiId);
                ps.executeUpdate();
                ps.close();
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
            if (route.equals("MenuMain")) {
                finishAffinity();
                System.runFinalization();
                System.exit(0); //앱종료
            } else {
                startActivity(new Intent(signActivity.this, MenuMain.class));
            }
        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }


}
