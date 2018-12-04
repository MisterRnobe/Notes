package com.medvedev.nikita.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.medvedev.nikita.notes.utils.ResultCodes;

public class NoteActivity extends AppCompatActivity {

    private EditText title, noteText;
    private FloatingActionButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Intent intentFromFragment = getIntent();
        title = findViewById(R.id.note_title);
        title.setText(intentFromFragment.getStringExtra("title"));
        noteText = findViewById(R.id.note_text);
        noteText.setText(intentFromFragment.getStringExtra("noteText"));
        btn = findViewById(R.id.saveChanges);
        btn.setOnClickListener((v)->returnResult(intentFromFragment.getIntExtra("note_id",-1)));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void returnResult(int id){
        Intent intent = new Intent();
        intent.putExtra("title",title.getText().toString());
        intent.putExtra("note",noteText.getText().toString());
        intent.putExtra("note_id",id);
        setResult(ResultCodes.EDIT_NOTE,intent);
        finish();
    }


}
