package com.medvedev.nikita.notes.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.medvedev.nikita.notes.MainActivity;
import com.medvedev.nikita.notes.R;
import com.medvedev.nikita.notes.objects.Body;
import com.medvedev.nikita.notes.objects.LoginPasswordData;
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
    public static final int OK = 0;
    public static final int ERROR = 1;

    private static final Map<String, Command> commandMap = new TreeMap<>();

    static {
        commandMap.put(REGISTER, new Command(REGISTER, Request.Method.POST));
        commandMap.put(LOGIN, new Command(LOGIN, Request.Method.POST));
        commandMap.put(ADD_NOTE, new Command(ADD_NOTE, Request.Method.POST));
        commandMap.put(GET_NOTES, new Command(GET_NOTES, Request.Method.GET));
        commandMap.put(TOKEN_LOGIN, new Command(TOKEN_LOGIN, Request.Method.POST));
    }

    //Другие запросы можно строить по такому же принципу
    public static void loginRequest(Context mContext, LoginPasswordData body, Consumer<String> callback) {

        doRequest(LOGIN, (l, t) ->
                        callback.accept(t.getToken()),
                (req, errCode) -> {
                    ((Activity)mContext).findViewById(R.id.progressbar).setVisibility(View.INVISIBLE);
                    Toast.makeText(mContext, "Ошибка! " + mContext.getResources().getString(ErrorManager.errorToResID(errCode)), Toast.LENGTH_LONG).show();
                }, body,
                Token.class);
    }

    public static void requestNotes(NotesRequest r, Context context, Consumer<Notes> callback) {
        doRequest(GET_NOTES, (req, notes) ->
        {
            callback.accept(notes);
        }, (req1, errCode) -> {
            ((Activity)context).findViewById(R.id.progressbar).setVisibility(View.INVISIBLE);
            Toast.makeText(context, "Ошибка! " + context.getResources().getString(ErrorManager.errorToResID(errCode)), Toast.LENGTH_LONG).show();
        }, r, Notes.class);
    }

    /**
     * Запрос, осуществляющий авторизацию при помощи токена
     * Если токен верный, сервер вернет новый токен и откроет MainActivity
     * Если токен неверный, оставит на логин активити
     */

    public static void tokenRequest(Context mContext, Token body, Consumer<String> callback) {
        doRequest(TOKEN_LOGIN,
                (l, t) ->
                        callback.accept(t.getToken()),
                (params, error) -> {
                    ((Activity) mContext).findViewById(R.id.progressbar).setVisibility(View.INVISIBLE);
                    Log.e("Token Request", mContext.getResources().getString(ErrorManager.errorToResID(error)));
                },
                body,
                Token.class);
    }

    public static void regRequest(Context mContext, RegisterData body, Consumer<String> callback) {
        doRequest(REGISTER,
                (l, t) ->
                        callback.accept(t.getToken()),
                (params, error) -> {
                    ((Activity) mContext).findViewById(R.id.progressbar).setVisibility(View.INVISIBLE);
                    Toast.makeText(mContext, ErrorManager.errorToResID(error), Toast.LENGTH_LONG).show();
                },
                body,
                Token.class);
    }

    /**
     * Шаблон, на основе которого можно построить конкретные запросы
     *
     * @param function  - Запрашиваемая функция, одна из объявленных в этом классе
     * @param onSuccess - Callback, вызываемый, когда приходит ответ со статусом OK
     * @param onError   - Callback, вызываемый, когда приходит ответ со статусом ERROR
     * @param params    - Параметры, передаваемые в запросе
     * @param clazz     - Класс, к которому нужно привести тело ответа (поле body)
     * @param <E>       - Класс, объект которого передается как параметры запроса
     * @param <T>       - см. clazz
     */
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
