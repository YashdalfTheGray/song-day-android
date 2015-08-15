package com.yashdalfthegray.songaday.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView titleView = (TextView)holder.view.getRootView().findViewById(R.id.title_view);
        TextView artistView = (TextView)holder.view.getRootView().findViewById(R.id.artist_view);
        TextView dateView = (TextView)holder.view.getRootView().findViewById(R.id.date_view);
        TextView genreView = (TextView)holder.view.getRootView().findViewById(R.id.genre_view);

        titleView.setText(mSongList.get(position).getTitle());
        artistView.setText("by " + mSongList.get(position).getArtist());
        dateView.setText("Posted " + mSongList.get(position).getDate());
        genreView.setText(mSongList.get(position).getGenre());
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;

            Button songEditButton = (Button)view.getRootView().findViewById(R.id.song_edit_button);
            Button songLinkButton = (Button)view.getRootView().findViewById(R.id.song_link_button);

            songEditButton.setOnClickListener(this);
            songLinkButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.song_edit_button:
                    Log.d("SongAdapter", "Edit button pressed at position " + getLayoutPosition());
                    break;
                case R.id.song_link_button:
                    Log.d("SongAdapter", "Link button pressed at position " + getLayoutPosition());
                    break;
                default:
                    Log.w("SongFragment", "Don't know what happened there!");
            }
        }
    }
}
