package com.spitfirex2.battlecatsmasterwiki.database.unit;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.spitfirex2.battlecatsmasterwiki.R;
import com.spitfirex2.battlecatsmasterwiki.activities.SpecUnitActivity;
import com.spitfirex2.battlecatsmasterwiki.database.FirebaseDB;

public class UnitRecycleViewHolder extends RecyclerView.ViewHolder {

    public UnitDB unit;
    public ImageView image;
    public TextView unitName;
    public TextView unitDescription;

    public UnitRecycleViewHolder(View view, final Context context) {
        super(view);

        unit = null;
        image = view.findViewById(R.id.unit_img);
        unitName = view.findViewById(R.id.unit_name_txt);
        unitDescription = view.findViewById(R.id.unit_description_txt);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unit != null) {
                    Intent intent = new Intent(context, SpecUnitActivity.class);
                    intent.putExtra("ATTACHED_UNIT_POS", FirebaseDB.mFirebaseUnitDB.indexOf(unit));
                    context.startActivity(intent);
                }
            }
        });
    }
}