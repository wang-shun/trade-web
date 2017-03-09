package com.centaline.parportal.mobile.login.repository;

import com.centaline.parportal.mobile.login.entity.TAppEquipmentBindingDetail;

/**
 * app 绑定及解绑
 * @author yumin3
 *
 */
public interface TAppEquipmentBindingDetailMapper {

    /**
     * 根据主键id删除信息
     * @return
     */
    int deleteByPrimaryKey(Long pkid) throws Exception;

    /**
     * 新增绑定信息
     * @return
     */
    int insert(TAppEquipmentBindingDetail record);

    /**
     * 根据主键id获得绑定信息
     * @return
     */
    TAppEquipmentBindingDetail selectByPrimaryKey(Long pkid);

    /**
     * 根据userId查询
     * @param userId
     * @return
     */
    TAppEquipmentBindingDetail selectByUserName(String userName);

    /**
     * 根据设备唯一标示查询
     * @param userId
     * @return
     */
    TAppEquipmentBindingDetail selectByEquipmentId(String equipmentId);

    /**
     * 修改解绑信息
     * @return
     */
    int updateByPrimaryKey(TAppEquipmentBindingDetail record);

    /**
     * 解绑 修改状态为无效
     * @return
     */
    int updateStatusByPrimaryKey(TAppEquipmentBindingDetail tAppEquipmentBindingDetail);
}