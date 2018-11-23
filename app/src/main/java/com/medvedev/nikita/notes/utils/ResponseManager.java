package com.medvedev.nikita.notes.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.medvedev.nikita.notes.MainActivity;
import com.medvedev.nikita.notes.R;


public class ResponseManager {

    public static void onLogin(Context context, String token)
    {
        SessionManager session = new SessionManager(context);
        session.setLogin(true);
        SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager.getInstance();
        sharedPreferencesManager.clearUserPreferences();
        sharedPreferencesManager.insertUserPreferences(token);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
    public static void checkRegisterResponse(Context mContext, String token) {
            Toast.makeText(mContext, R.string.success_register, Toast.LENGTH_LONG).show();
            SessionManager session = new SessionManager(mContext);
            session.setLogin(true);
            SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager.getInstance();
            sharedPreferencesManager.clearUserPreferences();
            sharedPreferencesManager.insertUserPreferences(token);
            Intent intent = new Intent(mContext,
                    MainActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).finish();
    }
}
