package com.codepath.apps.twitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.adapters.TweetArrayAdapter;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.utils.EndlessScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    private TwitterClient client;
    TweetArrayAdapter aTweets;
    ArrayList<Tweet> tweets;
    ListView lvTweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        //find listview
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        //create array list
        tweets = new ArrayList<>();
        //construct adapter from data source
        aTweets = new TweetArrayAdapter(this, tweets);
        //connect adadpter to the listview
        lvTweets.setAdapter(aTweets);
        //get the client
        client = TwitterApplication.getRestClient();//singleton client
        populateTimeline();

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                return false;
            }
        });
    }

    //send the timeline request to get the timeline json
    //fill listview by creating the tweet object from json
    private void populateTimeline() {
        //get client
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            //success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                Log.d("DEBUG", json.toString());

                //DESERIALIZE JSON
                //CREATE MODELS and them to adapter
                //LOAD THE MODEL DATA INTO THE LISTVIEW
                aTweets.addAll(Tweet.fromJSONArray(json));
                Log.d("DEBUG", aTweets.toString());
            }

            //failure

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                Log.d("DEBUG", errorResponse.toString());
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_compose:
                composeTweet();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void composeTweet() {

        Intent intent = new Intent(TimelineActivity.this, ComposeActivity.class);
        startActivity(intent);
    }
}