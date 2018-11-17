package com.medvedev.nikita.notes.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager{
    private static SharedPreferencesManager instance;
    public static SharedPreferencesManager getInstance()
    {
        if (instance == null)
            instance = new SharedPreferencesManager();
        return instance;
    }


    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    private final static String sp_name = "ThisUser";
    private final static String token_key = "token";

    private SharedPreferencesManager(){}


    public void clearUserPreferences(){
        sp = getPrefs();
        sp.edit().clear().apply();
    }
    private SharedPreferences getPrefs() {
        return AppController.getAppContext().getSharedPreferences(sp_name, Context.MODE_PRIVATE);
    }
    public String getToken(){
        return getPrefs().getString(token_key,"");
    }

    public void insertUserPreferences(String tokenData) {
        editor = getPrefs().edit();
        editor.putString(token_key, tokenData);
        editor.apply();
    }
}

