package com.medvedev.nikita.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.medvedev.nikita.notes.objects.Note;
import com.medvedev.nikita.notes.objects.RegisterData;
import com.medvedev.nikita.notes.objects.Token;
import com.medvedev.nikita.notes.utils.ErrorManager;
import com.medvedev.nikita.notes.utils.RequestManager;
import com.medvedev.nikita.notes.utils.ResultCodes;
import com.medvedev.nikita.notes.utils.SessionManager;
import com.medvedev.nikita.notes.utils.SharedPreferencesManager;
import com.medvedev.nikita.notes.utils.TokenManager;

public class MainActivity extends AppCompatActivity {
    private SessionManager session;
    private TextView textView, headerName, headerEmail;
    private ProgressBar pb;
    private ListView listView;
    private DrawerLayout drawerLayout;
    RegisterData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        userData = TokenManager.getUserData();
        headerName = headerView.findViewById(R.id.header_name);
        headerEmail = headerView.findViewById(R.id.header_email);
        headerName.setText(userData.getName()+" "+userData.getSurname());
        headerEmail.setText(userData.getEmail());
        session = new SessionManager(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::openNewNoteActivity);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0)
            return;
        if (resultCode == ResultCodes.EDIT_NOTE) {
            String title = data.getStringExtra("title");
            String noteText = data.getStringExtra("note");
            int note_id = data.getIntExtra("note_id", -1);
            RequestManager.updateNoteRequest(
                    new Note()
                            .setId(note_id)
                            .setTitle(title)
                            .setNote(noteText)
                            .setToken(SharedPreferencesManager.getInstance().getToken()),
                    this::updateNote,
                    this::onError);
        } else if (resultCode == ResultCodes.ADD_NOTE) {
            String title = data.getStringExtra("title");
            String note = data.getStringExtra("note");
            RequestManager.addNoteRequest(
                    new Note()
                            .setTitle(title)
                            .setNote(note)
                            .setToken(SharedPreferencesManager.getInstance().getToken()),
                    this::addNote,
                    this::onError);
        } else if (resultCode == ResultCodes.DELETE_NOTE) {
            int id = data.getIntExtra("note_id", -1);
            RequestManager.deleteNoteRequest(new
                            Note()
                            .setId(id)
                            .setToken(SharedPreferencesManager.getInstance().getToken()),
                    this::deleteNote,
                    this::onError);
        } else {
            Toast.makeText(this, R.string.bad_result, Toast.LENGTH_LONG).show();
        }

    }

    private void deleteNote(int note_id) {
        FragmentList fragmentList = (FragmentList) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragmentList.deleteNote(note_id);
    }

    private void updateNote(Note note) {
        FragmentList fragmentList = (FragmentList) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragmentList.updateNote(note);
    }

    private void addNote(Note note) {
        FragmentList fragmentList = (FragmentList) getSupportFragmentManager().findFragmentById(R.id.fragment);

        fragmentList.addNote(note);
    }

    private void onError(int errCode) {
        if(errCode==ErrorManager.EXPIRED_TOKEN){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            this.startActivity(intent);
            finish();
        }
        Toast.makeText(this, ErrorManager.errorToResID(errCode), Toast.LENGTH_LONG).show();

    }

    private void openNewNoteActivity(View v) {
        Intent intent = new Intent(this, NewNoteActivity.class);
        startActivityForResult(intent, ResultCodes.ADD_NOTE);
    }


}
