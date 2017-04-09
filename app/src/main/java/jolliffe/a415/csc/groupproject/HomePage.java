package jolliffe.a415.csc.groupproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomePage extends AppCompatActivity implements Button.OnClickListener {

    public boolean isDark = false;  // determines the current theme being used
    public boolean hasMem = true;   // determines if the app should remember the last entries
    private boolean flag = false;

    //ui objects
    private Button addButton;
    private Button trackButton;
    private TextView appTitleText;

    //preferences/data saving
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ContextThemeWrapper w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        resetTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // get references to the widgets
        addButton = (Button)findViewById(R.id.add_button);
        trackButton = (Button)findViewById(R.id.track_button);
        appTitleText = (TextView)findViewById(R.id.main_title_text);


        // set the listeners
        addButton.setOnClickListener(this);
        trackButton.setOnClickListener(this);

        display();
    }


    // listeners for menu items
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:     // open the settings activity
                startActivity(new Intent(HomePage.this, Settings.class));
                flag = true;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case(R.id.add_button):
                startActivity(new Intent(HomePage.this, MainActivity.class));
                //switch to mainactivity
                break;
            case(R.id.track_button):
                //switch to graph
                break;

        }
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
            //todo: place things here if we want our app to remember any sharedpreferences values.
            //example: myReps = sharedpreferences.getInt("myReps", myReps);
        }
    }


    // update the activity's display,
    public void display() {
       //set texts for all ui objects
        addButton.setText("Input GainZ!");
        trackButton.setText("Track GainZ");
        appTitleText.setText("Do You Even Lift?");
        // if "Remember Entries" is checked, save the input values
        if (hasMem)
        {
            editor = sharedpreferences.edit();
            //todo place things to be remembered
            //example: editor.putInt("myReps", myReps);
            editor.commit();
        }
    }
}
