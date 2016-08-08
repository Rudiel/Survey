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

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }


    public Lover getLover() {
        return lover;
    }

    public void setLover(Lover lover) {
        this.lover = lover;
    }

    public Happy getHappy() {
        return happy;
    }

    public void setHappy(Happy happy) {
        this.happy = happy;
    }

    public Relaxed getRelaxed() {
        return relaxed;
    }

    public void setRelaxed(Relaxed relaxed) {
        this.relaxed = relaxed;
    }

    public Sad getSad() {
        return sad;
    }

    public void setSad(Sad sad) {
        this.sad = sad;
    }

    public Angry getAngry() {
        return angry;
    }

    public void setAngry(Angry angry) {
        this.angry = angry;
    }
}
