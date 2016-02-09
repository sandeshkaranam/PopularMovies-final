package com.udacity.kssand.popularmovies.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.udacity.kssand.popularmovies.adapter.ReviewFragmentAdapter;
import com.udacity.kssand.popularmovies.model.Review;
import com.udacity.kssand.popularmovies.util.CommonAsyncTask;
import com.udacity.kssand.popularmovies.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kssand on 24-Jan-16.
 */
public class ReviewsFragment extends android.support.v4.app.ListFragment {
    public static final String MOVIE_ID = "movie_id";
    private String mParam;
    private List<Review> reviewList = new ArrayList<>();
    private ReviewFragmentAdapter adapter;

    public static ReviewsFragment newInstance(String param) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_ID,param);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReviewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(MOVIE_ID);
            CommonAsyncTask asyncTask = new CommonAsyncTask(this, Constants.MOVIE_REVIEWS_REQUEST);
            asyncTask.execute();
        }

        adapter = new ReviewFragmentAdapter(getActivity(),reviewList);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onStart() {
        super.onStart();
        getListView().setDivider(new ColorDrawable(Color.BLACK));
        getListView().setDividerHeight(1);
    }


    public void onTaskCompleted(Object object) {
        if(null!=object) {
            reviewList.addAll((List<Review>) object);
            adapter.notifyDataSetChanged();
            if(reviewList.size()==0){
                setEmptyText("No Reviews Found");
            }
        }else{
            Toast.makeText(getActivity(), "Sorry, some error occured", Toast.LENGTH_SHORT).show();
        }
    }

}
