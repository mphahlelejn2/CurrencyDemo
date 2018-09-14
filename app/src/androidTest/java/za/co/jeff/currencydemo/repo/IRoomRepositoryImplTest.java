package za.co.jeff.currencydemo.repo;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import za.co.jeff.currencydemo.db.Application_Database;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class IRoomRepositoryImplTest {

    private Application_Database db;
    private double value=2;
    private String code ="S";
    private String code2 ="R";


    @Before
    public void setUp(){
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, Application_Database.class).build();
    }

    @Test
    public void deleteCurrencyFromDatabase() throws InterruptedException {
        List<Currency> currencyList = LiveDataTestUtil.getValue(db.currencyDao().getAllCurrency());
        assertTrue(currencyList.isEmpty());
        db.currencyDao().addCurrency(getCurrency(code,value));
        currencyList = LiveDataTestUtil.getValue(db.currencyDao().getAllCurrency());
        assertEquals(currencyList.get(0).getCurrencyCode(),code);
        db.currencyDao().deleteCurrency(currencyList.get(0));
        currencyList = LiveDataTestUtil.getValue(db.currencyDao().getAllCurrency());
        assertTrue( currencyList.isEmpty());
    }

    @Test
    public void addCurrencyToDatabase() throws InterruptedException {
        List<Currency> currencyList = LiveDataTestUtil.getValue(db.currencyDao().getAllCurrency());
        assertTrue(currencyList.isEmpty());
        db.currencyDao().addCurrency(getCurrency(code,2.0));
        currencyList = LiveDataTestUtil.getValue(db.currencyDao().getAllCurrency());
        assertEquals(currencyList.get(0).getCurrencyCode(),code);
    }

    private Currency getCurrency(String code,double value) {
        Currency currency=new Currency();
        currency.setWarningValue(value);
        currency.setCurrencyCode(code);
        return currency;
    }

    @Test
    public void addCurrencyRecordToDatabase() throws InterruptedException {
        List<CurrencyRecord> currencyRecordList = LiveDataTestUtil.getValue(db.currencyRecordDao().getAllCurrencyRecordById(code));
        assertTrue(currencyRecordList.isEmpty());
        db.currencyDao().addCurrency(getCurrency(code,value));
        db.currencyRecordDao().addCurrencyRecord(getCurrencyRecord(code,value));
        currencyRecordList = LiveDataTestUtil.getValue(db.currencyRecordDao().getAllCurrencyRecordById(code));
        assertEquals(currencyRecordList.get(0).getValue(),value);
    }

    private CurrencyRecord getCurrencyRecord(String code,double value) {
        CurrencyRecord currencyRecord=new CurrencyRecord();
        currencyRecord.setValue(value);
        currencyRecord.setName(code);
        return currencyRecord;
    }

    @Test
    public void getListOfCurrencyRecordByCode() throws InterruptedException {
        db.currencyDao().addCurrency(getCurrency(code,value));
        db.currencyRecordDao().addCurrencyRecord(getCurrencyRecord(code,value));
        db.currencyRecordDao().addCurrencyRecord(getCurrencyRecord(code,value));
        List<CurrencyRecord> currencyRecordList = LiveDataTestUtil.getValue(db.currencyRecordDao().getAllCurrencyRecordById(code));
        Assert.assertEquals(currencyRecordList.size(),2);
    }

    @Test
    public void getListOfCurrency() throws InterruptedException {
        db.currencyDao().addCurrency(getCurrency(code,value));
        db.currencyDao().addCurrency(getCurrency(code2,value));
        List<Currency> currencyRecordList = LiveDataTestUtil.getValue(db.currencyDao().getAllCurrency());
        Assert.assertEquals(currencyRecordList.size(),2);
    }

    @Test
    public void getCurrencyByKey() {
        db.currencyDao().addCurrency(getCurrency(code,value));
        db.currencyDao().getCurrencyByKey(code)
                .test()
                .assertValue(currency -> currency.getCurrencyCode().equals(code));
    }
}