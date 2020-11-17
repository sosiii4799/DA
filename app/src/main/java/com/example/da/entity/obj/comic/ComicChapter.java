package com.example.da.entity.obj.comic;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ComicChapter implements Parcelable, Serializable {

    @SerializedName("idChapter")
    @Expose
    private String idChapter;
    @SerializedName("chapterComic")
    @Expose
    private String chapterComic;
    @SerializedName("dayCreated")
    @Expose
    private String dayCreated;

    protected ComicChapter(Parcel in) {
        idChapter = in.readString();
        chapterComic = in.readString();
        dayCreated = in.readString();
    }

    public static final Creator<ComicChapter> CREATOR = new Creator<ComicChapter>() {
        @Override
        public ComicChapter createFromParcel(Parcel in) {
            return new ComicChapter(in);
        }

        @Override
        public ComicChapter[] newArray(int size) {
            return new ComicChapter[size];
        }
    };

    public String getIdChapter() {
        return idChapter;
    }

    public void setIdChapter(String idChapter) {
        this.idChapter = idChapter;
    }

    public String getChapterComic() {
        return chapterComic;
    }

    public void setChapterComic(String chapterComic) {
        this.chapterComic = chapterComic;
    }

    public String getDayCreated() {
        return dayCreated;
    }

    public void setDayCreated(String dayCreated) {
        this.dayCreated = dayCreated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idChapter);
        dest.writeString(chapterComic);
        dest.writeString(dayCreated);
    }
}