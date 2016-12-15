package com.gloobe.survey.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gloobe.survey.Actividades.Actividad_Principal;
import com.gloobe.survey.Modelos.Answer;
import com.gloobe.survey.Modelos.Carita;
import com.gloobe.survey.Modelos.CaritaID;
import com.gloobe.survey.Modelos.Multiple;
import com.gloobe.survey.Modelos.MultipleID;
import com.gloobe.survey.Modelos.PreguntaAbierta;
import com.gloobe.survey.Modelos.Question;
import com.gloobe.survey.Modelos.RespuestaJSON;
import com.gloobe.survey.R;
import com.gloobe.survey.Interfaces.SurveyInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rudielavilaperaza on 26/07/16.
 */
public class Frgament_Encuesta extends Fragment {

    private LinearLayout ll;
    private LinearLayout llContenedor;
    private List<Question> questionList;
    private ImageView ivLogo;

    private ArrayList<RespuestaJSON> arrRespuestasJSON;

    private ArrayList<Carita> arrCaritas;
    private ArrayList<PreguntaAbierta> arrPreguntasAbiertas;
    private ArrayList<Multiple> arrMultiple;
    private ArrayList<Integer> arrIDs;
    private ArrayList<Integer> arrImageID;
    private ArrayList<MultipleID> arrMultipleID;
    private ArrayList<CaritaID> arrCaritaID;

    private JSONObject data;

    private RespuestaJSON resEdittex;
    private RespuestaJSON resCheckBox;
    private RespuestaJSON resCarita;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_encuestas, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((Actividad_Principal) getActivity()).toolbar.setVisibility(View.GONE);

        ll = (LinearLayout) getActivity().findViewById(R.id.llEncuestas);
        llContenedor = (LinearLayout) getActivity().findViewById(R.id.llContenedorEncuesta);
        ivLogo = (ImageView) getActivity().findViewById(R.id.ivLogo);

        llContenedor.setBackgroundColor(Color.parseColor("#" + ((Actividad_Principal) getActivity()).datos.getColor()));
        Glide.with(getActivity()).load(((Actividad_Principal) getActivity()).datos.getImage()).into(ivLogo);

        arrRespuestasJSON = new ArrayList<>();
        arrCaritas = new ArrayList<>();
        arrMultiple = new ArrayList<>();
        arrIDs = new ArrayList<>();
        arrMultipleID = new ArrayList<>();
        arrImageID = new ArrayList<>();
        arrCaritaID = new ArrayList<>();

        arrPreguntasAbiertas = new ArrayList<>();

        questionList = ((Actividad_Principal) getActivity()).questionList;

        for (int i = 0; i < questionList.size(); i++) {

            switch (questionList.get(i).getType_question()) {
                case 1:
                    crearOpcionMultipleUna(questionList.get(i).getQuestion(), questionList.get(i).getAnswers(), questionList.get(i).getId());
                    break;
                case 2:
                    crearOpcionMultiple(questionList.get(i).getQuestion(), questionList.get(i).getAnswers(), questionList.get(i).getId());
                    break;
                case 3:
                    crearCaritas(questionList.get(i).getQuestion(), questionList.get(i).getAnswers(), questionList.get(i).getId());
                    break;
                case 4:
                    crearRaiting(questionList.get(i).getQuestion(), questionList.get(i).getId());
                    break;
                case 5:
                    crearPreguntaAbierta(questionList.get(i).getQuestion(), questionList.get(i).getId());
                    break;
                case 6:
                    crearDropdown(questionList.get(i).getQuestion(), questionList.get(i).getAnswers(), questionList.get(i).getId());
                    break;
            }
        }

