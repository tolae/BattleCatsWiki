package com.spitfirex2.battlecatsmasterwiki.fragments.Adapters;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spitfirex2.battlecatsmasterwiki.R;
import com.spitfirex2.battlecatsmasterwiki.database.unit.UnitDB;
import com.spitfirex2.battlecatsmasterwiki.database.unit.UnitRecycleViewHolder;
import com.spitfirex2.battlecatsmasterwiki.util.Utility;

import java.util.List;

public class UnitRecycleViewAdapter extends RecyclerView.Adapter<UnitRecycleViewHolder> {

    private List<UnitDB> unitDBList;

    public UnitRecycleViewAdapter(List<UnitDB> unitDBList) { this.unitDBList = unitDBList; }

    @NonNull
    @Override
    public UnitRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_unit_row_view, parent, false);
        return new UnitRecycleViewHolder(itemView, parent.getContext());
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull UnitRecycleViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.image.setImageResource(android.R.color.transparent);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(@NonNull final UnitRecycleViewHolder holder, final int position) {
        UnitDB unit = this.unitDBList.get(position);
        holder.unit = unit;
        holder.unitName.setText(unit.Normal.enName);
        holder.unitDescription.setText(unit.Normal.enDescription);
        if (holder.unit.Normal.imgDrawable == null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    Utility.loadFormImage(holder.unit.Normal);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if (holder.unit.Normal.imgDrawable != null &&
                        holder.unit.Normal.unitNumber.equals(unitDBList.get(position).Normal.unitNumber)
                    )
                        holder.image.setImageDrawable(holder.unit.Normal.imgDrawable);
                }
            }.execute();
        } else {
            holder.image.setImageDrawable(holder.unit.Normal.imgDrawable);
        }
    }

    @Override
    public int getItemCount() { return unitDBList.size(); }
}
