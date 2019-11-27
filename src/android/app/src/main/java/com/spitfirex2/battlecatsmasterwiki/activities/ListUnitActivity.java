package com.spitfirex2.battlecatsmasterwiki.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

import com.spitfirex2.battlecatsmasterwiki.R;

public class ListUnitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_unit_layout);

        LinearLayout table = findViewById(R.id.list_unit_linear_container);

        for (int child_index = 0; child_index < table.getChildCount(); child_index++) {
            TableRow row = (TableRow) table.getChildAt(child_index);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListUnitActivity.this, SpecUnitActivity.class);
                    ListUnitActivity.this.startActivity(intent);
                }
            });
        }
    }
}
