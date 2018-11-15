package com.medvedev.nikita.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.medvedev.nikita.notes.utils.AppController;
import com.medvedev.nikita.notes.utils.SessionManager;
import com.medvedev.nikita.notes.utils.SharedPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText password, login;
    private static final String TAG = LoginActivity.class.getCanonicalName();
    private SessionManager session;
    private final Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"Called onCreate(...)");
        setContentView(R.layout.activity_login);
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
        String req_login_url = "";
        Log.i(TAG, "Request send to server");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                req_login_url, response -> {
            Log.d(TAG, response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (!jsonObject.getBoolean("error")) {
                    session.setLogin(true);
                    SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager();
                    sharedPreferencesManager.clearUserPreferences(mContext);
                    sharedPreferencesManager.insertUserPreferences(mContext, login, jsonObject.getString("token"));
                    Intent intent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e(TAG, jsonObject.getString("error_msg"));
                    Toast.makeText(getApplicationContext(), jsonObject.getString("error_msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.e(TAG, "Error: " + error.getMessage());
            Toast.makeText(getApplicationContext(),
                    error.getMessage(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
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

}
