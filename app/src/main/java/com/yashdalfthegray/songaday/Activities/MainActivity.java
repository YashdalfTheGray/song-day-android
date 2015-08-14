package com.yashdalfthegray.songaday.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.yashdalfthegray.songaday.Adapters.NavDrawerAdapter;
import com.yashdalfthegray.songaday.Fragments.SettingsFragment;
import com.yashdalfthegray.songaday.Fragments.SongListFragment;
import com.yashdalfthegray.songaday.Models.DrawerItem;
import com.yashdalfthegray.songaday.R;
import com.yashdalfthegray.songaday.Models.Song;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public Firebase songsDb;
    public ArrayList<Song> songList = new ArrayList<>();
    public String HEADER_TITLE;

    private final static int SONG_LIST_FRAGMENT = 1;
    private final static int SETTINGS_FRAGMENT = 2;

    private int currentFragment = 1;

    private Toolbar mToolbar;
    private RecyclerView mRecycleerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout drawer;
    private RecyclerView.Adapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<DrawerItem> dataList;


    private void addItemsToDataList(){
        dataList.add(new DrawerItem(getString(R.string.title_song_list), R.drawable.ic_music_note_black_36dp));
        dataList.add(new DrawerItem(getString(R.string.title_settings), R.drawable.ic_settings_black_36dp));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HEADER_TITLE = getString(R.string.app_name);

        Firebase.setAndroidContext(this);
        songsDb = new Firebase("https://onesongaday.firebaseio.com/songs");

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mRecycleerView = (RecyclerView)findViewById(R.id.sidebar_recycler);
        mRecycleerView.setHasFixedSize(true);
        dataList = new ArrayList<>();
        addItemsToDataList();

        mAdapter = new NavDrawerAdapter(dataList, this, HEADER_TITLE);
        mRecycleerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecycleerView.setLayoutManager(mLayoutManager);

        drawer = (DrawerLayout)findViewById(R.id.main_drawer_layout);
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

        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecycleerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());

                if (child != null && mGestureDetector.onTouchEvent(e)) {
                    drawer.closeDrawers();
                    onTouchDrawer(rv.getChildLayoutPosition(child));
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        songsDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Main Activity", "Found " + dataSnapshot.getChildrenCount() + " children in the database!");

                for (DataSnapshot songSnapshot : dataSnapshot.getChildren()) {
                    songList.add(songSnapshot.getValue(Song.class));
                }

                onTouchDrawer(currentFragment);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.w("MainActivity", "Firebase read error: " + firebaseError.getMessage());
            }
        });
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openFragment(final Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    private void onTouchDrawer(final int position) {
        currentFragment = position;
        switch (position) {
            case SONG_LIST_FRAGMENT:
                // Going to have to use a bundle here...for whatever fucking reason...thanks Android!
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("songList", songList);

                SongListFragment songFragment = new SongListFragment();
                songFragment.setArguments(bundle);

                setTitle(R.string.title_song_list);
                openFragment(songFragment);
                break;
            case SETTINGS_FRAGMENT:
                setTitle(R.string.title_settings);
                openFragment(new SettingsFragment());
                break;
            default:
                break;
        }
    }
}
