package com.yashdalfthegray.songaday.Activities;

import android.support.design.widget.Snackbar;
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

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.yashdalfthegray.songaday.Models.Song;
import com.yashdalfthegray.songaday.R;

import java.util.HashMap;
import java.util.Map;

public class AddSongActivity extends AppCompatActivity implements EditText.OnEditorActionListener, Button.OnClickListener {

    private Toolbar mToolbar;
    private Button actionButton;
    private EditText editTitle;
    private EditText editArtist;
    private EditText editGenre;
    private EditText editLink;

    private String activityMode;
    private Song songContent;

    Firebase songRef;
    Map<String, Object> songObject;
    Snackbar addSongSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        songObject = new HashMap<>();

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

                songRef = new Firebase(MainActivity.DB_URL + '/' + songContent.getKey());
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
            finish();
        }
        else if (activityMode.equals(MainActivity.EDIT_MODE)) {
            Log.d("AddSongActivity", "This should submit the form to updateChildren()");
            findChangedProperties();
            if(songObject.size() > 0) {
                songRef.updateChildren(songObject, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    if (firebaseError != null) {
                        addSongSnackbar = Snackbar.make(findViewById(R.id.add_song_activity_root), "Couldn't save song :(", Snackbar.LENGTH_SHORT);
                        addSongSnackbar.getView().setElevation(10000);
                        addSongSnackbar.show();
                        Log.e("AddSongActivity", firebaseError.getMessage());
                    }
                    else {
                        finish();
                    }
                    }
                });
            }
            else {
                finish();
            }
        }
    }

    private void findChangedProperties() {
        if (!editTitle.getText().toString().equals(songContent.getTitle())) {
            songObject.put("title", editTitle.getText().toString());
        }
        if (!editArtist.getText().toString().equals(songContent.getArtist())) {
            songObject.put("artist", editArtist.getText().toString());
        }
        if (!editGenre.getText().toString().equals(songContent.getGenre())) {
            songObject.put("genre", editGenre.getText().toString());
        }
        if (!editLink.getText().toString().equals(songContent.getLink())) {
            songObject.put("link", editLink.getText().toString());
        }
    }
}
