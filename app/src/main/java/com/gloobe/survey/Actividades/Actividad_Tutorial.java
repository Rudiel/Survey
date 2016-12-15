package com.gloobe.survey.Actividades;

import android.content.Intent;
import android.graphics.Typeface;
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
    private Typeface tfTitulos;
    private Typeface tfTextos;
    private ImageView ivImagenFondo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tutorial);

        viewPager = (ViewPager) findViewById(R.id.vpCarrusel);
        ivPuntos = (ImageView) findViewById(R.id.ivPuntos);
        ivImagenFondo = (ImageView) findViewById(R.id.ivImagenFondo);

        intros = new ArrayList<>();

        tfTitulos = Typeface.createFromAsset(getAssets(),
                "fonts/titulos.ttf");
        tfTextos = Typeface.createFromAsset(getAssets(),
                "fonts/textos.ttf");

        intros.add(new Intro(getString(R.string.intro_nuestro_titulo), getString(R.string.intro_nuestro_texto), R.drawable.tuto_uno));
        intros.add(new Intro(getString(R.string.intro_trabajando_titulo), getString(R.string.intro_trabajando_texto), R.drawable.tuto_dos));
        intros.add(new Intro(getString(R.string.intro_tipos_titulo), getString(R.string.intro_tipos_texto), R.drawable.tuto_tres));

        adapter = new AdapterImageSwipe(this, intros, tfTextos, tfTitulos);
        viewPager.setAdapter(adapter);

        btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setTypeface(tfTitulos);
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
                        ivPuntos.setImageResource(R.drawable.puntos_uno);
                        Glide.with(Actividad_Tutorial.this).load(R.drawable.tuto_imagen_uno).centerCrop().into(ivImagenFondo);
                        break;
                    case 1:
                        ivPuntos.setImageResource(R.drawable.puntos_dos);
                        Glide.with(Actividad_Tutorial.this).load(R.drawable.tuto_imagen_dos).centerCrop().into(ivImagenFondo);
                        break;
                    case 2:
                        ivPuntos.setImageResource(R.drawable.puntos_tres);
                        Glide.with(Actividad_Tutorial.this).load(R.drawable.tuto_imagen_tres).centerCrop().into(ivImagenFondo);
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
