package com.gloobe.survey.Adaptadores;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gloobe.survey.Modelos.Intro;
import com.gloobe.survey.R;

import java.util.ArrayList;

/**
 * Created by rudielavilaperaza on 26/07/16.
 */
public class AdapterImageSwipe extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Intro> intros;

    public AdapterImageSwipe(Context context, ArrayList<Intro> intros) {
        this.context = context;
        this.intros = intros;
    }

    @Override
    public int getCount() {
        return intros.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.layout_imageitem, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.ivImagen);
        TextView titulo = (TextView) itemView.findViewById(R.id.tvTitulo);
        TextView texto = (TextView) itemView.findViewById(R.id.tvTexto);

        titulo.setText(intros.get(position).getTitulo());
        texto.setText(intros.get(position).getTexto());
        Glide.with(context).load(intros.get(position).getImagen()).into(imageView);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
