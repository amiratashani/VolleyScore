package com.example.volleyscore.model;

import com.google.gson.annotations.SerializedName;

public class Match {

    @SerializedName("match_id")
    private String mId;
    @SerializedName("match_hometeam_id")
    private String mHomeId;
    @SerializedName("match_hometeam_name")
    private String mHomeTeamName;
    @SerializedName("match_hometeam_score")
    private String mHomeScore;
    @SerializedName("match_awayteam_id")
    private String mAwayId;
    @SerializedName("match_awayteam_name")
    private String mAwayTeamName;
    @SerializedName("match_awayteam_score")
    private String mAwayScore;


    public String getId() {
        return mId;
    }

    public String getHomeId() {
        return mHomeId;
    }

    public void setHomeId(String homeId) {
        mHomeId = homeId;
    }

    public String getHomeTeamName() {
        return mHomeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        mHomeTeamName = homeTeamName;
    }

    public String getHomeScore() {
        return mHomeScore;
    }

    public void setHomeScore(String homeScore) {
        mHomeScore = homeScore;
    }

    public String getAwayId() {
        return mAwayId;
    }

    public void setAwayId(String awayId) {
        mAwayId = awayId;
    }

    public String getAwayTeamName() {
        return mAwayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        mAwayTeamName = awayTeamName;
    }

    public String getAwayScore() {
        return mAwayScore;
    }

    public void setAwayScore(String awayScore) {
        mAwayScore = awayScore;
    }
}




