package com.gloobe.survey.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.gloobe.survey.Actividades.Actividad_Principal;
import com.gloobe.survey.Interfaces.SurveyInterface;
import com.gloobe.survey.Modelos.Models.Request.ObjectToSend;
import com.gloobe.survey.Modelos.Models.Response.Encuesta;
import com.gloobe.survey.Modelos.Models.Response.Question;
import com.gloobe.survey.R;
import com.gloobe.survey.Utils.DatePikerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rudielavilaperaza on 12/8/16.
 */

public class Fragment_Survey extends Fragment {

    private LinearLayout ll;
    private LinearLayout llContenedor;
    private ImageView ivLogo;
    private Encuesta encuesta;
    private List<com.gloobe.survey.Modelos.Models.Request.Question> lisQuestionsRequest;
    private ProgressDialog progressDialog;


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

        encuesta = ((Actividad_Principal) getActivity()).encuesta;

        lisQuestionsRequest = new ArrayList<>();

        for (int q = 0; q < encuesta.getQuestions().size(); q++) {

            switch (encuesta.getQuestions().get(q).getQuestion_type().getId()) {
                case 1:
                    //abierta editext
                    createOpenQuestion(encuesta.getQuestions().get(q));
                    break;
                case 2:
                    //una opcion radio button
                    createSingleChoiseQuestion(encuesta.getQuestions().get(q));
                    break;
                case 3:
                    //opcion multiple checkbox
                    createMultipleChoiseQuestion(encuesta.getQuestions().get(q));
                    break;
                case 4:
                    //fecha date
                    createDateQuestion(encuesta.getQuestions().get(q));
                    break;
                case 5:
                    createImageQuestion(encuesta.getQuestions().get(q));
                    //Imagenes
                    break;
                case 6:
                    createRaitingQuestion(encuesta.getQuestions().get(q));
                    //Raiting
                    break;
                case 7:
                    createSingleChoiceListQuestion(encuesta.getQuestions().get(q));
                    //Una opcion de una lista spinner
                    break;

            }

        }

        createSendButton();

        progressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
    }

    private void createOpenQuestion(Question question) {

        //ll.addView(createTitle(question.getTitle()));
        EditText editText = new EditText(new android.view.ContextThemeWrapper(getActivity(), R.style.EdittextStyle));
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setHint(getString(R.string.encuesta_edittext_hint));
        editText.setHintTextColor(getResources().getColor(R.color.survey_gris));
        editText.setSingleLine(true);
        editText.setTextColor(getResources().getColor(R.color.survey_text));
        editText.setMaxLines(1);
        editText.setLines(1);
        //editText.setId(id);

        com.gloobe.survey.Modelos.Models.Request.Question ques = new com.gloobe.survey.Modelos.Models.Request.Question();
        ques.setQuestion_type(question.getQuestion_type().getId());
        ques.setQuestion_id(question.getId());
        ques.setEditText(editText);

        if (!lisQuestionsRequest.contains(ques))
            lisQuestionsRequest.add(ques);

        ll.addView(createCardview(createTitle(question.getTitle()), editText));

    }

    private void createSingleChoiseQuestion(final Question question) {

        final AppCompatRadioButton[] rb = new AppCompatRadioButton[question.getChoices().size()];
        final RadioGroup rg = new RadioGroup(getActivity());
        rg.setOrientation(RadioGroup.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 20;
        rg.setLayoutParams(params);

        for (int i = 0; i < question.getChoices().size(); i++) {
            rb[i] = new AppCompatRadioButton(new android.view.ContextThemeWrapper(getActivity(), R.style.RadioButtonStyle));
            rb[i].setText(question.getChoices().get(i).getTitle());
            rb[i].setTextColor(getResources().getColor(R.color.survey_text));
            rg.addView(rb[i]);
        }

        final com.gloobe.survey.Modelos.Models.Request.Question ques = new com.gloobe.survey.Modelos.Models.Request.Question();
        ques.setQuestion_type(question.getQuestion_type().getId());

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (rg.getCheckedRadioButtonId() != -1) {
                    int idb = rg.getCheckedRadioButtonId();
                    View radioButton = rg.findViewById(idb);
                    int radioId = rg.indexOfChild(radioButton);
                    AppCompatRadioButton btn = (AppCompatRadioButton) rg.getChildAt(radioId);
                    String selection = "";
                    try {
                        selection = (String) btn.getText();
                    } catch (Exception e) {

                    }
                    ques.setChoice_id(question.getChoices().get(radioId).getId());
                    if (!lisQuestionsRequest.contains(ques))
                        lisQuestionsRequest.add(ques);
                }
            }
        });

        ll.addView(createCardview(createTitle(question.getTitle()), rg));
    }

    private void createMultipleChoiseQuestion(final Question question) {

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.LEFT);

        final com.gloobe.survey.Modelos.Models.Request.Question ques = new com.gloobe.survey.Modelos.Models.Request.Question();
        ques.setQuestion_id(question.getId());
        ques.setQuestion_type(question.getQuestion_type().getId());

        List<CheckBox> checkBoxList= new ArrayList<>();


        for (int i = 0; i < question.getChoices().size(); i++) {
            //CheckBox cb = new CheckBox(getActivity());
            //new RatingBar(new android.view.ContextThemeWrapper(getActivity(), R.style.RatingBarStyle), null, 0);
            AppCompatCheckBox cb = new AppCompatCheckBox(new android.view.ContextThemeWrapper(getActivity(), R.style.CheckBoxStyle), null, 0);
            cb.setText(question.getChoices().get(i).getTitle());
            cb.setTextColor(getResources().getColor(R.color.survey_text));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 20;
            cb.setLayoutParams(params);

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //agregar aqui las respuestas
                    if (isChecked) {
                    } else {

                    }
                }
            });

            linearLayout.addView(cb);
        }

        ll.addView(createCardview(createTitle(question.getTitle()), linearLayout));

    }

    private void createDateQuestion(Question question) {

        final Button btDate = new Button(getActivity());
        //btDate.setBackgroundColor(getResources().getColor(R.color.survey_rosado));
        btDate.setBackground(getResources().getDrawable(R.drawable.custom_spinner));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.encuesta_boton_enviar_w), (int) getResources().getDimension(R.dimen.encuesta_boton_enviar_h));
        params.gravity = Gravity.CENTER;
        params.bottomMargin = 20;
        params.topMargin = 20;
        btDate.setLayoutParams(params);
        btDate.setId(question.getId());
        btDate.setTextColor(getActivity().getResources().getColor(R.color.survey_text));
        btDate.setText("Select a Date");

        final com.gloobe.survey.Modelos.Models.Request.Question ques = new com.gloobe.survey.Modelos.Models.Request.Question();
        ques.setQuestion_type(question.getQuestion_type().getId());
        ques.setQuestion_id(question.getId());

        btDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        btDate.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);
                        List<String> lisfecha = new ArrayList<>();
                        lisfecha.add(String.valueOf(selectedday));
                        lisfecha.add(String.valueOf(selectedmonth));
                        lisfecha.add(String.valueOf(selectedyear));
                        ques.setDate(lisfecha);
                        if (!lisQuestionsRequest.contains(ques))
                            lisQuestionsRequest.add(ques);
                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });

        ll.addView(createCardview(createTitle(question.getTitle()), btDate));
    }

    private void createImageQuestion(Question question) {
        // ll.addView(createCardview(createTitle(question.getTitle()), null));

    }

    private void createRaitingQuestion(final Question question) {

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);

        RatingBar rating = //new RatingBar(getActivity());
                new RatingBar(new android.view.ContextThemeWrapper(getActivity(), R.style.RatingBarStyle), null, 0);
        rating.setMax(5);
        LinearLayout.LayoutParams paramsStar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rating.setLayoutParams(paramsStar);
        rating.setNumStars(5);

        final com.gloobe.survey.Modelos.Models.Request.Question ques = new com.gloobe.survey.Modelos.Models.Request.Question();
        ques.setQuestion_id(question.getId());
        ques.setQuestion_type(question.getQuestion_type().getId());

        //rating.setOnRatingBarChangeListener(clickRatingBar(res, id, titulo));
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ques.setResponse(String.valueOf(rating));
                if (!lisQuestionsRequest.contains(ques))
                    lisQuestionsRequest.add(ques);

            }
        });

        linearLayout.addView(rating);

        ll.addView(createCardview(createTitle(question.getTitle()), linearLayout));

    }

    private void createSingleChoiceListQuestion(final Question question) {

        String[] respuestas = new String[question.getChoices().size()];

        for (int i = 0; i < question.getChoices().size(); i++) {
            respuestas[i] = question.getChoices().get(i).getTitle();
        }

        Spinner spinner = new Spinner(new android.view.ContextThemeWrapper(getActivity(), R.style.SpinnerStyle3), null, 0);
        //Spinner spinner = new Spinner(getActivity());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.layout_spinner, respuestas); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        //spinner.setId(id);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 20;
        params.rightMargin = 20;
        params.bottomMargin = 20;
        params.topMargin = 20;

        spinner.setLayoutParams(params);

        final com.gloobe.survey.Modelos.Models.Request.Question ques = new com.gloobe.survey.Modelos.Models.Request.Question();
        ques.setQuestion_id(question.getId());
        ques.setQuestion_type(question.getQuestion_type().getId());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ques.setChoice_id(question.getChoices().get(position).getId());
                if (!lisQuestionsRequest.contains(ques))
                    lisQuestionsRequest.add(ques);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ll.addView(createCardview(createTitle(question.getTitle()), spinner));


        //spinner.setOnItemSelectedListener(clickSpinner(res, id, mAnswerList, titulo));


    }


    private TextView createTitle(String title) {

        TextView titulo = new TextView(getActivity());
        titulo.setText(title);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 20;
        params.leftMargin = 20;
        titulo.setLayoutParams(params);
        //titulo.setTextSize(getResources().getDimension(R.dimen.encustas_titulos_s));
        titulo.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
        titulo.setTextColor(getResources().getColor(R.color.survey_text));
        titulo.setTypeface(((Actividad_Principal) getActivity()).tfTitulos);
        return titulo;
    }

    private CardView createCardview(View title, View view) {

        //CardView card = new CardView(new ContextThemeWrapper(getActivity(), R.style.CardViewStyle), null, 0);
        CardView card = new CardView(getActivity());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int margin = (int) getActivity().getResources().getDimension(R.dimen.margin_card);
        params.setMargins(margin, margin, margin, margin);
        card.setLayoutParams(params);
        card.setRadius(margin);

        LinearLayout cardInner = new LinearLayout(new ContextThemeWrapper(getActivity(), R.style.Widget_CardContent));
        cardInner.setOrientation(LinearLayout.VERTICAL);

        cardInner.addView(title);
        cardInner.addView(view);

        card.addView(cardInner);


        return card;
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePikerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "");
    }

    private void createSendButton() {

        final Button btSend = new Button(getActivity());
        btSend.setBackgroundColor(getResources().getColor(R.color.survey_rosado));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.encuesta_boton_enviar_w), (int) getResources().getDimension(R.dimen.encuesta_boton_enviar_h));
        params.gravity = Gravity.CENTER;
        params.bottomMargin = 20;
        params.topMargin = 20;
        btSend.setLayoutParams(params);
        btSend.setTextColor(getActivity().getResources().getColor(android.R.color.white));
        btSend.setText("Send Survey");

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                try {
                    createJson();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        });

        ll.addView(btSend);
    }

    private void createJson() throws JSONException {

        JSONObject objectAnswers = new JSONObject();

        objectAnswers.put("survey_id", encuesta.getId());
        objectAnswers.put("user_id", ((Actividad_Principal) getActivity()).user.getId());

        JSONObject objectAttributes = new JSONObject();

        //add respuestas de acuerdo a la

        for (int i = 0; i < lisQuestionsRequest.size(); i++) {

            JSONObject questionObject = new JSONObject();
            questionObject.put("question_id", lisQuestionsRequest.get(i).getQuestion_id());

            if (lisQuestionsRequest.get(i).getQuestion_type() == 6) {

                JSONObject ratingObject = new JSONObject();
                ratingObject.put("response", lisQuestionsRequest.get(i).getResponse());
                questionObject.put("answer_raiting_attributes", ratingObject);
            }

            if (lisQuestionsRequest.get(i).getQuestion_type() == 2 || lisQuestionsRequest.get(i).getQuestion_type() == 7) {

                JSONObject choiceObject = new JSONObject();
                choiceObject.put("choice_id", lisQuestionsRequest.get(i).getChoice_id());
                questionObject.put("choice_answer_attributes", choiceObject);
            }

            if (lisQuestionsRequest.get(i).getQuestion_type() == 4) {
                JSONObject dateObject = new JSONObject();
                dateObject.put("response1", lisQuestionsRequest.get(i).getDate().get(0));
                dateObject.put("response2", lisQuestionsRequest.get(i).getDate().get(1));
                dateObject.put("response3", lisQuestionsRequest.get(i).getDate().get(2));
                questionObject.put("answer_date_attributes", dateObject);
            }

            if (lisQuestionsRequest.get(i).getQuestion_type() == 1) {
                JSONObject openObject = new JSONObject();
                openObject.put("response", lisQuestionsRequest.get(i).getEditText().getText().toString());
                questionObject.put("answer_open_attributes", openObject);

            }

            if (lisQuestionsRequest.get(i).getQuestion_type() == 3) {
                //opcion multiple
                
            }

            if (lisQuestionsRequest.get(i).getQuestion_type() == 5) {
                //imagenes
            }

            objectAttributes.put(String.valueOf(i), questionObject);
        }

        objectAnswers.put("answers_attributes", objectAttributes);

        Log.d("OBJECT", objectAnswers.toString());

        //sendResponse(objectAnswers);

        //TO:DO borrar esto
        progressDialog.dismiss();

    }


    private void sendResponse(JSONObject sur) {

        ConnectivityManager conMan = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {


            try {
                Log.d("DB", "" + Actividad_Principal.db4oHelper.db().query(ObjectToSend.class).size());

            } finally {
                Actividad_Principal.db4oHelper.db().close();
            }


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.url_global))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            SurveyInterface service = retrofit.create(SurveyInterface.class);

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (sur.toString()));


            Call<ResponseBody> surveyCall = service.setSurvey(this.encuesta.getId(), "Token token=" + ((Actividad_Principal) getActivity()).user.getApi_key(), body);

            surveyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.body() != null) {
                        progressDialog.dismiss();
                        showMessage("Send Survey", "Encuesta enviada con exito");
                    } else {
                        showMessage("Send Survey", "Ocurrio un error");
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    showMessage("Send Survey", "Ocurrio un error");
                    progressDialog.dismiss();

                }
            });

        } else {
            ObjectToSend objectToSend = new ObjectToSend();
            objectToSend.setId(UUID.randomUUID());
            objectToSend.setClient_id(((Actividad_Principal) getActivity()).user.getId());
            objectToSend.setId_encuesta(encuesta.getId());
            objectToSend.setRequestBody(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (sur.toString())));

            try {
                Actividad_Principal.db4oHelper.db().store(objectToSend);
                Log.d("DB", "" + Actividad_Principal.db4oHelper.db().query(ObjectToSend.class).size());
                showMessage("Send Survey", "No cuentas con conexion a internet, la encuesta se enviará automaticamente cuando se establezca la conexión");

            } finally {
                Actividad_Principal.db4oHelper.db().close();
            }
            progressDialog.dismiss();
        }
    }

    private void showMessage(String titulo, String mensaje) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_dialog_custom);

        Window window = dialog.getWindow();

        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setBackgroundDrawableResource(R.color.survey_dialog);

        TextView tvTitulo = (TextView) dialog.findViewById(R.id.tvDialogTitulo);
        tvTitulo.setText(titulo);

        TextView tvTexto = (TextView) dialog.findViewById(R.id.tvDialogText);
        tvTexto.setText(mensaje);

        Button dialogButton = (Button) dialog.findViewById(R.id.btDialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


}
