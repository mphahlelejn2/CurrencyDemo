package za.co.jeff.currencydemo.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,AppModule.class, Builder.class})
public interface AppComponent extends AndroidInjector<App> {

   @Component.Builder
   interface Builder {
      @BindsInstance
      Builder application(Application application);
      AppComponent build();
   }

   @Override
   void inject(App app);

}