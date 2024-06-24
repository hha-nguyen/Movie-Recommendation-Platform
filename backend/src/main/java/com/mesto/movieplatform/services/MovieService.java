package com.mesto.movieplatform.services;

import java.util.List;

import com.mesto.movieplatform.dtoes.MovieDTO;
import com.mesto.movieplatform.entities.Movie;
import com.mesto.movieplatform.entities.Rating;
import com.mesto.movieplatform.exceptions.MovieDetailsNotFoundException;

public interface MovieService {
    MovieDTO addMovie(MovieDTO movie, List<Integer> genreIds);

    MovieDTO updateMovie(Integer movieId, MovieDTO movie);

    List<MovieDTO> searchMovie(String query);

    List<MovieDTO> fetchAllMovies();

    List<MovieDTO> fetchMoviesByGenre(Integer genreId);

    List<MovieDTO> fetchMoviesByName(String movieName) throws MovieDetailsNotFoundException;

    MovieDTO fetchMovie(Integer movieId) throws MovieDetailsNotFoundException;

    List<MovieDTO> fetchPopularMovies();

    List<MovieDTO> fetchTopRatedMovies();

    List<MovieDTO> fetchSimlarMovies(Integer movieId);

    MovieDTO addGenresToMovie(Integer movieId, List<Integer> genreIds);

    MovieDTO addRatingToMovie(Integer movieId, Integer userId, Integer score);

    boolean deleteMovie(Integer movieId);
}
