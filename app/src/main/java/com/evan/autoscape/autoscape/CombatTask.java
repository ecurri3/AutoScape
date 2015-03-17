package com.evan.autoscape.autoscape;

import android.content.Context;

/**
 * Created by EvanOwns on 3/16/2015.
 */
public class CombatTask extends Task {

    private Monster monster;

    public CombatTask(int min, Context c, Monster monst){

        super(min, c);
        this.monster = monst;
    }
}
