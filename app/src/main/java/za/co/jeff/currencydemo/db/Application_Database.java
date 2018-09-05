package za.co.jeff.currencydemo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import za.co.jeff.currencydemo.dao.ICurrencyDao;
import za.co.jeff.currencydemo.dao.ICurrencyRecordDao;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;


@Database(entities = {Currency.class, CurrencyRecord.class}, version = 5)
public abstract class Application_Database extends RoomDatabase {
    public abstract ICurrencyDao currencyDao();
    public abstract ICurrencyRecordDao currencyRecordDao();
}
