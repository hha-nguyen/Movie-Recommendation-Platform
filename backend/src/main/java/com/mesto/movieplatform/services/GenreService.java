package com.mesto.movieplatform.services;

import com.mesto.movieplatform.dtoes.GenreDTO;

import java.util.List;

public interface GenreService {
    List<GenreDTO> fetchAllGenres();
}
