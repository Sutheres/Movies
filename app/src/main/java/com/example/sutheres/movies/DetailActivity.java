package com.example.sutheres.movies;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w185";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Movie movie = (Movie) getIntent().getSerializableExtra("Movie");

        Movie movie = getIntent().getParcelableExtra("Movie");

        ImageView detailImage = (ImageView)findViewById(R.id.detail_image_thumbnail);
        TextView detailTitle = (TextView)findViewById(R.id.detail_title);
        TextView detailRating = (TextView)findViewById(R.id.detail_rating);
        TextView detailReleaseDate = (TextView)findViewById(R.id.detail_release_date);
        TextView detailOverview = (TextView)findViewById(R.id.detail_overview);
        TextView detailID = (TextView)findViewById(R.id.movie_id);

        Glide.with(this).load(BASE_IMAGE_URL + movie.getPosterPath()).into(detailImage);
        detailTitle.setText(movie.getTitle());
        detailRating.setText("Rating: " + Double.toString(movie.getRating()) + "/10");
        detailReleaseDate.setText("Release Date: " + movie.getReleaseDate());
        detailOverview.setText(movie.getOverview());
        detailID.setText(Integer.toString(movie.getMovieID()));

        TextView testTV = new TextView(this);
        testTV.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        testTV.setText("YOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOo");

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.activity_detail);
        linearLayout.addView(testTV);

    }
}
