package com.example.assignment7_hc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity<setOnClickListener> extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        View lv_one = findViewById(R.id.rl_one);
        lv_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadAndParseRSS("https://feeds.24.com/articles/fin24/tech/rss", "fin").execute();
            }
        });

        View lv_two = findViewById(R.id.rl_two);
        lv_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadAndParseRSS("https://www.cbc.ca/cmlink/rss-topstories", "cbc").execute();
            }
        });

        View lv_third = findViewById(R.id.rl_third);
        lv_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadAndParseRSS(" https://abcnews.go.com/abcnews/topstories", "abc").execute();
            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    public class DownloadAndParseRSS extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        public String urlst;
        public String channelName;

        public DownloadAndParseRSS(String url, String channelName) {
            this.urlst = url;
            this.channelName = channelName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Busy");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Intent intent = new Intent(MainActivity.this, Selected_Feed.class);
            Bundle extras = new Bundle();
            extras.putString("channelName", channelName);
            Log.d("mv", channelName);
            intent.putExtras(extras);
            startActivity(intent);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String news24RSS = urlst;
            URL rssURL = null;
            HttpsURLConnection connection = null;
            InputStream inputStream = null;


            try {
                rssURL = new URL(news24RSS);
                connection = (HttpsURLConnection) rssURL.openConnection();
                inputStream = connection.getInputStream();

            } catch (IOException e) {
                e.printStackTrace();
            }

            SAXParserFactory spf = SAXParserFactory.newInstance();

            try {
                SAXParser saxParser = spf.newSAXParser();
                RSSParseHandler rssParseHandler = new RSSParseHandler();
                saxParser.parse(inputStream, rssParseHandler);

            } catch (SAXException | ParserConfigurationException | IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

}