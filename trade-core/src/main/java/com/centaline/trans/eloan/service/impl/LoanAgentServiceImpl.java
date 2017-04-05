package com.centaline.trans.eloan.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.eloan.entity.LoanAgent;
import com.centaline.trans.eloan.entity.LoanStatusChange;
import com.centaline.trans.eloan.enums.LoanCompany;
import com.centaline.trans.eloan.enums.LoanType;
import com.centaline.trans.eloan.repository.LoanAgentMapper;
import com.centaline.trans.eloan.repository.LoanStatusChangeMapper;
import com.centaline.trans.eloan.service.LoanAgentService;
import com.centaline.trans.utils.DateUtil;

@Service
public class LoanAgentServiceImpl implements LoanAgentService {
	@Autowired
	private LoanAgentMapper loanAgentMapper;
	@Autowired
	private LoanStatusChangeMapper loanStatusChangeMapper;
	@Autowired
	private TgServItemAndProcessorMapper servItemMapper;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private ToCaseMapper toCaseMapper;

	@Override
	public LoanAgent view(Long pkid) {
		return loanAgentMapper.selectByPkid(pkid);
	}

	@Override
	public void add(LoanAgent loanAgent) {
		int r = doLoanCheck(loanAgent);
		loanAgent.setPkid(null);
		if (r != 1) {
			return;
		}
		loanAgent.setCreateTime(new Date());
		buildFCaseCode(loanAgent);
		bindServItem(loanAgent);
		loanAgentMapper.insertSelective(loanAgent);

		processLoanStatusChange(null, loanAgent, loanAgent.getOptUser());
	}

	private void buildFCaseCode(LoanAgent loanAgent) {
		if (!StringUtils.isBlank(loanAgent.getCaseCode()) && StringUtils.isBlank(loanAgent.getFinCaseCode())) {
			String dateStr = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
			String month = dateStr.substring(0, 6);
			if (LoanType.ZY_XD.getCode().equals(loanAgent.getLoanSrvCode())) {
				loanAgent.setFinCaseCode(uamBasedataService.nextSeqVal("ZYDK_CODE","XD", month));
			} else {
				loanAgent.setFinCaseCode(uamBasedataService.nextSeqVal("WDDK_CODE",
						LoanCompany.getCaseValueByCode(loanAgent.getFinOrgCode()), month));
			}
		}
	}

	@Override
	public void update(LoanAgent loanAgent) {
		int r = doLoanCheck(loanAgent);
		if (r != 1) {
			return;
		}

		LoanAgent obj = loanAgentMapper.selectByPkid(loanAgent.getPkid());
		unbindServItem(loanAgent,obj);
		bindServItem(loanAgent);
		processLoanStatusChange(obj, loanAgent, loanAgent.getOptUser());
		BeanUtils.copyProperties(loanAgent, obj);
		buildFCaseCode(obj);
		loanAgentMapper.updateByPrimaryKeySelective(obj);
	}

	private void bindServItem(LoanAgent loanAgent) {
		TgServItemAndProcessor p = new TgServItemAndProcessor();
		if(!StringUtils.isBlank(loanAgent.getCaseCode())){
			p.setCaseCode(loanAgent.getCaseCode());
			p.setSrvCat(loanAgent.getLoanSrvCode());
			TgServItemAndProcessor ts = servItemMapper.findTgServItemAndProcessor(p);
			boolean isNew=false;
			if(ts==null){
				ts=p;
				isNew=true;
			}
			
			String caseCode = loanAgent.getCaseCode();
			ToCase toCase = toCaseMapper.findToCaseByCaseCode(caseCode);
			User oldUser = uamUserOrgService.getUserById(toCase.getLeadingProcessId());
			ts.setSrvCat(loanAgent.getLoanSrvCode());
			ts.setSrvCode(loanAgent.getLoanSrvCode()+"01");
			ts.setPreProcessorId(oldUser.getId());
			ts.setPreOrgId(oldUser.getOrgId());
			ts.setProcessorId(loanAgent.getExecutorId());
			ts.setOrgId(loanAgent.getExecutorTeam());
			if(isNew){
				servItemMapper.insertSelective(ts);
			}else{
				servItemMapper.updateByPrimaryKeySelective(ts);
			}
		}
	}

