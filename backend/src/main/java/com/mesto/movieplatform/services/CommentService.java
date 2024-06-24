package com.mesto.movieplatform.services;

import com.mesto.movieplatform.dtoes.CommentDTO;
import com.mesto.movieplatform.entities.Comment;
import com.mesto.movieplatform.exceptions.CommentDetailsNotFoundException;

public interface CommentService {
    CommentDTO writeComment(CommentDTO commentDTO);

    Boolean deleteComment(Integer commentId);

    CommentDTO getComment(Integer commentId) throws CommentDetailsNotFoundException;

    CommentDTO editComment(Integer commentId, String content) throws CommentDetailsNotFoundException;
}
