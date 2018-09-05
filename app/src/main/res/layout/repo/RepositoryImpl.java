package com.jeffreymphahlele.jeffdemo.repo;

import com.jeffreymphahlele.jeffdemo.models.ServerRespond;

import io.reactivex.Maybe;

public class RepositoryImpl implements IRepository {

    private APIClient apiClient;
    public RepositoryImpl(APIClient apiClient) {
        this.apiClient=apiClient;
    }

    @Override
    public Maybe<ServerRespond> getPlaceList(String api_key, String lat, String lon)
     {
        return apiClient.getPlaceList( api_key,lat,lon);
    }
}
