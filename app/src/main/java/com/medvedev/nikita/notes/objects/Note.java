package com.medvedev.nikita.notes.objects;

import com.medvedev.nikita.notes.utils.FluentBuilderMap;

import java.util.Map;

public class Note extends Body {
    private String title, note;
    private int id;
    private long created;

    public String getTitle() {
        return title;
    }

    public Note setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getNote() {
        return note;
    }

    public Note setNote(String note) {
        this.note = note;
        return this;
    }

    public int getId() {
        return id;
    }

    public Note setId(int id) {
        this.id = id;
        return this;
    }

    public long getCreated() {
        return created;
    }

    public Note setCreated(long created) {
        this.created = created;
        return this;
    }

    @Override
    public Map<String, String> getAsMap() {
        return new FluentBuilderMap()
                .putFluent("title", title)
                .putFluent("note", note)
                .putFluent("created", created)
                .putFluent("id", id);
    }
}
