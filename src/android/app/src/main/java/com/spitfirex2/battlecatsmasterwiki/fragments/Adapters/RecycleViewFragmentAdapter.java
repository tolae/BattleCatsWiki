package com.spitfirex2.battlecatsmasterwiki.fragments.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.spitfirex2.battlecatsmasterwiki.database.unit.UnitDB;
import com.spitfirex2.battlecatsmasterwiki.fragments.RecycleViewFragment;

import java.util.List;

public class RecycleViewFragmentAdapter extends FragmentStatePagerAdapter {

    private List<UnitDB> unitDisplayList;

    public RecycleViewFragmentAdapter(FragmentManager fm, List<UnitDB> displayList) {
        super(fm);
        this.unitDisplayList = displayList;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new RecycleViewFragment(unitDisplayList);
    }
}
