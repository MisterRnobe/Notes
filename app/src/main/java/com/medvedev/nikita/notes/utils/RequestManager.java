package com.medvedev.nikita.notes.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.medvedev.nikita.notes.objects.Body;
import com.medvedev.nikita.notes.objects.LoginPasswordData;
import com.medvedev.nikita.notes.objects.Token;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class RequestManager {
    private static final String server_url = "http://nikitamedvedev.ddns.net:8080/";
    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String ADD_NOTE = "add_note";
    public static final String GET_NOTES = "get_notes";

    private static final Map<String, Command> commandMap = new TreeMap<>();
    static {
        commandMap.put(REGISTER, new Command(REGISTER, Request.Method.POST));
        commandMap.put(LOGIN, new Command(LOGIN, Request.Method.POST));
        commandMap.put(ADD_NOTE, new Command(ADD_NOTE, Request.Method.POST));
        commandMap.put(GET_NOTES, new Command(GET_NOTES, Request.Method.GET));
    }

    //Другие запросы можно строить по такому же принципу
    public static void loginRequest(Context mContext,String login, String password){

        doRequest(LOGIN, (l, t)->{
                    Toast.makeText(mContext, t.getToken(), Toast.LENGTH_LONG).show();
                }, new LoginPasswordData().setLogin(login).setPassword(password),
                Token.class);

        /*MyRequest request = new MyRequest(Request.Method.POST,
                CommandManager.LOGIN, response -> {
            Log.d(TAG, response);
            ResponseManager.checkLoginResponse(mContext,response);
        }, error -> {
            Log.e(TAG, "Error: " + error.getMessage());
            Toast.makeText(mContext,
                    error.getMessage(), Toast.LENGTH_LONG).show();
        }).setBody(new LoginPasswordData().setLogin(login).setPassword(password));
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);*/
    }
    //E - класс для передаваемых аргументов
    //T - класс для получаемого объекта
    private static<E extends Body, T extends Body> void doRequest(String function, BiConsumer<E,T> onSuccess, E params, Class<T> clazz)
    {
        Command c = commandMap.get(function);
        if (c == null)
            return;
        Log.i("RequestManager", "Request \""+c.getName()+"\" is sending to server...");

        MyRequest request = new MyRequest(Request.Method.POST, server_url + c.getName(),
                r->{
                    onSuccess.accept(params, JSON.parseObject(r).getJSONObject("body").toJavaObject(clazz));
                },
                e->{
                    // TODO: 15.11.2018 Implement
                }).setBody(params);
        AppController.getInstance().addToRequestQueue(request, c.getName());
    }
    private static class MyRequest extends StringRequest
    {
        private Map<String, String> params = new TreeMap<>();
        MyRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }
        <E extends Body> MyRequest setBody(E body)
        {
            this.params = body.getAsMap();
            return this;
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }
    public interface BiConsumer<T, U> {
        void accept(T var1, U var2);
    }
}
