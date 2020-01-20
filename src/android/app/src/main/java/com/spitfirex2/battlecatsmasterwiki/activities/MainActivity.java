package com.spitfirex2.battlecatsmasterwiki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.spitfirex2.battlecatsmasterwiki.R;
import com.spitfirex2.battlecatsmasterwiki.database.FirebaseDB;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "FIREBASE_DATABASE_TESTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDB firebaseDB = new FirebaseDB();
        firebaseDB.loadAllUnits();

        Button cat_unit_btn = findViewById(R.id.cat_unit_btn);
        cat_unit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListUnitActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }
}
