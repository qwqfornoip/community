package com.qwqnoi.community.advice;

import com.qwqnoi.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice()
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable ex, Model model) {
        if (ex instanceof CustomizeException)
            model.addAttribute("message", ex.getMessage());
        else
            model.addAttribute("message", "你似乎来到了没有知识的荒原......");
        return new ModelAndView("error");
    }
}
