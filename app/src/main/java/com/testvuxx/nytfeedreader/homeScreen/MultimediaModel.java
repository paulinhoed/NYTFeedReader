package com.testvuxx.nytfeedreader.homeScreen;

import android.os.Parcel;
import android.os.Parcelable;

public class MultimediaModel implements Parcelable {

    public String url;
    public String caption;
    public String format;
    public String height;
    public String width;

    public MultimediaModel() {}

    protected MultimediaModel(Parcel in) {
        url = in.readString();
        caption = in.readString();
        format = in.readString();
        height = in.readString();
        width = in.readString();
    }

    public static final Creator<MultimediaModel> CREATOR = new Creator<MultimediaModel>() {
        @Override
        public MultimediaModel createFromParcel(Parcel in) {
            return new MultimediaModel(in);
        }

        @Override
        public MultimediaModel[] newArray(int size) {
            return new MultimediaModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(caption);
        dest.writeString(format);
        dest.writeString(height);
        dest.writeString(width);
    }
}
