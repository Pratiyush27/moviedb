package com.example.inshortsv1.requests;

import com.example.inshortsv1.utils.Api;
import com.example.inshortsv1.utils.Credential;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Services {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Credential.Base)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static Api api = retrofit.create(Api.class);

    public static Api getApi()
    {
        return api;
    }

}
