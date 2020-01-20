package com.spitfirex2.battlecatsmasterwiki.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.spitfirex2.battlecatsmasterwiki.R;
import com.spitfirex2.battlecatsmasterwiki.database.FirebaseDB;
import com.spitfirex2.battlecatsmasterwiki.database.unit.Unit;
import com.spitfirex2.battlecatsmasterwiki.database.unit.UnitDB;
import com.spitfirex2.battlecatsmasterwiki.fragments.Adapters.UnitFragmentAdapter;

public class SpecUnitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spec_unit_layout);

        int unit_pos = getIntent().getIntExtra("ATTACHED_UNIT_POS", -1);
        if (unit_pos < 0) {
            // TODO: Crash
        }
        UnitDB unit = FirebaseDB.mFirebaseUnitDB.get(unit_pos);

        TabLayout tabLayout = findViewById(R.id.unit_tabs);
        if (!unit.hasTrueForm())
            tabLayout.removeTabAt(2);

        final ViewPager viewPager = findViewById(R.id.unit_view_pager);
        UnitFragmentAdapter unit_adapter = new UnitFragmentAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), unit);
        viewPager.setAdapter(unit_adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
