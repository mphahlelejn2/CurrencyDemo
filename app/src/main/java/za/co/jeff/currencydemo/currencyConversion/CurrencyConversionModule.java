package za.co.jeff.currencydemo.currencyConversion;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import za.co.jeff.currencydemo.repo.IOnlineRepository;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;


@Module
public abstract class CurrencyConversionModule {

    @Binds
    public  abstract ICurrencyConversion.View getView(CurrencyConversionFragment currencyConversionFragment);

    @Provides
    static ICurrencyConversion.Presenter getAddCurrencyPresenter(ICurrencyConversion.View view, IOnlineRepository repository, IRoomRepository roomRepository, BaseSchedulerProvider provider){
        return new CurrencyConversionPresenterImpl(view,repository,roomRepository,provider);
    }
}
