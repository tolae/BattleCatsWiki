package com.spitfirex2.battlecatsmasterwiki.database.unit;

import com.spitfirex2.battlecatsmasterwiki.util.Utility;

public class UnitDB {

    public Unit Normal;
    public Unit Evolved;
    public Unit True;

    public UnitDB() {
        // Blank for Firebase
    }

    public void init(final String unitNumber) {
        if (this.Normal != null && this.Evolved != null) {
            this.Normal.unitNumber = unitNumber;
            this.Normal.unitForm = Unit.UnitForm.NORMAL;

            this.Evolved.unitNumber = unitNumber;
            this.Evolved.unitForm = Unit.UnitForm.EVOLVED;
        }
        if (hasTrueForm()) {
            this.True.unitNumber = unitNumber;
            this.True.unitForm = Unit.UnitForm.TRUE;
        }
    }

    public void loadUnitDrawables() {
        if (this.Normal.imgDrawable == null)
            Utility.loadFormImage(this.Normal);
        if (this.Evolved.imgDrawable == null)
            Utility.loadFormImage(this.Evolved);
        if (this.hasTrueForm())
            if (this.True.imgDrawable == null)
                Utility.loadFormImage(this.True);
    }

    public boolean hasTrueForm() {
        return this.True != null;
    }

    public static void copyUnitDB(UnitDB dest, UnitDB src) {
        Unit.copyUnit(dest.Normal, src.Normal);
        Unit.copyUnit(dest.Evolved, src.Evolved);
        Unit.copyUnit(dest.True, src.True);
    }

    @Override
    public String toString() {
        return "Normal:\n" + this.Normal.toString() +
            "\nEvolved:\n" + this.Evolved.toString() +
            "\nTrue:\n" + this.True.toString();
    }
}
