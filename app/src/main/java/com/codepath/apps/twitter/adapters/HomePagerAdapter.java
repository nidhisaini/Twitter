package com.codepath.apps.twitter.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.codepath.apps.twitter.fragment.MentionsFragment;
import com.codepath.apps.twitter.fragment.TimelineFragment;


public class HomePagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private String tabTitles[] = { "Timeline", "Mentions" };

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            TimelineFragment Timeline = new TimelineFragment();
            return Timeline;
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            MentionsFragment Mentions = new MentionsFragment();
            return Mentions;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }


    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
