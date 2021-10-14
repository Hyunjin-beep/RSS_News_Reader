package com.example.assignment7_hc;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RSSParseHandler extends DefaultHandler {
    Boolean inItem = false;
    Boolean inTitle = false;
    Boolean inDesc = false;
    Boolean inPub = false;
    StringBuilder sb;
    public static ArrayList<Channel> channels;
    public static ArrayList<Channel> list;
    static ArrayList<String> titleList;
    static ArrayList<String> descList;
    static ArrayList<String> pubDateList;


    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
//        Log.d("HC", "start document");
        titleList = new ArrayList<>();
        descList = new ArrayList<>();
        pubDateList = new ArrayList<>();
        channels = new ArrayList<>();
        list = new ArrayList<>();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        //https://stackoverflow.com/questions/38323379/android-studio-need-rss-feed-example
        int i = 0;
        while(i != titleList.size()){
            String title = titleList.get(i);
            String desp =  descList.get(i);
            String pubDate = pubDateList.get(i);
            channels.add(new Channel(title,  desp, pubDate));
            i++;
        }

        list.addAll(channels);

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if(qName.equals("item")){
            inItem = true;
        } else if(qName.equals("title") && inItem){
            sb = new StringBuilder();
            inTitle = true;
        } else if(qName.equals("description") && inItem){
            sb = new StringBuilder();
            inDesc = true;
        } else if(qName.equals("pubDate") && inItem){
            sb = new StringBuilder();
            inPub = true;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if(qName.equalsIgnoreCase("item")){
            inItem = false;
        } else if(qName.equalsIgnoreCase("title") && inTitle){
            inTitle = false;
            titleList.add(sb.toString());

        } else if(qName.equals("description") && inDesc){
            inDesc = false;
            descList.add(sb.toString());
        } else if(qName.equals("pubDate") && inPub){
            inPub = false;
//            String formatedDate = pubDateSt.replace(" +0000", "");
//            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("MM dd, yyyy");
//            LocalDate dateTime = LocalDate.parse(formatedDate, inputFormat);
//
//            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MM dd, yyyy");
//            String pubDateResult = dateTime.format(outputFormat);
//            pubDateList.add(pubDateResult);
            String pubDateSt = sb.toString().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMMM dd, yyyy");
            Date pubdate = new Date(Date.parse(pubDateSt));
            String date = sdf.format(pubdate);
            pubDateList.add(date);
//            try {
//                date = intputFormat.parse(pubDateSt);
//                String outputText = outputFormat.format(date);
//                pubDateList.add(outputText);
//                Log.d("DATE", outputText);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if((inItem && inTitle || (inItem && inDesc) ||(inItem && inPub))){
            sb.append(ch,start,length);
        }
    }
}
