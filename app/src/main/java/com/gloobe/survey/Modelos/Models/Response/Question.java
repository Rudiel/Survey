package com.gloobe.survey.Modelos.Models.Response;

import java.util.List;

/**
 * Created by rudielavilaperaza on 12/8/16.
 */

public class Question {

    private int id;
    private String title;
    private String created_at;
    private String question_type;
    private List<Choices> choices;
    private List<Images> images;
    private List<Raiting> raitings;
    private String info_body;
    private Avatar info_image;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public List<Choices> getChoices() {
        return choices;
    }

    public void setChoices(List<Choices> choices) {
        this.choices = choices;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public List<Raiting> getRaitings() {
        return raitings;
    }

    public void setRaitings(List<Raiting> raitings) {
        this.raitings = raitings;
    }

    public String getInfo_body() {
        return info_body;
    }

    public void setInfo_body(String info_body) {
        this.info_body = info_body;
    }

    public Avatar getInfo_image() {
        return info_image;
    }

    public void setInfo_image(Avatar info_image) {
        this.info_image = info_image;
    }
}
