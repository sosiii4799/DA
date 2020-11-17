package com.example.da.entity.obj.comic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhanHoi {

    @SerializedName("idFeedback")
    @Expose
    private String idFeedback;
    @SerializedName("Cmt")
    @Expose
    private String cmt;
    @SerializedName("DanhGia")
    @Expose
    private String danhGia;
    @SerializedName("PhanHoi")
    @Expose
    private String phanHoi;
    @SerializedName("checkDoc")
    @Expose
    private String checkDoc;

    public String getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(String idFeedback) {
        this.idFeedback = idFeedback;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public String getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(String danhGia) {
        this.danhGia = danhGia;
    }

    public String getPhanHoi() {
        return phanHoi;
    }

    public void setPhanHoi(String phanHoi) {
        this.phanHoi = phanHoi;
    }

    public String getCheckDoc() {
        return checkDoc;
    }

    public void setCheckDoc(String checkDoc) {
        this.checkDoc = checkDoc;
    }

}