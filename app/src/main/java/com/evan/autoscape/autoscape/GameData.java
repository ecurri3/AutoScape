package com.evan.autoscape.autoscape;

import android.content.Context;

/**
 * Created by EvanOwns on 5/21/2015.
 */
public class GameData {

    private Context context;
    public CombatTask[] combat_tasks = new CombatTask[10];
    Monster goblin = new Monster(0,200,0, "Goblin", 1);

    public GameData(){

        for(int i=0; i<10; i++){
            combat_tasks[i] = new CombatTask(1, context, goblin);
        }
    }
}
