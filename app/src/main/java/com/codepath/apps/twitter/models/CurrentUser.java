package com.codepath.apps.twitter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.parceler.Parcel;

/**
 * Created by NidhiSaini on 8/7/16.
 */
@Table(name = "CurrentUser")@Parcel
public class CurrentUser extends Model {
    @Column(name = "cuser_name")
    private String cname;
    /*@Column(name = "cuser_screen_name")
    private String cscreenName;
    @Column(name = "cuser_profile_image_url")
    private String cprofileImageUrl;
    @Column(name = "cuse_followers_count")
    private int cfollowers_count;
    @Column(name = "cfollowers_count")
    private int cfriends_count;*/


   /* @Column(name = "cuser_id", index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long cuid;*/

   /* public long getCuid() {
        return cuid;
    }*/

    public String getCname() {
        return cname;
    }

    /*public String getCscreenName() {
        return cscreenName;
    }

    public String getCprofileImageUrl() {
        return cprofileImageUrl;
    }

    public int getCfollowers_count() {
        return cfollowers_count;
    }

    public int getCfriends_count() {
        return cfriends_count;
    }
*/

    public CurrentUser() {
        super();
    }


    public CurrentUser(String cname) {
        this.cname = cname;
    }

    public static CurrentUser UseInfo(){ //CurrentUser currentUser
           return new Select().from(CurrentUser.class).executeSingle();

    }
}
