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
import android.widget.TextView;

import com.gloobe.survey.Actividades.Actividad_Principal;
import com.gloobe.survey.R;

/**
 * Created by rudielavilaperaza on 12/5/16.
 */

public class Fragment_Support extends Fragment {

    private RelativeLayout rlPhone;
    private RelativeLayout rlMail;
    private RelativeLayout rlFaq;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_support, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rlPhone = (RelativeLayout) getActivity().findViewById(R.id.rlPhone);
        rlMail = (RelativeLayout) getActivity().findViewById(R.id.rlMail);
        rlFaq = (RelativeLayout) getActivity().findViewById(R.id.rlFaq);

        ((TextView) getActivity().findViewById(R.id.tvSupportLlamada)).setTypeface(((Actividad_Principal) getActivity()).tfTitulos);
        ((TextView) getActivity().findViewById(R.id.tvSupportLlamadaIcon)).setTypeface(((Actividad_Principal) getActivity()).tfTitulos);
        ((TextView) getActivity().findViewById(R.id.tvSupportMail)).setTypeface(((Actividad_Principal) getActivity()).tfTitulos);
        ((TextView) getActivity().findViewById(R.id.tvSupportMailIcon)).setTypeface(((Actividad_Principal) getActivity()).tfTitulos);
        ((TextView) getActivity().findViewById(R.id.tvFaq)).setTypeface(((Actividad_Principal) getActivity()).tfTitulos);

        if (!getResources().getBoolean(R.bool.isTablet))
            rlPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = "9982392580";
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }
            });

        rlMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, "rudielap@gmail.com");

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        rlFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Actividad_Principal) getActivity()).iniciarFragment(new Fragment_QuestionsAnswers(), true, ((Actividad_Principal) getActivity()).FG_FAQ);
            }
        });


    }
}
