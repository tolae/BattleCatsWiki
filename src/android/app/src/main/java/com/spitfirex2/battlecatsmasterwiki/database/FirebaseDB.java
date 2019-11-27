package com.spitfirex2.battlecatsmasterwiki.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spitfirex2.battlecatsmasterwiki.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDB {

    private DatabaseReference mFirebaseDB;
    private List<Unit> cat_units;

    private static final ValueEventListener value_updater = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.i(MainActivity.TAG, "Got a value!");
            Unit unit = dataSnapshot.getValue(Unit.class);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public FirebaseDB() {
        mFirebaseDB = FirebaseDatabase.getInstance().getReference();
        cat_units = new ArrayList<>();
    }
}
