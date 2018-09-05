package com.jeffreymphahlele.jeffdemo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable {

    @SerializedName("title")
    private String title;
    @SerializedName("server")
    private String server;
    @SerializedName("farm")
    private int farm;
    @SerializedName("secret")
    private String secret;
    @SerializedName("id")
    private String id;
    @SerializedName("owner")
    private String owner;


    public Photo(Parcel in) {
        title = in.readString();
        server = in.readString();
        farm = in.readInt();
        secret = in.readString();
        id = in.readString();
        owner = in.readString();
    }

    public static final Creator<com.jeffreymphahlele.jeffdemo.models.Photo> CREATOR = new Creator<com.jeffreymphahlele.jeffdemo.models.Photo>() {
        @Override
        public com.jeffreymphahlele.jeffdemo.models.Photo createFromParcel(Parcel in) {
            return new com.jeffreymphahlele.jeffdemo.models.Photo(in);
        }

        @Override
        public com.jeffreymphahlele.jeffdemo.models.Photo[] newArray(int size) {
            return new com.jeffreymphahlele.jeffdemo.models.Photo[size];
        }
    };

    public Photo() {

    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(server);
        dest.writeInt(farm);
        dest.writeString(secret);
        dest.writeString(id);
        dest.writeString(owner);
    }
}