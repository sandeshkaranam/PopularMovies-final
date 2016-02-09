package com.udacity.kssand.popularmovies.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.udacity.kssand.popularmovies.R;
import com.udacity.kssand.popularmovies.fragment.DetailActivityFragment;
import com.udacity.kssand.popularmovies.fragment.FavoriteFragment;
import com.udacity.kssand.popularmovies.model.MovieDetails;

public class ScrollingMoviesDetailActivity extends AppCompatActivity {
    final String LOG_TAG = ScrollingMoviesDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle =getIntent().getExtras();
        MovieDetails movie = bundle.getParcelable("movie");
        int ft= bundle.getInt("ft");
        String fragmenttype;
        if(ft==3)
        {
            fragmenttype= FavoriteFragment.class.getSimpleName();
        }else {
            fragmenttype="";
        }
            setTitle(movie.getName());
            DetailActivityFragment detailFragment = DetailActivityFragment.newInstance(movie,fragmenttype);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detailContainer,detailFragment)
                    .commit();
    }
}
