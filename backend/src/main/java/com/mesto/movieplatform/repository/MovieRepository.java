package com.mesto.movieplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mesto.movieplatform.entities.Movie;

import java.util.List;
import java.util.Optional;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.amazonaws.services.securityhub.model.Product;
import com.mesto.movieplatform.entities.Genre;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId")
    List<Movie> findByGenreId(@Param("genreId") Integer genreId);

    // search movie by name
    @Query("SELECT m FROM Movie m WHERE m.title LIKE %:query%")
    List<Movie> searchMovies(String query);

    List<Movie> findByTitle(String title);

    List<Movie> findByVoteAverageGreaterThan(Float voteAverage);

    List<Movie> findByVoteAverageLessThan(Float voteAverage);

    List<Movie> findByVoteCountGreaterThan(Integer voteCount);

    List<Movie> findByVoteCountLessThan(Integer voteCount);
}
