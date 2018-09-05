package za.co.jeff.currencydemo.currencyConversion;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import za.co.jeff.currencydemo.R;
import za.co.jeff.currencydemo.util.ActivityUtils;


public class CurrencyConversionActivity extends AppCompatActivity {

    private CurrencyConversionFragment currencyConversionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_conversion_activity);

        FragmentManager fragmentManager=this.getSupportFragmentManager();
        if(savedInstanceState==null){
            currencyConversionFragment = CurrencyConversionFragment.getInstance();
            ActivityUtils.addFragment(fragmentManager, currencyConversionFragment,R.id.fragment);
        }
    }


}
