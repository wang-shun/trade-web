package com.centaline.trans.cases.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.cases.service.AuditCaseService;
import com.centaline.trans.common.entity.ToCcaiAttachment;
import com.centaline.trans.common.repository.ToCcaiAttachmentMapper;

@Service
public class AuditCaseServiceImpl implements AuditCaseService {
	@Autowired
	private ToCcaiAttachmentMapper toCcaiAttachmentMapper;
	
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;
	
	@Autowired
	private ToCaseParticipantMapper toCaseParticipantMapper;
	
	@Autowired
	private UamUserOrgService uamUserOrgServiceClient;

	@Override
	public String getPayType(String caseCode) {
		// TODO Auto-generated method stub
		ToCaseInfo caseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
		return caseInfo.getPayType();
	}

	@Override
	public List<ToCcaiAttachment> getCcaiAttachment(String caseCode) {
		return toCcaiAttachmentMapper.findAttachmentsByCaseCode(caseCode);
	}

	@Override
	public int addLoanProcessor(String userName,String caseCode) {
		if(null==userName||null==caseCode){
			return 0;
		}
		ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
		toCaseParticipant.setCaseCode(caseCode);
		toCaseParticipant.setPosition("loan");
//		先查出user
		User userByUsername = uamUserOrgServiceClient.getUserByUsername(userName);
		if(null==userByUsername){
			return 0;
		}
//		再先原来有没有贷款权证
		List<ToCaseParticipant> listParticipant = toCaseParticipantMapper.selectByCondition(toCaseParticipant);
		if(listParticipant.size()==1){
			ToCaseParticipant toCaseParticipant2 = listParticipant.get(0);
			toCaseParticipant.setCcaiCode(toCaseParticipant2.getCcaiCode());
			Long pkid=toCaseParticipant2.getPkid();
//			先清空，再赋值，避免污染
			if(userByUsername!=null){
				toCaseParticipant.setPkid(toCaseParticipant2.getPkid());
				toCaseParticipant.setUserName(userByUsername.getUsername());
				toCaseParticipant.setRealName(userByUsername.getRealName());
				toCaseParticipant.setMobile(userByUsername.getMobile());
				toCaseParticipant.setGrpName(userByUsername.getOrgName());
				return toCaseParticipantMapper.updateByPrimaryKeySelective(toCaseParticipant);
			}
			
			
		}else{
			toCaseParticipant.setPosition(null);
			List<ToCaseParticipant> selectByCaseCode = toCaseParticipantMapper.selectByCaseCode(caseCode);
			if(selectByCaseCode.size()>0){
				ToCaseParticipant toCaseParticipant2 = selectByCaseCode.get(0);				
				toCaseParticipant.setCcaiCode(toCaseParticipant2.getCcaiCode());
				toCaseParticipant.setUserName(userByUsername.getUsername());
				toCaseParticipant.setRealName(userByUsername.getRealName());
				toCaseParticipant.setMobile(userByUsername.getMobile());
				toCaseParticipant.setGrpName(userByUsername.getOrgName());
				toCaseParticipant.setPosition("loan");
				return toCaseParticipantMapper.insertSelective(toCaseParticipant);
			}
		}
		
		
		return 0;
	}
	/**
	 * 根据当前贷款或者过户权证账号和caseCode查出贷款或者过户权证的上级账号
	 */
	@Override
	public String getLeaderUserName(ToCaseParticipant toCaseParticipant) {
		// TODO Auto-generated method stub
		if(null!=toCaseParticipant.getCaseCode()&&null!=toCaseParticipant.getUserName()){			
		List<ToCaseParticipant> userList = toCaseParticipantMapper.selectByCondition(toCaseParticipant);
		if(userList.size()==1){
			ToCaseParticipant toCaseParticipant2 = userList.get(0);
			return toCaseParticipant2.getGrpMgrUsername();
		}					
		}
		return null;
	}
	
}
