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
import android.widget.Toast;

import com.medvedev.nikita.notes.objects.Note;
import com.medvedev.nikita.notes.objects.Notes;
import com.medvedev.nikita.notes.objects.Token;
import com.medvedev.nikita.notes.utils.DBManager;
import com.medvedev.nikita.notes.utils.ErrorManager;
import com.medvedev.nikita.notes.utils.RequestManager;
import com.medvedev.nikita.notes.utils.SharedPreferencesManager;

public class NoteActivity extends AppCompatActivity {

    private EditText title, noteText;
    private FloatingActionButton btn;
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
        btn.setOnClickListener((v)->RequestManager.updateNoteRequest(
                new Note()
                        .setId(getIntent().getIntExtra("note_id",-1))
                        .setTitle(title.getText().toString())
                        .setNote(noteText.getText().toString())
                        .setToken(SharedPreferencesManager.getInstance().getToken()),
                this::saveChanges,
                this::onUpdateError));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }



    private void onUpdateError(int errCode) {
        Toast.makeText(this,ErrorManager.errorToResID(errCode),Toast.LENGTH_LONG).show();

    }


    //мое
    //FIXME bug list doesnt upd
    private void saveChanges(Note note){
        Log.i("saveChanges",note.getTitle()+note.getId());
        DBManager myDB = new DBManager();
        myDB.dbUpdateNote(note);
        finish();
    }//мое

}
