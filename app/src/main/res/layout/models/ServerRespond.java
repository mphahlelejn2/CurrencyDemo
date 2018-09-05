package com.jeffreymphahlele.jeffdemo.models;
import com.google.gson.annotations.SerializedName;

public class ServerRespond {

    @SerializedName("photos")
    private com.jeffreymphahlele.jeffdemo.models.Contents contents;

    public com.jeffreymphahlele.jeffdemo.models.Contents getContents() {
        return contents;
    }

    public void setContents(com.jeffreymphahlele.jeffdemo.models.Contents contents) {
        this.contents = contents;
    }
}
