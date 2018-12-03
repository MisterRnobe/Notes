package com.medvedev.nikita.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.medvedev.nikita.notes.utils.ErrorManager;
import com.medvedev.nikita.notes.utils.SessionManager;
import com.medvedev.nikita.notes.utils.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity {
    private SessionManager session;
    private TextView textView;
    private ProgressBar pb;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::openNewNoteActivity);

        //Token token = new Token().setToken(SharedPreferencesManager.getInstance().getToken());
      //  RequestManager.requestNotes(new NotesRequest().setCount(20).setOffset(0).setToken(token.getToken()), this::onGetNotes,this::onRequestNotesError);

        //List<Note> notes = Arrays.asList(new Note().setTitle("Тайтл1"), new Note().setTitle("ээ блэт"));
        //NoteAdapter noteAdapter = new NoteAdapter(this, notes);
        //View include = findViewById(R.layout.content_main);
       // listView = findViewById(R.id.include);
        //listView.setAdapter(noteAdapter);
    }
    protected void onRequestNotesError(int errCode){

        Toast.makeText(this, ErrorManager.errorToResID(errCode), Toast.LENGTH_LONG).show();
    }
  /*  protected void onGetNotes(Notes notes) {
        //dbSetNotes(notes);
        List<Note> noteList = notes.getNotes();
        NoteAdapter noteAdapter = new NoteAdapter(this, noteList);
        listView.setAdapter(noteAdapter);
    }*/


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
    private void openNote(int id)
    {
        // TODO: 02.12.2018 Implement
    }

}
