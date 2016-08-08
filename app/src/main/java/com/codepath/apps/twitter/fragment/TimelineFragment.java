package com.codepath.apps.twitter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {
    public static final String Timeline_PAGE = "Timeline_PAGE";




    public TimelineFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   mPage = getArguments().getInt(Timeline_PAGE);*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }


}
