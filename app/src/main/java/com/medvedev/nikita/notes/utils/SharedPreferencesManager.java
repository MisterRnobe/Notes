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
    private static String sp_name = "ThisUser";
    private static String token = "token";

    private SharedPreferencesManager(){}


    public void clearUserPreferences(Context context){
        sp = getPrefs(context);
        sp.edit().clear().apply();
    }
    private SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
    }

    public void insertUserPreferences(Context context, String tokenData) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(token, tokenData);
        editor.apply();
    }
    public String getToken()
    {
        return token;
    }
}

