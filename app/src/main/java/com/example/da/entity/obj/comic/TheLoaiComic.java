package com.example.da.entity.obj.comic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TheLoaiComic {

@SerializedName("genre")
@Expose
private String genre;

public String getGenre() {
return genre;
}

public void setGenre(String genre) {
this.genre = genre;
}

}