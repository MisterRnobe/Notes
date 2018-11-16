package com.medvedev.nikita.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.medvedev.nikita.notes.utils.SessionManager;

public class MainActivity extends AppCompatActivity {
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> logoutUser());
    }


    @Override
    protected void onStop() {
        super.onStop();
        session.setLogin(false);
    }

    private void logoutUser() {
        session.setLogin(false);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
