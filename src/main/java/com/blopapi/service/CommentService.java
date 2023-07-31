package com.blopapi.service;

import com.blopapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    public List<CommentDto> findCommentByPostId(Long postId);

    void deleteCommentByPostId(long postId, long id);

    CommentDto getCommentByPostId(long postId, long id);


    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);
}
