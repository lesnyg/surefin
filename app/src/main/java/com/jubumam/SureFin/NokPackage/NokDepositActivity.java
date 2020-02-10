package com.jubumam.SureFin.NokPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;

import com.jubumam.SureFin.R;
import com.jubumam.SureFin.Recipient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NokDepositActivity extends AppCompatActivity {
    private AsyncTask<String, String, String> mTask;
    private String name;        //이름
    private String rating;      //등급
    private int settlement;      //정산금
    private int charge;      //청구금
    private int deposit;      //입금액
    private int balance;      //잔액
    private int totalbal = 0;       //총 잔액

    private TextView tv_settlement;
    private TextView tv_charge;
    private TextView tv_deposit;
    private TextView tv_balance;
    private String date;

    private List<Deposit> list;
    private DepositAdapter mAdapter;
    private RecyclerView recyclerView;
    private TextView tv_totalbal;
    private DecimalFormat moneyfm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nok_deposit);

        Nok nok = Nok.getInstance();
        name = nok.getRecipientName();
        rating = nok.getRating();

        recyclerView = findViewById(R.id.recyclerview_nok);
        tv_totalbal = findViewById(R.id.tv_totalbalance);
        moneyfm = new DecimalFormat("###,###");
        mTask = new MyAsyncTask().execute();

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
            tv_totalbal.setText(moneyfm.format(totalbal));
        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }

    private void query() {
        Connection connection = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Su_본인부담금정산 where 수급자명='" + name + "'");
            list = new ArrayList<>();
            while (resultSet.next()) {
                date = resultSet.getString("일자");
                settlement = resultSet.getInt("정산금");
                charge = resultSet.getInt("청구금");
                deposit = resultSet.getInt("입금액");
                balance = resultSet.getInt("잔액");

                list.add(new Deposit(date, settlement, charge, deposit, balance));

                totalbal = totalbal + balance;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter = new DepositAdapter();
                    mAdapter.setItems(list);
                    recyclerView.setAdapter(mAdapter);
                }
            });

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
