package com.spitfirex2.battlecatsmasterwiki.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spitfirex2.battlecatsmasterwiki.R;
import com.spitfirex2.battlecatsmasterwiki.database.FirebaseDB;
import com.spitfirex2.battlecatsmasterwiki.database.unit.UnitDB;
import com.spitfirex2.battlecatsmasterwiki.fragments.Adapters.UnitRecycleViewAdapter;

import java.util.List;

public class RecycleViewFragment extends Fragment {

    private List<UnitDB> unitDisplayList;

    public RecycleViewFragment(List<UnitDB> displayList) {
        this.unitDisplayList = displayList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.recycler_list_layout, container, false);

        final RecyclerView recyclerView = layout.findViewById(R.id.list_unit_scroll_container);
        UnitRecycleViewAdapter adapter = new UnitRecycleViewAdapter(this.unitDisplayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(layout.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        SeekBar unitSeeker = layout.findViewById(R.id.unit_seeker);
        unitSeeker.setMax(FirebaseDB.mFirebaseUnitDB.size());
        unitSeeker.setMin(0);
        unitSeeker.setProgress(0);
        unitSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                recyclerView.scrollToPosition(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return layout;
    }
}
