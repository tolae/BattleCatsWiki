package com.spitfirex2.battlecatsmasterwiki.database.unit;

import java.util.HashMap;
import java.util.List;

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

    public String enDescription;
    public String enName;
    public String img;
    public String jpDescription;
    public String jpName;
    public String rarity;
    public String version;
    public UnitStats stats;
    public HashMap<String, List<String>> combos;

    public String unitNumber;
    public UnitForm unitForm;

    public Unit() {
        // Blank for Firebase
    }

    public Unit(final String unitNumber, final UnitForm unitForm) {
        this.unitNumber = unitNumber;
        this.unitForm = unitForm;

        this.enDescription = "";
        this.enName = "";
        this.img = "";
        this.jpDescription = "";
        this.jpName = "";
        this.rarity = "";
        this.version = "";
        this.stats = new UnitStats();
        this.combos = new HashMap<>();
    }

    public static void copyUnit(Unit dest, Unit src) {
        dest.enName = src.enName;
        dest.jpName = src.jpName;
        dest.jpDescription = src.jpDescription;
        dest.enDescription = src.enDescription;
        dest.version = src.version;
        dest.rarity = src.rarity;
        dest.img = src.img;
        dest.combos = src.combos;
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
                "\n\tunitForm='" + unitForm + '\'' +
                "\n\tstats=" + stats +
                "\n}";
    }
}
