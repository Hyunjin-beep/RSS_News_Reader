package com.example.assignment7_hc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    ListView lvRSS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvRSS = findViewById(R.id.lv_RSS_Feed);
        ChannelAdapter channelAdapter = new ChannelAdapter(this, R.layout.channel_layout, Channel.generateChannel());

        lvRSS.setAdapter(channelAdapter);

        new DownloadAndParseRSS().execute();
    }

    public class DownloadAndParseRSS extends AsyncTask<Void, Void, Void>{

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Busy");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            progressDialog.dismiss();
        }

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
            return null;
        }
    }


}