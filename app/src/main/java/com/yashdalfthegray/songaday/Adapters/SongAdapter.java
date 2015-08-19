package com.yashdalfthegray.songaday.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yashdalfthegray.songaday.Activities.AddSongActivity;
import com.yashdalfthegray.songaday.Activities.MainActivity;
import com.yashdalfthegray.songaday.Models.Song;
import com.yashdalfthegray.songaday.R;

import java.util.ArrayList;

/**
 * Created by Yash Kulshrestha on 8/4/2015.
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private ArrayList<Song> mSongList;

    public SongAdapter(ArrayList<Song> songs) {
        mSongList = songs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TextView titleView = (TextView)holder.view.getRootView().findViewById(R.id.title_view);
        TextView artistView = (TextView)holder.view.getRootView().findViewById(R.id.artist_view);
        TextView dateView = (TextView)holder.view.getRootView().findViewById(R.id.date_view);
        TextView genreView = (TextView)holder.view.getRootView().findViewById(R.id.genre_view);

        Button songEditButton = (Button)holder.view.getRootView().findViewById(R.id.song_edit_button);
        Button songLinkButton = (Button)holder.view.getRootView().findViewById(R.id.song_link_button);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            switch(v.getId()) {
                case R.id.song_edit_button:
                    Intent intent = new Intent(v.getContext(), AddSongActivity.class);
                    intent.putExtra(MainActivity.SONG_ACTIVITY_MODE, MainActivity.EDIT_MODE);
                    intent.putExtra(MainActivity.SONG_CONTENT, mSongList.get(position));
                    v.getContext().startActivity(intent);
                    break;
                case R.id.song_link_button:
                    v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mSongList.get(position).getLink())));
                    break;
                default:
                    Log.w("SongFragment", "Don't know what happened there!");
            }
            }
        };

        titleView.setText(mSongList.get(position).getTitle());
        artistView.setText("by " + mSongList.get(position).getArtist());
        dateView.setText("Posted " + mSongList.get(position).getDate());
        genreView.setText(mSongList.get(position).getGenre());

        songEditButton.setOnClickListener(onClickListener);
        songLinkButton.setOnClickListener(onClickListener);
    }

    public int getItemCount() {
        return mSongList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }
}
