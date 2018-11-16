package com.medvedev.nikita.notes.objects;

public class LoginPasswordData extends Body {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public LoginPasswordData setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginPasswordData setPassword(String password) {
        this.password = password;
        return this;
    }
}
