package com.spitfirex2.battlecatsmasterwiki.database.unit;

import androidx.annotation.NonNull;

public class Unit {

    public enum UnitForm {
        NORMAL,
        EVOLVED,
        TRUE
    }

    public enum Rarity {
        NORMAL,
        SPECIAL,
        RARE,
        SUPER_RARE,
        UBER_RARE,
        CRAZED
    }

    public String unitNumber;
    public String enName;
    public String jpName;
    public String version;
    public String rarity;
    public String img;
    public UnitStats stats;
    public String unitForm;

    public Unit() {
        // Blank for Firebase
    }

    public static void copyUnit(Unit dest, Unit src) {
        dest.unitNumber = src.unitNumber;
        dest.enName = src.enName;
        dest.jpName = src.jpName;
        dest.version = src.version;
        dest.rarity = src.rarity;
        dest.img = src.img;
        dest.unitForm = src.unitForm;
        UnitStats.copyStats(dest.stats, src.stats);
    }

    public UnitStats getUnitStats(int something) {
        return null;
    }

    @Override
    public String toString() {
        return "Unit{\n" +
                "\tunitNumber='" + unitNumber + '\'' +
                "\n\tenName='" + enName + '\'' +
                "\n\tjpName='" + jpName + '\'' +
                "\n\tversion='" + version + '\'' +
                "\n\trarity='" + rarity + '\'' +
                "\n\timg='" + img + '\'' +
                "\n\tstats=" + stats +
                "\n\tunitForm='" + unitForm + '\'' +
                "\n}";
    }
}
