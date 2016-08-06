package com.codepath.apps.twitter.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by NidhiSaini on 8/4/16.
 */
public class User {

    //list the attributes
    private long uid;
    private String name;
    private String screenName;
    private String profileImageUrl;

    //getters
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


    //deserialize the json obj into java object (user obj.)
    public static User fromJSON(JSONObject jsonObject) {
        User u = new User();
        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.profileImageUrl = jsonObject.getString("profile_image_url");
            u.screenName = jsonObject.getString("screen_name");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }

}
