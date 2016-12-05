package com.gloobe.survey.Modelos.Models.Request;

/**
 * Created by rudielavilaperaza on 12/1/16.
 */

public class Login {
    private String password;
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
