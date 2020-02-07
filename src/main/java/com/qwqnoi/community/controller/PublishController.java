package com.qwqnoi.community.controller;

import com.qwqnoi.community.dto.QuestionDTO;
import com.qwqnoi.community.model.Question;
import com.qwqnoi.community.model.Usr;
import com.qwqnoi.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired(required = false)
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable Integer id, Model model){
        QuestionDTO question = questionService.getById(id);

        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tags", question.getTags());
        model.addAttribute("id", question.getId());

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam String title, @RequestParam String description,
                            @RequestParam String tags, @RequestParam Integer id,
                            HttpServletRequest request, Model model) {

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

        Usr usr = (Usr) request.getSession().getAttribute("usr");
        if (usr == null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTags(tags);
        question.setCreator(usr.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
