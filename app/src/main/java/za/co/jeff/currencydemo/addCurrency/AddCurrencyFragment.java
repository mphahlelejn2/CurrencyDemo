package za.co.jeff.currencydemo.addCurrency;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;
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
    private JSONObject jsonObject;
    private ArrayAdapter<String> adapter;

    @Inject
    public IAddCurrency.Presenter presenter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getListOfCurrencyFromOnline();
        initOnclick();
        adaptorInit();
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
    public void sendBackResults(JSONObject ratesList) throws JSONException {
        jsonObject=ratesList;
        adapter.addAll(UtilTool.getListFromJsonArray(ratesList));
        currencyList.setAdapter(adapter);
    }

    private void initOnclick() {
        save.setOnClickListener(view -> {
              if(!UtilTool.isEmpty(warningValue))
            {
                try {
                    String selectedValue=currencyList.getSelectedItem().toString();
                    String description= presenter.getValueByKey(jsonObject,currencyList.getSelectedItem().toString());
                    Currency currency=  UtilTool.generateCurrency(selectedValue,description, Double.parseDouble(warningValue.getText().toString()));
                    presenter.addCurrency(currency);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        cancel.setOnClickListener(view -> {
         getActivity().finish();
        });
    }


    @Override
    public void doneAddingCurrency(Currency currency) {
        this.currency=currency;
        presenter.getCurrencyRecentValueByCode(currency.getCurrencyCode());
    }



    @Override
    public void doneAddingCurrencyRecord() {
        getActivity().finish();
    }

    @Override
    public void sendBackRecentValue(Double value) {
        CurrencyRecord currencyRecord= UtilTool.generateCurrencyRecord(currency,value);
        presenter.addCurrencyRecord(currencyRecord);
    }

    @Override
    public void pleaseCheckInternet() {
        Toast.makeText(getActivity(),"Please check your internet",Toast.LENGTH_SHORT).show();
    }
}
