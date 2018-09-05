package com.jeffreymphahlele.jeffdemo.photosFolder;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.jeffreymphahlele.jeffdemo.models.Photo;
import com.jeffreymphahlele.jeffdemo.models.ServerRespond;
import com.jeffreymphahlele.jeffdemo.repo.IRepository;
import com.jeffreymphahlele.jeffdemo.repo.UrlManager;
import com.jeffreymphahlele.jeffdemo.rxjava.BaseSchedulerProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class FolderPresenterImpl implements com.jeffreymphahlele.jeffdemo.photosFolder.IFolder.Presenter {

    private FusedLocationProviderClient mFusedLocationClient;
    private com.jeffreymphahlele.jeffdemo.photosFolder.IFolder.View view;
    private IRepository repository;
    private BaseSchedulerProvider scheduler;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FolderPresenterImpl(com.jeffreymphahlele.jeffdemo.photosFolder.IFolder.View view, IRepository repository, BaseSchedulerProvider scheduler) {
        this.view = view;
        this.repository = repository;
        this.scheduler = scheduler;
    }

    @Override
    public void verifyLocationFunctionalityEnabled() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());
        checkLocationServiceEnabled();
        if (ActivityCompat.checkSelfPermission(view.getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            view.requestPermission();
        }else{
            getCurrentUserLocation();
        }
    }

    @Override
    public void getCurrentUserLocation() {
        if (ActivityCompat.checkSelfPermission(view.getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        view.returnLocation(Double.toString(location.getLatitude()), Double.toString(location.getLongitude()));
                    }
                })
                .addOnFailureListener(e -> e.printStackTrace());
    }


    public void checkLocationServiceEnabled() {
        LocationManager locationManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Snackbar snackbar = Snackbar
                    .make(view.getContainer(), "Please Enable your location", Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }



    @Override
    public void getListOfPhotosCloseToUserLocation(String lat, String lon) {
        view.initLoadProgressDialog();
        compositeDisposable.add(repository.getPlaceList(UrlManager.api_key,lat,lon)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableMaybeObserver<ServerRespond>() {

                    @Override
                    public void onSuccess(ServerRespond serverRespond) {
                        view.sendListOfPlaces(serverRespond.getContents().getPhotoList());
                        view.dismissLoadDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.errorLoadingImages();
                        view.dismissLoadDialog();
                    }

                    @Override
                    public void onComplete() {
                        view.emptyList();
                        view.dismissLoadDialog();
                    }
                })

                );
    }

    @Override
    public Map<String, List<Photo>> getSortedFoldersByOwner(List<Photo> groupList) {
        Map<String, List<Photo>> cache = new HashMap<>();
        for (Photo yo : groupList) {
            List<Photo> list = cache.get(yo.getOwner());
            if (list == null) {
                list = new ArrayList<>();
                cache.put(yo.getOwner(), list);
            }
            list.add(yo);
        }
        return cache;
    }
}
