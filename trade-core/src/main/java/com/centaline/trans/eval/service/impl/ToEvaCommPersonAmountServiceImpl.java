package com.centaline.trans.eval.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.api.service.CommissionAssignApiService;
import com.centaline.trans.api.vo.ApiCommissionAssign;
import com.centaline.trans.api.vo.ApiCommissionAssign.SharingInfo;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.eval.entity.ToEvaCommPersonAmount;
import com.centaline.trans.eval.entity.ToEvaCommissionChange;
import com.centaline.trans.eval.repository.ToEvaCommPersonAmountMapper;
import com.centaline.trans.eval.repository.ToEvaCommissionChangeMapper;
import com.centaline.trans.eval.service.ToEvaCommPersonAmountService;
import com.centaline.trans.eval.vo.EvalChangeCommVO;
/**
 * @author xiefei1
 * @since 2017年10月11日 下午3:14:04 
 * @description 调佣对象与调佣金额
 */

@Service
public class ToEvaCommPersonAmountServiceImpl implements ToEvaCommPersonAmountService {
	@Autowired
	private ToEvaCommPersonAmountMapper toEvaCommPersonAmountMapper; 
	@Autowired
	private ToEvaCommissionChangeMapper toEvaCommissionChangeMapper; 
	@Autowired
	private ToCaseService toCaseService; 
	@Autowired
	private CommissionAssignApiService commissionAssignApiService; 
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toEvaCommPersonAmountMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(ToEvaCommPersonAmount record) {
		// TODO Auto-generated method stub
		return toEvaCommPersonAmountMapper.insert(record);
	}

	@Override
	public int insertSelective(ToEvaCommPersonAmount record) {
		// TODO Auto-generated method stub
		return toEvaCommPersonAmountMapper.insertSelective(record);
	}

	@Override
	public ToEvaCommPersonAmount selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toEvaCommPersonAmountMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvaCommPersonAmount record) {
		// TODO Auto-generated method stub
		return toEvaCommPersonAmountMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ToEvaCommPersonAmount record) {
		// TODO Auto-generated method stub
		return toEvaCommPersonAmountMapper.updateByPrimaryKey(record);
	}

	/* 获取完整的调佣对象与调佣金额VO
	 * 版本 从DB获取调佣对象与调佣金额
	 * @see com.centaline.trans.eval.service.ToEvaCommPersonAmountService#getFullEvalChangeCommVO(java.lang.String)
	 */
//	@Override
//	public EvalChangeCommVO getFullEvalChangeCommVO(String caseCode) {
//		if(caseCode==null||caseCode==""){
//			throw new BusinessException("caseCode案件编码为空，请检查！");
//		}
//		EvalChangeCommVO evalChangeCommVO = new EvalChangeCommVO(caseCode);
//		List<ToEvaCommPersonAmount> ToEvaCommPersonAmountList = toEvaCommPersonAmountMapper.selectByCasecode(caseCode);
//		for (ToEvaCommPersonAmount toEvaCommPersonAmount : ToEvaCommPersonAmountList) {
//			if(toEvaCommPersonAmount.getPosition().contains("权证")){
//				evalChangeCommVO.getWarrantPersonList().add(toEvaCommPersonAmount);
//			}else if(toEvaCommPersonAmount.getPosition().contains("分成")){
//				evalChangeCommVO.getSharePersonList().add(toEvaCommPersonAmount);
//			}else{
//				evalChangeCommVO.getCoPersonList().add(toEvaCommPersonAmount);
//			}
//		}
//		ToEvaCommissionChange toEvaCommissionChange = toEvaCommissionChangeMapper.selectByCaseCode(caseCode);
//		if(null!=toEvaCommissionChange){
//			evalChangeCommVO.setTtlComm(toEvaCommissionChange.getCommisionTtlAmount());
//			evalChangeCommVO.setDealCount(1);
//			evalChangeCommVO.setChangeChargesCause(toEvaCommissionChange.getChangeChargesCause());
//			evalChangeCommVO.setChangeChargesItem(toEvaCommissionChange.getChangeChargesItem());
//			evalChangeCommVO.setChangeChargesType(toEvaCommissionChange.getChangeChargesType());		
//			evalChangeCommVO.setAgEvalAmount(toEvaCommissionChange.getAgEvalAmount());
//		}
//		return evalChangeCommVO;
//	}
	/* 获取完整的调佣对象与调佣金额VO
	 * 版本 从CCAI获取调佣对象与调佣金额
	 * @see com.centaline.trans.eval.service.ToEvaCommPersonAmountService#getFullEvalChangeCommVO(java.lang.String)
	 */
