/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.parportal.mobile.login.repository.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.centaline.parportal.mobile.login.entity.TAppEquipmentBindingDetail;
import com.centaline.parportal.mobile.login.repository.TAppEquipmentBindingDetailMapper;

/**
 * 
 * @author wm
 * @version $Id: THmTrackMapperImpl.java, v 0.1 2015年6月23日 下午2:45:01 wm Exp $
 */
@Repository
public class TAppEquipmentBindingDetailMapperImpl implements TAppEquipmentBindingDetailMapper {

    @Autowired
    private SqlSessionTemplate session;

    /**Mapper名称  */
    final String               mapperName = "com.centaline.parportal.mobile.login.repository.TAppEquipmentBindingDetailMapper";

    /**
     * 获得MapperSqlid全称
     * @param mapperid
     * @return
     */
    private String getMapperSqlId(String mapperid) {
        return mapperName + "." + mapperid;
    }

    @Override
    public int deleteByPrimaryKey(Long pkid) throws Exception {
        return session.delete(getMapperSqlId("deleteByPrimaryKey"), pkid);
    }

    @Override
    public int insert(TAppEquipmentBindingDetail record) {
        return session.insert(getMapperSqlId("insert"), record);
    }

    @Override
    public TAppEquipmentBindingDetail selectByPrimaryKey(Long pkid) {
        return session.selectOne(getMapperSqlId("selectByPrimaryKey"), pkid);
    }

    @Override
    public TAppEquipmentBindingDetail selectByUserName(String userName) {
        return session.selectOne(getMapperSqlId("selectByUserId"), userName);
    }

    @Override
    public TAppEquipmentBindingDetail selectByEquipmentId(String equipmentId) {
        return session.selectOne(getMapperSqlId("selectByEquipmentId"), equipmentId);
    }

    @Override
    public int updateByPrimaryKey(TAppEquipmentBindingDetail record) {
        return session.update(getMapperSqlId("updateByPrimaryKey"), record);
    }

    @Override
    public int updateStatusByPrimaryKey(TAppEquipmentBindingDetail tAppEquipmentBindingDetail) {
        return session.update(getMapperSqlId("updateByPrimaryKeySelective"),
            tAppEquipmentBindingDetail);
    }

}
