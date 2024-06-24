package com.mesto.movieplatform.services;

import com.mesto.movieplatform.dtoes.RoomDTO;
import com.mesto.movieplatform.entities.Room;

public interface RoomService {
    RoomDTO createRoomForMovie(RoomDTO roomDTO, Integer movieId, Integer hostId);

    RoomDTO getRoomById(Integer roomId);

    RoomDTO getRoomByMovieId(Integer movieId);

    RoomDTO updateRoom(RoomDTO roomDTO);

    boolean deleteRoom(Integer roomId);
}
