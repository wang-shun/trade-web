package com.centaline.api;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.api.service.FlowApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.common.enums.CaseParticipantEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import com.centaline.trans.common.enums.CcaiTaskEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.support.Assert;

import java.util.List;

public class FlowApiServiceTest extends AbstractServiceTest {
	@Autowired
	FlowApiService service;

	@Autowired
	UamSessionService uamSessionService;
	@Autowired
	ToCaseMapper mapper;
	@Autowired
	ToCaseInfoMapper infoMapper;
	@Autowired
	ToCaseParticipantMapper participantMapper;
	@Autowired
	UamUserOrgService uamUserOrgService;

	@Test
	public void tradeFeedBackCcai_BACK() throws Exception {
		String ccaiCode = "DJZY-ZBZF1-201709-001";
		// String caseCode = "ZY-TJ-2017090018";//TJZY-ZBZF1-1708-0001
		String caseCode = infoMapper.findcaseCodeByCcaiCode(ccaiCode);
		System.out.println("caseCode is :"+caseCode);
		// SessionUser user = uamSessionService.getSessionUserById("0EEE36FF103841A882F2AFAF9CE2BC24");
		SessionUser user = uamSessionService.getSessionUserById(getManagerId(caseCode));
		FlowFeedBack info = new FlowFeedBack(user, CcaiFlowResultEnum.BACK,"权证人员不正确");
		ApiResultData result = service.tradeFeedBackCcai(caseCode, CcaiTaskEnum.TRADE_WARRANT_MANAGER,info);
		System.out.println(result.getMessage()+"-------"+result.isSuccess());
		if(result.isSuccess()){
			//修改案件状态为驳回CCAI
			ToCase ca  = mapper.findToCaseByCaseCode(caseCode);
			ca.setStartDate(CaseStatusEnum.BHCCAI.getCode());
			mapper.updateByCaseCodeSelective(ca);
		}
	}

	@Test
	public void tradeFeedBackCcai_SUCCESS() throws Exception {
		String caseCode = "ZY-TJ-2017090020";// TJZY-JHXY2-1612-0004
		SessionUser user = uamSessionService.getSessionUserById(getManagerId(caseCode));
		FlowFeedBack info = new FlowFeedBack(user, CcaiFlowResultEnum.SUCCESS,"进入交易环节");
		ApiResultData result = service.tradeFeedBackCcai(caseCode, CcaiTaskEnum.TRADE_WARRANT_MANAGER,info);
		System.out.println(result.getMessage()+"-------"+result.isSuccess());
		if(result.isSuccess()){
			//修改案件状态为驳回CCAI
			ToCase ca  = mapper.findToCaseByCaseCode(caseCode);
			ca.setStartDate(CaseStatusEnum.YJD.getCode());
			mapper.updateByCaseCodeSelective(ca);
		}
	}

	/**
	 * 获取对应的权证经理 域账号
	 * @param caseCode
	 * @return
	 */
	private String getManagerId(String caseCode){
		List<ToCaseParticipant> participants = participantMapper.selectByCaseCode(caseCode);
		ToCaseParticipant pa = null;
		for(ToCaseParticipant p :participants){
			System.out.println(p.getPosition()+"---"+p.getUserName()+"----"+p.getRealName()+" manager :"+p.getGrpMgrUsername());
			//优先找贷款权证
			if(CaseParticipantEnum.LOAN.getCode().equals(p.getPosition())){
				pa = p ;
				break;
			}
			//没有贷款权证 找过户权证
			if(pa==null && CaseParticipantEnum.WARRANT.getCode().equals(p.getPosition())){
				pa = p;
			}
		}
		Assert.notNull(pa,"贷款或者过户权证不能都不存在");
		User u = uamUserOrgService.getUserByUsername(pa.getGrpMgrUsername());
		Assert.notNull(u,pa.getGrpMgrUsername()+" 主管信息不存在");
		return u.getId();
	}

}