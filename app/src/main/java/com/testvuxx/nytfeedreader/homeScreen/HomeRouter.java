package com.testvuxx.nytfeedreader.homeScreen;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import com.testvuxx.nytfeedreader.ReadPreferences;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


interface HomeRouterInput {
    public void goToBrowser(int position);
}

public class HomeRouter implements HomeRouterInput, AdapterView.OnItemClickListener {

    public static String TAG = HomeRouter.class.getSimpleName();
    public WeakReference<HomeActivity> activity;

    ReadPreferences readPreferences;

    @Override
    public void goToBrowser(int position){
        ReportModel report = activity.get().listOfReports.get(position);
        readPreferences = new ReadPreferences(activity.get());
        ArrayList<ReportModel> listOfReadReports = new ArrayList<>();
        listOfReadReports = (ArrayList<ReportModel>) readPreferences.getReadListFromSharedPreference();
        if(!checkIfReportWasRead(listOfReadReports,report)) {
            listOfReadReports.add(report);
            readPreferences.setReadListToSharedpreference(listOfReadReports);
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(report.url));
        activity.get().startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goToBrowser(position);
    }

    private boolean checkIfReportWasRead(ArrayList<ReportModel> list, ReportModel report) {
        for(ReportModel rpt : list) {
            if(rpt.equals(report))
                return true;
        }
        return false;
    }
}
