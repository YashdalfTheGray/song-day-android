package com.yashdalfthegray.songaday.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.yashdalfthegray.songaday.Activities.AddSongActivity;
import com.yashdalfthegray.songaday.Activities.MainActivity;
import com.yashdalfthegray.songaday.Adapters.SongAdapter;
import com.yashdalfthegray.songaday.Models.Song;
import com.yashdalfthegray.songaday.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongListFragment extends Fragment implements ValueEventListener {

    public Firebase songsDb;
    public ValueEventListener childEventListener;
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

        songList = new ArrayList<>();

        songsDb = new Firebase(MainActivity.DB_URL);

        mRecyclerView = (RecyclerView)inflatedView.findViewById(R.id.song_recycler);
        mAdapter = new SongAdapter(songList);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        addFab = (FloatingActionButton)inflatedView.findViewById(R.id.add_fab);
        addFab.setOnClickListener(onClickListener);

        return this.inflatedView;
    }

    @Override
    public void onResume() {
        super.onResume();
        childEventListener = songsDb.addValueEventListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        songsDb.removeEventListener(childEventListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_fab:
                Intent intent = new Intent(getActivity(), AddSongActivity.class);
                intent.putExtra(MainActivity.SONG_ACTIVITY_MODE, MainActivity.ADD_MODE);
                getActivity().startActivity(intent);
                break;
            default:
                Log.w("SongListFragment", "Don't know what happened there!");
        }
        }
    };

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        int i = 0;

        Log.i("SongListFragment", "Found " + dataSnapshot.getChildrenCount() + " children in the database!");

        for (DataSnapshot songSnapshot : dataSnapshot.getChildren()) {
            songList.add(songSnapshot.getValue(Song.class));
            songList.get(i).setKey(songSnapshot.getKey());
            i++;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        Log.w("SongListFragment", "Firebase read error: " + firebaseError.getMessage());
    }
}
