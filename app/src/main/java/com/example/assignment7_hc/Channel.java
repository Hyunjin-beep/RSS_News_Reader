package com.example.assignment7_hc;

import java.util.ArrayList;

public class Channel {
    private String title;
    private String pubDate;



    public Channel(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear(){
        return this.pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

}