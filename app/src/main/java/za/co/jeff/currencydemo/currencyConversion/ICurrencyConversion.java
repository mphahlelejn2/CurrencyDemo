package za.co.jeff.currencydemo.currencyConversion;

import java.util.Map;

public interface ICurrencyConversion {

     interface View {
        void sendBackCurrencyListValues(Map<String, Double> recentCurrencyUpdates);
    }

     interface Presenter {

        boolean getCurrencyListValues();
        String getCurrencyConversionValue(double inputValue, double value);
    }
}
