package com.yashdalfthegray.songaday.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yashdalfthegray.songaday.Models.Song;
import com.yashdalfthegray.songaday.R;

public class AddSongActivity extends AppCompatActivity implements EditText.OnEditorActionListener, Button.OnClickListener {

    private Toolbar mToolbar;
    private Button actionButton;
    private EditText editTitle;
    private EditText editArtist;
    private EditText editGenre;
    private EditText editLink;

    private String activityMode;
    private Song songContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        activityMode = getIntent().getStringExtra(MainActivity.SONG_ACTIVITY_MODE);
        songContent = getIntent().getParcelableExtra(MainActivity.SONG_CONTENT);

        actionButton = (Button)findViewById(R.id.song_action_button);
        editTitle = (EditText)findViewById(R.id.title_edit_text);
        editArtist = (EditText)findViewById(R.id.artist_edit_text);
        editGenre = (EditText)findViewById(R.id.genre_edit_text);
        editLink = (EditText)findViewById(R.id.link_edit_text);

        if(activityMode.equals(MainActivity.ADD_MODE)) {
            setTitle("Add a song");
            actionButton.setText("Add");
        }
        else if (activityMode.equals(MainActivity.EDIT_MODE)) {
            setTitle("Edit song");
            if(songContent != null) {
                editTitle.setText(songContent.getTitle());
                editArtist.setText(songContent.getArtist());
                editGenre.setText(songContent.getGenre());
                editLink.setText(songContent.getLink());
            }
            actionButton.setText("Save");
        }

        actionButton.setOnClickListener(this);
        editTitle.setOnEditorActionListener(this);
        editArtist.setOnEditorActionListener(this);
        editGenre.setOnEditorActionListener(this);
        editLink.setOnEditorActionListener(this);

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

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_DONE:
                onClick(actionButton);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (activityMode.equals(MainActivity.ADD_MODE)) {
            Log.d("AddSongActivity", "This should submit the form to push() and setValue()");

        }
        else if (activityMode.equals(MainActivity.EDIT_MODE)) {
            Log.d("AddSongActivity", "This should submit the form to updateChildren()");
        }
        finish();
    }
}
