package com.jubumam.surefin.ProtectorPackage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jubumam.surefin.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

public class ProtectorPaymentActivity extends ProtectorBaseActivity {
    private String recipiId;
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
        setContentView(R.layout.activity_protector_payment);

        activateToolbar();

        Protector protector = Protector.getInstance();
        recipiId = protector.getRecipiId();
        recipiName = protector.getRecipientName();
        recipiPhone = protector.getRecipientPhone();

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
                Intent intent = new Intent(ProtectorPaymentActivity.this,SMPayWebSampleAutoActivity.class);
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
            ResultSet resultSet = statement.executeQuery("select * from Su_????????????????????? where ??????????????? = '" + recipiId + "'");


            while (resultSet.next()) {
                nokname = resultSet.getString("???????????????");
                rating = resultSet.getString("??????");
                kounggam = resultSet.getString("??????");
                number = resultSet.getString("????????????1");
                gongdanper = resultSet.getString("??????????????????");
                gongdanprice = resultSet.getInt("??????????????????");
                individualper = resultSet.getString("??????????????????");
                individualprice = resultSet.getInt("???????????????");
                phoneNumber = resultSet.getString("hp");
                gender = resultSet.getString("??????");
                birth = resultSet.getString("????????????");
                pastdisease = resultSet.getString("????????????");
                responsibility = resultSet.getString("??????");
                meal = resultSet.getInt("??????");
                liquidmeal = resultSet.getInt("???????????????");
                snack = resultSet.getInt("?????????");
                onebedroom = resultSet.getInt("??????1???");
                twobedroom = resultSet.getInt("??????2???");
                beauty = resultSet.getInt("????????????");
                payway = resultSet.getString("????????????");
                payday = resultSet.getString("???????????????");
                joinday = resultSet.getString("???????????????");
                joinpayday = resultSet.getString("?????????????????????");
                contractstart = resultSet.getString("????????????1");
                contractend = resultSet.getString("????????????2");
                limitfood = resultSet.getInt("?????????????????????");
                limitbed = resultSet.getInt("?????????????????????");
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
                    tv_gongdanprice.setText(moneyfm.format(gongdanprice) + " ???");
                    tv_gongdansum.setText(moneyfm.format(gongdanprice) + " ???");
                    tv_individualper.setText(individualper + "%");
                    tv_individualprice.setText(moneyfm.format(individualprice) + " ???");
//                    tv_pastdisease.setText(pastdisease);
                    tv_meal.setText(moneyfm.format(meal) + " ???");
                    tv_liquidmeal.setText(moneyfm.format(liquidmeal) + " ???");
                    tv_snack.setText(moneyfm.format(snack) + " ???");
                    tv_onebedroom.setText(moneyfm.format(onebedroom) + " ???");
                    tv_twobedroom.setText(moneyfm.format(twobedroom) + " ???");
                    tv_beauty.setText(moneyfm.format(beauty) + " ???");
                    sum = individualprice + meal + liquidmeal + snack + onebedroom + twobedroom + beauty;
                    tv_sum.setText(moneyfm.format(sum) + " ???");
                    tv_totalsum.setText(moneyfm.format(gongdanprice + sum) + " ???");
//                    tv_contractstart.setText(contractstart + " ~ ");
//                    tv_contractend.setText(contractend);
//                    tv_limitfood.setText(moneyfm.format(limitfood) + " ???");
//                    tv_limitbed.setText(moneyfm.format(limitbed) + " ???");
                }
            });
            connection.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());
        }
    }
}
