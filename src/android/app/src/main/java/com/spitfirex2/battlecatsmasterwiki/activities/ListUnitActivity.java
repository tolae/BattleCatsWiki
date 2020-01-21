package com.spitfirex2.battlecatsmasterwiki.activities;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.spitfirex2.battlecatsmasterwiki.R;
import com.spitfirex2.battlecatsmasterwiki.database.FirebaseDB;
import com.spitfirex2.battlecatsmasterwiki.database.unit.UnitDB;
import com.spitfirex2.battlecatsmasterwiki.fragments.Adapters.RecycleViewFragmentAdapter;

import java.util.List;

public class ListUnitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_unit_layout);

        final ViewPager viewPager = findViewById(R.id.recycle_view_pager);
        RecycleViewFragmentAdapter unit_adapter = new RecycleViewFragmentAdapter(getSupportFragmentManager(), FirebaseDB.mFirebaseUnitDB);
        viewPager.setAdapter(unit_adapter);

        SearchView searchUnit = findViewById(R.id.search_units);
        searchUnit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<UnitDB> foundList = FirebaseDB.search(query);
                if (foundList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "No cats found.", Toast.LENGTH_SHORT).show();
                    foundList = FirebaseDB.mFirebaseUnitDB;
                    RecycleViewFragmentAdapter unit_adapter = new RecycleViewFragmentAdapter(getSupportFragmentManager(), foundList);
                    viewPager.setAdapter(unit_adapter);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RecycleViewFragmentAdapter unit_adapter = new RecycleViewFragmentAdapter(getSupportFragmentManager(), FirebaseDB.search(newText));
                viewPager.setAdapter(unit_adapter);
                return true;
            }
        });


    }
}
