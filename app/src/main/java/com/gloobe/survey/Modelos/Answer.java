package com.gloobe.survey.Modelos;

/**
 * Created by rudielavilaperaza on 27/07/16.
 */
public class Answer {
    private int id, question_id;
    private String answer, created_at, updated_at;
    private Lover lover;
    private Happy happy;
    private Relaxed relaxed;
    private Sad sad;
    private Angry angry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getAnswer() {
        return answer;
    }

    public Lover getLover() {
        return lover;
    }


    public Happy getHappy() {
        return happy;
    }


    public Relaxed getRelaxed() {
        return relaxed;
    }


    public Sad getSad() {
        return sad;
    }

    public Angry getAngry() {
        return angry;
    }

}
