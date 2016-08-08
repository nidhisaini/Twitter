package com.codepath.apps.twitter.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NidhiSaini on 8/7/16.
 */
public class SessionManagement {
    // User name (make variable public to access from outside)
    public static final String KEY_FIRSTNAME = "firstname";
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "TwitterPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public SessionManagement(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
}
