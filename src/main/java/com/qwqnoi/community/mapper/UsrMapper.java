package com.qwqnoi.community.mapper;

import com.qwqnoi.community.model.Usr;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface UsrMapper {
    @Insert("INSERT INTO usr (name, account_id, token, gmt_create, gmt_modified) " +
            "values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified})")
    public void insert(Usr usr);

    @Select("SELECT * FROM usr WHERE token=#{token}")
    Usr findByToken(@Param("token") String token);
}
