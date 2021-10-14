package com.example.assignment7_hc;

import java.util.ArrayList;

public class Channel {
    private String title;
    private String pubDate;
    private String desp;



    public Channel(String title, String desp, String pubDate){
        this.title = title;
        this.desp = desp;
        this.pubDate = pubDate;

    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesp(){
        return this.desp;
    }

    public void setDesp(String desp){
        this.desp = desp;
    }

    public String getPubDate(){
        return this.pubDate;
    }

    public void setPubDate(String pubDate){
        this.pubDate = pubDate;
    }

}