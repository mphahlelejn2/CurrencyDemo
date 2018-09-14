package za.co.jeff.currencydemo.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import za.co.jeff.currencydemo.R;
import za.co.jeff.currencydemo.util.ActivityUtils;


public class DetailActivity extends AppCompatActivity {

    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        FragmentManager fragmentManager=this.getSupportFragmentManager();
        if(savedInstanceState==null){
            detailFragment = DetailFragment.getInstance();
            ActivityUtils.addFragment(fragmentManager, detailFragment,R.id.fragment);
        }

    }
}
