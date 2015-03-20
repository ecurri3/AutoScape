package com.evan.autoscape.autoscape;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by EvanOwns on 3/16/2015.
 */
public class Task {

    private int requirements;
    private int cost;
    private Item[] itemRewards;
    private ArrayList<String> arrayListEvents = new ArrayList<String>();

    private int expRewardSkills[]; //Skills for which exp is earned
    private int expRewardValues[]; //Corresponding exp amount for each skill

    private long Duration;//in millis
    public long startTime;//in millis
    private long lastChecked;//in millis
    public long endTime;//in millis

    public boolean isDone = false; //Is task done?

    private Context context;

    public static final String MY_PREFS = "MyPrefs";
    SharedPreferences sharedpreferences;

    public Task(){

    }

    public Task(int minDuration, Context c){
        this.startTime = System.currentTimeMillis();
        this.lastChecked = startTime;
        this.endTime = startTime + minDuration * 60 * 1000;
        context = c;

        sharedpreferences = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong("endTime_currTask", endTime);
        editor.apply();

        //example exp rewards for combat task: Str and Prayer
        expRewardSkills = new int[] {Player.STAT_ATTACK, Player.STAT_PRAYER};
        expRewardValues = new int[] {200, 300};
    }

    /*
    Updates task progress based on last time you checked
     */
    public void updateTask(){
        long currTime = System.currentTimeMillis();
        long rewardTick = rewardTick();
        long nextRewardTime = lastChecked + rewardTick;

        //Give out as many rewards as the player has earned
        //Stop giving rewards if task has been completed
        while(currTime >= nextRewardTime && lastChecked <= endTime){
            giveReward();
            lastChecked = nextRewardTime;
            nextRewardTime += rewardTick;
        }

        //If task is done, set flag so we can stop updating
        if (currTime >= endTime) {
            arrayListEvents.add("Task complete!");
            this.isDone = true;
        }
    }

    public int getProgress() {
        //Return an integer representing the percentage that this task is complete
        //Range: [0, 100]
        return Math.min(100, (int)(100.0*(System.currentTimeMillis() - startTime) / (endTime - startTime)));
    }

    /*
    Calculates the time for player to receive a reward from the current task
     */
    public long rewardTick(){
        return 2000;
    }

    /*
    Gives rewards to the player
     */
    public void giveReward(){

            rewardTable();
            giveEXP();
    }

    public void rewardTable(){

        int roll = getRoll(1,10);
        if(isBetween(roll, 1, 8))
            normalDrop();
        else if(isBetween(roll, 9, 10))
            rareDrop();
    }

    public int getRoll(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /*
    Grants EXP to player
     */
    public void giveEXP(){
        //Grant exp in each skill this task involves
        for (int i = 0; i < expRewardSkills.length; i++) {

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
    public void normalDrop(){
        arrayListEvents.add("Normal drop.");
    }

    /*
    Grants a rare drop to player
     */
    public void rareDrop(){
        arrayListEvents.add("Rare drop!");
    }

    public ArrayList<String> getEvents() {
        return arrayListEvents;
    }
}
