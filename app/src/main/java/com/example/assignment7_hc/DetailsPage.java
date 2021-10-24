package com.example.assignment7_hc;

import static com.example.assignment7_hc.R.style.AppTheme;
import static com.example.assignment7_hc.R.style.AppTheme2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailsPage extends AppCompatActivity {
    static ArrayList<TextView> title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean light_btn = changeTheme(getSharedPreferences(MainActivity.pref_light, MODE_PRIVATE), MainActivity.cb_theme_light_key, AppTheme);
        setTheme(light_btn? AppTheme2 : AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        Toolbar myToolbar =  findViewById(R.id.my_toolbar_third);
        setSupportActionBar(myToolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView tvTitle = findViewById(R.id.tvTitleDP);
        TextView tvDesp = findViewById(R.id.tvDespDP);
        TextView tvPubDate = findViewById(R.id.tvPubDateDP);
        ImageView ivChannel = findViewById(R.id.ivImageDP);

        Bundle extras = getIntent().getExtras();
        String title = extras.getString("title");
        String desp = extras.getString("desp");
        String pubDate = extras.getString("pubdate");
        int img = extras.getInt("img");
        Log.d("img - dp", String.valueOf(img));

        tvTitle.setText(title);
        tvDesp.setText(desp);
        tvPubDate.setText(pubDate);
        ivChannel.setImageResource(img);
    }

    public boolean changeTheme(SharedPreferences preferences, String key, int themeName){
        return preferences.getBoolean(key, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adjustFontSize(getSharedPreferences(MainActivity.pref_14, MODE_PRIVATE), MainActivity.cb_14_key, 16);
        adjustFontSize(getSharedPreferences(MainActivity.pref_16, MODE_PRIVATE), MainActivity.cb_16_key,20);
        adjustFontSize(getSharedPreferences(MainActivity.pref_18, MODE_PRIVATE), MainActivity.cb_18_key,23);
    }

    public void adjustFontSize(SharedPreferences preferences, String key, int size){
        boolean checkState = preferences.getBoolean(key, true);
        TextView tvTitle = findViewById(R.id.tvTitleDP);
        TextView tvDesp = findViewById(R.id.tvDespDP);
        TextView tvPubDate = findViewById(R.id.tvPubDateDP);



        if(checkState){
            tvTitle.setTextSize(size);
            tvDesp.setTextSize(size-3);
            tvPubDate.setTextSize(size-5);
            Toast.makeText(DetailsPage.this, "qe", Toast.LENGTH_SHORT).show();

        }

    }
}