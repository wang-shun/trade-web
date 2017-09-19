package com.centaline.trans.evaPricing.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.evaPricing.entity.ToEvaPricingVo;
import com.centaline.trans.evaPricing.repository.ToEvaPricingMapper;
import com.centaline.trans.evaPricing.service.EvaPricingService;
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

	@Override
	public ToEvaPricingVo findEvaPricingDetailByPKID(Long PKID) {
		ToEvaPricingVo vo = toEvaPricingMapper.findEvaPricingDetailByPKID(PKID);
		if(StringUtils.isNotBlank(vo.getAriserId())){
			User user = uamUserOrgService.getUserById(vo.getAriserId());
			vo.setAriserName(user.getRealName());
			vo.setAriserOrgName(user.getOrgName());
		}
		
		return vo;
	}

	@Override
	public void insertEvaPricing(ToEvaPricingVo vo) {
		BigDecimal evaPrice = vo.getTotalPrice();
		BigDecimal originPrice = vo.getOriginPrice();
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
			propertyVo.setArea(vo.getArea().doubleValue());//面积
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
		}
	}

	@Override
	public void updateEvaPricing(ToEvaPricingVo vo) {
		vo.setTotalPrice(vo.getTotalPrice().multiply(new BigDecimal(10000)));
		Integer count = toEvaPricingMapper.updateEvaPricing(vo);
		Integer propCount = toEvaPricingMapper.updateEguPropertyInfo(vo.getEvaCode(), vo.getHouseAge());
		if(count ==0 || count == null){
			throw new BusinessException("记录询价失败!");
		}
		if(propCount ==0 || propCount == null){
			throw new BusinessException("记录询价物业失败!");
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
	
	/**
	 * 自生成询价编号:EvaCodeyyyyMMddHHmmss
	 * @return
	 */
	private String generateEvaCode(){
		String dateString = DateUtil.getFormatDate(new Date(), DATE_FORMAT);
		return EVA_CODE_PRE+dateString;
	}



}
