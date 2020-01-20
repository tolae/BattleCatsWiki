package com.spitfirex2.battlecatsmasterwiki.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.spitfirex2.battlecatsmasterwiki.R;
import com.spitfirex2.battlecatsmasterwiki.database.FirebaseDB;

public class SplashActivity extends AppCompatActivity {
    public static final int EXPECTED_UNIT_SIZE = 513;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        FirebaseDB firebaseDB = new FirebaseDB();
        firebaseDB.loadAllUnits();

        final ProgressBar loadingBar = findViewById(R.id.loadingProgressBar);
        loadingBar.setSecondaryProgressTintList(ColorStateList.valueOf(Color.BLACK));
        loadingBar.setProgressBackgroundTintList(ColorStateList.valueOf(Color.RED));
        loadingBar.setMax(100);
        loadingBar.setMin(0);

        Thread loadingThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    while (FirebaseDB.mFirebaseUnitDB.size() < EXPECTED_UNIT_SIZE) {
                        loadingBar.setProgress(FirebaseDB.mFirebaseUnitDB.size() / EXPECTED_UNIT_SIZE);
                        sleep(50);
                    }
                } catch (Exception e) {

                } finally {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(intent);
                }
            }
        };

        loadingThread.start();
    }
}
