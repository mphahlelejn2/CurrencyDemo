package za.co.jeff.currencydemo.util;


import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;

import static org.junit.Assert.*;

public class UtilToolTest {


    @Test
    public void getCurrentTimesStamp() {
        assertNotNull(UtilTool.getCurrentTimesStamp());
    }

    @Test
    public void generateCurrencyRecord() {
        Currency currency=UtilTool.createCurrency("","dd",9);
        assertNotNull(currency);
    }

    @Test
    public void createCurrency() {
        Currency currency=UtilTool.createCurrency("","dd",9);
        CurrencyRecord currencyRecord=UtilTool.generateCurrencyRecord(currency,9);
        assertNotNull(currencyRecord);
    }


}