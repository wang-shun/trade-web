package com.centaline.parportal.mobile.login.service;

import com.centaline.parportal.mobile.login.entity.TAppEquipmentBindingDetail;

public interface TAppEquipmentBindingDetailService {

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public TAppEquipmentBindingDetail selectByPrimaryKey(Long id);
	
	/**
	 * 根据userId查询
	 * @return
	 */
	public TAppEquipmentBindingDetail selectByUserName(String userName);
	
	/**
	 * 根据设备编号查询
	 */
	public TAppEquipmentBindingDetail selectByEquipmentId(String equipmentId);
	
	/**
	 * 添加绑定手机
	 * @param tAppEquipmentBindingDetail
	 * @return
	 */
	public boolean insert(TAppEquipmentBindingDetail tAppEquipmentBindingDetail);
	
	/**
	 * 解绑
	 * @param id
	 * @return
	 */
	public boolean updateStatusByPrimaryKey(TAppEquipmentBindingDetail tAppEquipmentBindingDetail);
	
}
