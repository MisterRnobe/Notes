package com.medvedev.nikita.notes.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.medvedev.nikita.notes.objects.Note;
import com.medvedev.nikita.notes.objects.Notes;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DBManager {

    public void dbSetNotes(Notes notes) {
        SQLiteDatabase myDB = AppController.getAppContext().openOrCreateDatabase("notes.db", MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS notes (id INT, title VARCHAR(200), note TEXT, created INT)");
        myDB.execSQL("DELETE FROM notes");
        myDB.close();
        List<Note> noteList = notes.getNotes();
        for (Note n : noteList) {
            dbAddNote(n);
        }
    }

    public void dbAddNote(Note note) {
        SQLiteDatabase myDB = AppController.getAppContext().openOrCreateDatabase("notes.db", MODE_PRIVATE, null);
        ContentValues row = new ContentValues();

        row.put("id", note.getId());
        row.put("title", note.getTitle());
        row.put("note", note.getNote());
        row.put("created", note.getCreated());
        myDB.insert("notes",null,row);
        myDB.close();
    }
    public void dbUpdateNote(Note note){
        SQLiteDatabase myDB = AppController.getAppContext().openOrCreateDatabase("notes.db", MODE_PRIVATE, null);
        SQLiteStatement stmt = myDB.compileStatement("UPDATE notes SET title=?, note=? WHERE id=?");
        stmt.bindString(1,note.getTitle());
        stmt.bindString(2,note.getNote());
        stmt.bindString(3,String.valueOf(note.getId()));
        stmt.execute();
        stmt.close();
        myDB.close();
    }

    public Notes dbGetNotes() {
        SQLiteDatabase myDB = AppController.getAppContext().openOrCreateDatabase("notes.db", MODE_PRIVATE, null);
        Notes notes = new Notes();
        Cursor result = myDB.rawQuery("SELECT * FROM notes", null);
        while (result.moveToNext()) {
            notes.addNote(new Note()
                    .setId(result.getInt(0))
                    .setTitle(result.getString(1))
                    .setNote(result.getString(2))
                    .setCreated(result.getLong(3)));
        }
        if(notes.getNotes().isEmpty())
            Log.i("123123","notes empty");
        else
            Log.i("123123","no empty");
        result.close();
        myDB.close();
        return notes;
    }

}
