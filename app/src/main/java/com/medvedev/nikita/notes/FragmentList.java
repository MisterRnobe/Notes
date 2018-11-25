/*
package com.medvedev.nikita.notes;

import android.content.ContentValues;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.medvedev.nikita.notes.objects.Note;
import com.medvedev.nikita.notes.objects.Notes;
import com.medvedev.nikita.notes.objects.NotesRequest;
import com.medvedev.nikita.notes.objects.Token;
import com.medvedev.nikita.notes.utils.RequestManager;
import com.medvedev.nikita.notes.utils.SharedPreferencesManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class FragmentList extends ListFragment {
    List<String> titles;
    String[] titles1 = new String[]{"jopa", "govno", "pisun"};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Token token = new Token().setToken(SharedPreferencesManager.getInstance().getToken());
        RequestManager.requestNotes(new NotesRequest().setCount(20).setOffset(0).setToken(token.getToken()), getContext(), this::onGetNotes);

    }

    public void setTitles(){
       */
/* SQLiteDatabase myDB = getActivity().openOrCreateDatabase("notes.db", MODE_PRIVATE, null);
        Cursor result = myDB.rawQuery("SELECT title,note,created FROM notes",null);
        while(result.moveToNext()){
            titles.add(result.getString(0));
        }
        myDB.close();
        result.close();*//*

        List<String> titles = getArguments().getStringArrayList("Titles");
        List<String> note = getArguments().getStringArrayList("Notes");
        List<String> created = getArguments().getStringArrayList("Created");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, titles1);
        setListAdapter(adapter);
    }
    protected void onGetNotes(Notes notes) {
        SQLiteDatabase myDB = getActivity().openOrCreateDatabase("notes.db",MODE_PRIVATE,null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS notes (id INT, title VARCHAR(200), note TEXT, created INT)");
        */
/*ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> note = new ArrayList<>();
        ArrayList<String> created = new ArrayList<>();*//*

        List<Note> noteList = notes.getNotes();
        for (Note n : noteList) {
            */
/*titles.add(n.getTitle());
            note.add(n.getNote());
            created.add(String.valueOf(n.getCreated()));*//*

            row.put("id",n.getId());
            row.put("title",n.getTitle());
            row.put("note",n.getNote());
            row.put("created",n.getCreated());
            myDB.insert("notes",null,row);
        }
       Cursor result = myDB.rawQuery("SELECT title,note,created FROM notes",null);
        while(result.moveToNext()){
            Log.i("huj",result.getString(0));
        }
        myDB.close();
    }
}*/
