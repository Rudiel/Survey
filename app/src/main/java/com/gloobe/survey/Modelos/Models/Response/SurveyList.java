package com.gloobe.survey.Modelos.Models.Response;

import java.util.List;

/**
 * Created by rudielavilaperaza on 1/10/17.
 */

public class SurveyList {
    private ImageUser avatar;
    private List<Survey> surveys;

    public ImageUser getAvatar() {
        return avatar;
    }

    public void setImageUser(ImageUser avatar) {
        this.avatar = avatar;
    }

    public List<Survey> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<Survey> surveys) {
        this.surveys = surveys;
    }
}
