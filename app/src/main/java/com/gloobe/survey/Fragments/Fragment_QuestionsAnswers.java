package com.gloobe.survey.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.gloobe.survey.Actividades.Actividad_Principal;
import com.gloobe.survey.Adaptadores.Adapter_ExpandibleList;
import com.gloobe.survey.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rudielavilaperaza on 12/1/16.
 */

public class Fragment_QuestionsAnswers extends Fragment {

    private Adapter_ExpandibleList expAdapter;
    private ExpandableListView expList;
    private List<String> listPreguntas;
    private HashMap<String, List<String>> listRespuestas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_questionsanswers, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((Actividad_Principal) getActivity()).toolbar.setVisibility(View.GONE);
        ((Actividad_Principal) getActivity()).drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        expList = (ExpandableListView) getActivity().findViewById(R.id.explistQuestions);

        prepareListData();

        expAdapter = new Adapter_ExpandibleList(getActivity(), listPreguntas, listRespuestas, (((Actividad_Principal) getActivity()).tfTitulos));

        expList.setAdapter(expAdapter);
    }

    private void prepareListData() {
        listPreguntas = new ArrayList<String>();
        listRespuestas = new HashMap<String, List<String>>();

        // Adding child data
        listPreguntas.add("Question 1");
        listPreguntas.add("Question 2");
        listPreguntas.add("Question 3");
        listPreguntas.add("Question 4");
        listPreguntas.add("Question 5");
        listPreguntas.add("Question 6");
        listPreguntas.add("Question 7");


        // Adding child data
        List<String> question1 = new ArrayList<String>();
        question1.add("Answer 1");

        List<String> question2 = new ArrayList<String>();
        question2.add("Answer 2");

        List<String> question3 = new ArrayList<String>();
        question3.add("Answer 3");

        List<String> question4 = new ArrayList<String>();
        question4.add("Answer 4");

        List<String> question5 = new ArrayList<String>();
        question5.add("Answer 5");

        List<String> question6 = new ArrayList<String>();
        question6.add("Answer 6");

        List<String> question7 = new ArrayList<String>();
        question7.add("Answer 7");


        listRespuestas.put(listPreguntas.get(0), question1); // Header, Child data
        listRespuestas.put(listPreguntas.get(1), question2);
        listRespuestas.put(listPreguntas.get(2), question3);
        listRespuestas.put(listPreguntas.get(3), question4);
        listRespuestas.put(listPreguntas.get(4), question5);
        listRespuestas.put(listPreguntas.get(5), question6);
        listRespuestas.put(listPreguntas.get(6), question7);

    }
}
