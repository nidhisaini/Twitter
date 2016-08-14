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

public class HomeTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get client
        client = TwitterApplication.getRestClient();//singleton client
        populateTimeline(0);

    }


    //send the timeline request to get the timeline json
    //fill listview by creating the tweet object from json
    private void populateTimeline(int page) {
        long max_id = 1;
        long since_id = 1;

        if (page == 0) {
            tweets.clear();
        }
        if (!tweets.isEmpty()) {
            max_id = Long.valueOf(((Tweet) tweets.get(0)).getTid()) - 1;
            since_id = Long.valueOf(((Tweet) tweets.get(tweets.size() - 1)).getTid());

        }
        //get client
        client.getHomeTimeline(max_id, since_id, page, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("Success", json.toString());
                addAll(Tweet.fromJSONArray(json));

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // Toast.makeText(HomeTimelineFragment.this, errorResponse.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Failure", errorResponse.toString());
            }
        });
    }




}
