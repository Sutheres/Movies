package com.example.sutheres.movies;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    //private MovieAdapter mAdapter;
    private MovieAdapter mAdapter;

    private static final int MOVIE_LOADER_ID = 1;

    private static final String MOVIE_REQUEST_URL = "https://api.themoviedb.org/3/discover/movie?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView movieGridView = (GridView)findViewById(R.id.grid);
        // Create a new adapter that takes an empty list of movies as input
        mAdapter = new MovieAdapter(this, new ArrayList<Movie>());

        // Set the adapter on the gridview
        // so the list can be populated in the user interface
        movieGridView.setAdapter(mAdapter);

        movieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Movie currentMovie = mAdapter.getItem(position);

                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);

                detailIntent.putExtra("Movie", currentMovie);

                startActivity(detailIntent);
            }
        });


        getLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(MOVIE_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("api_key", "api key goes here");
        uriBuilder.appendQueryParameter("language", "en");
        uriBuilder.appendQueryParameter("sort_by", orderBy);
        uriBuilder.appendQueryParameter("include_adult", "false");
        uriBuilder.appendQueryParameter("include_video", "false");
        uriBuilder.appendQueryParameter("page", "1");
        return new MovieLoader(this, uriBuilder.toString());
    }


    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {

        mAdapter.clear();


        // If there is a valid list of movies then add them to the
        // adapter's data set. This will trigger the ListView to update
        if(movies != null && !movies.isEmpty()) {
            mAdapter.addAll(movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
            mAdapter.clear();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
