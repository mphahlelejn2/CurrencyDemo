package za.co.jeff.currencydemo.service;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import za.co.jeff.currencydemo.R;
import za.co.jeff.currencydemo.models.Currency;
import za.co.jeff.currencydemo.models.CurrencyRecord;
import za.co.jeff.currencydemo.models.ServerRespond;
import za.co.jeff.currencydemo.repo.IOnlineRepository;
import za.co.jeff.currencydemo.repo.IRoomRepository;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;
import za.co.jeff.currencydemo.util.UtilTool;

import static za.co.jeff.currencydemo.repo.UrlManager.API_KEY;


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

        compositeDisposable.add(repository.onlineCurrencyValues(API_KEY)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableMaybeObserver<ServerRespond>() {

                    @Override
                    public void onSuccess(ServerRespond serverRespond) {
                    sync(serverRespond.getCurrencyListValues());
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


    private void sync(Map<String, Double> ratesList) {
        for (Map.Entry<String, Double> entry : ratesList.entrySet()) {
            CurrencyRecord currencyRecord=new CurrencyRecord();
            currencyRecord.setName(entry.getKey());
            currencyRecord.setTimeStamp(UtilTool.getCurrentTimesStanp());
            currencyRecord.setValue(entry.getValue());
            checkDatabaseExistance(currencyRecord);
           checkWarningNumber(entry.getKey(),entry.getValue());

        }
    }

    private void checkWarningNumber(String key, Double value) {
        compositeDisposable.add(roomRepository.getCurrencyBykey(key)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableMaybeObserver<Currency>()
                {

                    @Override
                    public void onSuccess(Currency currency) {
                        if(currency.getWarningValue()<value)
                            sendNotification();
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


        private void checkDatabaseExistance(CurrencyRecord currencyRecord) {
            compositeDisposable.add(roomRepository.getCurrencyBykey(currencyRecord.getName()).subscribeOn(scheduler.io())
                    .observeOn(scheduler.ui()).subscribeWith(new DisposableMaybeObserver<Currency>() {

                        @Override
                        public void onSuccess(Currency currency) {
                           saveCurrencyRecord(currencyRecord);
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
        compositeDisposable.add(roomRepository.saveCurrencyRecord(currencyRecord).subscribeOn(scheduler.io())
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


    private void sendNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Please open updates")
                        .setContentText("Maximum number reached!");

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());

    }

}
