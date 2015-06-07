package com.evan.autoscape.autoscape;

/**
 * Created by EvanOwns on 3/16/2015.
 */
public class Monster {

    private int requirements;
    private int rewards;
    private int difficulty;
    private String name;
    private int ID;

    public Monster(int reqs, int rewrds, int diff, String n, int id){

        requirements = reqs;
        rewards = rewrds;
        difficulty = diff;
        name = n;
        ID = id;
    }
}
