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
import com.medvedev.nikita.notes.R;


public class ResponseManager {

    public static void checkLoginResponse(Context mContext, String jsonString) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        if (jsonObject.getInteger("status") != 1) {
            SessionManager session = new SessionManager(mContext);
            session.setLogin(true);
            SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager();
            sharedPreferencesManager.clearUserPreferences(mContext);
            sharedPreferencesManager.insertUserPreferences(mContext, jsonObject.getJSONObject("body").getString("token"));
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            mContext.startActivity(intent);
            ((Activity) mContext).finish();
        } else {
            String errorMsg = mContext.getResources().getString(ErrorManager.errorToResID(jsonObject.getInteger("message")));
            Log.e(LoginActivity.TAG, errorMsg);
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_LONG).show();
        }
    }

    public static void checkRegisterResponse(Context mContext, String jsonString) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        if (jsonObject.getInteger("status") != 1) {
            Toast.makeText(mContext, R.string.success_register, Toast.LENGTH_LONG).show();
            SessionManager session = new SessionManager(mContext);
            session.setLogin(true);
            SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager();
            sharedPreferencesManager.clearUserPreferences(mContext);
            sharedPreferencesManager.insertUserPreferences(mContext, jsonObject.getJSONObject("body").getString("token"));
            Intent intent = new Intent(mContext,
                    MainActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).finish();

        } else {
            String errorMsg = mContext.getResources().getString(ErrorManager.errorToResID(jsonObject.getInteger("message")));
            Log.e(LoginActivity.TAG, errorMsg);
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_LONG).show();

        }
    }
}
