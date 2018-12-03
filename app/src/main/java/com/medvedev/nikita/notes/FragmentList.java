package com.medvedev.nikita.notes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.medvedev.nikita.notes.objects.Note;
import com.medvedev.nikita.notes.objects.Notes;
import com.medvedev.nikita.notes.objects.NotesRequest;
import com.medvedev.nikita.notes.objects.Token;
import com.medvedev.nikita.notes.utils.DBManager;
import com.medvedev.nikita.notes.utils.ErrorManager;
import com.medvedev.nikita.notes.utils.RequestManager;
import com.medvedev.nikita.notes.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class FragmentList extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       Token token = new Token().setToken(SharedPreferencesManager.getInstance().getToken());
        RequestManager.requestNotes(new NotesRequest().setCount(20).setOffset(0).setToken(token.getToken()), this::onGetNotes,this::onRequestNotesError);
    }

    protected void onRequestNotesError(int errCode){

        Toast.makeText(getActivity(), ErrorManager.errorToResID(errCode), Toast.LENGTH_LONG).show();
    }
    protected void onGetNotes(Notes notes) {
        DBManager myDB = new DBManager();
        myDB.dbSetNotes(notes);
        drawNotes();
    }
    public void drawNotes(){
        DBManager myDB = new DBManager();
        List<Note> noteList = myDB.dbGetNotes().getNotes();
        ArrayAdapter<Note> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, noteList);
        setListAdapter(adapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        drawNotes();
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Context activity = getActivity();
        Note note = (Note)(getListAdapter()).getItem(position);
        Intent intent = new Intent(activity, NoteActivity.class);
        intent.putExtra("title",note.getTitle());
        intent.putExtra("note",note.getNote());
        intent.putExtra("note_id",note.getId());
        activity.startActivity(intent);
      }
}