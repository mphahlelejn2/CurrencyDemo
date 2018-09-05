package com.jeffreymphahlele.jeffdemo.photosFolder;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeffreymphahlele.jeffdemo.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import dmax.dialog.SpotsDialog;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;

public class FolderFragment extends Fragment implements com.jeffreymphahlele.jeffdemo.photosFolder.IFolder.View {

    public SpotsDialog loadPhotosProgressDialog;
    @Inject
    public com.jeffreymphahlele.jeffdemo.photosFolder.IFolder.Presenter presenter;
    private com.jeffreymphahlele.jeffdemo.photosFolder.FolderListAdapter FolderListAdapter;
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    private ViewGroup container;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    public ViewGroup getContainer() {
        return container;
    }

    public static com.jeffreymphahlele.jeffdemo.photosFolder.FolderFragment getInstance() {
        return new com.jeffreymphahlele.jeffdemo.photosFolder.FolderFragment();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        presenter.verifyLocationFunctionalityEnabled();
    }

    @Override
    public void dismissLoadDialog() {
        loadPhotosProgressDialog.dismiss();
    }

    @Override
    public void initLoadProgressDialog() {
        loadPhotosProgressDialog =new SpotsDialog(getActivity(), "Loading Photos....");
        loadPhotosProgressDialog.show();
    }

    @Override
    public void sendListOfPlaces(List<com.jeffreymphahlele.jeffdemo.models.Photo> groupList) {
        FolderListAdapter =new com.jeffreymphahlele.jeffdemo.photosFolder.FolderListAdapter(getContext(), presenter.getSortedFoldersByOwner(groupList));
        recyclerView.setAdapter(FolderListAdapter);
    }

    @Override
    public void emptyList() {
        Snackbar snackbar = Snackbar
                .make(container, "Photos List is Empty", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void errorLoadingImages() {
        Snackbar snackbar = Snackbar
                .make(container, "Error loading Photos . Please Check your Network", Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container=container;
        return inflater.inflate(R.layout.folder_fragment, container, false);
    }

    @Override
   public void requestPermission() {
       requestPermissions(new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION,INTERNET}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void returnLocation(String lat, String lon) {
        presenter.getListOfPhotosCloseToUserLocation(lat,lon);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean locationAccepted2 = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean internetAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted && locationAccepted2&&internetAccepted ){
                        Snackbar.make(container, "Permission Granted", Snackbar.LENGTH_LONG).show();
                    presenter.getCurrentUserLocation();}
                   else {
                        Snackbar.make(container, "Permission Denied, You cannot access this App .", Snackbar.LENGTH_LONG).show();
                    }
                }


                break;
        }
    }


}
