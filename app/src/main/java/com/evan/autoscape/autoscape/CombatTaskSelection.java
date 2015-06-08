package com.evan.autoscape.autoscape;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.ViewAnimator;

/**
 * Created by EvanOwns on 5/21/2015.
 */
public class CombatTaskSelection extends ActionBarActivity {

    public static final String MY_PREFS = "MyPrefs"; //Name of sharedPrefs file
    SharedPreferences sharedPrefs; //sharedPrefs for storing game data

    ViewAnimator viewAnimator;
    Animation slide_in_left, slide_out_right;

    //List all buttons
    Button goblinTask;
    Button skeletonTask;

    ItemData itemData;

    Task currTask;

    int taskDuration;
    String taskName;
    Item[] taskDrops;
    int[] taskRS;
    int[] taskRV;

    Button confirmTask;
    Button declineTask;

    TextView taskName_view;
    TextView taskSkills_view;
    TextView taskValues_view;
    TextView rareDrops_view;

    NumberPicker np;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combat_task_choosing);

        //Get sharedPrefs
        sharedPrefs = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);

        itemData = new ItemData();

        viewAnimator = (ViewAnimator)findViewById(R.id.viewAnimator);
        slide_in_left = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        viewAnimator.setInAnimation(slide_in_left);
        viewAnimator.setOutAnimation(slide_out_right);

        taskName_view = (TextView) findViewById(R.id.taskName);
        taskSkills_view = (TextView) findViewById(R.id.taskSkills);
        taskValues_view = (TextView) findViewById(R.id.taskValues);
        rareDrops_view = (TextView) findViewById(R.id.rareDrops);

        np = (NumberPicker) findViewById(R.id.numberpicker);
        np.setMinValue(1);
        np.setValue(5);
        np.setMaxValue(15);
        np.setWrapSelectorWheel(false);

        confirmTask = (Button) findViewById(R.id.confirmTask);
        declineTask = (Button) findViewById(R.id.declineTask);
        goblinTask = (Button) findViewById(R.id.goblinTask);
        skeletonTask = (Button) findViewById(R.id.skeletonTask);

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                taskDuration = newVal;
                taskValues_view.setText("Experience: ");
                for(int i=0; i<taskRV.length; i++){
                    taskValues_view.append((taskRV[i]*taskDuration) + ", ");
                }
            }
        });


        goblinTask.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                setTask("Goblins", itemData.goblinTable, new int[] {Player.STAT_ATTACK, Player.STAT_STRENGTH}, new int[] {200, 300});
                viewAnimator.showNext();
            }
        });

        skeletonTask.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                setTask("Skeletons", itemData.skeletonTable, new int[] {Player.STAT_ATTACK, Player.STAT_DEFENCE}, new int[] {400, 500});
                viewAnimator.showNext();
            }
        });

        /*
        Decline task button
         */
        declineTask.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                viewAnimator.showPrevious();
            }
        });

        /*
        Confirm Task button
         */
        confirmTask.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                for(int i=0; i<taskRV.length; i++){
                    taskRV[i] *= taskDuration;
                }
                currTask = new Task((taskDuration * 60000), taskName, 2000, taskDrops, taskRS, taskRV, getApplicationContext());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                String currTaskJSON = currTask.toJSONString();
                editor.putString("currTask", currTaskJSON);
                editor.apply();
                startActivity(new Intent(CombatTaskSelection.this, MainActivity.class));
            }
        });

    }

    public void setTask(String name, Item[] table, int[] rewSkill, int[] rewValue){
        taskName = name;
        taskDrops = table;
        taskRS = rewSkill;
        taskRV = rewValue;

        taskName_view.setText(name);
        getSkillName(taskRS);
        taskValues_view.setText("Experience: ");
        for(int i=0; i<taskRV.length; i++){
            taskValues_view.append(taskRV[i] + ", ");
        }
        rareDrops_view.setText("Rare Drops: ");
        for(int i=8; i<10; i++){
            rareDrops_view.append(taskDrops[i].name + ", ");
        }
    }

    public void getSkillName(int[] rewSkill){
        taskSkills_view.setText("");
        for(int i=0; i<rewSkill.length; i++){
            switch(i){
                case 0:
                    taskSkills_view.append("Attack, ");
                    break;
                case 1:
                    taskSkills_view.append("Strength, ");
                    break;
                case 2:
                    taskSkills_view.append("Defence, ");
                    break;
                case 3:
                    taskSkills_view.append("Ranged, ");
                    break;
                case 4:
                    taskSkills_view.append("Prayer, ");
                    break;
                case 5:
                    taskSkills_view.append("Magic, ");
                    break;
                case 6:
                    taskSkills_view.append("Runecrafting, ");
                    break;
                case 7:
                    taskSkills_view.append("Construction, ");
                    break;
                case 8:
                    taskSkills_view.append("Dungeoneering, ");
                    break;
                case 9:
                    taskSkills_view.append("Hitpoints, ");
                    break;
                case 10:
                    taskSkills_view.append("Agility, ");
                    break;
                case 11:
                    taskSkills_view.append("Herblore, ");
                    break;
                case 12:
                    taskSkills_view.append("Thieving, ");
                    break;
                case 13:
                    taskSkills_view.append("Crafting, ");
                    break;
                case 14:
                    taskSkills_view.append("Fletching, ");
                    break;
                case 15:
                    taskSkills_view.append("Slayer, ");
                    break;
                case 16:
                    taskSkills_view.append("Hunter, ");
                    break;
                case 17:
                    taskSkills_view.append("Divination, ");
                    break;
                case 18:
                    taskSkills_view.append("Mining, ");
                    break;
                case 19:
                    taskSkills_view.append("Smithing, ");
                    break;
                case 20:
                    taskSkills_view.append("Fishing, ");
                    break;
                case 21:
                    taskSkills_view.append("Cooking, ");
                    break;
                case 22:
                    taskSkills_view.append("Firemaking, ");
                    break;
                case 23:
                    taskSkills_view.append("Woodcutting, ");
                    break;
                case 24:
                    taskSkills_view.append("Farming, ");
                    break;
                case 25:
                    taskSkills_view.append("Summoning, ");
                    break;


            }
        }
    }
}
