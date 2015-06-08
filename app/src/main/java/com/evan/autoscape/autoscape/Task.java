package com.evan.autoscape.autoscape;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by EvanOwns on 3/16/2015.
 */
public class Task implements Serializable {

    public String name;
    public int cost;
    public Item[] itemRewards;
    public ArrayList<String> arrayListEvents = new ArrayList<String>();

    public int expRewardSkills[]; //Skills for which exp is earned
    public int expRewardValues[]; //Corresponding exp amount for each skill
    public long tickDur = 0;

    public Item dropTable[] = {};

    public long duration;//in millis
    public long startTime;//in millis
    public long lastChecked;//in millis
    public long endTime;//in millis

    public boolean isDone = false; //Is task done?

    private Context context;

    public static final String MY_PREFS = "MyPrefs";
    SharedPreferences sharedpreferences;
    Player player;

    public Task(){

    }

    public Task(int minDuration, Context c){
        this.startTime = System.currentTimeMillis();
        this.lastChecked = startTime;
        this.endTime = startTime + minDuration;
        context = c;

        sharedpreferences = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong("endTime_currTask", endTime);
        editor.apply();

        //example exp rewards for combat task: Str and Prayer
        expRewardSkills = new int[] {Player.STAT_ATTACK, Player.STAT_PRAYER};
        expRewardValues = new int[] {200, 300};
        duration = minDuration;
    }

    public Task(long minDuration, String name, long tickDur, Item[] dropTable, int[] xpRS, int[] xpRV, Context c){
        this.startTime = System.currentTimeMillis();
        this.lastChecked = startTime;
        this.endTime = startTime + minDuration;
        context = c;

        sharedpreferences = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong("endTime_currTask", endTime);
        editor.apply();

        //example exp rewards for combat task: Str and Prayer
        expRewardSkills = xpRS;
        expRewardValues = xpRV;
        this.dropTable = dropTable;

        this.duration = minDuration;
        this.tickDur = tickDur;
        this.name = name;
    }

    public Task(String JSON) {
        try {
            JSONObject JSONObj = new JSONObject(JSON);

            Gson gson = new Gson();

            this.startTime = JSONObj.getLong("startTime");
            this.endTime = JSONObj.getLong("endTime");
            this.lastChecked = JSONObj.getLong("lastChecked");
            this.name = JSONObj.getString("taskName");
            this.tickDur = JSONObj.getLong("tickDur");
            this.duration = JSONObj.getLong("duration");

            this.expRewardSkills = gson.fromJson(JSONObj.getString("expRewardSkills"), int[].class);
            this.expRewardValues = gson.fromJson(JSONObj.getString("expRewardValues"), int[].class);
            this.dropTable = gson.fromJson(JSONObj.getString("dropTable"), Item[].class);

            JSONArray JSONListEvents = JSONObj.getJSONArray("arrayListEvents");
            for (int i = 0; i < JSONListEvents.length(); i++) {
                arrayListEvents.add((String)JSONListEvents.get(i));
            }
        }
        catch (Exception e) {
            Log.e("Task JSON Constructor", e.getMessage());
        }
    }

    /**
     * Updates task progress based on last time you checked
     */
    public void updateTask(){
        long currTime = System.currentTimeMillis();
        long rewardTick = tickDur;
        long nextRewardTime = lastChecked + rewardTick;

        //Give out as many rewards as the player has earned
        //Stop giving rewards if task has been completed
        while(currTime >= nextRewardTime && lastChecked <= endTime){
            giveReward();
            lastChecked = nextRewardTime;
            nextRewardTime += rewardTick;
        }

        //If task is done, set flag so we can stop updating
        //Task is complete after all rewards have been given
        if (currTime >= endTime) {
            arrayListEvents.add("Task complete!");
            this.isDone = true;
        }
    }


    /*
    Return an integer representing the percentage that this task is complete
    Range: [0, 100]
    */
    public int getProgress() {
        return Math.min(100, (int)(100.0*(System.currentTimeMillis() - startTime) / (endTime - startTime)));
    }

    /*
    Gives rewards to the player
    Includes item rewards from drop table and exp rewards
     */
    public void giveReward(){

            rewardTable();
            giveEXP();
    }


    /*
    Roll for an item from the drop table
    By generating a random number and comparing to drop table ranges
     */
    public void rewardTable(){
        int roll = getRoll(10);
        if(isBetween(roll, 0, 7))
            normalDrop(roll);
        else if(isBetween(roll, 8, 9))
            rareDrop(roll);

        MainActivity.player.addItem(dropTable[roll]);
    }

    public int getRoll(int max) {
        Random rand = new Random();
        return rand.nextInt(max);
    }

    /*
    Grants EXP to player
     */
    public void giveEXP(){
        long tick = duration / tickDur;
        int value;
        String message;
        //Grant exp in each skill this task involves
        for (int i = 0; i < expRewardSkills.length; i++) {
            value = expRewardValues[i] / (int)tick;
            MainActivity.player.gainExp(expRewardSkills[i], value);
            message = "Gained " + value + "EXP";
            arrayListEvents.add(message);
        }
    }

    /*
    Determines if roll was in a certain range
     */
    public boolean isBetween(int roll, int min, int max){

        return (roll >= min && roll <= max);
    }

    /*
    Grants a normal drop to player
     */
    public void normalDrop(int roll){

        String message = "Normal drop  --  " + dropTable[roll].name;
        arrayListEvents.add(message);
    }

    /*
    Grants a rare drop to player
     */
    public void rareDrop(int roll){

        String message = "Rare Drop!  --  " + dropTable[roll].name;
        arrayListEvents.add(message);
    }

    public ArrayList<String> getEvents() {
        return arrayListEvents;
    }

    public String toJSONString() {
        JSONObject jsonObject= new JSONObject();
        Gson gson = new Gson();
        try {
            jsonObject.put("startTime", startTime);
            jsonObject.put("endTime", endTime);
            jsonObject.put("lastChecked", lastChecked);
            jsonObject.put("taskName", name);
            jsonObject.put("tickDur", tickDur);
            jsonObject.put("duration", duration);

            jsonObject.put("expRewardSkills", gson.toJson(expRewardSkills));
            jsonObject.put("expRewardValues", gson.toJson(expRewardValues));
            jsonObject.put("dropTable", gson.toJson(dropTable));

            JSONArray JSONListEvents = new JSONArray(this.arrayListEvents);
            jsonObject.put("arrayListEvents", JSONListEvents);
        } catch (Exception e) {
            Log.e("Task.getJSONString()", e.getMessage());
        }
        return jsonObject.toString();
    }
}
