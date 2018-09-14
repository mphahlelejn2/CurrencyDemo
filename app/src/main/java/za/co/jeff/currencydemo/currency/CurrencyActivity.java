package za.co.jeff.currencydemo.currency;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import za.co.jeff.currencydemo.R;
import za.co.jeff.currencydemo.base.BaseActivity;
import za.co.jeff.currencydemo.currencyConversion.CurrencyConversionActivity;
import za.co.jeff.currencydemo.util.ActivityUtils;


public class CurrencyActivity extends BaseActivity {

    private CurrencyFragment currencyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager=this.getSupportFragmentManager();
        if(savedInstanceState==null){
            currencyFragment = CurrencyFragment.getInstance();
            ActivityUtils.addFragment(fragmentManager, currencyFragment,R.id.fragment);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_conversion) {
            openCurrencyConversionActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openCurrencyConversionActivity() {
        Intent intent= new Intent(this, CurrencyConversionActivity.class);
        startActivity(intent);

    }
}
