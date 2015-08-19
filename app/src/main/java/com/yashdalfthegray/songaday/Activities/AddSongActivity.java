package com.yashdalfthegray.songaday.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.yashdalfthegray.songaday.R;

public class AddSongActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        String activityMode = getIntent().getStringExtra(MainActivity.SONG_ACTIVITY_MODE);

        actionButton = (Button)findViewById(R.id.song_action_button);

        if(activityMode.equals(MainActivity.ADD_MODE)) {
            setTitle("Add a song");
            actionButton.setText("Add");
        }
        else if (activityMode.equals(MainActivity.EDIT_MODE)) {
            setTitle("Edit song");
            actionButton.setText("Save");
        }

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
