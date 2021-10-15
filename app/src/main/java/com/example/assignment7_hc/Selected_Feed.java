package com.example.assignment7_hc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Selected_Feed extends AppCompatActivity {

    ListView lvRSS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_feed);

        lvRSS = findViewById(R.id.lv_RSS_Feed);

        ChannelAdapter channelAdapter = new ChannelAdapter(this, R.layout.channel_layout, RSSParseHandler.list);

        lvRSS.setAdapter(channelAdapter);



        lvRSS.setOnItemClickListener(feedClicked);

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