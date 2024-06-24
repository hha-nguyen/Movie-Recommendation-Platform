package com.mesto.movieplatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mesto.movieplatform.dtoes.RoomDTO;
import com.mesto.movieplatform.services.RoomService;

import lombok.AllArgsConstructor;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/")
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO, @RequestParam("movieId") Integer movieId,
            @RequestParam("hostId") Integer hostId) {
        RoomDTO response = roomService.createRoomForMovie(roomDTO, movieId, hostId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable("roomId") Integer roomId) {
        RoomDTO roomDTO = roomService.getRoomById(roomId);
        if (roomDTO != null) {
            return ResponseEntity.ok(roomDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable("roomId") Integer roomId) {
        boolean deleted = roomService.deleteRoom(roomId);
        if (deleted) {
            return ResponseEntity.ok("Room deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }
    }

    // Add more endpoints as needed for updating rooms, fetching rooms by user, etc.
}
