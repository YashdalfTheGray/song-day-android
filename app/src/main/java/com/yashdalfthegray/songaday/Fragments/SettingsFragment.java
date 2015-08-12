package com.yashdalfthegray.songaday.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yashdalfthegray.songaday.R;

import java.util.logging.SocketHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    View inflatedView;

    private SwitchCompat mSwitchCompat;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflatedView = inflater.inflate(R.layout.fragment_settings, container, false);

        SharedPreferences settings = getActivity().getPreferences(Context.MODE_PRIVATE);

        mSwitchCompat = (SwitchCompat)inflatedView.findViewById(R.id.enable_player);
        mSwitchCompat.setChecked(settings.getBoolean("enable_player", true));

        return this.inflatedView;
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences settings = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("enable_player", mSwitchCompat.isChecked());

        editor.commit();
    }

}
