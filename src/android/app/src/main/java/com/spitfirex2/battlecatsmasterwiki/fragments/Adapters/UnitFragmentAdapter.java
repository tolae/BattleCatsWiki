package com.spitfirex2.battlecatsmasterwiki.fragments.Adapters;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.spitfirex2.battlecatsmasterwiki.activities.MainActivity;
import com.spitfirex2.battlecatsmasterwiki.database.unit.UnitDB;
import com.spitfirex2.battlecatsmasterwiki.fragments.UnitFragment;

public class UnitFragmentAdapter extends FragmentStatePagerAdapter {

    private UnitDB unit;
    private int tab_count;

    public UnitFragmentAdapter(FragmentManager fm, int number_of_tabs, UnitDB unit) {
        super(fm);
        this.unit = unit;
        this.tab_count = number_of_tabs;
    }

    @Override
    public int getCount() {
        return tab_count;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new UnitFragment(unit.Normal);
            case 1:
                return new UnitFragment(unit.Evolved);
            case 2:
                return new UnitFragment(unit.True);
            default:
                Log.d(MainActivity.TAG, "Rut ro");
                return null;
        }
    }
}
