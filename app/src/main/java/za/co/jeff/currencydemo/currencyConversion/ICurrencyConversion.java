package za.co.jeff.currencydemo.currencyConversion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;

public interface ICurrencyConversion {

    public interface View {

        void sendBackCurrencyListValues(Map<String, Double> recentCurrencyUpdates);
    }

    public interface Presenter {

        boolean getCurrencyListValues();
    }
}
