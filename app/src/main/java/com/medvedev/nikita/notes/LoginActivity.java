package com.medvedev.nikita.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.medvedev.nikita.notes.utils.AppController;
import com.medvedev.nikita.notes.utils.SessionManager;


import java.util.Map;
import java.util.TreeMap;

public class LoginActivity extends AppCompatActivity {
    private EditText password, login;
    private static final String TAG = LoginActivity.class.getCanonicalName();
    private SessionManager session;
    private final Context mContext = this;
    private static final int OK = 0;
    private static final int ERROR = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(TAG,"Called onCreate(...)");
        password = findViewById(R.id.passwordInput);
        login = findViewById(R.id.loginInput);

        Button b = findViewById(R.id.button5);
        b.setOnClickListener(v-> {
            Log.i(TAG, "Login: "+login.getText().toString()+", password: "+password.getText().toString());
            String login_text = login.getText().toString().trim();
            String password_text = password.getText().toString().trim();
            if (!login_text.isEmpty()) {
                if (!password_text.isEmpty()) {
                    checkLogin(login_text, password_text);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.empty_password, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.empty_login, Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void onLogin()
    {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(i);
        this.finish();
    }

    private void checkLogin(String login, String password) {
        String tag_string_req = "req_login";
        String req_login_url = "http://nikitamedvedev.ddns.net:8080/login";
        Log.i(TAG, "Request send to server");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                req_login_url, response -> handleRequest(JSON.parseObject(response)), error -> {
            Log.e(TAG, "Error: " + error.getMessage());
            Toast.makeText(getApplicationContext(),
                    error.getMessage(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new TreeMap<>();
                params.put("login", login);
                params.put("password", password);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Called onDestroy()");
    }
    protected void handleRequest(JSONObject json)
    {
        String text = "";
        if (json.getInteger("status") == OK)
        {
            text = "Успешно залогинился. Токен: "+ json.getJSONObject("body").getString("token");
        }
        else
        {
            text = "Произошла ошибка. Код: "+json.getString("message");
        }
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

}
