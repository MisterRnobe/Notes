package com.medvedev.nikita.notes.utils;

import android.util.Base64;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medvedev.nikita.notes.objects.RegisterData;


public class TokenManager {


    public static RegisterData getUserData() {
        String jwtToken = SharedPreferencesManager.getInstance().getToken();
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedBody = split_string[1];
        byte[] base64url = Base64.decode(base64EncodedBody, Base64.URL_SAFE);
        String body = new String(base64url);
        JSONObject userdataJson = JSON.parseObject(body);
        RegisterData reg = new
                RegisterData()
                .setLogin(userdataJson.getString("login"))
                .setEmail(userdataJson.getString("email"))
                .setName(userdataJson.getString("first_name"))
                .setSurname(userdataJson.getString("second_name"));
        return reg;
    }
}
