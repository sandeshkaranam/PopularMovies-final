package com.udacity.kssand.popularmovies.fragment;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.udacity.kssand.popularmovies.R;
import com.udacity.kssand.popularmovies.adapter.DetailedSwipePageAdapter;
import com.udacity.kssand.popularmovies.database.MoviesContract;
import com.udacity.kssand.popularmovies.model.MovieDetails;
import com.udacity.kssand.popularmovies.util.Constants;

import java.util.ArrayList;

/**
 * Created by kssand on 30-Jan-16.
 */
public class DetailActivityFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM = "movie";

    private MovieDetails movie;
    private ImageView posterIV;
    private TextView nameTV;
    private TextView releaseDateTV;
    private RatingBar ratingTV;
    private ViewPager viewPager;
    private Button favoriteBtn;
    private String fragmentType;
    private ShareActionProvider mShareActionProvider;


    public static DetailActivityFragment newInstance(MovieDetails movie,String fragmenttype) {
        DetailActivityFragment fragment = new DetailActivityFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM, movie);
        args.putString("fragmenttype",fragmenttype);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailActivityFragment() {
        int z=1;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments() !=null){
            movie = getArguments().getParcelable(ARG_PARAM);
            fragmentType = getArguments().getString("fragmenttype");
        }
    }

    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("movie", movie);
        outState.putString("fragmenttype",fragmentType);
        super.onSaveInstanceState(outState);
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        if(movie!=null) {
            getActivity().setTitle(movie.getName());
            posterIV = (ImageView) view.findViewById(R.id.movie_poster);
            nameTV= (TextView) view.findViewById(R.id.nameTV);
            releaseDateTV = (TextView) view.findViewById(R.id.movie_release_date);
            ratingTV = (RatingBar)view.findViewById(R.id.movie_average_rating);
            viewPager = (ViewPager)view.findViewById(R.id.pager);
            FloatingActionButton favoriteBtn = (FloatingActionButton) view.findViewById(R.id.fab);
            if (null != fragmentType && fragmentType.equals(FavoriteFragment.class.getSimpleName())) {
                favoriteBtn.setVisibility(View.GONE);
            } else {
                favoriteBtn.setOnClickListener(this);
            }
            DetailedSwipePageAdapter adapter = new DetailedSwipePageAdapter(getActivity(),getChildFragmentManager(), movie);
            viewPager.setAdapter(adapter);
            Glide.with(getActivity())
                    .load(movie.getImageUrl())
                    .placeholder(R.raw.no_poster)
                    .crossFade()
                    .into(posterIV);

            nameTV.setText(movie.getName());
            releaseDateTV.setText("Release Date : " + movie.getReleaseDate());
        }
        ratingTV.setRating(Float.parseFloat(movie.getVoteAverage()) / 2);
        return view;
    }

    public void onClick(View view){
        if(view.getId()==R.id.fab){
            Uri uri = MoviesContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(movie.getId()).build();
            Cursor movieCursor = getActivity().getContentResolver().query(uri,null,null,null,null);
            if(movieCursor.moveToNext()!=true) {
                ContentValues contentValues = generateContentValues(movie);
                Uri insertedUri = getActivity().getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, contentValues);
                long id = ContentUris.parseId(insertedUri);
                if (id != -1) {
                    Snackbar.make(view, "Added to Favorites", Snackbar.LENGTH_LONG).show();
                   /* Toast.makeText(getActivity(), "Added to Favorites", Toast.LENGTH_SHORT).show();*/
                }
            }else{
                Snackbar.make(view, "Already in Favorites", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private ContentValues generateContentValues(MovieDetails movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIES_ID,movie.getId());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_NAME,movie.getName());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_RATING,movie.getRating());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE,movie.getReleaseDate());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_IMAGE_URL,movie.getImageUrl());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_LANGUAGE,movie.getLanguage());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_ADULT,movie.isAdult());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_OVERVIEW,movie.getOverview());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdroppath());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT, movie.getVoteCount());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_VIDEO, movie.isVideo());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        return  contentValues;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail_fragment, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        mShareActionProvider.setShareIntent(null);
    }

    public void setShareIntent(String text){
        if(mShareActionProvider != null){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}
