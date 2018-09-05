package za.co.jeff.currencydemo.currency;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import za.co.jeff.currencydemo.addCurrency.AddCurrencyPresenterImpl;
import za.co.jeff.currencydemo.repo.IOnlineRepository;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;


@Module
public abstract class CurrencyModule {

    @Binds
    public  abstract ICurrency.View getView(CurrencyFragment currencyFragment);



    @Provides
    static ViewModelProvider.Factory provideFaceViewModelFactory(ICurrency.View view, IRoomRepository repository, BaseSchedulerProvider provider) {
        return new CurrencyViewModelFactory(view,repository,provider);
    }

}
