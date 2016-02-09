package com.udacity.kssand.popularmovies.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.kssand.popularmovies.R;
import com.udacity.kssand.popularmovies.activity.MainActivity;
import com.udacity.kssand.popularmovies.activity.ScrollingMoviesDetailActivity;
import com.udacity.kssand.popularmovies.adapter.ImageAdapter;
import com.udacity.kssand.popularmovies.util.CommonAsyncTask;
import com.udacity.kssand.popularmovies.util.Constants;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * A placeholder fragment containing a simple view.
 */
public class MostPopularFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener  {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private LinearLayoutManager mLayoutManager;
    List<com.udacity.kssand.popularmovies.model.MovieDetails> movieList = new ArrayList<>();
    ;
    SharedPreferences sharedPreferences;
    final String LOG_TAG = MostPopularFragment.class.getSimpleName();
    private int pageCount = 1;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int swipePosition;
    TextView emptyTextView;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    public MostPopularFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "onCreate");
        setHasOptionsMenu(true);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (savedInstanceState != null && !savedInstanceState.containsKey("movies")) {
            movieList = savedInstanceState.getParcelableArrayList("movies");
        } else {
            updateMovies();
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        emptyTextView = (TextView) rootView.findViewById(R.id.EmptyTextView);
        recyclerView.setHasFixedSize(true);
        // The number of Columns
        if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation) {
            mLayoutManager = new GridLayoutManager(this.getActivity(), 3);
        } else {
            mLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        }
        recyclerView.setLayoutManager(mLayoutManager);
        imageAdapter = new ImageAdapter(getActivity(), movieList,this);
        recyclerView.setAdapter(imageAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView mrecyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = recyclerView.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        if (loading && pageCount <= 40) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            pageCount++;
                            new CommonAsyncTask(MostPopularFragment.this, Constants.MOST_POPULAR_REQUEST).execute(Integer.toString(pageCount));

                        }
                    }
                }
            }
        });

        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", (ArrayList<? extends Parcelable>) movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        Log.v(LOG_TAG, "onDestroy");
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        Log.v(LOG_TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.v(LOG_TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onResume() {
        Log.v(LOG_TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.v(LOG_TAG, "onStart");
        // how to avoid update movies on every start of activity and use it only when settings changes
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh) {
            updateMovies();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateMovies() {
        new CommonAsyncTask(this, Constants.MOST_POPULAR_REQUEST).execute("1");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        movieList.clear();
        updateMovies();
    }

    public void onTaskCompleted(Object object) {
        loading = true;
        if (object != null) {
            movieList.addAll((List<com.udacity.kssand.popularmovies.model.MovieDetails>) object);
            /*imageAdapter =new ImageAdapter(this.getActivity(),movieList);
            recyclerView.setAdapter(imageAdapter);*/
            imageAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "Sorry, some error occured", Toast.LENGTH_SHORT).show();
        }
    }


}


