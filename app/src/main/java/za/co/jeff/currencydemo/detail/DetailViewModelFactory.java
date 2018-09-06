package za.co.jeff.currencydemo.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import za.co.jeff.currencydemo.currency.ICurrency;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;


@Singleton
public class DetailViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    public IRoomRepository repository;

    public DetailViewModelFactory(IRoomRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            return (T) new  DetailViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}