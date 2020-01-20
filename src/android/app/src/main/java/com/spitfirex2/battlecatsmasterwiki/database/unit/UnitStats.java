package com.spitfirex2.battlecatsmasterwiki.database.unit;

public class UnitStats {

    public String health, knockback, attackRateF, attackRateS, attackPower, movementSpeed,
            attackAnimF, attackAnimS, range, respawnTimeF, respawnTimeS, cost, attackType;

    public UnitStats() {
        // For Firebase
    }

    public String getHealthAtLevel(int level, Unit.Rarity rarity) {
        if (level <= 0)
            return this.health;
        return String.valueOf(getStatAtLevel(level, Double.parseDouble(this.health), rarity));
    }

    public String getAttackPowerAtLevel(int level, Unit.Rarity rarity) {
        if (level <= 0)
            return this.attackPower;
        return String.valueOf(getStatAtLevel(level, Double.parseDouble(this.attackPower), rarity));
    }

    private static int getStatAtLevel(int level, double initialStat, Unit.Rarity rarity) {
        return (int) (initialStat * (1 +
                0.2 * Math.min(level - 1, Unit.Rarity.getFirstRedPoint(rarity)) +
                0.1 * Math.max(Math.min(level - Unit.Rarity.getFirstRedPoint(rarity), Unit.Rarity.getSecondRedPoint(rarity)), 0) +
                0.05 * Math.max(level - Unit.Rarity.getSecondRedPoint(rarity), 0)));
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
}