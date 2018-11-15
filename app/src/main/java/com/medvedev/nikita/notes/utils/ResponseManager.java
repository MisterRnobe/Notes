package com.medvedev.nikita.notes.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medvedev.nikita.notes.LoginActivity;
import com.medvedev.nikita.notes.MainActivity;

public class ResponseManager {

    public static void checkLoginResponse(Context mContext, String jsonString){
        JSONObject jsonObject = JSON.parseObject(jsonString);
        if (jsonObject.getInteger("status")!=1) {
            SessionManager session = new SessionManager(mContext);
            session.setLogin(true);
            SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager();
            sharedPreferencesManager.clearUserPreferences(mContext);
            sharedPreferencesManager.insertUserPreferences(mContext, jsonObject.getString("token"));
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            mContext.startActivity(intent);
            ((Activity)mContext).finish();
        } else {
            Log.e(LoginActivity.TAG, Integer.toString(jsonObject.getInteger("message")));
            Toast.makeText(mContext, Integer.toString(jsonObject.getInteger("message")), Toast.LENGTH_SHORT).show();
        }
    }
}
