package com.evan.autoscape.autoscape;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by EvanOwns on 3/16/2015.
 */
public class Task {

    private int requirements;
    private int cost;
    private Item[] itemRewards;
    private int expRewardSkills[];
    private int expRewardValues[];

    private long Duration;//in millis
    private long timeStarted;//in millis
    private long lastChecked;//in millis
    private long endTime;//in millis

    private Context context;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;

    public Task(){

    }

    public Task(int minDuration, Context c){
        this.timeStarted = System.currentTimeMillis();
        this.lastChecked = timeStarted;
        this.endTime = timeStarted + minDuration * 60 * 1000;
        context = c;

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
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
        long endTime = sharedpreferences.getLong("endTime_currTask", 1000);
        String endTime_S = "" + endTime;
        Toast.makeText(context, endTime_S, Toast.LENGTH_SHORT).show();
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
