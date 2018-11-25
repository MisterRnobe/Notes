package com.medvedev.nikita.notes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.medvedev.nikita.notes.objects.Note;
import com.medvedev.nikita.notes.objects.Notes;
import com.medvedev.nikita.notes.objects.NotesRequest;
import com.medvedev.nikita.notes.objects.Token;
import com.medvedev.nikita.notes.utils.ErrorManager;
import com.medvedev.nikita.notes.utils.RequestManager;
import com.medvedev.nikita.notes.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class FragmentList extends ListFragment {
    List<String> titles = new ArrayList<>();

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
        dbSetNotes(notes);
        List<Note> noteList = notes.getNotes();
        for(Note note : noteList){
            titles.add(note.getTitle());
        }
        setList(titles);
    }
    private void dbSetNotes(Notes notes){
        SQLiteDatabase myDB = getActivity().openOrCreateDatabase("notes.db",MODE_PRIVATE,null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS notes (id INT, title VARCHAR(200), note TEXT, created INT)");
        myDB.execSQL("DELETE FROM notes");
        ContentValues row = new ContentValues();
        List<Note> noteList = notes.getNotes();
        for (Note n : noteList) {
            row.put("id",n.getId());
            row.put("title",n.getTitle());
            row.put("note",n.getNote());
            row.put("created",n.getCreated());
            myDB.insert("notes",null,row);
        }
        myDB.close();
    }
    private Notes dbGetNotes(){
        Notes notes = new Notes();
        SQLiteDatabase myDB = getActivity().openOrCreateDatabase("notes.db",MODE_PRIVATE,null);
        Cursor result = myDB.rawQuery("SELECT * FROM notes",null);
        while(result.moveToNext()){
            notes.addNote(new Note()
                    .setId(result.getInt(0))
                    .setTitle(result.getString(1))
                    .setNote(result.getString(2))
                    .setCreated(result.getLong(3)));
        }
        result.close();
        myDB.close();
        return notes;
    }
    private void setList(List<String> titles){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, titles);
        setListAdapter(adapter);
    }
}