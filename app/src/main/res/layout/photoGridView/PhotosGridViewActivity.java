package com.jeffreymphahlele.jeffdemo.photoGridView;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jeffreymphahlele.jeffdemo.R;
import com.jeffreymphahlele.jeffdemo.util.ActivityUtils;

public class PhotosGridViewActivity extends AppCompatActivity {

    private PhotosGridViewFragment photosGridViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view_activity);

        FragmentManager fragmentManager=this.getSupportFragmentManager();
        if(savedInstanceState==null){
            photosGridViewFragment = PhotosGridViewFragment.getInstance();
            ActivityUtils.addFragment(fragmentManager, photosGridViewFragment,R.id.gridview_fragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
