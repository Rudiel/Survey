package com.gloobe.survey.Modelos.Models.Request;

import android.widget.EditText;

import com.gloobe.survey.Modelos.Models.Response.Raiting;

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
    private String date;
    private String question_type;
    private EditText editText;
    private List<Integer> checkBoxIdsList;
    private List<Raiting> raitings;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }


    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public List<Integer> getCheckBoxIdsList() {
        return checkBoxIdsList;
    }

    public void setCheckBoxIdsList(List<Integer> checkBoxIdsList) {
        this.checkBoxIdsList = checkBoxIdsList;
    }

    public List<Raiting> getRaitings() {
        return raitings;
    }

    public void setRaitings(List<Raiting> raitings) {
        this.raitings = raitings;
    }
}
