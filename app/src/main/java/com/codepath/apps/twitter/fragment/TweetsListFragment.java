package com.codepath.apps.twitter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.activities.DetailActivity;
import com.codepath.apps.twitter.adapters.TweetArrayAdapter;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.utils.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;


public class TweetsListFragment extends Fragment {
    private TwitterClient client;
    TweetArrayAdapter aTweets;
    ArrayList<Tweet> tweets;
    ListView lvTweets;
    long max_id = 1;
    long since_id = 1;

    //create lifecycle event
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create array list
        tweets = new ArrayList<>();

       // aTweets.notifyDataSetChanged();
        client = TwitterApplication.getRestClient();
        populateTimeline(0);
    }

    //inflation logic
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        //find listview
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        //construct adapter from data source
        aTweets = new TweetArrayAdapter(getActivity(), tweets);

        //connect adadpter to the listview
        lvTweets.setAdapter(aTweets);

        setup();

        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Tweet posTweet = aTweets.getItem(position);
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("tweet", posTweet);
                getContext().startActivity(intent);
            }
        });


        return v;
    }

    private void setup() {
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Tweet lastTweet = tweets.get(tweets.size() - 1);
                TweetsListFragment.this.populateTimeline(lastTweet.getTid());
                return true;


            }
        });
    }

    private void populateTimeline(long tid) {

    }


    public void addAll(List<Tweet> tweets){
        aTweets.addAll(tweets);
    }

    public void clear(){
        aTweets.clear();
    }

}
