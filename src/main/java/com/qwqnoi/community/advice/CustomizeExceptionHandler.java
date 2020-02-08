package com.qwqnoi.community.advice;

import com.alibaba.fastjson.JSON;
import com.qwqnoi.community.dto.ResultDTO;
import com.qwqnoi.community.exception.CustomizeErrorCode;
import com.qwqnoi.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice()
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handle(Throwable ex, Model model,
                  HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            //返回json
            ResultDTO resultDTO;
            if (ex instanceof CustomizeException)
                resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            else
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SERVICE_ERROR);
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.write(JSON.toJSONString(resultDTO));
                out.close();
            } catch (IOException e){
            }
            return null;
        } else {
            //错误
            if (ex instanceof CustomizeException)
                model.addAttribute("message", ex.getMessage());
            else
                model.addAttribute("message", CustomizeErrorCode.SERVICE_ERROR.getMessage());
            return new ModelAndView("error");
        }
    }
}
