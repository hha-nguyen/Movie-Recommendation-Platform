package com.mesto.movieplatform.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mesto.movieplatform.entities.VideoChunk;

public interface VideoChunkRepository extends JpaRepository<VideoChunk, Integer> {
    VideoChunk findByMovieIdAndChunkOrder(Integer movieId, Integer chunkNumber);

    List<byte[]> getVideoChunksByMovieId(Integer movieId);

    List<Integer> getChunkOrdersByMovieId(Integer movieId);

    byte[] getVideoChunkByMovieIdAndChunkOrder(Integer movieId, Integer chunkNumber);
}
