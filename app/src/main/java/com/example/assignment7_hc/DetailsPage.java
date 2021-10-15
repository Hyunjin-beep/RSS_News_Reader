package com.example.assignment7_hc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

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
//        Bundle bundle = getIntent().getExtras();
//        if(bundle != null){
//            int image = bundle.getInt("fin");
//            ivChannel.setImageResource(image);
//        }



    }
}