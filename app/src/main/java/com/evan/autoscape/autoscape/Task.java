package com.evan.autoscape.autoscape;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Random;

/**
 * Created by EvanOwns on 3/16/2015.
 */
public class Task {

    private int requirements;
    private int cost;
    private Item[] rewards;
    private int expReward;

    private long Duration;//in millis
    private long timeStarted;//in millis
    private long lastChecked;//in millis
    private long endTime;//in millis

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;

    public Task(){

    }

    public Task(int min, Context c){

        this.timeStarted = System.currentTimeMillis();
        this.endTime = timeStarted + min * 60 * 100;
        //I dont know what this does
        sharedpreferences = c.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    /*
    Updates task progress based on last time you checked
     */
    public void updateTask(){

        long currTime = System.currentTimeMillis();
        long timeDiff = currTime - this.lastChecked;
        if(timeDiff >= rewardTick()){
            int numRewards = (int)(timeDiff / rewardTick());
            this.lastChecked = currTime;
            giveRewards(numRewards, expReward);
        }
    }

    /*
    Calculates the time for player to receive a reward from the current task
     */
    public long rewardTick(){
        return 0;
    }

    /*
    Gives rewards to the player
     */
    public void giveRewards(int numRewards, int expReward){

        for(int i = 0; i < numRewards; i++){
            rewardTable();
            giveEXP(expReward);
        }
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
    public void giveEXP(int expReward){

    }

    /*
    Determines if roll was in a certain range
     */
    public boolean isBetween(int roll, int min, int max){
        if(roll >= min && roll <= max)
            return true;
        else
            return false;
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
