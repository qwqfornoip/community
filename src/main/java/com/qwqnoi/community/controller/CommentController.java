package com.qwqnoi.community.controller;

import com.qwqnoi.community.dto.CommentDTO;
import com.qwqnoi.community.dto.ResultDTO;
import com.qwqnoi.community.exception.CustomizeErrorCode;
import com.qwqnoi.community.mapper.CommentMapper;
import com.qwqnoi.community.model.Comment;
import com.qwqnoi.community.model.Usr;
import com.qwqnoi.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class CommentController {

    @Autowired(required = false)
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {
        Usr usr = (Usr) request.getSession().getAttribute("usr");
        if (usr == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOG_IN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(usr.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
