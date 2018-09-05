package za.co.jeff.currencydemo.util;

import android.text.TextUtils;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;


public class UtilTool {

    public static boolean isEmpty(EditText text) {
        CharSequence s=text.getText().toString();
        return TextUtils.isEmpty(s);
    }

    public static String getCurrentTimesStanp() {
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    public static CurrencyRecord generateCurrencyRecord(Currency currency,double value) {
        CurrencyRecord currencyRecord=new CurrencyRecord();
        currencyRecord.setTimeStamp(UtilTool.getCurrentTimesStanp());
        currencyRecord.setName(currency.getCurrencyCode());
        currencyRecord.setValue(value);
        return currencyRecord;

    }

    public static Currency generateCurrency(String selectedValue,String description,double value) {
        Currency currency=new Currency();
        currency.setCurrencyCode(selectedValue);
        currency.setCurrencyDescription(description);
        currency.setWarningValue(value);
        return currency;
    }

    public static List<String> getListFromJsonArray(JSONObject ratesList) throws JSONException {
        JSONArray jsonArray = ratesList.names ();;
        List<String> list=new ArrayList<>();
        for (int i = 0; i < ratesList.length (); ++i) {
            list.add(jsonArray.getString (i));
        }
        return list;
    }
}
