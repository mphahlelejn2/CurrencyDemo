package za.co.jeff.currencydemo.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import za.co.jeff.currencydemo.db.Application_Database;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.repo.IRoomRepositoryImpl;
import za.co.jeff.currencydemo.repo.APIClient;
import za.co.jeff.currencydemo.repo.IOnlineRepository;
import za.co.jeff.currencydemo.repo.OnlineRepositoryImpl;
import za.co.jeff.currencydemo.repo.UrlManager;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;
import za.co.jeff.currencydemo.rxjava.SchedulerProvider;


@Module
public class AppModule {

    @Provides
    @Singleton
    Gson getGson(){
        return new GsonBuilder().setLenient().create();
    }

    @Provides
    @Singleton
    APIClient getRetrofit(Gson gson)
    {
        return new Retrofit.Builder()
                .baseUrl(UrlManager.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(APIClient.class);
      /* return new Retrofit.Builder()
                .baseUrl(UrlManager.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(APIClient.class);*/

    }

    @Provides
    @Singleton
    IOnlineRepository getRepository(APIClient APIClient){
        return new OnlineRepositoryImpl(APIClient);
    }


    @Provides
    @Singleton
    BaseSchedulerProvider getBaseSchedulerProvider(){
        return SchedulerProvider.getInstance();
    }

    @Provides
    @Singleton
    Application_Database getDatabase(Application app)
    {
        return  Room.databaseBuilder(app,Application_Database.class,"application_Database").build();
    }

    @Provides
    @Singleton
    IRoomRepository getRoomRepository(Application_Database db){
        return new IRoomRepositoryImpl(db);
    }


}
