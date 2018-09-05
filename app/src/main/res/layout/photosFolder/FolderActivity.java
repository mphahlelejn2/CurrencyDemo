package com.jeffreymphahlele.jeffdemo.photosFolder;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jeffreymphahlele.jeffdemo.R;


public class FolderActivity extends AppCompatActivity {

    private com.jeffreymphahlele.jeffdemo.photosFolder.FolderFragment folderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folder_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FragmentManager fragmentManager=this.getSupportFragmentManager();
        if(savedInstanceState==null){
            folderFragment = com.jeffreymphahlele.jeffdemo.photosFolder.FolderFragment.getInstance();
            com.jeffreymphahlele.jeffdemo.util.ActivityUtils.addFragment(fragmentManager, folderFragment,R.id.fragment);
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
