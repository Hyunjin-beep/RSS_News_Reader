package com.example.assignment7_hc;

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
    SharedPreferences preferences;
    boolean shouldExecuteOnResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        cb_fin.setOnClickListener(new clickedCB(MainActivity.pref_fin, MainActivity.cb_fin_state, R.id.cb_fin ));
        cb_cbc.setOnClickListener(new clickedCB(MainActivity.pref_cbc, MainActivity.cb_cbc_key, R.id.cb_cbc ));
        cb_cbc.setOnClickListener(new clickedCB(MainActivity.pref_abc, MainActivity.cb_cbc_key, R.id.cb_abc ));

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
//                Toast.makeText(SettingDetails.this, Boolean.toString(clickedCB.isChecked()), Toast.LENGTH_SHORT).show();
            } else{
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.putBoolean(key, clickedCB.isChecked());
                editor.apply();
//                Toast.makeText(SettingDetails.this, Boolean.toString(clickedCB.isChecked()), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("setting", "onpause");
//        cb_fin = findViewById(R.id.cb_fin);
//        boolean cb_fin_state = cb_fin.isChecked();
//        if(cb_fin_state){
//            preferences.edit().putBoolean("checkboxstate_fin",true).apply();
////            Toast.makeText(SettingDetails.this, "pause"+Boolean.toString(cb_fin_state), Toast.LENGTH_SHORT).show();
//        } else{
//            preferences.edit().putBoolean("checkboxstate_fin",false).apply();
////            Toast.makeText(SettingDetails.this, Boolean.toString(cb_fin_state), Toast.LENGTH_SHORT).show();
//        }

        savingState(getSharedPreferences(MainActivity.pref_fin, MODE_PRIVATE), MainActivity.cb_fin_state, R.id.cb_fin);
        savingState(getSharedPreferences(MainActivity.pref_cbc, MODE_PRIVATE), MainActivity.cb_cbc_key, R.id.cb_cbc);
        savingState(getSharedPreferences(MainActivity.pref_abc, MODE_PRIVATE), MainActivity.cb_abc_key, R.id.cb_abc);

    }

    public void savingState(SharedPreferences preferences, String key, int cb_id){
        CheckBox cb_num = findViewById(cb_id);
        boolean cb_checked_state = cb_num.isChecked();
        if(cb_num.isChecked()){
            preferences.edit().putBoolean(key,true).apply();
            Toast.makeText(SettingDetails.this, "setting - pause "+cb_num.isChecked(), Toast.LENGTH_SHORT).show();
        } else{
            preferences.edit().putBoolean(key,false).apply();
            Toast.makeText(SettingDetails.this, "setting - pause "+cb_num.isChecked(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adaptingPref(getSharedPreferences(MainActivity.pref_fin, MODE_PRIVATE), MainActivity.cb_fin_state, R.id.cb_fin);
        adaptingPref(getSharedPreferences(MainActivity.pref_cbc, MODE_PRIVATE), MainActivity.cb_cbc_key, R.id.cb_cbc);
        adaptingPref(getSharedPreferences(MainActivity.pref_abc, MODE_PRIVATE), MainActivity.cb_abc_key, R.id.cb_abc);
        Toast.makeText(SettingDetails.this, "resume ", Toast.LENGTH_SHORT).show();


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
