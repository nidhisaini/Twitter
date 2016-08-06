package com.codepath.apps.twitter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    private TwitterClient client;
    EditText etCompose;
    Button btnTweet;
    String status;
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        etCompose = (EditText) findViewById(R.id.etCompose);
        btnTweet = (Button) findViewById(R.id.btnTweet);

        client = TwitterApplication.getRestClient();//singleton client

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = etCompose.getText().toString();

                populateTweet(status);
            }

        });
        Log.d("DEBUG", "outside onclick");

    }

    private void populateTweet(String status){
        if (status != "") {
            tweet = new Tweet();
            tweet.setBody(status);


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

        }else{
            Toast.makeText(ComposeActivity.this, "Enter a tweet", Toast.LENGTH_LONG).show();
            //btnTweet.
        }

    }
}


