package za.co.jeff.currencydemo.addCurrency;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Response;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;

public interface IAddCurrency {

     interface View {
        void loadListOfCurrencyFromOnline(JSONObject ratesList) throws JSONException;
        void currencyAdded(Currency currencyCode);
        void addedCurrencyRecord();
        void returnCurrencyValue(Double aDouble);
        void errorLoadingOnlineCurrencyList();
        void emptyOnlineCurrencyList();
        void errorAddingCurrency();
        void errorGettingCurrencyValue();
        void emptyCurrencyValue();
        void errorAddingCurrencyRecord();
        void loadCurrencyRespondsFromOnline(Response<ResponseBody> respondsSuccess);
     }

      interface Presenter {
        void getListOfCurrencyAndDescriptionsFromOnline();
        void addCurrency(Currency currency);
        boolean getOnlineCurrencyValueByCode(String code);
        void addCurrencyRecord(CurrencyRecord currencyRecord);
          void clear();
          void getJSONObject(Response<ResponseBody> responseBodyResponse);
    }
}
