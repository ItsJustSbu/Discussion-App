package com.example.myapplication;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("Registration.php")
    Call<ResponseBody> createUser(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("Login.php")
    Call<ResponseBody> logUser(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("PostQuestions.php")
    Call<ResponseBody> post(
            @Field("author") String author,
            @Field("title") String title,
            @Field("question") String question
    );

    @FormUrlEncoded
    @POST("PostResponse.php")
    Call<ResponseBody> respond(
            @Field("responder") String responder,
            @Field("title") String title,
            @Field("response") String response
    );

    @GET("displayQuestions.php")
    Call<List<Data>> getData();

    @GET("displayResponses.php")
    Call<List<Responses>> getResponses();
}
