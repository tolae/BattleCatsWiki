package com.spitfirex2.battlecatsmasterwiki.database.unit;

public class UnitStats {

    public String health, knockback, attackRateF, attackRateS, attackPower, movementSpeed,
            attackAnimF, attackAnimS, range, respawnTimeF, respawnTimeS, cost, attackType;

    public UnitStats() {
        // For Firebase
    }

    public static void copyStats(UnitStats dest, UnitStats src) {
        dest.health = src.health;
        dest.knockback = src.knockback;
        dest.attackRateF = src.attackRateF;
        dest.attackRateS = src.attackRateS;
        dest.attackPower = src.attackPower;
        dest.movementSpeed = src.movementSpeed;
        dest.attackAnimF = src.attackAnimF;
        dest.attackAnimS = src.attackAnimS;
        dest.range = src.range;
        dest.respawnTimeF = src.respawnTimeF;
        dest.respawnTimeS = src.respawnTimeS;
        dest.cost = src.cost;
        dest.attackType = src.attackType;
    }

    @Override
    public String toString() {
        return "UnitStats{\n" +
                "\thealth='" + health + '\'' +
                "\n\tknockback='" + knockback + '\'' +
                "\n\tattackRateF='" + attackRateF + '\'' +
                "\n\tattackRateS='" + attackRateS + '\'' +
                "\n\tattackPower='" + attackPower + '\'' +
                "\n\tmovementSpeed='" + movementSpeed + '\'' +
                "\n\tattackAnimF='" + attackAnimF + '\'' +
                "\n\tattackAnimS='" + attackAnimS + '\'' +
                "\n\trange='" + range + '\'' +
                "\n\trespawnTimeF='" + respawnTimeF + '\'' +
                "\n\trespawnTimeS='" + respawnTimeS + '\'' +
                "\n\tcost='" + cost + '\'' +
                "\n\tattackType='" + attackType + '\'' +
                "\n}";
    }

    private int calculate_stat(int level, int base_stat) {
        return -1;
    }
}