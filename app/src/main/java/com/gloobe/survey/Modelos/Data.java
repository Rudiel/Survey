package com.gloobe.survey.Modelos;

import java.util.List;

/**
 * Created by rudielavilaperaza on 26/07/16.
 */
public class Data {

    private int id;
    private String username;
    private List<Survey> surveys;
    private String color;
    private String image;

    public List<Survey> getSurveys() {
        return surveys;
    }

    public String getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

}
