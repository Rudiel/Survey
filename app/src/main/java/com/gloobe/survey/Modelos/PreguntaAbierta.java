package com.gloobe.survey.Modelos;

import android.widget.EditText;

/**
 * Created by rudielavilaperaza on 02/08/16.
 */
public class PreguntaAbierta {

    private EditText editText;
    private String nombre;

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
