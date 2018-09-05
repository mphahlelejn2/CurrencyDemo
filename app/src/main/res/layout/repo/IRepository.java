package com.jeffreymphahlele.jeffdemo.repo;

import com.jeffreymphahlele.jeffdemo.models.ServerRespond;

import io.reactivex.Maybe;

public interface IRepository {
    Maybe<ServerRespond> getPlaceList(String api_key, String lat, String lon
    );
}
