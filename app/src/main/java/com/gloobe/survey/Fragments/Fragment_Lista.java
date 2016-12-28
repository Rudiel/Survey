package com.gloobe.survey.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.gloobe.survey.Actividades.Actividad_Principal;
import com.gloobe.survey.Adaptadores.RecyclerViewAdapter;
import com.gloobe.survey.Interfaces.IRecyclerItemClic;
import com.gloobe.survey.Modelos.Models.Response.Survey;
import com.gloobe.survey.R;
import com.gloobe.survey.Interfaces.SurveyInterface;
import com.gloobe.survey.Utils.Utils;

import java.util.List;

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
    private ImageView ivProfile;
    private TextView tvProfile;
    private SwipeRefreshLayout swipeRefreshLayout;

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


        ivProfile = (ImageView) getActivity().findViewById(R.id.ivProfile);
        tvProfile = (TextView) getActivity().findViewById(R.id.tvProfile);
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swiperefresh);

        //pbLista.getIndeterminateDrawable().setColorFilter(0xff2863, android.graphics.PorterDuff.Mode.MULTIPLY);


        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(((Actividad_Principal) getActivity()).surveyList, this, getActivity(),
                ((Actividad_Principal) getActivity()).tfTitulos, ((Actividad_Principal) getActivity()).tfTextos, ((Actividad_Principal) getActivity()).tfTitulosBold);
        mRecyclerView.setAdapter(mAdapter);

        Glide.with(this).load(Utils.getImageUser()).asBitmap().centerCrop().into(new BitmapImageViewTarget(ivProfile) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                ivProfile.setImageDrawable(circularBitmapDrawable);
            }
        });

        Utils.setContext(getActivity());

        tvProfile.setText(getString(R.string.lista_welcome) + " " + Utils.getUserName());
        tvProfile.setTypeface(((Actividad_Principal) getActivity()).tfTitulosBold);

        /*ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });*/

        ((Actividad_Principal) getActivity()).questionList = null;
        ((Actividad_Principal) getActivity()).answerList = null;

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.survey_rosado), getResources().getColor(R.color.survey_morado));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getEncuestas(Utils.getUserID(), Utils.getApiKey());
            }
        });

    }

    @Override
    public void clicItem(View v) {

        ((Actividad_Principal) getActivity()).pbPrincipal.setVisibility(View.VISIBLE);

        int position = mRecyclerView.getChildLayoutPosition(v);
        ((Actividad_Principal) getActivity()).survey_id = ((Actividad_Principal) getActivity()).surveyList.get(position).getId();
        Utils.setContext(getActivity());
        getEncuesta(Utils.getUserID(), ((Actividad_Principal) getActivity()).survey_id, Utils.getApiKey());
        //callGetEncuestas(survey_id);
    }

    private void getEncuesta(int customer_id, int survey_id, String token) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url_global))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SurveyInterface service = retrofit.create(SurveyInterface.class);

        Call<com.gloobe.survey.Modelos.Models.Response.Encuesta> encuestaCall = service.getEncuesta(customer_id, survey_id, "Token token=" + token);

        encuestaCall.enqueue(new Callback<com.gloobe.survey.Modelos.Models.Response.Encuesta>() {
            @Override
            public void onResponse(Call<com.gloobe.survey.Modelos.Models.Response.Encuesta> call, Response<com.gloobe.survey.Modelos.Models.Response.Encuesta> response) {
                if (response.body() != null) {
                    ((Actividad_Principal) getActivity()).encuesta = response.body();
                    ((Actividad_Principal) getActivity()).pbPrincipal.setVisibility(View.GONE);
                    ((Actividad_Principal) getActivity()).iniciarFragment(new Fragment_Survey(), true, ((Actividad_Principal) getActivity()).FG_SURVEY);
                }
            }

            @Override
            public void onFailure(Call<com.gloobe.survey.Modelos.Models.Response.Encuesta> call, Throwable t) {
                ((Actividad_Principal) getActivity()).pbPrincipal.setVisibility(View.GONE);
            }
        });

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
                if (response.body() != null) {
                    ((Actividad_Principal) getActivity()).surveyList = response.body();
                    mAdapter = new RecyclerViewAdapter(((Actividad_Principal) getActivity()).surveyList, Fragment_Lista.this, getActivity(),
                            ((Actividad_Principal) getActivity()).tfTitulos, ((Actividad_Principal) getActivity()).tfTextos, ((Actividad_Principal) getActivity()).tfTitulosBold);
                    mRecyclerView.setAdapter(mAdapter);
                }
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<List<Survey>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }


    /*private void callGetEncuestas(final int survey_id) {

        progressDialog.show();

        ((Actividad_Principal) getActivity()).survey_id = survey_id;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url_global))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SurveyInterface service = retrofit.create(SurveyInterface.class);

        Call<Encuesta> encuesta = service.getEncuestas(((Actividad_Principal) getActivity()).surveyList.get(survey_id).getClient_id(), ((Actividad_Principal) getActivity()).surveyList.get(survey_id).getId());

        encuesta.enqueue(new Callback<Encuesta>() {
            @Override
            public void onResponse(Call<Encuesta> call, Response<Encuesta> response) {
                if (response.body() != null) {
                    ((Actividad_Principal) getActivity()).encuesta = response.body();

                    if (((Actividad_Principal) getActivity()).encuesta.getData().getQuestions().size() != 0 && ((Actividad_Principal) getActivity()).encuesta.getData().getQuestions() != null) {

                        ((Actividad_Principal) getActivity()).questionList = ((Actividad_Principal) getActivity()).encuesta.getData().getQuestions();

                        ((Actividad_Principal) getActivity()).iniciarFragment(new Frgament_Encuesta(), true);

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

    }*/
}
