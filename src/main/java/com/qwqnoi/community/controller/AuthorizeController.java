package com.qwqnoi.community.controller;

import com.qwqnoi.community.dto.AccessTokenDTO;
import com.qwqnoi.community.dto.GithubUser;
import com.qwqnoi.community.model.Usr;
import com.qwqnoi.community.provider.GithubProvider;
import com.qwqnoi.community.service.UsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired(required=false)
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired(required = false)
    private UsrService usrService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUsr(accessToken);

        if (githubUser != null && githubUser.getId() != null){
            Usr usr = new Usr();
            String token = UUID.randomUUID().toString();
            usr.setToken(token);
            usr.setName(githubUser.getName());
            usr.setAccountId(String.valueOf(githubUser.getId()));
            usr.setAvatarUrl(githubUser.getAvatarUrl());
            usrService.createOrUpdate(usr);

            //登录成功，写cookie和session
            response.addCookie(new Cookie("token", token));
        } else {
            //登录失败，从新登录
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("usr");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
