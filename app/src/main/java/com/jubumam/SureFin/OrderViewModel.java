package com.jubumam.SureFin;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private String name = "";
    private String phoneNumber;
    private String address;
    private String quantity;
    private String orderPrice;
    private String paymentmethod = "";
    private String complete = "";
    private String orderTime;
    private String cpTime;
    private AsyncTask<String, String, String> orderTask;
    private Connection connection = null;
    private String strToday;
    private List<SongyeongOrder> list;

    MutableLiveData<List<SongyeongOrder>> orderlist = new MutableLiveData<>();

    public OrderViewModel(@NonNull Application application) {
        super(application);
        lodeData();
    }

    public void lodeData() {
        new AsyncTask<Void,Void,List<SongyeongOrder>>(){

            @Override
            protected List<SongyeongOrder> doInBackground(Void... voids) {
                listQuery();
                return null;
            }
            protected void onPostExecute(List<SongyeongOrder> data) {
                orderlist.setValue(data);
            }
        }.execute();
    }

    public void listQuery() {
    }


}
