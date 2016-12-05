package com.gloobe.survey.Adaptadores;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.gloobe.survey.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rudielavilaperaza on 12/1/16.
 */

public class Adapter_ExpandibleList extends BaseExpandableListAdapter {


    private Context context;
    private List<String> listPreguntas;
    private HashMap<String, List<String>> listaRespuestas;

    public Adapter_ExpandibleList(Context context, List<String> listPreguntas, HashMap<String, List<String>> listaRespuestas) {
        this.context = context;
        this.listPreguntas = listPreguntas;
        this.listaRespuestas = listaRespuestas;
    }

    @Override
    public int getGroupCount() {
        return listPreguntas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listaRespuestas.get(listPreguntas.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listPreguntas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listaRespuestas.get(listPreguntas.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String pregunta = (String) getGroup(groupPosition);
        if(convertView ==null){
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_fragment_questionsanswers_group, null);
        }
        TextView  tvPregunta= (TextView) convertView.findViewById(R.id.lblListHeader);
        tvPregunta.setTypeface(null, Typeface.BOLD);
        tvPregunta.setText(pregunta);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String respuesta= (String) getChild(groupPosition,childPosition);

        if(convertView==null){
            LayoutInflater infalInflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_fragment_questionsanswers_item, null);
        }

        TextView tvRespuesta = (TextView) convertView.findViewById(R.id.lblListItem);
        tvRespuesta.setText(respuesta);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
