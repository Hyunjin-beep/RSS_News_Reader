package com.example.assignment7_hc;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class RSSParseHandler extends DefaultHandler {

    Boolean inItem = false;
    Boolean inTitle = false;
    StringBuilder sb;

    Channel currentChannelItem = null;
    ArrayList<Channel> resultRSSList = new ArrayList<Channel>();

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        Log.d("HC", "start document");
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.d("HC", "end document");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        Log.d("HC", "start element " + qName);

        if(qName.equals("item")){

            inItem = true;
            currentChannelItem = new Channel();
            //https://www.usna.edu/Users/cs/adina/teaching/it472/spring2020/course/page.php?shortname=mobileos&id=21
            Log.d("HC", "start element " + qName);
        } else if(qName.equals("title")){
            inTitle = true;
            sb = new StringBuilder();

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        Log.d("HC", "end element " + qName);

        if(qName.equals("item")){
            // do something
            // create a new object to store data
            // create an empty list
            inItem = false;
            Log.d("HC", "end element " + qName);
        } else if(qName.equals("title")){
            inTitle = false;
            Log.d("HC", "Content " + sb);


//            podCast.setDescription(sb);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if(inItem && inTitle){
            sb.append(ch, start, length);
        }

    }
}
