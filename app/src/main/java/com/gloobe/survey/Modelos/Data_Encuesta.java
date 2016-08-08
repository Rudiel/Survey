package com.gloobe.survey.Modelos;

import java.util.List;

/**
 * Created by rudielavilaperaza on 27/07/16.
 */
public class Data_Encuesta {
    private String title, description, expires_at, url;
    private int id;
    private List<Question> questions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

}
