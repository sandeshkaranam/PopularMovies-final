package com.udacity.kssand.popularmovies.helper;

import com.udacity.kssand.popularmovies.model.MovieDetails;
import com.udacity.kssand.popularmovies.model.Review;
import com.udacity.kssand.popularmovies.model.Trailer;
import com.udacity.kssand.popularmovies.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kssand on 21-Jan-16.
 */
public class JSONParseHelper {

    public List<Object> parseMovieList(String json){
        List<Object> movieList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i =0;i<jsonArray.length();i++){
                MovieDetails movie = new MovieDetails();
                JSONObject object = jsonArray.getJSONObject(i);
                movie.setId(object.getString("id"));
                movie.setImageUrl(Constants.IMAGE_PATH_BASE_URL + object.get("poster_path"));
                movie.setAdult(object.getBoolean("adult"));
                movie.setOverview(object.getString("overview"));
                movie.setReleaseDate(object.getString("release_date"));
                movie.setName(object.getString("original_title"));
                movie.setLanguage(object.getString("original_language"));
                movie.setBackdroppath(Constants.BACKDROP_IMAGE_PATH_BASE_URL + object.getString("backdrop_path"));
                movie.setPopularity(object.getString("popularity"));
                movie.setVoteCount(object.getString("vote_count"));
                movie.setVideo(object.getBoolean("video"));
                movie.setVoteAverage(object.getString("vote_average"));
                movieList.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }

    public List<Object> parseMovieTrailers(String json){
        List<Object> trailerList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i =0;i<jsonArray.length();i++){
                Trailer trailer = new Trailer();
                JSONObject object = jsonArray.getJSONObject(i);
                trailer.setId(object.getString("id"));
                trailer.setKey(object.getString("key"));
                trailer.setName(object.getString("name"));
                trailer.setSite(object.getString("site"));
                trailer.setSize(object.getString("size"));
                trailer.setType(object.getString("type"));
                trailerList.add(trailer);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailerList;
    }

    public List<Object> parseMovieReviews(String json){
        List<Object> reviewList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i =0;i<jsonArray.length();i++){
                Review review = new Review();
                JSONObject object = jsonArray.getJSONObject(i);
                review.setId(object.getString("id"));
                review.setAuthor(object.getString("author"));
                review.setContent(object.getString("content"));
                review.setUrl(object.getString("url"));
                reviewList.add(review);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviewList;
    }
}
