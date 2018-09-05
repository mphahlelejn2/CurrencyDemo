package com.jeffreymphahlele.jeffdemo.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Contents {

    @SerializedName("photo")
    private List<Photo> photoList;
    @SerializedName("pages")
    private int pages;

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }


}
