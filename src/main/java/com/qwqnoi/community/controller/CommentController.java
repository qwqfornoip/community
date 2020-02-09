package com.qwqnoi.community.controller;

import com.qwqnoi.community.dto.CommentCreateDTO;
import com.qwqnoi.community.dto.CommentDTO;
import com.qwqnoi.community.dto.ResultDTO;
import com.qwqnoi.community.enums.CommentTypeEnum;
import com.qwqnoi.community.exception.CustomizeErrorCode;
import com.qwqnoi.community.model.Comment;
import com.qwqnoi.community.model.Usr;
import com.qwqnoi.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired(required = false)
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        Usr usr = (Usr) request.getSession().getAttribute("usr");
        if (usr == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOG_IN);
        }

        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(usr.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable Long id) {
        return ResultDTO.okOf(commentService.listByTargetId(id, CommentTypeEnum.COMMENT));
    }
}
