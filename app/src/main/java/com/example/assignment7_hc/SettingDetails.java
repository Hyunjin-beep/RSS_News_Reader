package com.example.assignment7_hc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class SettingDetails extends AppCompatActivity {

    CheckBox cb_fin;
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

        cb_fin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked) {
                    String settings = "fin";

                    preferences = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putBoolean("checkboxstate", isChecked);
                    editor.apply();

                    Toast.makeText(SettingDetails.this, Boolean.toString(isChecked), Toast.LENGTH_SHORT).show();
                } else{
                    SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putBoolean("checkboxstate", isChecked);
                    editor.apply();

                    Toast.makeText(SettingDetails.this, Boolean.toString(isChecked), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("setting", "onpause");
        cb_fin = findViewById(R.id.cb_fin);
        boolean cb_fin_state = cb_fin.isChecked();
        if(cb_fin_state){
            preferences.edit().putBoolean("checkboxstate",true).apply();
            Toast.makeText(SettingDetails.this, "pause"+Boolean.toString(cb_fin_state), Toast.LENGTH_SHORT).show();
        } else{
            preferences.edit().putBoolean("checkboxstate",false).apply();
            Toast.makeText(SettingDetails.this, Boolean.toString(cb_fin_state), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        cb_fin = findViewById(R.id.cb_fin);
        String key = "checkboxstate";
        preferences = getSharedPreferences("pref", MODE_PRIVATE);
        boolean checkState = preferences.getBoolean("checkboxstate", true);
        cb_fin.setChecked(checkState);
        Toast.makeText(SettingDetails.this, "resume "+Boolean.toString(checkState), Toast.LENGTH_SHORT).show();





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
