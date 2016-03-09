package com.centaline.trans.award.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.award.entity.BaseImportEntity;
import com.centaline.trans.award.repository.BaseImportMapper;
import com.centaline.trans.award.service.BaseImportService;
import com.centaline.trans.award.vo.BaseImportVo;
@Service
@Transactional(readOnly = true)
public class BaseImportServiceImpl implements BaseImportService {
	@Autowired
	private BaseImportMapper mapper;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Transactional(readOnly = false,propagation=Propagation.REQUIRES_NEW)
	@Override
	public boolean importOne(BaseImportVo vo) {
		BaseImportEntity record=voToEntity(vo);
		if (null==record) {
			return false;
		}
		mapper.deleteByBizKey(record);
		return mapper.insertSelective(record) > 0;
	}
	private BaseImportEntity voToEntity(BaseImportVo vo) {
		BaseImportEntity entity=null;
		User u= uamUserOrgService.getUserByEmployeeCode(vo.getEmployeeCode());
		if(u==null){
			return entity;
		}
		if(u.getRealName()==null||!u.getRealName().equals(vo.getUserName())){
			return entity;
		}
		List<UserOrgJob>userOrgJobs =uamUserOrgService.getUserOrgJobByUserId(u.getId());
		for (UserOrgJob userOrgJob : userOrgJobs) {
			if(vo.getOrgName().equals(userOrgJob.getOrgName())&&vo.getJobName().equals(userOrgJob.getJobName())){
				entity=new BaseImportEntity();
				entity.setBelongMonth(vo.getBelongMonth());
				entity.setJobId(userOrgJob.getJobId());
				entity.setOrgId(userOrgJob.getOrgId());
				entity.setParticipant(u.getId());
				entity.setBelongMonth(vo.getBelongMonth());
				entity.setBaseAmount(vo.getAmount());
				entity.setCaseCode(vo.getCaseCode());
				entity.setCreateTime(new Date());
				return entity;
			}
		}
		return entity;
	}

}
