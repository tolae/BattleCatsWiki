package com.spitfirex2.battlecatsmasterwiki.database;

import java.util.HashMap;

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

    private int unit_number;
    private String en_name;
    private String jp_name;
    private int version;
    private Rarity rarity;
    private int image_id;
    private UnitStats unit_stats;

    public Unit(int unit_number, String en_name, String jp_name, int version, Rarity rarity, int image_id, HashMap<String, Object> unit_stats) {
        this.unit_number = unit_number;
        this.en_name = en_name;
        this.jp_name = jp_name;
        this.version = version;
        this.rarity = rarity;
        this.image_id = image_id;
        this.unit_stats = new  UnitStats(unit_stats);
    }

    public UnitStats getUnitStats(int level) {
        return unit_stats.getStats(level);
    }

    public int getUnitNumber() {
        return unit_number;
    }

    public String getEnName() {
        return en_name;
    }

    public String getJpName() {
        return jp_name;
    }

    public int getVersion() {
        return version;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int getImageId() {
        return image_id;
    }

    public class UnitStats {

        public final int health, knockback, attack_rate_f, attack_rate_s, attack_power, movement_speed,
                attack_anim_f, attack_anim_s, range, respawn_time_f, cost, attack_type;

        private UnitStats(HashMap<String, Object> stats) {
            this.health = (Integer) stats.get("health");
            this.knockback = (Integer) stats.get("knockback");
            this.attack_rate_f = (Integer) stats.get("attack_rate_f");
            this.attack_rate_s = (Integer) stats.get("attack_rate_s");
            this.attack_power = (Integer) stats.get("attack_power");
            this.movement_speed = (Integer) stats.get("movement_speed");
            this.attack_anim_f = (Integer) stats.get("attack_anim_f");
            this.attack_anim_s = (Integer) stats.get("attack_anim_s");
            this.range = (Integer) stats.get("range");
            this.respawn_time_f = (Integer) stats.get("respawn_time_f");
            this.cost = (Integer) stats.get("cost");
            this.attack_type = (Integer) stats.get("attack_type");
        }

        private UnitStats(int health, int knockback, int attack_rate_f, int attack_rate_s,
                        int attack_power, int movement_speed, int attack_anim_f, int attack_anim_s,
                        int range, int respawn_time_f, int cost, int attack_type) {
            this.health = health;
            this.knockback = knockback;
            this.attack_rate_f = attack_rate_f;
            this.attack_rate_s = attack_rate_s;
            this.attack_power = attack_power;
            this.movement_speed = movement_speed;
            this.attack_anim_f = attack_anim_f;
            this.attack_anim_s = attack_anim_s;
            this.range = range;
            this.respawn_time_f = respawn_time_f;
            this.cost = cost;
            this.attack_type = attack_type;
        }

        private UnitStats getStats(int level) {
            return new UnitStats(
                    calculate_stat(level, this.health),
                    this.knockback,
                    this.attack_rate_f,
                    this.attack_rate_s,
                    calculate_stat(level, this.attack_power),
                    this.movement_speed,
                    this.attack_anim_f,
                    this.attack_anim_s,
                    this.range,
                    this.respawn_time_f,
                    this.cost,
                    this.attack_type
            );
        }

        private int calculate_stat(int level, int base_stat) {
            return -1;
        }
    }
}
