package com.udacity.kssand.popularmovies.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.udacity.kssand.popularmovies.fragment.OverviewFragment;
import com.udacity.kssand.popularmovies.fragment.ReviewsFragment;
import com.udacity.kssand.popularmovies.fragment.TrailerFragment;
import com.udacity.kssand.popularmovies.model.MovieDetails;

/**
 * Created by kssand on 21-Jan-16.
 */
public class DetailedSwipePageAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private MovieDetails movie;

    public DetailedSwipePageAdapter(Context mContext, FragmentManager fm,MovieDetails movie) {
        super(fm);
        this.mContext = mContext;
        this.movie = movie;
    }

    @Override
    public Fragment getItem(int position) {
        if(getPageTitle(position).equals("OVERVIEW")){
            return OverviewFragment.newInstance(movie.getOverview());
        }else if(getPageTitle(position).equals("REVIEWS")){
            return ReviewsFragment.newInstance(movie.getId());
        }else if(getPageTitle(position).equals("TRAILERS")){
            return TrailerFragment.newInstance(movie.getId());
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "OVERVIEW";
            case 1:
                return "REVIEWS";
            case 2:
                return "TRAILERS";
        }
        return null;
    }
}
