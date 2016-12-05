package com.gloobe.survey.Actividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.gloobe.survey.Fragments.Fragment_AboutUs;
import com.gloobe.survey.Fragments.Fragment_Lista;
import com.gloobe.survey.Fragments.Fragment_QuestionsAnswers;
import com.gloobe.survey.Fragments.Fragment_Settings;
import com.gloobe.survey.Fragments.Fragment_Support;
import com.gloobe.survey.Modelos.Answer;
import com.gloobe.survey.Modelos.Cliente;
import com.gloobe.survey.Modelos.Data;
import com.gloobe.survey.Modelos.Encuesta;
import com.gloobe.survey.Modelos.Models.Response.User;
import com.gloobe.survey.Modelos.Question;
import com.gloobe.survey.Modelos.Survey;
import com.gloobe.survey.R;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by rudielavilaperaza on 26/07/16.
 */
public class Actividad_Principal extends AppCompatActivity {


    public Data datos;
    public List<Survey> surveyList;
    public Encuesta encuesta;
    public List<Question> questionList;
    public List<Answer> answerList;
    public int position;
    public static LinearLayout llconexion;
    private User user;
    public Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        llconexion = (LinearLayout) findViewById(R.id.llConexion);

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("User");
        user = gson.fromJson(strObj, User.class);
        //datos = cliente.getData();
        //surveyList = datos.getSurveys();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.VISIBLE);

        drawerLayout = (DrawerLayout) findViewById(R.id.ndPrincipal);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_encuestas:
                        iniciarFragment(new Fragment_Lista(), false);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menu_settings:
                        iniciarFragment(new Fragment_Settings(), false);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menu_preguntas:
                        iniciarFragment(new Fragment_QuestionsAnswers(), false);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menu_support:
                        iniciarFragment(new Fragment_Support(),false);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menu_about:
                        iniciarFragment(new Fragment_AboutUs(),false);
                        drawerLayout.closeDrawers();
                        return true;
                }

                return false;
            }
        });

        if (savedInstanceState == null) {
            //iniciarFragment(new Fragment_Login(), false);
            //iniciarFragment(new Fragment_Lista(), false);
        }

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.app_name, R.string.app_name
        );

        drawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

    }

   /* private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        //resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.fondo);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(new ResideMenu.OnMenuListener() {
            @Override
            public void openMenu() {

            }

            @Override
            public void closeMenu() {

            }
        });
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        /*itemHome = new ResideMenuItem(this, R.drawable.icon_home, "Home");
        itemProfile = new ResideMenuItem(this, R.drawable.icon_profile, "Profile");
        itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar, "Calendar");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "Settings");

        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);*/

    // You can disable a direction by setting ->
    // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

       /* findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
}*/

    public void iniciarFragment(Fragment fragment, boolean backstack) {
        if (backstack)
            getSupportFragmentManager().beginTransaction().replace(R.id.flContenedor, fragment).addToBackStack(null).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.flContenedor, fragment).commit();

    }

    public void mostarDialogo(String mensaje, String titulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje)
                .setTitle(titulo);
        final AlertDialog dialog = builder.create();
        dialog.setButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
