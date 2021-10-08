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

import java.util.List;

public class ChannelAdapter extends ArrayAdapter<Channel> {

    private Context context;
    private int channel_items_id;

    public ChannelAdapter(@NonNull Context context, int channel_items_id, @NonNull List<Channel> channels) {
        super(context, channel_items_id, channels);

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

        TextView tvTitle = convertView.findViewById(R.id.tv_channel_title);
        TextView tvYear = convertView.findViewById(R.id.tv_channel_year);

        Channel channel = getItem(position);

        tvTitle.setText(channel.getTitle());
        tvYear.setText(Integer.toString(channel.getYear()));



        return convertView;
    }
}
