package com.jubumam.surefin.ProtectorPackage;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.jubumam.surefin.LoginActivity;
import com.jubumam.surefin.R;

import java.util.List;

public class SelectActivity extends ProtectorBaseActivity {
    private String packageName = "com.surefin.surefindelivery";
    private String className = "com.surefin.surefindelivery.SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        findViewById(R.id.btn_respon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectActivity.this, LoginActivity.class));
            }
        });

        findViewById(R.id.btn_nok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectActivity.this, ProtectorLoginActivity.class));
            }
        });

        findViewById(R.id.btn_delivery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean bool = getPackageList();
                if(bool) {
                    Intent intent = new Intent();
                    intent.setClassName(packageName, className);
                    startActivity(intent);
                }else{
                    String url = "market://details?id=" + "com.surefin.surefindelivery";
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                }

            }
        });


    }
    public boolean getPackageList() {
        boolean isExist = false;

        PackageManager pkgMgr = getPackageManager();
        List<ResolveInfo> mApps;
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps = pkgMgr.queryIntentActivities(mainIntent, 0);

        try {
            for (int i = 0; i < mApps.size(); i++) {
                if(mApps.get(i).activityInfo.packageName.startsWith(packageName)){
                    isExist = true;
                    break;
                }
            }
        }
        catch (Exception e) {
            isExist = false;
        }
        return isExist;
    }
}
