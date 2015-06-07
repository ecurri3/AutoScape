package com.evan.autoscape.autoscape;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by EvanOwns on 5/21/2015.
 */
public class CombatTaskSelection extends ActionBarActivity {

    public static final String MY_PREFS = "MyPrefs"; //Name of sharedPrefs file
    SharedPreferences sharedPrefs; //sharedPrefs for storing game data

    //List all buttons
    Button goblinTask;
    Button skeletonTask;
    Button cowTask;
    Button chickenTask;

    Item[] goblinTable = createGoblinTable();
    int[] goblinRS = new int[] {Player.STAT_ATTACK, Player.STAT_STRENGTH};
    int[] goblinRV = new int[] {200, 300};

    Task currTask;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combat_task_choosing);

        //Get sharedPrefs
        sharedPrefs = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);

        goblinTask = (Button) findViewById(R.id.goblinTask);

        goblinTask.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                currTask = new Task(60000, "Goblin", 2000, goblinTable, goblinRS, goblinRV, getApplicationContext());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                String currTaskJSON = currTask.toJSONString();
                editor.putString("currTask", currTaskJSON);
                editor.apply();
                startActivity(new Intent(CombatTaskSelection.this, MainActivity.class));
            }
        });

    }

    public Item[] createGoblinTable(){
        Item table[] = new Item[10];

        table[0] = new Item(5, 1, "Bones", "Bones are for burying!", 1);
        table[1] = new Item(5, 1, "Bones", "Bones are for burying!", 1);
        table[2] = new Item(5, 1, "Bones", "Bones are for burying!", 1);
        table[3] = new Item(5, 1, "Bones", "Bones are for burying!", 1);
        table[4] = new Item(5, 1, "Bones", "Bones are for burying!", 1);
        table[5] = new Item(5, 1, "Bones", "Bones are for burying!", 1);
        table[6] = new Item(5, 1, "Bones", "Bones are for burying!", 1);
        table[7] = new Item(5, 1, "Bones", "Bones are for burying!", 1);
        table[8] = new Item(69696969, 1001, "Blue Partyhat", "Holy Shit WTF", 1);
        table[9] = new Item(11000, 100, "Rune Medium Helmet", "Rune med sux", 1);

        return table;
    }
}
