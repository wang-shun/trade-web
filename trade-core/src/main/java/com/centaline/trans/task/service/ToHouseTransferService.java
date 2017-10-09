package com.centaline.trans.task.service;

import java.util.List;
import com.aist.common.web.validate.AjaxResponse;
import javax.servlet.http.HttpServletRequest;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.task.entity.ToHouseTransfer;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.MortgageToSaveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

public interface ToHouseTransferService {
	
	boolean saveToHouseTransfer(ToHouseTransfer toHouseTransfer);
	
	ToHouseTransfer findToGuoHuByCaseCode(String caseCode);
	
	void saveToHouseTransferAndMort(ToHouseTransfer toHouseTransfer,ToMortgage toMortgage);

	/**
	 * 保存过户信息和贷款流失信息
	 * @param toHouseTransfer
	 * @param mortgageToSaveVO
	 */
	void savaToHouseTransferAndMortageToVO(ToHouseTransfer toHouseTransfer, MortgageToSaveVO mortgageToSaveVO);

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
	boolean submitToHouseTransfer(ToHouseTransfer toHouseTransfer,MortgageToSaveVO toMortgage,LoanlostApproveVO loanlostApproveVO, String taskId, String processInstanceId);

	/**
	 * 过户保存数据,提供给APP使得保存数据和提交流程分开的需求
	 * @author caoy
	 * @param toHouseTransfer
	 * @param toMortgage
	 * @param loanlostApproveVO
	 * @param processInstanceId
	 * @return
	 */
	AjaxResponse saveToHouseTransfer(ToHouseTransfer toHouseTransfer, ToMortgage toMortgage,LoanlostApproveVO loanlostApproveVO, String processInstanceId);

	/**
	 * 提交流程,提供给APP使得保存数据和提交流程分开的需求
	 * @author caoy
	 * @param toHouseTransfer
	 * @param taskId
	 * @param processInstanceId
	 * @return
	 */
	AjaxResponse submitToHouseTransfer(ToHouseTransfer toHouseTransfer,String taskId, String processInstanceId);
	
	Boolean guohuApprove(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String GuohuApprove, String GuohuApprove_response, String notApprove,
			String members);
   
}
