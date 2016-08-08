package com.gloobe.survey.Actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gloobe.survey.R;

/**
 * Created by rudielavilaperaza on 26/07/16.
 */
public class Actividad_Splash extends Activity {
    private static final int SPLASH_TIME_OUT = 3000;
    private ImageView ivFondo, ivLogo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_splash);

        ivFondo = (ImageView) findViewById(R.id.ivSplashFondo);
        ivLogo = (ImageView) findViewById(R.id.ivSplashLogo);

        Glide.with(this).load(R.drawable.fondo).centerCrop().into(ivFondo);
        Glide.with(this).load(R.drawable.logo_name).into(ivLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(Actividad_Splash.this, Actividad_Tutorial.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }, SPLASH_TIME_OUT);
    }
}
