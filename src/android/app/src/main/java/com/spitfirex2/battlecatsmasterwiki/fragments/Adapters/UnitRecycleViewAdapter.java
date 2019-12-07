package com.spitfirex2.battlecatsmasterwiki.fragments.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spitfirex2.battlecatsmasterwiki.R;

public class UnitRecycleViewAdapter extends RecyclerView.ViewHolder {

    public View linearLayout;
    public View row;

    View mView;

    ImageView image;
    TextView unitName;
    TextView unitDescription;

    public UnitRecycleViewAdapter(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        linearLayout = itemView.findViewById(R.id.list_unit_linear_container);
        row = itemView.findViewById(R.id.row);
        image = itemView.findViewById(R.id.unit_img);
        unitName = itemView.findViewById(R.id.unit_txt);
        unitDescription = itemView.findViewById(R.id.unit_description_txt);
    }
}
