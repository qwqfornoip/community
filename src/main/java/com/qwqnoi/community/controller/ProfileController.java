package com.qwqnoi.community.controller;

import com.qwqnoi.community.dto.PaginationDTO;
import com.qwqnoi.community.model.Usr;
import com.qwqnoi.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired(required=false)
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable String action, Model model, HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "16") Integer size){
        Usr usr = (Usr) request.getSession().getAttribute("usr");
        if (usr == null) return "redirect:/";

        if ("question".equals(action)){
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的问题");
        }else if ("replies".equals(action)){
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }else {
            model.addAttribute("sectionName", "profile");
        }

        PaginationDTO paginationDTO = questionService.list(usr.getId(), page, size);
        model.addAttribute("pagination", paginationDTO);
        return "profile";
    }
}
