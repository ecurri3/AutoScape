package com.evan.autoscape.autoscape;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by EvanOwns on 6/5/2015.
 */
public class PlayerStatsActivity extends ActionBarActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_stats);

        TextView total = (TextView)findViewById(R.id.totalLevel);
        TextView combat = (TextView)findViewById(R.id.combatLevel);

        TextView attack = (TextView)findViewById(R.id.textView1);
        TextView strength = (TextView)findViewById(R.id.textView2);
        TextView defence = (TextView)findViewById(R.id.textView3);

        TextView ranged = (TextView)findViewById(R.id.textView4);
        TextView magic = (TextView)findViewById(R.id.textView5);
        TextView prayer = (TextView)findViewById(R.id.textView6);

        TextView runecrafting = (TextView)findViewById(R.id.textView7);
        TextView hitpoints = (TextView)findViewById(R.id.textView8);
        TextView agility = (TextView)findViewById(R.id.textView9);

        TextView herblore = (TextView)findViewById(R.id.textView10);
        TextView theiving = (TextView)findViewById(R.id.textView11);
        TextView crafting = (TextView)findViewById(R.id.textView12);

        total.setText("Total Level: " + MainActivity.player.totalLevel);
        combat.setText("Combat Level: " + MainActivity.player.combatLevel);

        attack.setText("Attack EXP: " + MainActivity.player.getExp(Player.STAT_ATTACK) +
                       "\nAttack Level: " + MainActivity.player.getLevel(MainActivity.player.getExp(Player.STAT_ATTACK)));
        strength.setText("Strength EXP: " + MainActivity.player.getExp(Player.STAT_STRENGTH) +
                         "\nStrength Level: " + MainActivity.player.getLevel(MainActivity.player.getExp(Player.STAT_STRENGTH)));
        defence.setText("Defence EXP: " + MainActivity.player.getExp(Player.STAT_DEFENCE) +
                        "\nDefence Level: " + MainActivity.player.getLevel(MainActivity.player.getExp(Player.STAT_DEFENCE)));

        ranged.setText("Ranged EXP: " + MainActivity.player.getExp(Player.STAT_RANGED) +
                "\nRanged Level: " + MainActivity.player.getLevel(MainActivity.player.getExp(Player.STAT_RANGED)));
        magic.setText("Magic EXP: " + MainActivity.player.getExp(Player.STAT_MAGIC) +
                "\nMagic Level: " + MainActivity.player.getLevel(MainActivity.player.getExp(Player.STAT_MAGIC)));
        prayer.setText("Prayer EXP: " + MainActivity.player.getExp(Player.STAT_PRAYER) +
                "\nPrayer Level: " + MainActivity.player.getLevel(MainActivity.player.getExp(Player.STAT_PRAYER)));

        runecrafting.setText("Runecrafting EXP: " + MainActivity.player.getExp(Player.STAT_RUNECRAFTING) +
                "\nRunecrafting Level: " + MainActivity.player.getLevel(MainActivity.player.getExp(Player.STAT_RUNECRAFTING)));
        hitpoints.setText("Hitpoints EXP: " + MainActivity.player.getExp(Player.STAT_HITPOINTS) +
                "\nHitpoints Level: " + MainActivity.player.getLevel(MainActivity.player.getExp(Player.STAT_HITPOINTS)));
        agility.setText("Agility EXP: " + MainActivity.player.getExp(Player.STAT_AGILITY) +
                "\nAgility Level: " + MainActivity.player.getLevel(MainActivity.player.getExp(Player.STAT_AGILITY)));

        herblore.setText("Herblore EXP: " + MainActivity.player.getExp(Player.STAT_HERBLORE) +
                "\nHerblore Level: " + MainActivity.player.getLevel(MainActivity.player.getExp(Player.STAT_HERBLORE)));
        theiving.setText("Theiving EXP: " + MainActivity.player.getExp(Player.STAT_THEIVING) +
                "\nTheiving Level: " + MainActivity.player.getLevel(MainActivity.player.getExp(Player.STAT_THEIVING)));
        crafting.setText("Crafting EXP: " + MainActivity.player.getExp(Player.STAT_CRAFTING) +
                "\nCrafting Level: " + MainActivity.player.getLevel(MainActivity.player.getExp(Player.STAT_CRAFTING)));
    }
}
