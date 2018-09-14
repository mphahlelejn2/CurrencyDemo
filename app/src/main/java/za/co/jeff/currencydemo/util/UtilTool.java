package za.co.jeff.currencydemo.util;

import android.text.TextUtils;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;

public class UtilTool {

    public static boolean isEmpty(EditText text) {
        CharSequence s=text.getText().toString();
        return TextUtils.isEmpty(s);
    }

    public static String getCurrentTimesStamp() {
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    public static CurrencyRecord generateCurrencyRecord(Currency currency,double value) {
        CurrencyRecord currencyRecord=new CurrencyRecord();
        currencyRecord.setTimeStamp(UtilTool.getCurrentTimesStamp());
        currencyRecord.setName(currency.getCurrencyCode());
        currencyRecord.setValue(value);
        return currencyRecord;

    }

    public static Currency createCurrency(String selectedValue, String description, double value) {
        Currency currency=new Currency();
        currency.setCurrencyCode(selectedValue);
        currency.setCurrencyDescription(description);
        currency.setWarningValue(value);
        return currency;
    }

    public static Map<String, String> convertToMap(JSONObject jsonObject) throws JSONException {
        Map<String, String> map = new HashMap<>();
        Iterator<String> keysItr = jsonObject.keys();
       // if(keysItr!=null)
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            String value = (String) jsonObject.get(key);
            map.put(key, value);
        }
        return new TreeMap<>(map);
    }

}
