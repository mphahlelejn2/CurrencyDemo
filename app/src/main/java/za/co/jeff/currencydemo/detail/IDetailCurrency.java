package za.co.jeff.currencydemo.detail;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.Map;

import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;

public interface IDetailCurrency {
    public interface View {

    }

    public interface IDetailViewModel {
        LiveData<List<CurrencyRecord>> getListOfCurrencyRecordByCode(String code);
    }
}
