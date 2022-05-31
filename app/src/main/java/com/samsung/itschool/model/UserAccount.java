package com.samsung.itschool.model;

import java.io.Serializable;
import java.sql.Date;

public class UserAccount implements Serializable {

    private Long id;

    private String name;

    private String login;

    private String password;

    private String secretWord;

    private Date birthDate;

    private String passportInfo;

    public UserAccount(Long id, String login) {
        this.id = id;
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getPassportInfo() {
        return passportInfo;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
