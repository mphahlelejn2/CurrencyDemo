package com.jeffreymphahlele.jeffdemo.photosFolder;

import android.content.Context;
import android.view.ViewGroup;

import com.jeffreymphahlele.jeffdemo.models.Photo;

import java.util.List;
import java.util.Map;

public interface IFolder {

    interface View {
        ViewGroup getContainer();
        void dismissLoadDialog();
        void initLoadProgressDialog();
        Context getContext();
        void sendListOfPlaces(List<Photo> groupList);
        void emptyList();
        void errorLoadingImages();
        void requestPermission();
        void returnLocation(String lat, String lon);
    }

    interface Presenter {
        void verifyLocationFunctionalityEnabled();

        void getCurrentUserLocation();

        void getListOfPhotosCloseToUserLocation(String lat, String lon);

        Map<String, List<Photo>> getSortedFoldersByOwner(List<Photo> groupList);
    }
}
