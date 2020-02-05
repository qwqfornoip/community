package com.qwqnoi.community.controller;

import com.qwqnoi.community.mapper.UsrMapper;
import com.qwqnoi.community.model.Usr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UsrMapper usrMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                Usr usr = usrMapper.findByToken(token);
                if (usr != null){
                    request.getSession().setAttribute("usr", usr);
                }
                break;
            }
        }
        return "index";
    }
}
