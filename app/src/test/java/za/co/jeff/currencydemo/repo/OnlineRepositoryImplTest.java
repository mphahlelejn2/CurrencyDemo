package za.co.jeff.currencydemo.repo;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import za.co.jeff.currencydemo.models.ServerRespond;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class OnlineRepositoryImplTest {

    @Mock
    private APIClient client;
    private IOnlineRepository onlineRepository;
    public static final String API_KEY ="a077992e57b34206abd4e70737f661b8";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        onlineRepository=new OnlineRepositoryImpl(client);
    }

    @Test
    public void onlineCurrencyListAndDescriptions_Results_Ok() {

        Response<ResponseBody> response=getRespondsSuccess();

        when(client.onlineCurrencyListAndDescriptions()).thenReturn(Maybe.just(response));
        //When
        TestObserver<Response<ResponseBody>> observer = new TestObserver<>();
        onlineRepository.getListOfCurrencyAndDescriptionsFromOnline().subscribe(observer);
        //then

        observer.assertNoErrors();
        observer.assertSubscribed();
        observer.assertComplete();
        observer.awaitTerminalEvent();

        Response<ResponseBody> currencyList1=  observer.values().get(0);
        Assert.assertEquals(currencyList1.body(), response.body());

    }

    private Response<ResponseBody> getRespondsSuccess(){
        return Response.success(
                ResponseBody.create(
                        MediaType.parse("application/json"),
                        "{}"
                )
        );
    }

    @Test
    public void onlineCurrencyListAndDescriptions_Results_Empty() {

        when(client.onlineCurrencyListAndDescriptions()).thenReturn(Maybe.empty());
        //When
        TestObserver<Response<ResponseBody>> observer = new TestObserver<>();
        onlineRepository.getListOfCurrencyAndDescriptionsFromOnline().subscribe(observer);
        //then
        observer.assertNoErrors();
        observer.assertSubscribed();
        observer.assertComplete();
        observer.awaitTerminalEvent();

        Assert.assertTrue(observer.values().isEmpty());

    }
    @Test
    public void onlineCurrencyListAndDescriptions_Results_Error() {

        Exception exception = new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Cannot execute request")));

        when(client.onlineCurrencyListAndDescriptions()).thenReturn(Maybe.error(exception));
        //When
        TestObserver<Response<ResponseBody>> observer = new TestObserver<>();
        onlineRepository.getListOfCurrencyAndDescriptionsFromOnline().subscribe(observer);

        //Then
        observer.awaitTerminalEvent();
        observer.assertError(HttpException.class);
        verify(client).onlineCurrencyListAndDescriptions();
    }


    @Test
    public void getOnlineCurrencyValues_Results_Ok() {

        ServerRespond response=new ServerRespond();

        when(client.onlineCurrencyValues(API_KEY)).thenReturn(Maybe.just(response));
        //When
        TestObserver<ServerRespond> observer = new TestObserver<>();
        onlineRepository.getOnlineCurrencyValues(API_KEY).subscribe(observer);
        //then

        observer.assertNoErrors();
        observer.assertSubscribed();
        observer.assertComplete();
        observer.awaitTerminalEvent();

        ServerRespond response1=  observer.values().get(0);
        Assert.assertEquals(response.getTimestamp(),response1.getTimestamp());

    }


    @Test
    public void getOnlineCurrencyValues_Results_Empty() {

        ServerRespond response=new ServerRespond();

        when(client.onlineCurrencyValues(API_KEY)).thenReturn(Maybe.just(response));
        //When
        TestObserver<ServerRespond> observer = new TestObserver<>();
        onlineRepository.getOnlineCurrencyValues(API_KEY).subscribe(observer);
        //then

        observer.assertNoErrors();
        observer.assertSubscribed();
        observer.assertComplete();
        observer.awaitTerminalEvent();

        //Assert.assertTrue(observer.getEvents().isEmpty());

    }

    @Test
    public void getOnlineCurrencyValues_Results_Error() {

        Exception exception = new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Cannot execute request")));

        when(client.onlineCurrencyValues(API_KEY)).thenReturn(Maybe.error(exception));

        //When
        TestObserver<ServerRespond> observer = new TestObserver<>();
        onlineRepository.getOnlineCurrencyValues(API_KEY).subscribe(observer);

        //Then
        observer.awaitTerminalEvent();
        observer.assertError(HttpException.class);
        verify(client).onlineCurrencyValues(API_KEY);
    }

}