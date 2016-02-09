package com.udacity.kssand.popularmovies.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.kssand.popularmovies.R;
import com.udacity.kssand.popularmovies.activity.MainActivity;
import com.udacity.kssand.popularmovies.adapter.ImageAdapter;
import com.udacity.kssand.popularmovies.database.MoviesContract;
import com.udacity.kssand.popularmovies.model.MovieDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kssand on 08-Feb-16.
 */
public class FavoriteFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private LinearLayoutManager mLayoutManager;
    List<MovieDetails> movieList;
    SharedPreferences sharedPreferences;
    TextView emptyTextView;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    final String LOG_TAG = FavoriteFragment.class.getSimpleName();

    public FavoriteFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "onCreate");
        setHasOptionsMenu(true);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(savedInstanceState != null && !savedInstanceState.containsKey("movies")) {
            movieList = savedInstanceState.getParcelableArrayList("movies");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreateView");
        movieList= new ArrayList<>();
        View rootView=inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        emptyTextView = (TextView)rootView.findViewById(R.id.EmptyTextView);
        recyclerView.setHasFixedSize(true);
        // The number of Columns
        if(Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation){
            mLayoutManager = new GridLayoutManager(this.getActivity(),3);
        }else{
            mLayoutManager = new GridLayoutManager(this.getActivity(),2);
        }
        recyclerView.setLayoutManager(mLayoutManager);
        imageAdapter = new ImageAdapter(getActivity(), movieList,this);
        recyclerView.setAdapter(imageAdapter);
            if (movieList == null || movieList.size() == 0) {
                Cursor cursor = getActivity().getContentResolver().query(MoviesContract.MovieEntry.CONTENT_URI, null, null, null, null);
                getMovieListFromCursor(cursor);
                if (movieList.size() == 0) {
                    emptyTextView.setText("No Favorites Added");
                    emptyTextView.setVisibility(View.VISIBLE);
                    if (((MainActivity) getActivity()).mTwoPane) {
                        android.support.v4.app.Fragment fragment = ((MainActivity) getActivity()).getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
                        ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                                .remove(fragment)
                                .commit();
                    }
                } else {
                    imageAdapter.notifyDataSetChanged();
                    if (((MainActivity) getActivity()).mTwoPane) {
                        ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.detailContainer, DetailActivityFragment.newInstance(movieList.get(0),FavoriteFragment.class.getSimpleName()), DETAILFRAGMENT_TAG)
                                .commit();
                    }
                }
            }

        return rootView;
    }

    private void getMovieListFromCursor(Cursor cursor) {
        if(movieList!=null){
            movieList.clear();
        }else {
            movieList=new ArrayList<>();
        }
        while(cursor.moveToNext()){
            MovieDetails movie = new MovieDetails();
            movie.setName(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_NAME)));
            movie.setId(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIES_ID)));
            movie.setRating(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RATING)));
            movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE)));
            movie.setImageUrl(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_IMAGE_URL)));
            movie.setLanguage(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_LANGUAGE)));
            movie.setAdult(cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_ADULT)) > 0);
            movie.setOverview(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_OVERVIEW)));
            movie.setBackdroppath(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH)));
            movie.setPopularity(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POPULARITY)));
            movie.setVoteCount(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT)));
            movie.setVideo(cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VIDEO)) > 0);
            movie.setVoteAverage(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE)));
            movieList.add(movie);
        }
    }
}
