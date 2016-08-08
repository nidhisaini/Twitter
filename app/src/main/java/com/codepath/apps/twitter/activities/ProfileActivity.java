package com.codepath.apps.twitter.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.adapters.TweetArrayAdapter;
import com.codepath.apps.twitter.models.CurrentUser;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.models.User;
import com.codepath.apps.twitter.utils.NetworkUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity {
    ImageView ivProfileImage;
    TextView tvProfileName;
    TextView tvProfileScreenName;
    TwitterClient client;
    TextView tvfollowers;
    TextView tvfriends;

    ListView lvMentionsTimeline;

    TweetArrayAdapter aTweets;
    ArrayList<Tweet> tweets;
    private SwipeRefreshLayout swipeRefreshMentionsTimeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //onLoad();
        client = TwitterApplication.getRestClient();

        setupViews();

        //check network connection
        if (NetworkUtil.getInstance(this).isNetworkAvailable()) {
            Toast.makeText(this, "Connected to Internet", Toast.LENGTH_SHORT).show();
            client.getUserProfile(new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    User currentUser = User.fromJSON(response);
                    populateprofile(currentUser);
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
            });*/
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                   Toast.makeText(ProfileActivity.this, "Network request failure", Toast.LENGTH_SHORT).show();

                }
            });

        }
        else{
            Toast.makeText(this, "Not connected to Internet", Toast.LENGTH_LONG).show();

        }

    }

   /* private void onLoad() {
        CurrentUser currentUser = CurrentUser.UseInfo();
        if(currentUser != null)

            tvProfileName.setText(currentUser.getCname());

    }*/

    private void populateprofile(User user) {

        tvProfileName.setText(user.getName());
        tvProfileScreenName.setText(user.getScreenName());
        Picasso.with(this)
                .load(user.getProfileImageUrl())
                .fit()
                .transform(new RoundedCornersTransformation(1, 1))
                .into(ivProfileImage);
        tvfollowers.setText(user.getFollowers_count() + " FOLLOWERS");
        tvfriends.setText(user.getFriends_count() + " FRIENDS");

        CurrentUser cuser = new CurrentUser(user.getName());
        cuser.save();


    }

    private void setupViews() {
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvProfileName = (TextView) findViewById(R.id.tvProfileName);
        tvProfileScreenName = (TextView) findViewById(R.id.tvProfileScreenName);
        tvfriends = (TextView) findViewById(R.id.tvfriends);
        tvfollowers = (TextView) findViewById(R.id.tvfollowers);
        lvMentionsTimeline = (ListView) findViewById(R.id.lvMentionsTimeline);
        swipeRefreshMentionsTimeline = (SwipeRefreshLayout) findViewById(R.id.scMentionsTimeline);
        tweets = new ArrayList<>();
        aTweets = new TweetArrayAdapter(this, tweets);
        lvMentionsTimeline.setAdapter(this.aTweets);
    }

    private void populateList(){

        client.getMentionsTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", json.toString());
                tweets.clear();
                tweets.addAll(Tweet.fromJSONArray(json));
                aTweets.notifyDataSetChanged();
                Toast.makeText(ProfileActivity.this, "After Success", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(ProfileActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
