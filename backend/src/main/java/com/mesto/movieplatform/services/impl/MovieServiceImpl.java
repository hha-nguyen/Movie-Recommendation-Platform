package com.mesto.movieplatform.services.impl;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mesto.movieplatform.dtoes.GenreDTO;
import com.mesto.movieplatform.dtoes.MovieDTO;
import com.mesto.movieplatform.entities.Genre;
import com.mesto.movieplatform.entities.Movie;
import com.mesto.movieplatform.entities.Rating;
import com.mesto.movieplatform.entities.User;
import com.mesto.movieplatform.exceptions.MovieDetailsNotFoundException;
import com.mesto.movieplatform.exceptions.UserDetailsNotFoundException;
import com.mesto.movieplatform.repository.GenreRepository;
import com.mesto.movieplatform.repository.MovieRepository;
import com.mesto.movieplatform.repository.RatingRepository;
import com.mesto.movieplatform.repository.UserRepository;
import com.mesto.movieplatform.services.MovieService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {
    // enum Genre {
    // ACTION, ADVENTURE, COMEDY, DOCUMENTARY, FAMILY, FANTASY, HORROR
    // }

    static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private final ModelMapper mapper;

    @Autowired
    private MovieRepository MovieRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public MovieDTO addMovie(MovieDTO movieDTO, List<Integer> genreIds) {
        LOGGER.debug("add movie with details" + movieDTO);
        LOGGER.info("add method movie has been triggered");

        Movie movie = mapper.map(movieDTO, Movie.class);
        List<Genre> genres = genreIds.stream().map(genreId -> genreRepository.findById(genreId).get())
                .collect(Collectors.toList());
        movie.setStreamingLink(movieDTO.getStreamingLink());
        movie.setMovieSaveDate(LocalDateTime.now());
        movie.setVoteCount(0);
        movie.setVoteAverage(0.0);
        movie.setGenres(genres);

        MovieRepository.save(movie);
        return mapper.map(movie, MovieDTO.class);
    }

    @Override
    public boolean deleteMovie(Integer id) {
        MovieRepository.deleteById(id);
        return true;
    }

    @Override
    public List<MovieDTO> searchMovie(String query) {
        List<Movie> movieList = MovieRepository.searchMovies(query);
        return movieList.stream().map(movie -> mapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> fetchAllMovies() {
        List<Movie> movieList = MovieRepository.findAll();
        return movieList.stream()
                .map(movie -> {
                    MovieDTO movieDTO = mapper.map(movie, MovieDTO.class);
                    List<GenreDTO> genreDTOList = movie.getGenres().stream()
                            .map(genre -> {
                                GenreDTO genreDTO = new GenreDTO();
                                genreDTO.setId(genre.getId());
                                genreDTO.setName(genre.getName());
                                return genreDTO;
                            })
                            .collect(Collectors.toList());
                    movieDTO.setGenres(genreDTOList);
                    return movieDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> fetchPopularMovies() {
        List<Movie> movieList = MovieRepository.findAll();
        // Get 10 movies have highest vote count
        return movieList.stream().sorted((m1, m2) -> m2.getVoteCount() - m1.getVoteCount()).limit(10)
                .map(movie -> mapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> fetchTopRatedMovies() {
        List<Movie> movieList = MovieRepository.findAll();
        // Get 10 movies have highest vote average
        return movieList.stream().sorted((m1, m2) -> Double.compare(m2.getVoteAverage(), m1.getVoteAverage())).limit(10)
                .map(movie -> mapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> fetchSimlarMovies(Integer movieId) {
        Movie movie = MovieRepository.findById(movieId).get();
        List<Genre> genres = movie.getGenres();
        List<Movie> movieList = MovieRepository.findAll();
        return movieList.stream().filter(m -> m.getGenres().containsAll(genres) && m.getId() != movieId)
                .map(m -> mapper.map(m, MovieDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> fetchMoviesByGenre(Integer genreId) {
        List<Movie> movieList = MovieRepository.findByGenreId(genreId);
        return movieList.stream().map(movie -> mapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> fetchMoviesByName(String movieName) {
        List<Movie> movieList = MovieRepository.findByTitle(movieName);
        return movieList.stream().map(movie -> mapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
    }

    @Override
    public MovieDTO fetchMovie(Integer movieId) throws MovieDetailsNotFoundException {
        Movie movie = MovieRepository.findById(movieId)
                .orElseThrow(() -> new MovieDetailsNotFoundException("Movie not found with id: " + movieId));
        MovieDTO movieDTO = mapper.map(movie, MovieDTO.class);
        List<GenreDTO> genreDTOList = movie.getGenres().stream()
                .map(genre -> {
                    GenreDTO genreDTO = new GenreDTO();
                    genreDTO.setId(genre.getId());
                    genreDTO.setName(genre.getName());
                    return genreDTO;
                })
                .collect(Collectors.toList());
        movieDTO.setGenres(genreDTOList);
        return movieDTO;
    }

    @Override
    public MovieDTO addGenresToMovie(Integer movieId, List<Integer> genreIds) {
        Movie movie = MovieRepository.findById(movieId).get();
        List<Genre> genres = genreIds.stream().map(genreId -> genreRepository.findById(genreId).get())
                .collect(Collectors.toList());
        movie.setGenres(genres);
        MovieRepository.save(movie);
        return mapper.map(movie, MovieDTO.class);
    }

    @Override
    public MovieDTO updateMovie(Integer movieId, MovieDTO movieDTO) {
        Movie movie = MovieRepository.findById(movieId).get();
        movie.setTitle(movieDTO.getTitle());
        movie.setOverview(movieDTO.getOverview());
        movie.setPosterPath(movieDTO.getPosterPath());
        movie.setStreamingLink(movieDTO.getStreamingLink());
        MovieRepository.save(movie);
        return mapper.map(movie, MovieDTO.class);
    }

    @Override
    public MovieDTO addRatingToMovie(Integer movieId, Integer userId, Integer score) {
        Movie movie = MovieRepository.findById(movieId).get();
        User user = userRepository.findUserById(userId).get();
        Rating rating = new Rating();
        rating.setScore(score);
        rating.setMovie(movie);
        rating.setUser(user);
        ratingRepository.save(rating);
        return mapper.map(movie, MovieDTO.class);
    }

}
