package za.co.jeff.currencydemo.di;

import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;
import za.co.jeff.currencydemo.addCurrency.AddCurrencyFragment;
import za.co.jeff.currencydemo.addCurrency.AddCurrencyModule;
import za.co.jeff.currencydemo.currency.CurrencyFragment;
import za.co.jeff.currencydemo.currency.CurrencyModule;
import za.co.jeff.currencydemo.currencyConversion.CurrencyConversionFragment;
import za.co.jeff.currencydemo.currencyConversion.CurrencyConversionModule;
import za.co.jeff.currencydemo.detail.DetailFragment;
import za.co.jeff.currencydemo.detail.DetailModule;
import za.co.jeff.currencydemo.service.BackGroundDbUpdateJobService;

@Module(includes = AndroidInjectionModule.class)
public abstract class Builder {

    @PerActivity
    @ContributesAndroidInjector(modules = CurrencyModule.class)
    abstract CurrencyFragment bindCurrencyFragment();

    @ContributesAndroidInjector
    abstract BackGroundDbUpdateJobService contributeMyService();

    @PerActivity
    @ContributesAndroidInjector(modules = AddCurrencyModule.class)
    abstract AddCurrencyFragment bindAddCurrencyFragment();

    @PerActivity
    @ContributesAndroidInjector(modules = DetailModule.class)
    abstract DetailFragment bindDetailFragment();

    @PerActivity
    @ContributesAndroidInjector(modules = CurrencyConversionModule.class)
    abstract CurrencyConversionFragment bindCurrencyConversionFragment();

}
