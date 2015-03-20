package com.evan.autoscape.autoscape;

/**
 * Created by EvanOwns on 3/16/2015.
 */
public class Player {

    private int combatLevel;
    private String username;
    private Item[] inventory;
    private Item[] bank;
    private int[] skillEXP;

    private enum combatStyle {
        MELEE, RANGED, MAGIC
    }

    public static final int STAT_ATTACK = 0;
    public static final int STAT_STRENGTH = 1;
    public static final int STAT_DEFENCE = 2;
    public static final int STAT_RANGED = 3;
    public static final int STAT_PRAYER = 4;
    public static final int STAT_MAGIC = 5;
    public static final int STAT_RUNECRAFTING = 6;
    public static final int STAT_CONSTRUCTION = 7;
    public static final int STAT_DUNGEONEERING = 8;
    public static final int STAT_HITPOINTS = 9;
    public static final int STAT_AGILITY = 10;
    public static final int STAT_HERBLORE = 11;
    public static final int STAT_THEIVING = 12;
    public static final int STAT_CRAFTING = 13;
    public static final int STAT_FLETCHING = 14;
    public static final int STAT_SLAYER = 15;
    public static final int STAT_HUNTER = 16;
    public static final int STAT_DIVINATION = 17;
    public static final int STAT_MINING = 18;
    public static final int STAT_SMITHING = 19;
    public static final int STAT_FISHING = 20;
    public static final int STAT_COOKING = 21;
    public static final int STAT_FIREMAKING = 22;
    public static final int STAT_WOODCUTTING = 23;
    public static final int STAT_FARMING = 24;
    public static final int STAT_SUMMONING = 25;

    public Player(String username){
        this.username = username;

    }

    public int getExp(int statID){

        return skillEXP[statID];
    }

    public void gainExp(int skill, int value) {
        skillEXP[skill] += value;
    }


}
