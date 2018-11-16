package com.medvedev.nikita.notes.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestManager {

    public static void loginRequest(Context mContext,String login, String password){
        String TAG = "Login Request";
        Log.i(TAG, "Request send to server");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                CommandManager.LOGIN, response -> {
            Log.d(TAG, response);
            ResponseManager.checkLoginResponse(mContext,response);
        }, error -> {
            Log.e(TAG, "Error: " + error.getMessage());
            Toast.makeText(mContext,
                    error.getMessage(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("login", login);
                params.put("password", password);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);
    }
    public static void registerRequest(Context mContext, String login, String name, String surname, String email, String password){
        String TAG = "Register request";
        Log.i(TAG,"Request send to server");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                CommandManager.REGISTER, response -> {
                    Log.d(TAG, "Register Response: " + response);
                    ResponseManager.checkRegisterResponse(mContext,response);
                }, error -> {
                    Log.e(TAG, "Registration Error: " + error.getMessage());
                    Toast.makeText(mContext,
                            error.getMessage(), Toast.LENGTH_LONG).show();
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("login", login);
                params.put("password", password);
                params.put("first_name", name);
                params.put("second_name",surname);
                params.put("email",email);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);
    }
}
