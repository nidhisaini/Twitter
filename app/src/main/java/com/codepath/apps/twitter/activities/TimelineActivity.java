package com.codepath.apps.twitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.fragment.HomeTimelineFragment;
import com.codepath.apps.twitter.fragment.MentionsTimelineFragment;

/*http://guides.codepath.com/android/Sliding-Tabs-with-PagerSlidingTabStrip*/
public class TimelineActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 20;
    // Instance of the progress action-view
    MenuItem miActionProgressItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //Get the viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the viewpager adapter for the page
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        //Find the pager sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        //attach the pager tabstrip to viewpager
        tabStrip.setViewPager(vpPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                userProfile();
                return true;
            case R.id.action_compose:
                composeTweet();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String status = data.getExtras().getString("status");
            // Tweet.fromJSON(status)

           /* *//**//*client.postTweet(status, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                Log.d("DEBUG", "Success");
                Toast.makeText(TimelineActivity.this, "Success", Toast.LENGTH_LONG).show();
                tweets.addAll(Tweet.fromJSONArray(json));
                aTweets.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", "Failure");
                Toast.makeText(TimelineActivity.this, "Failure", Toast.LENGTH_LONG).show();
            }
        });*/
        }
    }

    private void userProfile() {
        Intent intent = new Intent(TimelineActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void composeTweet() {
        Intent intent = new Intent(TimelineActivity.this, ComposeActivity.class);
      /*  intent.putExtra("user", tweet.getUser());
        intent.putExtra("screen_name", tweet.getUser().getScreenName());*/
        startActivityForResult(intent, REQUEST_CODE);
    }




    //returns the order of fragments in the viewpager
    public class TweetsPagerAdapter extends FragmentPagerAdapter{
        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[] { "Home", "Mentions" };

        //Adapter get the manager to insert or remove fragment from activity
        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //Controls th order and creation of Fragment within the pager
        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return new HomeTimelineFragment();
            }
            else if(position == 1){
                return new MentionsTimelineFragment();
            }
            else{
                return null;
            }

        }

        //will return the tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        // how many fragments their are to swipe between?
        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
