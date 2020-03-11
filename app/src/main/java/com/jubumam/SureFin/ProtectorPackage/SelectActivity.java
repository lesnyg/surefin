package com.jubumam.SureFin.ProtectorPackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jubumam.SureFin.LoginActivity;
import com.jubumam.SureFin.R;

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
