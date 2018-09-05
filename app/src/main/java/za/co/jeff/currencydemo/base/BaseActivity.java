package za.co.jeff.currencydemo.base;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import za.co.jeff.currencydemo.R;
import za.co.jeff.currencydemo.service.BackGroundDbUpdateJobService;

import static za.co.jeff.currencydemo.repo.UrlManager.timeInterval;

abstract public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomDataBaseUpdateSchedular();
    }


    private void roomDataBaseUpdateSchedular(){
        JobScheduler jobScheduler = (JobScheduler)getApplicationContext()
                .getSystemService(JOB_SCHEDULER_SERVICE);

        ComponentName componentName = new ComponentName(this,
                BackGroundDbUpdateJobService.class);

        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                .setPeriodic(timeInterval).setRequiredNetworkType(
                        JobInfo.NETWORK_TYPE_NOT_ROAMING)
                .setPersisted(true).build();
        jobScheduler.schedule(jobInfo);
    }

}
