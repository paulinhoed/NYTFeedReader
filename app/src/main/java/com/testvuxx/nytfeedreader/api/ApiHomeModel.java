package com.testvuxx.nytfeedreader.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.testvuxx.nytfeedreader.homeScreen.ReportModel;

import java.util.ArrayList;

public class ApiHomeModel {
    @SerializedName("results")
    @Expose
    private ArrayList<ReportModel> reportList;

    public ArrayList<ReportModel> getReportList() {
        return reportList;
    }

    public void setReportList(ArrayList<ReportModel> reportList) {
        this.reportList = reportList;
    }
}
