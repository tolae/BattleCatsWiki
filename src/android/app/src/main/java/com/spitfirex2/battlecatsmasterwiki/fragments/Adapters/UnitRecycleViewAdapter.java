package com.spitfirex2.battlecatsmasterwiki.fragments.Adapters;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spitfirex2.battlecatsmasterwiki.R;
import com.spitfirex2.battlecatsmasterwiki.database.unit.UnitDB;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static com.spitfirex2.battlecatsmasterwiki.MainActivity.TAG;

public class UnitRecycleViewAdapter extends RecyclerView.Adapter<UnitRecycleViewAdapter.UnitRecycleViewHolder> {

    private List<UnitDB> unitDBList;

    public UnitRecycleViewAdapter(List<UnitDB> unitDBList) {
        this.unitDBList = unitDBList;
    }

    @NonNull
    @Override
    public UnitRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_unit_row_view, parent, false);

        return new UnitRecycleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitRecycleViewHolder holder, int position) {
        UnitDB unit = unitDBList.get(position);
        holder.unitName.setText(unit.Normal.enName);
        holder.unitDescription.setText(unit.Normal.enDescription);
        new LoadUnitRow(holder).execute(unit);
    }

    @Override
    public int getItemCount() {
        return unitDBList.size();
    }

    private static class LoadUnitRow extends AsyncTask<UnitDB, Void, Drawable> {
        private UnitRecycleViewHolder view;

        public LoadUnitRow(UnitRecycleViewHolder view) {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Drawable doInBackground(UnitDB... unit) {
            String img_url = unit[0].Normal.img;
            Log.d(TAG, img_url);
            Drawable fromUrl = null;
            if (!img_url.isEmpty()) {
                fromUrl = LoadImageFromWebOperations(img_url);
            }
            return fromUrl;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);
            if (drawable != null) {
                this.view.image.setImageDrawable(drawable);
            }
        }

        private Drawable LoadImageFromWebOperations(String url) {
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                return Drawable.createFromStream(is, "src name");
            } catch (Exception e) {
                Log.d(TAG, Log.getStackTraceString(e));
                return null;
            }
        }
    }

    public class UnitRecycleViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView unitName;
        TextView unitDescription;

        public UnitRecycleViewHolder(View view) {
            super(view);

            image = itemView.findViewById(R.id.unit_img);
            unitName = itemView.findViewById(R.id.unit_name_txt);
            unitDescription = itemView.findViewById(R.id.unit_description_txt);
        }
    }
}