//	@Deprecated
//	@Override
//	public EvalChangeCommVO getFullEvalChangeCommVO(String caseCode) {
//		if(caseCode==null||caseCode==""){
//			throw new BusinessException("caseCode案件编码为空，请检查！");
//		}
//		EvalChangeCommVO evalChangeCommVO = new EvalChangeCommVO(caseCode);
//		ToCase tocase = toCaseService.findToCaseByCaseCode(caseCode);
//		String ccaiCode = tocase.getCcaiCode();
//		ApiCommissionAssign apiCommissionAssign = commissionAssignApiService.getApiCommissionAssign(ccaiCode);
//		if(null!=apiCommissionAssign){
//			BigDecimal ttlComm = evalChangeCommVO.getTtlComm();
//			if(null==ttlComm){
//				ttlComm=new BigDecimal("0");
//			}
//			List<ToEvaCommPersonAmount> warrantPersonList = evalChangeCommVO.getWarrantPersonList();
//			List<ToEvaCommPersonAmount> sharePersonList = evalChangeCommVO.getSharePersonList();
//			List<SharingInfo> sharingInfo = apiCommissionAssign.getSharingInfo();
//			for (SharingInfo sharingInfo2 : sharingInfo) {
//				//计算佣金总额
//				BigDecimal sharingAmount = sharingInfo2.getSharingAmount();
//				ttlComm=ttlComm.add(sharingInfo2.getSharingAmount());
//				evalChangeCommVO.setTtlComm(ttlComm);
//				//区分权证和分成人 type>1:分成人 2:权证
//				if(sharingInfo2.getType()==2){
//					ToEvaCommPersonAmount toEvaCommPersonAmount = new ToEvaCommPersonAmount();
//					toEvaCommPersonAmount.setPosition("权证");
//					toEvaCommPersonAmount.setDepartment(sharingInfo2.getDepartment());
//					toEvaCommPersonAmount.setEmployeeName(sharingInfo2.getEmployee());
//					toEvaCommPersonAmount.setShareAmount(sharingInfo2.getSharingAmount());
//					toEvaCommPersonAmount.setShareReason(sharingInfo2.getSharingInstruction());
//					toEvaCommPersonAmount.setDealCount(sharingInfo2.getTurnoverNum().intValue());
//					warrantPersonList.add(toEvaCommPersonAmount);
//				}else{
//					//从CCAI获取调佣对象与调佣金额只有两种情况，不是分成人就是权证
//					ToEvaCommPersonAmount toEvaCommPersonAmount = new ToEvaCommPersonAmount();
//					toEvaCommPersonAmount.setPosition("分成人");
//					toEvaCommPersonAmount.setDepartment(sharingInfo2.getDepartment());
//					toEvaCommPersonAmount.setEmployeeName(sharingInfo2.getEmployee());
//					toEvaCommPersonAmount.setShareAmount(sharingInfo2.getSharingAmount());
//					toEvaCommPersonAmount.setShareReason(sharingInfo2.getSharingInstruction());
//					toEvaCommPersonAmount.setDealCount(sharingInfo2.getTurnoverNum().intValue());
//					sharePersonList.add(toEvaCommPersonAmount);
//				}
//			}	
//		}
//		ToEvaCommissionChange toEvaCommissionChange = toEvaCommissionChangeMapper.selectByCaseCode(caseCode);
//		if(null!=toEvaCommissionChange){
//			evalChangeCommVO.setTtlComm(toEvaCommissionChange.getCommisionTtlAmount());
//			evalChangeCommVO.setDealCount(1);
//			evalChangeCommVO.setChangeChargesCause(toEvaCommissionChange.getChangeChargesCause());
//			evalChangeCommVO.setChangeChargesItem(toEvaCommissionChange.getChangeChargesItem());
//			evalChangeCommVO.setChangeChargesType(toEvaCommissionChange.getChangeChargesType());		
//			evalChangeCommVO.setAgEvalAmount(toEvaCommissionChange.getAgEvalAmount());
//		}
//		return evalChangeCommVO;	
//	}

	@Override
	/**保存调佣对象与调佣金额VO
	 * save**是有事务的
	 * ToEvaCommissionChange&EvalChangeCommVO有重叠的属性，前台以EvalChangeCommVO为主，所以这里只判断EvalChangeCommVO；
	 */
	public void saveEvalChangeCommVO(EvalChangeCommVO evalChangeCommVO,ToEvaCommissionChange toEvaCommissionChange) {
		String caseCode = evalChangeCommVO.getCaseCode();
		ArrayList<ToEvaCommPersonAmount> toEvaCommPersonAmountList = new ArrayList<ToEvaCommPersonAmount>();

		List<ToEvaCommPersonAmount> coPersonList = evalChangeCommVO.getCoPersonList();
		for (ToEvaCommPersonAmount toEvaCommPersonAmount : coPersonList) {
			toEvaCommPersonAmountList.add(toEvaCommPersonAmount);
		}
		List<ToEvaCommPersonAmount> sharePersonList = evalChangeCommVO.getSharePersonList();
		for (ToEvaCommPersonAmount toEvaCommPersonAmount : sharePersonList) {
			toEvaCommPersonAmountList.add(toEvaCommPersonAmount);
		}
		List<ToEvaCommPersonAmount> warrantPersonList = evalChangeCommVO.getWarrantPersonList();
		for (ToEvaCommPersonAmount toEvaCommPersonAmount : warrantPersonList) {
			toEvaCommPersonAmountList.add(toEvaCommPersonAmount);
		}
		List<ToEvaCommPersonAmount> selectByCasecode = toEvaCommPersonAmountMapper.selectByCasecode(caseCode);
		
		//如果有pkid就update,如果没有pkid就insert
		for (ToEvaCommPersonAmount toEvaCommPersonAmount : toEvaCommPersonAmountList) {
			Long pkid = toEvaCommPersonAmount.getPkid();
			if(null!=pkid){
				toEvaCommPersonAmountMapper.updateByPrimaryKeySelective(toEvaCommPersonAmount);					
			}else{
				toEvaCommPersonAmount.setCaseCode(caseCode);
				toEvaCommPersonAmountMapper.insertSelective(toEvaCommPersonAmount);
			}
		}			
				
//		保存EvalChangeCommVO中的 ttlComm dealCount；
		BigDecimal ttlComm = evalChangeCommVO.getTtlComm();
		if(null!=ttlComm){
			toEvaCommissionChange.setCommisionTtlAmount(ttlComm);			
		}

//		ToEvaCommissionChange中主要使用了两个属性：调佣事由和调佣类型
		if(null!=toEvaCommissionChange&&toEvaCommissionChange.getCaseCode()!=null){
			toEvaCommissionChangeMapper.updateByCaseCodeSelective(toEvaCommissionChange);			
		}

	}
	/* 获取完整的调佣对象与调佣金额VO
	 * 版本 从CCAI获取调佣对象与调佣金额
	 * @see com.centaline.trans.eval.service.ToEvaCommPersonAmountService#getFullEvalChangeCommVO(java.lang.String)
	 */
	@Override
	public EvalChangeCommVO getFullEvalChangeCommVOFromCCAI(String caseCode) {
		if(caseCode==null||caseCode==""){
			throw new BusinessException("caseCode案件编码为空，请检查！");
		}
		EvalChangeCommVO evalChangeCommVO = new EvalChangeCommVO(caseCode);
		ToCase tocase = toCaseService.findToCaseByCaseCode(caseCode);
		String ccaiCode = tocase.getCcaiCode();
		ApiCommissionAssign apiCommissionAssign = commissionAssignApiService.getApiCommissionAssign(ccaiCode);
		if(null!=apiCommissionAssign){
			BigDecimal ttlComm = evalChangeCommVO.getTtlComm();
			if(null==ttlComm){
				ttlComm=new BigDecimal("0");
			}
			List<ToEvaCommPersonAmount> warrantPersonList = evalChangeCommVO.getWarrantPersonList();
			List<ToEvaCommPersonAmount> sharePersonList = evalChangeCommVO.getSharePersonList();
			List<SharingInfo> sharingInfo = apiCommissionAssign.getSharingInfo();
			for (SharingInfo sharingInfo2 : sharingInfo) {
				//计算佣金总额
				BigDecimal sharingAmount = sharingInfo2.getSharingAmount();
				ttlComm=ttlComm.add(sharingInfo2.getSharingAmount());
				evalChangeCommVO.setTtlComm(ttlComm);
				//区分权证和分成人 type>1:分成人 2:权证
				if(sharingInfo2.getType()==2){
					ToEvaCommPersonAmount toEvaCommPersonAmount = new ToEvaCommPersonAmount();
					toEvaCommPersonAmount.setPosition("权证");
					toEvaCommPersonAmount.setDepartment(sharingInfo2.getDepartment());
					toEvaCommPersonAmount.setEmployeeName(sharingInfo2.getEmployee());
					toEvaCommPersonAmount.setShareAmount(sharingInfo2.getSharingAmount());
					toEvaCommPersonAmount.setShareReason(sharingInfo2.getSharingInstruction());
					//注释by xiefei1 2017-11-09			防止出现这里为空指针,已经在ApiCommissionAssign中判断过
					toEvaCommPersonAmount.setDealCount(sharingInfo2.getTurnoverNum().intValue());
					warrantPersonList.add(toEvaCommPersonAmount);
				}else{
					//从CCAI获取调佣对象与调佣金额只有两种情况，不是分成人就是权证
					ToEvaCommPersonAmount toEvaCommPersonAmount = new ToEvaCommPersonAmount();
					toEvaCommPersonAmount.setPosition("分成人");
					toEvaCommPersonAmount.setDepartment(sharingInfo2.getDepartment());
					toEvaCommPersonAmount.setEmployeeName(sharingInfo2.getEmployee());
					toEvaCommPersonAmount.setShareAmount(sharingInfo2.getSharingAmount());
					toEvaCommPersonAmount.setShareReason(sharingInfo2.getSharingInstruction());
					//注释by xiefei1 2017-11-09			防止出现这里为空指针,已经在ApiCommissionAssign中判断过
					toEvaCommPersonAmount.setDealCount(sharingInfo2.getTurnoverNum().intValue());
					sharePersonList.add(toEvaCommPersonAmount);
				}
			}	
		}
		ToEvaCommissionChange toEvaCommissionChange = toEvaCommissionChangeMapper.selectByCaseCode(caseCode);
		if(null!=toEvaCommissionChange){
			evalChangeCommVO.setTtlComm(toEvaCommissionChange.getCommisionTtlAmount());
			evalChangeCommVO.setDealCount(1);
			evalChangeCommVO.setChangeChargesCause(toEvaCommissionChange.getChangeChargesCause());
			evalChangeCommVO.setChangeChargesItem(toEvaCommissionChange.getChangeChargesItem());
			evalChangeCommVO.setChangeChargesType(toEvaCommissionChange.getChangeChargesType());		
			evalChangeCommVO.setAgEvalAmount(toEvaCommissionChange.getAgEvalAmount());
		}
		return evalChangeCommVO;	
	}
	@Override
	/* 获取完整的调佣对象与调佣金额VO
	 * 版本 从DB获取调佣对象与调佣金额
	 * @see com.centaline.trans.eval.service.ToEvaCommPersonAmountService#getFullEvalChangeCommVO(java.lang.String)
	 */
	public EvalChangeCommVO getFullEvalChangeCommVOFromDB(String caseCode){
		if(caseCode==null||caseCode==""){
		throw new BusinessException("caseCode案件编码为空，请检查！");
	}
	EvalChangeCommVO evalChangeCommVO = new EvalChangeCommVO(caseCode);
	List<ToEvaCommPersonAmount> ToEvaCommPersonAmountList = toEvaCommPersonAmountMapper.selectByCasecode(caseCode);
	for (ToEvaCommPersonAmount toEvaCommPersonAmount : ToEvaCommPersonAmountList) {
		if(toEvaCommPersonAmount.getPosition().contains("权证")){
			evalChangeCommVO.getWarrantPersonList().add(toEvaCommPersonAmount);
		}else if(toEvaCommPersonAmount.getPosition().contains("分成")){
			evalChangeCommVO.getSharePersonList().add(toEvaCommPersonAmount);
		}else{
			evalChangeCommVO.getCoPersonList().add(toEvaCommPersonAmount);
		}
	}
	ToEvaCommissionChange toEvaCommissionChange = toEvaCommissionChangeMapper.selectByCaseCode(caseCode);
	if(null!=toEvaCommissionChange){
		evalChangeCommVO.setTtlComm(toEvaCommissionChange.getCommisionTtlAmount());
		evalChangeCommVO.setDealCount(1);
		evalChangeCommVO.setChangeChargesCause(toEvaCommissionChange.getChangeChargesCause());
		evalChangeCommVO.setChangeChargesItem(toEvaCommissionChange.getChangeChargesItem());
		evalChangeCommVO.setChangeChargesType(toEvaCommissionChange.getChangeChargesType());		
		evalChangeCommVO.setAgEvalAmount(toEvaCommissionChange.getAgEvalAmount());
	}
	return evalChangeCommVO;
}

	@Override
	public EvalChangeCommVO getFullEvalChangeCommVO(String caseCode) {
		if(caseCode==null||caseCode==""){
			throw new BusinessException("caseCode案件编码为空，请检查！");
		}
		EvalChangeCommVO evalChangeCommVO = new EvalChangeCommVO(caseCode);
		List<ToEvaCommPersonAmount> toEvaCommPersonAmountList = toEvaCommPersonAmountMapper.selectByCasecode(caseCode);
//		如果DB已经有调佣信息就从DB取，如果没有则从CCAI获取
		if(!CollectionUtils.isNotEmpty(toEvaCommPersonAmountList)){
			return getFullEvalChangeCommVOFromCCAI(caseCode);
		}else{
			return getFullEvalChangeCommVOFromDB(caseCode);			
		}
	}

}
