package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitClient {

    private static final String BASE_URL = "https://lamp.ms.wits.ac.za/~s2452375/";
    public static retrofitClient minstance;
    private Retrofit retrofit;

    private retrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static synchronized retrofitClient getInstance(){
        if (minstance == null){
            minstance = new retrofitClient();
        }
        return minstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
