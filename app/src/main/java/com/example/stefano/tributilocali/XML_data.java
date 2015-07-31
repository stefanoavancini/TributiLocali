package com.example.stefano.tributilocali;

import android.app.Application;

import java.util.HashMap;

/**
 * Created by stefano on 31/07/2015.
 */
public class XML_data extends Application {
    String matricola;
    String xml;
    HashMap<String, String> Data = new HashMap<String, String>();
    public XML_data()
    {

    }
    public boolean setXMLdata(String matricola,String xml){
        Data.put(matricola,xml);

        return true;
    }
    public String getXMLdata(String matricola){
        return Data.get(matricola);

    }

}


