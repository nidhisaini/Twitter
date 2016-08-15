package com.codepath.apps.twitter.models;

import android.text.format.DateUtils;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * [
 * {
 * "text": "just another test",
 * "contributors": null,
 * "id": 240558470661799936,
 * "retweet_count": 0,
 * "in_reply_to_status_id_str": null,
 * "geo": null,
 * "retweeted": false,
 * "in_reply_to_user_id": null,
 * "place": null,
 * "source": "<a href="//realitytechnicians.com%5C%22" rel="\"nofollow\"">OAuth Dancer Reborn</a>",
 * "user": {
 * "name": "OAuth Dancer",
 * "profile_sidebar_fill_color": "DDEEF6",
 * "profile_background_tile": true,
 * "profile_sidebar_border_color": "C0DEED",
 * "profile_image_url": "http://a0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
 * "created_at": "Wed Mar 03 19:37:35 +0000 2010",
 * "location": "San Francisco, CA",
 * "follow_request_sent": false,
 * "id_str": "119476949",
 * "is_translator": false,
 * "profile_link_color": "0084B4",
 * "entities": {
 * "url": {
 * "urls": [
 * {
 * "expanded_url": null,
 * "url": "http://bit.ly/oauth-dancer",
 * "indices": [
 * 0,
 * 26
 * ],
 * "display_url": null
 * }
 * ]
 * },
 * "description": null
 * },
 * "default_profile": false,
 * "url": "http://bit.ly/oauth-dancer",
 * "contributors_enabled": false,
 * "favourites_count": 7,
 * "utc_offset": null,
 * "profile_image_url_https": "https://si0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
 * "id": 119476949,
 * "listed_count": 1,
 * "profile_use_background_image": true,
 * "profile_text_color": "333333",
 * "followers_count": 28,
 * "lang": "en",
 * "protected": false,
 * "geo_enabled": true,
 * "notifications": false,
 * "description": "",
 * "profile_background_color": "C0DEED",
 * "verified": false,
 * "time_zone": null,
 * "profile_background_image_url_https": "https://si0.twimg.com/profile_background_images/80151733/oauth-dance.png",
 * "statuses_count": 166,
 * "profile_background_image_url": "http://a0.twimg.com/profile_background_images/80151733/oauth-dance.png",
 * "default_profile_image": false,
 * "friends_count": 14,
 * "following": false,
 * "show_all_inline_media": false,
 * "screen_name": "oauth_dancer"
 * },
 * "in_reply_to_screen_name": null,
 * "in_reply_to_status_id": null
 * }
 */
@Table(name = "tweet")@Parcel
public class Tweet extends Model {
    //list out attributes
    @Column(name = "tweet_body")
    private String body;
    @Column(name = "tweet_id")//, index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE
    private long tid;
    @Column(name = "tweet_User")//,, onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)//
    private User user;//store embedded user object
    @Column(name = "tweet_created_at")
    private String createdAt;
    @Column(name = "tweet_created_at")
    private Boolean favorited;



    @Column(name = "tweet_created_at")
    private Integer favorite_count;



   /* @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long remoteId;
*/
    //getters
   /* public long getRemoteId() {
        return remoteId;
    }*/
    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getTid() {
        return tid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    //setters
    public void setBody(String body) {
        this.body = body;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFavorite_count() {
        return favorite_count;
    }

    public Boolean getFavorited() {
        return favorited;
    }






   //Empty constructor for ActiveAndroid
    public Tweet() {
        super();
    }

    /*public Tweet(String body, long tid, User user, String createdAt, long remoteId) {
        this.body = body;
        this.tid = tid;
        this.user = user;
        this.createdAt = createdAt;
       *//* this.remoteId = remoteId;*//*
    }*/

    //deserialize json and turn it into java obj(tweet obj)
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        try {
            //extract the value from json, store them
            tweet.body = jsonObject.getString("text");
            tweet.tid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.favorite_count =jsonObject.getInt("favourites_count");
            tweet.favorited =jsonObject.getBoolean("favorited");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return tweet object
        return tweet;
    }

    //pass in Tweet.fromJSONArray[{ }, { }, { }]  ==> List<Tweet>
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray){
        ArrayList<Tweet> tweets = new  ArrayList<>();
        JSONObject tweetJson;

        for(int i = 0; i< jsonArray.length(); i++ ){
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
                Tweet tweet = Tweet.fromJSON(tweetJson);
            if (tweet != null) {
                tweet.getUser().save();
                tweet.save();
                tweets.add(tweet);
            }

        }


        return tweets;
    }


    // Optional helper method for ActiveAndroid to establish a direct relationship with the Users table
    public List<User> getUsers() {
        return getMany(User.class, "Tweets");
    }



    // Used to return items from another table based on the foreign key
    public static List<Tweet> getAll() {

        return new Select().from(Tweet.class).orderBy("tweet_created_at DESC").execute();
    }

    public String setCreatedAtNow() {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        return simpleDateFormat.format(System.currentTimeMillis());
    }


    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    //  "created_at": "Wed Mar 07 22:23:19 +0000 2007"
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeTime = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeTime = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            String[] pieces = relativeTime.split(" ");
            if (pieces.length < 2) {
                Log.d("DEBUG", "No relative time to return.");
            } else {
                String number = pieces[0];
                char letter = pieces[1].charAt(0);
                relativeTime = number + Character.toString(letter);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeTime;
    }

    public void copyFrom(Tweet tweet) {
        this.favorite_count = tweet.getFavorite_count();
        this.favorited = tweet.getFavorited();
        /*this.retweet_count = tweet.getRetweet_count();
        this.retweeted = tweet.getRetweeted();*/
    }




}
