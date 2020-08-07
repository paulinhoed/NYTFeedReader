package com.testvuxx.nytfeedreader.api;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
//https://api.nytimes.com/svc/topstories/v2/home.json?api-key=wVPYOHVtFBIsD9tgFhMRIVkdrtwxAjVW
//    private static final String URL = "https://api.nytimes.com/svc/topstories/v2/home.json";

    private static Retrofit retrofit;
    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
    private static String API_KEY = "wVPYOHVtFBIsD9tgFhMRIVkdrtwxAjVW";
    private static String API_BASE_URL = "https://api.nytimes.com/svc/topstories/v2/";

    private static OkHttpClient.Builder getHttpClientBuilder() {
        // Append api-key parameter to every query
        Interceptor apiKeyInterceptor = chain -> {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder().addQueryParameter("api-key", API_KEY).build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        };

        httpClientBuilder
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(apiKeyInterceptor).build();
//                .addInterceptor(chain -> {
//                    Request.Builder requestBuilder = chain.request().newBuilder();
//                    requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded");
//                    requestBuilder.addHeader("Accept", "application/json");
//                    return chain.proceed(requestBuilder.build());
//                });
        return httpClientBuilder;
    }

    private static OkHttpClient httpClient = getHttpClientBuilder().build();

    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .client(httpClient)
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
