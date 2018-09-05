package za.co.jeff.currencydemo.currency;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.ServerRespond;
import za.co.jeff.currencydemo.repo.IOnlineRepository;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.repo.UrlManager;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;


public class CurrencyViewModel extends ViewModel implements ICurrency.ICurrencyViewModel {

    private IRoomRepository roomRepository;
    private BaseSchedulerProvider provider;
    private ICurrency.View view;
    CompositeDisposable compositeDisposable=new CompositeDisposable();


    public CurrencyViewModel(ICurrency.View view, IRoomRepository roomRepository, BaseSchedulerProvider provider) {
        this.roomRepository = roomRepository;
        this.provider = provider;
        this.view = view;
    }


    @Override
    public LiveData<List<Currency>> getListOfCurrency() {
        return roomRepository.getListOfCurrency();
    }

    @Override
    public void deleteCurrency(Currency currency) {
        Disposable disposable= roomRepository.deleteCurrency(currency)
                .subscribeOn(provider.io()).observeOn(provider.ui())
                .subscribeWith(new DisposableCompletableObserver(){
                    @Override
                    public void onComplete() {

                        view.doneDeletingCurrency();
                    }

                    @Override
                    public void onError(Throwable e) {
                    Log.d("","");
                    }
                });
        compositeDisposable.add(disposable);
    }



}
