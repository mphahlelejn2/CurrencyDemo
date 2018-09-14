package za.co.jeff.currencydemo.Rx;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;
import za.co.jeff.currencydemo.rxjava.BaseSchedulerProvider;


public class SchedulerProviderTest implements BaseSchedulerProvider {

    @Nullable
    private static SchedulerProviderTest INSTANCE;

    // Prevent direct instantiation.
    private SchedulerProviderTest() {
    }

    public static synchronized  SchedulerProviderTest getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchedulerProviderTest();
        }
        return INSTANCE;
    }

    @Override
    @NonNull
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @Override
    @NonNull
    public  Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    @NonNull
    public  Scheduler ui() {
        return Schedulers.trampoline();
    }
}
