package za.co.jeff.currencydemo.currency;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;


public class CurrencyViewModel extends ViewModel implements ICurrency.ICurrencyViewModel {

    private IRoomRepository roomRepository;
    private BaseSchedulerProvider provider;
    private ICurrency.View view;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();

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
    public void deleteCurrencyFromDatabase(Currency currency) {
        Disposable disposable= roomRepository.deleteCurrencyFromDatabase(currency)
                .subscribeOn(provider.io()).observeOn(provider.ui())
                .subscribeWith(new DisposableCompletableObserver(){
                    @Override
                    public void onComplete() {

                        view.doneDeletingCurrency();
                    }

                    @Override
                    public void onError(Throwable e) {
                     view.errorDeletingCurrency();
                    }
                });
        compositeDisposable.add(disposable);
    }
}
