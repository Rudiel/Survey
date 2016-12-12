package com.gloobe.survey.Modelos.Models.Response;

/**
 * Created by rudielavilaperaza on 12/8/16.
 */

public class QuestionType {

    private int id;
    private String name;
    private String prefix;

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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
