package jolliffe.a415.csc.groupproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.GONE;
import static jolliffe.a415.csc.groupproject.MainActivity.workoutType.BENCH_PRESS;
import static jolliffe.a415.csc.groupproject.MainActivity.workoutType.PUSH_UPS;
import static jolliffe.a415.csc.groupproject.MainActivity.workoutType.SIT_UPS;
import static jolliffe.a415.csc.groupproject.MainActivity.workoutType.SQUATS;
//todo replace input styles with pickers
//todo set sharedpreferences to store the spinner workoutSpin position, and the sets/reps potentially
//todo can we set different sharedpreference things to store a value for each workout type?
//todo example: save pushups reps/sets independent of squat reps/sets/weight
public class MainActivity extends AppCompatActivity implements Button.OnClickListener, AdapterView.OnItemSelectedListener, EditText.OnEditorActionListener {

    public boolean isDark = false;  // determines the current theme being used
    public boolean hasMem = true;   // determines if the app should remember the last entries
    private boolean flag = false;


    WorkoutsDB workoutDB = new WorkoutsDB(this);
    StringBuilder sb = new StringBuilder();

    public int pushupReps = 0;         // the number of reps
    public int pushupSets = 0;       // the amount of sets


    public int squatReps = 0;         // the number of reps
    public int squatSets = 0;       // the amount of sets
    public float squatWeight = 45;   //the ammount of weight



    public int crunchReps = 0;         // the number of reps
    public int crunchSets = 0;       // the amount of sets

    public int benchReps = 0;         // the number of reps
    public int benchSets = 0;       // the amount of sets
    public float benchWeight = 45;   //the ammount of weight







    public enum workoutType{PUSH_UPS,SIT_UPS,SQUATS,BENCH_PRESS}
    public workoutType workout;
    // define variables for the widgets
    private Spinner workoutSpin;    //spinner to define workout
    private TextView repsLabel;     // "Reps"
    private TextView repsTextView;  // Number of Reps
    private Button repsUpButton;    // Reps+
    private Button repsDownButton;  // Reps-
    private TextView setsLabel;     // "Sets"
    private TextView setsTextView;  // Number of Sets
    private Button setsUpButton;    // Sets+
    private Button setsDownButton;  // Sets-
    private TextView weightLabel;     // "Weight"
    private EditText weightEditText;  // Number of weight
    private Button weightUpButton;    // weight+
    private Button weightDownButton;  // weight-
    private Button submitButton; // Submit

    // set up SharedPreferences
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ContextThemeWrapper w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        resetTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to the widgets
        repsLabel = (TextView) findViewById(R.id.repsLabel);
        repsTextView = (TextView) findViewById(R.id.repsTextView);
        repsDownButton = (Button) findViewById(R.id.DownButton);
        repsUpButton = (Button) findViewById(R.id.UpButton);
        setsLabel = (TextView) findViewById(R.id.setsLabel);
        setsTextView = (TextView) findViewById(R.id.setsTextView);
        setsDownButton = (Button) findViewById(R.id.SetsDownButton);
        setsUpButton = (Button) findViewById(R.id.SetsUpButton);
        weightLabel = (TextView) findViewById(R.id.weightLabel);
        weightEditText = (EditText) findViewById(R.id.weightTextView);
        weightDownButton = (Button) findViewById(R.id.weightDownButton);
        weightUpButton = (Button) findViewById(R.id.weightUpButton);
        workoutSpin = (Spinner) findViewById(R.id.workoutSpin);
        submitButton = (Button) findViewById(R.id.submitButton);


        hideWeight();   //hide weight attribute since pushups don't have one by default


        // set the listeners
        repsUpButton.setOnClickListener(this);
        repsDownButton.setOnClickListener(this);
        setsUpButton.setOnClickListener(this);
        setsDownButton.setOnClickListener(this);
        weightDownButton.setOnClickListener(this);
        weightUpButton.setOnClickListener(this);
        workoutSpin.setOnItemSelectedListener(this);
        submitButton.setOnClickListener(this);
        weightEditText.setOnEditorActionListener(this);
        display();
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        if (flag) {         // if flag is true, restart the activity to update the theme
            flag = false;   // reset flag to false
            finish();
            startActivity(getIntent());
        }
    }

    // open an options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    // listeners for menu items
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:     // open the settings activity
                startActivity(new Intent(MainActivity.this, Settings.class));
                flag = true;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // get the desired theme from SharedPreferences, then set the theme
    public void resetTheme()
    {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        isDark = sharedpreferences.getBoolean("isDark", false);
        hasMem = sharedpreferences.getBoolean("hasMem", false);
        if (isDark) w = new ContextThemeWrapper(this, R.style.AppTheme_Dark);
        else w = new ContextThemeWrapper(this, R.style.AppTheme);
        getTheme().setTo(w.getTheme());
        // if "Remember Entries" is checked, reload the numbers previously entered instead of resetting fields to 0

        if(hasMem)
        {
            myReps = sharedpreferences.getInt("myReps", myReps);
            mySets = sharedpreferences.getInt("mySets", mySets);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DownButton:
                if (myReps > 0) myReps = myReps - 1;
                display();
                break;
            case R.id.UpButton:
                myReps = myReps + 1;
                display();
                break;
            case R.id.SetsDownButton:
                if (mySets > 0) mySets = mySets - 1;
                display();
                break;
            case R.id.SetsUpButton:
                mySets = mySets + 1;
                display();
                break;
            case R.id.weightDownButton:
                if(myWeight > 0)
                    myWeight = myWeight - 2.5f;
                display();
                break;
            case R.id.weightUpButton:
                myWeight = myWeight + 2.5f;
                display();
                break;
            case R.id.submitButton:


                Workout newWorkout = new Workout(mySets, myReps, myWeight, 1, workout.toString());
                long insertID = workoutDB.insertWorkout(newWorkout);

                if (insertID > 0){

                    Toast.makeText(getApplicationContext(), "Workout inserted.", Toast.LENGTH_SHORT).show();
                    sb.append("Row inserted! Insert id: " + insertID + "\n");

                }
                else{
                    Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                break;




        }
    }

    // update the activity's display
    public void display() {
        repsTextView.setText(Integer.toString(myReps));
        setsTextView.setText(Integer.toString(mySets));
        weightEditText.setText(Double.toString(myWeight));
        // if "Remember Entries" is checked, save the input values
        if (hasMem)
        {
            editor = sharedpreferences.edit();
            editor.putInt("myReps", myReps);
            editor.putInt("mySets", mySets);
            editor.putFloat("myWeight", myWeight);
            editor.commit();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
            case 0:
                hideWeight();
                workout = PUSH_UPS;
                break;
            case 1:
                hideWeight();
                workout = SIT_UPS;
                break;
            case 2:
                showWeight(view);
                workout = SQUATS;
                break;
            case 3:
                showWeight(view);
                workout = BENCH_PRESS;
                break;
        }
    }

    private void showWeight(View view) {
        weightLabel.setVisibility(view.VISIBLE);
        weightEditText.setVisibility(view.VISIBLE);
        weightDownButton.setVisibility(view.VISIBLE);
        weightUpButton.setVisibility(view.VISIBLE);
    }


    private void hideWeight() {
        weightLabel.setVisibility(GONE);
        weightEditText.setVisibility(GONE);
        weightDownButton.setVisibility(GONE);
        weightUpButton.setVisibility(GONE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
            myWeight = Float.parseFloat(weightEditText.getText().toString());
        myWeight = myWeight - myWeight%2.5f;
        display();
        return false;
    }
}
