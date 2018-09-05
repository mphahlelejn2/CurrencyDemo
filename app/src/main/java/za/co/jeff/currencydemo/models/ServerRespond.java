package za.co.jeff.currencydemo.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

import okhttp3.ResponseBody;

public class ServerRespond {

    @SerializedName("rates")
    private Map<String, Double> currencyListValues;

    @SerializedName("body")
    private Map<String,String> currencyListAndDescription;

    @SerializedName("base")
    private String USD;

    @SerializedName("timestamp")
    private String timestamp;

    public Map<String, Double> getCurrencyListValues() {
        return currencyListValues;
    }

    public void setCurrencyListValues(Map<String, Double> currencyListValues) {
        this.currencyListValues = currencyListValues;
    }

    public String getUSD() {
        return USD;
    }

    public void setUSD(String USD) {
        this.USD = USD;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getCurrencyListAndDescription() {
        return currencyListAndDescription;
    }

    public void setCurrencyListAndDescription(Map<String, String> currencyListAndDescription) {
        this.currencyListAndDescription = currencyListAndDescription;
    }
}
