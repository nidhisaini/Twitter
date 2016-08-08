package com.codepath.apps.twitter.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


public class NetworkUtil {
    static Context context;
    boolean connected = false;

    private static NetworkUtil instance = new NetworkUtil();

    public static NetworkUtil getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    public Boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            connected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"Not connected to Internet", Toast.LENGTH_SHORT).show();
            //Log.v("Not connected to Internet", e.toString());
        }
        return connected;

    }


}
