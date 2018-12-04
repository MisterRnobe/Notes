package com.medvedev.nikita.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.medvedev.nikita.notes.utils.ResultCodes;

public class NewNoteActivity extends AppCompatActivity {
    private EditText title, noteText;
    private FloatingActionButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        title = findViewById(R.id.addnote_title);
        noteText = findViewById(R.id.addnote_text);
        btn = findViewById(R.id.addNote);
        btn.setOnClickListener(this::returnResult);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }



    private void returnResult(View v){
        Intent intent = new Intent();
        intent.putExtra("title",title.getText().toString());
        intent.putExtra("note",noteText.getText().toString());
        setResult(ResultCodes.ADD_NOTE,intent);
        finish();
    }
}
