package com.evan.autoscape.autoscape;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by EvanOwns on 3/16/2015.
 */
public class Player {

    public int combatLevel;
    public String username;
    public Item[] inventory;
    public Item[] bank;
    public int skillEXP[];
    private int inventorySize = 28;
    private int bankSize = 1024;

    private enum combatStyle {
        MELEE, RANGED, MAGIC
    }

    private combatStyle currentCombatStyle;

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

    public Player(){
        combatLevel = 0;
        username = "Player";
        skillEXP = new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        Log.i("skillEXP", "" + skillEXP[0]);
    }

    public Player(String JSON){
        try {
            JSONObject JSONObj = new JSONObject(JSON);
            Gson gson = new Gson();
            this.combatLevel = JSONObj.getInt("combatLevel");
            this.inventory = gson.fromJson(JSONObj.getString("inventory"), Item[].class);
            this.bank = gson.fromJson(JSONObj.getString("bank"), Item[].class);
            this.skillEXP = gson.fromJson(JSONObj.getString("skillEXP"), int[].class);
            this.username = JSONObj.getString("playerName");
        }
        catch (Exception e) {
            Log.e("Task JSON Constructor", e.getMessage());
        }
    }

    public int getExp(int statID){

        return skillEXP[statID];
    }

    public int getLevel (int exp){

        int skills[] = new int[99];
        skills[0] = 0;
        skills[1] = 83;
        int diff = 83;

        for(int i=2; i<99 ; i++){
            diff = (int)(diff * 1.10409);
            skills[i] = skills[i-1] + diff;
        }

        for(int i=0; i<99; i++){
            if(skills[i] > exp){
                return i;
            }
        }

        return 1;
    }

    /**
     * Increases skill's experience amount.
     * @param skill Skill number. This number should be between (and including) 0 and 25.
     * @param value Amount of experience gained. This number will be added to skill's experience.
     */
    public void gainExp(int skill, int value) {
        skillEXP[skill] += value;
        updateCombatLevel();
    }

    /**
     * Combat level = (1/4)*(Attack + Strength + Defence + Hitpoints) + (1/7)*(Prayer)
     */
    private void updateCombatLevel(){
        combatLevel = (skillEXP[STAT_ATTACK] + skillEXP[STAT_STRENGTH] + skillEXP[STAT_DEFENCE]
                + skillEXP[STAT_HITPOINTS])/4;
        combatLevel += (skillEXP[STAT_PRAYER])/7;
    }

    public String toJSONString(){

        JSONObject jsonObject= new JSONObject();
        Gson gson = new Gson();
        try{
            jsonObject.put("combatLevel", combatLevel);
            jsonObject.put("playerName", username);
            jsonObject.put("inventory", gson.toJson(inventory));
            jsonObject.put("bank", gson.toJson(bank));
            jsonObject.put("skillEXP", gson.toJson(skillEXP));
        }catch (Exception e){
            Log.e("Player.toJSONString()", e.getMessage());
        }
        return jsonObject.toString();
    }

    public void addItem(Item item){
        for(int i=0; i<inventorySize; i++){
            if(inventory[i].ID == item.ID){
                inventory[i].quantity += item.quantity;
                return;
            }
        }
        for(int i=0;i<inventorySize; i++){
            if(inventory[i] == null){
                inventory[i] = item;
                return;
            }
        }
        for(int i=0; i<bankSize; i++){
            if(bank[i].ID == item.ID){
                bank[i].quantity += item.quantity;
                return;
            }
        }
        for(int i=0;i<bankSize; i++){
            if(bank[i] == null){
                bank[i] = item;
                return;
            }
        }
    }

}
