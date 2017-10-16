package com.centaline.trans.eval.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
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
//	获取完整的调佣对象与调佣金额VO
	@Override
	public EvalChangeCommVO getFullEvalChangeCommVO(String caseCode) {
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
		evalChangeCommVO.setTtlComm(toEvaCommissionChange.getCommisionTtlAmount());
		evalChangeCommVO.setDealCount(1);
		return evalChangeCommVO;
	}
//	保存调佣对象与调佣金额VO
//	save**是有事务的
	@Override
	/**
	 * ToEvaCommissionChange&EvalChangeCommVO有重叠的属性，前台以EvalChangeCommVO为主，所以这里只判断EvalChangeCommVO；
	 */
	public void saveEvalChangeCommVO(EvalChangeCommVO evalChangeCommVO,ToEvaCommissionChange toEvaCommissionChange) {
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
		
		for (ToEvaCommPersonAmount toEvaCommPersonAmount : toEvaCommPersonAmountList) {
			toEvaCommPersonAmountMapper.updateByPrimaryKeySelective(toEvaCommPersonAmount);
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
	


}
