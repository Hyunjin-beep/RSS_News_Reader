package com.example.assignment7_hc;

import static com.example.assignment7_hc.R.style.AppTheme;
import static com.example.assignment7_hc.R.style.AppTheme2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class SettingDetails extends AppCompatActivity {

    CheckBox cb_fin;
    CheckBox cb_cbc;
    CheckBox cb_abc;

    CheckBox cb_font_14;
    CheckBox cb_font_16;
    CheckBox cb_font_18;

    CheckBox cb_theme_light;
    CheckBox cb_theme_dark;

    SharedPreferences preferences;
    boolean shouldExecuteOnResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean light_btn = changeTheme(getSharedPreferences(MainActivity.pref_light, MODE_PRIVATE), MainActivity.cb_theme_light_key, AppTheme);
        setTheme(light_btn? AppTheme2 : AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_details);
        shouldExecuteOnResume = false;
        Toolbar myToolbar =  findViewById(R.id.my_toolbar_setting);
        setSupportActionBar(myToolbar);


        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();

        cb_fin = findViewById(R.id.cb_fin);
        cb_cbc = findViewById(R.id.cb_cbc);
        cb_abc = findViewById(R.id.cb_abc);

        cb_font_14 = findViewById(R.id.cb_font_14);
        cb_font_16 = findViewById(R.id.cb_font_16);
        cb_font_18 = findViewById(R.id.cb_font_18);
        cb_theme_light = findViewById(R.id.cb_theme_light);
        cb_theme_dark = findViewById(R.id.cb_theme_dark);

        cb_fin.setOnClickListener(new clickedCB(MainActivity.pref_fin, MainActivity.cb_fin_state, R.id.cb_fin ));
        cb_cbc.setOnClickListener(new clickedCB(MainActivity.pref_cbc, MainActivity.cb_cbc_key, R.id.cb_cbc ));
        cb_cbc.setOnClickListener(new clickedCB(MainActivity.pref_abc, MainActivity.cb_cbc_key, R.id.cb_abc ));

        cb_font_14.setOnClickListener(new clickedCB(MainActivity.pref_14, MainActivity.cb_14_key, R.id.cb_font_14));
        cb_font_16.setOnClickListener(new clickedCB(MainActivity.pref_16, MainActivity.cb_16_key, R.id.cb_font_16));
        cb_font_18.setOnClickListener(new clickedCB(MainActivity.pref_18, MainActivity.cb_18_key, R.id.cb_font_18));

        cb_theme_light.setOnClickListener(new clickedCB(MainActivity.pref_light, MainActivity.cb_theme_light_key, R.id.cb_theme_light));
        cb_theme_dark.setOnClickListener(new clickedCB(MainActivity.pref_dark, MainActivity.cb_theme_dark_key, R.id.cb_theme_dark));

    }

    public class clickedCB implements View.OnClickListener{

        String pref;
        String key;
        int cb_id;

        public clickedCB(String pref, String key, int cb_id){
            this.pref = pref;
            this.key = key;
            this.cb_id = cb_id;
        }

        @Override
        public void onClick(View v) {
            CheckBox clickedCB = findViewById(cb_id);
            preferences = getSharedPreferences(pref, MODE_PRIVATE);
            if(clickedCB.isChecked()){
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.putBoolean(key, clickedCB.isChecked());
                editor.apply();
            } else{
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.putBoolean(key, clickedCB.isChecked());
                editor.apply();
            }
        }
    }

    public boolean changeTheme(SharedPreferences preferences, String key, int themeName){
        boolean checkStateSetting = preferences.getBoolean(key, true);
        return checkStateSetting;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("setting", "onpause");

        savingState(getSharedPreferences(MainActivity.pref_fin, MODE_PRIVATE), MainActivity.cb_fin_state, R.id.cb_fin);
        savingState(getSharedPreferences(MainActivity.pref_cbc, MODE_PRIVATE), MainActivity.cb_cbc_key, R.id.cb_cbc);
        savingState(getSharedPreferences(MainActivity.pref_abc, MODE_PRIVATE), MainActivity.cb_abc_key, R.id.cb_abc);
        savingState(getSharedPreferences(MainActivity.pref_14, MODE_PRIVATE), MainActivity.cb_14_key, R.id.cb_font_14);
        savingState(getSharedPreferences(MainActivity.pref_16, MODE_PRIVATE), MainActivity.cb_16_key, R.id.cb_font_16);
        savingState(getSharedPreferences(MainActivity.pref_18, MODE_PRIVATE), MainActivity.cb_18_key, R.id.cb_font_18);
        savingStateRelatedToTheme(getSharedPreferences(MainActivity.pref_light, MODE_PRIVATE), MainActivity.cb_theme_light_key, R.id.cb_theme_light);
        savingStateRelatedToTheme(getSharedPreferences(MainActivity.pref_dark, MODE_PRIVATE), MainActivity.cb_theme_dark_key, R.id.cb_theme_dark);

    }

    public void savingState(SharedPreferences preferences, String key, int cb_id){
        CheckBox cb_num = findViewById(cb_id);
        boolean cb_checked_state = cb_num.isChecked();
        if(cb_num.isChecked()){
            preferences.edit().putBoolean(key,true).apply();
        } else{
            preferences.edit().putBoolean(key,false).apply();
        }
    }

    public void savingStateRelatedToTheme(SharedPreferences preferences, String key, int cb_id){
        CheckBox cb_num = findViewById(cb_id);
        if(cb_num.isChecked()){
            preferences.edit().putBoolean(key,true).apply();
        } else{
            preferences.edit().putBoolean(key,false).apply();
        }

        Intent intent = new Intent(SettingDetails.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        adaptingPref(getSharedPreferences(MainActivity.pref_fin, MODE_PRIVATE), MainActivity.cb_fin_state, R.id.cb_fin);
        adaptingPref(getSharedPreferences(MainActivity.pref_cbc, MODE_PRIVATE), MainActivity.cb_cbc_key, R.id.cb_cbc);
        adaptingPref(getSharedPreferences(MainActivity.pref_abc, MODE_PRIVATE), MainActivity.cb_abc_key, R.id.cb_abc);
        adaptingPref(getSharedPreferences(MainActivity.pref_14, MODE_PRIVATE), MainActivity.cb_14_key, R.id.cb_font_14);
        adaptingPref(getSharedPreferences(MainActivity.pref_16, MODE_PRIVATE), MainActivity.cb_16_key, R.id.cb_font_16);
        adaptingPref(getSharedPreferences(MainActivity.pref_18, MODE_PRIVATE), MainActivity.cb_18_key, R.id.cb_font_18);
        adaptingPref(getSharedPreferences(MainActivity.pref_light, MODE_PRIVATE), MainActivity.cb_theme_light_key, R.id.cb_theme_light);
        adaptingPref(getSharedPreferences(MainActivity.pref_dark, MODE_PRIVATE), MainActivity.cb_theme_dark_key, R.id.cb_theme_dark);

    }

    public void adaptingPref(SharedPreferences preferences, String key, int cb_id){
        boolean checkState = preferences.getBoolean(key, true);
        CheckBox cb_num = findViewById(cb_id);
        cb_num.setChecked(checkState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
