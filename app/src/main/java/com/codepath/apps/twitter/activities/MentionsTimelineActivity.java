package com.codepath.apps.twitter.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.adapters.TweetArrayAdapter;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.utils.NetworkUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MentionsTimelineActivity extends AppCompatActivity {
    ListView lvMentionsTimeline;
    TwitterClient client;
    TweetArrayAdapter aTweets;
    ArrayList<Tweet> tweets;
    private SwipeRefreshLayout swipeRefreshMentionsTimeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentions_timeline);
        setupViews();
        client = TwitterApplication.getRestClient();



        //check network connection
        if (NetworkUtil.getInstance(this).isNetworkAvailable()) {
            Toast.makeText(this, "Connected to Internet", Toast.LENGTH_SHORT).show();
            populateList();

           /* swipeRefreshMentionsTimeline.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    populateList();
                }
            });
            swipeRefreshMentionsTimeline.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.primary, R.color.primary_dark);

            lvMentionsTimeline.setOnScrollListener(new EndlessScrollListener() {
                @Override
                public boolean onLoadMore(int page, int totalItemsCount) {
                    populateList();
                    return false;
                }
            });
*/
        } else {
            Toast.makeText(this, "Not connected to Internet", Toast.LENGTH_LONG).show();

        }

    }



    private void setupViews() {
        lvMentionsTimeline = (ListView) findViewById(R.id.lvMentionsTimeline);
        swipeRefreshMentionsTimeline = (SwipeRefreshLayout) findViewById(R.id.scMentionsTimeline);
        tweets = new ArrayList<>();
        aTweets = new TweetArrayAdapter(this, tweets);
        lvMentionsTimeline.setAdapter(this.aTweets);



    }

    private void populateList(){

        client.getMentionsTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Toast.makeText(MentionsTimelineActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", json.toString());
                tweets.clear();
                tweets.addAll(Tweet.fromJSONArray(json));
                aTweets.notifyDataSetChanged();
                Toast.makeText(MentionsTimelineActivity.this, "After Success", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MentionsTimelineActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }




}
