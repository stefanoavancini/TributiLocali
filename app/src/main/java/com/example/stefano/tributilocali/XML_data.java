package com.example.stefano.tributilocali;

import android.app.Application;

import java.util.HashMap;

/**
 * Created by stefano on 31/07/2015.
 */
public class XML_data extends Application {
    String username;
    String password;
    String auth;
    String key;
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

    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setAuth(String auth){
        this.auth = auth;
    }
    public void setKey(String key){
        this.key = key;
    }

    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public String getAuth(){
        return this.auth;
    }
    public String getKey(){
        return this.key;
    }

}


