package com.gloobe.survey.Modelos.Models.Response;

/**
 * Created by rudielavilaperaza on 12/7/16.
 */

public class Survey {
    private int id;
    private String name;
    private String description;
    private String created_at;
    private int question_counter;

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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getQuestion_counter() {
        return question_counter;
    }

    public void setQuestion_counter(int question_counter) {
        this.question_counter = question_counter;
    }
}
