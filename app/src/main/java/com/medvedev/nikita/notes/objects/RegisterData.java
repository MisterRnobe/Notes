package com.medvedev.nikita.notes.objects;

import com.medvedev.nikita.notes.utils.FluentBuilderMap;

import java.util.Map;

public class RegisterData extends LoginPasswordData<RegisterData> {
    private String name;
    private String surname;
    private String email;
    public RegisterData setLogin(String login) {
        return super.setLogin(login);
    }
    public RegisterData setPassword(String password){
        return super.setPassword(password);
    }
    public RegisterData setSurname(String surname){
        this.surname = surname;
        return this;
    }
    public RegisterData setName(String name){
        this.name = name;
        return this;
    }
    public RegisterData setEmail(String email){
        this.email = email;
        return this;
    }


    @Override
    public Map<String, String> getAsMap() {
        return new FluentBuilderMap().putFluent("login", getLogin())
                .putFluent("password", getPassword())
                .putFluent("first_name",name)
                .putFluent("second_name",surname)
                .putFluent("email",email);
    }
}
