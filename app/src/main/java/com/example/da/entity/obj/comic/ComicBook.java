package com.example.da.entity.obj.comic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ComicBook implements Serializable {

    @SerializedName("idComic")
    @Expose
    private String idComic;
    @SerializedName("tacgia")
    @Expose
    private String tacgia;
    @SerializedName("nameComic")
    @Expose
    private String nameComic;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("imgComic")
    @Expose
    private String imgComic;
    @SerializedName("plot")
    @Expose
    private String plot;
    @SerializedName("likeComic")
    @Expose
    private String likeComic;
    @SerializedName("viewComic")
    @Expose
    private String viewComic;

    public String getIdComic() {
        return idComic;
    }

    public void setIdComic(String idComic) {
        this.idComic = idComic;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getNameComic() {
        return nameComic;
    }

    public void setNameComic(String nameComic) {
        this.nameComic = nameComic;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImgComic() {
        return imgComic;
    }

    public void setImgComic(String imgComic) {
        this.imgComic = imgComic;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLikeComic() {
        return likeComic;
    }

    public void setLikeComic(String likeComic) {
        this.likeComic = likeComic;
    }

    public String getViewComic() {
        return viewComic;
    }

    public void setViewComic(String viewComic) {
        this.viewComic = viewComic;
    }

}