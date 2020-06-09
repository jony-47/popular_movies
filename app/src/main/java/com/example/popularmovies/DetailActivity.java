package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.popularmovies.MainActivity.INTENT_MOVIE_DESCRIPTION;
import static com.example.popularmovies.MainActivity.INTENT_MOVIE_IMG_URL;
import static com.example.popularmovies.MainActivity.INTENT_MOVIE_RELEASE_DATE;
import static com.example.popularmovies.MainActivity.INTENT_MOVIE_TITLE;
import static com.example.popularmovies.MainActivity.INTENT_MOVIE_VOTE;

public class DetailActivity extends AppCompatActivity {
    private ImageView mimageView;
    private TextView mTitle;
    private TextView mDes;
    private TextView mReleaseDate;
    private TextView mVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();

        mimageView = findViewById(R.id.movie_poster_detail);
        mTitle = findViewById(R.id.movie_title);
        mDes = findViewById(R.id.movie_details);
        mReleaseDate = findViewById(R.id.release_date);
        mVote = findViewById(R.id.movie_rating);

        setTitle("Detail Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String imgUrl = intent.getStringExtra(INTENT_MOVIE_IMG_URL);
        String title = intent.getStringExtra(INTENT_MOVIE_TITLE);
        String des = intent.getStringExtra(INTENT_MOVIE_DESCRIPTION);
        String releaseDate = intent.getStringExtra(INTENT_MOVIE_RELEASE_DATE);
        Double vote = intent.getDoubleExtra(INTENT_MOVIE_VOTE, 0);

        Picasso.get()
                .load(imgUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.noimg)
                .fit()
                .centerInside()
                .into(mimageView);

        if (des.equals("") || des.equals(null)) {
            des = "No description is available";
        }

        if (vote.equals(0.0) || vote.equals(null)) {
            mVote.setText("Not rated yet");
        } else {
            mVote.setText("Rating: " + vote);
        }

        mTitle.setText(title);
        mDes.setText(des);
        mReleaseDate.setText("Release Date: " + releaseDate);
    }
}
