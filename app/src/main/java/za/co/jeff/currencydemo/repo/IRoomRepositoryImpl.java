package za.co.jeff.currencydemo.repo;
import android.arch.lifecycle.LiveData;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import za.co.jeff.currencydemo.db.Application_Database;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;


public class IRoomRepositoryImpl implements IRoomRepository {

    private Application_Database db;

    public IRoomRepositoryImpl(Application_Database db) {
        this.db = db;
    }

    @Override
    public Completable deleteCurrencyFromDatabase(Currency currency) {
        return Completable.create(emitter -> {
            db.currencyDao().deleteCurrency(currency);
            emitter.onComplete();
        });
    }

    @Override
    public LiveData<List<Currency>> getListOfCurrency() {
        return db.currencyDao().getAllCurrency();
    }

    @Override
    public Completable addCurrencyToDatabase(Currency currency) {
        return Completable.create(emitter -> {
            db.currencyDao().addCurrency(currency);
            emitter.onComplete();
        });
    }

    @Override
    public Completable addCurrencyRecord(CurrencyRecord currencyRecord) {
        return Completable.create(emitter -> {
            db.currencyRecordDao().addCurrencyRecord(currencyRecord);
            emitter.onComplete();
        });
    }

    @Override
    public LiveData<List<CurrencyRecord>> getListOfCurrencyRecordByCode(String code) {
        return db.currencyRecordDao().getAllCurrencyRecordById(code);
    }

    @Override
         public Maybe<Currency> getCurrencyBykey(String key){
            return db.currencyDao().getCurrencyByKey(key);
        }
    }



