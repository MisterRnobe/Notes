package com.medvedev.nikita.notes.objects;

import com.medvedev.nikita.notes.utils.FluentBuilderMap;

import java.util.Map;

public class LoginPasswordData<S extends LoginPasswordData<S>> extends Body {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public S setLogin(String login) {
        this.login = login;
        return (S)this;
    }

    public String getPassword() {
        return password;
    }

    public S setPassword(String password) {
        this.password = password;
        return (S) this;
    }

    @Override
    public Map<String, String> getAsMap() {
        return new FluentBuilderMap().putFluent("login", login).putFluent("password", password);
    }
}
