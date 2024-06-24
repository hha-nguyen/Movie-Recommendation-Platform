package com.mesto.movieplatform.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mesto.movieplatform.dtoes.CommentDTO;
import com.mesto.movieplatform.entities.Comment;
import com.mesto.movieplatform.exceptions.CommentDetailsNotFoundException;
import com.mesto.movieplatform.services.impl.CommentServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/comments")
public class CommentController {

    CommentServiceImpl commentServiceImpl;

    @PostMapping("")
    public ResponseEntity<CommentDTO> writeComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO myCommentDTO = commentServiceImpl.writeComment(commentDTO);
        return ResponseEntity.ok(myCommentDTO);
    }

    @GetMapping("")
    public ResponseEntity<List<CommentDTO>> getAllComments(
            @RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "movieId", required = false) Integer movieId)
            throws CommentDetailsNotFoundException {
        if (userId != null) {
            return ResponseEntity.ok(commentServiceImpl.getAllCommentsOfUser(userId));
        } else if (movieId != null) {
            return ResponseEntity.ok(commentServiceImpl.getAllCommentsOfMovie(movieId));
        } else {
            return ResponseEntity.ok(new ArrayList<CommentDTO>());
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable("commentId") Integer commentId) {
        Boolean flag = commentServiceImpl.deleteComment(commentId);
        return ResponseEntity.ok(flag);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> editMyComment(
            @PathVariable("commentId") Integer commentId, String content)
            throws CommentDetailsNotFoundException {
        CommentDTO commentDTO = commentServiceImpl.editComment(commentId, content);
        return ResponseEntity.ok(commentDTO);
    }
}
