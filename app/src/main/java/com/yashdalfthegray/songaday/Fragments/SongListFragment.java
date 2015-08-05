package com.yashdalfthegray.songaday.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yashdalfthegray.songaday.Activities.MainActivity;
import com.yashdalfthegray.songaday.Adapters.SongAdapter;
import com.yashdalfthegray.songaday.Models.Song;
import com.yashdalfthegray.songaday.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongListFragment extends Fragment {

    public ArrayList<Song> songList;

    View inflatedView;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FloatingActionButton addFab;

    public SongListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflatedView = inflater.inflate(R.layout.fragment_song_list, container, false);

        songList = this.getArguments().getParcelableArrayList("songList");

        mRecyclerView = (RecyclerView)inflatedView.findViewById(R.id.song_recycler);
        mAdapter = new SongAdapter(songList);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        addFab = (FloatingActionButton)inflatedView.findViewById(R.id.add_fab);
        addFab.setOnClickListener(onClickListener);

        return this.inflatedView;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.add_fab:
                    Log.d("SongListFragment", "Add button pressed");
                    break;
                default:
                    Log.w("SongListFragment", "Don't know what happened there!");
            }
        }
    };
}
