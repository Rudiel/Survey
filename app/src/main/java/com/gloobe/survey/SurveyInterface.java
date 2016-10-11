package com.gloobe.survey;

import com.gloobe.survey.Modelos.Authorize;
import com.gloobe.survey.Modelos.Cliente;
import com.gloobe.survey.Modelos.Encuesta;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by rudielavilaperaza on 26/07/16.
 */
public interface SurveyInterface {

    @Headers({"Content-Type: application/json"})
    @POST("clients")
    Call<Cliente> getClients(@Body Authorize authorize);

    @Headers({"Content-Type: application/json"})
    @GET("clients/{client_id}/surveys/{survey_id}")
    Call<Encuesta> getEncuestas(@Path("client_id") int client_id, @Path("survey_id") int survey_id);

    @Headers({"Content-Type: application/json"})
    @POST("clients/{client_id}/surveys/{survey_id}/user_answers")
    Call<ResponseBody> setResultado(@Path("client_id") int client_id, @Path("survey_id") int survey_id, @Body RequestBody resultado);
}
