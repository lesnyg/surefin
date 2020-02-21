package com.jubumam.SureFin.NokPackage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jubumam.SureFin.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

public class NokPaymentActivity extends AppCompatActivity {
    private String recipiName;
    private String recipiPhone;
    private AsyncTask<String, String, String> mTask;
    private String nokname;
    private String rating;
    private String kounggam;
    private String number;
    private String gongdanper;
    private int gongdanprice;
    private String individualper;
    private int individualprice;
    private String phoneNumber;
    private String gender;
    private String birth;
    private String pastdisease;
    private String responsibility;
    private String payway;
    private String payday;
    private String joinday;
    private String joinpayday;
    private String contractstart;
    private String contractend;
    private int limitfood;
    private int limitbed;

    private TextView tv_recipiname;
    private TextView tv_nokname;
    private TextView tv_rating;
    private TextView tv_kounggam;
    private TextView tv_number;
    private TextView tv_gongdanper;
    private TextView tv_gongdanprice;
    private TextView tv_individualper;
    private TextView tv_individualprice;
    private TextView tv_phoneNumber;
    private TextView tv_gender;
    private TextView tv_birth;
    private TextView tv_pastdisease;
    private TextView tv_responsibility;
    private TextView tv_meal;
    private TextView tv_liquidmeal;
    private TextView tv_snack;
    private TextView tv_onebedroom;
    private TextView tv_twobedroom;
    private TextView tv_beauty;
    private TextView tv_sum;
    private TextView tv_gongdansum;
    private TextView tv_payway;
    private TextView tv_payday;
    private TextView tv_joinday;
    private TextView tv_joinpayday;
    private TextView tv_totalsum;
    private TextView tv_contractstart;
    private TextView tv_contractend;
    private TextView tv_limitfood;
    private TextView tv_limitbed;
    private int meal;
    private int liquidmeal;
    private int snack;
    private int onebedroom;
    private int twobedroom;
    private int beauty;
    private int sum;
    private DecimalFormat moneyfm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nok_payment);

        Nok nok = Nok.getInstance();
        recipiName = nok.getRecipientName();
        recipiPhone = nok.getRecipientPhone();

        tv_recipiname = findViewById(R.id.tv_recipiname);
        tv_nokname = findViewById(R.id.tv_nokname);
        tv_gongdanper = findViewById(R.id.tv_gongdanper);
        tv_gongdanprice = findViewById(R.id.tv_gongdanprice);
        tv_individualper = findViewById(R.id.tv_individualper);
        tv_individualprice = findViewById(R.id.tv_individualprice);
//        tv_pastdisease = findViewById(R.id.tv_pastdisease);
        tv_meal = findViewById(R.id.tv_meal);
        tv_liquidmeal = findViewById(R.id.tv_liquidmeal);
        tv_snack = findViewById(R.id.tv_snack);
        tv_onebedroom = findViewById(R.id.tv_onebedroom);
        tv_twobedroom = findViewById(R.id.tv_twobedroom);
        tv_beauty = findViewById(R.id.tv_beauty);
        tv_sum = findViewById(R.id.tv_individualsum);
        tv_gongdansum = findViewById(R.id.tv_gongdansum);
//        tv_payway = findViewById(R.id.tv_payway);
//        tv_payday = findViewById(R.id.tv_payday);
//        tv_joinday = findViewById(R.id.tv_joinday);
//        tv_joinpayday = findViewById(R.id.tv_joinpayday);
        tv_totalsum = findViewById(R.id.tv_totalsum);
//        tv_contractstart = findViewById(R.id.tv_contractstart);
//        tv_contractend = findViewById(R.id.tv_contractend);
//        tv_limitfood = findViewById(R.id.tv_limitfood);
//        tv_limitbed = findViewById(R.id.tv_limitbed);

        mTask = new MyAsyncTask().execute();

        findViewById(R.id.btn_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NokPaymentActivity.this,SMPayWebSampleAutoActivity.class);
                intent.putExtra("totalPrice",sum);
                intent.putExtra("recipiName",recipiName);
                intent.putExtra("recipiPhone",recipiPhone);
                startActivity(intent);
            }
        });
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

    public void query() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Su_수급자기본정보 where 수급자명='" + recipiName + "' and  hp = '" + recipiPhone + "'");

            while (resultSet.next()) {
                nokname = resultSet.getString("보호자성명");
                rating = resultSet.getString("등급");
                kounggam = resultSet.getString("경감");
                number = resultSet.getString("인정번호1");
                gongdanper = resultSet.getString("공단부담비율");
                gongdanprice = resultSet.getInt("공단부담금액");
                individualper = resultSet.getString("개인부담비율");
                individualprice = resultSet.getInt("개인부담금");
                phoneNumber = resultSet.getString("hp");
                gender = resultSet.getString("성별");
                birth = resultSet.getString("생년월일");
                pastdisease = resultSet.getString("과거병력");
                responsibility = resultSet.getString("담당");
                meal = resultSet.getInt("식대");
                liquidmeal = resultSet.getInt("경관유동식");
                snack = resultSet.getInt("간식비");
                onebedroom = resultSet.getInt("침실1인");
                twobedroom = resultSet.getInt("침실2인");
                beauty = resultSet.getInt("이미용비");
                payway = resultSet.getString("납부방법");
                payday = resultSet.getString("매월납부일");
                joinday = resultSet.getString("최초입사일");
                joinpayday = resultSet.getString("입소당월납부일");
                contractstart = resultSet.getString("계약기간1");
                contractend = resultSet.getString("계약기간2");
                limitfood = resultSet.getInt("식재료월한도액");
                limitbed = resultSet.getInt("상급침실한도액");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    moneyfm = new DecimalFormat("###,###");
                    tv_recipiname.setText(recipiName + " ");
                    tv_nokname.setText(nokname + " ");
//                    tv_payway.setText(payway + " ");
//                    tv_payday.setText(payday + " ");
//                    tv_joinday.setText(joinday + " ");
//                    tv_joinpayday.setText(joinpayday + " ");
                    tv_gongdanper.setText(gongdanper + " %");
                    tv_gongdanprice.setText(moneyfm.format(gongdanprice) + " 원");
                    tv_gongdansum.setText(moneyfm.format(gongdanprice) + " 원");
                    tv_individualper.setText(individualper + "%");
                    tv_individualprice.setText(moneyfm.format(individualprice) + " 원");
//                    tv_pastdisease.setText(pastdisease);
                    tv_meal.setText(moneyfm.format(meal) + " 원");
                    tv_liquidmeal.setText(moneyfm.format(liquidmeal) + " 원");
                    tv_snack.setText(moneyfm.format(snack) + " 원");
                    tv_onebedroom.setText(moneyfm.format(onebedroom) + " 원");
                    tv_twobedroom.setText(moneyfm.format(twobedroom) + " 원");
                    tv_beauty.setText(moneyfm.format(beauty) + " 원");
                    sum = individualprice + meal + liquidmeal + snack + onebedroom + twobedroom + beauty;
                    tv_sum.setText(moneyfm.format(sum) + " 원");
                    tv_totalsum.setText(moneyfm.format(gongdanprice + sum) + " 원");
//                    tv_contractstart.setText(contractstart + " ~ ");
//                    tv_contractend.setText(contractend);
//                    tv_limitfood.setText(moneyfm.format(limitfood) + " 원");
//                    tv_limitbed.setText(moneyfm.format(limitbed) + " 원");
                }
            });
            connection.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());
        }
    }
}
