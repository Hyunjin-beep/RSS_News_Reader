package com.example.assignment7_hc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
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
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_details);

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
                if(isChecked){
                    String settings = "fin";

                    SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("checkboxstate", Boolean.parseBoolean(Boolean.toString(isChecked)));
                    editor.apply();


                    Toast.makeText(SettingDetails.this, Boolean.toString(isChecked), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
//    private View.OnClickListener saveData = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            String settings = et_settings.getText().toString();
//
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("settings", settings);
//            editor.apply();
//
//            Toast.makeText(SettingDetails.this, "saved", Toast.LENGTH_SHORT).show();
//        }
//    };





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
