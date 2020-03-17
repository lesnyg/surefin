package com.jubumam.surefin.ProtectorPackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jubumam.surefin.LoginActivity;
import com.jubumam.surefin.R;

public class SelectActivity extends ProtectorBaseActivity {

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
    }
}
