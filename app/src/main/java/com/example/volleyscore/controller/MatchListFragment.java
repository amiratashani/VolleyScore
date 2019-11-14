package com.example.volleyscore.controller;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.volleyscore.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchListFragment extends Fragment {

    public static MatchListFragment newInstance() {

        Bundle args = new Bundle();

        MatchListFragment fragment = new MatchListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MatchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_match_list, container, false);
        return view;
    }

}
