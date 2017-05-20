package com.example.sutheres.movies;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Sutheres on 2/21/2017.
 *

public class Movie implements Serializable{

    // Holds movie id
    private int mMovieID;
    // Holds the path of the poster image. Will need to be appended to a base URL
    private String mPosterPath;
    // Holds original title
    private String mTitle;
    // Holds movie synopsis
    private String mOverview;
    // Holds user rating
    private double mRating;
    // Holds release date
    private String mReleaseDate;

    public Movie(int movieID, String posterPath, String title, String overview, double rating, String releaseDate) {
        mMovieID = movieID;
        mPosterPath = posterPath;
        mTitle = title;
        mOverview = overview;
        mRating = rating;
        mReleaseDate = releaseDate;
    }

    public int getMovieID() {return mMovieID;}

    public String getPosterPath() {return  mPosterPath;}

    public String getTitle() {return mTitle;}

    public String getOverview() {return mOverview;}

    public double getRating() {return mRating;}

    public String getReleaseDate() {return mReleaseDate;}

    public Context getContext() {return getContext();}

}*/

public class Movie implements Parcelable{

    // Holds movie id
    private int mMovieID;
    // Holds the path of the poster image. Will need to be appended to a base URL
    private String mPosterPath;
    // Holds original title
    private String mTitle;
    // Holds movie synopsis
    private String mOverview;
    // Holds user rating
    private double mRating;
    // Holds release date
    private String mReleaseDate;

    public Movie(int movieID, String posterPath, String title, String overview, double rating, String releaseDate) {
        mMovieID = movieID;
        mPosterPath = posterPath;
        mTitle = title;
        mOverview = overview;
        mRating = rating;
        mReleaseDate = releaseDate;

    }

    // This is where you write the values you want to save to the `Parcel`.
    // The `Parcel` class has methods defined to help you save all of your values.
    // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
    // You may need to make several classes Parcelable to send the data you want.
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mMovieID);
        parcel.writeString(mPosterPath);
        parcel.writeString(mTitle);
        parcel.writeString(mOverview);
        parcel.writeDouble(mRating);
        parcel.writeString(mReleaseDate);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        /**
         * It will be required during un-marshaling data stored in a Parcel
         * @author prasanta
         */
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private Movie(Parcel in) {
        /**
         * Re-construct from the parcel
         */
        mMovieID = in.readInt();
        mPosterPath = in.readString();
        mTitle = in.readString();
        mOverview = in.readString();
        mRating = in.readDouble();
        mReleaseDate = in.readString();
    }

    public int getMovieID() {return mMovieID;}

    public String getPosterPath() {return  mPosterPath;}

    public String getTitle() {return mTitle;}

    public String getOverview() {return mOverview;}

    public double getRating() {return mRating;}

    public String getReleaseDate() {return mReleaseDate;}

    public Context getContext() {return getContext();}
}
