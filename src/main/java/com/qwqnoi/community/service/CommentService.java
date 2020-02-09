package com.qwqnoi.community.service;

import com.qwqnoi.community.dto.CommentDTO;
import com.qwqnoi.community.enums.CommentTypeEnum;
import com.qwqnoi.community.exception.CustomizeErrorCode;
import com.qwqnoi.community.exception.CustomizeException;
import com.qwqnoi.community.mapper.*;
import com.qwqnoi.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired(required = false)
    private CommentMapper commentMapper;

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private QuestionExtMapper questionExtMapper;

    @Autowired(required = false)
    private UsrMapper usrMapper;

    @Autowired(required = false)
    private CommentExtMapper commentExMapper;

    @Transactional
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

            //增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExMapper.incCommentCount(parentComment);
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.isEmpty()) return new ArrayList<>();

        //获取去重评论人,转换成usrIds
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator())
                .collect(Collectors.toSet());
        List<Long> usrIds = new ArrayList<>();
        usrIds.addAll(commentators);

        //获取评论人,转换为map
        UsrExample usrExample = new UsrExample();
        usrExample.createCriteria().andIdIn(usrIds);
        List<Usr> usrs = usrMapper.selectByExample(usrExample);
        Map<Long, Usr> usrMap = usrs.stream().collect(Collectors.toMap(usr->usr.getId(), usr->usr));

        //转换comment为commentDTO
        List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUsr(usrMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOs;
    }
}
