package jolliffe.a415.csc.groupproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.CheckBox;

/**
 * Created by Aaron on 3/28/2017.
 */

public class Settings extends AppCompatActivity implements CheckBox.OnClickListener {

    protected CheckBox darkCheck;
    protected CheckBox memory;

    protected boolean isDark = false;
    protected boolean hasMem = true;

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ContextThemeWrapper w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        isDark = sharedpreferences.getBoolean("isDark", false);
        hasMem = sharedpreferences.getBoolean("hasMem", false);
        if (isDark) w = new ContextThemeWrapper(this, R.style.AppTheme_Dark);
        else w = new ContextThemeWrapper(this, R.style.AppTheme);
        getTheme().setTo(w.getTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        darkCheck = (CheckBox) findViewById(R.id.darkCheck);
        darkCheck.setOnClickListener((View.OnClickListener) this);
        darkCheck.setChecked(isDark);

        memory = (CheckBox) findViewById(R.id.memoryCheck);
        memory.setOnClickListener((View.OnClickListener) this);
        memory.setChecked(hasMem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.darkCheck:
                setPreferences();
                finish();
                startActivity(getIntent());
                break;
            case R.id.memoryCheck:
                setPreferences();
                finish();
                startActivity(getIntent());
                break;
        }
    }

    // send settings to SharedPreferences
    public void setPreferences()
    {
        isDark = darkCheck.isChecked();
        hasMem = memory.isChecked();
        editor = sharedpreferences.edit();
        editor.putBoolean("isDark", isDark);
        editor.putBoolean("hasMem", hasMem);
        editor.commit();
    }
}
