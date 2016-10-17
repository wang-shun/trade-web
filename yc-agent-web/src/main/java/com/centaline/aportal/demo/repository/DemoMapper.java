/*
 * Copyright (c) 2016.
 */

package com.centaline.aportal.demo.repository;

import com.centaline.aportal.demo.entity.Demo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by linjiarong on 2016/10/12.
 */
@Mapper
public interface DemoMapper {

    @Select("select * from sctrans.demo where pkid = #{pkid}")
    public Demo findByPkid( @Param("pkid") Integer pkid);

}
