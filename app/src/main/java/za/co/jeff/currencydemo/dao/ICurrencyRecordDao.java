package za.co.jeff.currencydemo.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;


import za.co.jeff.currencydemo.models.CurrencyRecord;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ICurrencyRecordDao {

    @Insert(onConflict = REPLACE)
    long addCurrencyRecord(CurrencyRecord currencyRecord);

    @Query("Select * from " + CurrencyRecord.CURRENCYRECORD+ " WHERE " + CurrencyRecord.CURRENCYRECORD_CODE+ " = :code")
    LiveData<List<CurrencyRecord>> getAllCurrencyRecordById(String code);

}
