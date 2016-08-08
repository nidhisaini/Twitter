package com.codepath.apps.twitter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by NidhiSaini on 8/4/16.
 */

@Table(name = "user")
public class User extends Model {

    //list the attributes
    @Column(name = "user_id", index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long uid;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_screen_name")
    private String screenName;
    @Column(name = "user_profile_image_url")
    private String profileImageUrl;
    @Column(name = "use_followers_count")
    private int followers_count;
    @Column(name = "followers_count")
    private int friends_count;
    /*// This is the unique id given by the server
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;
*/

   /* //getters
    public long getRemoteId() {
        return remoteId;
    }*/
    public long getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public int getFollowers_count() {
        return followers_count;
    }


    public User() {
        super();
    }


    public User(long uid, String name, String screenName, String profileImageUrl, int followers_count, int friends_count) {
        this.uid = uid;
        this.name = name;
        this.screenName = screenName;
        this.profileImageUrl = profileImageUrl;
        this.followers_count = followers_count;
        this.friends_count = friends_count;
    }

    //deserialize the json obj into java object (user obj.)
    public static User fromJSON(JSONObject jsonObject) {
        User u = new User();
        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.profileImageUrl = jsonObject.getString("profile_image_url");
            u.screenName = jsonObject.getString("screen_name");
            u.followers_count = jsonObject.getInt("followers_count");
            u.friends_count = jsonObject.getInt("friends_count");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;


    }

    public static User queryUseInfo(){
     //   return new Select().from(User.class).where("uid= ?", currentUid).execute();
        return null;
    }




}
