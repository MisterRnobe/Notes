package com.medvedev.nikita.notes.objects;

import com.medvedev.nikita.notes.utils.FluidBuilderMap;

import java.util.Map;

public class Token extends Body {
    private String token;

    public String getToken() {
        return token;
    }

    public Token setToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public Map<String, String> getAsMap() {
        return new FluidBuilderMap().putFluid("token", token);
    }
}
