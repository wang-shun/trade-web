package com.centaline.trans.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.vo.ViHouseDelBaseVo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.vo.OrgVO;
import com.centaline.trans.signroom.vo.PropertyAddrInfoVo;
import com.centaline.trans.signroom.vo.PropertyAddrSearchVo;
import com.centaline.trans.task.entity.ToPropertyResearchVo;

@Service
public class ToPropertyInfoServiceImpl implements ToPropertyInfoService {

	@Autowired
	ToPropertyInfoMapper toPropertyInfoMapper;

	@Override
	public ToPropertyInfo findToPropertyInfoByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toPropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
	}

	@Override
	public List<ToPropertyInfo> getPropertyInfoByUserId(String userId) {
		return toPropertyInfoMapper.getPropertyInfoByUserId(userId);
	}

	@Override
	public int insertSelective(ToPropertyInfo record) {
		return toPropertyInfoMapper.insertSelective(record);
	}

	@Override
	public ToPropertyInfo findToPropertyInfoByCaseCodeAndAddr(
			ToPropertyInfo record) {
		// TODO Auto-generated method stub
		return toPropertyInfoMapper.findToPropertyInfoByCaseCodeAndAddr(record);
	}

	@Override
	public ViHouseDelBaseVo getHouseBaseByHoudelCode(String delCode) {
		return toPropertyInfoMapper.selectByHoudelCode(delCode);
	}

	/**
	 * 根据当前用户部门id得到 根节点下第二级组织信息
	 */
	@Override
	public ToPropertyResearchVo getPropertyDepInfoByuserDepIdEloan(String depId) {

		OrgVO orgvo = toPropertyInfoMapper
				.getPropertyDepInfoByuserDepIdEloan(depId);
		String type = "no";
		// 1D29BB468F504774ACE653B946A393EE 营业部 ff8080814e01474e014e2e97c8b30036
		// 非营业部
		if (orgvo.getOrgId().equals("ff8080814f459a78014f45a73d820006")) {
			ToPropertyResearchVo pinfo = new ToPropertyResearchVo();
			pinfo.setPrApplyDepId(orgvo.getOrgParentId());
			// pinfo.setPrApplyDepName(orgvo.getOrgName());
			type = "yes";
			return pinfo;

		} else {
			if (type.equals("no"))
				return getPropertyDepInfoByuserDepIdEloan(orgvo.getOrgId());
		}
		return null;
	}

	@Override
	public List<PropertyAddrInfoVo> getPropertyInfoListByInputValue(
			PropertyAddrSearchVo propertyAddrSearchVo) {
		return toPropertyInfoMapper
				.getPropertyInfoListByInputValue(propertyAddrSearchVo);
	}

	@Override
	public String getCaseCodeByPropertyAddr(String propertyAddress) {
		return toPropertyInfoMapper.getCaseCodeByPropertyAddr(propertyAddress);
	}

}
