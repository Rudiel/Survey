package com.gloobe.survey.Modelos.Models.Response;

/**
 * Created by rudielavilaperaza on 3/15/17.
 */

public class Raiting {

    private int id;
    private String name;
    private String range;
    private int quiestion_id;

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

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public int getQuiestion_id() {
        return quiestion_id;
    }

    public void setQuiestion_id(int quiestion_id) {
        this.quiestion_id = quiestion_id;
    }
}
