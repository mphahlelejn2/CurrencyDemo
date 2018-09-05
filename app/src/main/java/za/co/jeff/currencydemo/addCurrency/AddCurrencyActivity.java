package za.co.jeff.currencydemo.addCurrency;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import za.co.jeff.currencydemo.R;
import za.co.jeff.currencydemo.util.ActivityUtils;


public class AddCurrencyActivity extends AppCompatActivity {

    private AddCurrencyFragment addCurrencyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_currency_activity);

        FragmentManager fragmentManager=this.getSupportFragmentManager();
        if(savedInstanceState==null){
            addCurrencyFragment = AddCurrencyFragment.getInstance();
            ActivityUtils.addFragment(fragmentManager, addCurrencyFragment,R.id.fragment);
        }
    }


}
