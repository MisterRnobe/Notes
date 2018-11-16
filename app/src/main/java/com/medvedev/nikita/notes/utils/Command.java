package com.medvedev.nikita.notes.utils;

public class Command {
    private final String name;
    private final int method;

    public Command(String name, int method) {
        this.name = name;
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public int getMethod() {
        return method;
    }
}
