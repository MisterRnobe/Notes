package com.medvedev.nikita.notes.utils;

import java.util.TreeMap;

public class FluidBuilderMap extends TreeMap<String, String> {
    public FluidBuilderMap putFluid(String key, Object value)
    {
        this.put(key, value.toString());
        return this;
    }
}
