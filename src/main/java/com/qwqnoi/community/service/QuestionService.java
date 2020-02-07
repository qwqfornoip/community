package com.qwqnoi.community.service;

import com.qwqnoi.community.dto.PaginationDTO;
import com.qwqnoi.community.dto.QuestionDTO;
import com.qwqnoi.community.exception.CustomizeErrorCode;
import com.qwqnoi.community.exception.CustomizeException;
import com.qwqnoi.community.mapper.QuestionExtMapper;
import com.qwqnoi.community.mapper.QuestionMapper;
import com.qwqnoi.community.mapper.UsrMapper;
import com.qwqnoi.community.model.Question;
import com.qwqnoi.community.model.QuestionExample;
import com.qwqnoi.community.model.Usr;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired(required=false)
    private QuestionMapper questionMapper;

    @Autowired(required=false)
    private UsrMapper usrMapper;

    @Autowired(required = false)
    private QuestionExtMapper questionExtMapper;

    //获取所有问题的列表
    private PaginationDTO getPaginationDTO(PaginationDTO paginationDTO, List<Question> questionList) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList){
            Usr usr = usrMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUsr(usr);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
    //页数处理
    private Integer CountPage(Integer page, Integer size, PaginationDTO paginationDTO, Integer totalCount) {
        Integer totalPage = totalCount / size;
        if (totalCount == 0 || totalCount % size != 0) totalPage++;
        if (page < 1) page = 1;
        if (page > totalPage) page = totalPage;
        paginationDTO.setPagination(page, totalPage);
        return size * (page-1); //offset
    }

    public PaginationDTO list(Integer page, Integer size) {
        //calculate the page
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = Math.toIntExact(questionMapper.countByExample(new QuestionExample()));

        Integer offset = CountPage(page, size, paginationDTO, totalCount);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds
                (new QuestionExample(), new RowBounds(offset, size));

        return getPaginationDTO(paginationDTO, questionList);
    }

    public PaginationDTO list(Integer usrId, Integer page, Integer size) {
        //calculate the page
        PaginationDTO paginationDTO = new PaginationDTO();

        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(usrId);
        Integer totalCount = Math.toIntExact(questionMapper.countByExample(example));

        Integer offset = CountPage(page, size, paginationDTO, totalCount);
        example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(usrId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds
                (example, new RowBounds(offset, size));

        return getPaginationDTO(paginationDTO, questionList);
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        Usr usr = usrMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUsr(usr);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        } else {
            //修改
            Question updateQuestion = new Question();
            //传入属性值
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTags(question.getTags());
            //使用Example将值传入sql语句
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int update = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if (update != 1) throw new CustomizeException(CustomizeErrorCode.SERVICE_ERROR);
        }
    }

    public void incView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
}
