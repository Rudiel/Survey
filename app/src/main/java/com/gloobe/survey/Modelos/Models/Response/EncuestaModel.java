package com.gloobe.survey.Modelos.Models.Response;

/**
 * Created by rudielavilaperaza on 1/12/17.
 */

public class EncuestaModel {

    private ImageUser avatar;
    Encuesta survey;

    public ImageUser getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageUser avatar) {
        this.avatar = avatar;
    }

    public Encuesta getSurvey() {
        return survey;
    }

    public void setSurvey(Encuesta survey) {
        this.survey = survey;
    }
}
