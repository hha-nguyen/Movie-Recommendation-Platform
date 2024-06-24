package com.mesto.movieplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mesto.movieplatform.entities.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
