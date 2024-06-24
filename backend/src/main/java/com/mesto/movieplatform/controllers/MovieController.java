package com.mesto.movieplatform.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mesto.movieplatform.dtoes.MovieDTO;
import com.mesto.movieplatform.entities.Movie;
import com.mesto.movieplatform.exceptions.MovieDetailsNotFoundException;
import com.mesto.movieplatform.exceptions.UserDetailsNotFoundException;
import com.mesto.movieplatform.services.StorageService;
import com.mesto.movieplatform.services.VideoChunkService;
import com.mesto.movieplatform.services.impl.MovieServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@RequestMapping("/api/v1/movie")
public class MovieController {

    @Autowired
    private MovieServiceImpl movieServiceImpl;

    @Autowired
    private VideoChunkService videoChunkService;

    @Autowired
    private StorageService storageService;

    @PostMapping("/")
    public ResponseEntity<MovieDTO> addMovieDetail(@RequestBody MovieDTO movieDTO,
            @RequestParam("genreIds") List<Integer> genreIds) {
        MovieDTO response = movieServiceImpl.addMovie(movieDTO, genreIds);

        return ResponseEntity.ok(response);
    }

    // @GetMapping("/allmovies/{user_id}")
    // public ResponseEntity fetchAllMoviesOfUser(@PathVariable Integer user_id)
    // throws UserDetailsNotFoundException, MovieDetailsNotFoundException {
    // // Doubt api
    // movieServiceImpl.checkUser(user_id);
    // List<MovieDTO> movieDTOList = movieServiceImpl.fetchMovie(user_id);
    // return new ResponseEntity(movieDTOList, HttpStatus.CREATED);
    // }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDTO> fetchMovieByName(@PathVariable("movieId") Integer movieId) {
        try {
            MovieDTO movieDTO = movieServiceImpl.fetchMovie(movieId);
            return ResponseEntity.ok(movieDTO);
        } catch (MovieDetailsNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allmovies")
    public ResponseEntity<List<MovieDTO>> fetchAllMovies() {
        List<MovieDTO> movieDTOList = movieServiceImpl.fetchAllMovies();
        return ResponseEntity.ok(movieDTOList);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<MovieDTO>> fetchPopularMovies() {
        List<MovieDTO> movieDTOList = movieServiceImpl.fetchPopularMovies();
        return ResponseEntity.ok(movieDTOList);
    }

    @GetMapping("/toprated")
    public ResponseEntity<List<MovieDTO>> fetchTopRatedMovies() {
        List<MovieDTO> movieDTOList = movieServiceImpl.fetchTopRatedMovies();
        return ResponseEntity.ok(movieDTOList);
    }

    @GetMapping("/similar/{movieId}")
    public ResponseEntity<List<MovieDTO>> fetchSimlarMovies(@PathVariable("movieId") Integer movieId) {
        List<MovieDTO> movieDTOList = movieServiceImpl.fetchSimlarMovies(movieId);
        return ResponseEntity.ok(movieDTOList);
    }

    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<MovieDTO>> fetchMoviesByGenre(@PathVariable("genreId") Integer genreId) {
        List<MovieDTO> movieDTOList = movieServiceImpl.fetchMoviesByGenre(genreId);
        return ResponseEntity.ok(movieDTOList);
    }

    @PostMapping("/genre/add")
    ResponseEntity<MovieDTO> addGenresToMovie(@RequestParam("movieId") Integer movieId,
            @RequestBody List<Integer> genreIds) {
        MovieDTO movieDTO = movieServiceImpl.addGenresToMovie(movieId, genreIds);
        return ResponseEntity.ok(movieDTO);
    }

    @DeleteMapping("/{movieId}")
    public String delteMovieDetail(@PathVariable("movieId") Integer movieId) {
        boolean flag = movieServiceImpl.deleteMovie(movieId);
        if (flag)
            return "success";
        return "fail";
    }

    @PostMapping("/upload/{movieId}")
    public ResponseEntity<String> uploadMovie(@PathVariable Integer movieId,
            @RequestParam("file") MultipartFile file) {
        try {
            MovieDTO movieDTO = movieServiceImpl.fetchMovie(movieId);
            videoChunkService.splitAndSaveVideoIntoChunks(file, movieDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
        return ResponseEntity.ok(storageService.uploadFile(file, "movies/"));
    }

    @PostMapping("/addrating/{movieId}/{userId}")
    public ResponseEntity<MovieDTO> addRatingToMovie(@PathVariable("movieId") Integer movieId,
            @PathVariable("userId") Integer userId, @RequestBody Integer rating) {
        MovieDTO movieDTO = movieServiceImpl.addRatingToMovie(movieId, userId, rating);
        return ResponseEntity.ok(movieDTO);
    }

    @PostMapping("/search")
    public ResponseEntity<List<MovieDTO>> searchMovies(@RequestBody String searchQuery) {
        List<MovieDTO> movieDTOList = movieServiceImpl.searchMovie(searchQuery);
        return ResponseEntity.ok(movieDTOList);
    }
}
