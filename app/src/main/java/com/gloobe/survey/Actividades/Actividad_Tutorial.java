package com.gloobe.survey.Actividades;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gloobe.survey.Adaptadores.AdapterImageSwipe;
import com.gloobe.survey.Modelos.Intro;
import com.gloobe.survey.R;

import java.util.ArrayList;

public class Actividad_Tutorial extends AppCompatActivity {

    private ViewPager viewPager;
    private AdapterImageSwipe adapter;
    private Button btLogin;
    private ArrayList<Intro> intros;
    private ImageView ivPuntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tutorial);

        viewPager = (ViewPager) findViewById(R.id.vpCarrusel);
        ivPuntos = (ImageView) findViewById(R.id.ivPuntos);

        intros = new ArrayList<>();

        intros.add(new Intro(getString(R.string.intro_nuestro_titulo),getString(R.string.intro_nuestro_texto), R.drawable.logo));
        intros.add(new Intro(getString(R.string.intro_trabajando_titulo), getString(R.string.intro_trabajando_texto), R.drawable.wifi));
        intros.add(new Intro(getString(R.string.intro_tipos_titulo), getString(R.string.intro_tipos_texto), R.drawable.smiles));

        adapter = new AdapterImageSwipe(this, intros);
        viewPager.setAdapter(adapter);

        btLogin = (Button) findViewById(R.id.btLogin);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Actividad_Tutorial.this, Actividad_Login.class));
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        ivPuntos.setImageResource(R.drawable.puntos1);
                        break;
                    case 1:
                        ivPuntos.setImageResource(R.drawable.puntos2);
                        break;
                    case 2:
                        ivPuntos.setImageResource(R.drawable.puntos3);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
