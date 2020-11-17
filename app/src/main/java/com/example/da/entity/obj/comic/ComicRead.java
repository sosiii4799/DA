package com.example.da.entity.obj.comic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComicRead {

@SerializedName("idReadComic")
@Expose
private String idReadComic;
@SerializedName("linkAnhChapter")
@Expose
private String linkAnhChapter;

public String getIdReadComic() {
return idReadComic;
}

public void setIdReadComic(String idReadComic) {
this.idReadComic = idReadComic;
}

public String getLinkAnhChapter() {
return linkAnhChapter;
}

public void setLinkAnhChapter(String linkAnhChapter) {
this.linkAnhChapter = linkAnhChapter;
}

}