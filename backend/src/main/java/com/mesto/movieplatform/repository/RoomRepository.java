package com.mesto.movieplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mesto.movieplatform.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Room findByMovieId(Integer movieId);
}
