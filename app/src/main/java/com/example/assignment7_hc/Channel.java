package com.example.assignment7_hc;


import java.util.ArrayList;

public class Channel {
    private String title;
    private int pubDate;

    public Channel(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear(){
        return this.pubDate;
    }

    public void setPubDate(int pubDate) {
        this.pubDate = pubDate;
    }

    public static ArrayList<Channel> generateChannel(){
        ArrayList<Channel> channelList = new ArrayList<>();

        channelList.add(new Channel("new24") );

        return channelList;
    }
}
