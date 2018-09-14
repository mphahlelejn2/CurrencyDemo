package za.co.jeff.currencydemo.currency;
import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import za.co.jeff.currencydemo.models.Currency;


public interface ICurrency {
    interface View{
        Activity getActivity();
        void initSetup();
        RecyclerView getRecyclerView();
        void filter(String newText);
        Context getContext();
        void doneDeletingCurrency();
        void doneAddingCurrency();
        void deleteCurrency(Currency currency);
        void emptyList();
        void errorLoadingImages();
        void disableInstruction();
        void enableInstruction();
        void errorDeletingCurrency();
    }
    interface ICurrencyViewModel {
        LiveData<List<Currency>> getListOfCurrency();
        void deleteCurrencyFromDatabase(Currency currency);
    }
}
