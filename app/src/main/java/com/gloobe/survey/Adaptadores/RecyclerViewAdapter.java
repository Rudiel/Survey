package com.gloobe.survey.Adaptadores;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gloobe.survey.Interfaces.IRecyclerItemClic;
import com.gloobe.survey.Modelos.Models.Response.Survey;
import com.gloobe.survey.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by rudielavilaperaza on 26/07/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Survey> surveyList;
    private IRecyclerItemClic listener;
    private Context context;
    private Typeface tfTitulo;
    private Typeface tfTextos;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEncuesta;
        public TextView tvFecha;
        public TextView tvNumero;
        public CardView rlNumero;

        public ViewHolder(View v) {
            super(v);
            tvEncuesta = (TextView) v.findViewById(R.id.tvLista);
            tvFecha = (TextView) v.findViewById(R.id.tvFecha);
            tvNumero = (TextView) v.findViewById(R.id.tvNumero);
            rlNumero = (CardView) v.findViewById(R.id.rlNumero);
        }
    }

    public RecyclerViewAdapter(List<Survey> surveyList, IRecyclerItemClic listener, Context context, Typeface tfTitulo, Typeface tfTexto) {
        this.surveyList = surveyList;
        this.listener = listener;
        this.context = context;
        this.tfTextos = tfTexto;
        this.tfTitulo = tfTitulo;

    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_itemlista, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.clicItem(view);
            }
        });
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        /*if (survey_id % 2 == 0)
            holder.tvEncuesta.setTextColor(context.getResources().getColor(R.color.tema_naranja));
        else
            holder.tvEncuesta.setTextColor(context.getResources().getColor(R.color.tema_verde));*/
        holder.tvEncuesta.setTypeface(tfTitulo);
        holder.tvFecha.setTypeface(tfTextos);
        holder.tvNumero.setTypeface(tfTitulo);

        holder.tvEncuesta.setText(surveyList.get(position).getName());

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(surveyList.get(position).getCreated_at());
            String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
            holder.tvFecha.setText(context.getString(R.string.lista_creado) + " " +formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvNumero.setText(String.valueOf(surveyList.get(position).getQuestion_counter()));
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }
}