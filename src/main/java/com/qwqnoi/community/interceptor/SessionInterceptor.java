package com.qwqnoi.community.interceptor;

import com.qwqnoi.community.mapper.UsrMapper;
import com.qwqnoi.community.model.Usr;
import com.qwqnoi.community.model.UsrExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired(required = false)
    private UsrMapper usrMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UsrExample usrExample = new UsrExample();
                    usrExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<Usr> usrList = usrMapper.selectByExample(usrExample);
                    if (usrList.size() != 0) {
                        request.getSession().setAttribute("usr", usrList.get(0));
                    }
                    break;
                }
            }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

    }
}
