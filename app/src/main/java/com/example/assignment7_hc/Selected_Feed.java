package com.example.assignment7_hc;

import static com.example.assignment7_hc.R.style.AppTheme;
import static com.example.assignment7_hc.R.style.AppTheme2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Selected_Feed extends AppCompatActivity {

    ListView lvRSS;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayAdapter<Channel> channelArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean light_btn = changeTheme(getSharedPreferences(MainActivity.pref_light, MODE_PRIVATE), MainActivity.cb_theme_light_key, AppTheme);
        setTheme(light_btn? AppTheme2 : AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_feed);

        Toolbar myToolbar =  findViewById(R.id.my_toolbar_second);
        setSupportActionBar(myToolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        lvRSS = findViewById(R.id.lv_RSS_Feed);

       ChannelAdapter channelAdapter = new ChannelAdapter(this, R.layout.channel_layout, RSSParseHandler.list);
        // ChannelAdapter channelAdapter = new ChannelAdapter(getApplicationContext(), R.layout.font_size, RSSParseHandler.list);
        //FontStyleAdapter fontStyleAdapter = new FontStyleAdapter(this, R.layout.channel_layout, RSSParseHandler.list);


        lvRSS.setAdapter(channelAdapter);
        //lvRSS.setAdapter(channelAdapter);

        lvRSS.setOnItemClickListener(feedClicked);

        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        channelArrayAdapter = new ArrayAdapter<>(this, R.layout.channel_layout, RSSParseHandler.list);
        lvRSS.setAdapter(channelArrayAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("second", "refresh" );
                //new MainActivity.DownloadAndParseRSS("https://feeds.24.com/articles/fin24/tech/rss", "fin").execute();
                reload();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void reload() {
        Toast.makeText(Selected_Feed.this, "refresh", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        overridePendingTransition(15, 15);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(15, 15);
        startActivity(intent);
    }

    public boolean changeTheme(SharedPreferences preferences, String key, int themeName){
        boolean checkStateSelected = preferences.getBoolean(key, true);
        return checkStateSelected;
    }

    @Override
    protected void onResume() {
        super.onResume();
        adjustFontSize(getSharedPreferences(MainActivity.pref_14, MODE_PRIVATE), MainActivity.cb_14_key, 16);
        adjustFontSize(getSharedPreferences(MainActivity.pref_16, MODE_PRIVATE), MainActivity.cb_16_key,20);
        adjustFontSize(getSharedPreferences(MainActivity.pref_18, MODE_PRIVATE), MainActivity.cb_18_key, 23);

        changeFontStyle(getSharedPreferences(MainActivity.pref_mono, MODE_PRIVATE), MainActivity.cb_theme_mono_key, Typeface.MONOSPACE);
        changeFontStyle(getSharedPreferences(MainActivity.pref_bold, MODE_PRIVATE), MainActivity.cb_theme_bold_key, Typeface.SERIF);
    }

    public void adjustFontSize(SharedPreferences preferences, String key, int size){
        boolean checkState = preferences.getBoolean(key, true);
        if(checkState){
            FontStyleAdapter fontStyleAdapter = new FontStyleAdapter(this, R.layout.channel_layout, RSSParseHandler.list, size);
            lvRSS.setAdapter(fontStyleAdapter);
        }
    }

    public void changeFontStyle(SharedPreferences preferences, String key, Typeface fontStyle){
        boolean checkState = preferences.getBoolean(key, true);
        if(checkState){
            FontAdapter fontAdapter = new FontAdapter(this, R.layout.channel_layout, RSSParseHandler.list, fontStyle);
            lvRSS.setAdapter(fontAdapter);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.menu_refresh){
            reload();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_selected, menu);
        return super.onCreateOptionsMenu(menu);
    }

    AdapterView.OnItemClickListener feedClicked = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Channel channel = (Channel) adapterView.getItemAtPosition(position);
            Intent intent = getIntent();
            int imgData = 0;

            Intent intentFromSF = new Intent(Selected_Feed.this, DetailsPage.class);
            Bundle extras = new Bundle();
            extras.putString("title", channel.getTitle());
            extras.putString("desp", channel.getDesp());
            extras.putString("pubdate", channel.getPubDate());



            if(intent.getExtras().containsKey("channelName")){
                if(intent.getStringExtra("channelName").equals("fin")){
                    imgData = R.drawable.fin24;
                } else if(intent.getStringExtra("channelName").equals("cbc")){
                    imgData = R.drawable.cbc;
                } else if(intent.getStringExtra("channelName").equals("abc")){
                    imgData = R.drawable.abcnews_pearl_stacked;
                }

                Log.d("img - sf in if", String.valueOf(imgData));
            }

            extras.putInt("img", imgData);
            Log.d("img from seleected", String.valueOf(imgData));
            intentFromSF.putExtras(extras);
            startActivity(intentFromSF);

        }
    };


}