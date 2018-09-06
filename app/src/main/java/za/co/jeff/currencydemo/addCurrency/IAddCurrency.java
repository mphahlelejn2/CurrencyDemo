package za.co.jeff.currencydemo.addCurrency;

import org.json.JSONException;
import org.json.JSONObject;

import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;

public interface IAddCurrency {

    public interface View {
        void sendBackResults(JSONObject ratesList) throws JSONException;
        void doneAddingCurrency(Currency currencyCode);
        void doneAddingCurrencyRecord();
        void sendBackRecentValue(Double aDouble);
        void pleaseCheckInternet();
    }

    public interface Presenter {
        void getListOfCurrencyFromOnline();
        void addCurrency(Currency currency);
        String getValueByKey(JSONObject jsonObject, String s) throws JSONException;
        boolean getCurrencyRecentValueByCode(String code);
        void addCurrencyRecord(CurrencyRecord currencyRecord);
        void clear();
    }
}
