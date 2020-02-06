package com.qwqnoi.community.service;

import com.qwqnoi.community.dto.QuestionDTO;
import com.qwqnoi.community.mapper.QuestionMapper;
import com.qwqnoi.community.mapper.UsrMapper;
import com.qwqnoi.community.model.Question;
import com.qwqnoi.community.model.Usr;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired(required=false)
    private QuestionMapper questionMapper;

    @Autowired(required=false)
    private UsrMapper usrMapper;

    public List<QuestionDTO> list() {
        List<Question> questionList = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList){
            Usr usr = usrMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUsr(usr);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
