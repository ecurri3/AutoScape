package com.evan.autoscape.autoscape;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    public static final String MY_PREFS = "MyPrefs"; //Name of sharedPrefs file
    //State of task to determine behavior of buttonBottom
    public static final int NO_TASK = -1;
    public static final int TASK_IN_PROGRESS = 0;
    public static final int TASK_COMPLETE = 1;
    final int UPDATE_FREQUENCY = 2000; //Number of milliseconds between updates
    Runnable runnableUpdate = new Runnable() { //Runnable object for calling update
        public void run() {
            update(); //Call the update method
            handler.postDelayed(this, UPDATE_FREQUENCY); //Repeat periodically
        }
    };
    final Handler handler = new Handler(); //Handler allows us to call runnableUpdate repeatedly
    SharedPreferences sharedPrefs; //sharedPrefs for storing game data
    Task currTask; //The current task in progress
    public static Player player;

    LinearLayout linearLayoutEvents;
    ScrollView scrollViewEvents;
    ProgressBar progressBar;
    Button buttonBottom; //New task, end task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get sharedPrefs
        sharedPrefs = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        //currTask = new Task(1, this);

        //create player every time activity is started
        //if player exists, read from preferences
        if (sharedPrefs.contains("player")) {
            String playerJSON = sharedPrefs.getString("player", "");
            player = new Player(playerJSON);
        } else {
            //else, create a new player
            player = new Player();
        }

        linearLayoutEvents = (LinearLayout) findViewById(R.id.LinearLayoutEvents);
        scrollViewEvents = (ScrollView) findViewById(R.id.scrollViewEvents);
        progressBar = (ProgressBar) findViewById(R.id.progressBarTask);
        buttonBottom = (Button) findViewById(R.id.buttonBottom);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.equipment:
                Toast.makeText(this, "Equipment clicked.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.stats:
                startActivity(new Intent(MainActivity.this, PlayerStatsActivity.class));
                return true;
            case R.id.bank:
                Toast.makeText(this, "Bank clicked.", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        //Called whenever this activity returns to foreground
        super.onResume();

        //Rebuild objects by reading sharedPrefs
        rebuildObjects();

        //Resume updating task
        update();
        //Restart runnableUpdate using Handler
        handler.postDelayed(runnableUpdate, UPDATE_FREQUENCY);
    }

    public void onPause() {
        //Called whenever this activity is in the background
        super.onPause();
        //Temporarily remove runnableUpdate calls from Handler
        handler.removeCallbacks(runnableUpdate);

        //Write JSON to sharedPrefs
        SharedPreferences.Editor editor = sharedPrefs.edit();
        String playerJSON = player.toJSONString();
        editor.putString("player", playerJSON);
        editor.apply();

        if (currTask != null) {
            editor = sharedPrefs.edit();
            String currTaskJSON = currTask.toJSONString();
            editor.putString("currTask", currTaskJSON);
            editor.apply();
            Toast.makeText(this, "Stored JSON", Toast.LENGTH_SHORT).show();
        }
        else {
            editor = sharedPrefs.edit();
            editor.putString("currTask", "");
            editor.apply();
            Toast.makeText(this, "Stored JSON", Toast.LENGTH_SHORT).show();
        }
    }


    /*
    Re-create objects stored as JSON in sharedPrefs
     */
    public void rebuildObjects() {
        //Read JSON from sharedPrefs
        String currTaskJSON = sharedPrefs.getString("currTask", "");
        String playerJSON = sharedPrefs.getString("player", "");
        //Create currTask from retrieved JSON
        //if player exists, read from preferences
        if (sharedPrefs.contains("player")) {
            player = new Player(playerJSON);
        } else {
            //else, create a new player
            player = new Player();
        }
        if (!currTaskJSON.equals("")) {
            currTask = new Task(currTaskJSON);
            Toast.makeText(this, "Read JSON", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Didn't read JSON", Toast.LENGTH_SHORT).show();
        }
    }


    /*
    Call update of the current Task
    Refresh UI: progressBar, linearLayoutEvents, buttonBottom
     */
    public void update() {
        //Have some task
        if (currTask != null) {
            //Task is not done
            if (!currTask.isDone) {
                //Updates current task progress
                currTask.updateTask();
                progressBar.setProgress(currTask.getProgress());
                updateEvents();
                //Check in case task just finished
                if (currTask.isDone)
                    updateButtonBottom(TASK_COMPLETE);
                else
                    updateButtonBottom(TASK_IN_PROGRESS);
            }
            //Task is done
            else {
                updateButtonBottom(TASK_COMPLETE);
                progressBar.setProgress(100);
            }
        }
        //No task in progress
        else {
            updateButtonBottom(NO_TASK);
            progressBar.setProgress(0);
        }
    }


    /*
    Re-draw the list of events within the linearLayoutEvents
     */
    public void updateEvents() {
        //Add all task events to event list
        if (linearLayoutEvents.getChildCount() > 0)
            linearLayoutEvents.removeAllViews();
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ArrayList<String> events = currTask.getEvents();
        for (int i = 0; i < events.size(); i++) {
            TextView tv = (TextView) layoutInflater.inflate(R.layout.text_view_event_item, null); //inflate TextView
            tv.setText(events.get(i));
            linearLayoutEvents.addView(tv); //add to list of events
        }
        scrollViewEvents.fullScroll(ScrollView.FOCUS_DOWN); //scroll to bottom
    }

    /*
Button's onClick is defined based on state of the current Task
 */
    public void buttonBottomClick(View v) {

    }


    /*
    Update the appearance and function of the buttonBottom
    Based on the state of the current Task
     */
    public void updateButtonBottom(int taskState) {
        switch (taskState) {
            case NO_TASK:
                //Button will add new task when clicked
                buttonBottom.setText("New task");
                buttonBottom.setBackgroundColor(Color.parseColor("#343434"));
                buttonBottom.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, TaskChoosingActivity.class));
                    }
                });
                break;
            case TASK_IN_PROGRESS:
                buttonBottom.setText("End task");
                buttonBottom.setBackgroundColor(Color.parseColor("#a50606"));
                buttonBottom.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        currTask = null;
                        update();
                    }
                });
                break;
            case TASK_COMPLETE:
                //Button will end current task when clicked
                buttonBottom.setText("End task");
                buttonBottom.setBackgroundColor(Color.parseColor("#12960a"));
                buttonBottom.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        currTask = null;
                        update();
                    }
                });
                break;
        }
    }
}
