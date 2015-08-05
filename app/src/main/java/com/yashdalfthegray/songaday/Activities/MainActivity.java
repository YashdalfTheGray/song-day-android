package com.yashdalfthegray.songaday.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.yashdalfthegray.songaday.Fragments.SongListFragment;
import com.yashdalfthegray.songaday.R;
import com.yashdalfthegray.songaday.Models.Song;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public Firebase songsDb;
    public ArrayList<Song> songList = new ArrayList<>();

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        songsDb = new Firebase("https://onesongaday.firebaseio.com/songs");

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        songsDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Main Activity", "Found " + dataSnapshot.getChildrenCount() + " children in the database!");

                for (DataSnapshot songSnapshot : dataSnapshot.getChildren()) {
                    songList.add(songSnapshot.getValue(Song.class));
                }
                Log.d("MainActivity", songList.get(5).toString());

                // Going to have to use a bundle here...for whatever fucking reason...thanks Android!
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("songList", songList);

                SongListFragment songFragment = new SongListFragment();
                songFragment.setArguments(bundle);
                openFragment(songFragment);
                setTitle(R.string.title_song_list);
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
