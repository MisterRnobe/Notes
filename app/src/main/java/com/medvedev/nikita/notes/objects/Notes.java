package com.medvedev.nikita.notes.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Notes extends Body {
    private List<Note> notes = new ArrayList<>();

    public List<Note> getNotes() {
        return notes;
    }

    public Notes setNotes(List<Note> notes) {
        this.notes = notes;
        return this;
    }
    public void addNote(Note n)
    {
        this.notes.add(n);
    }

    @Override
    public Map<String, String> getAsMap() {
        return null;
    }
}
