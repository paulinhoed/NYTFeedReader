package com.testvuxx.nytfeedreader.homeScreen;


import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.testvuxx.nytfeedreader.ReadPreferences;
import com.testvuxx.nytfeedreader.util.DialogUtil;
import com.testvuxx.nytfeedreader.util.GlideApp;
import com.testvuxx.tnytfeedreader.R;

import java.util.ArrayList;


interface HomeActivityInput {
    public void displayHomeData(HomeViewModel viewModel);
    void callApiError(String error);
}


public class HomeActivity extends AppCompatActivity
        implements HomeActivityInput {

    public static String TAG = HomeActivity.class.getSimpleName();
    public ArrayList<ReportModel> listOfReports = new ArrayList<ReportModel>();
    HomeInteractorInput output;
    HomeRouter router;

    ReadPreferences readPreferences;
    DialogUtil dialogUtil;

    private Button btnRefresh;
    private Button btnClearReads;
    private Spinner spnSection;

    private String section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        HomeConfigurator.INSTANCE.configure(this);
        readPreferences = new ReadPreferences(this);
        dialogUtil = new DialogUtil(this);

        bindViews();

        section = spnSection.getSelectedItem().toString();

        refreshList();

        spnSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                section = (String) parent.getItemAtPosition(position);
                refreshList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                refreshList();
            }
        });
        btnClearReads.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                readPreferences.resetReadListFromSharedPreference();
                refreshList();
            }
        });
    }

    private void refreshList() {
        dialogUtil.showProgress(getResources().getString(R.string.loading));
        listOfReports.clear();
        fetchMetaData();
        createReportListView();
    }

    private void bindViews() {
        btnRefresh = findViewById(R.id.btn_refresh);
        btnClearReads = findViewById(R.id.btn_clear_reads);
        spnSection = (Spinner) findViewById(R.id.spSection);
    }

    public void fetchMetaData() {
        HomeRequest homeRequest = new HomeRequest();
        homeRequest.section = section;
        ArrayList<ReportModel> listOfReadReports = new ArrayList<>();
        listOfReadReports = (ArrayList<ReportModel>) readPreferences.getReadListFromSharedPreference();
        homeRequest.listOfReadReports = listOfReadReports;
        output.fetchHomeData(homeRequest);
    }

    private void createReportListView() {
        ListView listView = (ListView) findViewById(R.id.listReports);
        listView.setAdapter(new ReportListAdapter());
        listView.setClickable(true);
        listView.setOnItemClickListener(router);
    }

    @Override
    public void displayHomeData(HomeViewModel viewModel) {
        listOfReports = viewModel.listOfReports;
        createReportListView();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogUtil.hideProgress();
            }
        }, 1000);
    }

    private class ReportListAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        ReportListAdapter() {
            layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return listOfReports.size();
        }

        @Override
        public Object getItem(int position) {
            return listOfReports.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.row_feed_new, null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.tvTitle);
                viewHolder.updatedDateTextView = (TextView) convertView.findViewById(R.id.tvUpdatedDate);
                viewHolder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.ivFeedNew);
                convertView.setTag(viewHolder);
            }
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.titleTextView.setText(listOfReports.get(position).title);
            viewHolder.updatedDateTextView.setText(formatDateTime(listOfReports.get(position).updated_date));

            GlideApp.with(getApplicationContext())
                    .load(stringToUrl(listOfReports.get(position).thumbnail))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(viewHolder.thumbnailImageView);
            return convertView;
        }
    }

    class ViewHolder {
        TextView titleTextView;
        TextView updatedDateTextView;
        ImageView thumbnailImageView;
    }

    @Override
    public void callApiError(String error) {
        CharSequence text = error;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    private Uri stringToUrl(String path) {
        Uri uri = null;
        if(path != null)
            uri = Uri.parse(path);
        return uri;
    }

    private String formatDateTime(String dateTime) {
        String date = dateTime.split("T")[0];
        date = date.replaceAll("-","/");
        String time = dateTime.split("T")[1];
        time = time.split(("-"))[0];
        return date + " - " + time;
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshList();
    }
}
