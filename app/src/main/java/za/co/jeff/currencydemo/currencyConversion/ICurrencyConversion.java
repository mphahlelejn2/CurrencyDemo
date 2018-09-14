package za.co.jeff.currencydemo.currencyConversion;

import java.util.Map;

public interface ICurrencyConversion {

     interface View {
        void sendBackCurrencyListValues(Map<String, Double> recentCurrencyUpdates);
         void errorGettingOnlineCurrencyValues();
         void emptyOnlineCurrencyValues();
         double getChoiceCurrencyValue(String choice);
     }

     interface Presenter {
        boolean getOnlineCurrencyValues();
         String getCurrencyConversionValue(double inputValue, double choiceCurrencyValue);
     }
}
