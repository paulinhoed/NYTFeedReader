package com.testvuxx.nytfeedreader.homeScreen;

import com.testvuxx.nytfeedreader.api.ApiEndPoints;
import com.testvuxx.nytfeedreader.api.ApiHomeModel;
import com.testvuxx.nytfeedreader.api.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

interface HomeInteractorInput {
    public void fetchHomeData(HomeRequest request);
}


public class HomeInteractor implements HomeInteractorInput {

    public static String TAG = HomeInteractor.class.getSimpleName();
    public HomePresenterInput output;

    public void fetchHomeData(HomeRequest request) {
        ApiEndPoints apiService = RetrofitService.getRetrofitInstance().create(ApiEndPoints.class);
        Call<ApiHomeModel> call = apiService.getTopReportsBySection(request.section);
        call.enqueue(new Callback<ApiHomeModel>() {
            @Override
            public void onResponse(Call<ApiHomeModel> call, Response<ApiHomeModel> response) {
                if (response.body() != null) {
                    ArrayList<ReportModel> list = response.body().getReportList();
                    if (null != list) {
                        if (!list.isEmpty()) {
                            HomeResponse homeResponse = new HomeResponse();
                            homeResponse.listOfReports = list;
                            homeResponse.listOfReadReports = request.listOfReadReports;
                            output.processRequestFetchHome(homeResponse);
                        } else
                            output.processErrorRequest("Empty list");
                    } else output.processErrorRequest(null);
                } else
                    output.processErrorRequest(null);
            }

            @Override
            public void onFailure(Call<ApiHomeModel> call, Throwable t) {
                output.processErrorRequest(null);
            }
        });
    }
}
