package com.jubumam.surefin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class LoginActivity extends Activity {

    Button btn1;
    EditText txthp;
    String hp, name, hp1, pass1;
    String PhoneNum;
    private AsyncTask<String, String, String> mTask;
    String lastChar = "";
    private String pass;
    private EditText txt_pass;
    private String personId;
    private String responsibility;
    private String responsibilityID;
    private String department;
    private CheckBox ck_bodyactiv1;
    private SharedPreferences loginInformation;
    private SharedPreferences.Editor editor;


    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
/*
        try{
            TelephonyManager telManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            PhoneNum = telManager.getLine1Number();
            if(PhoneNum.startsWith("+82")){
                PhoneNum = PhoneNum.replace("+82", "0");
            }

        }catch (SecurityException e){

        }



        txthp.setText(PhoneNum);


*/

        txthp = (EditText) findViewById(R.id.txt_hp);
        txt_pass = (EditText) findViewById(R.id.txt_pass);
        btn1 = (Button) findViewById(R.id.btn1);
        ck_bodyactiv1 = findViewById(R.id.ck_bodyactiv1);

        loginInformation = getSharedPreferences("autoLogin",0);
        editor = loginInformation.edit();

        if(loginInformation.getString("hp","")!=null) {
            txthp.setText(loginInformation.getString("hp", ""));
            txt_pass.setText(loginInformation.getString("password", ""));

        }

        txthp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int digits = txthp.getText().toString().length();
                if (digits > 1)
                    lastChar = txthp.getText().toString().substring(digits - 1);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int digits = txthp.getText().toString().length();
                Log.d("LENGTH", "" + digits);
                if (!lastChar.equals("-")) {
                    if (digits == 3 || digits == 8) {
                        txthp.append("-");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                hp = txthp.getText().toString();
                pass = txt_pass.getText().toString();

                if(ck_bodyactiv1.isChecked()){

                    editor.putString("hp",hp);
                    editor.putString("password",pass);
                    editor.commit();
                }
                query();

                mTask = new MyAsyncTask().execute();

                // Toast.makeText(MainActivity.this,no,Toast.LENGTH_SHORT).show();


                   /*    try{

                        if (name.equals(id)&&pass.equals(wd)){
                            Intent intent = new Intent(getApplicationContext(), Main.class);
                            startActivity(intent);
                            finish();
                        }else if (name.equals(id)&&pass !=wd){

                        }else if (name != id&&pass.equals(wd)){

                        }

                    }catch(Exception e){


                    }*/

                try {
                    if (hp.equals(hp1)) {
                        new Login(personId, hp, responsibility, department);
                        Intent intent = new Intent(getApplicationContext(), MenuMain.class);
                        intent.putExtra("caregiverPhone", hp);
                        intent.putExtra("responsibility", name);
                        intent.putExtra("route", "LoginActivity");
                        startActivity(intent);
                        finish();
                    } else if (hp == null) {
                        Toast.makeText(getApplicationContext(), "?????????????????? ???????????????.", Toast.LENGTH_SHORT).show();
                    } else if (pass == null) {
                        Toast.makeText(getApplicationContext(), "??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
                    } else if (pass != pass1) {
                        Toast.makeText(getApplicationContext(), "??????????????? ?????? ?????????????????????.", Toast.LENGTH_SHORT).show();


                    }
                } catch (Exception e) {


                }

            }
        });


    }


    public void query() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Su_?????????????????? where hp='" + hp + "' and  ???????????? = '" + pass + "'");

            while (resultSet.next()) {

                name = resultSet.getString("?????????");
                hp1 = resultSet.getString("hp");
                pass1 = resultSet.getString("????????????");
                personId = resultSet.getString("????????????");
                responsibility = resultSet.getString("?????????");
                responsibilityID = resultSet.getString("?????????");
                department = resultSet.getString("????????????");



                // id = resultSet.getString(2);
                //wd = resultSet.getString(3);
                // no = resultSet.getString(5);


            }


            connection.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());


        }


    }

    public class MyAsyncTask extends AsyncTask<String, String, String> {

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