package za.co.jeff.currencydemo.addCurrency;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import za.co.jeff.currencydemo.repo.IOnlineRepository;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;


@Module
public abstract class AddCurrencyModule {

    @Binds
    public  abstract IAddCurrency.View getView(AddCurrencyFragment addCurrencyFragment );

    @Provides
    static IAddCurrency.Presenter getAddCurrencyPresenter(IAddCurrency.View view, IOnlineRepository repository, IRoomRepository roomRepository, BaseSchedulerProvider provider){
        return new AddCurrencyPresenterImpl(view,repository,roomRepository,provider);
    }
}
