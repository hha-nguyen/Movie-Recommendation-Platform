package com.mesto.movieplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.apigateway.model.Op;
import com.mesto.movieplatform.entities.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c JOIN c.user u WHERE u.id = :userId")
    Optional<List<Comment>> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT c FROM Comment c JOIN c.movie m WHERE m.id = :id")
    Optional<List<Comment>> findByMovieId(@Param("id") Integer id);

    Optional<Comment> findByCommentAtAfter(LocalDateTime commentAt);

    Optional<Comment> findByContentContaining(String content);

}
