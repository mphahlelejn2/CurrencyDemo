package za.co.jeff.currencydemo.currencyConversion;

import junit.framework.Assert;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import io.reactivex.Maybe;
import za.co.jeff.currencydemo.Rx.SchedulerProviderTest;
import za.co.jeff.currencydemo.TestData;
import za.co.jeff.currencydemo.models.ServerRespond;
import za.co.jeff.currencydemo.repo.IOnlineRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class CurrencyConversionPresenterImplTest {


    @Mock
    private IOnlineRepository onlineRepository;
    private BaseSchedulerProvider provider;
    @Mock
    private ICurrencyConversion.View view;

    private Map<String,Double> recentCurrencyUpdates;
    private ICurrencyConversion.Presenter presenter;

    @Before
    public void setUp() {
        provider= SchedulerProviderTest.getInstance();
        MockitoAnnotations.initMocks(this);
        presenter=new CurrencyConversionPresenterImpl(view, onlineRepository,provider);
    }


    @Test
    public void getCurrencyListValues_Results_Empty() {
       Map<String,Double> map=new HashMap<>();
        //Given
        Mockito.when(onlineRepository.getOnlineCurrencyValues(TestData.API_KEY)).thenReturn(Maybe.empty());
        //when
        presenter.getOnlineCurrencyValues();
        //then
        Mockito.verify(view, Mockito.never()).sendBackCurrencyListValues(map);
        Mockito.verify(view).emptyOnlineCurrencyValues();
        Mockito.verify(view, Mockito.never()).errorGettingOnlineCurrencyValues();
    }


    @Test
    public void getCurrencyListValues_Results_Ok() {
        ServerRespond serverRespond=new ServerRespond();
        //Given
        Mockito.when(onlineRepository.getOnlineCurrencyValues(TestData.API_KEY)).thenReturn(Maybe.just(new ServerRespond()));
        //when
        presenter.getOnlineCurrencyValues();
        //then
        Mockito.verify(view, Mockito.never()).emptyOnlineCurrencyValues();
        Mockito.verify(view).sendBackCurrencyListValues(serverRespond.getCurrencyListValues());
        Mockito.verify(view, Mockito.never()).errorGettingOnlineCurrencyValues();
    }

    @Test
    public void getCurrencyListValues_Results_Error(){
        Exception exception = new Exception();
        Map<String,Double> map=new HashMap<>();
        //Given
        Mockito.when(onlineRepository.getOnlineCurrencyValues(TestData.API_KEY)).thenReturn(Maybe.error(exception));
        //when
        presenter.getOnlineCurrencyValues();
        //then
        Mockito.verify(view, Mockito.never()).emptyOnlineCurrencyValues();
        Mockito.verify(view).errorGettingOnlineCurrencyValues();
        Mockito.verify(view, Mockito.never()).sendBackCurrencyListValues(map);;
    }


    @Test
    public void getCurrencyConversionValue() {
        double value=55;
        double expected=3.943;
        double currencyChoiceValue= 14.92500;
        assertEquals(presenter.getCurrencyConversionValue(value,currencyChoiceValue),expected+"");
    }
}