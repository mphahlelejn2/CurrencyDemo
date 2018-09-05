package za.co.jeff.currencydemo.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import za.co.jeff.currencydemo.currency.ICurrency;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;


public class DetailViewModel extends ViewModel implements IDetailCurrency.IDetailViewModel {

    private IRoomRepository roomRepository;

    public DetailViewModel( IRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @Override
    public LiveData<List<CurrencyRecord>> getListOfCurrencyRecordByCode(String code) {
        return roomRepository.getListOfCurrencyRecordByCode(code);
    }


}
