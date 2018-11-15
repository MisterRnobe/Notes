package com.medvedev.nikita.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.medvedev.nikita.notes.utils.RequestManager;
import com.medvedev.nikita.notes.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private EditText password, login;
    public static final String TAG = LoginActivity.class.getCanonicalName();
    private SessionManager session;
    private final Context mContext = this;
    private static final int OK = 0;
    private static final int ERROR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(mContext);
        if (session.isLoggedIn()) {
            Intent intent = new Intent(this,
                    MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);
        Log.i(TAG, "Called onCreate(...)");
        password = findViewById(R.id.passwordInput);
        login = findViewById(R.id.loginInput);
        TextView reg_link = findViewById(R.id.link_to_reg_activity);
        Button b = findViewById(R.id.button5);
        reg_link.setOnClickListener(v -> {
            Log.i(TAG, "Redirecting to register activity");
            Intent intent = new Intent(mContext, RegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            startActivity(intent);
            finish();
        });
        b.setOnClickListener(v -> {
            String login_text = login.getText().toString().trim();
            String password_text = password.getText().toString().trim();
            if (!login_text.isEmpty()) {
                if (!password_text.isEmpty()) {
                    RequestManager.loginRequest(mContext,login_text,password_text);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.empty_password, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.empty_login, Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Called onDestroy()");
    }

}
