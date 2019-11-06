package com.example.productsmanager2.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import com.example.productsmanager2.R;

public class SettingsActivity extends AppCompatActivity {

    private CheckBox themeCheckBox;
    private CheckBox backgroundCheckBox;
    private boolean whiteTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        whiteTheme = loadTheme();
        boolean greenBackground = loadBackground();
        setTheme(whiteTheme ? R.style.AppThemeDark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        themeCheckBox = findViewById(R.id.darkThemeCheckBox);
        themeCheckBox.setChecked(whiteTheme);
        backgroundCheckBox = findViewById(R.id.greenBackgroundCheckBox);
        backgroundCheckBox.setChecked(greenBackground);
    }

    public void saveSetting(String name, boolean toggle) {
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.
                SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name, toggle);
        editor.apply();
    }

    public boolean loadTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.
                SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getBoolean(SharedPrefs.PREF_TEXT, false);

    }

    public boolean loadBackground() {
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.
                SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getBoolean(SharedPrefs.PREF_BACKGROUND, false);
    }

    public void onThemeCheckBoxClick(View view) {
        saveSetting(SharedPrefs.PREF_TEXT, themeCheckBox.isChecked());
        setTheme(whiteTheme ? R.style.AppThemeDark : R.style.AppTheme);
        recreate();
    }

    public void onGreenBackgroundCheckBoxClick(View view) {
        saveSetting(SharedPrefs.PREF_BACKGROUND, backgroundCheckBox.isChecked());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
