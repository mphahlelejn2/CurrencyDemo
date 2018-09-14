package za.co.jeff.currencydemo.base;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import za.co.jeff.currencydemo.service.BackGroundDbUpdateJobService;

import static za.co.jeff.currencydemo.repo.UrlManager.timeInterval;

abstract public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startJob();
    }


    private void roomDataBaseUpdateScheduler(){
        JobScheduler jobScheduler = (JobScheduler)getApplicationContext()
                .getSystemService(JOB_SCHEDULER_SERVICE);

        ComponentName componentName = new ComponentName(this,
                BackGroundDbUpdateJobService.class);

        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                .setPeriodic(timeInterval)
                .setRequiredNetworkType(
                        JobInfo.NETWORK_TYPE_NOT_ROAMING)
                .setPersisted(true).build();
        jobScheduler.schedule(jobInfo);
    }

    private void startJob(){
        SharedPreferences preferences = PreferenceManager.
                getDefaultSharedPreferences(this);
        if(!preferences.getBoolean("IsOn", false)){
            roomDataBaseUpdateScheduler();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("IsOn", true);
            editor.commit();
        }
    }
}
