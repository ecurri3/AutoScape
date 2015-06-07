package com.evan.autoscape.autoscape;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by EvanOwns on 3/21/2015.
 */
public class TaskChoosingActivity extends ActionBarActivity {

    Button combatTask;
    Button gatheringTask;
    Button productionTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_choosing);

        combatTask = (Button) findViewById(R.id.combatTask);
        gatheringTask = (Button) findViewById(R.id.gatheringTask);
        productionTask = (Button) findViewById(R.id.productionTask);

        combatTask.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(TaskChoosingActivity.this, CombatTaskSelection.class));
            }
        });
    }
}
