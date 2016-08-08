package com.gloobe.survey.Actividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;

import com.gloobe.survey.Fragments.Fragment_Login;
import com.gloobe.survey.Modelos.Answer;
import com.gloobe.survey.Modelos.Data;
import com.gloobe.survey.Modelos.Encuesta;
import com.gloobe.survey.Modelos.Question;
import com.gloobe.survey.Modelos.Survey;
import com.gloobe.survey.R;

import java.util.List;

/**
 * Created by rudielavilaperaza on 26/07/16.
 */
public class Actividad_Principal extends FragmentActivity {


    public Data datos;
    public List<Survey> surveyList;
    public Encuesta encuesta;
    public List<Question> questionList;
    public List<Answer> answerList;
    public int position;
    public static LinearLayout llconexion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        llconexion = (LinearLayout) findViewById(R.id.llConexion);

        if (savedInstanceState == null) {
            iniciarFragment(new Fragment_Login(), false);
        }

    }

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
