package com.medvedev.nikita.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.medvedev.nikita.notes.objects.Note;
import com.medvedev.nikita.notes.utils.DBManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewNoteActivity extends AppCompatActivity {
    private EditText title, noteText;
    private FloatingActionButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Intent intent = getIntent();
        title = findViewById(R.id.addnote_title);
        title.setText(intent.getStringExtra("title"));
        noteText = findViewById(R.id.addnote_text);
        noteText.setText(intent.getStringExtra("noteText"));
        btn = findViewById(R.id.addNote);
        btn.setOnClickListener(this::saveChanges);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    //мое
    private void saveChanges(View v){
        int id = getIntent().getIntExtra("note_id",-1);
        Note note = new Note().setId(id).setTitle(title.getText().toString()).setNote(noteText.getText().toString()).setCreated(Calendar.getInstance().getTimeInMillis());
        DBManager myDB = new DBManager();
        myDB.dbAddNote(note);
        //TODO: add request to server (NEED TIMESTAMP OR NOT?)
        finish();
    }//мое
}
