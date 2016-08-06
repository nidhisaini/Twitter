package com.codepath.apps.twitter.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 *https://github.com/vinc3m1/RoundedImageView
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

private static class ViewHolder{
    TextView tvUserName;
    TextView tvBody;
    ImageView ivProfileImage;
    TextView tvCreatedAt;
}


    public TweetArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1 Get the tweet
        Tweet tweet = getItem(position);

        //2 Find and inflate the template
        ViewHolder viewHolder; // view lookup cache stored in tag

            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
                viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
                viewHolder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
                viewHolder.tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);
                viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
                // Cache the viewHolder object inside the fresh view
                convertView.setTag(viewHolder);
            }else{
                // View is being recycled, retrieve the viewHolder object from tag
                viewHolder = (ViewHolder) convertView.getTag();

                }
                viewHolder.tvUserName.setText(tweet.getUser().getName());
                viewHolder.tvBody.setText(tweet.getBody());
                String date = getRelativeTimeAgo(tweet.getCreatedAt());
                viewHolder.tvCreatedAt.setText(date);
                viewHolder.ivProfileImage.setImageResource(0);
                Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl())
                .transform(new RoundedCornersTransformation(1, 1))
                .into(viewHolder.ivProfileImage);


        //5 return to the view to be inserted into the list
        return convertView;
    }


    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return relativeDate;
    }


}
