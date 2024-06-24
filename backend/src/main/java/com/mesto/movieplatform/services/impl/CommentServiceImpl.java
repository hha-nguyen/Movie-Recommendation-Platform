package com.mesto.movieplatform.services.impl;

import lombok.AllArgsConstructor;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mesto.movieplatform.dtoes.CommentDTO;
import com.mesto.movieplatform.dtoes.MovieDTO;
import com.mesto.movieplatform.dtoes.UserDTO;
import com.mesto.movieplatform.entities.Comment;
import com.mesto.movieplatform.entities.Movie;
import com.mesto.movieplatform.entities.User;
import com.mesto.movieplatform.exceptions.CommentDetailsNotFoundException;
import com.mesto.movieplatform.repository.CommentRepository;
import com.mesto.movieplatform.repository.MovieRepository;
import com.mesto.movieplatform.repository.UserRepository;
import com.mesto.movieplatform.services.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CommentDTO writeComment(CommentDTO commentDTO) {
        Integer userId = commentDTO.getUserId();
        Integer movieId = commentDTO.getMovieId();
        User user = userRepository.findById(userId).get();
        Movie movie = movieRepository.findById(movieId).get();

        Comment comment = mapper.map(commentDTO, Comment.class);
        comment.setCommentAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setMovie(movie);

        comment = commentRepository.save(comment);

        // Add user id and movie id to the response DTO
        CommentDTO responseDTO = mapper.map(comment, CommentDTO.class);
        responseDTO.setUserId(user.getId());
        responseDTO.setMovieId(movie.getId());

        return responseDTO;
    }

    @Override
    public Boolean deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
        return true;
    }

    @Override
    public CommentDTO getComment(Integer commentId) throws CommentDetailsNotFoundException {
        Comment mycomment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentDetailsNotFoundException("Comment Details not found"));
        CommentDTO commentDTO = mapper.map(mycomment, CommentDTO.class);
        return commentDTO;
    }

    @Override
    public CommentDTO editComment(Integer commentId, String content) throws CommentDetailsNotFoundException {
        CommentDTO commentDTO = getComment(commentId);
        commentDTO.setContent(content);
        Comment comment = mapper.map(commentDTO, Comment.class);
        Comment updated = commentRepository.save(comment);
        return mapper.map(updated, CommentDTO.class);
    }

    public List<CommentDTO> getAllCommentsOfUser(Integer userId) throws CommentDetailsNotFoundException {
        List<Comment> commentList = commentRepository.findByUserId(userId)
                .orElseThrow(() -> new CommentDetailsNotFoundException("Passed User Id is invalid"));
        return commentList.stream()
                .map(comment -> {
                    CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
                    // add user id and movie id to the commentDTO
                    commentDTO.setUserId(comment.getUser().getId());
                    commentDTO.setMovieId(comment.getMovie().getId());

                    return commentDTO;
                })
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getAllCommentsOfMovie(Integer movieId) throws CommentDetailsNotFoundException {
        List<Comment> commentList = commentRepository.findByMovieId(movieId)
                .orElseThrow(() -> new CommentDetailsNotFoundException("Passed Movie Id is invalid"));
        return commentList.stream()
                .map(comment -> {
                    CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
                    // add user id and movie id to the commentDTO
                    commentDTO.setUserId(comment.getUser().getId());
                    commentDTO.setMovieId(comment.getMovie().getId());

                    return commentDTO;
                })
                .collect(Collectors.toList());

    }
}
