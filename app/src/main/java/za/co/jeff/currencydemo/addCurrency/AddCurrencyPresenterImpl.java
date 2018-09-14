package za.co.jeff.currencydemo.addCurrency;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import okhttp3.ResponseBody;
import retrofit2.Response;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;
import za.co.jeff.currencydemo.models.ServerRespond;
import za.co.jeff.currencydemo.repo.IOnlineRepository;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;
import static za.co.jeff.currencydemo.repo.UrlManager.API_KEY;


public class AddCurrencyPresenterImpl implements IAddCurrency.Presenter {

    private IOnlineRepository onlineRepository;
    private BaseSchedulerProvider provider;
    private IAddCurrency.View view;
    private IRoomRepository roomRepository;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();


    public AddCurrencyPresenterImpl(IAddCurrency.View view, IOnlineRepository onlineRepository, IRoomRepository roomRepository, BaseSchedulerProvider provider) {
        this.onlineRepository = onlineRepository;
        this.provider = provider;
        this.view = view;
        this.roomRepository=roomRepository;
    }

    @Override
    public void getListOfCurrencyAndDescriptionsFromOnline() {
        compositeDisposable.add(onlineRepository.getListOfCurrencyAndDescriptionsFromOnline()
                .subscribeOn(provider.io()).observeOn(provider.ui())
                .subscribeWith(new DisposableMaybeObserver<Response<ResponseBody>>(){

                    @Override
                    public void onSuccess(Response<ResponseBody> responseBodyResponse) {
                        view.loadCurrencyRespondsFromOnline(responseBodyResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                     view.errorLoadingOnlineCurrencyList();
                    }

                    @Override
                    public void onComplete() {
                        view.emptyOnlineCurrencyList();
                        }})
        );


    }

    @Override
    public void addCurrency(Currency currency) {

        compositeDisposable.add(roomRepository.addCurrencyToDatabase(currency)
                .subscribeOn(provider.io()).observeOn(provider.ui())
                .subscribeWith(new DisposableCompletableObserver(){
                    @Override
                    public void onComplete() {
                        view.currencyAdded(currency);
                    }

                    @Override
                    public void onError(Throwable e) {
                     view.errorAddingCurrency();
                    }
                })

        );

    }


    @Override
    public boolean getOnlineCurrencyValueByCode(String code) {
        compositeDisposable.add(onlineRepository.getOnlineCurrencyValues(API_KEY)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribeWith(new DisposableMaybeObserver<ServerRespond>() {

                    @Override
                    public void onSuccess(ServerRespond serverRespond) {
                        view.returnCurrencyValue(serverRespond.getCurrencyListValues().get(code));
                    }

                    @Override
                    public void onError(Throwable e) {
                      view.errorGettingCurrencyValue();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete(){
                        view.emptyCurrencyValue();
                    }
                })

        );

        return false;
    }


    @Override
    public void addCurrencyRecord(CurrencyRecord currencyRecord) {
        compositeDisposable.add(roomRepository.addCurrencyRecord(currencyRecord)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onError(Throwable e) {
                        view.errorAddingCurrencyRecord();
                    }

                    @Override
                    public void onComplete(){
                        view.addedCurrencyRecord();
                    }
                })

        );
    }

    @Override
    public void clear(){
        compositeDisposable.clear();
    }

    @Override
    public void getJSONObject(Response<ResponseBody> responseBodyResponse) {
        try {
            String responseRecieved = responseBodyResponse.body().string();
            JSONObject jsonObject = new JSONObject(responseRecieved);
            view.loadListOfCurrencyFromOnline(jsonObject );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
