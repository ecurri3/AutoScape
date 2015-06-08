package com.evan.autoscape.autoscape;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by EvanOwns on 3/16/2015.
 */
public class Player {

    public int combatLevel;
    public int totalLevel;
    public String username;
    public Item inventory[];
    public Item bank[];
    public int skillEXP[];
    private int inventorySize = 28;
    private int bankSize = 1024;

    private enum combatStyle {
        MELEE, RANGED, MAGIC
    }

    private combatStyle currentCombatStyle;

    /*
    static ints that refer to index of each stat
     */
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

    /*
    Class constructor that is called only when app is run for first time
     */
    public Player(){
        combatLevel = 3;
        totalLevel = 26;
        username = "Player";
        skillEXP = new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        inventory = new Item[inventorySize];
        bank = new Item[bankSize];
    }

    /*
    JSON constructor
     */
    public Player(String JSON){
        try {
            JSONObject JSONObj = new JSONObject(JSON);
            Gson gson = new Gson();
            this.combatLevel = JSONObj.getInt("combatLevel");
            this.totalLevel = JSONObj.getInt("totalLevel");
            this.inventory = gson.fromJson(JSONObj.getString("inventory"), Item[].class);
            this.bank = gson.fromJson(JSONObj.getString("bank"), Item[].class);
            this.skillEXP = gson.fromJson(JSONObj.getString("skillEXP"), int[].class);
            this.username = JSONObj.getString("playerName");
        }
        catch (Exception e) {
            Log.e("Task JSON Constructor", e.getMessage());
        }
    }

    /*
    returns exp for given index
     */
    public int getExp(int statID){

        return skillEXP[statID];
    }

    /*
    returns level for a given exp value

    difference between levels grows by roughly 10.4% each level up
     */
    public int getLevel (int exp){

        // new int array is created
        // NOTE: May be wise to add skillLVL array to Player that is saved in JSON to
        //       reduce processing
        int skills[] = new int[99];
        skills[0] = 0;
        skills[1] = 83; // index is initially set to be used for further calculations
        int diff = 83;

        for(int i=2; i<99 ; i++){
            diff = (int)(diff * 1.10409); // update the difference between levels
            skills[i] = skills[i-1] + diff;
        }

        // finds index at which the exp is higher than the exp param and returns index(your level)
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
        updateTotalLevel();
    }

    /**
     * Combat level = (1/4)*(Attack + Strength + Defence + Hitpoints) + (1/7)*(Prayer)
     */
    private void updateCombatLevel(){
        combatLevel = (getLevel(skillEXP[STAT_ATTACK]) + getLevel(skillEXP[STAT_STRENGTH]) + getLevel(skillEXP[STAT_DEFENCE])
                + getLevel(skillEXP[STAT_HITPOINTS]))/4;
        combatLevel += (getLevel(skillEXP[STAT_PRAYER]))/7;
    }

    /*
    Total level = all levels added together, duh
     */
    private void updateTotalLevel(){
        totalLevel = 0;
        for(int i=0; i<26; i++){
            totalLevel += getLevel(skillEXP[i]);
        }
    }

    /*
    Write player data to JSON String
     */
    public String toJSONString(){

        JSONObject jsonObject= new JSONObject();
        Gson gson = new Gson();
        try{
            jsonObject.put("combatLevel", combatLevel);
            jsonObject.put("totalLevel", totalLevel);
            jsonObject.put("playerName", username);
            jsonObject.put("inventory", gson.toJson(inventory));
            jsonObject.put("bank", gson.toJson(bank));
            jsonObject.put("skillEXP", gson.toJson(skillEXP));
        }catch (Exception e){
            Log.e("Player.toJSONString()", e.getMessage());
        }
        return jsonObject.toString();
    }

    /*
    Used for debugging,
    prints all item information to Log.d
     */
    public void getItem(int index){
        Log.d("Item name", "" + inventory[index].name);
        Log.d("Item examine", "" + inventory[index].examine);
        Log.d("Item quantity", "" + inventory[index].quantity);
        Log.d("Item value", "" + inventory[index].value);
    }

    /*
    Adds item to either inventory or bank when reward is given

    Inventory is first checked for the same Item
        if item is found, add quantities together
        if item is not found, find first null index and add item

    Bank is checked second and follows same algorithm as inventory
     */
    public void addItem(Item item){
        for(int i=0; i<inventorySize; i++){
            if(inventory[i] != null) {
                if (inventory[i].ID == item.ID) {
                    inventory[i].quantity += item.quantity;
                    return;
                }
            }
        }
        for(int i=0;i<inventorySize; i++){
            if(inventory[i] == null){
                inventory[i] = item;
                return;
            }
        }
        for(int i=0; i<bankSize; i++){
            if(bank[i] != null) {
                if (bank[i].ID == item.ID) {
                    bank[i].quantity += item.quantity;
                    return;
                }
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
