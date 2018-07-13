package com.example.phoen.popularmovies.rest;

import android.support.annotation.NonNull;

import com.example.phoen.popularmovies.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//original via https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
// with modifications from https://stackoverflow.com/questions/43366164/retrofit-and-okhttp-basic-authentication
// and https://futurestud.io/tutorials/retrofit-2-how-to-add-query-parameters-to-every-request
// and probably a bunch more places that I can't remember
// and https://stackoverflow.com/questions/34973432/okhttpclient-throws-exception-after-upgrading-to-okhttp3
public class ApiClient {

    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (null==retrofit) {

            final String apiKey = BuildConfig.API_KEY;

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(@NonNull Chain chain) throws IOException {
                    HttpUrl original = chain.request().url().newBuilder().addQueryParameter("api_key",apiKey).build();
                    Request request = chain.request().newBuilder().url(original).build();
                    return chain.proceed(request);
                }
            }).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

}
