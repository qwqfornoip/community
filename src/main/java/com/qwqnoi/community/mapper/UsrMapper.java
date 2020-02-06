package com.qwqnoi.community.mapper;

import com.qwqnoi.community.model.Usr;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface UsrMapper {
    @Insert("INSERT INTO usr (name, account_id, token, gmt_create, gmt_modified, avatar_url) " +
            "values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insert(Usr usr);

    @Select("SELECT * FROM usr WHERE token=#{token}")
    Usr findByToken(@Param("token") String token);

    @Select("SELECT * FROM usr WHERE id=#{id}")
    Usr findById(@Param("id") Integer creator);
}
