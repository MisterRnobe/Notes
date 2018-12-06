package com.medvedev.nikita.notes.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.medvedev.nikita.notes.objects.Body;
import com.medvedev.nikita.notes.objects.LoginPasswordData;
import com.medvedev.nikita.notes.objects.Note;
import com.medvedev.nikita.notes.objects.Notes;
import com.medvedev.nikita.notes.objects.NotesRequest;
import com.medvedev.nikita.notes.objects.RegisterData;
import com.medvedev.nikita.notes.objects.Token;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class RequestManager {
    private static final String server_url = "http://nikitamedvedev.ddns.net:8080/";
    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String ADD_NOTE = "add_note";
    public static final String GET_NOTES = "get_notes";
    public static final String TOKEN_LOGIN = "update_token";
    public static final String UPDATE_NOTE = "edit_note";
    public static final String REMOVE_NOTE = "remove_note";
    public static final int OK = 0;
    public static final int ERROR = 1;

    private static final Map<String, Command> commandMap = new TreeMap<>();

    static {
        commandMap.put(REGISTER, new Command(REGISTER, Request.Method.POST));
        commandMap.put(LOGIN, new Command(LOGIN, Request.Method.POST));
        commandMap.put(ADD_NOTE, new Command(ADD_NOTE, Request.Method.POST));
        commandMap.put(GET_NOTES, new Command(GET_NOTES, Request.Method.GET));
        commandMap.put(TOKEN_LOGIN, new Command(TOKEN_LOGIN, Request.Method.POST));
        commandMap.put(UPDATE_NOTE,new Command(UPDATE_NOTE,Request.Method.POST));
        commandMap.put(REMOVE_NOTE,new Command(REMOVE_NOTE,Request.Method.POST));
    }

    public static void loginRequest(LoginPasswordData body, Consumer<String> onSuccess, Consumer<Integer> onError) {

        doRequest(LOGIN,
                (l, t) -> onSuccess.accept(t.getToken()),
                (req, errCode) -> onError.accept(errCode),
                body,
                Token.class);
    }
    public static void updateNoteRequest(Note note, Consumer<Note> onSuccess, Consumer<Integer> onError) {

        doRequest(UPDATE_NOTE,
                (req, respNote) -> onSuccess.accept(new Note()
                        .setCreated(respNote.getCreated())
                        .setId(respNote.getId())
                        .setNote(respNote.getNote())
                        .setTitle(respNote.getTitle())),
                (req, errCode) ->
                        onError.accept(errCode),
                note,
                Note.class);
    }
    //TODO change req.getID with resp.getId
    public static void deleteNoteRequest(Note note, Consumer<Integer> onSuccess,Consumer<Integer> onError){
        doRequest(REMOVE_NOTE,
                (req,resp)->
                        onSuccess.accept(resp.getId()),
                (req,errCode)->
                        onError.accept(errCode),
                note,
                Note.class);
    }
    public static void addNoteRequest(Note note, Consumer<Note> onSuccess, Consumer<Integer> onError) {
        doRequest(ADD_NOTE,
                (reqNote, respNote) ->
                        onSuccess.accept(new Note()
                                .setCreated(respNote.getCreated())
                                .setId(respNote.getId())
                                .setNote(reqNote.getNote())
                                .setTitle(reqNote.getTitle())),
                (n, errCode) -> onError.accept(errCode),
                note,
                Note.class);
    }
    public static void requestNotes(NotesRequest r, Consumer<Notes> onSuccess, Consumer<Integer> onError) {
        doRequest(GET_NOTES,
                (req, notes) -> onSuccess.accept(notes),
                (req1, errCode) -> onError.accept(errCode),
                r,
                Notes.class);
    }
    public static void tokenRequest(Token body, Consumer<String> onSuccess, Consumer<Integer> onError) {
        doRequest(TOKEN_LOGIN,
                (l, t) -> onSuccess.accept(t.getToken()),
                (params, errCode) -> onError.accept(errCode),
                body,
                Token.class);
    }
    public static void regRequest(RegisterData body, Consumer<String> onSuccess, Consumer<Integer> onError) {
        doRequest(REGISTER,
                (l, t) -> onSuccess.accept(t.getToken()),
                (params, errCode) -> onError.accept(errCode),
                body,
                Token.class);
    }

    private static <E extends Body, T extends Body> void doRequest(String function, BiConsumer<E, T> onSuccess, BiConsumer<E, Integer> onError, E params, Class<T> clazz) {
        Command c = commandMap.get(function);
        if (c == null)
            return;
        Log.i("RequestManager", "Request \"" + c.getName() + "\" is sending to server...");

        MyRequest request = MyRequest.create(c.getMethod(), server_url + c.getName(),
                r -> {
                    JSONObject response = JSON.parseObject(r);
                    int code = response.getInteger("status");
                    if (code == OK)
                        onSuccess.accept(params, response.getJSONObject("body").toJavaObject(clazz));
                    else {
                        onError.accept(params, response.getInteger("message"));
                    }
                },
                e -> {
                    Log.e("Request Error", e.getMessage());
                }, params);

        AppController.getInstance().addToRequestQueue(request, c.getName());
    }

    private static class MyRequest extends StringRequest {
        private Map<String, String> params = new TreeMap<>();

        MyRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        <E extends Body> MyRequest setBody(E body) {
            this.params = body.getAsMap();
            return this;
        }

        @Override
        public Map<String, String> getParams() {

            return params;
        }

        public static MyRequest create(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, Body params) {

            if (method != Method.GET)
                return new MyRequest(method, url, listener, errorListener).setBody(params);

            StringBuilder stringParameters = new StringBuilder("?");
            Set<Map.Entry<String, String>> entries = params.getAsMap().entrySet();
            for (Map.Entry<String, String> entry : entries) {
                stringParameters.append(entry.getKey());
                stringParameters.append("=");
                stringParameters.append(entry.getValue());
                stringParameters.append("&");
            }
            //& - последний символ, надо убрать
            stringParameters.deleteCharAt(stringParameters.length() - 1);
            url = url + stringParameters.toString();
            return new MyRequest(method, url, listener, errorListener);
        }
    }

    public interface BiConsumer<T, U> {
        void accept(T var1, U var2);
    }

    public interface Consumer<T> {
        void accept(T var1);
    }
}
