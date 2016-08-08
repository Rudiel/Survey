package com.gloobe.survey.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.gloobe.survey.Actividades.Actividad_Principal;
import com.gloobe.survey.Adaptadores.RecyclerViewAdapter;
import com.gloobe.survey.IRecyclerItemClic;
import com.gloobe.survey.Modelos.Encuesta;
import com.gloobe.survey.R;
import com.gloobe.survey.SurveyInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rudielavilaperaza on 26/07/16.
 */
public class Fragment_Lista extends Fragment implements IRecyclerItemClic {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog progressDialog;
    private ImageView ivLogout, ivIdioma;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_lista, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        ivIdioma = (ImageView) getActivity().findViewById(R.id.ivIdioma);
        ivLogout = (ImageView) getActivity().findViewById(R.id.ivLogout);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(((Actividad_Principal) getActivity()).surveyList, this, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        progressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
        progressDialog.setCancelable(false);

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        ivIdioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        ((Actividad_Principal) getActivity()).questionList = null;
        ((Actividad_Principal) getActivity()).answerList = null;
        ((Actividad_Principal) getActivity()).encuesta = null;

    }

    @Override
    public void clicItem(View v) {
        int position = mRecyclerView.getChildLayoutPosition(v);
        callGetEncuestas(position);
    }

    private void callGetEncuestas(final int position) {

        progressDialog.show();

        ((Actividad_Principal) getActivity()).position = position;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url_global))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SurveyInterface service = retrofit.create(SurveyInterface.class);

        Call<Encuesta> encuesta = service.getEncuestas(((Actividad_Principal) getActivity()).surveyList.get(position).getClient_id(), ((Actividad_Principal) getActivity()).surveyList.get(position).getId());

        encuesta.enqueue(new Callback<Encuesta>() {
            @Override
            public void onResponse(Call<Encuesta> call, Response<Encuesta> response) {
                if (response.body() != null) {
                    ((Actividad_Principal) getActivity()).encuesta = response.body();

                    if (((Actividad_Principal) getActivity()).encuesta.getData().getQuestions().size() != 0 && ((Actividad_Principal) getActivity()).encuesta.getData().getQuestions() != null) {

                        ((Actividad_Principal) getActivity()).questionList = ((Actividad_Principal) getActivity()).encuesta.getData().getQuestions();

                        if (((Actividad_Principal) getActivity()).questionList.get(position).getAnswers().size() != 0 && ((Actividad_Principal) getActivity()).questionList.get(position).getAnswers() != null) {

                            ((Actividad_Principal) getActivity()).answerList = ((Actividad_Principal) getActivity()).encuesta.getData().getQuestions().get(position).getAnswers();

                            ((Actividad_Principal) getActivity()).iniciarFragment(new Frgament_Encuesta(), true);

                        } else
                            ((Actividad_Principal) getActivity()).mostarDialogo(getString(R.string.lista_dialogo_texto_mal), getString(R.string.lista_dialogo_titulo));

                    } else
                        ((Actividad_Principal) getActivity()).mostarDialogo(getString(R.string.lista_dialogo_texto_mal), getString(R.string.lista_dialogo_titulo));

                } else
                    Toast.makeText(getActivity(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Encuesta> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }
}
