package com.example.volleyscore.Utility;

import android.net.Uri;

public class Utils {
    private static final String API_KEY = "ad3145cbd75737086986d91ebf81efca0b2359f8418d5c17c5b6b5dfe4434804";
    private static final String FOOTBALL_REST_PATH = "https://apiv2.apifootball.com";
    private static final String METHOD_GET_PREDICTIONS = "get_predictions";
    private static final String METHOD_GET_TEAMS = "get_teams";

    private static Uri baseUri = Uri.parse(FOOTBALL_REST_PATH)
            .buildUpon()
            .appendQueryParameter("APIkey", API_KEY)
            .build();

    public static String generateURl() {
        Uri uri = baseUri.buildUpon()
                .appendQueryParameter("action", METHOD_GET_PREDICTIONS)
                .appendQueryParameter("from", "2019-11-04")
                .appendQueryParameter("to", "2019-11-09")
                .appendQueryParameter("league_id", "248")
                .build();
        return uri.toString();
    }
    public static String generateURl(String teamId) {
        Uri uri = baseUri.buildUpon()
                .appendQueryParameter("action", METHOD_GET_TEAMS)
                .appendQueryParameter("team_id", teamId)
                .build();
        return uri.toString();
    }
}
