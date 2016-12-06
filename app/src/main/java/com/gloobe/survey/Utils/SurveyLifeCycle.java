package com.gloobe.survey.Utils;

import android.app.Application;

/**
 * Created by rudielavilaperaza on 12/5/16.
 */

public class SurveyLifeCycle extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LocaleHelper.onCreate(this, "en");

    }
}
