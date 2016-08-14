package com.codepath.apps.twitter.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.fragment.UserTimelinefragment;
import com.codepath.apps.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;
    User user;
    ImageView ivProfileImage;
    TextView tvProfileName;
    TextView tvTagLine;
    TextView tvfollowers;
    TextView tvfriends;
    ImageView ivBackgroundImage;
    String screenName;
    //TextView

    /*ListView lvMentionsTimeline;

    TweetArrayAdapter aTweets;
    ArrayList<Tweet> tweets;*/
    /*private SwipeRefreshLayout swipeRefreshMentionsTimeline;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.abc_ic_ab_back_material, null));
        else
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.abc_ic_ab_back_material));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setupViews();
        user = (User) this.getIntent().getSerializableExtra("user");
        screenName = (String) this.getIntent().getSerializableExtra("screen_name");

        if(user != null) {
            getSupportActionBar().setTitle("@" + user.getScreenName());
            populateUserprofile(user);
        }
       else {
            //get client
            client = TwitterApplication.getRestClient();
            client.getUserProfile(new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //my current user account info
                    user = User.fromJSON(response);
                    getSupportActionBar().setTitle("@" + user.getScreenName());
                    populateUserprofile(user);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(ProfileActivity.this, "Network request failure", Toast.LENGTH_SHORT).show();

                }
            });
        }

        //get screen name
        //String screenName = getIntent().getStringExtra("screen_name");
        //Create the user timeline
        UserTimelinefragment userTimelinefragment = UserTimelinefragment.newInstance(screenName);
        //Dispaly user fragment within this Activity (dynamically)
       if(savedInstanceState == null){
           FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
           ft.replace(R.id.flCointainer, userTimelinefragment);
           ft.commit();
       }

}

    private void setupViews() {
        ivProfileImage = (ImageView) findViewById(R.id.ivRetweetProfileImage);
        tvProfileName = (TextView) findViewById(R.id.tvProfileName);
        tvTagLine = (TextView) findViewById(R.id.tvTagLine);
        tvfriends = (TextView) findViewById(R.id.tvfriends);
        tvfollowers = (TextView) findViewById(R.id.tvfollowers);
        ivBackgroundImage =(ImageView) findViewById(R.id.ivBackgroundImage);
    }

    private void populateUserprofile(User user) {

        tvProfileName.setText(user.getName());
        tvTagLine.setText(user.getTagLine());
        Picasso.with(this)
                .load(user.getProfileImageUrl())
                .fit()
                .transform(new RoundedCornersTransformation(10, 10))
                .into(ivProfileImage);
        tvfollowers.setText(user.getFollowers_count() + " FOLLOWERS");
        tvfriends.setText(user.getFriends_count() + " FOLLOWING");
        if(user.getProfileBackgroundImageUrl() != null) {
            Picasso.with(this)
                    .load(user.getProfileBackgroundImageUrl())
                    .fit()
                    .transform(new RoundedCornersTransformation(2, 2))
                    .into(ivBackgroundImage);
        }
        else{
            Picasso.with(this)
                    .load(user.getProfileBackgroundImageUrl())
                    .fit()
                    .transform(new RoundedCornersTransformation(2, 2))
                    .into(ivBackgroundImage);
        }

    }
}