	private void unbindServItem(LoanAgent newObj, LoanAgent obj) {
		TgServItemAndProcessor p = new TgServItemAndProcessor();
		if (!StringUtils.isBlank(obj.getCaseCode()) && obj.getLoanSrvCode() != null
				&& !obj.getLoanSrvCode().equals(newObj.getLoanSrvCode())) {
			p.setCaseCode(obj.getCaseCode());
			p.setSrvCat(obj.getLoanSrvCode());
			TgServItemAndProcessor ts = servItemMapper.findTgServItemAndProcessor(p);
			if (ts != null) {
				servItemMapper.deleteByPrimaryKey(ts.getPkid());//confirmed by xiacheng 
			}
		}
	}

	private void processLoanStatusChange(LoanAgent oldObj, LoanAgent newObj, String userId) {
		if (oldObj == null
				|| (newObj.getApplyStatus() != null && !newObj.getApplyStatus().equals(oldObj.getApplyStatus()))) {
			LoanStatusChange lsc = new LoanStatusChange();
			lsc.setChangeDate(new Date());
			lsc.setChangeUser(newObj.getExecutorId());

			lsc.setCreateBy(userId);
			lsc.setCreateTime(new Date());
			
			lsc.setUpdateBy(userId);
			lsc.setUpdateTime(new Date());
			lsc.setIsConfirm("0");

			lsc.setStFrom(oldObj == null ? null : oldObj.getApplyStatus());
			lsc.setStTo(newObj.getApplyStatus());
			
			lsc.setLoanId(newObj.getPkid());
			if (oldObj != null) {
				loanStatusChangeMapper.deleteUnConfirm(newObj.getPkid());
			}
			loanStatusChangeMapper.insertSelective(lsc);
		}
	}

	private int doLoanCheck(LoanAgent loanAgent) {
		int r = 3;
		if (!StringUtils.isBlank(loanAgent.getCaseCode()) && !"30004005".equals(loanAgent.getLoanSrvCode())) {
			if (loanAgent.getPkid() == null) {
				loanAgent.setPkid(-1L);
			}
			List<LoanAgent> agents = loanAgentMapper.listByPkIdAndCaseCodeSrvCode(loanAgent.getPkid(),
					loanAgent.getCaseCode(), loanAgent.getLoanSrvCode());
			if (agents != null && !agents.isEmpty()) {
				User u = uamUserOrgService.getUserById(agents.get(0).getExecutorId());
				if (u != null) {
					loanAgent.setOptUser(u.getRealName());
				}
				loanAgent.setResult("2");
				r = 2;
				return r;
			}
			r=1;
		} else {
			r = 1;
		}
		loanAgent.setResult(r + "");
		return r;
	}

	@Override
	public void doDelete(LoanAgent loanAgent) {
		LoanAgent obj = loanAgentMapper.selectByPkid(loanAgent.getPkid());
		TgServItemAndProcessor p = new TgServItemAndProcessor();
		if (obj.getLoanSrvCode() != null && !StringUtils.isBlank(obj.getCaseCode())) {
			p.setCaseCode(obj.getCaseCode());
			p.setSrvCat(obj.getLoanSrvCode());
			TgServItemAndProcessor ts = servItemMapper.findTgServItemAndProcessor(p);
			if (ts != null) {
				String caseCode = loanAgent.getCaseCode();
				ToCase toCase = toCaseMapper.findToCaseByCaseCode(caseCode);
				User oldUser = uamUserOrgService.getUserById(toCase.getLeadingProcessId());
				
				ts.setPreProcessorId(oldUser.getId());
				ts.setPreOrgId(oldUser.getOrgId());
				ts.setProcessorId(null);
				ts.setOrgId(null);
				servItemMapper.updateByPrimaryKeySelective(ts);
			}
		}
		loanAgent.setResult(loanAgentMapper.deleteByPkid(loanAgent.getPkid()) + "");
	}

	@Override
	public void updateLoanAgent(LoanAgent loanAgent) {
		loanAgentMapper.updateByPrimaryKeySelective(loanAgent);

	}

	@Override
	public void updateStatusLoanAgent(LoanAgent loanAgent, LoanStatusChange loanStatusChange) {
		loanAgentMapper.updateByPrimaryKeySelective(loanAgent);
		loanStatusChangeMapper.updateByPrimaryKeySelective(loanStatusChange);
	}

	@Override
	public int batchUpdateExportTime(String[] array) {
		// return loanAgentMapper.batchUpdateExportTime(array);
		return 0;
	}

	@Override
	public List<LoanAgent> selectByCaseCode(String caseCode) {
		
		return loanAgentMapper.selectByCaseCode(caseCode);
	}
}
