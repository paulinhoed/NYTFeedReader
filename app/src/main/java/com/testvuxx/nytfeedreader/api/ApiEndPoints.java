package com.testvuxx.nytfeedreader.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiEndPoints {

    @GET("{section}.json")
    Call<ApiHomeModel> getTopReportsBySection(@Path("section") String section);
}
