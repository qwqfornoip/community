package com.qwqnoi.community.controller;

import com.qwqnoi.community.dto.AccessTokenDTO;
import com.qwqnoi.community.dto.GithubUser;
import com.qwqnoi.community.mapper.UsrMapper;
import com.qwqnoi.community.model.Usr;
import com.qwqnoi.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UsrMapper usrMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUsr(accessToken);
        //System.out.println("usr_name:" + githubUser.getName());
        if (githubUser != null){
            Usr usr = new Usr();
            usr.setToken(UUID.randomUUID().toString());
            usr.setName(githubUser.getName());
            usr.setAccountId(String.valueOf(githubUser.getId()));
            usr.setGmtCreate(System.currentTimeMillis());
            usr.setGmtModified(System.currentTimeMillis());
            usrMapper.insert(usr);
            //登录成功，写cookie和session
            request.getSession().setAttribute("usr", githubUser);
            return "redirect:/";
        } else {
            //登录失败，从新登录
            return "redirect:/";
        }
    }
}
