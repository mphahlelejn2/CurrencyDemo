package com.jeffreymphahlele.jeffdemo.util;

import android.content.Context;
import android.widget.ImageView;

import com.jeffreymphahlele.jeffdemo.R;
import com.jeffreymphahlele.jeffdemo.models.Photo;

import java.util.ArrayList;

public class Utils {

    public static ArrayList<String> getUrlPhotoListFromListOfPhotoObjects(ArrayList<Photo> photosList) {
        ArrayList<String> urlPhotosList=new ArrayList<>();
        for (Photo p: photosList) {
            urlPhotosList.add(getUrlFromPhotoClass(p));
        }
        return urlPhotosList;
    }

    public static String getUrlFromPhotoClass(Photo photo) {
        return "https://farm"+photo.getFarm()+".staticflickr.com/"+photo.getServer()+"/"+photo.getId()+"_"+photo.getSecret()+".jpg";
    }

    public static void loadImage(Context context, ImageView imageView, String url) {
        com.jeffreymphahlele.jeffdemo.util.PicassoTrustAll.getInstance(context).load(url)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher)
                .into( imageView);
    }
}
