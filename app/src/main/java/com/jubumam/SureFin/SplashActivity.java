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

    private String storeVersion;
    private String deviceVersion;

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
    private class VersionCheck extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public class BackgroundThread extends Thread {
        @Override
        public void run() {

            // 패키지 네임 전달
            storeVersion = MarketVersionChecker.getMarketVersion(getPackageName());

            // 디바이스 버전 가져옴
            try {
                deviceVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            deviceVersionCheckHandler.sendMessage(deviceVersionCheckHandler.obtainMessage());
            // 핸들러로 메세지 전달
        }
    }

    private final DeviceVersionCheckHandler deviceVersionCheckHandler = new DeviceVersionCheckHandler(this);

    // 핸들러 객체 만들기
    private static class DeviceVersionCheckHandler extends Handler {
        private final WeakReference<SplashActivity> splashActivityWeakReference;

        public DeviceVersionCheckHandler(SplashActivity splashActivity) {
            splashActivityWeakReference = new WeakReference<SplashActivity>(splashActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity activity = splashActivityWeakReference.get();
            if (activity != null) {
                activity.handleMessage(msg);
                // 핸들메세지로 결과값 전달
            }
        }
    }

    private void handleMessage(Message msg) {
        //핸들러에서 넘어온 값 체크
        Log.e(" storeVersion : ", storeVersion);
        Log.e(" deviceVersion : ", deviceVersion);
        if (storeVersion.compareTo(deviceVersion) > 0) {
            // 업데이트 필요

            AlertDialog.Builder alertDialogBuilder =
                    new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_Light));
            alertDialogBuilder.setTitle("업데이트");
            alertDialogBuilder
                    .setMessage("새로운 버전이 있습니다.\n보다 나은 사용을 위해 업데이트 해 주세요.")
                    .setPositiveButton("업데이트 바로가기", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);

                            intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                            startActivity(intent);
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();

        } else {
            // 업데이트 불필요

        }

    }

}
