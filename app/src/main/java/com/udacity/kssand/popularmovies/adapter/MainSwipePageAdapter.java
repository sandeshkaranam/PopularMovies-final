package com.udacity.kssand.popularmovies.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.udacity.kssand.popularmovies.fragment.FavoriteFragment;
import com.udacity.kssand.popularmovies.fragment.MostPopularFragment;
import com.udacity.kssand.popularmovies.fragment.TopRatedFragment;

/**
 * Created by kssand on 07-Feb-16.
 */
public class MainSwipePageAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    public MainSwipePageAdapter(FragmentManager fm,Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        if(getPageTitle(position).equals("MOST POPULAR")){
            return new MostPopularFragment();
        }else if(getPageTitle(position).equals("TOP RATED")){
            return new TopRatedFragment();
        }else if(getPageTitle(position).equals("FAVORITES")){
            return new FavoriteFragment();
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
                return "MOST POPULAR";
            case 1:
                return "TOP RATED";
            case 2:
                return "FAVORITES";
        }
        return null;
    }
}
