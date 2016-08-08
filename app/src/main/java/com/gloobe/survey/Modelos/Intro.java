package com.gloobe.survey.Modelos;

/**
 * Created by rudielavilaperaza on 27/07/16.
 */
public class Intro {

    private String titulo, texto;
    private int imagen;

    public Intro(String titulo, String texto, int imagen) {
        this.titulo = titulo;
        this.texto = texto;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
