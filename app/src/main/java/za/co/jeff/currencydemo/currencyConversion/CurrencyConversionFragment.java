package za.co.jeff.currencydemo.currencyConversion;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import za.co.jeff.currencydemo.R;
import za.co.jeff.currencydemo.base.BaseFragment;
import za.co.jeff.currencydemo.models.Currency;
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
    private Map<String, Double> recentCurrencyUpdates;

    @Inject
    public ICurrencyConversion.Presenter presenter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getCurrencyListValues();
        initOnclick();
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
                double value=recentCurrencyUpdates.get(toListSpinner.getSelectedItem().toString());
                double inputValue= Double.parseDouble(this.inputValue.getText().toString());
                double finalValueM=(inputValue/value);
                DecimalFormat df = new DecimalFormat("0.000");
                finalValue.setText("Value: "+df.format(finalValueM));
            }
        });
        cancel.setOnClickListener(view -> {
         getActivity().finish();
        });
    }


    @Override
    public void sendBackCurrencyListValues(Map<String, Double> recentCurrencyUpdates) {
        this.recentCurrencyUpdates=recentCurrencyUpdates;
        List<String> list= new ArrayList<>();
        list.addAll(recentCurrencyUpdates.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(list);
        toListSpinner.setAdapter(adapter);
    }
}
