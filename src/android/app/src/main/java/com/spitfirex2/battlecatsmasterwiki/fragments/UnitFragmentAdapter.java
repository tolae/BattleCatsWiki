package com.spitfirex2.battlecatsmasterwiki.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.spitfirex2.battlecatsmasterwiki.database.Unit;

public class UnitFragmentAdapter extends FragmentStatePagerAdapter {

    private Unit.UnitForm curr_form;
    private int tab_count;

    public UnitFragmentAdapter(FragmentManager fm, int number_of_tabs, Unit.UnitForm form) {
        super(fm);
        this.curr_form = form;
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
                curr_form = Unit.UnitForm.NORMAL;
                return new UnitFragment(curr_form);
            case 1:
                curr_form = Unit.UnitForm.EVOLVED;
                return new UnitFragment(curr_form);
            case 2:
                curr_form = Unit.UnitForm.TRUE;
                return new UnitFragment(curr_form);
            default:
                return null;
        }
    }
}
