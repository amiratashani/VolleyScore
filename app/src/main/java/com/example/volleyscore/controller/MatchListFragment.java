package com.example.volleyscore.controller;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.volleyscore.MatchAdapter;
import com.example.volleyscore.R;
import com.example.volleyscore.Utility.Utils;
import com.example.volleyscore.model.Match;
import com.example.volleyscore.volley.MyVolley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchListFragment extends Fragment {

    public static final String TAG_ERROR_REQUEST = "TAG_ERROR_REQUEST";
    private static final String REQUEST_GET_ALL_MATCH = "RequestGetAllMatch";
    private RecyclerView mRecyclerViewMatchList;
    private MatchAdapter mMatchAdapter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_list, container, false);

        initUi(view);
        getMatchs();

        return view;
    }

    private void initUi(View view) {
        mRecyclerViewMatchList = view.findViewById(R.id.recycle_view_match_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerViewMatchList.setLayoutManager(gridLayoutManager);


    }

    private void setupAdapter(List<Match> matches) {
        if (mMatchAdapter == null) {
            mMatchAdapter = new MatchAdapter(matches, getActivity());
            mRecyclerViewMatchList.setAdapter(mMatchAdapter);
        } else {
            mMatchAdapter.setMatch(matches);
            mMatchAdapter.notifyDataSetChanged();
        }
    }

    public void getMatchs() {
        String url = Utils.generateURl();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                url, null, response -> {

            List<Match> matches = new Gson().fromJson(response.toString(), new TypeToken<List<Match>>() {
            }.getType());

            setupAdapter(matches);

        },
                error -> {
                    Log.i(TAG_ERROR_REQUEST, error.toString());

                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }

            @Override
            public byte[] getBody() {
                return super.getBody();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };

        request.setTag(REQUEST_GET_ALL_MATCH);
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyVolley.getInstance().addToRequestQueue(request);

    }

    @Override
    public void onStop() {
        super.onStop();
        MyVolley.getInstance().getRequestQueue().cancelAll(REQUEST_GET_ALL_MATCH);
    }
}
