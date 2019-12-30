package com.jubumam.SureFin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VisitingMonthActivity extends AppCompatActivity {

    private NumberPicker picker1;
    private TextView tv_result1;
    private TextView tv_result2;
    private TextView tv_result3;
    private TextView tv_result4;

    private String bodyMon;
    private String cognitiveMon;
    private String emotionalMon;
    private String lifeMon;
    private int monBody = 0;

    private AsyncTask<String, String, String> mTask;
    private ResultSet resultSet;
    private Connection connection;
    private NumberPicker picker2;
    private String startMon;
    private String endMon;
    private long diff1 = 0;
    private long diff2 = 0;
    private long diff3 = 0;
    private long diff4 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visiting_month);


        tv_result1 = findViewById(R.id.tv_result1);
        tv_result2 = findViewById(R.id.tv_result2);
        tv_result3 = findViewById(R.id.tv_result3);
        tv_result4 = findViewById(R.id.tv_result4);

        picker1 = (NumberPicker) findViewById(R.id.picker1);
        picker1.setMinValue(2019);
        picker1.setMaxValue(2030);
        picker1.setWrapSelectorWheel(false);

        picker2 = (NumberPicker) findViewById(R.id.picker2);
        picker2.setMinValue(0);
        picker2.setMaxValue(12);


        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                diff1 = 0;
                diff2 = 0;
                diff3 = 0;
                diff4 = 0;
            }
        });
        picker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                diff1 = 0;
                diff2 = 0;
                diff3 = 0;
                diff4 = 0;
            }
        });

        findViewById(R.id.btn_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tv_result.setText(picker1.getValue() + " 년 " + picker2.getValue() + " 월 ");
                startMon = picker1.getValue() + "-" + picker2.getValue() + "-" + "1";
                endMon = picker1.getValue() + "-" + picker2.getValue() + "-" + "32";
                mTask = new MySyncTask().execute();
            }
        });


    }

    public class MySyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled()) {
                return null;
            }
            visitingMonthQuery();
            return null;
        }
    }

    private void visitingMonthQuery() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();

            String name = "홍길동";
            resultSet = statement.executeQuery("select * from Su_방문요양급여정보 where 수급자명 = '"+name+"' and (일자 between '" + startMon + "' and '" + endMon + "')");

//            SSQL = "select * from Hy_입출력정보 where (일자 between '" + Date1.Text + "'" + "and'" + Date2.Text + "')" + "and 입고처 LIKE '%" + s1.Text + "%'and 입출구분='자재입고'" + "ORDER BY 일자"
            final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
            while (resultSet.next()) {

                bodyMon = resultSet.getString("신체사용시간계");
                cognitiveMon = resultSet.getString("인지사용시간계");
                emotionalMon = resultSet.getString("정서사용시간계");
                lifeMon = resultSet.getString("생활지원사용시간계");

                Date d1= format.parse(bodyMon);
                diff1 = d1.getTime()+diff1;
                Date d2 = format.parse(cognitiveMon);
                diff2 = d2.getTime()+diff2;
                Date d3 = format.parse(emotionalMon);
                diff3 = d3.getTime()+diff3;
                Date d4 = format.parse(lifeMon);
                diff4 = d4.getTime()+diff4;
//                DecimalFormat myFormatter = new DecimalFormat("###,###");
//                formattedGongPrice = myFormatter.format(gongPrice);
//                formattedindividualPrice = myFormatter.format(indiviPrice);
//                formattedsum = myFormatter.format(sum);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        tv_result1.setText(format.format(diff1)+"");
                        tv_result2.setText(format.format(diff2)+"");
                        tv_result3.setText(format.format(diff3)+"");
                        tv_result4.setText(format.format(diff4)+"");
                    }
                });
            }


            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
