package com.codepath.apps.twitter.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.models.CurrentUser;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.utils.NetworkUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/*http://stackoverflow.com/questions/3013791/live-character-count-for-edittext
* https://developer.android.com/training/keyboard-input/visibility.html*/
public class ComposeActivity extends AppCompatActivity {
    private TwitterClient client;
    TextView tvCharCount;
    EditText etCompose;
    Button btnTweet;
    String status;
    Tweet tweet;
    TextView tvUserName;
    ImageView ivUserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.abc_ic_ab_back_material, null));
        else
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.abc_ic_ab_back_material));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CurrentUser cuser = new CurrentUser();
        String username = cuser.getCname();
        //String profileUrl = user.getProfileImageUrl();
        //getArgument



        if(username != null){
            tvUserName = (TextView) findViewById(R.id.tvretweetUserName);
            tvUserName.setText(username);
        }

      /*  if(profileUrl != null){
            ivUserImage = (ImageView) findViewById(R.id.ivRetweetProfileImage);
            Picasso.with(this)
                    .load(profileUrl)
                    .fit()
                    .transform(new RoundedCornersTransformation(1, 1))
                    .into(ivUserImage);
        }*/


        etCompose = (EditText) findViewById(R.id.etCompose);
        btnTweet = (Button) findViewById(R.id.btnTweet);
        tvCharCount = (TextView) findViewById(R.id.tvCharCount);

        client = TwitterApplication.getRestClient();//singleton client

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = etCompose.getText().toString();

                populateTweet(status);

                Intent intent = new Intent(ComposeActivity.this, TimelineActivity.class);
                startActivity(intent);

            }

        });
        Log.d("DEBUG", "outside onclick");

        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvCharCount.setText(140 - editable.toString().length() + "/140");
            }
        });
        
        onLoad();

    }

    private void onLoad() {
       /* Tweet tweekval = Tweet.getAll();
        if(tweekval != null){
            etCompose.setText(tweekval.getBody());
        }*/
    }

    //store the value from edittext to disk
    public void onPersist(View v){
       /* Tweet tweetVal = new Tweet(etCompose.getText().toString());
        tweetVal.save();
        Toast.makeText(this, "Save to data base", Toast.LENGTH_SHORT).show();*/

    }


    private void populateTweet(String status){

        if (NetworkUtil.getInstance(this).isNetworkAvailable()) {
            Toast.makeText(ComposeActivity.this, "Connected to Internet", Toast.LENGTH_SHORT).show();

            if (status != "") {
                tweet = new Tweet();
                tweet.setBody(status);
                tweet.setCreatedAt(tweet.setCreatedAtNow());
                tweet.save();
                // tweet.getUser(tweet.getUser().)
                tweet.save();
                client.postTweet(status, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        Log.d("DEBUG", "Success");
                        Toast.makeText(ComposeActivity.this, "Success", Toast.LENGTH_LONG).show();
                        tweet = Tweet.fromJSON(response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("DEBUG", "Failure");
                        Toast.makeText(ComposeActivity.this, "Failure", Toast.LENGTH_LONG).show();
                    }
                });

                Intent i = new Intent();
                i.putExtra("status", status);
                setResult(RESULT_OK, i);
                finish();

            } else {
                Toast.makeText(ComposeActivity.this, "Enter a tweet", Toast.LENGTH_LONG).show();
                //btnTweet.
            }
        }

    }
}


