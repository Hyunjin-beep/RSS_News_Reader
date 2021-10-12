package com.example.assignment7_hc;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RSSParseHandler extends DefaultHandler {
    Boolean inItem = false;
    Boolean inTitle = false;
    StringBuilder sb;
    public static ArrayList<Channel> channels;
    static ArrayList<String> titleList;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
//        Log.d("HC", "start document");
        titleList = new ArrayList<String>();
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
//            channel = new Channel();
        } else if(qName.equals("title") && inItem){
            sb = new StringBuilder();
            inTitle = true;

        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if(qName.equalsIgnoreCase("title") && inTitle){
            inTitle = false;
            titleList.add(sb.toString());
        } else if(qName.equalsIgnoreCase("item")){
            inItem = false;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if(inItem && inTitle){
            sb.append(ch,start,length);
        }
    }

    public static ArrayList<Channel> generateChannel(){
        channels = new ArrayList<Channel>();
        for(int i=0; i < titleList.size(); i++){
            channels.add(new Channel(titleList.get(i)));
        }

        ArrayList<Channel> list = new ArrayList<>();

        list.addAll(channels);

        return list;
    }


}
