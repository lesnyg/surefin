package com.jubumam.surefin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NursingCheckActivity extends AppCompatActivity {

    private CheckBox cb_breath1;
    private CheckBox cb_breath2;
    private CheckBox cb_breath3;
    private CheckBox cb_breath4;
    private CheckBox cb_skin1;
    private CheckBox cb_skin2;
    private CheckBox cb_skin3;
    private CheckBox cb_skin4;
    private CheckBox cb_skin5;
    private CheckBox cb_ache1;
    private CheckBox cb_excretion1;
    private CheckBox cb_excretion2;
    private CheckBox cb_excretion3;
    private CheckBox cb_excretion4;
    private CheckBox cb_excretion5;
    private CheckBox cb_excretion6;
    private CheckBox cb_etc1;
    private CheckBox cb_etc2;
    private CheckBox cb_etc3;
    private CheckBox cb_etc4;
    private EditText et_etc;
    private EditText et_treatment;
    private EditText et_meal;
    private EditText et_excretion;
    private EditText et_etc2;
    private Button btn_send;

    private String breath1;         //흡입실시
    private String breath2;         //가습기
    private String breath3;         //네이브라이저
    private String breath4;         //산소공급
    private String skin1;           //외상처치
    private String skin2;           //붕대교환
    private String skin3;           //연고바르기
    private String skin4;           //욕창간호
    private String skin5;           //양용제공
    private String ache1;           //온냉습포제공
    private String excretion1;          //방광훈련
    private String excretion2;          //유치도뇨
    private String excretion3;          //단순도뇨
    private String excretion4;          //Figerevacustion
    private String excretion5;          //관장
    private String excretion6;          //장루간호
    private String etc1;            //복막투석
    private String etc2;            //기관절개간호
    private String etc3;            //위독시간호
    private String etc4;            //기타
    private String etc;             //기타내용
    private String treatment;      //상세처치내용
    private String meal;            //식사
    private String excretion;       //배설
    private String etetc2;          //기타2

    private AsyncTask<String,String,String> NursingCheckSyncTask;
    private String strToday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursing_check);

        cb_breath1 = findViewById(R.id.cb_breath1);
        cb_breath2 = findViewById(R.id.cb_breath2);
        cb_breath3 = findViewById(R.id.cb_breath3);
        cb_breath4 = findViewById(R.id.cb_breath4);
        cb_skin1 = findViewById(R.id.cb_skin1);
        cb_skin2 = findViewById(R.id.cb_skin2);
        cb_skin3 = findViewById(R.id.cb_skin3);
        cb_skin4 = findViewById(R.id.cb_skin4);
        cb_skin5 = findViewById(R.id.cb_skin5);
        cb_ache1 = findViewById(R.id.cb_ache1);
        cb_excretion1 = findViewById(R.id.cb_excretion1);
        cb_excretion2 = findViewById(R.id.cb_excretion2);
        cb_excretion3 = findViewById(R.id.cb_excretion3);
        cb_excretion4 = findViewById(R.id.cb_excretion4);
        cb_excretion5 = findViewById(R.id.cb_excretion5);
        cb_excretion6 = findViewById(R.id.cb_excretion6);
        cb_etc1 = findViewById(R.id.cb_etc1);
        cb_etc2 = findViewById(R.id.cb_etc2);
        cb_etc3 = findViewById(R.id.cb_etc3);
        cb_etc4 = findViewById(R.id.cb_etc4);
        et_etc = findViewById(R.id.et_etc);
        et_treatment = findViewById(R.id.et_treatment);
        et_meal = findViewById(R.id.et_meal);
        et_excretion = findViewById(R.id.et_excretion);
        et_etc2 = findViewById(R.id.et_etc2);

        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_breath1.isChecked()){ breath1 = "True";}else {breath1 = "False";}
                if(cb_breath2.isChecked()){ breath2 = "True";}else {breath2 = "False";}
                if(cb_breath3.isChecked()){ breath3 = "True";}else {breath3 = "False";}
                if(cb_breath4.isChecked()){ breath4 = "True";}else {breath4 = "False";}
                if(cb_skin1.isChecked()){ skin1 = "True";}else {skin1 = "False";}
                if(cb_skin2.isChecked()){ skin2 = "True";}else {skin2 = "False";}
                if(cb_skin3.isChecked()){ skin3 = "True";}else {skin3 = "False";}
                if(cb_skin4.isChecked()){ skin4 = "True";}else {skin4 = "False";}
                if(cb_skin5.isChecked()){ skin5 = "True";}else {skin5 = "False";}
                if(cb_ache1.isChecked()){ ache1 = "True";}else {ache1 = "False";}
                if(cb_excretion1.isChecked()){ excretion1 = "True";}else {excretion1 = "False";}
                if(cb_excretion2.isChecked()){ excretion2 = "True";}else {excretion2 = "False";}
                if(cb_excretion3.isChecked()){ excretion3 = "True";}else {excretion3 = "False";}
                if(cb_excretion4.isChecked()){ excretion4 = "True";}else {excretion4 = "False";}
                if(cb_excretion5.isChecked()){ excretion5 = "True";}else {excretion5 = "False";}
                if(cb_excretion6.isChecked()){ excretion6 = "True";}else {excretion6 = "False";}
                if(cb_etc1.isChecked()){ etc1 = "True";}else { etc1 = "False";}
                if(cb_etc2.isChecked()){ etc2 = "True";}else { etc2 = "False";}
                if(cb_etc3.isChecked()){ etc3 = "True";}else { etc3 = "False";}
                if(cb_etc4.isChecked()){ etc4 = "True";}else { etc4 = "False";}

                etc = et_etc.getText().toString();
                treatment = et_treatment.getText().toString();
                meal = et_meal.getText().toString();
                excretion = et_excretion.getText().toString();
                etetc2 = et_etc2.getText().toString();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                Date today = new Date();
                strToday = formatter.format(today);

                NursingCheckSyncTask = new NursingCheckSyncTask().execute();
            }
        });



    }

    public class NursingCheckSyncTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;
            nursingCheckQuery();

            return null;

        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }

    public void nursingCheckQuery() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();



            String name = "홍길동";            //수급자 이름
            String gender = "남";            //수급자 성별
            String rating = "1등급";          //수급자 등급
            String pastdisease = "무";       //수급자 과거병력
            String room = "101";            //수급자 병실
            String birth = "1980-08-11";    //수급자 생일
            String manager = "김철수";     //담당
            String division = "방문";        //수급자 구분
            String place = "지점";            //수급자 지점
            String baseTime = "90분";        //수급자 기본시간

            ResultSet resultSet = statement.executeQuery("insert into Su_간호처치정보(일자,수급자명,성별,등급,주요질환,병실,생년월일,담당,구분,기본시간,지점," +
                    "흡입실시,가습기,네이브라이저,산소공급,외상처치,봉대교환,연고바르기,욕창간호,약욕제공,온냉습포제공," +
                    "방광훈련,유치도뇨,단순도뇨,Fingerevacustion,관장,장루간호," +
                    "복막투석,기관절개간호,위독시간호,기타,기타내용,상세처지내용,식사,배설,기타1) " +
                    "values('"+strToday+"','" + name + "','"+gender+"','"+rating+"','"+pastdisease+"','"+room+"','"+birth+"','" + manager + "','"+division+"','"+baseTime+"','"+place+"'," +
                    "'" + breath1 + "','" + breath2 + "','" + breath3 + "','" + breath4 + "','" + skin1 + "','" + skin2 + "','" + skin3 + "','" + skin4 + "','" + skin5 + "','" + ache1 + "'," +
                    "'" + excretion1 + "','" + excretion2 + "','" + excretion3 + "','" + excretion4 + "','" + excretion5 + "','" + excretion6 + "'," +
                    "'" + etc1 + "','" + etc2 + "','" + etc3 + "','" + etc4 + "','"+etc+"','"+treatment+"','" + meal + "','" + excretion + "','" + etetc2 + "')");
            while (resultSet.next()) {
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
