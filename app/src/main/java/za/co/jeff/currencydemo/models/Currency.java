package za.co.jeff.currencydemo.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

import static za.co.jeff.currencydemo.models.Currency.CURRENCY;
import static za.co.jeff.currencydemo.models.Currency.CURRENCY_CODE;
import static za.co.jeff.currencydemo.models.Currency.CURRENCY_DI;

@Entity(tableName = CURRENCY, indices = {@Index(value = {CURRENCY_CODE},
        unique = true)})
public class Currency {

    public static final String CURRENCY = "currency";
    public static final String CURRENCY_DI="currency_id";
    public static final String CURRENCY_CODE ="currencyCode";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name=CURRENCY_DI)
    private int id;
    @ColumnInfo(name= CURRENCY_CODE)
    private String currencyCode;

    private String currencyDescription;
    @Ignore
    private ArrayList<Currency> currencyArrayList;

    @Ignore
    private double warningValue;

    public double getWarningValue() {
        return warningValue;
    }

    public void setWarningValue(double warningValue) {
        this.warningValue = warningValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyDescription() {
        return currencyDescription;
    }

    public void setCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
    }

}
