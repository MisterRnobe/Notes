package com.medvedev.nikita.notes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.medvedev.nikita.notes.utils.AppController;
import com.medvedev.nikita.notes.utils.RequestManager;
import com.medvedev.nikita.notes.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private Button btnRegister;
    private TextView btnLinkToLogin;
    private EditText inputLogin;
    private EditText inputName;
    private EditText inputSurname;
    private EditText inputEmail;
    private EditText inputPassword;
    private SessionManager session;
    private Context mContext = this;
    private CheckBox checkBox;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            Intent intent = new Intent(RegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_register);
        inputLogin = findViewById(R.id.loginInput);
        inputName = findViewById(R.id.nameInput);
        inputSurname = findViewById(R.id.surnameInput);
        inputEmail = findViewById(R.id.emailInput);
        inputPassword = findViewById(R.id.passwordInput);
        btnRegister = findViewById(R.id.register);
        btnLinkToLogin = findViewById(R.id.link_to_login_activity);
        checkBox = findViewById(R.id.showPassCheckBox);
        btnRegister.setOnClickListener(v -> {
                    String login = inputLogin.getText().toString().trim();
                    String name = inputName.getText().toString().trim();
                    String surname = inputSurname.getText().toString().trim();
                    String email = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();
                    if (!login.isEmpty()) {
                        if (!name.isEmpty()) {
                            if (!surname.isEmpty()) {
                                if (!email.isEmpty()) {
                                    if (verifyEmail(email)) {
                                        if (!password.isEmpty()) {
                                            RequestManager.registerRequest(mContext, login, name, surname, email, password);
                                        } else {
                                            Toast.makeText(mContext,
                                                    R.string.empty_password, Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    } else {
                                        Toast.makeText(mContext, R.string.invalid_email, Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(mContext, R.string.empty_email, Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(mContext, R.string.empty_surname, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(mContext,
                                    R.string.empty_name, Toast.LENGTH_LONG)
                                    .show();
                        }
                    } else {
                        Toast.makeText(mContext,
                                R.string.empty_login, Toast.LENGTH_LONG)
                                .show();
                    }
                }
        );
        btnLinkToLogin.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),
                    LoginActivity.class);
            startActivity(i);
            finish();
        });
        checkBox.setOnClickListener(v->{
            boolean checked = ((CheckBox)v).isChecked();
            switch (v.getId()){
                case R.id.showPassCheckBox:
                    if (checked)
                        inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    else
                        inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    break;
            }
        });

    }



    private boolean verifyEmail(String email) {
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
