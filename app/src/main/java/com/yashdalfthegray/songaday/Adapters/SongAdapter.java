package com.yashdalfthegray.songaday.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        titleView.setText(mSongList.get(position).getTitle());
    }

    @Override
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