        crearBotonEnviar();

    }

    private void crearOpcionMultipleUna(String titulo, List<Answer> mAnswerList, int id) {
        //RadioGroup
        ll.addView(crearTitulos(titulo));

        final RadioButton[] rb = new RadioButton[mAnswerList.size()];
        RadioGroup rg = new RadioGroup(getActivity());
        rg.setOrientation(RadioGroup.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 20;
        rg.setLayoutParams(params);

        for (int i = 0; i < mAnswerList.size(); i++) {
            rb[i] = new RadioButton(getActivity());
            rb[i].setText(mAnswerList.get(i).getAnswer());
            rb[i].setTextColor(getResources().getColor(android.R.color.white));
            rb[i].setTextSize(getResources().getDimension(R.dimen.encustas_respuestas_s));
            rb[i].setButtonDrawable(getResources().getDrawable(R.drawable.radiobutton_custom));
            rg.addView(rb[i]);

        }
        rg.setId(id);

        RespuestaJSON res = new RespuestaJSON();

        rg.setOnCheckedChangeListener(clickRadioButton(res, id, titulo));

        ll.addView(rg);
    }

    private void crearDropdown(String titulo, List<Answer> mAnswerList, int id) {

        ll.addView(crearTitulos(titulo));

        String[] respuestas = new String[mAnswerList.size()];

        for (int i = 0; i < mAnswerList.size(); i++) {
            respuestas[i] = mAnswerList.get(i).getAnswer();
        }

        Spinner spinner = new Spinner(new ContextThemeWrapper(getActivity(), R.style.SpinnerStyle1), null, 0);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, respuestas); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setId(id);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 20;
        params.rightMargin = 20;

        spinner.setLayoutParams(params);

        RespuestaJSON res = new RespuestaJSON();

        spinner.setOnItemSelectedListener(clickSpinner(res, id, mAnswerList, titulo));

        ll.addView(spinner);

    }

    private void crearPreguntaAbierta(String titulo, int id) {

        ll.addView(crearTitulos(titulo));

        EditText editText = new EditText(new ContextThemeWrapper(getActivity(), R.style.EdittextStyle));
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setHint(getString(R.string.encuesta_edittext_hint));
        editText.setHintTextColor(Color.WHITE);
        editText.setSingleLine(true);
        editText.setTextColor(Color.WHITE);
        editText.setTextSize(getResources().getDimension(R.dimen.encustas_respuestas_s));
        editText.setMaxLines(1);
        editText.setLines(1);
        editText.setId(id);

        PreguntaAbierta pa = new PreguntaAbierta();
        pa.setEditText(editText);
        pa.setNombre(titulo);

        arrPreguntasAbiertas.add(pa);

        resEdittex = new RespuestaJSON();

        ll.addView(editText);

    }

    private void crearOpcionMultiple(String titulo, List<Answer> mAnswerList, int id) {
        //Checkbox
        ll.addView(crearTitulos(titulo));

        for (int i = 0; i < mAnswerList.size(); i++) {
            CheckBox cb = new CheckBox(getActivity());
            cb.setText(mAnswerList.get(i).getAnswer());
            cb.setTextColor(getResources().getColor(android.R.color.white));
            cb.setTextSize(getResources().getDimension(R.dimen.encustas_respuestas_s));
            cb.setButtonDrawable(getResources().getDrawable(R.drawable.radiobutton_custom));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 20;
            cb.setLayoutParams(params);


            if (!arrIDs.contains(id)) {
                arrIDs.add(id);

                MultipleID multipleID = new MultipleID();
                multipleID.setId(id);
                multipleID.setRespuestaJSON(new RespuestaJSON());

                arrMultipleID.add(multipleID);
            }

            Multiple multiple = new Multiple();
            cb.setOnCheckedChangeListener(clicCheckBox(multiple, mAnswerList, id, titulo));


            ll.addView(cb);
        }
    }

    private void crearCaritas(String titulo, List<Answer> mAnswerList, int id_pregunta) {
        ll.addView(crearTitulos(titulo));

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        ll.addView(linearLayout);

        for (int i = 0; i < 5; i++) {
            ImageView carita = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.dimen_carita), (int) getResources().getDimension(R.dimen.dimen_carita));
            params.gravity = Gravity.CENTER;
            params.leftMargin = 20;

            carita.setLayoutParams(params);

            Carita mCarita = new Carita();
            mCarita.setId(i);
            mCarita.setId_pregunta(id_pregunta);
            mCarita.setImagen(carita);

            if (mAnswerList.get(i).getLover() != null) {
                Glide.with(getActivity()).load(mAnswerList.get(i).getLover().getInactive()).into(carita);
                mCarita.setActive(mAnswerList.get(i).getLover().getActive());
                mCarita.setInactive(mAnswerList.get(i).getLover().getInactive());
                mCarita.setRespuesta("Lover");
            }
            if (mAnswerList.get(i).getHappy() != null) {
                Glide.with(getActivity()).load(mAnswerList.get(i).getHappy().getInactive()).into(carita);
                mCarita.setActive(mAnswerList.get(i).getHappy().getActive());
                mCarita.setInactive(mAnswerList.get(i).getHappy().getInactive());
                mCarita.setRespuesta("Happy");

            }
            if (mAnswerList.get(i).getRelaxed() != null) {
                Glide.with(getActivity()).load(mAnswerList.get(i).getRelaxed().getInactive()).into(carita);
                mCarita.setActive(mAnswerList.get(i).getRelaxed().getActive());
                mCarita.setInactive(mAnswerList.get(i).getRelaxed().getInactive());
                mCarita.setRespuesta("Relaxed");

            }
            if (mAnswerList.get(i).getSad() != null) {
                Glide.with(getActivity()).load(mAnswerList.get(i).getSad().getInactive()).into(carita);
                mCarita.setActive(mAnswerList.get(i).getSad().getActive());
                mCarita.setInactive(mAnswerList.get(i).getSad().getInactive());
                mCarita.setRespuesta("Sad");

            }
            if (mAnswerList.get(i).getAngry() != null) {
                Glide.with(getActivity()).load(mAnswerList.get(i).getAngry().getInactive()).into(carita);
                mCarita.setActive(mAnswerList.get(i).getAngry().getActive());
                mCarita.setInactive(mAnswerList.get(i).getAngry().getInactive());
                mCarita.setRespuesta("Angry");

            }


            arrCaritas.add(mCarita);

            carita.setOnClickListener(clickCarita(carita, i, mAnswerList, arrCaritas, id_pregunta, titulo));

            linearLayout.addView(carita);
        }

        if (!arrImageID.contains(id_pregunta)) {
            arrImageID.add(id_pregunta);

            CaritaID caritaID = new CaritaID();
            caritaID.setId(id_pregunta);
            caritaID.setRespuestaJSON(new RespuestaJSON());

            if (!arrCaritaID.contains(caritaID))
                arrCaritaID.add(caritaID);
        }

    }

    private void crearRaiting(String titulo, int id) {
        ll.addView(crearTitulos(titulo));

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);

        RatingBar rating =
                new RatingBar(new ContextThemeWrapper(getActivity(), R.style.StarCustomRatingBar), null, 0);
        rating.setMax(5);
        LinearLayout.LayoutParams paramsStar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rating.setLayoutParams(paramsStar);
        rating.setNumStars(5);

        RespuestaJSON res = new RespuestaJSON();

        rating.setOnRatingBarChangeListener(clickRatingBar(res, id, titulo));

        linearLayout.addView(rating);

        ll.addView(linearLayout);

    }

    private TextView crearTitulos(String mtitulo) {
        TextView titulo = new TextView(getActivity());
        titulo.setText(mtitulo.toUpperCase());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 20;
        params.leftMargin = 20;
        titulo.setLayoutParams(params);
        titulo.setTextSize(getResources().getDimension(R.dimen.encustas_titulos_s));
        titulo.setTextColor(getResources().getColor(android.R.color.white));
        return titulo;
    }

    private View.OnClickListener clickCarita(final ImageView imageView, final int id, final List<Answer> mAnswerList, final ArrayList<Carita> arrCaritas, final int id_pregunta, final String titulo) {
        return new View.OnClickListener() {
            public void onClick(View v) {

                String r = null;
                for (int i = 0; i < arrCaritas.size(); i++) {

                    if (arrCaritas.get(i).getId_pregunta() == id_pregunta) {
                        Glide.with(getActivity()).load(arrCaritas.get(i).getInactive()).into(arrCaritas.get(i).getImagen());

                        if (arrCaritas.get(i).getImagen() == v) {
                            Glide.with(getActivity()).load(arrCaritas.get(i).getActive()).into(arrCaritas.get(i).getImagen());
                            r = arrCaritas.get(i).getRespuesta();
                        }
                    }

                }
                for (CaritaID c : arrCaritaID) {
                    if (id_pregunta == c.getId()) {
                        if (r != null)
                            c.getRespuestaJSON().setRespuesta(r);
                        c.getRespuestaJSON().setId_pregunta(c.getId());
                        c.getRespuestaJSON().setTipo(3);

                        if (!c.getRespuestaJSON().getRespuesta().equals(""))
                            if (!arrRespuestasJSON.contains(c.getRespuestaJSON()))
                                arrRespuestasJSON.add(c.getRespuestaJSON());
                    }
                }


            }
        };
    }


    private Spinner.OnItemSelectedListener clickSpinner(final RespuestaJSON res, final int id, final List<Answer> mAnswers, final String titulo) {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                res.setNombre(titulo);
                res.setId_pregunta(id);
                res.setTipo(6);
                res.setRespuesta(mAnswers.get(i).getAnswer());

                if (!arrRespuestasJSON.contains(res))
                    arrRespuestasJSON.add(res);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
    }

    private CheckBox.OnCheckedChangeListener clicCheckBox(final Multiple multiple, List<Answer> mAnswerList, final int id, final String titulo) {

        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                multiple.setCb((CheckBox) compoundButton);
                multiple.setId_pregunta(id);
                multiple.setRespuesta(((CheckBox) compoundButton).getText().toString());
                multiple.setTitulo(titulo);
                if (b) {
                    arrMultiple.add(multiple);
                } else {
                    arrMultiple.remove(multiple);
                }
            }
        };
    }

    private RatingBar.OnRatingBarChangeListener clickRatingBar(final RespuestaJSON res, final int id, final String titulo) {
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                res.setTipo(4);
                res.setNombre(titulo);
                res.setRes(Math.round(v));

                if (!arrRespuestasJSON.contains(res))
                    arrRespuestasJSON.add(res);

            }
        };


    }

    private RadioGroup.OnCheckedChangeListener clickRadioButton(final RespuestaJSON res, final int id, final String titulo) {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    int idb = radioGroup.getCheckedRadioButtonId();
                    View radioButton = radioGroup.findViewById(idb);
                    int radioId = radioGroup.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                    String selection = "";
                    try {
                        selection = (String) btn.getText();
                    } catch (Exception e) {

                    }
                    res.setTipo(1);
                    res.setNombre(titulo);
                    res.setRespuesta(selection);

                    if (!arrRespuestasJSON.contains(res))
                        arrRespuestasJSON.add(res);
                }
            }
        };
    }

    private void crearBotonEnviar() {
        Button btenviar = new Button(getActivity());
        btenviar.setBackground(getResources().getDrawable(R.drawable.button_pink));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.encuesta_boton_enviar_w), (int) getResources().getDimension(R.dimen.encuesta_boton_enviar_h));
        params.gravity = Gravity.CENTER;
        params.bottomMargin = 20;
        params.topMargin = 20;
        btenviar.setLayoutParams(params);
        btenviar.setTextColor(getActivity().getResources().getColor(android.R.color.white));
        btenviar.setTextSize((int) getResources().getDimension(R.dimen.botones_texto));
        btenviar.setText(getResources().getString(R.string.encuesta_boton_texto));
        btenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    createJsonObject();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (data != null) {
                    ((Actividad_Principal) getActivity()).pbPrincipal.setVisibility(View.VISIBLE);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(getString(R.string.url_global))
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    SurveyInterface service = retrofit.create(SurveyInterface.class);


                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (data.toString()));

                    /*Call<ResponseBody> resultado = service.setResultado(((Actividad_Principal) getActivity()).surveyList.get(((Actividad_Principal) getActivity()).survey_id).getClient_id(), ((Actividad_Principal) getActivity()).surveyList.get(((Actividad_Principal) getActivity()).survey_id).getId(), body);

                    resultado.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.body() != null)
                                ((Actividad_Principal) getActivity()).mostarDialogo(getString(R.string.encuestra_dialgo_texto_bien), getString(R.string.encuesta_dialog_titulo));
                            else
                                ((Actividad_Principal) getActivity()).mostarDialogo(getString(R.string.encuestra_dialgo_texto_mal), getString(R.string.encuesta_dialog_titulo));

                            progressDialog.dismiss();

                            arrRespuestasJSON.clear();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            ((Actividad_Principal) getActivity()).mostarDialogo(getString(R.string.encuestra_dialgo_texto_mal), getString(R.string.encuesta_dialog_titulo));
                            progressDialog.dismiss();

                            arrRespuestasJSON.clear();

                        }
                    });*/
                }
            }
        });
        ll.addView(btenviar);

    }

    private void createJsonObject() throws JSONException {

        setAnswerEdittext();
        setAnswerCheckbox();

        if (arrRespuestasJSON.size() < questionList.size()) {
            ((Actividad_Principal) getActivity()).pbPrincipal.setVisibility(View.GONE);
            ((Actividad_Principal) getActivity()).mostarDialogo("Faltan Preguntas por completar", "Encuesta");
            return;

        }

        JSONObject answers = new JSONObject();
        for (int i = 0; i < arrRespuestasJSON.size(); i++) {

            if (arrRespuestasJSON.get(i).getTipo() == 4) {
                //Respuesta int
                JSONObject object1 = new JSONObject();
                object1.put("type", arrRespuestasJSON.get(i).getTipo());
                object1.put("answer", arrRespuestasJSON.get(i).getRes());

                answers.put(arrRespuestasJSON.get(i).getNombre(), object1);

            } else if (arrRespuestasJSON.get(i).getTipo() == 2) {
                //Lista de respuestas

                JSONObject object2 = new JSONObject();
                object2.put("type", arrRespuestasJSON.get(i).getTipo());

                JSONArray respuestas = new JSONArray();

                for (int j = 0; j < arrRespuestasJSON.get(i).getRespuestas().size(); j++) {
                    respuestas.put(arrRespuestasJSON.get(i).getRespuestas().get(j));
                }
                object2.put("answer", respuestas);

                answers.put(arrRespuestasJSON.get(i).getNombre(), object2);

            } else {
                //Respuesrta string
                JSONObject object3 = new JSONObject();
                object3.put("type", arrRespuestasJSON.get(i).getTipo());
                object3.put("answer", arrRespuestasJSON.get(i).getRespuesta());

                answers.put(arrRespuestasJSON.get(i).getNombre(), object3);

            }

        }
        data = new JSONObject();
        data.put("id", ((Actividad_Principal) getActivity()).datos.getId());
        data.put("survey_id", 1);
        data.put("answers", answers);

        Log.d("ENVIO", "" + data);

    }

    private void setAnswerEdittext() {

        for (int e = 0; e < arrPreguntasAbiertas.size(); e++) {
            if (!arrPreguntasAbiertas.get(e).getEditText().getText().toString().equals("")) {
                resEdittex.setTipo(5);
                resEdittex.setNombre(arrPreguntasAbiertas.get(e).getNombre());
                resEdittex.setRespuesta(arrPreguntasAbiertas.get(e).getEditText().getText().toString());

                if (!arrRespuestasJSON.contains(resEdittex))
                    arrRespuestasJSON.add(resEdittex);
            } else
                arrRespuestasJSON.remove(resEdittex);
        }
    }

    private void setAnswerCheckbox() {
        String nombre = "";
        for (int i = 0; i < arrMultipleID.size(); i++) {

            resCheckBox = arrMultipleID.get(i).getRespuestaJSON();

            resCheckBox.setId_pregunta(arrMultipleID.get(i).getId());

            List<String> lista = new ArrayList<>();

            for (int j = 0; j < arrMultiple.size(); j++) {
                if (arrMultiple.get(j).getId_pregunta() == arrMultipleID.get(i).getId()) {
                    nombre = arrMultiple.get(j).getTitulo();
                    lista.add(arrMultiple.get(j).getRespuesta());
                }

            }
            resCheckBox.setNombre(nombre);
            resCheckBox.setRespuestas(lista);
            resCheckBox.setTipo(2);

            if (!resCheckBox.getNombre().equals("")) {
                if (resCheckBox.getRespuestas().size() != 0)
                    if (!arrRespuestasJSON.contains(resCheckBox))
                        arrRespuestasJSON.add(resCheckBox);
            }
        }

    }

}

