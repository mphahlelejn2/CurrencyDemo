package za.co.jeff.currencydemo.repo;

import android.arch.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;

public interface IRoomRepository {
    Completable deleteCurrencyFromDatabase(Currency currency);
    LiveData<List<Currency>> getListOfCurrency();
    Completable addCurrencyToDatabase(Currency currency);
    Completable addCurrencyRecord(CurrencyRecord currencyRecord);
    LiveData<List<CurrencyRecord>> getListOfCurrencyRecordByCode(String code);
    Maybe<Currency> getCurrencyBykey(String key);
}
