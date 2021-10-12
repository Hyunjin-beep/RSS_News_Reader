package com.example.assignment7_hc;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class RSSParseHandler extends DefaultHandler {
    Boolean inItem = false;
    Boolean inTitle = false;
    Boolean inDesc = false;
    StringBuilder sbForTitle;
    StringBuilder sbForDesc;
    public static ArrayList<Channel> channels;
    static ArrayList<String> titleList;
    static ArrayList<String> descList;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
//        Log.d("HC", "start document");
        titleList = new ArrayList<String>();
        descList = new ArrayList<String>();
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
        }

    }

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
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if(inItem && inTitle){
            sbForTitle.append(ch,start,length);
        } else if(inItem && inDesc){
            sbForDesc.append(ch,start,length);
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
