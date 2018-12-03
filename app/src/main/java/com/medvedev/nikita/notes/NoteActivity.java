package com.medvedev.nikita.notes;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.medvedev.nikita.notes.objects.Note;
import com.medvedev.nikita.notes.objects.Notes;
import com.medvedev.nikita.notes.utils.DBManager;

public class NoteActivity extends AppCompatActivity {

    private EditText title, noteText;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        //мое
        Intent intent = getIntent();
        title = findViewById(R.id.note_title);
        title.setText(intent.getStringExtra("title"));
        noteText = findViewById(R.id.note_text);
        noteText.setText(intent.getStringExtra("noteText"));
        btn = findViewById(R.id.saveChanges);
        btn.setOnClickListener(this::saveChanges);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    //мое
    private void saveChanges(View v){
        int id = getIntent().getIntExtra("note_id",-1);
        Note note = new Note().setId(id).setTitle(title.getText().toString()).setNote(noteText.getText().toString());
        DBManager myDB = new DBManager();
        myDB.dbUpdateNote(note);
        //TODO: update request to server
        finish();
    }//мое

}
