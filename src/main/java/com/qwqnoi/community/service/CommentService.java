package com.qwqnoi.community.service;

import com.qwqnoi.community.enums.CommentTypeEnum;
import com.qwqnoi.community.exception.CustomizeErrorCode;
import com.qwqnoi.community.exception.CustomizeException;
import com.qwqnoi.community.mapper.CommentMapper;
import com.qwqnoi.community.mapper.QuestionExtMapper;
import com.qwqnoi.community.mapper.QuestionMapper;
import com.qwqnoi.community.model.Comment;
import com.qwqnoi.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired(required = false)
    private CommentMapper commentMapper;

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private QuestionExtMapper questionExtMapper;

    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUNT);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUNT);
            commentMapper.insert(comment);
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }
}
