package com.gloobe.survey.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.gloobe.survey.R;

/**
 * Created by rudielavilaperaza on 12/5/16.
 */

public class Fragment_Support extends Fragment {

    private RelativeLayout rlCallcenter;
    private RelativeLayout rlCorreo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_support, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rlCallcenter = (RelativeLayout) getActivity().findViewById(R.id.rlLlamada);
        rlCorreo = (RelativeLayout) getActivity().findViewById(R.id.rlMensaje);

        rlCallcenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "9982392580";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

        rlCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
