package za.co.jeff.currencydemo.currency;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import javax.inject.Inject;
import javax.inject.Singleton;

import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;


@Singleton
public class CurrencyViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    public IRoomRepository repository;
    @Inject
    public BaseSchedulerProvider provider;


    public ICurrency.View view;

    public CurrencyViewModelFactory(ICurrency.View view, IRoomRepository repository, BaseSchedulerProvider provider) {
        this.repository = repository;
        this.provider = provider;
        this.view=view;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CurrencyViewModel.class)) {
            return (T) new CurrencyViewModel(view,repository,provider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}