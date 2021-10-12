package com.example.assignment7_hc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    ListView lvRSS;
    ArrayList<Channel> channelItems;
    private ArrayList<Channel> channels;
    ArrayList<String> titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

//        lvRSS = findViewById(R.id.lv_RSS_Feed);
//        ChannelAdapter channelAdapter = new ChannelAdapter(this, R.layout.channel_layout, Channel.generateChannel());

//        lvRSS.setAdapter(channelAdapter);
        Button btn = findViewById(R.id.btn_test);
        btn.setOnClickListener(test);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private View.OnClickListener test = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new DownloadAndParseRSS().execute();
        }
    };


    public class DownloadAndParseRSS extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            String news24RSS = "https://feeds.24.com/articles/fin24/tech/rss";
            URL rssURL = null;
            HttpsURLConnection connection = null;
            InputStream inputStream = null;

            try {
                rssURL = new URL(news24RSS);
                connection = (HttpsURLConnection) rssURL.openConnection();
                inputStream = connection.getInputStream();

            }catch (IOException e) {
                e.printStackTrace();
            }

            SAXParserFactory spf = SAXParserFactory.newInstance();

            try{
                SAXParser saxParser = spf.newSAXParser();
                RSSParseHandler rssParseHandler = new RSSParseHandler();
                saxParser.parse(inputStream, rssParseHandler);
            } catch (SAXException | ParserConfigurationException | IOException e) {
                e.printStackTrace();
            }

            startActivity(new Intent(MainActivity.this, Selected_Feed.class));
            return null;
        }
    }


}