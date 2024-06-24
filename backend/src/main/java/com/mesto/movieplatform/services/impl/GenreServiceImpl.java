package com.mesto.movieplatform.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mesto.movieplatform.dtoes.GenreDTO;
import com.mesto.movieplatform.repository.GenreRepository;
import com.mesto.movieplatform.services.GenreService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<GenreDTO> fetchAllGenres() {
        return genreRepository.findAll().stream().map(genre -> {
            GenreDTO genreDTO = new GenreDTO();
            genreDTO.setId(genre.getId());
            genreDTO.setName(genre.getName());
            return genreDTO;
        }).collect(Collectors.toList());
    }

}
