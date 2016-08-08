package com.codepath.apps.twitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.adapters.TweetArrayAdapter;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.utils.EndlessScrollListener;
import com.codepath.apps.twitter.utils.NetworkUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/*http://guides.codepath.com/android/Implementing-Pull-to-Refresh-Guide
https://gist.github.com/voltazor/9937690*/

public class TimelineActivity extends AppCompatActivity {
    private TwitterClient client;
    TweetArrayAdapter aTweets;
    ArrayList<Tweet> tweets;
    ListView lvTweets;
    private final int REQUEST_CODE = 20;
    private SwipeRefreshLayout swipeRefreshTimeline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timeline);

        setupViews();

        //check network connection
        if (NetworkUtil.getInstance(this).isNetworkAvailable()) {
            Toast.makeText(this, "Connected to Internet", Toast.LENGTH_SHORT).show();

            populateTimeline(0);

            swipeRefreshTimeline.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    populateTimeline(0);
                }
            });

            swipeRefreshTimeline.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.primary, R.color.primary_dark);

            lvTweets.setOnScrollListener(new EndlessScrollListener() {
                @Override
                public boolean onLoadMore(int page, int totalItemsCount) {

                    populateTimeline(page);
                    return false;
                }
            });


        } else {
            Toast.makeText(this, "Not connected to Internet", Toast.LENGTH_LONG).show();

            List<Tweet> oldtweets = Tweet.getAll();
            aTweets.addAll((ArrayList<Tweet>) oldtweets);

        }
    }

    private void setupViews() {
        //find listview
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        swipeRefreshTimeline =(SwipeRefreshLayout) findViewById(R.id.scTimeline);

        //create array list
        tweets = new ArrayList<>();
        //construct adapter from data source
        aTweets = new TweetArrayAdapter(this, tweets);
        //connect adadpter to the listview
        lvTweets.setAdapter(this.aTweets);
        //get the client
        client = TwitterApplication.getRestClient();//singleton client
    }


    //send the timeline request to get the timeline json
    //fill listview by creating the tweet object from json
    private void populateTimeline(int page) {
        //get client
        client.getHomeTimeline(page, new JsonHttpResponseHandler() {
            //success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                tweets.clear();
                tweets.addAll(Tweet.fromJSONArray(json));
               // aTweets.addAll(Tweet.fromJSONArray(json));
                aTweets.notifyDataSetChanged();

            }
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
            case R.id.action_profile:
                userProfile();
                return true;
            case R.id.action_mentions:
                mentions();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void mentions() {
        Intent intent = new Intent(TimelineActivity.this, MentionsTimelineActivity.class);
        startActivity(intent);
    }


    private void userProfile() {
        Intent intent = new Intent(TimelineActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void composeTweet() {
        Intent intent = new Intent(TimelineActivity.this, ComposeActivity.class);
        intent.putExtra("text", 2);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String status = data.getExtras().getString("status");
           // Tweet.fromJSON(status)

            /*client.postTweet(status, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                    Log.d("DEBUG", "Success");
                    Toast.makeText(TimelineActivity.this, "Success", Toast.LENGTH_LONG).show();
                    tweets.addAll(Tweet.fromJSONArray(json));
                    aTweets.notifyDataSetChanged();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("DEBUG", "Failure");
                    Toast.makeText(TimelineActivity.this, "Failure", Toast.LENGTH_LONG).show();
                }
            });*/

        }


    }
}