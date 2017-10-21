package com.centaline.trans.cases.service.impl;

import java.util.List;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.common.enums.CaseParticipantEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.cases.service.ToCaseParticipantService;

/**
 * @Description:案件参与人相关服务实现
 * @author：jinwl6
 * @date:2017年9月15日
 * @version:
 */
@Service
public class ToCaseParticipantServiceImpl implements ToCaseParticipantService {
    
	@Autowired
	ToCaseParticipantMapper toCaseParticipantMapper;

	@Autowired
	ToCaseMapper toCaseMapper;

	@Autowired
	ToCaseInfoMapper toCaseInfoMapper;

	@Autowired
	UamUserOrgService uamUserOrgService;

	
	@Override
	public List<ToCaseParticipant> findToCaseParticipantByCondition(ToCaseParticipant toCaseParticipant) {
		return toCaseParticipantMapper.selectByCondition(toCaseParticipant);
	}

	@Override
	public int updateCaseParticipant(String caseCode, User user, User manager) {
		return toCaseParticipantMapper.updateCaseParticipant(caseCode, user, manager);
	}

	@Override
	public User findCaseOwner(String caseCode) {
		ToCase info = toCaseMapper.findToCaseByCaseCode(caseCode);
		User user = uamUserOrgService.getUserById(info.getLeadingProcessId());
		return user;
	}

	@Override
	public User findCaseLoan(String caseCode) {
		return findParticipant(caseCode,CaseParticipantEnum.LOAN);
	}

	@Override
	public User findCaseAssistant(String caseCode) {
		return findParticipant(caseCode,CaseParticipantEnum.ASSISTANT);
	}

	@Override
	public User findCaseWarrant(String caseCode) {
		return findParticipant(caseCode,CaseParticipantEnum.WARRANT);
	}

	@Override
	public User findCaseLeader(String caseCode) {
		ToCaseInfo info = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
		return uamUserOrgService.getUserById(info.getRequireProcessorId());
	}

	/**
	 * 根据案件编号及参与人类型
	 * 获取参与人用户信息
	 * @param caseCode 案件编号
	 * @param type 参与人类型
	 * @return
	 */
	private User findParticipant(String caseCode,CaseParticipantEnum type){
		ToCaseParticipant participant = new ToCaseParticipant();
		participant.setCaseCode(caseCode);
		participant.setPosition(type.getCode());
		List<ToCaseParticipant> lists = toCaseParticipantMapper.selectByCondition(participant);
		if(lists!=null && !lists.isEmpty()){
			return getUser(lists.get(0));
		}
		return null;
	}

	/**
	 * 获取参与人用户信息
	 *
	 * @param participant 参与人信息
	 * @return
	 */
	private User getUser(ToCaseParticipant participant){
		User user = uamUserOrgService.getUserByUsername(participant.getUserName());
		if(user==null){
			user = uamUserOrgService.getUserByEmployeeCode(participant.getUserName());
		}
		return user;
	}

}
