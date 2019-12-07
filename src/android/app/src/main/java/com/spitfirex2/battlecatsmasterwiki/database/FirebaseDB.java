package com.spitfirex2.battlecatsmasterwiki.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spitfirex2.battlecatsmasterwiki.MainActivity;
import com.spitfirex2.battlecatsmasterwiki.database.unit.Unit;

import java.util.HashMap;

public class FirebaseDB {

    private DatabaseReference mFirebaseDB;

    private static final ChildEventListener value_updater = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Log.i(MainActivity.TAG, "Happy");
            Unit unit = dataSnapshot.getValue(Unit.class);
            Log.i(MainActivity.TAG, unit.toString());
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Log.i(MainActivity.TAG, "Changed");
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            Log.i(MainActivity.TAG, "Removed");
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Log.i(MainActivity.TAG, "Moved");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.i(MainActivity.TAG, "Sad");
        }
    };

    public FirebaseDB() {
        mFirebaseDB = FirebaseDatabase.getInstance().getReference();
    }

    public Unit getUnit(final String unitNumber) {
        final Unit unitToReturn = new Unit();
        final DatabaseReference ref = mFirebaseDB.child("cat_units/" + unitNumber);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Unit unit = dataSnapshot.getValue(Unit.class);
                Unit.copyUnit(unitToReturn, unit);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(MainActivity.TAG, "Sad");
            }
        });

        return unitToReturn;
    }
}
