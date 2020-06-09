package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.onMovieClickListener {

    // CONSTANTS FOR names to pass data through intents
    public static final String INTENT_MOVIE_IMG_URL = "img_url";
    public static final String INTENT_MOVIE_TITLE = "movie_title";
    public static final String INTENT_MOVIE_DESCRIPTION = "movie_description";
    public static final String INTENT_MOVIE_RELEASE_DATE = "release_date";
    public static final String INTENT_MOVIE_VOTE = "movie_vote";

    //Links to parse the json
    //TODO add your own api key below
    public static final String TOP_RATED_MOVIE = "http://api.themoviedb.org/3/movie/top_rated?api_key=[YOUR_API_KEY]";
    public static final String POPULAR_MOVIE = "https://api.themoviedb.org/3/discover/movie?api_key=[YOUR_API_KEY]&language=en-US&sort_by=popularity.desc&include_adult=true&include_video=false&page=1";
    public static final String RELEASE_DATE = "https://api.themoviedb.org/3/discover/movie?api_key=[YOUR_API_KEY]&language=en-US&sort_by=release_date.desc&include_adult=false&include_video=false&page=1";

    // In the Resonse error ahowing a toast to connect the internet
    public static final String ERROR_MESSAGE = "Connect to the internet";

    // Constants for json objects
    private static final String MOVIE_IMG_URL = "poster_path";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_DESCRIPTION = "overview";
    private static final String MOVIE_RELEASE_DATE = "release_date";
    private static final String MOVIE_VOTE = "vote_average";
    private static final String RESULTS = "results";

    private RecyclerView mRecycleView;
    private MovieAdapter mMovieAdapter;
    private ArrayList<MovieDataSrc> mList;
    private RequestQueue mRequestQueue;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycleView = findViewById(R.id.rv);
        mRecycleView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecycleView.setHasFixedSize(true);
        mList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJson(s);
    }

    private void parseJson(String url) {
        if (url == null) {
            url = POPULAR_MOVIE;
        }
        Log.i("URL ", url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray(RESULTS);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String movie_title = jsonObject.getString(MOVIE_TITLE);
                                String overview = jsonObject.getString(MOVIE_DESCRIPTION);
                                String half_img_url = jsonObject.getString(MOVIE_IMG_URL);
                                String full_img_url = "https://image.tmdb.org/t/p/w185" + half_img_url;
                                double rating = jsonObject.getDouble(MOVIE_VOTE);
                                String releaseDate = jsonObject.getString(MOVIE_RELEASE_DATE);
                                Log.i(movie_title, full_img_url);
                                Log.i(movie_title, overview);
                                mList.add(new MovieDataSrc(full_img_url, movie_title, overview, rating, releaseDate));
                            }
                            mMovieAdapter = new MovieAdapter(MainActivity.this, mList, MainActivity.this);
                            mRecycleView.setAdapter(mMovieAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, ERROR_MESSAGE, Toast.LENGTH_LONG).show();

            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onMovieItemClickListener(int pos) {
        MovieDataSrc movieDataSrc = mList.get(pos);
        String img = movieDataSrc.getmImgUrl();
        String title = movieDataSrc.getmMovieName();
        String des = movieDataSrc.getmMovieOverview();
        String releaseDate = movieDataSrc.getReleaseDate();
        Double vote = movieDataSrc.getmMovieLength();

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(INTENT_MOVIE_IMG_URL, img);
        intent.putExtra(INTENT_MOVIE_TITLE, title);
        intent.putExtra(INTENT_MOVIE_DESCRIPTION, des);
        intent.putExtra(INTENT_MOVIE_RELEASE_DATE, releaseDate);
        intent.putExtra(INTENT_MOVIE_VOTE, vote);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.releaseDate) {
            mList.clear();
            s = RELEASE_DATE;
            parseJson(s);
            MovieAdapter movieAdapter = new MovieAdapter(MainActivity.this, mList, MainActivity.this);
            mRecycleView.setAdapter(movieAdapter);
            return true;
        } else if (i == R.id.top_rated) {
            mList.clear();
            s = TOP_RATED_MOVIE;
            parseJson(s);
            MovieAdapter top_movieAdapter = new MovieAdapter(MainActivity.this, mList, MainActivity.this);
            mRecycleView.setAdapter(top_movieAdapter);
            return true;
        } else if (i == R.id.popular_movies) {
            mList.clear();
            s = POPULAR_MOVIE;
            parseJson(s);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
