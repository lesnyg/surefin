package com.jubumam.surefin.ProtectorPackage;

import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.jubumam.surefin.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProtectorDepositActivity extends ProtectorBaseActivity {
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
    private SimpleDateFormat sdf;
    private SimpleDateFormat sdf2;
    private String recipiId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protector_deposit);

        activateToolbar();

        Protector protector = Protector.getInstance();
        recipiId = protector.getRecipiId();
        name = protector.getRecipientName();
        rating = protector.getRating();

        recyclerView = findViewById(R.id.recyclerview_nok);
        tv_totalbal = findViewById(R.id.tv_totalbalance);
        moneyfm = new DecimalFormat("###,###");
        sdf = new SimpleDateFormat("yyyy-MM");
        sdf2 = new SimpleDateFormat("yyyy년\nMM월");
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
            tv_totalbal.setText(moneyfm.format(totalbal) + " 원");
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
            ResultSet resultSet = statement.executeQuery("select * from Su_본인부담금정산 where 수급자코드 = '" + recipiId + "' order by 일자 desc");
            list = new ArrayList<>();
            while (resultSet.next()) {
                date = resultSet.getString("일자");
                settlement = resultSet.getInt("정산금");
                charge = resultSet.getInt("청구금");
                deposit = resultSet.getInt("입금액");
                balance = resultSet.getInt("잔액");



                Date dt = sdf.parse(date);

                list.add(new Deposit(sdf2.format(dt), settlement, charge, deposit, balance));

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
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
