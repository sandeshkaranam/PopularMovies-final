package com.udacity.kssand.popularmovies.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.kssand.popularmovies.R;
import com.udacity.kssand.popularmovies.adapter.MainSwipePageAdapter;

/**
 * Created by kssand on 08-Feb-16.
 */
public class TestFragment extends Fragment {


    public TestFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("onCreate", "onCreate");
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_test, container, false);
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.main_pager);
        MainSwipePageAdapter mainSwipePageAdapter = new MainSwipePageAdapter(getChildFragmentManager(),getActivity());
        viewPager.setAdapter(mainSwipePageAdapter);
        return rootView;
    }
}
