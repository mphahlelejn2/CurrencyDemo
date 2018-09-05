package com.jeffreymphahlele.jeffdemo.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jeffreymphahlele.jeffdemo.repo.IRepository;
import com.jeffreymphahlele.jeffdemo.repo.UrlManager;
import com.jeffreymphahlele.jeffdemo.rxjava.BaseSchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class AppModule {

    @Provides
    @Singleton
    Gson getGson(){
        return new GsonBuilder().setLenient().create();
    }

    @Provides
    @Singleton
    com.jeffreymphahlele.jeffdemo.repo.APIClient getRetrofit(Gson gson)
    {
        return new Retrofit.Builder()
                .baseUrl(UrlManager.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(com.jeffreymphahlele.jeffdemo.repo.APIClient.class);
    }

    @Provides
    @Singleton
    IRepository getRepository(com.jeffreymphahlele.jeffdemo.repo.APIClient APIClient){
        return new com.jeffreymphahlele.jeffdemo.repo.RepositoryImpl(APIClient);
    }


    @Provides
    @Singleton
    BaseSchedulerProvider getBaseSchedulerProvider(){
        return com.jeffreymphahlele.jeffdemo.rxjava.SchedulerProvider.getInstance();
    }

}
