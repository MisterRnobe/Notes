package com.medvedev.nikita.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.medvedev.nikita.notes.objects.LoginPasswordData;
import com.medvedev.nikita.notes.objects.Token;
import com.medvedev.nikita.notes.utils.RequestManager;
import com.medvedev.nikita.notes.utils.SessionManager;
import com.medvedev.nikita.notes.utils.SharedPreferencesManager;

public class LoginActivity extends AppCompatActivity {
    private EditText password, login;
    public static final String TAG = LoginActivity.class.getCanonicalName();
    private SessionManager session;
    private final Context mContext = this;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pb = findViewById(R.id.progressbar);
        pb.setVisibility(ProgressBar.VISIBLE);
        RequestManager.tokenRequest(mContext, new Token().setToken(SharedPreferencesManager.getInstance().getToken()),this::onLogin);

        Log.i(TAG, "Called onCreate(...)");
        password = findViewById(R.id.passwordInput);
        login = findViewById(R.id.loginInput);
        TextView reg_link = findViewById(R.id.link_to_reg_activity);
        Button b = findViewById(R.id.button5);
        session = new SessionManager(mContext);
        if (session.isLoggedIn()) {
            Intent intent = new Intent(this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }
        reg_link.setOnClickListener(v -> {
            Log.i(TAG, "Redirecting to register activity");
            Intent intent = new Intent(mContext, RegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            startActivity(intent);
            finish();
        });
        b.setOnClickListener(v -> {
            String loginText = login.getText().toString().trim();
            String passwordText = password.getText().toString().trim();
            if (loginText.isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.empty_login, Toast.LENGTH_LONG).show();
            } else if (passwordText.isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.empty_password, Toast.LENGTH_LONG).show();
            } else {
                pb.setVisibility(ProgressBar.VISIBLE);
                RequestManager.loginRequest(mContext, new LoginPasswordData().setLogin(loginText).setPassword(passwordText), this::onLogin);
            }
        });
    }

    protected void onLogin(String token)
    {
        SessionManager session = new SessionManager(mContext);
        session.setLogin(true);
        SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager.getInstance();
        sharedPreferencesManager.clearUserPreferences();
        sharedPreferencesManager.insertUserPreferences(token);
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        mContext.startActivity(intent);
        pb = findViewById(R.id.progressbar);
        pb.setVisibility(ProgressBar.INVISIBLE);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Called onDestroy()");
    }

}
