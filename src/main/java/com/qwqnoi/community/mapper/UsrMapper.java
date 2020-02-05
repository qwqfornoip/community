package com.qwqnoi.community.mapper;

import com.qwqnoi.community.model.Usr;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsrMapper {
    @Insert("INSERT INTO communityschema.usr (name, account_id, token, gmt_create, gmt_modified) " +
            "values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified})")
    public void insert(Usr usr);
}
