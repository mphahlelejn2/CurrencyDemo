package za.co.jeff.currencydemo.currencyConversion;


import java.text.DecimalFormat;
import java.util.Map;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;
import za.co.jeff.currencydemo.models.ServerRespond;
import za.co.jeff.currencydemo.repo.IOnlineRepository;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;
import static za.co.jeff.currencydemo.repo.UrlManager.API_KEY;

public class CurrencyConversionPresenterImpl implements ICurrencyConversion.Presenter {

    private IOnlineRepository repository;
    private BaseSchedulerProvider provider;
    private ICurrencyConversion.View view;
    private IRoomRepository roomRepository;
    private Map<String,Double> recentCurrencyUpdates;

    CompositeDisposable compositeDisposable=new CompositeDisposable();


    public CurrencyConversionPresenterImpl(ICurrencyConversion.View view, IOnlineRepository repository, IRoomRepository roomRepository, BaseSchedulerProvider provider) {
        this.repository = repository;
        this.provider = provider;
        this.view = view;
        this.roomRepository=roomRepository;
    }


    @Override
    public boolean getCurrencyListValues() {
        compositeDisposable.add(repository.getAllOnlineCurrencyValues(API_KEY)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribeWith(new DisposableMaybeObserver<ServerRespond>() {

                    @Override
                    public void onSuccess(ServerRespond serverRespond) {
                        recentCurrencyUpdates=serverRespond.getCurrencyListValues();
                       view.sendBackCurrencyListValues(recentCurrencyUpdates);
                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete(){
                    }
                })

        );

        return false;
    }

    @Override
    public String getCurrencyConversionValue(double inputValue, double value) {
        double finalValueM=(inputValue/value);
        DecimalFormat df = new DecimalFormat("0.000");
        return df.format(finalValueM);
    }

}
