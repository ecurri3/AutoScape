package com.evan.autoscape.autoscape;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

/**
 * Created by EvanOwns on 3/16/2015.
 */
public class Task {

    private int requirements;
    private int cost;
    private Item[] itemRewards;

    private int expRewardSkills[]; //Skills for which exp is earned
    private int expRewardValues[]; //Corresponding exp amount for each skill

    private long Duration;//in millis
    public long startTime;//in millis
    private long lastChecked;//in millis
    public long endTime;//in millis

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
        while(currTime >= nextRewardTime){
            giveReward();
            lastChecked = nextRewardTime;
            nextRewardTime += rewardTick;
        }

    }

    /*
    Calculates the time for player to receive a reward from the current task
     */
    public long rewardTick(){
        return 1000;
    }

    /*
    Gives rewards to the player
     */
    public void giveReward(){

            rewardTable();
            giveEXP();
    }

    public void rewardTable(){

        Random rand = new Random();
        int roll = rand.nextInt((950 - 0) + 1) + 0;
        if(isBetween(roll, 0, 900))
            normalDrop();
        else if(isBetween(roll, 901, 950))
            rareDrop();
    }

    /*
    Grants EXP to player
     */
    public void giveEXP(){
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

    }

    /*
    Grants a rare drop to player
     */
    public void rareDrop(){

    }
}
