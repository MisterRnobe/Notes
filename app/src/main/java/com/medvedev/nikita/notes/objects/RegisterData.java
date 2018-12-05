package com.medvedev.nikita.notes.objects;

import com.medvedev.nikita.notes.utils.FluentBuilderMap;

import java.util.Map;

public class RegisterData extends LoginPasswordData<RegisterData> {
    private String first_name;
    private String second_name;
    private String email;
    public RegisterData setLogin(String login) {
        return super.setLogin(login);
    }
    public RegisterData setPassword(String password){
        return super.setPassword(password);
    }
    public RegisterData setSurname(String surname){
        this.second_name = surname;
        return this;
    }
    public RegisterData setName(String name){
        this.first_name = name;
        return this;
    }
    public RegisterData setEmail(String email){
        this.email = email;
        return this;
    }

    public String getName() {
        return first_name;
    }

    public String getSurname() {
        return second_name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Map<String, String> getAsMap() {
        return new FluentBuilderMap().putFluent("login", getLogin())
                .putFluent("password", getPassword())
                .putFluent("first_name", first_name)
                .putFluent("second_name", second_name)
                .putFluent("email",email);
    }
}
