package com.centaline.trans.evaPricing.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.evaPricing.entity.ToEvaPricingVo;
import com.centaline.trans.evaPricing.repository.ToEvaPricingMapper;
import com.centaline.trans.evaPricing.service.EvaPricingService;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.repository.ToEvalReportProcessMapper;
import com.centaline.trans.mortgage.entity.ToEguPropertyInfo;
import com.centaline.trans.utils.DateUtil;

/**
 * 询价serviceImpl
 * @author wbcaiyx
 *
 */
@Service
public class EvaPricingServiceImpl implements EvaPricingService{

	/**
	 * 询价编码前缀
	 */
	private static String EVA_CODE_PRE = "EvaCode";
	
	private static String DATE_FORMAT = "yyyyMMddHHmmssSSS";
	
	@Autowired
	ToEvaPricingMapper toEvaPricingMapper;
	@Autowired
	UamUserOrgService uamUserOrgService;
	@Autowired
	private ToEvalReportProcessMapper toEvalReportProcessMapper;

	@Override
	public ToEvaPricingVo findEvaPricingDetailByPKID(Long PKID,String evaCode) {
		ToEvaPricingVo vo = toEvaPricingMapper.findEvaPricingDetailByPKID(PKID,evaCode);
		if(StringUtils.isNotBlank(vo.getAriserId())){
			User user = uamUserOrgService.getUserById(vo.getAriserId());
			vo.setAriserName(user.getRealName());
			vo.setAriserOrgName(user.getOrgName());
		}
		
		return vo;
	}

	@Override
	public List<String> insertEvaPricing(ToEvaPricingVo vo) {
		BigDecimal evaPrice = vo.getTotalPrice();
		BigDecimal originPrice = vo.getOriginPrice();
		
		List<String> evaCodes = new ArrayList<String>();
		for(String finorgId :vo.getFinorgIds()){
			String evaCode = generateEvaCode();
			
			vo.setEvaCode(evaCode);
			vo.setTotalPrice(evaPrice.multiply(new BigDecimal(10000)));
			vo.setOriginPrice(originPrice == null?null:originPrice.multiply(new BigDecimal(10000)));
			vo.setCreateTime(new Date());
			vo.setFinorgId(finorgId);
			
			ToEguPropertyInfo propertyVo = new ToEguPropertyInfo();
			propertyVo.setEvaCode(evaCode);//询价编号
			propertyVo.setResidenceName(vo.getResidenceName());//产证地址
			propertyVo.setTotalFloor(vo.getTotalFloor());//总楼层
			propertyVo.setFloor(vo.getFloor());//所在楼层
			propertyVo.setArea(vo.getArea()==null?null:vo.getArea().doubleValue());//面积
			propertyVo.setCompleteYear(vo.getCompleteYear());//竣工年限
			propertyVo.setHouseAge(vo.getHouseAge());//房龄
			propertyVo.setRoom(vo.getRoom());//房型
			propertyVo.setHall(vo.getHall());
			propertyVo.setToilet(vo.getToilet());
			propertyVo.setPropType(vo.getPropType());//房屋类型
			
			Integer count =  toEvaPricingMapper.insertSelective(vo);
			if(count ==0 || count == null){
				throw new BusinessException("新增询价失败!");
			}
			Integer eguCount =  toEvaPricingMapper.insertEguPropertyInfoSelective(propertyVo);
			if(eguCount ==0 || eguCount == null){
				throw new BusinessException("新增询价物业失败!");
			}
			
			evaCodes.add(evaCode);
		}
		return evaCodes;
	}

	@Override
	public void updateEvaPricing(ToEvaPricingVo vo) {
		vo.setTotalPrice(vo.getTotalPrice()==null?vo.getTotalPrice():vo.getTotalPrice().multiply(new BigDecimal(10000)));
		Integer count = toEvaPricingMapper.updateEvaPricing(vo);
		if(count ==0 || count == null){
			throw new BusinessException("记录询价失败!");
		}
		Integer propCount = null;
		if("1".equals(vo.getIsValid())){
			propCount = toEvaPricingMapper.updateEguPropertyInfo(vo.getEvaCode(), vo.getHouseAge());
			if(propCount ==0 || propCount == null){
				throw new BusinessException("记录询价物业失败!");
			}
		}
		
		
	}
	
	@Override
	public int cancelByPKID(Long PKID) {
		Integer count = toEvaPricingMapper.cancelEvaPricingByPKID(PKID);
		if(count ==0 || count == null){
			throw new BusinessException("取消询价失败!");
		}
		return count;
	}

	@Override
	public boolean queryInfoByCase(String caseCode) {
		Integer count = toEvaPricingMapper.queryInfoByCaseCode(caseCode);
		if(count !=null && count > 0){
			return true;
		}
		return false;
	}
	
	@Override
	public List<Map<String, String>> queryEvaFinOrg() {
		
		return toEvaPricingMapper.queryEvaFinOrg();
	}
	
	@Override
	public boolean evalRelation(long pkid, String caseCode) {
		int count = toEvaPricingMapper.updateEvalPricingRela(pkid, caseCode);
		if(count >0){
			return true;
		}
		return false;
	}
	
	/**
	 * 自生成询价编号:EvaCodeyyyyMMddHHmmss
	 * @return
	 */
	private String generateEvaCode(){
		String dateString = DateUtil.getFormatDate(new Date(), DATE_FORMAT);
		return EVA_CODE_PRE+dateString;
	}

	@Override
	public ToEvaPricingVo findEvaPricingDetailByCaseCode(String caseCode) {
		return toEvaPricingMapper.findEvaPricingDetailByCaseCode(caseCode);
	}

	@Override
	public int updateEvaPricingDetail(Long pkid, String isValid, String reason) {
		int count = toEvaPricingMapper.updateEvaPricingDetail(pkid, isValid, reason.length()>255?reason.substring(0, 255):reason);
		return count;
	}

	@Override
	public AjaxResponse<Integer> checkEvalProcess(String caseCode) {
		AjaxResponse<Integer> result = new AjaxResponse<Integer>();
		
		//检查是否已有评估申请记录
		ToEvalReportProcess reportProcess = toEvalReportProcessMapper.findToEvalReportProcessByCaseCode(caseCode);		
		if(reportProcess != null){
			result.setSuccess(false);
			result.setMessage("系统已经存在此案件评估上报申请，请在评估申请中查询!");
			return result;
		}
		//检查是否已有询价申请记录及是否已完成,区分
		ToEvaPricingVo evaPricing =  toEvaPricingMapper.findEvaPricingDetailByCaseCode(caseCode);
		if(evaPricing != null){
			if("0".equals(evaPricing.getStatus())){
				result.setSuccess(false);
				result.setMessage("系统已存在与此案件相关的未完成的询价申请记录,请完成询价申请!");
				return result;
			}else if("1".equals(evaPricing.getStatus())){
				//1：询价已完成,可以评估申请
				result.setContent(1);
				return result;
			}
		}
		//2：无询价申请记录或询价无效,需要先询价
		result.setContent(2);
		return result;
	}


}
