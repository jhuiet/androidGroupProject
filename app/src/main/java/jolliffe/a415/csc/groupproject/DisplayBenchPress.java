package jolliffe.a415.csc.groupproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Blake on 4/26/2017.
 */

public class DisplayBenchPress extends AppCompatActivity implements TextView.OnEditorActionListener {

    private TextView tv;

    WorkoutsDB workoutDB = new WorkoutsDB(this);
    StringBuilder sb = new StringBuilder();


    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_benchpress);

        // get references to the widgets
        tv = (TextView) findViewById(R.id.benchpressTextView);


        // set the listeners
        tv.setOnEditorActionListener(this);


        // get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);


        ArrayList<Workout> workouts = workoutDB.getWorkoutWithType("BENCH_PRESS");
        for (Workout w : workouts) {
            //sb.append(p.getID() + "|" + p.getName() + "\n");
            sb.append(w.getWorkoutType() + "    " + w.getReps() + "       " + w.getSets() + "       " + w.getWeight() + "      " +
                    w.getWorkoutDate() + "\n");



        }

        tv.setText(sb.toString());


    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }
}
