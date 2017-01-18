package com.gloobe.survey.Modelos.Models.Response;

import java.util.List;

/**
 * Created by rudielavilaperaza on 12/8/16.
 */

public class Encuesta {
    private int id;
    private String name;
    private String description;
    private int questions_count;
    private String created_at;
    private List<Question> questions;
    private Avatar avatar;
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

    public int getQuestions_count() {
        return questions_count;
    }

    public void setQuestions_count(int questions_count) {
        this.questions_count = questions_count;
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

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
