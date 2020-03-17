package com.jubumam.surefin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderCheckActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String phone;
    private String address;
    private String count;
    private String calMethod;
    private String local;
    private String orderMethod;
    private int totalPrice;
    private String today;
    private String time;
    private TextView et_totalPrice;
    private EditText et_phone;
    private EditText et_address;
    private EditText et_count;
    private EditText et_calMethod;
    private EditText et_local;
    private EditText et_orderMethod;
    private String[] item_count;
    private String[] item_calMethod;
    private String[] item_ordermethod;
    private String[] item_local;
    private TextView tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        tv_date = findViewById(R.id.tv_date);
        et_phone = findViewById(R.id.et_phone);
        et_address = findViewById(R.id.et_address);
        et_count = findViewById(R.id.et_count);
        et_calMethod = findViewById(R.id.et_calMethod);
        et_local = findViewById(R.id.et_local);
        et_orderMethod = findViewById(R.id.et_orderMethod);
        et_totalPrice = findViewById(R.id.et_totalPrice);
        Spinner spinner_count =findViewById(R.id.spinner_count);
        Spinner spinner_calMethod =findViewById(R.id.spinner_calMethod);
        Spinner spinner_ordermethod =findViewById(R.id.spinner_ordermethod);
        Spinner spinner_local =findViewById(R.id.spinner_local);

        tv_date.setText(today);

        item_count = new String[]{"1","2","3","4","5","6","7","8","9","10"};
        item_calMethod = new String[]{"카드","현금"};
        item_ordermethod = new String[]{"일반","요양"};
        item_local = new String[]{"서초","도곡"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,item_count);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,item_calMethod);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,item_ordermethod);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,item_local);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_count.setAdapter(adapter);
        spinner_calMethod.setAdapter(adapter2);
        spinner_ordermethod.setAdapter(adapter3);
        spinner_local.setAdapter(adapter4);

        spinner_count.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_count.setText(item_count[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_calMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_calMethod.setText(item_calMethod[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_ordermethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_orderMethod.setText(item_ordermethod[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_local.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_local.setText(item_local[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.btn_order).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                phone = et_phone.getText().toString();
                address = et_address.getText().toString();
                count = et_count.getText().toString();
                calMethod = et_calMethod.getText().toString();
                local = et_local.getText().toString();
                orderMethod = et_orderMethod.getText().toString();
                totalPrice = Integer.parseInt(count)*7500;
                et_totalPrice.setText(totalPrice+"");
                time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                new OrderSyncTask().execute();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }




    public class OrderSyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;

            orderQuery();
            return null;

        }

        protected void onPostExecute(String result) {


        }
    }

    private void orderQuery() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();

            ResultSet areaRS = statement.executeQuery("insert into Su_주문리스트(일자,전화번호,주소,수량,계산방법,지역,주문형태,금액,주문시간,배달확인) " +
                    "values('"+today+"','"+phone+"','"+address+"','"+count+"','"+calMethod+"','"+local+"','"+orderMethod+"','"+totalPrice+"','"+time+"','진행중') ");
           connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
