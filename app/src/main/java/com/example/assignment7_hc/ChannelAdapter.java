package com.example.assignment7_hc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChannelAdapter extends ArrayAdapter<Channel> {

    ArrayList<Channel> channels;
    private final Context context;
    private final int channel_items_id;

    public ChannelAdapter(@NonNull Context context, int channel_items_id, @NonNull ArrayList<Channel> channels) {
        super(context, channel_items_id, channels);

        this.channels = channels;
        this.context = context;
        this.channel_items_id = channel_items_id;


    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(channel_items_id, parent, false);
        }

        Channel current = getItem(position);

        TextView tvTitle = convertView.findViewById(R.id.tv_channel_title);


        tvTitle.setText(current.getTitle());
//        Channel channel = getItem(position);
//
//        tvTitle.setText(channel.getTitle());
//        tvYear.setText(Integer.toString(channel.getYear()));



        return convertView;
    }
}
