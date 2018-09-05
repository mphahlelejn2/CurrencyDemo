package com.jeffreymphahlele.jeffdemo.repo;
import com.jeffreymphahlele.jeffdemo.models.ServerRespond;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIClient {

    @GET(UrlManager.API_END_POINT)
    Maybe<ServerRespond> getPlaceList(@Query("api_key") String api_key, @Query("lat") String lat, @Query("lon") String lan);

}
