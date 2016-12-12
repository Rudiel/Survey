package com.gloobe.survey.Actividades;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gloobe.survey.Fragments.Fragment_AboutUs;
import com.gloobe.survey.Fragments.Fragment_Lista;
import com.gloobe.survey.Fragments.Fragment_Support;
import com.gloobe.survey.Fragments.Fragment_Survey;
import com.gloobe.survey.Interfaces.SurveyInterface;
import com.gloobe.survey.Modelos.Answer;
import com.gloobe.survey.Modelos.Data;
import com.gloobe.survey.Modelos.Models.Response.Encuesta;
import com.gloobe.survey.Modelos.Models.Response.User;
import com.gloobe.survey.Modelos.Question;
import com.gloobe.survey.Modelos.Models.Response.Survey;
import com.gloobe.survey.R;
import com.gloobe.survey.Utils.LocaleHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rudielavilaperaza on 26/07/16.
 */
public class Actividad_Principal extends AppCompatActivity {


    public Data datos;

    public List<Question> questionList;
    public List<Answer> answerList;
    public List<Survey> surveyList;

    public static LinearLayout llconexion;
    public User user;
    public Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    //Nuevas variables v2

    public Typeface tfTitulos;
    public Typeface tfTextos;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    public int survey_id = 0;
    public Encuesta encuesta = null;

    public static final String FG_ABOUT = "ABOUT_US";
    public static final String FG_LISTA = "LISTA";
    public static final String FG_SUPPORT = "SUPPORT";
    public static final String FG_SURVEY = "SURVEY";
    public static final String FG_FAQ = "FAQ";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        llconexion = (LinearLayout) findViewById(R.id.llConexion);

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("Usuario");
        user = gson.fromJson(strObj, User.class);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setVisibility(View.VISIBLE);

        drawerLayout = (DrawerLayout) findViewById(R.id.ndPrincipal);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        tfTitulos = Typeface.createFromAsset(getAssets(),
                "fonts/titulos.ttf");
        tfTextos = Typeface.createFromAsset(getAssets(),
                "fonts/textos.ttf");


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_encuestas:
                        iniciarFragment(new Fragment_Lista(), false, FG_LISTA);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menu_support:
                        iniciarFragment(new Fragment_Support(), false, FG_SUPPORT);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menu_about:
                        iniciarFragment(new Fragment_AboutUs(), false, FG_ABOUT);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menu_idioma:
                        drawerLayout.closeDrawers();
                        showLenguageDialog();
                        return true;
                }

                return false;
            }
        });

        if (savedInstanceState == null) {
            //iniciarFragment(new Fragment_Login(), false);
        }

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.app_name, R.string.app_name
        );

        drawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        getEncuestas(user.getId(), user.getApi_key());

        surveyList = new ArrayList<>();

    }

    private void getEncuestas(int custumer_id, String token) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url_global))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SurveyInterface service = retrofit.create(SurveyInterface.class);

        Call<List<Survey>> encuestas = service.getSurveys(custumer_id, "Token token=" + token);
        encuestas.enqueue(new Callback<List<Survey>>() {
            @Override
            public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                Log.d("RESPONSE", response.body().toString());
                surveyList = response.body();
                iniciarFragment(new Fragment_Lista(), false, FG_LISTA);
            }

            @Override
            public void onFailure(Call<List<Survey>> call, Throwable t) {

            }
        });


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

    public void iniciarFragment(Fragment fragment, boolean backstack, String fgId) {
        if (backstack)
            getSupportFragmentManager().beginTransaction().replace(R.id.flContenedor, fragment, fgId).addToBackStack(null).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.flContenedor, fragment, fgId).commit();

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

    private void showLenguageDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.layout_dialog_lenguage);

        Window window = dialog.getWindow();

        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setBackgroundDrawableResource(R.color.survey_dialog);
        //Window window = dialog.getWindow();
        //window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //window.setBackgroundDrawableResource(R.color.fondo_dialogo);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;


        final Button btEspanol = (Button) dialog.findViewById(R.id.btLenguajeEspañol);
        final Button btIngles = (Button) dialog.findViewById(R.id.btLenguajeIngles);
        btEspanol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocale(Actividad_Principal.this, "es");
                updateViews();
                dialog.dismiss();
            }
        });

        btIngles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocale(Actividad_Principal.this, "en");
                updateViews();
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    private void updateViews() {
        recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) searchItem.getActionView();
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
         this.toolbar.setVisibility(View.VISIBLE);
    }
}
