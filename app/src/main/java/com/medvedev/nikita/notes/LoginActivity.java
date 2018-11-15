package com.medvedev.nikita.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText password, login;
    private static final String TAG = LoginActivity.class.getCanonicalName();

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
                    Toast.makeText(getApplicationContext(), "Please, enter Password!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please, enter Login!", Toast.LENGTH_LONG).show();
            }
            onLogin();
        });
    }

    protected void onLogin()
    {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(i);
        this.finish();
    }

    private void checkLogin(String login, String password){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Called onDestroy()");
    }

}
