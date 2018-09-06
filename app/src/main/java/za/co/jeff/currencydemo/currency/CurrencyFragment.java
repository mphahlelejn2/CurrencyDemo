package za.co.jeff.currencydemo.currency;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import javax.inject.Inject;
import butterknife.BindView;
import dmax.dialog.SpotsDialog;
import za.co.jeff.currencydemo.R;
import za.co.jeff.currencydemo.addCurrency.AddCurrencyActivity;
import za.co.jeff.currencydemo.base.BaseFragment;
import za.co.jeff.currencydemo.models.Currency;

public class CurrencyFragment extends BaseFragment implements ICurrency.View {

    public CurrencyAdapter currencyAdapter;
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    @BindView(R.id.fab)
    public FloatingActionButton add;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ICurrency.ICurrencyViewModel viewModel;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
       super.onViewCreated(view, savedInstanceState);
        initRecycleView();
        currencyAdapter =new CurrencyAdapter(this,new ArrayList<>());
        recyclerView.setAdapter(currencyAdapter);
        initAddNewUser();
        initViewModel();
        Snackbar snackbar = Snackbar
                .make(container, "Make sure you have internet connection", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.currency_fragment;
    }

   private void initAddNewUser() {

        add.setOnClickListener(view -> {
            OpenAddCurrencyActivity();
        });
    }

    private void OpenAddCurrencyActivity() {
        Intent i=new Intent(getActivity(), AddCurrencyActivity.class);
        startActivity(i);
    }


    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyViewModel.class);

        viewModel.getListOfCurrency().observe(CurrencyFragment.this, list -> {
            currencyAdapter.addCurrency(list);
        });
    }

    private void initRecycleView() {
        RecyclerView.LayoutManager manager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
    }


    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void filter(String newText) {
        currencyAdapter.getFilter().filter(newText);
    }

    @Override
    public void doneDeletingCurrency() {

    }

    @Override
    public void doneAddingCurrency() {

    }

    @Override
    public void deleteCurrency(Currency currency) {
        viewModel.deleteCurrency(currency);
    }


    @Override
    public void emptyList() {
        Snackbar snackbar = Snackbar
                .make(container, "Photos List is Empty", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void errorLoadingImages() {
        Snackbar snackbar = Snackbar
                .make(container, "Error loading Photos . Please Check your Network", Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    public static CurrencyFragment getInstance() {
        return new CurrencyFragment();
    }
    @Override
    public void dismissLoadDialog() {
        loadPhotosProgressDialog.dismiss();
    }

    @Override
    public void initLoadProgressDialog() {
        loadPhotosProgressDialog =new SpotsDialog(getActivity(), "Loading local db ....");
        loadPhotosProgressDialog.show();
    }
}
