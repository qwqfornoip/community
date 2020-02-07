package com.qwqnoi.community.service;

import com.qwqnoi.community.mapper.UsrMapper;
import com.qwqnoi.community.model.Usr;
import com.qwqnoi.community.model.UsrExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsrService {
    @Autowired(required = false)
    private UsrMapper usrMapper;

    public void createOrUpdate(Usr usr) {
        UsrExample usrExample = new UsrExample();
        usrExample.createCriteria().andAccountIdEqualTo(usr.getAccountId());
        List<Usr> usrList = usrMapper.selectByExample(usrExample);
        if (usrList.size() == 0){
            //插入
            usr.setGmtCreate(System.currentTimeMillis());
            usr.setGmtModified(usr.getGmtCreate());
            usrMapper.insert(usr);
        } else {
            //更新
            Usr updateUsr = new Usr();
            updateUsr.setGmtModified(System.currentTimeMillis());
            updateUsr.setAvatarUrl(usr.getAvatarUrl());
            updateUsr.setName(usr.getName());
            updateUsr.setToken(usr.getToken());
            UsrExample example = new UsrExample();
            example.createCriteria().andIdEqualTo(usrList.get(0).getId());
            usrMapper.updateByExampleSelective(updateUsr, example);
        }
    }
}
