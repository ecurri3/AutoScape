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
import android.widget.Toast;

import java.util.ArrayList;


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

        switch (id) {
            case R.id.action_settings:
            return true;
            case R.id.equipment:
                Toast.makeText(this, "Equipment clicked.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.stats:
                Toast.makeText(this, "Stats clicked.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.bank:
                Toast.makeText(this, "Bank clicked.", Toast.LENGTH_SHORT).show();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        //Called whenever this activity returns to foreground
        super.onResume();

        //Rebuild objects by reading sharedPrefs
        rebuildObjects();

        //Resume updating task
        update();
        //Restart runnableUpdate using Handler
        handler.postDelayed(runnableUpdate, UPDATE_FREQUENCY);
    }

    public void onPause(){
        //Called whenever this activity is in the background
        super.onPause();
        //Temporarily remove runnableUpdate calls from Handler
        handler.removeCallbacks(runnableUpdate);

        //Write JSON to sharedPrefs
        SharedPreferences.Editor editor = sharedPrefs.edit();
        String currTaskJSON = currTask.toJSONString();
        editor.putString("currTask", currTaskJSON);
        editor.apply();
        Toast.makeText(this, "Stored JSON", Toast.LENGTH_SHORT).show();
    }




    public void rebuildObjects() {
        //Read JSON from sharedPrefs
        String currTaskJSON = sharedPrefs.getString("currTask", "");
        //Create currTask from retrieved JSON
        if (!currTaskJSON.equals("")) {
            currTask = new Task(currTaskJSON);
            Toast.makeText(this,"Read JSON", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Null String", Toast.LENGTH_SHORT).show();
        }
    }

    public void update() {
        //Currently have a task in progress
        if (currTask != null && !currTask.isDone) {
            //Updates current task progress
            currTask.updateTask();
            progressBar.setProgress(currTask.getProgress());
            updateEvents();

        }
        //No task in progress
        else {

        }
    }

    public void updateEvents() {
        //Add all task events to event list
        if (linearLayoutEvents.getChildCount() > 0)
            linearLayoutEvents.removeAllViews();
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ArrayList<String> events = currTask.getEvents();
        for (int i = 0; i < events.size(); i++) {
            TextView tv = (TextView) layoutInflater.inflate(R.layout.text_view_event_item, null); //inflate TextView
            tv.setText(events.get(i));
            linearLayoutEvents.addView(tv); //add to list of events
        }
        scrollViewEvents.fullScroll(ScrollView.FOCUS_DOWN); //scroll to bottom
    }
}
