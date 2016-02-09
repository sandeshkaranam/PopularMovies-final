package com.udacity.kssand.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.GlidePalette;
import com.udacity.kssand.popularmovies.R;
import com.udacity.kssand.popularmovies.activity.MainActivity;
import com.udacity.kssand.popularmovies.activity.ScrollingMoviesDetailActivity;
import com.udacity.kssand.popularmovies.fragment.DetailActivityFragment;
import com.udacity.kssand.popularmovies.fragment.FavoriteFragment;
import com.udacity.kssand.popularmovies.model.MovieDetails;

import java.util.List;

import static butterknife.ButterKnife.findById;

/**
 * Created by kssand on 29-Nov-15.
 */
public class ImageAdapter extends  RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context mContext;
    private List<MovieDetails> posters;
    final String LOG_TAG=ImageAdapter.class.getSimpleName();
    private long mMovieId;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private Fragment fragmentType;

    public ImageAdapter(Context c,List<MovieDetails> posters,Fragment fragmentType) {
        mContext = c;
        this.posters=posters;
        this.fragmentType=fragmentType;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, final int position) {
            holder.bind(posters.get(position));
    }

    @Override
    public int getItemCount() {
        return posters.toArray().length;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements GlidePalette.CallBack{

        public ImageView movieImage;
        public TextView movieTextView,voteTextView;
        public CardView cardView;
        View movieFooterView;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            cardView =(CardView) itemView.findViewById(R.id.movie_item_container);
            movieImage= (ImageView) itemView.findViewById(R.id.movie_item_image);
            movieTextView = (TextView) itemView.findViewById(R.id.movie_item_title);
            voteTextView = (TextView) itemView.findViewById(R.id.movie_voting);
            movieFooterView=(View)itemView.findViewById(R.id.movie_item_footer);

        }

        public void bind(@NonNull final MovieDetails movieDetails){
            movieTextView.setText(movieDetails.getName());
            voteTextView.setText(movieDetails.getVoteAverage());
            // prevents unnecessary color blinking
            if (mMovieId != Integer.parseInt(movieDetails.getId())) {
                resetColors();
                mMovieId = Integer.parseInt(movieDetails.getId());
            }

            Glide.with(mContext)
                    .load(movieDetails.getImageUrl())
                    .crossFade()
                    .placeholder(R.color.movie_poster_placeholder)
                    .listener(GlidePalette.with(movieDetails.getImageUrl())
                            .intoCallBack(this))
                    .into(movieImage);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    if(((MainActivity)mContext).mTwoPane) {
                        DetailActivityFragment detailFragment = DetailActivityFragment.newInstance(movieDetails,fragmentType.getClass().getSimpleName());
                        /*bundle.putString("fragmenttype",fragmentType);
                        detailFragment.setArguments(bundle);*/
                        ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.detailContainer, detailFragment, DETAILFRAGMENT_TAG)
                                .commit();
                    }else {
                        Intent intent = new Intent(mContext, ScrollingMoviesDetailActivity.class);
                        bundle.putParcelable("movie", movieDetails);
                        if(fragmentType.getClass().getSimpleName().equals(FavoriteFragment.class.getSimpleName()))
                        {bundle.putInt("ft",3);}
                        bundle.putString("fragmenttype",fragmentType.getClass().getSimpleName());
                        intent.putExtras(bundle);
                        fragmentType.startActivity(intent);
                    }

                }
            });

        }
        private void resetColors() {
            movieFooterView.setBackgroundColor(Color.parseColor("#ffe53935"));
            movieTextView.setTextColor(Color.parseColor("#FFFFFFFF"));
            voteTextView.setTextColor(Color.parseColor("#FFFFFFFF"));
        }


        private void applyColors(Palette.Swatch swatch) {
            if (swatch != null) {
                movieFooterView.setBackgroundColor(swatch.getRgb());
                movieTextView.setTextColor(swatch.getBodyTextColor());
                voteTextView.setTextColor(swatch.getTitleTextColor());
            }
        }

        @Override
        public void onPaletteLoaded(Palette palette) {
            applyColors(palette.getVibrantSwatch());
        }
    }
}
