package za.co.jeff.currencydemo.addCurrency;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Map;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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

    private IOnlineRepository repository;
    private BaseSchedulerProvider provider;
    private IAddCurrency.View view;
    private IRoomRepository roomRepository;
    private Map<String,Double> recentCurrencyUpdates;

    CompositeDisposable compositeDisposable=new CompositeDisposable();


    public AddCurrencyPresenterImpl(IAddCurrency.View view, IOnlineRepository repository,IRoomRepository roomRepository, BaseSchedulerProvider provider) {
        this.repository = repository;
        this.provider = provider;
        this.view = view;
        this.roomRepository=roomRepository;
    }

    @Override
    public void getListOfCurrencyFromOnline() {
        Disposable disposable=repository.onlineCurrencyListAndDescriptions()
                .subscribeOn(provider.io()).observeOn(provider.ui())
                .subscribeWith(new DisposableMaybeObserver<Response<ResponseBody>>(){

                    @Override
                    public void onSuccess(Response<ResponseBody> responseBodyResponse) {

                        String responseRecieved = null;
                        try {
                            responseRecieved = responseBodyResponse.body().string();
                            JSONObject jsonObject = new JSONObject(responseRecieved);
                            view.sendBackResults(jsonObject );
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                     Log.d("", e.toString());
                     view.pleaseCheckInternet();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("","");
                    }
                });

        compositeDisposable.add(disposable);
    }

    @Override
    public void addCurrency(Currency currency) {

        Disposable disposable= roomRepository.addCurrency(currency)
                .subscribeOn(provider.io()).observeOn(provider.ui())
                .subscribeWith(new DisposableCompletableObserver(){
                    @Override
                    public void onComplete() {
                        view.doneAddingCurrency(currency);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

        compositeDisposable.add(disposable);
    }

    @Override
    public String getValueByKey(JSONObject jsonObject, String s) throws JSONException {
        return (String) jsonObject.get(s);
    }


    @Override
    public boolean getCurrencyRecentValueByCode(String code) {
        compositeDisposable.add(repository.onlineCurrencyValues(API_KEY)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribeWith(new DisposableMaybeObserver<ServerRespond>() {

                    @Override
                    public void onSuccess(ServerRespond serverRespond) {
                        recentCurrencyUpdates=serverRespond.getCurrencyListValues();
                        view.sendBackRecentValue(recentCurrencyUpdates.get(code));
                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete(){
                    }
                })

        );

        return false;
    }


    @Override
    public void addCurrencyRecord(CurrencyRecord currencyRecord) {
        compositeDisposable.add(roomRepository.saveCurrencyRecord(currencyRecord)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete(){
                        view.doneAddingCurrencyRecord();
                    }
                })

        );
    }
}
