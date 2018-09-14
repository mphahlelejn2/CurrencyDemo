package za.co.jeff.currencydemo.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import dmax.dialog.SpotsDialog;
import za.co.jeff.currencydemo.R;


abstract public class BaseFragment extends Fragment{

    protected ViewGroup container;
    public SpotsDialog loadPhotosProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container=container;
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         ButterKnife.bind(this, view);
        showDefaultTitle();
    }


    public void showDefaultTitle() {
        getActivity().setTitle(R.string.app_name);
    }
    protected abstract int getFragmentLayout();
}
