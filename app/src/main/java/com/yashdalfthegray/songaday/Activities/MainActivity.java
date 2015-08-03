package com.yashdalfthegray.songaday.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;
import com.yashdalfthegray.songaday.Fragments.SongListFragment;
import com.yashdalfthegray.songaday.R;


public class MainActivity extends AppCompatActivity {

    public Firebase songsDb;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        songsDb = new Firebase("https://onesongaday.firebaseio.com/songs");

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        openFragment(new SongListFragment());
        setTitle(R.string.title_song_list);
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
            Log.d("MainActivity", "Settings button pressed");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openFragment (final Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
