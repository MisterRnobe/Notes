package com.medvedev.nikita.notes.utils;

import java.util.TreeMap;

public class FluentBuilderMap extends TreeMap<String, String> {
    public FluentBuilderMap putFluent(String key, Object value)
    {
        this.put(key, value.toString());
        return this;
    }
}
