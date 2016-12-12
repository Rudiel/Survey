package com.gloobe.survey.Modelos.Models.Response;

import java.util.List;

/**
 * Created by rudielavilaperaza on 12/8/16.
 */

public class Encuesta {
    private int id;
    private String name;
    private String description;
    private int question_counter;
    private String created_at;
    private List<Question> questions;
    //falta la lista de imagenes


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuestion_counter() {
        return question_counter;
    }

    public void setQuestion_counter(int question_counter) {
        this.question_counter = question_counter;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
