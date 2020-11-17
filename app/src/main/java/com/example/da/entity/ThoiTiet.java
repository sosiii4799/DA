package com.example.da.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThoiTiet {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("maxT")
    @Expose
    private String maxT;
    @SerializedName("minT")
    @Expose
    private String minT;
    @SerializedName("temp")
    @Expose
    private String temp;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMaxT() {
        return maxT;
    }

    public void setMaxT(String maxT) {
        this.maxT = maxT;
    }

    public String getMinT() {
        return minT;
    }

    public void setMinT(String minT) {
        this.minT = minT;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public ThoiTiet(String day, String maxT, String minT, String temp) {
        this.day = day;
        this.maxT = maxT;
        this.minT = minT;
        this.temp = temp;
    }
}