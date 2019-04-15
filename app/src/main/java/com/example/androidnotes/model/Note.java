package com.example.androidnotes.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note  implements Parcelable {

    private int id;
    private String header;
    private String message;
    private String createDate;
    private String updateDate;
    private boolean isJob;
    private boolean isPurchases;
    private boolean isHome;
    private boolean isFavorites;

    public Note() {}

    public Note(String header, String message, String createDate, String updateDate, boolean isJob, boolean isPurchases, boolean isHome, boolean isFavorites) {
        this.id = (int) new Date().getTime();
        this.header = header;
        this.message = message;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.isJob = isJob;
        this.isPurchases = isPurchases;
        this.isHome = isHome;
        this.isFavorites = isFavorites;
    }

    protected Note(Parcel in) {
        id = in.readInt();
        header = in.readString();
        message = in.readString();
        createDate = in.readString();
        updateDate = in.readString();
        isJob = Boolean.valueOf(in.readString());
        isPurchases = Boolean.valueOf(in.readString());
        isHome = Boolean.valueOf(in.readString());
        isFavorites = Boolean.valueOf(in.readString());
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(header);
        dest.writeString(message);
        dest.writeString(createDate);
        dest.writeString(updateDate);
        dest.writeString(String.valueOf((isJob)));
        dest.writeString(String.valueOf((isPurchases)));
        dest.writeString(String.valueOf((isHome)));
        dest.writeString(String.valueOf((isFavorites)));
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", message='" + message + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", isJob=" + isJob +
                ", isPurchases=" + isPurchases +
                ", isHome=" + isHome +
                ", isFavorites=" + isFavorites +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getMessage() {
        return message;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public boolean isJob() {
        return isJob;
    }

    public boolean isPurchases() {
        return isPurchases;
    }

    public boolean isHome() {
        return isHome;
    }

    public boolean isFavorites() {
        return isFavorites;
    }
}
