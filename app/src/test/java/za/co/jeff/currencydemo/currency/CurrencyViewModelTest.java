package za.co.jeff.currencydemo.currency;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import io.reactivex.Completable;
import za.co.jeff.currencydemo.Rx.SchedulerProviderTest;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;

public class CurrencyViewModelTest {

    @Mock
    private IRoomRepository roomRepository;
    private BaseSchedulerProvider provider;
    @Mock
    private ICurrency.View view;
    private ICurrency.ICurrencyViewModel viewModel;


    @Before
    public void setUp() throws Exception {
        provider= SchedulerProviderTest.getInstance();
        MockitoAnnotations.initMocks(this);
        viewModel= new CurrencyViewModel(view,roomRepository,provider);
    }


    @Test
    public void deleteCurrencyFromDatabase_Results_Error() {
        Exception exception = new Exception();
        Currency currency=new Currency();
        //Given
        Mockito.when(roomRepository.deleteCurrencyFromDatabase(currency)).thenReturn(Completable.error(exception));
        //when
        viewModel.deleteCurrencyFromDatabase(currency);

        //then
        Mockito.verify(view, Mockito.never()).doneDeletingCurrency();
        Mockito.verify(view).errorDeletingCurrency();
    }

    @Test
    public void deleteCurrencyFromDatabase_Results_Ok(){
        Currency currency=new Currency();
        //Given
        Mockito.when(roomRepository.deleteCurrencyFromDatabase(currency)).thenReturn(Completable.complete());
        //when
        viewModel.deleteCurrencyFromDatabase(currency);
        //then
        Mockito.verify(view, Mockito.never()).errorDeletingCurrency();
        Mockito.verify(view).doneDeletingCurrency();
    }

}