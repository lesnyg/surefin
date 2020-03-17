package com.jubumam.surefin;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Client_Registration extends Activity {

    EditText c_name_insert;
    EditText c_phone_insert;
    EditText c_rating_insert;
    EditText c_birth_insert;
    EditText c_adress_insert;
    EditText c_guardian_insert;
    EditText c_relationship_insert;
    EditText c_gbirth_insert;
    EditText c_gnumber_insert;
    EditText c_gadress_insert;
    Button btn_save;
    Button btn_return;
    String c_name;
    String c_phone;
    String c_rating;
    String c_birth;
    String c_adress;
    String c_guardian;
    String c_relationship;
    String c_gbirth;
    String c_gnumber;
    String c_gadress;

    private AsyncTask<String, String, String> crTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_registration);

        c_name_insert = (EditText) findViewById(R.id.c_name_insert);
        c_phone_insert = (EditText) findViewById(R.id.c_phone_insert);
        c_rating_insert = (EditText) findViewById(R.id.c_rating_insert);
        c_birth_insert = (EditText) findViewById(R.id.c_birth_insert);
        c_adress_insert = (EditText) findViewById(R.id.c_adress_insert);
        c_guardian_insert = (EditText) findViewById(R.id.c_guardian_insert);
        c_relationship_insert = (EditText) findViewById(R.id.c_relationship_insert);
        c_gbirth_insert = (EditText) findViewById(R.id.c_gbirth_insert);
        c_gnumber_insert = (EditText) findViewById(R.id.c_gnumber_insert);
        c_gadress_insert = (EditText) findViewById(R.id.c_gadress_insert);
        btn_save = (Button) findViewById(R.id.btn_save);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_name = c_name_insert.getText().toString();
                c_phone = c_phone_insert.getText().toString();
                c_rating = c_rating_insert.getText().toString();
                c_birth = c_birth_insert.getText().toString();
                c_adress = c_adress_insert.getText().toString();
                c_guardian = c_guardian_insert.getText().toString();
                c_relationship = c_relationship_insert.getText().toString();
                c_gbirth = c_gbirth_insert.getText().toString();
                c_gnumber = c_gnumber_insert.getText().toString();
                c_gadress = c_gadress_insert.getText().toString();

                crTask = new CRAsyncTask().execute();
                Toast.makeText(getApplicationContext(), "입력완료", Toast.LENGTH_SHORT).show();
                //  Intent intent = new Intent(getApplicationContext(),.class);
                // startActivity(intent);
            }
        });


    }

    public class CRAsyncTask extends AsyncTask<String, String, String> {

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


    public void query() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("INSERT INTO Su_수급자기본정보(수급자명,hp,등급,생년월일,주소1,보호자성명,관계,생년월일1,hp2,주소11)VALUES ('" + c_name + "','" + c_phone + "','" + c_rating + "','" + c_birth + "','" + c_adress + "','" + c_guardian + "','" + c_relationship + "','" + c_gbirth + "','" + c_gnumber + "','" + c_gadress + "')");

            while (resultSet.next()) {

            }
            connection.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());

        }

    }
}
