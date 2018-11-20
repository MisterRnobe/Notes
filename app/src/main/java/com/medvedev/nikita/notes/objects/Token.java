package com.medvedev.nikita.notes.objects;

import com.medvedev.nikita.notes.utils.FluentBuilderMap;

import java.util.Map;

public class Token<S extends Token<S>> extends Body {
    private String token;

    public String getToken() {
        return token;
    }

    public S setToken(String token) {
        this.token = token;
        return (S)this;
    }

    @Override
    public Map<String, String> getAsMap() {
        return new FluentBuilderMap().putFluent("token", token);
    }
}
