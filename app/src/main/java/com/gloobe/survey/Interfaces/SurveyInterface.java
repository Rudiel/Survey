package com.gloobe.survey.Interfaces;

import com.gloobe.survey.Modelos.Authorize;
import com.gloobe.survey.Modelos.Cliente;
import com.gloobe.survey.Modelos.Encuesta;
import com.gloobe.survey.Modelos.Models.Request.Login;
import com.gloobe.survey.Modelos.Models.Response.Survey;
import com.gloobe.survey.Modelos.Models.Response.User;

import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @Headers({"Content-Type: application/json"})
    @POST("sessions")
    Call<User> login(@Body Login login);

    @Headers({"Content-Type: application/json"})
    @GET("customers/{customer_id}/surveys")
    Call<List<Survey>> getSurveys(@Path("customer_id") int customer_id, @Header("Authorization") String token);

    @Headers({"Content-Type: application/json"})
    @GET("customers/{customer_id}/surveys/{survey_id}")
    Call<com.gloobe.survey.Modelos.Models.Response.Encuesta> getEncuesta(@Path("customer_id") int customer_id, @Path("survey_id") int survey_id, @Header("Authorization") String token);

    @Headers({"Content-Type: application/json"})
    @POST("surveys/{survey_id}/submissions")
    Call<ResponseBody> setSurvey(@Path("survey_id") int survey_id, @Header("Authorization") String token, @Body RequestBody encuesta);

}
