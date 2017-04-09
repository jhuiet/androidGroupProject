package jolliffe.a415.csc.groupproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    public boolean isDark = false;  // determines the current theme being used
    public boolean hasMem = true;   // determines if the app should remember the last entries
    private boolean flag = false;

    public int myReps = 0;         // the number of reps
    public int mySets = 0;       // the amount of sets

    // define variables for the widgets
    private TextView repsLabel;     // "Reps"
    private TextView repsTextView;  // Number of Reps
    private Button repsUpButton;    // Reps+
    private Button repsDownButton;  // Reps-
    private TextView setsLabel;     // "Sets"
    private TextView setsTextView;  // Number of Sets
    private Button setsUpButton;    // Sets+
    private Button setsDownButton;  // Sets-

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

        // set the listeners
        repsUpButton.setOnClickListener(this);
        repsDownButton.setOnClickListener(this);
        setsUpButton.setOnClickListener(this);
        setsDownButton.setOnClickListener(this);

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
                if (myReps > 0) myReps = myReps - 5;
                display();
                break;
            case R.id.UpButton:
                myReps = myReps + 5;
                display();
                break;
            case R.id.SetsDownButton:
                if (mySets > 0) mySets = mySets - 5;
                display();
                break;
            case R.id.SetsUpButton:
                mySets = mySets + 5;
                display();
                break;
        }
    }

    // update the activity's display
    public void display() {
        repsTextView.setText(Integer.toString(myReps));
        setsTextView.setText(Integer.toString(mySets));
        // if "Remember Entries" is checked, save the input values
        if (hasMem)
        {
            editor = sharedpreferences.edit();
            editor.putInt("myReps", myReps);
            editor.putInt("mySets", mySets);
            editor.commit();
        }
    }
}
