package com.jubumam.surefin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderCheckActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String phone;
    private String address;
    private int intcount = 1;
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
    private Button btn_count;
    String lastChar = "";
    private String responsibility;
    private String personId;
    private String department;
    private String recipiId;
    private String recipiName ="없음";
    private String rating;
    private String commute;
    private String recipiPhone ="없음";
    private String recipiAdd;
    private String firstOrder;

    private AsyncTask<String, String, String> recipiTask;
    private final static int RESULT_NUM = 100;
    private Spinner spinner_ordermethod;
    private Spinner spinner_calMethod;
    private String date;
    private String mealTime;
    private AsyncTask<String, String, String> orderSyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_check);

        Login login = Login.getInstance();
        //직원이름
        responsibility = login.getResponsibility();
        //직원코드
        personId = login.getPersonId();
        //직원부서
        department = login.getDepartment();

        CommuteRecipient commuteRecipient = CommuteRecipient.getInstance();
        //수급자코드
        recipiId = commuteRecipient.getRecipiId();
        recipiPhone = commuteRecipient.getPhoneNumber();
        recipiName = commuteRecipient.getName();
        rating = commuteRecipient.getRating();
        commute = commuteRecipient.getCommute();

        Intent intent = getIntent();
        date = intent.getExtras().getString("date");
        mealTime = intent.getExtras().getString("mealTime");

        today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        tv_date = findViewById(R.id.tv_date);
        et_phone = findViewById(R.id.et_phone);
        et_address = findViewById(R.id.et_address);
        et_count = findViewById(R.id.et_count);
        et_calMethod = findViewById(R.id.et_calMethod);
        et_orderMethod = findViewById(R.id.et_orderMethod);
        et_totalPrice = findViewById(R.id.et_totalPrice);
        et_count.setText(intcount+"");


        spinner_calMethod = findViewById(R.id.spinner_calMethod);
        spinner_ordermethod = findViewById(R.id.spinner_ordermethod);


        tv_date.setText(today+"");

        if(commute!=null && commute.equals("true")){
            recipiTask = new RecipiTask().execute();

        }


        item_calMethod = new String[]{"카드","현금"};
        item_ordermethod = new String[]{"일반","요양"};



        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,item_calMethod);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,item_ordermethod);


        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner_calMethod.setAdapter(adapter2);
        spinner_ordermethod.setAdapter(adapter3);


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

        findViewById(R.id.btn_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intcount = Integer.parseInt(et_count.getText().toString());
                intcount++;
                et_count.setText(intcount+"");
            }
        });


        findViewById(R.id.btn_order).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                phone = et_phone.getText().toString();
                address = et_address.getText().toString();
                count = et_count.getText().toString();
                calMethod = et_calMethod.getText().toString();
                orderMethod = et_orderMethod.getText().toString();
                totalPrice = Integer.parseInt(count)*7500;
                et_totalPrice.setText(totalPrice+"");
                time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                Intent intent1 = new Intent(OrderCheckActivity.this,OrderSignActivity.class);
                intent1.putExtra("date",date);
                intent1.putExtra("mealTime",mealTime);
                intent1.putExtra("count",count);
                intent1.putExtra("calmethod",calMethod);
                intent1.putExtra("totalPrice",totalPrice+"");
                startActivity(intent1);

            }
        });



        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int digits = et_phone.getText().toString().length();
                if (digits > 1)
                    lastChar = et_phone.getText().toString().substring(digits - 1);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int digits = et_phone.getText().toString().length();
                Log.d("LENGTH", "" + digits);
                if (!lastChar.equals("-")) {
                    if (digits == 3 || digits == 8) {
                        et_phone.append("-");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_NUM:

                break;
        }
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

            ResultSet areaRS = statement.executeQuery("insert into Su_주문리스트(일자,주문자,전화번호,주소,수량,계산방법,주문형태,금액,주문시간,배달확인,식사시간,수급자코드,첫주문) " +
                    "values('"+date+"','"+recipiName+"','"+phone+"','"+address+"','"+count+"','"+calMethod+"','"+orderMethod+"','"+totalPrice+"','"+time+"','진행중','"+mealTime+"','"+recipiId+"','"+firstOrder+"') ");
           connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public class RecipiTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return (null);
            recipiQuery();
            return null;
        }

        protected void onPostExecute(String result) {
            et_phone.setText(recipiPhone);
            et_address.setText(recipiAdd);
            spinner_ordermethod.setSelection(1);
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
            ResultSet firstRS = statement.executeQuery("select 첫주문 from Su_주문리스트 where 수급자코드 = '" +recipiId+"'");
            while (firstRS.next()){
                firstOrder = firstRS.getString("첫주문");
            }
            if(firstOrder==null){
                firstOrder = "True";
            }else if(firstOrder != null && firstOrder.equals("True")){
                firstOrder = "False";
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
