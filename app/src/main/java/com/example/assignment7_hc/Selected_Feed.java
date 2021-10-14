package com.example.assignment7_hc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class Selected_Feed extends AppCompatActivity {

    ListView lvRSS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_feed);

        lvRSS = findViewById(R.id.lv_RSS_Feed);

        ChannelAdapter channelAdapter = new ChannelAdapter(this, R.layout.channel_layout, RSSParseHandler.list);

        lvRSS.setAdapter(channelAdapter);

        Intent intent = getIntent();

    }
}