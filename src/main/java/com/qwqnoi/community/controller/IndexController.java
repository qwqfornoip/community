package com.qwqnoi.community.controller;

import com.qwqnoi.community.dto.QuestionDTO;
import com.qwqnoi.community.mapper.UsrMapper;
import com.qwqnoi.community.model.Question;
import com.qwqnoi.community.model.Usr;
import com.qwqnoi.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired(required=false)
    private UsrMapper usrMapper;

    @Autowired(required=false)
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null || cookies.length != 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    Usr usr = usrMapper.findByToken(token);
                    if (usr != null) {
                        request.getSession().setAttribute("usr", usr);
                    }
                    break;
                }
            }

        List<QuestionDTO> questionList = questionService.list();
        model.addAttribute("questions", questionList);

        return "index";
    }
}
