package com.example.assignment7_hc;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RSSParseHandler extends DefaultHandler {
    Boolean inItem = false;
    Boolean inTitle = false;
    Boolean inDesc = false;
    Boolean inPub = false;
    StringBuilder sbForTitle;
    StringBuilder sbForDesc;
    StringBuilder sbForPub;
    public static ArrayList<Channel> channels;
    static ArrayList<String> titleList;
    static ArrayList<String> descList;
    static ArrayList<String> pubDateList;


    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
//        Log.d("HC", "start document");
        titleList = new ArrayList<String>();
        descList = new ArrayList<String>();
        pubDateList = new ArrayList<String>();
        channels = new ArrayList<Channel>();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if(qName.equals("item")){
            inItem = true;
        } else if(qName.equals("title") && inItem){
            sbForTitle = new StringBuilder();
            inTitle = true;

        } else if(qName.equals("description") && inItem){
            sbForDesc = new StringBuilder();
            inDesc = true;
        } else if(qName.equals("pubDate") && inItem){
            sbForPub = new StringBuilder();
            inPub = true;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if(qName.equalsIgnoreCase("title") && inTitle){
            inTitle = false;
            titleList.add(sbForTitle.toString());
        } else if(qName.equalsIgnoreCase("item")){
            inItem = false;
        } else if(qName.equals("description") && inDesc){
            inDesc = false;
            descList.add(sbForDesc.toString());
        } else if(qName.equals("pubDate") && inPub){
            inPub = false;
//            String formatedDate = pubDateSt.replace(" +0000", "");
//            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("MM dd, yyyy");
//            LocalDate dateTime = LocalDate.parse(formatedDate, inputFormat);
//
//            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MM dd, yyyy");
//            String pubDateResult = dateTime.format(outputFormat);
//            pubDateList.add(pubDateResult);
            String pubDateSt = sbForPub.toString().trim();
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

        if(inItem && inTitle){
            sbForTitle.append(ch,start,length);
        } else if(inItem && inDesc){
            sbForDesc.append(ch,start,length);
        } else if(inItem && inPub){
            sbForPub.append(ch,start,length);
        }
    }

    public static ArrayList<Channel> generateChannel(){
        for(int i=0; i < titleList.size(); i++){
            channels.add(new Channel(titleList.get(i),  descList.get(i), pubDateList.get(i)));
        }

        ArrayList<Channel> list = new ArrayList<>();

        list.addAll(channels);

        return list;
    }
}
