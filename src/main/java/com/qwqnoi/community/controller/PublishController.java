package com.qwqnoi.community.controller;

import com.qwqnoi.community.mapper.QuestionMapper;
import com.qwqnoi.community.mapper.UsrMapper;
import com.qwqnoi.community.model.Question;
import com.qwqnoi.community.model.Usr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired(required=false)
    private QuestionMapper questionMapper;
    @Autowired(required=false)
    private UsrMapper usrMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tags") String tags,
                            HttpServletRequest request,
                            Model model) {

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tags", tags);

        if (title.equals("")){
            model.addAttribute("error", "问题标题不能为空");
            return "publish";
        }if (description.equals("")){
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }if (tags.equals("")){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        Usr usr = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null || cookies.length != 0)
            for (Cookie cookie : cookies){
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    usr = usrMapper.findByToken(token);
                    if (usr != null){
                        request.getSession().setAttribute("usr", usr);
                    }
                    break;
                }
            }

        if (usr == null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTags(tags);
        question.setCreator(usr.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        questionMapper.create(question);
        return "redirect:/";
    }
}
