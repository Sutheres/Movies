package com.example.sutheres.movies;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Sutheres on 2/21/2017.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {forceLoad();}

    @Override
    public List<Movie> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null
        if(mUrl == null) {
            return null;
        }

        // Perform the HTTP request for Movie data and receive a response
        List<Movie> movies = QueryUtils.extractMovies(mUrl);
        return movies;
    }
}
