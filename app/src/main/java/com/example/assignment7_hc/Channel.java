package com.example.assignment7_hc;

import java.util.ArrayList;

public class Channel {
    private String title;
    private String pubDate;
    private String desp;



    public Channel(String title){
        this.title = title;

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

}