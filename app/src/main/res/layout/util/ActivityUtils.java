package com.jeffreymphahlele.jeffdemo.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class ActivityUtils extends AppCompatActivity {

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int fragmentID) {
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentID,fragment);
        fragmentTransaction.commit();
    }
}
