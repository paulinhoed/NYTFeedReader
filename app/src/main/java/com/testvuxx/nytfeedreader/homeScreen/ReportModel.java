package com.testvuxx.nytfeedreader.homeScreen;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Objects;

public class ReportModel implements Parcelable {

    public String title;
    public String thumbnail;
    public String url;
    public String created_date;
    public String updated_date;
    public ArrayList<MultimediaModel> multimedia = new ArrayList<MultimediaModel>();

    public ReportModel() {}

    protected ReportModel(Parcel in) {
        title = in.readString();
        thumbnail = in.readString();
        url = in.readString();
        created_date = in.readString();
        updated_date = in.readString();
        in.readList(multimedia, MultimediaModel.class.getClassLoader());
    }

    public static final Creator<ReportModel> CREATOR = new Creator<ReportModel>() {
        @Override
        public ReportModel createFromParcel(Parcel in) {
            return new ReportModel(in);
        }

        @Override
        public ReportModel[] newArray(int size) {
            return new ReportModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(thumbnail);
        dest.writeString(url);
        dest.writeString(created_date);
        dest.writeString(updated_date);
        dest.writeList(multimedia);
    }

    @Override
    public String toString() {
        return "\n\nReportModel{" +
                "\n   title='" + title +
                "\n   thumbnail='" + thumbnail +
                "\n   url='" + url +
                "\n   created_date='" + created_date +
                "\n   updated_date='" + updated_date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportModel that = (ReportModel) o;
        return title.equals(that.title) &&
                thumbnail.equals(that.thumbnail) &&
                url.equals(that.url) &&
                created_date.equals(that.created_date) &&
                updated_date.equals(that.updated_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, thumbnail, url, created_date, updated_date);
    }
}
