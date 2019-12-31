package com.spitfirex2.battlecatsmasterwiki.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spitfirex2.battlecatsmasterwiki.database.unit.UnitDB;

import java.util.ArrayList;
import java.util.List;

import static com.spitfirex2.battlecatsmasterwiki.MainActivity.TAG;

public class FirebaseDB {

    private DatabaseReference mFirebaseDB;
    public static final List<UnitDB> mFirebaseUnitDB = new ArrayList<>();

    public FirebaseDB() {
        mFirebaseDB = FirebaseDatabase.getInstance().getReference();
    }

    public void loadAllUnits() {
        final DatabaseReference ref = mFirebaseDB.child("cat_units");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UnitDB unit = postSnapshot.getValue(UnitDB.class);
                    unit.init(postSnapshot.getKey());
                    mFirebaseUnitDB.add(unit);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "Sad");
            }
        });
    }
}
