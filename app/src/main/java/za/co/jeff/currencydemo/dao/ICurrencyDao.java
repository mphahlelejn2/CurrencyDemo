package za.co.jeff.currencydemo.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

import io.reactivex.Maybe;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ICurrencyDao {

    @Delete
    void deleteCurrency(Currency currency);

    @Query("Select * from "+Currency.CURRENCY)
    LiveData<List<Currency>> getAllCurrency();

    @Insert(onConflict=REPLACE)
    void addCurrency(Currency currency);

    @Query("Select * from " + Currency.CURRENCY+ " WHERE " + Currency.CURRENCY_CODE+ " = :key")
    Maybe<Currency> getCurrencyBykey(String key);
}
