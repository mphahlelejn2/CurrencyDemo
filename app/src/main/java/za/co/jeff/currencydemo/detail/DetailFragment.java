package za.co.jeff.currencydemo.detail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import za.co.jeff.currencydemo.R;
import za.co.jeff.currencydemo.base.BaseFragment;
import za.co.jeff.currencydemo.currency.CurrencyAdapter;
import za.co.jeff.currencydemo.currency.CurrencyFragment;
import za.co.jeff.currencydemo.currency.CurrencyViewModel;
import za.co.jeff.currencydemo.currency.ICurrency;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;


public class DetailFragment extends BaseFragment implements IDetailCurrency{

    @BindView(R.id.tvCode)
    TextView code;
    @BindView(R.id.codeList)
    public RecyclerView recyclerView;
    Currency currency;

    private CurrencyTrackAdapter currencyTrackAdapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private IDetailCurrency.IDetailViewModel viewModel;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currency= getCurrencyFromIntent();
        initRecycleView();
        currencyTrackAdapter =new CurrencyTrackAdapter(new ArrayList<>());
        recyclerView.setAdapter(currencyTrackAdapter);
        initViewModel();
        code.setText(currency.getCurrencyCode());
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel.class);
        viewModel.getListOfCurrencyRecordByCode(currency.getCurrencyCode()).observe(DetailFragment.this, list -> {
            currencyTrackAdapter.addCurrencyRecord(list);
        });
    }

    private void initRecycleView() {
        RecyclerView.LayoutManager manager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
    }


    private Currency getCurrencyFromIntent() {
        Gson gson = new Gson();
        return gson.fromJson(getActivity().getIntent().getStringExtra("data"), Currency.class);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.detail_fragment;
    }

    public static DetailFragment getInstance() {
        return new DetailFragment();
    }


}
