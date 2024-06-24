package com.mesto.movieplatform.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.amplifybackend.model.Mode;
import com.mesto.movieplatform.dtoes.MovieDTO;
import com.mesto.movieplatform.entities.Movie;
import com.mesto.movieplatform.entities.VideoChunk;
import com.mesto.movieplatform.repository.MovieRepository;
import com.mesto.movieplatform.repository.VideoChunkRepository;
import com.mesto.movieplatform.services.VideoChunkService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class VideoChunkServiceImpl implements VideoChunkService {
    private static final int CHUNK_SIZE = 1024 * 1024; // 1MB chunk size

    @Autowired
    private VideoChunkRepository videoChunkRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private final ModelMapper mapper;

    @Override
    public String splitAndSaveVideoIntoChunks(MultipartFile file, MovieDTO movieDTO) throws IOException {
        Movie movie = mapper.map(movieDTO, Movie.class);
        InputStream inputStream = file.getInputStream();
        List<byte[]> chunks = splitVideoIntoChunks(inputStream);

        // Save each chunk in the database
        for (int i = 0; i < chunks.size(); i++) {
            VideoChunk videoChunk = new VideoChunk();
            videoChunk.setChunkOrder(i + 1);
            videoChunk.setData(chunks.get(i));
            videoChunk.setMovie(movie);
            videoChunkRepository.save(videoChunk);
        }

        return "Video split into " + chunks.size() + " chunks";
    }

    @Override
    public List<byte[]> getVideoChunksByMovieId(Integer movieId) {
        return videoChunkRepository.getVideoChunksByMovieId(movieId);
    }

    @Override
    public byte[] getVideoChunkByMovieIdAndChunkNumber(Integer movieId, Integer chunkNumber) {
        VideoChunk videoChunk = videoChunkRepository.findByMovieIdAndChunkOrder(movieId, chunkNumber);
        return videoChunk.getData();
    }

    private List<byte[]> splitVideoIntoChunks(InputStream inputStream) throws IOException {
        List<byte[]> chunks = new ArrayList<>();
        byte[] buffer = new byte[CHUNK_SIZE];
        int bytesRead;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                if (outputStream.size() >= CHUNK_SIZE) {
                    chunks.add(outputStream.toByteArray());
                    outputStream.reset();
                }
            }
            if (outputStream.size() > 0) {
                chunks.add(outputStream.toByteArray());
            }
        } finally {
            outputStream.close();
        }

        return chunks;
    }
}
