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
import android.widget.TextView;

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
    @BindView(R.id.instruction)
    public TextView instruction;
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

    @Override
    public void onResume() {
        super.onResume();
        initSetup();
    }


    @Override
    public void initSetup() {
        if(currencyAdapter.getItemCount()!=0)
            disableInstruction();
        else
            enableInstruction();
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
        makeSnackBar("Done Deleting currency");
    }

    @Override
    public void doneAddingCurrency() {
        makeSnackBar("Done adding Currency");
    }

    @Override
    public void deleteCurrency(Currency currency) {
        viewModel.deleteCurrencyFromDatabase(currency);
    }


    @Override
    public void emptyList() {
        makeSnackBar("Photos List is Empty");
    }

    @Override
    public void errorLoadingImages() {
        makeSnackBar("Error loading Photos . Please Check your Network");
    }

    @Override
    public void disableInstruction() {
        instruction.setVisibility(View.INVISIBLE);
    }

    @Override
    public void enableInstruction() {
        instruction.setVisibility(View.VISIBLE);
    }

    @Override
    public void errorDeletingCurrency() {
        makeSnackBar("Error Deleting Currency");
    }

    public static CurrencyFragment getInstance() {
        return new CurrencyFragment();
    }

    private void makeSnackBar(String s) {
        Snackbar snackbar = Snackbar
                .make(container, s, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
