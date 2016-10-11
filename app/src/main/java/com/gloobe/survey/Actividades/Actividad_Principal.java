package com.gloobe.survey.Actividades;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gloobe.survey.Db4oHelper;
import com.gloobe.survey.Fragments.Fragment_Login;
import com.gloobe.survey.Modelos.Answer;
import com.gloobe.survey.Modelos.Data;
import com.gloobe.survey.Modelos.Encuesta;
import com.gloobe.survey.Modelos.ObjectToSend;
import com.gloobe.survey.Modelos.Question;
import com.gloobe.survey.Modelos.Survey;
import com.gloobe.survey.R;
import com.gloobe.survey.SurveyInterface;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    public static boolean wifiActive;
    public static Db4oHelper db4oHelper = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        llconexion = (LinearLayout) findViewById(R.id.llConexion);

        if (savedInstanceState == null) {
            iniciarFragment(new Fragment_Login(), false);
        }
        //  db= new DB4OProvider(this);
        dbHelper();

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

    private Db4oHelper dbHelper() {
        if (db4oHelper == null) {
            db4oHelper = new Db4oHelper(this);
            db4oHelper.db();
        }
        return db4oHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper().close();
        db4oHelper = null;
    }

    public static void eliminarEncuesta(Context context) {
        if (db4oHelper.db().query(ObjectToSend.class).size() != 0) {
            List<ObjectToSend> objectToSendList = (Actividad_Principal.db4oHelper.db().query(ObjectToSend.class));
            for (int i = 0; i < objectToSendList.size(); i++) {
                sender(objectToSendList.get(i), context);
            }
        }
    }

    private static void sender(final ObjectToSend objectToSend, final Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://murmuring-ocean-26308.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SurveyInterface service = retrofit.create(SurveyInterface.class);

        Call<ResponseBody> resultado = service.setResultado(objectToSend.getClient_id(), objectToSend.getId_encuesta(), objectToSend.getRequestBody());

        resultado.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    try {
                        db4oHelper.db().delete(objectToSend);
                        Log.d("ENCUESTABD", "HECHO");
                        db4oHelper.db().commit();
                        if (db4oHelper.db().query(ObjectToSend.class).size() == 0) {
                            Toast.makeText(context, context.getString(R.string.toast_encuestasatrasadas), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

                    }


                } else {
                    Log.d("ENCUESTABD", "FALLO");


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("ENCUESTABD", "FALLO");
            }
        });
    }
}
