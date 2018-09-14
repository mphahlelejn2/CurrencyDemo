package za.co.jeff.currencydemo.currencyConversion;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import za.co.jeff.currencydemo.R;
import za.co.jeff.currencydemo.base.BaseFragment;
import za.co.jeff.currencydemo.util.UtilTool;


public class CurrencyConversionFragment extends BaseFragment implements ICurrencyConversion.View{

    @BindView(R.id.STo)
    Spinner toListSpinner;
    @BindView(R.id.etSurname)
    EditText inputValue;
    @BindView(R.id.finalValue)
    TextView finalValue;
    @BindView(R.id.bCancel)
    public Button cancel;
    @BindView(R.id.bSave)
    public Button save;
    private Map<String, Double> currencyInfo;
    @Inject
    public ICurrencyConversion.Presenter presenter;
    private ArrayAdapter<String> adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getOnlineCurrencyValues();
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
        return R.layout.currency_conversion_fragments;
    }

    public static CurrencyConversionFragment getInstance() {
        return new CurrencyConversionFragment();
    }


    private void initOnclick() {
        save.setOnClickListener(view -> {
              if(!UtilTool.isEmpty(this.inputValue))
            {
                String selectedValue=toListSpinner.getSelectedItem().toString();
                double USDValue= currencyInfo.get(selectedValue);
                double inputValue= Double.parseDouble(this.inputValue.getText().toString());
                String results=presenter.getCurrencyConversionValue(inputValue,USDValue);
                finalValue.setText("Value: "+results);
            }
        });
        cancel.setOnClickListener(view -> {
         getActivity().finish();
        });
    }

    @Override
    public void sendBackCurrencyListValues(Map<String, Double> currencyInfo) {
        this.currencyInfo =currencyInfo;
        List<String> list= new ArrayList<>();
        list.addAll(currencyInfo.keySet());
        adapter.addAll(list);
        toListSpinner.setAdapter(adapter);
    }

    @Override
    public void errorGettingOnlineCurrencyValues() {
        makeSnackBar("Error getting online Values");
    }

    @Override
    public void emptyOnlineCurrencyValues() {
        makeSnackBar("Empty Values");
    }

    @Override
    public double getChoiceCurrencyValue(String choice) {
        return currencyInfo.get(choice);
    }

    private void makeSnackBar(String s) {
        Snackbar snackbar = Snackbar
                .make(container, s, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
