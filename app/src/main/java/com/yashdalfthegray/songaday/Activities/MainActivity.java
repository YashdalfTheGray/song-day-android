package com.yashdalfthegray.songaday.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.yashdalfthegray.songaday.Fragments.AboutFragment;
import com.yashdalfthegray.songaday.Fragments.SettingsFragment;
import com.yashdalfthegray.songaday.Fragments.SongListFragment;
import com.yashdalfthegray.songaday.Models.Song;
import com.yashdalfthegray.songaday.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String SONG_ACTIVITY_MODE = "com.yashdalfthegray.SongaDay.SONG_ACTIVITY_MODE";
    public static final String EDIT_MODE = "com.yashdalfthegray.SongaDay.EDIT_MODE";
    public static final String ADD_MODE = "com.yashdalfthegray.SongaDay.ADD_MODE";
    public static final String SONG_CONTENT = "com.yashdalfthegray.SongaDay.SONG_CONTENT";

    public static final String DB_URL = "https://onesongaday.firebaseio.com/songs";

    private Toolbar mToolbar;
    private DrawerLayout drawer;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        drawer = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        mNavigationView = (NavigationView)findViewById(R.id.navigation_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.getTitle().toString().equals("Songs")) {
                    onTouchDrawer(R.string.title_song_list);
                    menuItem.setChecked(true);
                }
                else if (menuItem.getTitle().toString().equals("Settings")) {
                    onTouchDrawer(R.string.title_settings);
                    menuItem.setChecked(true);
                }
                drawer.closeDrawers();
                return false;
            }
        };

        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        onTouchDrawer(R.string.title_song_list);
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
        if (id == R.id.action_about) {
            Log.d("MainActivity", "About button pressed");
            onTouchDrawer(R.string.title_about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openFragment(final Fragment fragment, final String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).commit();
    }

    private void onTouchDrawer(final int position) {
        switch (position) {
            case R.string.title_song_list:
                setTitle(R.string.title_song_list);
                openFragment(new SongListFragment(), "SongFragment");
                break;
            case R.string.title_settings:
                setTitle(R.string.title_settings);
                openFragment(new SettingsFragment(), "SettingsFragment");
                break;
            case R.string.title_about:
                setTitle(R.string.title_about);
                openFragment(new AboutFragment(), "AboutFragment");
                break;
            default:
                break;
        }
    }
}
