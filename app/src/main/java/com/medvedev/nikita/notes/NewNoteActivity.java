package com.medvedev.nikita.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewNoteActivity extends AppCompatActivity {
    private EditText title, note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Button b = findViewById(R.id.sendNoteButton);
        title = findViewById(R.id.titleText);
        note = findViewById(R.id.noteText);

        b.setOnClickListener(this::onClickSend);
    }
    private void onClickSend(View v)
    {
        String titleText = title.getText().toString();
        String noteText = note.getText().toString();
        // TODO: 25.11.2018 Send query

    }
}
