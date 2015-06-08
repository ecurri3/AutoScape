package com.evan.autoscape.autoscape;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * Created by EvanOwns on 6/6/2015.
 */
public class InventoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_layout);

        GridLayout gv = (GridLayout) findViewById(R.id.gridlayout);
        int currentRow = 0; // keep track of current row of grid layout

        /*
        Loop through entire inventory array and print out contents of each index
         */
        for(int i=0; i<28; i++){
            // dont add a textview if inventory index is null
            if(MainActivity.player.inventory[i] != null) {

                TextView tv = new TextView(this);
                tv.setSingleLine(false); // enables the use of indents '\t'

                // create message to be displayed
                String message = "Inventory Slot: " + (i+1) +
                                 "\n\n\tItem Name: " + MainActivity.player.inventory[i].name +
                                 "\n\tQuantity: " + MainActivity.player.inventory[i].quantity +
                                 "\n";

                // customize text color, size
                tv.setText(message);
                tv.setTextAppearance(this, android.R.style.TextAppearance_Medium);
                tv.setTextColor(Color.YELLOW);

                // customize parameters of textview element
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                params.width = GridLayout.LayoutParams.WRAP_CONTENT;
                params.columnSpec = GridLayout.spec(0);
                params.rowSpec = GridLayout.spec(currentRow);

                tv.setLayoutParams(params); // apply parameters
                gv.addView(tv); // add to grid layout
                currentRow++; // increment current row
            }
        }
    }
}
