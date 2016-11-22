package com.gloobe.survey.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gloobe.survey.Actividades.Actividad_Principal;
import com.gloobe.survey.R;

/**
 * Created by rudielavilaperaza on 11/20/16.
 */

public class Fragment_Settings extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((Actividad_Principal) getActivity()).toolbar.setVisibility(View.VISIBLE);
    }
}
