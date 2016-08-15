package com.codepath.apps.twitter.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.activities.ProfileActivity;
import com.codepath.apps.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
    private TwitterClient client;
    private static class ViewHolder{
        TextView tvUserName;
        TextView tvBody;
        ImageView ivProfileImage;
        TextView tvCreatedAt;
        ImageButton ibRetweet;
        ImageButton ibHeart;

    }


    public TweetArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1 Get the tweet
        final Tweet tweet = getItem(position);

        //2 Find and inflate the template
        final ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvretweetUserName);
            viewHolder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);
            viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivRetweetProfileImage);
            viewHolder.ibRetweet =(ImageButton) convertView.findViewById(R.id.ibRetweet);
            viewHolder.ibHeart = (ImageButton) convertView.findViewById(R.id.ibHeart);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }else{
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.tvUserName.setText(tweet.getUser().getName());
        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.tvCreatedAt.setText(Tweet.getRelativeTimeAgo(tweet.getCreatedAt()));
        viewHolder.ivProfileImage.setImageResource(0);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl())
                .transform(new RoundedCornersTransformation(2, 2))
                .into(viewHolder.ivProfileImage);

        viewHolder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ProfileActivity.class);
                i.putExtra("user", tweet.getUser());
                i.putExtra("screen_name", tweet.getUser().getScreenName());
                view.getContext().startActivity(i);


            }
        });

        viewHolder.ibRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client = TwitterApplication.getRestClient();
                client.getRetweet(tweet.getTid(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                });

            }
        });

        viewHolder.ibHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client = TwitterApplication.getRestClient();
                client.favorite(tweet.getTid(), tweet.getFavorited() , new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                      // tweet.copyFrom(response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }

                });

            }
        });




        //5 return to the view to be inserted into the list
        return convertView;
    }




}

