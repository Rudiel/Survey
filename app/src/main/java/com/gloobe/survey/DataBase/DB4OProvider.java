package com.gloobe.survey.DataBase;

import android.content.Context;


import com.gloobe.survey.Modelos.Models.Request.ObjectToSend;

import java.util.List;

/**
 * Created by rudielavilaperaza on 11/08/16.
 */
public class DB4OProvider extends Db4oHelper {

    private static DB4OProvider provider = null;

    public DB4OProvider(Context context) {
        super(context);
    }

    public static DB4OProvider getInstance(Context ctx) {
        if (provider == null)
            provider = new DB4OProvider(ctx);
        return provider;
    }

    //Metodo utilizado para almacenar el objeto en la base de datos
    public void store(ObjectToSend send) {
        db().store(send);
    }

    //Metodo utilizado para eliminar el objeto en la base de datos
    public void delete(ObjectToSend send) {
        db().delete(send);
    }

    //Metodo utilizado para obtener todos los objetos
    public List<ObjectToSend> findAll() {
        return db().query(ObjectToSend.class);
    }

    //Metodo utilizado para obtener una lista con objetos iguales al requerido
    public List<ObjectToSend> getRecord(ObjectToSend send){
        return db().queryByExample(send);
    }



}
