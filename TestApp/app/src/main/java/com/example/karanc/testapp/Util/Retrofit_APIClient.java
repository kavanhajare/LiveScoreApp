package com.example.karanc.testapp.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by karanc on 04-11-2017.
 */

public class Retrofit_APIClient {
    // Retrofit Instance
    static Retrofit retrofit = null;

    public static Retrofit getClient(){
        OkHttpClient client = new OkHttpClient.Builder().build();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")    // BaseURL which can be manipulated later
                .addConverterFactory(GsonConverterFactory.create(gson)) //ConverterFactory for JSON Parsing, using Gson Lib
                .client(client) //HttpClient
                .build();

        return retrofit;
    }
}
