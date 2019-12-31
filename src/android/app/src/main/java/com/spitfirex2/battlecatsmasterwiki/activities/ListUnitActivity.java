package com.spitfirex2.battlecatsmasterwiki.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spitfirex2.battlecatsmasterwiki.R;
import com.spitfirex2.battlecatsmasterwiki.database.FirebaseDB;
import com.spitfirex2.battlecatsmasterwiki.fragments.Adapters.UnitRecycleViewAdapter;

public class ListUnitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_unit_layout);

        RecyclerView recyclerView = findViewById(R.id.list_unit_scroll_container);
        UnitRecycleViewAdapter adapter = new UnitRecycleViewAdapter(FirebaseDB.mFirebaseUnitDB);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
