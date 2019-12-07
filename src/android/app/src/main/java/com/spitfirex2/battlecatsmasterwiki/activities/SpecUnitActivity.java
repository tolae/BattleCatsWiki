package com.spitfirex2.battlecatsmasterwiki.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.spitfirex2.battlecatsmasterwiki.R;
import com.spitfirex2.battlecatsmasterwiki.database.unit.Unit;
import com.spitfirex2.battlecatsmasterwiki.fragments.Adapters.UnitFragmentAdapter;

public class SpecUnitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spec_unit_layout);

        TabLayout tabLayout = findViewById(R.id.unit_tabs);
        final ViewPager viewPager = findViewById(R.id.unit_view_pager);
        UnitFragmentAdapter unit_adapter = new UnitFragmentAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), Unit.UnitForm.NORMAL);
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
