package com.mesto.movieplatform.services;

import java.io.IOException;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.mesto.movieplatform.dtoes.MovieDTO;
import com.mesto.movieplatform.entities.Movie;

public interface VideoChunkService {
    String splitAndSaveVideoIntoChunks(MultipartFile file, MovieDTO movieDTO) throws IOException;

    byte[] getVideoChunkByMovieIdAndChunkNumber(Integer movieId, Integer chunkNumber);

    List<byte[]> getVideoChunksByMovieId(Integer movieId);
}
