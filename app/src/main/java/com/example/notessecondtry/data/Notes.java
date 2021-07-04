package com.example.notessecondtry.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Notes implements Parcelable {
    private String title;       // заголовок
    private String description; // описание
    private int picture;
    private static Date date;
    public Notes(String title, String description, int picture, Date date){
        this.title = title;
        this.description=description;
        this.picture=picture;
        this.date = date;
    }
    protected Notes(Parcel in) {
        title = in.readString();
        description = in.readString();
        picture = in.readInt();
        date = new Date(in.readLong());
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(picture);
        dest.writeLong(date.getTime());
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPicture() {
        return picture;
    }
    public static Date getDate(){
        return date;
    }
}
