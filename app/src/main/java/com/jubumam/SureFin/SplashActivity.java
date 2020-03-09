package com.jubumam.SureFin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import com.jubumam.SureFin.NokPackage.SelectActivity;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler hd = new Handler();
        hd.postDelayed(new SplashHandler(), 2000); // 1초 후에 hd handler 실행  1000ms = 1초

    }

    private class SplashHandler implements Runnable {
        public void run() {
            startActivity(new Intent(getApplication(), SelectActivity.class)); //로딩이 끝난 후 MainActivity 로 이동
            finish(); // 로딩페이지 Activity stack 에서 제거
        }
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }


}
