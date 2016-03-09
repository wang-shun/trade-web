package com.centaline.trans.task.service;

import java.util.List;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.task.entity.ToHouseTransfer;
import com.centaline.trans.task.vo.LoanlostApproveVO;

public interface ToHouseTransferService {
	
	boolean saveToHouseTransfer(ToHouseTransfer toHouseTransfer);
	
	ToHouseTransfer findToGuoHuByCaseCode(String caseCode);
	
	void saveToHouseTransferAndMort(ToHouseTransfer toHouseTransfer,ToMortgage toMortgage);

	/**
	 * 统计过户
	 * @param userId
	 * @return
	 */
	ToCaseInfoCountVo countToHouseTransferById(String userId);

	/**
	 * 查询统计过户
	 * @param toCase
	 * @return
	 */
	ToCaseInfoCountVo queryCountToHouseTransferById(ToCase toCase);

	/**
	 * 过户数统计查询
	 * @param orgId
	 * @return
	 */
	ToCaseInfoCountVo countToHouseTransferByOrgId(String orgId,String startDate,String endDate);

	/**
	 * 过户数统计查询
	 * @param toCase
	 * @return
	 */
	ToCaseInfoCountVo queryCountToHouseTransferByOrg(ToCase toCase);

	/**
	 * 过户数多条统计查询
	 * @param toCase
	 * @return
	 */
	List<ToCaseInfoCountVo> countToHouseTransferListByOrgId(String orgId);

	/**
	 * 查询统计数据
	 * @param orgList
	 * @return
	 */
	List<ToCaseInfoCountVo> countToHouseTransferListByOrgList(
			List<String> orgList);

	/**
	 * 查询统计数
	 * @param strList
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int countToHouseTransferByOrgList(List<String> strList, String startDate,
			String endDate);

	/**
	 * 初始化统计数据
	 * @param orgId
	 * @return
	 */
	int initCountToHouseTransferByOrgId(String orgId,String createTime);

	/**
	 * 查询统计
	 * @param idList
	 * @return
	 */
	List<ToCaseInfoCountVo> countToHouseTransferListByIdList(List<String> idList);
	/**
	 * 查询统计
	 * @param idList
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int countToHouseTransferByIdList(List<String> idList, String startDate,	String endDate);
	
	/**
	 * 完成过户操作
	 * @param toHouseTransfer
	 * @param toMortgage
	 * @param loanlostApproveVO
	 * @param taskId
	 * @param processInstanceId
	 */
	String submitToHouseTransfer(ToHouseTransfer toHouseTransfer,ToMortgage toMortgage,LoanlostApproveVO loanlostApproveVO, String taskId, String processInstanceId);
}
