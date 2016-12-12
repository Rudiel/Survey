package com.gloobe.survey.Modelos.Models.Request;

import java.util.List;

/**
 * Created by rudielavilaperaza on 12/11/16.
 */

public class Question {

    private int question_id;
    private String response;
    private int choice_id;
    private List<String> choice_ids;
    private int image_id;
    private List<String> date;
    private int question_type;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getChoice_id() {
        return choice_id;
    }

    public void setChoice_id(int choice_id) {
        this.choice_id = choice_id;
    }

    public List<String> getChoice_ids() {
        return choice_ids;
    }

    public void setChoice_ids(List<String> choice_ids) {
        this.choice_ids = choice_ids;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public int getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(int question_type) {
        this.question_type = question_type;
    }
}
