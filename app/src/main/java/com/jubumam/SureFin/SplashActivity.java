package com.jubumam.SureFin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_dav);

        Handler hd = new Handler();
        hd.postDelayed(new SplashHandler(), 2000); // 1초 후에 hd handler 실행  1000ms = 1초

    }

    private class SplashHandler implements Runnable {
        public void run() {
            startActivity(new Intent(getApplication(),login.class)); //로딩이 끝난 후 MainActivity 로 이동
            finish(); // 로딩페이지 Activity stack 에서 제거
        }
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }

}
