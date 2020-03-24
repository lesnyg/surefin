package com.jubumam.surefin.ProtectorPackage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jubumam.surefin.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProtectorLoginActivity extends AppCompatActivity {
    private EditText txt_centerName;
    private EditText txt_recipiName;
    private EditText txt_recipiPhone;
    private String centerName;
    private String dbCenterName;
    private String recipiId;
    private String recipiName;
    private String recipiPhone;
    private String rating;
    private String lastChar = "";
    private AsyncTask<String, String, String> mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protector_login);
        txt_centerName = findViewById(R.id.txt_centerName);
        txt_recipiName = findViewById(R.id.txt_recipiName);
        txt_recipiPhone = findViewById(R.id.txt_recipiPhone);
        txt_recipiPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int digits = txt_recipiPhone.getText().toString().length();
                if (digits > 1)
                    lastChar = txt_recipiPhone.getText().toString().substring(digits - 1);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int digits = txt_recipiPhone.getText().toString().length();
                Log.d("LENGTH", "" + digits);
                if (!lastChar.equals("-")) {
                    if (digits == 3 || digits == 8) {
                        txt_recipiPhone.append("-");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerName = txt_centerName.getText().toString() + "동";
                recipiName = txt_recipiName.getText().toString();
                recipiPhone = txt_recipiPhone.getText().toString();

                mTask = new MyAsyncTask().execute();
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
            if (centerName.equals(dbCenterName)) {
                new Protector(recipiId, recipiName, recipiPhone, centerName, rating);
                Intent intent = new Intent(ProtectorLoginActivity.this, ProtectorMainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(ProtectorLoginActivity.this, "센터에 문의하세요", Toast.LENGTH_SHORT).show();
            }
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
                recipiId = resultSet.getString("수급자코드");
                dbCenterName = resultSet.getString("지점");
                rating = resultSet.getString("등급");

            }
            connection.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());
        }
    }
}


