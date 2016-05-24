package com.centaline.trans.property.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.PrChannelEnum;
import com.centaline.trans.common.enums.PropertyStatusEnum;
import com.centaline.trans.common.enums.ToPropertyResearchEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.property.service.ToPropertyResearchService;
import com.centaline.trans.task.entity.ToPropertyResearch;
import com.centaline.trans.task.entity.ToPropertyResearchVo;
import com.centaline.trans.task.entity.TsPrResearchMap;
import com.centaline.trans.task.repository.ToPropertyResearchMapper;
import com.centaline.trans.task.repository.TsPrResearchMapMapper;

import sun.management.resources.agent;

@Service
public class ToPropertyResarchServiceImpl implements ToPropertyResearchService {
	@Autowired
	private ToPropertyResearchMapper mapper;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private TsPrResearchMapMapper tsPrResearchMapper;
	@Autowired
	private UamBasedataService uamBasedataService;
	@Value("${centaline.environment}")
	private String environment;
	@Override
	public String getEnvironment(){
		return this.environment;
	}
	@Override
	public int recordProperty(ToPropertyResearchVo vo) {
		if (StringUtils.isBlank(vo.getPrChannel())) {
			getChannel(vo);
		}
		if (PrChannelEnum.TJJR.getCode().equals(vo.getPrChannel())) {//经纪人发起 根据行政区号获得大区ID
			getDistrictId(vo);
		}

		if (StringUtils.isBlank(vo.getAppliant())) {
			User user = uamUserOrgService.getUserByUsername(vo.getUsername());
			if (user == null) {
				throw new BusinessException("用户名不存在:" + vo.getUsername());
			}
			vo.setAppliant(user.getId());
		}
		if (StringUtils.isBlank(vo.getPrCostOrgId())) {
			if (PrChannelEnum.TJJR.getCode().equals(vo.getPrChannel())) {

				setPrCost(vo, vo.getAppliant());
			} else {
				setPrCost(vo, vo.getAgentCode());
			}
		}

		String prCode = uamBasedataService.nextSeqVal("CHANDIAO_CODE", new Date());
		vo.setPrCode(prCode);
		ToPropertyResearch tpr = buildPrResearch(vo);
		return mapper.insertSelective(tpr);
	}

	private void setPrCost(ToPropertyResearchVo vo, String userId) {
		String orgId = mapper.getOrgIdByUserId(userId);
		Org org=null;
		if(orgId!=null){
			org=uamUserOrgService.getOrgById(orgId);
		}
		if(org!=null){
		vo.setPrCostOrgId(org.getId());
		vo.setPrCostOrgName(org.getOrgName());
		vo.setPrCostOrgMgr(mapper.getPrCostMgrByOrgId(org.getId()));
		}
	}


	private void getChannel(ToPropertyResearchVo vo) {
		if (StringUtils.isBlank(vo.getCaseCode())) {
			vo.setPrChannel(PrChannelEnum.TJJR.getCode());
		} else {
			vo.setPrChannel(PrChannelEnum.TJYGW.getCode());
		}
	}

	private void getDistrictId(ToPropertyResearchVo vo) {
		List<TsPrResearchMap> researchMaps = tsPrResearchMapper.selectByDistCode(vo.getDistrict());
		TsPrResearchMap rm = null;
		if (researchMaps != null && !researchMaps.isEmpty()) {
			rm = researchMaps.get(0);
		}
		if (rm != null && !StringUtils.isBlank(rm.getYuDistCode())) {
			Org org = uamUserOrgService.getOrgByCode(rm.getYuDistCode());
			if (org != null) {
				vo.setDistrictId(org.getId());
			}
		}
	}

	/**
	 * 获取区域秘书或者各组助理
	 * 
	 * @param vo
	 * @param disId
	 */
	@Override
	public List<User> getZLList(String disId) {
		if (!StringUtils.isBlank(disId)) {
			List<User> list = uamUserOrgService.getUserByOrgIdAndJobCode(disId, TransJobs.TQYMS.getCode());
			if (list == null || list.isEmpty()) {
				if (list == null) {
					list = new ArrayList<>();
				}
				List<Org> orgList = uamUserOrgService.getOrgByParentId(disId);
				if (orgList != null && !orgList.isEmpty()) {
					for (Org org : orgList) {
						List<User> zlList = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(),
								TransJobs.TJYZL.getCode());
						if (zlList != null && !zlList.isEmpty()) {
							list.addAll(zlList);
						}
					}
				}

			}
			return list;
		}
		return null;
	}

	@Override
	public boolean hasMapping(String districtCode) {
		List<TsPrResearchMap> researchMaps = tsPrResearchMapper.selectByDistCode(districtCode);
		return researchMaps != null && !researchMaps.isEmpty()
				&& !StringUtils.isBlank(researchMaps.get(0).getYuDistCode());
	}

	public ToPropertyResearch buildPrResearch(ToPropertyResearchVo vo) {
		ToPropertyResearch record = new ToPropertyResearch();

		ToPropertyResearch sameOne = mapper.selectByForSamePrCode(vo.getPropertyAddr(),vo.getPrCat());
		if (sameOne != null) {
			record.setSamePRCode(sameOne.getPrCode());
		}

		record.setPrCode(vo.getPrCode());
		record.setCaseCode(vo.getCaseCode());
		record.setPrAppliant(vo.getAppliant());
		record.setPartCode(ToPropertyResearchEnum.PROPERTY_RESEARCH.getCode());
		record.setPrCat(vo.getPrCat());
		record.setPrStatus(PropertyStatusEnum.CONTACTS.getCode());
		record.setPrApplyTime(new Date());

		record.setDistCode(vo.getDistrict());
		record.setPrAddress(vo.getPropertyAddr());
		record.setCreateTime(new Date());
		record.setCreateBy(vo.getAppliant());
		record.setPrDistrictId(vo.getDistrictId());

		record.setPrApplyOrgId(vo.getPrApplyOrgId());
		record.setPrApplyOrgName(vo.getPrApplyOrgName());
		record.setPrApplyTime(new Date());

		record.setPrCostOrgId(vo.getPrCostOrgId());
		record.setPrCostOrgMgr(vo.getPrCostOrgMgr());
		record.setPrCostOrgName(vo.getPrCostOrgName());
		return record;
	}

	@Override
	public int insertSelective(ToPropertyResearch record) {
		return mapper.insertSelective(record);
	}

	@Override
	public ToPropertyResearch getToPropertyResearchsByPrCode(String caseCode) {
		return mapper.getToPropertyResearchsByPrCode(caseCode);
	}

	@Override
	public ToPropertyResearch getToPropertyResearchsByPkid(Long pkId) {
		return mapper.findToPropertyResearchById(pkId);
	}
}
