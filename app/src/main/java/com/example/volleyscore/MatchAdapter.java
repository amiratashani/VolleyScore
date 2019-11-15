package com.example.volleyscore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.volleyscore.Utility.Utils;
import com.example.volleyscore.model.Match;
import com.example.volleyscore.volley.MyVolley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter {

    private List<Match> mMatch;
    private Context mContext;
    private ImageLoader mImageLoader;

    public MatchAdapter(List<Match> matches, Context context) {
        mMatch = matches;
        mContext = context;
        mImageLoader=MyVolley.getInstance().getImageLoader();
    }

    public void setMatch(List<Match> match) {
        mMatch = match;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.match_holder, null, false);
        return new MatchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MatchHolder) {
            MatchHolder matchHolder = (MatchHolder) holder;
            matchHolder.bindHolder(mMatch.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mMatch.size();
    }


    public class MatchHolder extends RecyclerView.ViewHolder {
        Match mMatch;

        TextView mTextViewHomeName, mTextViewAwayName,
                mTextViewScoreHome, mTextViewScoreAway;
        ImageView mImageViewHome;
        NetworkImageView mNetworkImageViewAway;


        public MatchHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewHomeName = itemView.findViewById(R.id.text_view_home_name);
            mTextViewAwayName = itemView.findViewById(R.id.text_view_away_name);
            mTextViewScoreHome = itemView.findViewById(R.id.text_view_home_score);
            mTextViewScoreAway = itemView.findViewById(R.id.text_view_away_score);
            mImageViewHome = itemView.findViewById(R.id.image_view_home);
            mNetworkImageViewAway = itemView.findViewById(R.id.image_view_away);
        }

        void bindHolder(Match match) {
            mTextViewHomeName.setText(match.getHomeTeamName());
            mTextViewAwayName.setText(match.getAwayTeamName());
            mTextViewScoreHome.setText(match.getHomeScore());
            mTextViewScoreAway.setText(match.getAwayScore());
            getImageMatch(match.getHomeId(),match.getAwayId());

            mMatch = match;
        }

        private void getImageMatch(String idHOme, String idAway) {
            String URL = Utils.generateURl(idHOme);
            JsonArrayRequest requestHome = new JsonArrayRequest(Request.Method.GET, URL, null,
                    response -> {
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            String url = jsonObject.getString("team_badge");

                            ImageRequest imageRequest = new ImageRequest(url,
                                    responseImage -> {
                                        mImageViewHome.setImageBitmap(responseImage);
                                    },
                                    0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                                    error -> {
                                        Drawable drawableErr = mContext.getResources().getDrawable(R.drawable.ic_err);
                                        mImageViewHome.setImageDrawable(drawableErr);
                                    }
                            );
                            MyVolley.getInstance().addToRequestQueue(imageRequest);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Log.i("MyTa", error.toString())) {


            };

            URL = Utils.generateURl(idAway);
            JsonArrayRequest requestAway = new JsonArrayRequest(Request.Method.GET, URL, null,
                    response -> {
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            String url = jsonObject.getString("team_badge");

                            mImageLoader.get(url, ImageLoader.getImageListener(mNetworkImageViewAway,
                                    R.drawable.ic_def, R.drawable.ic_err));
                            mNetworkImageViewAway.setImageUrl(url,mImageLoader);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Log.i("MyTa", error.toString());
                    });


            requestHome.setRetryPolicy(new DefaultRetryPolicy(2 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestAway.setRetryPolicy(new DefaultRetryPolicy(2 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            MyVolley.getInstance().addToRequestQueue(requestHome);
            MyVolley.getInstance().addToRequestQueue(requestAway);
        }

    }
}
