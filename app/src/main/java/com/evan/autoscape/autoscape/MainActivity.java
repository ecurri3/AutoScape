package com.evan.autoscape.autoscape;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    public static final String MY_PREFS = "MyPrefs"; //Name of sharedPrefs file
    SharedPreferences sharedPrefs; //sharedPrefs for storing game data
    Task currTask; //The current task in progress


    LinearLayout linearLayoutEvents;
    ScrollView scrollViewEvents;

    ProgressBar progressBar;


    final int UPDATE_FREQUENCY = 2000; //Number of milliseconds between updates
    final Handler handler = new Handler(); //Handler allows us to call runnableUpdate repeatedly
    Runnable runnableUpdate = new Runnable() { //Runnable object for calling update
        public void run() {
            update(); //Call the update method
            handler.postDelayed(this, UPDATE_FREQUENCY); //Repeat periodically
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get sharedPrefs
        sharedPrefs = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        //TODO: Initialize GameData by reading sharedPrefs
        currTask = new Task(1, this);

        linearLayoutEvents = (LinearLayout)findViewById(R.id.LinearLayoutEvents);
        scrollViewEvents = (ScrollView) findViewById(R.id.scrollViewEvents);
        progressBar = (ProgressBar) findViewById(R.id.progressBarTask);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        //Called whenever this activity returns to foreground
        super.onResume();
        //Call update immediately
        update();
        //Restart runnableUpdate using Handler
        handler.postDelayed(runnableUpdate, UPDATE_FREQUENCY);
    }

    public void onPause(){
        //Called whenever this activity is in the background
        super.onPause();
        //Temporarily remove runnableUpdate calls from Handler
        handler.removeCallbacks(runnableUpdate);
    }

    public void update() {
        //Testing adding an event
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView tv = (TextView)layoutInflater.inflate(R.layout.text_view_event_item, null); //inflate textview
        tv.setText("Selling lobbies 200 ea");
        linearLayoutEvents.addView(tv); //add to list of events
        scrollViewEvents.fullScroll(ScrollView.FOCUS_DOWN); //scroll to bottom



        //Currently have a task in progress
        if (currTask != null) {
            //Updates current task progress
            currTask.updateTask();
            int progress = Math.min((int)((System.currentTimeMillis() - currTask.startTime) / (currTask.endTime - currTask.startTime)*100), 100);
            progressBar.setProgress(progress);
        }
        //No task in progress
        else {

        }
    }
}
