package com.mesto.movieplatform.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mesto.movieplatform.dtoes.GenreDTO;
import com.mesto.movieplatform.services.GenreService;

import lombok.AllArgsConstructor;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/")
    public ResponseEntity<List<GenreDTO>> fetchAllGenres() {
        return ResponseEntity.ok(genreService.fetchAllGenres());
    }
}
