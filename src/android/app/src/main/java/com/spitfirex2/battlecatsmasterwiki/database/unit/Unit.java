package com.spitfirex2.battlecatsmasterwiki.database.unit;

import android.graphics.drawable.Drawable;

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
        CRAZED,
        LEGEND,
        UNDEFINED;

        public static Rarity getRarity(String rarity) {
            switch (rarity) {
                case "基本":
                    return NORMAL;
                case "EX":
                    return SPECIAL;
                case "レア":
                    return RARE;
                case "激レア":
                    return SUPER_RARE;
                case "狂乱":
                    return CRAZED;
                case "超激レア":
                    return UBER_RARE;
                case "伝説レア":
                    return LEGEND;
                default:
                    return UNDEFINED;
            }
        }

        public static int getFirstRedPoint(Rarity rarity) {
            switch (rarity) {
                case NORMAL:
                case SPECIAL:
                case SUPER_RARE:
                case UBER_RARE:
                case LEGEND:
                    return 60;
                case RARE:
                    return 70;
                case CRAZED:
                    return 20;
                default:
                    return 0;
            }
        }

        public static int getSecondRedPoint(Rarity rarity) {
            switch (rarity) {
                case NORMAL:
                case SPECIAL:
                case CRAZED:
                    return Integer.MAX_VALUE;
                case RARE:
                    return 90;
                case SUPER_RARE:
                case UBER_RARE:
                case LEGEND:
                    return 80;
                default:
                    return 0;
            }
        }
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

    public Drawable imgDrawable;

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
        this.imgDrawable = null;
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
        dest.imgDrawable = src.imgDrawable;
        UnitStats.copyStats(dest.stats, src.stats);
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
