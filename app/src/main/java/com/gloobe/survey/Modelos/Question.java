package com.gloobe.survey.Modelos;

import java.util.List;

/**
 * Created by rudielavilaperaza on 27/07/16.
 */
public class Question {
    private int id, type_question;
    private String question;
    private List<Answer> answers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType_question() {
        return type_question;
    }

    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
