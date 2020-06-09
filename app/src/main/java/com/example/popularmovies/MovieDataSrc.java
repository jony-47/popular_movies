package com.example.popularmovies;

public class MovieDataSrc {
    private String mImgUrl;
    private String mMovieName;
    private String mMovieOverview;
    private double mMovieLength;
    private String releaseDate;

    public MovieDataSrc(String mImgUrl, String mMovieName, String mMovieOverview, double mMovieLength, String releaseDate) {
        this.mImgUrl = mImgUrl;
        this.mMovieName = mMovieName;
        this.mMovieOverview = mMovieOverview;
        this.mMovieLength = mMovieLength;
        this.releaseDate = releaseDate;
    }

    public String getmImgUrl() {
        return mImgUrl;
    }

    public String getmMovieName() {
        return mMovieName;
    }

    public String getmMovieOverview() {
        return mMovieOverview;
    }

    public double getmMovieLength() {
        return mMovieLength;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
