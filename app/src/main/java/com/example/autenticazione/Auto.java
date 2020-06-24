package com.example.autenticazione;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Auto implements Parcelable {
    public String licensePlate;
    public String model;
    public long id;
    public String color;

    public static class AutoMetaData {
        public static String ID = "_id";
        public static String LICENSEPLATE = "licensePlate";
        public static String MODEL = "model";
        public static String COLOR = "color";
        public static String TABLE_NAME = "Auto";
        public static String[] COLUMNS = new String[] { ID, LICENSEPLATE, MODEL, COLOR };
    }

    public static final Parcelable.Creator<Auto> CREATOR = new Parcelable.Creator<Auto>() {

        @Override
        public Auto createFromParcel(Parcel source) {
            return new Auto(source);
        }

        @Override
        public Auto[] newArray(int size) {
            return new Auto[size];
        }
    };

    public Auto() {
    }

    private Auto(Parcel in) {
        id = in.readLong();
        licensePlate = in.readString();
        model = in.readString();
        color = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(licensePlate);
        dest.writeString(model);
        dest.writeString(color);
    }
}
