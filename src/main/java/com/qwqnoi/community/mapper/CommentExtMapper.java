package com.qwqnoi.community.mapper;

import com.qwqnoi.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment record);
}