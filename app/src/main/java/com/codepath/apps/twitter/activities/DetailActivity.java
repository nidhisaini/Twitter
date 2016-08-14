package com.codepath.apps.twitter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.Tweet;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailActivity extends AppCompatActivity {
    Tweet posTweet;
    ImageView ivRetweetProfileImage;
    TextView tvretweetUserName;
    TextView tvRetweetBody;
    TextView tvRetweetCreatedAt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        posTweet = (Tweet)getIntent().getSerializableExtra("posTweet");

        ivRetweetProfileImage = (ImageView) findViewById(R.id.ivRetweetProfileImage);
        tvretweetUserName = (TextView) findViewById(R.id.tvretweetUserName);
        tvRetweetBody = (TextView) findViewById(R.id.tvRetweetBody);
        tvRetweetCreatedAt = (TextView) findViewById(R.id.tvRetweetCreatedAt);

        tvretweetUserName.setText(posTweet.getUser().getName());
        tvRetweetBody.setText(posTweet.getBody());
        tvRetweetCreatedAt.setText(posTweet.getCreatedAt());

        Picasso.with(DetailActivity.this).load(posTweet.getUser().getProfileImageUrl())
                .transform(new RoundedCornersTransformation(2, 2))
                .into(ivRetweetProfileImage);


    }
}
