package com.example.assignment7_hc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.SAXException;
import android.content.SharedPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity<setOnClickListener> extends AppCompatActivity {

    SharedPreferences preferences;
    boolean shouldExecuteOnResume;
    public static final String pref_fin = "pref_fin";
    public static final String pref_cbc = "pref_cbc";
    public static final String pref_abc = "pref_abc";
    public static final String cb_fin_state = "checkboxstat_fin";
    public static final String cb_cbc_key = "checkboxstat_cbc";
    public static final String cb_abc_key = "checkboxstat_abc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shouldExecuteOnResume = false;

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
    protected void onResume() {
        super.onResume();

        adaptingPref(getSharedPreferences(MainActivity.pref_fin, MODE_PRIVATE), MainActivity.cb_fin_state, R.id.rl_one);
        adaptingPref(getSharedPreferences(MainActivity.pref_cbc, MODE_PRIVATE), MainActivity.cb_cbc_key, R.id.rl_two);
        adaptingPref(getSharedPreferences(MainActivity.pref_abc, MODE_PRIVATE), MainActivity.cb_abc_key, R.id.rl_third);
    }


    public void adaptingPref(SharedPreferences preferences, String key, int lv_id){
        boolean checkState = preferences.getBoolean(key, true);
        View lv_num = findViewById(lv_id);
        Toast.makeText(MainActivity.this, "main "+ checkState, Toast.LENGTH_SHORT).show();
        if(checkState){
            lv_num.setVisibility(View.VISIBLE);
        } else{
            lv_num.setVisibility(View.GONE);
        }

    }



    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.menu_setting){
            Intent intent = new Intent(MainActivity.this, SettingDetails.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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