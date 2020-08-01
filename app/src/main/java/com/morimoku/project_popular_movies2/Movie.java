package com.morimoku.project_popular_movies2;

public class Movie {

    private int movieId;
    private String movieTitle;
    private String movieReleaseDate;
    private String movieVoteAverage;
    private String movieOverview;
    private String moviePosterPath;

    public Movie(){

    }

    public Movie(int movieId, String movieTitle, String movieReleaseDate, String movieVoteAverage, String movieOverview, String moviePosterPath) {
        this.movieId = movieId;
        this.movieTitle = movieTitle ;
        this.movieReleaseDate = movieReleaseDate;
        this.movieVoteAverage = movieVoteAverage;
        this.movieOverview = movieOverview;
        this.moviePosterPath = moviePosterPath;
    }
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }
    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }
    public String getMovieVoteAverage() {
        return movieVoteAverage;
    }
    public void setMovieVoteAverage(String movieVoteAverage) {
        this.movieVoteAverage = movieVoteAverage;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }


}
