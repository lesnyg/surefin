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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;


public class OrderSignActivity extends AppCompatActivity {

    private AsyncTask<String, String, String> caTask;
    private AsyncTask<String, String, String> recipiTask;

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
    private String date;
    private String mealTime;
    private String recipiPhone;
    private String recipiAdd;
    private String currentTime;
    private String firstOrder;
    private String count;
    private String calmethod;
    private String totalPrice;

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
        date = intent.getExtras().getString("date");
        mealTime = intent.getExtras().getString("mealTime");
        count = intent.getExtras().getString("count");
        calmethod = intent.getExtras().getString("calmethod");
        totalPrice = intent.getExtras().getString("totalPrice");


        Login login = Login.getInstance();
        personId = login.getPersonId();


        recipiTask = new RecipiTask().execute();

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

                Toast.makeText(OrderSignActivity.this, "저장 되었습니다.", Toast.LENGTH_SHORT).show();
                //Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                //Bitmap bitmap = (Bitmap)signaturePad.getSignatureBitmap();
                //signaturePad.setSignatureBitmap(bitmap);
                //signaturePad.setSignatureBitmap(bitmap);


                bitmap = signaturePad.getSignatureBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                imageBytes = baos.toByteArray();

                currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());


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

    public class RecipiTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return (null);
            recipiQuery();


            return null;
        }
    }

    private void recipiQuery() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            ResultSet recipiRS = statement.executeQuery("select hp,주소2 from Su_수급자기본정보 WHERE 수급자코드 = '" + recipiId + "'");
            while (recipiRS.next()) {
                recipiPhone = recipiRS.getString("hp");
                recipiAdd = recipiRS.getString("주소2");
            }
            ResultSet firstRS = statement.executeQuery("select 첫주문 from Su_주문리스트 where 수급자코드 = '" + recipiId + "'");
            while (firstRS.next()) {
                firstOrder = firstRS.getString("첫주문");
            }
            if (firstOrder == null) {
                firstOrder = "True";
            } else if (firstOrder != null && firstOrder.equals("True")) {
                firstOrder = "False";
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

            PreparedStatement ps = connection.prepareStatement("INSERT INTO  Su_주문리스트(일자,전화번호,주소,배달확인,주문자,주문시간,지역,주문형태,첫주문,수급자코드,식사시간,주문사인,수량,계산방법,금액)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, date);
            ps.setString(2, recipiPhone);
            ps.setString(3, recipiAdd);
            ps.setString(4, "미배달");
            ps.setString(5, name);
            ps.setString(6, currentTime);
            ps.setString(7, "서초");
            ps.setString(8, "요양");
            ps.setString(9, firstOrder);
            ps.setString(10, recipiId);
            ps.setString(11, mealTime);
            ps.setBytes(12, imageBytes);
            ps.setString(13, count);
            ps.setString(14, calmethod);
            ps.setString(15, totalPrice);
            ps.executeUpdate();
            ps.close();

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


}
