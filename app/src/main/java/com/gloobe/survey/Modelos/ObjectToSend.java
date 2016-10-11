package com.gloobe.survey.Modelos;

import java.util.UUID;

import okhttp3.RequestBody;

/**
 * Created by rudielavilaperaza on 11/08/16.
 */
public class ObjectToSend {

    private RequestBody requestBody;

    private UUID id;

    private int id_encuesta;

    private int position;

    private int client_id;


    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getId_encuesta() {
        return id_encuesta;
    }

    public void setId_encuesta(int id_encuesta) {
        this.id_encuesta = id_encuesta;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
