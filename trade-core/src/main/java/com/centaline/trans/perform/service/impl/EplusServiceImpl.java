package com.centaline.trans.perform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.perform.entity.EplusEntity;
import com.centaline.trans.perform.repository.EplusMapper;
import com.centaline.trans.perform.service.EplusService;
import com.centaline.trans.perform.vo.EplusVo;
@Service
@Transactional(readOnly = true)
public class EplusServiceImpl implements EplusService {
	@Autowired
	private EplusMapper mapper;
	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean importOne(EplusVo vo) {
		EplusEntity record=voToEntity(vo);
		if (null==record) {
			return false;
		}
		mapper.deleteByBizKey(record);
		Integer listOrders=mapper.getLastOrders(record);
		if(listOrders==null){
			listOrders=0;
		}else{
			listOrders=listOrders-1;
			listOrders=listOrders<0?0:listOrders;
		}
		record.setKpiOrders(record.getOrders()+listOrders);
		return mapper.insertSelective(record) > 0;
	}
	

	private EplusEntity voToEntity(EplusVo vo) {
		EplusEntity entity=null;
		User u= uamUserOrgService.getUserByEmployeeCode(vo.getEmployeeCode());
		if(u==null){
			return entity;
		}
		if(u.getRealName()==null||!u.getRealName().equals(vo.getUserNmae())){
			return entity;
		}
		List<UserOrgJob>userOrgJobs =uamUserOrgService.getUserOrgJobByUserId(u.getId());
		for (UserOrgJob userOrgJob : userOrgJobs) {
			if(vo.getOrgName().equals(userOrgJob.getOrgName())&&vo.getJobName().equals(userOrgJob.getJobName())){
				entity=new EplusEntity();
				entity.setBelongMonth(vo.getBelongMonth());
				entity.setJobId(userOrgJob.getJobId());
				entity.setOrgId(userOrgJob.getOrgId());
				entity.setParticipantId(u.getId());
				entity.setParticipantName(u.getRealName());
				entity.setOrders(vo.getOrders());
				return entity;
			}
		}
		return entity;
	}
}
