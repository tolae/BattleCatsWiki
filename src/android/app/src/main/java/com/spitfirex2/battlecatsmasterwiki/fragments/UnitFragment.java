package com.spitfirex2.battlecatsmasterwiki.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spitfirex2.battlecatsmasterwiki.R;
import com.spitfirex2.battlecatsmasterwiki.database.unit.Unit;

public class UnitFragment extends Fragment {

    private Integer level;
    private Unit unit;

    public UnitFragment(Unit unit) {
        this.unit = unit;
        this.level = 30;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.unit_fragment_layout, container, false);
        Toast.makeText(getContext(), "Level must be greater than 0.", Toast.LENGTH_SHORT).show();

        // Level Independent Stats
        setLevelViews(layout);

        EditText level_input = layout.findViewById(R.id.unit_fragment_level_input);
        level_input.setText("30");
        level_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    level = Integer.valueOf(editable.toString());
                    update(layout);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Level can only be a positive integer.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return layout;
    }

    private void update(View layout) {
        if (this.level <= 0) {
            return;
        }

        TextView health = layout.findViewById(R.id.unit_health);
        TextView attack = layout.findViewById(R.id.unit_attack);

        health.setText(String.valueOf(this.unit.stats.getHealthAtLevel(this.level, Unit.Rarity.getRarity(unit.rarity))));
        attack.setText(String.valueOf(this.unit.stats.getAttackPowerAtLevel(this.level, Unit.Rarity.getRarity(unit.rarity))));
    }

    private void setLevelViews(View layout) {
        // Generic Unit
        TextView jpName = layout.findViewById(R.id.jpName);
        TextView jpDesc = layout.findViewById(R.id.unit_jp_desc);
        TextView unitNumber = layout.findViewById(R.id.unitNumber);
        TextView enName = layout.findViewById(R.id.enName);
        TextView enDesc = layout.findViewById(R.id.unit_en_desc);

        jpName.setText(this.unit.jpName);
        jpDesc.setText(this.unit.jpDescription);
        unitNumber.setText(this.unit.unitNumber);
        enName.setText(this.unit.enName);
        enDesc.setText(this.unit.enDescription);
        // Image
        ImageView cat_img = layout.findViewById(R.id.unit_fragment_img);
        cat_img.setImageDrawable(this.unit.imgDrawable);
        // Stats
        TextView cost = layout.findViewById(R.id.unit_cost);
        TextView kb = layout.findViewById(R.id.unit_knockback);
        TextView range = layout.findViewById(R.id.unit_range);
        TextView ms = layout.findViewById(R.id.unit_movement_speed);
        TextView respawn = layout.findViewById(R.id.unit_respawn);
        TextView attackRate = layout.findViewById(R.id.unit_attack_rate);
        TextView attackAnim = layout.findViewById(R.id.unit_attack_anim);
//        TextView effects = layout.findViewById(R.id.unit_effects);

        cost.setText(this.unit.stats.cost);
        kb.setText(this.unit.stats.knockback);
        range.setText(this.unit.stats.range);
        ms.setText(this.unit.stats.movementSpeed);
        respawn.setText(this.unit.stats.respawnTimeS);
        attackRate.setText(this.unit.stats.attackRateS);
        attackAnim.setText(this.unit.stats.attackAnimS);
//        effects.setText(this.unit.stats.cost);

        // Level Dependent Initial Stats (30)
        TextView health = layout.findViewById(R.id.unit_health);
        TextView attack = layout.findViewById(R.id.unit_attack);

        health.setText(String.valueOf(this.unit.stats.getHealthAtLevel(this.level, Unit.Rarity.getRarity(unit.rarity))));
        attack.setText(String.valueOf(this.unit.stats.getAttackPowerAtLevel(this.level, Unit.Rarity.getRarity(unit.rarity))));
    }
}
