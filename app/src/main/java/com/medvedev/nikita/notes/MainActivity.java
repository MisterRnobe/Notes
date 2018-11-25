package com.medvedev.nikita.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.medvedev.nikita.notes.utils.SessionManager;
import com.medvedev.nikita.notes.utils.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity {
    private SessionManager session;
    private TextView textView;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        textView = findViewById(R.id.outputText);
        /*pb = findViewById(R.id.progressbar);
        pb.setVisibility(ProgressBar.VISIBLE);*/
        fab.setOnClickListener(this::logoutUser);//(view -> logoutUser());
    }



    @Override
    protected void onStop() {
        super.onStop();
        session.setLogin(false);
    }

    private void logoutUser(View v) {
        session.setLogin(false);
        SharedPreferencesManager sp = SharedPreferencesManager.getInstance();
        sp.clearUserPreferences();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void openNewNoteActivity(View v)
    {
        Intent intent = new Intent(this, NewNoteActivity.class);
        startActivity(intent);
        //finish();
    }

}
