package com.medvedev.nikita.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.medvedev.nikita.notes.objects.Note;
import com.medvedev.nikita.notes.objects.Notes;
import com.medvedev.nikita.notes.objects.Token;
import com.medvedev.nikita.notes.utils.RequestManager;
import com.medvedev.nikita.notes.utils.SessionManager;
import com.medvedev.nikita.notes.utils.SharedPreferencesManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SessionManager session;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        textView = findViewById(R.id.ouputText);

        RequestManager.requestNotes(new Token().setToken(SharedPreferencesManager.getInstance().getToken()), this::onGetNotes);

        fab.setOnClickListener(view -> logoutUser());
    }

    protected void onGetNotes(Notes notes)
    {
        List<Note> noteList = notes.getNotes();
        StringBuilder text = new StringBuilder();
        for (Note n: noteList)
        {
            text.append("Title: ");
            text.append(n.getTitle());
            text.append('\n');
            text.append("Note: ");
            text.append(n.getNote());
            text.append('\n');
        }
        textView.setText(text.toString());
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
