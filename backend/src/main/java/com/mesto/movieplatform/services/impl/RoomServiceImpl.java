package com.mesto.movieplatform.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.amazonaws.services.ivsrealtime.model.Video;
import com.mesto.movieplatform.dtoes.RoomDTO;
import com.mesto.movieplatform.entities.Movie;
import com.mesto.movieplatform.entities.Room;
import com.mesto.movieplatform.entities.User;
import com.mesto.movieplatform.repository.MovieRepository;
import com.mesto.movieplatform.repository.RoomRepository;
import com.mesto.movieplatform.repository.UserRepository;
import com.mesto.movieplatform.repository.VideoChunkRepository;
import com.mesto.movieplatform.services.RoomService;
import com.mesto.movieplatform.services.VideoChunkService;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private VideoChunkService videoChunkService;

    @Autowired
    private VideoChunkRepository videoChunkRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private final ModelMapper mapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public RoomDTO createRoomForMovie(RoomDTO roomDTO, Integer movieId, Integer hostId) {
        Room room = mapper.map(roomDTO, Room.class);
        Movie movie = movieRepository.findById(movieId).orElse(null);
        User host = userRepository.findById(hostId).orElse(null);
        LOGGER.info("Creating room for movie with ID: " + movieId);

        room.setStartTime(LocalDateTime.now());
        room.setStatus("ACTIVE");
        room.setMovie(movie);
        room.setHost(host);

        roomRepository.save(room);

        // Send video chunks to clients in the room via WebSocket
        sendVideoChunksToClients(room);

        return mapper.map(room, RoomDTO.class);
    }

    public RoomDTO getRoomById(Integer roomId) {
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room != null) {
            return mapper.map(room, RoomDTO.class);
        } else {
            return null;
        }
    }

    public RoomDTO getRoomByMovieId(Integer movieId) {
        Room room = roomRepository.findByMovieId(movieId);
        if (room != null) {
            return mapper.map(room, RoomDTO.class);
        } else {
            return null;
        }
    }

    public RoomDTO updateRoom(RoomDTO roomDTO) {
        Room room = mapper.map(roomDTO, Room.class);
        roomRepository.save(room);
        return mapper.map(room, RoomDTO.class);
    }

    public boolean deleteRoom(Integer roomId) {
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room != null) {
            roomRepository.delete(room);

            return true;
        }
        return false;
    }

    private void sendVideoChunksToClients(Room room) {
        // Retrieve chunk orders asynchronously
        CompletableFuture.runAsync(() -> {
            List<Integer> chunkOrders = videoChunkRepository.getChunkOrdersByMovieId(room.getMovie().getId());
            for (Integer order : chunkOrders) {
                try {
                    // Fetch video chunk data by order
                    byte[] chunkData = videoChunkRepository.getVideoChunkByMovieIdAndChunkOrder(
                            room.getMovie().getId(),
                            order);
                    if (chunkData != null) {
                        // Simulate processing delay (replace with actual processing logic)
                        Thread.sleep(1000); // Adjust delay as needed

                        // Send chunk to clients in the room via WebSocket
                        messagingTemplate.convertAndSend("/topic/room/" + room.getId(), chunkData);
                    }
                } catch (InterruptedException e) {
                    LOGGER.error("Error processing video chunk: {}", e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
}
