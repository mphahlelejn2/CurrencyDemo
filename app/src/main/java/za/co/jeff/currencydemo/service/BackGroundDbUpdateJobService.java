package za.co.jeff.currencydemo.service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;
import za.co.jeff.currencydemo.models.ServerRespond;
import za.co.jeff.currencydemo.repo.IOnlineRepository;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;
import za.co.jeff.currencydemo.util.UtilTool;

import static za.co.jeff.currencydemo.repo.UrlManager.API_KEY;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BackGroundDbUpdateJobService extends JobService {

    @Inject
    public IOnlineRepository repository;
    @Inject
    public IRoomRepository roomRepository;
    @Inject
    public BaseSchedulerProvider scheduler;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }


    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        compositeDisposable.add(repository.getOnlineCurrencyValues(API_KEY)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableMaybeObserver<ServerRespond>() {

                    @Override
                    public void onSuccess(ServerRespond serverRespond) {
                    loopDatabaseCurrencyRecords(serverRespond.getCurrencyListValues());
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


    private void loopDatabaseCurrencyRecords(Map<String, Double> ratesList) {
        for (Map.Entry<String, Double> entry : ratesList.entrySet()) {
            checkDatabaseExistence(entry.getKey(),entry.getValue());
        }
    }

    private void checkDatabaseExistence(String key, Double value) {
        compositeDisposable.add(roomRepository.getCurrencyBykey(key).subscribeOn(scheduler.io())
                .observeOn(scheduler.ui()).subscribeWith(new DisposableMaybeObserver<Currency>() {

                    @Override
                    public void onSuccess(Currency currency) {
                        CurrencyRecord currencyRecord=UtilTool.generateCurrencyRecord(currency,value);
                        saveCurrencyRecord(currencyRecord);
                        monitorWarningNumber(key,value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onComplete() {
                    }

                })

        );
    }

    private void monitorWarningNumber(String key, Double value) {
        compositeDisposable.add(roomRepository.getCurrencyBykey(key)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableMaybeObserver<Currency>()
                {

                    @Override
                    public void onSuccess(Currency currency) {
                        if(currency.getWarningValue()>=value)
                            Notification.sendNotification(getApplicationContext());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })

        );

    }

    private void saveCurrencyRecord(CurrencyRecord currencyRecord) {
        compositeDisposable.add(roomRepository.addCurrencyRecord(currencyRecord).subscribeOn(scheduler.io())
                .observeOn(scheduler.ui()).subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                })

        );

    }


    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        compositeDisposable.clear();
        return false;
    }

}
