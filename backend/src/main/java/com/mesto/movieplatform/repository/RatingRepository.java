package com.mesto.movieplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mesto.movieplatform.entities.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}