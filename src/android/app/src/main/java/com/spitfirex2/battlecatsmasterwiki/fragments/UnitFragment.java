package com.spitfirex2.battlecatsmasterwiki.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spitfirex2.battlecatsmasterwiki.R;
import com.spitfirex2.battlecatsmasterwiki.database.unit.Unit;
import com.spitfirex2.battlecatsmasterwiki.database.unit.UnitStats;

public class UnitFragment extends Fragment {

    private Integer level;
    private Unit unit;

    public UnitFragment(Unit unit) {
        this.unit = unit;
        this.level = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.unit_fragment_layout, container, false);
        ImageView cat_img = layout.findViewById(R.id.unit_fragment_img);
        switch (this.unit.unitForm) {
            case NORMAL:
                cat_img.setImageResource(R.mipmap.colors);
                break;
            case EVOLVED:
                cat_img.setImageResource(R.mipmap.duck);
                break;
            case TRUE:
                cat_img.setImageResource(R.mipmap.reddit);
                break;
            default:
                break;
        }

        EditText level_input = layout.findViewById(R.id.unit_fragment_level_input);
        level_input.setText("30");
        level_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("Ethan", String.format("%s:\n\t%d\n\t%d\n\t%d", charSequence.toString(), i, i1, i2));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    level = Integer.valueOf(editable.toString());
                } catch (NumberFormatException e) {
                    level = null;
                }
                update(layout);
            }
        });
        return layout;
    }

    private void update(View layout) {
        UnitStats stat = unit.getUnitStats(this.level);
        TextView cost = layout.findViewById(R.id.unit_cost);

        cost.setText(String.valueOf(stat.cost));
    }
}
//https://www.tutlane.com/tutorial/android/android-tabs-with-fragments-and-viewpager
//https://stackoverflow.com/questions/34306476/dynamically-add-and-remove-tabs-in-tablayoutmaterial-design-android