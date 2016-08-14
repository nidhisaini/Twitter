package com.codepath.apps.twitter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get client
        client = TwitterApplication.getRestClient();//singleton client
        populateTimeline(0);

    }

    //send the mentionstimeline request to get the timeline json
    //fill listview by creating the tweet object from json
    private void populateTimeline(int page) {
        client.getMentionsTimeline(page, new JsonHttpResponseHandler(){

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
