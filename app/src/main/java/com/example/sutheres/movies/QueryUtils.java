package com.example.sutheres.movies;

/**
 * Created by Sutheres on 2/21/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving movie data from TheMovieDB
 */
public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Movie} objects that has been built up from parsing a JSON response.
     */
    public static List<Movie> extractMovies(String stringURL) {

        // Create a URL object
        URL url = createURL(stringURL);

        // Perform HTTP request and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create a {@link Movie} event
        List<Movie> movies = extractFeaturesFromJSON(jsonResponse);
        return movies;
    }

    /**
     * Returns a new URL object from the given String URL.
     */
    private static URL createURL(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonResponse = "";
        // If the URL is null, return early
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000 /* milliseconds*/);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successfull (response code 200),
            // then read the input stream and parse the response.
            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error making HTTP request: " + e);
        } finally {

            if(urlConnection != null) {
                urlConnection.disconnect();
            }

            if(inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * convert the {@link InputStream} into a string which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a {@link Movie} object by parsing out information
     * about the first movie from the input movieJSON string.
     */
    private static List<Movie> extractFeaturesFromJSON(String movieJSON) {
        if(TextUtils.isEmpty(movieJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding movies to
        List<Movie> movies = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_REPONSE. If there's a problem with the way the JSON
        // is formated, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Parse the response given by the movieJSON string and
            // build up a list of movie objects with the corresponding data.
            JSONObject jsonRootObject = new JSONObject(movieJSON);

            // Get the instance of JSON array that contains the features (movies)
            JSONArray movieArray = jsonRootObject.getJSONArray("results");

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject jsonObject = movieArray.getJSONObject(i);

                // Extract the movie ID
                int movieID = jsonObject.getInt("id");
                // Extract poster path
                String posterPath = jsonObject.getString("poster_path");
                // Extract title
                String title = jsonObject.getString("original_title");
                // Extract overview
                String overview = jsonObject.getString("overview");
                // Extract rating
                double rating = jsonObject.getDouble("vote_average");
                // Extract release date
                String releaseDate = jsonObject.getString("release_date");

                // Create a Parcelable Movie object from above features
                //Movie newMovie = new Movie(movieID, posterPath, title, overview, rating, releaseDate);
                Parcelable newMovie = new Movie(movieID, posterPath, title, overview, rating, releaseDate);
                // Add movie to list of movies
                movies.add((Movie)newMovie);
            }



        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of movies
        return movies;
    }
}
