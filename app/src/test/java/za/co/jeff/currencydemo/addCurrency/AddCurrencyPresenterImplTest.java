package za.co.jeff.currencydemo.addCurrency;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import za.co.jeff.currencydemo.Rx.SchedulerProviderTest;
import za.co.jeff.currencydemo.TestData;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;
import za.co.jeff.currencydemo.models.ServerRespond;
import za.co.jeff.currencydemo.repo.IOnlineRepository;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;

public class AddCurrencyPresenterImplTest {

    @Mock
    private IOnlineRepository onlineRepository;
    @Mock
    private IAddCurrency.View view;
    @Mock
    private IRoomRepository roomRepository;
    @Mock
    private JSONObject jsonObject;
    private BaseSchedulerProvider provider;

    private IAddCurrency.Presenter presenter;

    @Before
    public void setUp()  {
        provider= SchedulerProviderTest.getInstance();
        MockitoAnnotations.initMocks(this);
        presenter=new AddCurrencyPresenterImpl(view, onlineRepository,roomRepository,provider);
    }

    @Test
    public void getListOfCurrencyFromOnline_Results_Ok() {
        Response<ResponseBody> res=getRespondsSuccess();
        //Given
        Mockito.when(onlineRepository.getListOfCurrencyAndDescriptionsFromOnline()).thenReturn(Maybe.just(res));
        //when
        presenter.getListOfCurrencyAndDescriptionsFromOnline();
        //then
        Mockito.verify(view, Mockito.never()).errorLoadingOnlineCurrencyList();
        Mockito.verify(view).loadCurrencyRespondsFromOnline(res);
        Mockito.verify(view, Mockito.never()).emptyOnlineCurrencyList();

    }

    @Test
    public void getListOfCurrencyFromOnline_Results_Error() {
        Exception exception = new Exception();

        Mockito.when(onlineRepository.getListOfCurrencyAndDescriptionsFromOnline())
                .thenReturn(Maybe.error(exception));
       presenter.getListOfCurrencyAndDescriptionsFromOnline();
        Mockito.verify(view).errorLoadingOnlineCurrencyList();
        Mockito.verify(view, Mockito.never()).loadCurrencyRespondsFromOnline(getRespondsSuccess());
        Mockito.verify(view, Mockito.never()).emptyOnlineCurrencyList();
    }

    @Test
    public void getListOfCurrencyFromOnline_Results_Empty() {
        //Given
        Mockito.when(onlineRepository.getListOfCurrencyAndDescriptionsFromOnline()).thenReturn(Maybe.empty());
        //when
        presenter.getListOfCurrencyAndDescriptionsFromOnline();
        //then
        Mockito.verify(view, Mockito.never()).errorLoadingOnlineCurrencyList();
        Mockito.verify(view, Mockito.times(1)).emptyOnlineCurrencyList();
        Mockito.verify(view).emptyOnlineCurrencyList();
    }

    @Test
    public void addCurrency_Results_Error() {
        Exception exception = new Exception();
        Currency currency=new Currency();
        //Given
        Mockito.when(roomRepository.addCurrencyToDatabase(currency)).thenReturn(Completable.error(exception));
        //when
        presenter.addCurrency(currency);
        //then
        Mockito.verify(view, Mockito.never()).currencyAdded(currency);
        Mockito.verify(view).errorAddingCurrency();
    }

    @Test
    public void addCurrency_Results_Ok(){
        Currency currency=new Currency();
        //Given
        Mockito.when(roomRepository.addCurrencyToDatabase(currency)).thenReturn(Completable.complete());
        //when
        presenter.addCurrency(currency);
        //then
        Mockito.verify(view, Mockito.never()).errorAddingCurrency();
        Mockito.verify(view).currencyAdded(currency);
    }

    @Test
    public void getCurrencyRecentValueByCode_Results_Ok(){
        ServerRespond serverRespond=new ServerRespond();
        //Given
        Mockito.when(onlineRepository.getOnlineCurrencyValues(TestData.API_KEY)).thenReturn(Maybe.just(serverRespond));
        //when

        presenter.getOnlineCurrencyValueByCode(TestData.API_KEY);
        //then
        Mockito.verify(view, Mockito.never()).errorGettingCurrencyValue();
        Mockito.verify(view).returnCurrencyValue(serverRespond.getCurrencyListValues().get(1));
        Mockito.verify(view, Mockito.never()).emptyCurrencyValue();
    }

    private Response<ResponseBody> getRespondsSuccess(){
        return Response.success(
                ResponseBody.create(
                        MediaType.parse("application/json"),
                        TestData.jsonListOfCurrenciesAndDescriptions
                )
        );
    }
    private Response<ResponseBody> getRespondsError(){
        return Response.error(
                403,
                ResponseBody.create(
                        MediaType.parse("application/json"),TestData.jsonListOfCurrenciesAndDescriptions
                )
        );
    }

    private JSONObject getJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("key", "value");
        return json;
    }


    @Test
    public void getCurrencyRecentValueByCode_Results_Error(){
        Exception exception = new Exception();
        double number=0l;
        //Given
        Mockito.when(onlineRepository.getOnlineCurrencyValues(TestData.API_KEY)).thenReturn(Maybe.error(exception));
        //when
        presenter.getOnlineCurrencyValueByCode(TestData.API_KEY);
        //then
        Mockito.verify(view, Mockito.never()).returnCurrencyValue(number);
        Mockito.verify(view).errorGettingCurrencyValue();
        Mockito.verify(view, Mockito.never()).emptyCurrencyValue();

    }

    @Test
    public void getCurrencyRecentValueByCode_Results_Empty() {
        double number=0l;
        //Given
        Mockito.when(onlineRepository.getOnlineCurrencyValues(TestData.API_KEY)).thenReturn(Maybe.empty());
        //when
        presenter.getOnlineCurrencyValueByCode(TestData.API_KEY);
        //then
        Mockito.verify(view, Mockito.never()).returnCurrencyValue(number);
        Mockito.verify(view).emptyCurrencyValue();
        Mockito.verify(view, Mockito.never()).errorGettingCurrencyValue();
    }


    @Test
    public void addCurrencyRecord_Results_Ok() {
        CurrencyRecord currencyRecord=new CurrencyRecord();
        //Given
        Mockito.when(roomRepository.addCurrencyRecord(currencyRecord)).thenReturn(Completable.complete());
        //when
        presenter.addCurrencyRecord(currencyRecord);
        //then
        Mockito.verify(view, Mockito.never()).errorAddingCurrency();
        Mockito.verify(view).addedCurrencyRecord();
    }

    @Test
    public void addCurrencyRecord_Results_Error(){
        Exception exception = new Exception();
        CurrencyRecord currencyRecord=new CurrencyRecord();
        //Given
        Mockito.when(roomRepository.addCurrencyRecord(currencyRecord)).thenReturn(Completable.error(exception));
        //when
        presenter.addCurrencyRecord(currencyRecord);
        //then
        Mockito.verify(view, Mockito.never()).addedCurrencyRecord();
        Mockito.verify(view).errorAddingCurrencyRecord();
    }

    @Test
    public void getValueByKey() {
    }

}