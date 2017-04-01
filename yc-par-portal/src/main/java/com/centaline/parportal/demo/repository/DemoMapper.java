/*
 * Copyright (c) 2016.
 */

package com.centaline.parportal.demo.repository;

import com.centaline.parportal.demo.entity.Demo;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by linjiarong on 2016/10/12.
 */

public interface DemoMapper {

    @Select("select * from sctrans.demo where pkid = #{pkid}")
    public Demo findByPkid( @Param("pkid") Integer pkid);

}
