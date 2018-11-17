package com.medvedev.nikita.notes.objects;

import com.medvedev.nikita.notes.utils.FluentBuilderMap;

import java.util.Map;

public class NotesRequest extends Token {
    private int count;
    private int offset;

    public int getCount() {
        return count;
    }

    public NotesRequest setCount(int count) {
        this.count = count;
        return this;
    }

    public int getOffset() {
        return offset;
    }

    public NotesRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }




    @Override
    public Map<String, String> getAsMap() {
        return ((FluentBuilderMap) super.getAsMap()).putFluent("count", count).putFluent("offset", offset);
    }
}
