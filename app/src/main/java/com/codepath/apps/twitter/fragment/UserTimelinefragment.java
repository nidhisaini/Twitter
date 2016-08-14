package com.codepath.apps.twitter.fragment;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by NidhiSaini on 8/12/16.
 */
public class UserTimelinefragment extends TweetsListFragment {
    private TwitterClient client;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get client
        client = TwitterApplication.getRestClient();//singleton client
        populateTimeline();

    }

    public static UserTimelinefragment newInstance(String screen_name) {
        UserTimelinefragment userTimelinefragment = new UserTimelinefragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        userTimelinefragment.setArguments(args);
        return userTimelinefragment;
    }

    //send the mentionstimeline request to get the timeline json
    //fill listview by creating the tweet object from json
    private void populateTimeline() {
        String screenName = getArguments().getString("screen_name");

        client.getUserTimeline(screenName, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("Success", json.toString());
                addAll(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Failure", errorResponse.toString());
            }
        });

    }
}
