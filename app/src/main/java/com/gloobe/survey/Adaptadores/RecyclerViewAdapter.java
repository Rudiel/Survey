package com.gloobe.survey.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gloobe.survey.Interfaces.IRecyclerItemClic;
import com.gloobe.survey.Modelos.Survey;
import com.gloobe.survey.R;

import java.util.List;

/**
 * Created by rudielavilaperaza on 26/07/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Survey> surveyList;
    private IRecyclerItemClic listener;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEncuesta;

        public ViewHolder(View v) {
            super(v);
            tvEncuesta = (TextView) v.findViewById(R.id.tvLista);
        }
    }

    public RecyclerViewAdapter(List<Survey> surveyList, IRecyclerItemClic listener, Context context) {
        this.surveyList = surveyList;
        this.listener = listener;
        this.context = context;

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
        if (position % 2 == 0)
            holder.tvEncuesta.setTextColor(context.getResources().getColor(R.color.tema_naranja));
        else
            holder.tvEncuesta.setTextColor(context.getResources().getColor(R.color.tema_verde));

        holder.tvEncuesta.setText(surveyList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }
}