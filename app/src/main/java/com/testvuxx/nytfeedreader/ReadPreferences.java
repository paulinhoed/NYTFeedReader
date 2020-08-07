package com.testvuxx.nytfeedreader;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.testvuxx.nytfeedreader.homeScreen.ReportModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReadPreferences {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Gson gson;
    // Context
    private Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String SHARED_PREF = "read_preference";
    private static final String READ_LIST = "read list";

    public ReadPreferences(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(SHARED_PREF, PRIVATE_MODE);
        editor = pref.edit();
        gson = new Gson();
    }

    private void saveReadList(String readListString) {
        editor.putString(READ_LIST, readListString);
        editor.commit();
    }

    private String loadReadList() {
        return pref.getString(READ_LIST, "");
    }

    private void resetReadList() {
        editor.remove(READ_LIST);
        editor.commit();
    }

    /**
     *
     */
    public void setReadListToSharedpreference(ArrayList readList) {
        //convert ArrayList object to String by Gson
        String jsonReadList = gson.toJson(readList);
        //save to shared preference
        saveReadList(jsonReadList);
    }

    public List getReadListFromSharedPreference() {
        //retrieve data from shared
        ArrayList<ReportModel> readList;
        String jsonReadList = loadReadList();
        Type type = new TypeToken<List<ReportModel>>(){}.getType();
        readList = gson.fromJson(jsonReadList, type);

        if (readList == null) {
            readList = new ArrayList<>();
        }
        return readList;
    }
    
    public void resetReadListFromSharedPreference() {
        resetReadList();
    }
}
