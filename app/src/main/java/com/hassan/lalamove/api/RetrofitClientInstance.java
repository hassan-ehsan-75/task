package com.hassan.lalamove.api;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.hassan.lalamove.Tools.Constants.BASE_URL;


public class RetrofitClientInstance {

    private static Retrofit retrofit;


    public static Retrofit getRetrofitInstance(final Context context) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .header("Content-Type","X-www-form-urlencoded")
                        .header("X-Requested-With","XMLHttpRequest")
                        .build();
                return chain.proceed(request);
            }
        });
        httpClient.addInterceptor(logging);
        httpClient.connectTimeout(240,TimeUnit.SECONDS);
        httpClient.readTimeout(240, TimeUnit.SECONDS);
        httpClient.writeTimeout(240,TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit;
    }
}
