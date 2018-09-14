package za.co.jeff.currencydemo.addCurrency;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Response;
import za.co.jeff.currencydemo.R;
import za.co.jeff.currencydemo.base.BaseFragment;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;
import za.co.jeff.currencydemo.util.UtilTool;


public class AddCurrencyFragment extends BaseFragment implements IAddCurrency.View{

    @BindView(R.id.etName)
    Spinner currencyList;
    @BindView(R.id.etSurname)
    EditText warningValue;

    @BindView(R.id.bCancel)
    public Button cancel;
    @BindView(R.id.bSave)
    public Button save;
    private Currency currency;
    Map<String, String> listOfCurrency;
    private ArrayAdapter<String> adapter;

    @Inject
    public IAddCurrency.Presenter presenter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getListOfCurrencyAndDescriptionsFromOnline();
        initOnclick();
        adaptorInit();
        getActivity().setTitle("");
    }

    private void adaptorInit() {
         adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.add_currency_fragment;
    }

    public static AddCurrencyFragment getInstance() {
        return new AddCurrencyFragment();
    }

    @Override
    public void loadListOfCurrencyFromOnline(JSONObject ratesList) throws JSONException {
        listOfCurrency = UtilTool.convertToMap( ratesList);
        adapter.addAll(listOfCurrency.keySet());
        currencyList.setAdapter(adapter);
    }

    private void initOnclick() {
        save.setOnClickListener(view -> {
           saveCurrencyValues();
        });
        cancel.setOnClickListener(view -> {
         getActivity().finish();
        });
    }

    private void saveCurrencyValues() {

        if(!UtilTool.isEmpty(warningValue))
        {
            String selectedValue=currencyList.getSelectedItem().toString();
            String description= listOfCurrency.get(selectedValue);
            double warning=Double.parseDouble(warningValue.getText().toString());
            Currency currency=  UtilTool.createCurrency(selectedValue,description, warning);
            presenter.addCurrency(currency);
        }
    }


    @Override
    public void currencyAdded(Currency currency) {
        this.currency=currency;
        presenter.getOnlineCurrencyValueByCode(currency.getCurrencyCode());
    }

    @Override
    public void addedCurrencyRecord() {
        getActivity().finish();
    }

    @Override
    public void returnCurrencyValue(Double value) {
        CurrencyRecord currencyRecord= UtilTool.generateCurrencyRecord(currency,value);
        presenter.addCurrencyRecord(currencyRecord);
    }

    @Override
    public void errorLoadingOnlineCurrencyList() {
        makeSnackBar("Please check your internet");
    }

    @Override
    public void emptyOnlineCurrencyList() {
        makeSnackBar("Empty list");
    }

    @Override
    public void errorAddingCurrency() {
        makeSnackBar("Error Adding Currency");
    }

    @Override
    public void errorGettingCurrencyValue() {
        makeSnackBar("Error getting Currency Value");
    }

    @Override
    public void emptyCurrencyValue() {
        makeSnackBar("Empty value");
    }

    @Override
    public void errorAddingCurrencyRecord() {
        makeSnackBar("Error adding currency record");
    }

    @Override
    public void loadCurrencyRespondsFromOnline(Response<ResponseBody> respondsSuccess) {
        presenter.getJSONObject(respondsSuccess);
    }

    private void makeSnackBar(String s) {
        Snackbar snackbar = Snackbar
                .make(container, s, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
