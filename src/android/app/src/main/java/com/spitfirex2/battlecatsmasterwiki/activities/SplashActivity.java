package com.spitfirex2.battlecatsmasterwiki.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

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

        final FirebaseDB firebaseDB = new FirebaseDB();

        final ProgressBar loadingBar = findViewById(R.id.loadingProgressBar);
        loadingBar.setMax(EXPECTED_UNIT_SIZE);
        loadingBar.setMin(0);

        final TextView loadingText = findViewById(R.id.loadingProgressText);
        Thread loadingThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    // Load cat units
                    firebaseDB.loadAllUnits();
                    while (FirebaseDB.mFirebaseUnitDB.size() < EXPECTED_UNIT_SIZE) {
                        loadingBar.setProgress(FirebaseDB.mFirebaseUnitDB.size());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingText.setText(loadingBar.getProgress() + " / " + EXPECTED_UNIT_SIZE);
                            }
                        });
                        sleep(100);
                    }
                } catch (Exception e) {
                    Log.e(MainActivity.TAG, "Error with loading progress bar.");
                } finally {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(intent);
                }
            }
        };

        loadingThread.start();
    }
}
