package za.co.jeff.currencydemo.detail;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import za.co.jeff.currencydemo.currency.CurrencyViewModelFactory;
import za.co.jeff.currencydemo.currency.ICurrency;
import za.co.jeff.currencydemo.repo.IOnlineRepository;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;


@Module
public abstract class DetailModule {

    @Binds
    public  abstract IDetailCurrency.View getView(DetailFragment detailFragment );

    @Provides
    static ViewModelProvider.Factory provideDetailViewModelFactory( IRoomRepository repository) {
        return new DetailViewModelFactory(repository);
    }

}
