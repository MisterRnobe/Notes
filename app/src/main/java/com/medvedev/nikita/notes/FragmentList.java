package com.medvedev.nikita.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.medvedev.nikita.notes.objects.Note;
import com.medvedev.nikita.notes.objects.Notes;
import com.medvedev.nikita.notes.objects.NotesRequest;
import com.medvedev.nikita.notes.objects.Token;
import com.medvedev.nikita.notes.utils.DBManager;
import com.medvedev.nikita.notes.utils.ErrorManager;
import com.medvedev.nikita.notes.utils.NoteAdapter;
import com.medvedev.nikita.notes.utils.RequestManager;
import com.medvedev.nikita.notes.utils.ResultCodes;
import com.medvedev.nikita.notes.utils.SharedPreferencesManager;

import java.util.List;

public class FragmentList extends ListFragment {
    private NoteAdapter noteAdapter;
    public void logjopa(){
        Log.i("onADD","JOPA");
    }
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
        noteAdapter = new NoteAdapter(getActivity(),notes.getNotes());
        setListAdapter(noteAdapter);
    }

    public void drawNotes(){
        DBManager myDB = new DBManager();
        List<Note> noteList = myDB.dbGetNotes().getNotes();
        noteAdapter.refreshNotes(noteList);
    }


    public void addNote(Note note){
        DBManager myDB = new DBManager();
        myDB.dbAddNote(note);
        drawNotes();
    }


    public void updateNote(Note note){
        DBManager myDB = new DBManager();
        myDB.dbUpdateNote(note);
        drawNotes();
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Context activity = getActivity();
        Note note = (Note)(getListAdapter()).getItem(position);
        Intent intent = new Intent(activity, NoteActivity.class);
        intent.putExtra("title",note.getTitle());
        intent.putExtra("noteText",note.getNote());
        intent.putExtra("note_id",note.getId());
        startActivityForResult(intent,1);
      }
}