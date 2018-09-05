package com.jeffreymphahlele.jeffdemo.rxjava;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;


public class SchedulerProvider implements BaseSchedulerProvider {

    @Nullable
    private static com.jeffreymphahlele.jeffdemo.rxjava.SchedulerProvider INSTANCE;

    private  SchedulerProvider() {
    }

    public static synchronized com.jeffreymphahlele.jeffdemo.rxjava.SchedulerProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new com.jeffreymphahlele.jeffdemo.rxjava.SchedulerProvider();
        }
        return INSTANCE;
    }

    @Override
    @NonNull
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    @NonNull
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    @NonNull
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}