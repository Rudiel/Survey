package com.gloobe.survey.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.gloobe.survey.Actividades.Actividad_Principal;
import com.gloobe.survey.Interfaces.SurveyInterface;
import com.gloobe.survey.Modelos.Models.Request.ObjectToSend;
import com.gloobe.survey.Modelos.Models.Response.Encuesta;
import com.gloobe.survey.Modelos.Models.Response.Question;
import com.gloobe.survey.R;
import com.gloobe.survey.Utils.DatePikerFragment;
import com.gloobe.survey.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private ImageView ivPerfil;
    private Encuesta encuesta;
    private List<com.gloobe.survey.Modelos.Models.Request.Question> lisQuestionsRequest;
    private Button btSend;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_encuestas, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Utils.setContext(getActivity());

        ((Actividad_Principal) getActivity()).toolbar.setVisibility(View.GONE);
        ((Actividad_Principal) getActivity()).drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


        ll = (LinearLayout) getActivity().findViewById(R.id.llEncuestas);
        llContenedor = (LinearLayout) getActivity().findViewById(R.id.llContenedorEncuesta);
        ivLogo = (ImageView) getActivity().findViewById(R.id.ivLogo);
        ivPerfil = (ImageView) getActivity().findViewById(R.id.ivSurveyProfile);

        encuesta = ((Actividad_Principal) getActivity()).encuesta;

        if (encuesta.getAvatar().getUrl() != null) {
            Glide.with(getActivity()).load(encuesta.getAvatar().getUrl()).centerCrop().into(ivLogo);
            if (((Actividad_Principal) getActivity()).surveyListObj.getAvatar().getImage().getUrl() != null) {
                ivPerfil.setVisibility(View.VISIBLE);
                Glide.with(this).load(((Actividad_Principal) getActivity()).surveyListObj.getAvatar().getImage().getUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(ivPerfil) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        ivPerfil.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }
        } else {
            Glide.with(getActivity()).load(R.drawable.unnamed).centerCrop().into(ivLogo);
            ivPerfil.setVisibility(View.GONE);
        }

        lisQuestionsRequest = new ArrayList<>();

        for (int q = 0; q < encuesta.getQuestions().size(); q++) {

            switch (encuesta.getQuestions().get(q).getQuestion_type()) {
                case "open":
                    //abierta editext
                    createOpenQuestion(encuesta.getQuestions().get(q));
                    break;
                case "single":
                    //una opcion radio button
                    createSingleChoiseQuestion(encuesta.getQuestions().get(q));
                    break;
                case "multiple":
                    //opcion multiple checkbox
                    createMultipleChoiseQuestion(encuesta.getQuestions().get(q));
                    break;
                case "date":
                    //fecha date
                    createDateQuestion(encuesta.getQuestions().get(q));
                    break;
                case "image":
                    createImageQuestion(encuesta.getQuestions().get(q));
                    //Imagenes
                    break;
                case "rating":
                    createRaitingQuestion(encuesta.getQuestions().get(q));
                    //Raiting
                    break;
                case "list":
                    createSingleChoiceListQuestion(encuesta.getQuestions().get(q));
                    //Una opcion de una lista spinner
                    break;

            }

        }

        createSendButton();

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
        ques.setQuestion_type(question.getQuestion_type());
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
        ques.setQuestion_type(question.getQuestion_type());
        ques.setQuestion_id(question.getId());
        lisQuestionsRequest.add(ques);

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
        ques.setQuestion_type(question.getQuestion_type());
        lisQuestionsRequest.add(ques);

        final List<Integer> checkBoxIdsList = new ArrayList<>();

        for (int i = 0; i < question.getChoices().size(); i++) {
            //CheckBox cb = new CheckBox(getActivity());
            //new RatingBar(new android.view.ContextThemeWrapper(getActivity(), R.style.RatingBarStyle), null, 0);
            final AppCompatCheckBox cb = new AppCompatCheckBox(new android.view.ContextThemeWrapper(getActivity(), R.style.CheckBoxStyle), null, 0);
            cb.setText(question.getChoices().get(i).getTitle());
            cb.setId(question.getId());
            cb.setTextColor(getResources().getColor(R.color.survey_text));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 20;
            cb.setLayoutParams(params);

            final int finalI = i;
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //agregar aqui las respuestas
                    if (isChecked) {
                        if (!checkBoxIdsList.contains(question.getChoices().get(finalI).getId()))
                            checkBoxIdsList.add(question.getChoices().get(finalI).getId());
                        //checkBoxList.add((CheckBox) buttonView);
                        //Toast.makeText(getActivity(), buttonView.getText() + "" + isChecked, Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("ID", "" + checkBoxIdsList.indexOf(question.getChoices().get(finalI).getId()));
                        checkBoxIdsList.remove(checkBoxIdsList.indexOf(question.getChoices().get(finalI).getId()));
                        //checkBoxList.remove((CheckBox) buttonView);
                        //Toast.makeText(getActivity(), buttonView.getText() + "" + isChecked, Toast.LENGTH_SHORT).show();
                    }

                }
            });

            linearLayout.addView(cb);
        }

        ques.setCheckBoxIdsList(checkBoxIdsList);

        if (!lisQuestionsRequest.contains(ques))
            lisQuestionsRequest.add(ques);

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
        btDate.setText(getString(R.string.send_survey_fecha));

        final com.gloobe.survey.Modelos.Models.Request.Question ques = new com.gloobe.survey.Modelos.Models.Request.Question();
        ques.setQuestion_type(question.getQuestion_type());
        ques.setQuestion_id(question.getId());
        lisQuestionsRequest.add(ques);

        btDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;
                        btDate.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);
                        ques.setDate(selectedday + "/" + selectedmonth + "/" + selectedyear);
                        if (!lisQuestionsRequest.contains(ques))
                            lisQuestionsRequest.add(ques);
                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });

        ll.addView(createCardview(createTitle(question.getTitle()), btDate));
    }

    private void createImageQuestion(final Question question) {

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getActivity());

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);

        final List<RelativeLayout> imageViewList = new ArrayList<>();

        final com.gloobe.survey.Modelos.Models.Request.Question ques = new com.gloobe.survey.Modelos.Models.Request.Question();
        ques.setQuestion_id(question.getId());
        ques.setQuestion_type(question.getQuestion_type());

        //necesito agregar las imagenes en el linear layout x cada pregunta
        //cada imagen tiene su listener
        for (int i = 0; i < question.getImages().size(); i++) {

            final RelativeLayout relativeLayout = new RelativeLayout(getActivity());
            RelativeLayout.LayoutParams paramsRelative = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeLayout.setLayoutParams(paramsRelative);

            final ImageView imagen = new ImageView(getActivity());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.dimen_carita), (int) getResources().getDimension(R.dimen.dimen_carita));
            params.addRule(RelativeLayout.CENTER_IN_PARENT);

            if (question.getImages().get(i).getFile().getUrl() != null)
                Glide.with(getActivity()).load(question.getImages().get(i).getFile().getUrl()).into(imagen);
            else
                Glide.with(getActivity()).load(question.getImages().get(i).getReference_path()).into(imagen);
            relativeLayout.setBackground(getResources().getDrawable(R.drawable.image_selected_background));
            relativeLayout.setBackground(null);
            params.bottomMargin = (int)getResources().getDimension(R.dimen.tutorial_button_height);
            imagen.setLayoutParams(params);


            final TextView textView = new TextView(getActivity());
            RelativeLayout.LayoutParams paramsText = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            textView.setText(question.getImages().get(i).getName());
            textView.setTypeface(((Actividad_Principal) getActivity()).tfTitulos);
            textView.setTextColor(getResources().getColor(R.color.survey_text));
            paramsText.addRule(RelativeLayout.CENTER_HORIZONTAL);
            paramsText.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            paramsText.topMargin = (int)getResources().getDimension(R.dimen.tutorial_button_height);
            textView.setLayoutParams(paramsText);


            final int finalI = i;
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int k = 0; k < imageViewList.size(); k++) {
                        imageViewList.get(k).setBackground(null);

                    }
                    relativeLayout.setBackground(getResources().getDrawable(R.drawable.image_selected_background));
                    ques.setChoice_id(question.getImages().get(finalI).getId());
                }
            });

            if (!imageViewList.contains(relativeLayout))
                imageViewList.add(relativeLayout);

            relativeLayout.addView(imagen);
            relativeLayout.addView(textView);

            linearLayout.addView(relativeLayout);

        }

        if (!lisQuestionsRequest.contains(ques))
            lisQuestionsRequest.add(ques);

        horizontalScrollView.addView(linearLayout);


        ll.addView(createCardview(createTitle(question.getTitle()), horizontalScrollView));
        //ll.addView(linearLayout);

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
        ques.setQuestion_type(question.getQuestion_type());
        lisQuestionsRequest.add(ques);

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

        //Spinner spinner = new Spinner(new android.view.ContextThemeWrapper(getActivity(), R.style.SpinnerStyle3), null, 0);
        Spinner spinner = new Spinner(getActivity());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.layout_spinner, respuestas); //selected item will look like a spinner set from XML
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setPopupBackgroundResource(R.color.survey_rosado);
        spinner.setBackground(getResources().getDrawable(R.drawable.custom_spinner));


        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 20;
        params.rightMargin = 20;
        params.bottomMargin = 20;
        params.topMargin = 20;

        spinner.setLayoutParams(params);

        final com.gloobe.survey.Modelos.Models.Request.Question ques = new com.gloobe.survey.Modelos.Models.Request.Question();
        ques.setQuestion_id(question.getId());
        ques.setQuestion_type(question.getQuestion_type());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.survey_rosado));
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

        btSend = new Button(getActivity());
        btSend.setBackground(getResources().getDrawable(R.drawable.button_pink));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.encuesta_boton_enviar_w), (int) getResources().getDimension(R.dimen.encuesta_boton_enviar_h));
        params.gravity = Gravity.CENTER;
        params.bottomMargin = 20;
        params.topMargin = 20;
        btSend.setLayoutParams(params);
        btSend.setTextColor(getActivity().getResources().getColor(android.R.color.white));
        btSend.setText(getResources().getString(R.string.send_survey_title));
        btSend.setTypeface(((Actividad_Principal) getActivity()).tfTitulos);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btSend.setEnabled(false);
                // if (lisQuestionsRequest.size() != encuesta.getQuestions().size()) {

                //   showMessage(getString(R.string.send_survey_title), getString(R.string.send_survey_sizeinvalid));

                //} else {
                ((Actividad_Principal) getActivity()).pbPrincipal.setVisibility(View.VISIBLE);
                try {
                    createJson();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ((Actividad_Principal) getActivity()).pbPrincipal.setVisibility(View.GONE);
                }
                // }

            }
        });

        ll.addView(btSend);
    }

    private void createJson() throws JSONException {

        JSONObject objectSubmissions = new JSONObject();

        JSONObject objectAnswers = new JSONObject();

        objectAnswers.put("survey_id", encuesta.getId());
        objectAnswers.put("user_id", Utils.getUserID());

        JSONObject objectAttributes = new JSONObject();

        //add respuestas de acuerdo a la

        for (int i = 0; i < lisQuestionsRequest.size(); i++) {

            JSONObject questionObject = new JSONObject();
            questionObject.put("question_id", lisQuestionsRequest.get(i).getQuestion_id());

            if (lisQuestionsRequest.get(i).getQuestion_type().equals("rating")) {

                JSONObject ratingObject = new JSONObject();
                if (lisQuestionsRequest.get(i).getResponse() != null)
                    ratingObject.put("response", lisQuestionsRequest.get(i).getResponse());
                else
                    ratingObject.put("response", "5.0");
                questionObject.put("answer_raiting_attributes", ratingObject);
            }

            if (lisQuestionsRequest.get(i).getQuestion_type().equals("single") || lisQuestionsRequest.get(i).getQuestion_type().equals("list")) {

                JSONObject choiceObject = new JSONObject();
                choiceObject.put("choice_id", lisQuestionsRequest.get(i).getChoice_id());
                questionObject.put("choice_answer_attributes", choiceObject);
            }

            if (lisQuestionsRequest.get(i).getQuestion_type().equals("date")) {
                JSONObject dateObject = new JSONObject();
                if (lisQuestionsRequest.get(i).getDate() != null)
                    dateObject.put("response", lisQuestionsRequest.get(i).getDate());
                else
                    dateObject.put("response", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                questionObject.put("answer_date_attributes", dateObject);
            }

            if (lisQuestionsRequest.get(i).getQuestion_type().equals("open")) {
                JSONObject openObject = new JSONObject();
                openObject.put("response", lisQuestionsRequest.get(i).getEditText().getText().toString());
                questionObject.put("answer_open_attributes", openObject);

            }

            if (lisQuestionsRequest.get(i).getQuestion_type().equals("multiple")) {
                //opcion multiple
                JSONObject multipleObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                for (int w = 0; w < lisQuestionsRequest.get(i).getCheckBoxIdsList().size(); w++) {
                    jsonArray.put(lisQuestionsRequest.get(i).getCheckBoxIdsList().get(w));
                }
                multipleObject.put("choice_ids", jsonArray);

                questionObject.put("answer_multiple_attributes", multipleObject);

            }

            if (lisQuestionsRequest.get(i).getQuestion_type().equals("image")) {
                //imagenes
                JSONObject imageObject = new JSONObject();
                imageObject.put("image_id", lisQuestionsRequest.get(i).getChoice_id());
                questionObject.put("answer_image_attributes", imageObject);
            }

            objectAttributes.put(String.valueOf(i), questionObject);
        }

        objectAnswers.put("answers_attributes", objectAttributes);

        objectSubmissions.put("submission", objectAnswers);

        Log.d("OBJECT", objectSubmissions.toString());

        sendResponse(objectSubmissions);

    }


    private void sendResponse(JSONObject sur) {

        Utils.setContext(getActivity());
        if (Utils.isConnected()) {

            try {
                Log.d("DB", "" + Actividad_Principal.db4oHelper.db().query(ObjectToSend.class).size());

            } finally {
                // Actividad_Principal.db4oHelper.db().close();
            }


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.url_global))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            SurveyInterface service = retrofit.create(SurveyInterface.class);

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (sur.toString()));


            Call<ResponseBody> surveyCall = service.setSurvey(this.encuesta.getId(), "Token token=" + Utils.getApiKey(), body);

            surveyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.body() != null) {
                        ((Actividad_Principal) getActivity()).pbPrincipal.setVisibility(View.GONE);
                        showMessage(getResources().getString(R.string.send_survey_title), getResources().getString(R.string.send_survey_done), true);
                    } else {
                        ((Actividad_Principal) getActivity()).pbPrincipal.setVisibility(View.GONE);
                        showMessage(getResources().getString(R.string.send_survey_title), getResources().getString(R.string.send_survey_notsend), false);
                        btSend.setEnabled(true);

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ((Actividad_Principal) getActivity()).pbPrincipal.setVisibility(View.GONE);
                    showMessage(getResources().getString(R.string.send_survey_title), getResources().getString(R.string.send_survey_notsend), false);
                    btSend.setEnabled(true);

                }
            });

        } else {
            ObjectToSend objectToSend = new ObjectToSend();
            objectToSend.setId(UUID.randomUUID());
            objectToSend.setClient_id(Utils.getUserID());
            objectToSend.setId_encuesta(encuesta.getId());
            objectToSend.setRequestBody(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (sur.toString())));
            objectToSend.setApiKey(Utils.getApiKey());
            try {
                Actividad_Principal.db4oHelper.db().store(objectToSend);
                //Log.d("DB", "" + Actividad_Principal.db4oHelper.db().query(ObjectToSend.class).size());
                showMessage(getResources().getString(R.string.send_survey_title), getResources().getString(R.string.send_survey_noconnection), false);

            } finally {
                Actividad_Principal.db4oHelper.db().close();
            }
            //cerrarFragment();
            ((Actividad_Principal) getActivity()).pbPrincipal.setVisibility(View.GONE);
            btSend.setEnabled(true);
            ((Actividad_Principal) getActivity()).iniciarFragment(new Fragment_Survey(), false, Actividad_Principal.FG_SURVEY);
        }


    }

    private void showMessage(String titulo, String mensaje, final boolean closeFg) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_dialog_custom);

        Window window = dialog.getWindow();

        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setBackgroundDrawableResource(R.color.survey_dialog);

        TextView tvTitulo = (TextView) dialog.findViewById(R.id.tvDialogTitulo);
        tvTitulo.setText(titulo);
        tvTitulo.setTypeface(((Actividad_Principal) getActivity()).tfTitulos);

        TextView tvTexto = (TextView) dialog.findViewById(R.id.tvDialogText);
        tvTexto.setText(mensaje);
        tvTexto.setTypeface(((Actividad_Principal) getActivity()).tfTitulos);

        Button dialogButton = (Button) dialog.findViewById(R.id.btDialog);
        dialogButton.setTypeface(((Actividad_Principal) getActivity()).tfTitulos);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (closeFg)
                    cerrarFragment();
            }
        });

        dialog.show();

    }

    private void cerrarFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
        ((Actividad_Principal) getActivity()).toolbar.setVisibility(View.VISIBLE);
        ((Actividad_Principal) getActivity()).drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

}
