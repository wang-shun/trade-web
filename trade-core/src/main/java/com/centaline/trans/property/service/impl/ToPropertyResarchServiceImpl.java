package com.centaline.trans.property.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.PrChannelEnum;
import com.centaline.trans.common.enums.PropertyStatusEnum;
import com.centaline.trans.common.enums.ToPropertyResearchEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.property.service.ToPropertyResearchService;
import com.centaline.trans.task.entity.ToPropertyResearch;
import com.centaline.trans.task.entity.ToPropertyResearchVo;
import com.centaline.trans.task.entity.TsPrResearchMap;
import com.centaline.trans.task.repository.ToPropertyResearchMapper;
import com.centaline.trans.task.repository.TsPrResearchMapMapper;

@Service
public class ToPropertyResarchServiceImpl implements ToPropertyResearchService {
	@Autowired
	private ToPropertyResearchMapper mapper;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private TsPrResearchMapMapper tsPrResearchMapper;
	@Autowired
	private ToCaseService caseService;
	@Autowired
	private ToPropertyInfoService propertyInfoService;
	@Autowired
	private UamBasedataService uamBasedataService;

	@Override
	public int recordProperty(ToPropertyResearchVo vo) {

		String caseCode = uamBasedataService.nextSeqVal("CHANDIAO_CODE", new Date());
		vo.setCaseCode(caseCode);
		ToPropertyResearch tpr = buildPrResearch(vo);
		vo.setDistrictId(tpr.getPrDistrictId());

		ToPropertyInfo pi = buildPropertyInfo(vo);
		mapper.insertSelective(tpr);
		propertyInfoService.insertSelective(pi);
		return 1;
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
						if (zlList != null && !list.isEmpty()) {
							list.addAll(zlList);
						}
					}
				}

			}
			return list;
		}
		return null;
	}

	public ToPropertyInfo buildPropertyInfo(ToPropertyResearchVo vo) {
		ToPropertyInfo pi = new ToPropertyInfo();
		pi.setCaseCode(vo.getCaseCode());
		pi.setDistCode(vo.getDistrict());
		pi.setPropertyAddr(vo.getPropertyAddr());
		return pi;
	}

	@Override
	public boolean hasMapping(String districtCode) {
		List<TsPrResearchMap> researchMaps = tsPrResearchMapper.selectByDistCode(districtCode);
		return researchMaps != null && !researchMaps.isEmpty()
				&& !StringUtils.isBlank(researchMaps.get(0).getYuDistCode());
	}

	public ToPropertyResearch buildPrResearch(ToPropertyResearchVo vo) {
		ToPropertyResearch record = new ToPropertyResearch();

		User user = uamUserOrgService.getUserByUsername(vo.getUsername());
		ToPropertyResearch sameOne = mapper.selectByForSamePrCode(vo.getPropertyAddr());
		if (sameOne != null) {
			record.setSamePRCode(sameOne.getPrCode());
		}
		if (user == null) {
			throw new BusinessException("用户名不存在:" + vo.getUsername());
		}

		String appliant = user.getId();

		record.setPrCode(vo.getCaseCode());
		record.setPrAppliant(appliant);
		record.setPartCode(ToPropertyResearchEnum.PROPERTY_RESEARCH.getCode());
		record.setPrCat(vo.getPrCat());
		record.setPrChannel(PrChannelEnum.TJJR.getCode());
		record.setPrStatus(PropertyStatusEnum.CONTACTS.getCode());
		record.setPrApplyTime(new Date());
		List<TsPrResearchMap> researchMaps = tsPrResearchMapper.selectByDistCode(vo.getDistrict());
		TsPrResearchMap rm = null;
		if (researchMaps != null && !researchMaps.isEmpty()) {
			rm = researchMaps.get(0);
		}
		if (rm != null && !StringUtils.isBlank(rm.getYuDistCode())) {
			Org org = uamUserOrgService.getOrgByCode(rm.getYuDistCode());
			// 秘书 优先 每个组的助理
			//
			if (org != null) {
				record.setPrDistrictId(org.getId());
				/*
				 * List<User> users =
				 * uamUserOrgService.getUserByOrgIdAndJobCode( org.getId(),
				 * TransJobs.TQYMS.getCode()); if (users != null &&
				 * !users.isEmpty()) { User u = users.get(0);
				 * 
				 * record.setPrExecutor(u.getId() + ""); }
				 */
			}
		}
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
