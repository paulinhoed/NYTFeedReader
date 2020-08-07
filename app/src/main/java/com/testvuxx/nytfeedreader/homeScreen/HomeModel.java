package com.testvuxx.nytfeedreader.homeScreen;

import java.util.ArrayList;

public class HomeModel {
}

class HomeViewModel {
    ArrayList<ReportModel> listOfReports;
}

class HomeRequest {
    String section;
    ArrayList<ReportModel> listOfReadReports;
}

class HomeResponse {
    ArrayList<ReportModel> listOfReports;
    ArrayList<ReportModel> listOfReadReports;
}
