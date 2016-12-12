package com.gloobe.survey.Fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.gloobe.survey.Actividades.Actividad_Principal;
import com.gloobe.survey.Adaptadores.RecyclerViewAdapter;
import com.gloobe.survey.Interfaces.IRecyclerItemClic;
import com.gloobe.survey.R;
import com.gloobe.survey.Interfaces.SurveyInterface;

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
    private ImageView ivProfile;
    private TextView tvProfile;

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

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(((Actividad_Principal) getActivity()).surveyList, this, getActivity(),
                ((Actividad_Principal) getActivity()).tfTitulos, ((Actividad_Principal) getActivity()).tfTextos);
        mRecyclerView.setAdapter(mAdapter);

        progressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
        progressDialog.setCancelable(false);

        Glide.with(this).load(R.drawable.pp).asBitmap().centerCrop().into(new BitmapImageViewTarget(ivProfile) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                ivProfile.setImageDrawable(circularBitmapDrawable);
            }
        });

        tvProfile.setText(getString(R.string.lista_welcome) + " " + ((Actividad_Principal) getActivity()).user.getName());
        tvProfile.setTypeface(((Actividad_Principal) getActivity()).tfTitulos);

        /*ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });*/

        ((Actividad_Principal) getActivity()).questionList = null;
        ((Actividad_Principal) getActivity()).answerList = null;

    }

    @Override
    public void clicItem(View v) {
        int position = mRecyclerView.getChildLayoutPosition(v);
        ((Actividad_Principal) getActivity()).survey_id = ((Actividad_Principal) getActivity()).surveyList.get(position).getId();
        getEncuesta(((Actividad_Principal) getActivity()).user.getId(), ((Actividad_Principal) getActivity()).survey_id, ((Actividad_Principal) getActivity()).user.getApi_key());
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
                    ((Actividad_Principal) getActivity()).iniciarFragment(new Fragment_Survey(), true,((Actividad_Principal)getActivity()).FG_SURVEY);

                }
            }

            @Override
            public void onFailure(Call<com.gloobe.survey.Modelos.Models.Response.Encuesta> call, Throwable t) {

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
