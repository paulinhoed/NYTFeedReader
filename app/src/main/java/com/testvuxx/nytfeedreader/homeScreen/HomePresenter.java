package com.testvuxx.nytfeedreader.homeScreen;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

interface HomePresenterInput {
    void processRequestFetchHome(HomeResponse response);
    void processErrorRequest(String error);
}


public class HomePresenter implements HomePresenterInput {

    public static String TAG = HomePresenter.class.getSimpleName();

    //weak var output: HomePresenterOutput!
    public WeakReference<HomeActivityInput> output;

    @Override
    public void processRequestFetchHome(HomeResponse response) {
        int limit = 20;
        HomeViewModel homeViewModel = new HomeViewModel();
        homeViewModel.listOfReports = new ArrayList<>();
        if (response.listOfReports != null) {
            for(ReportModel report : response.listOfReports) {
                ReportModel rpt = new ReportModel();
                rpt.title = report.title;
                rpt.url = report.url;
                rpt.created_date = report.created_date;
                rpt.updated_date = report.updated_date;
                rpt.multimedia = report.multimedia;
                rpt.thumbnail = report.multimedia.get(1).url;

                if(!checkIfReportWasRead(response.listOfReadReports,rpt)
                && homeViewModel.listOfReports.size() < limit)
                    homeViewModel.listOfReports.add(rpt);
            }
            output.get().displayHomeData(homeViewModel);
        }
        else {
            processErrorRequest(null);
        }
    }

    @Override
    public void processErrorRequest(String error) {
        if(null == error) {
            String msg = "Null response from server";
            output.get().callApiError(msg);
        }
        else {
            output.get().callApiError(error);
        }
    }

    private boolean checkIfReportWasRead(ArrayList<ReportModel> list, ReportModel report) {
        if(!list.isEmpty()) {
            for(ReportModel rpt : list) {
                if(rpt.equals(report)){
                    return true;
                }
            }
        }
        return false;
    }

}
