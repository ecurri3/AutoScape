package com.evan.autoscape.autoscape;

/**
 * Created by EvanOwns on 3/16/2015.
 */
public class Player {

    private int combatLevel;
    private String username;
    private String combatStyle;
    private Item[] inventory;
    private Item[] bank;
    private int[] skillEXP;

    public Player(){

    }

    public int getLevel(int statID){

        return skillEXP[statID];
    }

    final int STAT_ATTACK = 0;
    final int STAT_STRENGTH = 1;
    final int STAT_DEFENCE = 2;
    final int STAT_RANGED = 3;
    final int STAT_PRAYER = 4;
    final int STAT_MAGIC = 5;
    final int STAT_RUNECRAFTING = 6;
    final int STAT_CONSTRUCTION = 7;
    final int STAT_DUNGEONEERING = 8;
    final int STAT_HITPOINTS = 9;
    final int STAT_AGILITY = 10;
    final int STAT_HERBLORE = 11;
    final int STAT_THEIVING = 12;
    final int STAT_CRAFTING = 13;
    final int STAT_FLETCHING = 14;
    final int STAT_SLAYER = 15;
    final int STAT_HUNTER = 16;
    final int STAT_DIVINATION = 17;
    final int STAT_MINING = 18;
    final int STAT_SMITHING = 19;
    final int STAT_FISHING = 20;
    final int STAT_COOKING = 21;
    final int STAT_FIREMAKING = 22;
    final int STAT_WOODCUTTING = 23;
    final int STAT_FARMING = 24;
    final int STAT_SUMMONING = 25;


}
