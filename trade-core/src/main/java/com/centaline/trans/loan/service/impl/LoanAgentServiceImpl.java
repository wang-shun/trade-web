package com.centaline.trans.loan.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.loan.entity.LoanAgent;
import com.centaline.trans.loan.entity.LoanStatusChange;
import com.centaline.trans.loan.enums.LoanCompany;
import com.centaline.trans.loan.enums.LoanType;
import com.centaline.trans.loan.repository.LoanAgentMapper;
import com.centaline.trans.loan.repository.LoanStatusChangeMapper;
import com.centaline.trans.loan.service.LoanAgentService;

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
		if (loanAgent.getTempServId() != null) {
			TgServItemAndProcessor p = new TgServItemAndProcessor();
			p.setPkid(loanAgent.getTempServId());
			p.setProcessorId(loanAgent.getExecutorId());
			p.setOrgId(loanAgent.getExecutorTeam());
			servItemMapper.updateByPrimaryKeySelective(p);
		}
		loanAgentMapper.insertSelective(loanAgent);
	}

	private void buildFCaseCode(LoanAgent loanAgent) {
		if (!StringUtils.isBlank(loanAgent.getCaseCode())
				&& StringUtils.isBlank(loanAgent.getFinCaseCode())) {
			if (LoanType.ZY_XD.getCode().equals(loanAgent.getLoanSrvCode())) {
				loanAgent.setFinCaseCode(uamBasedataService.nextSeqVal(
						"ZYDK_CODE", new Date()));
			} else {
				loanAgent.setFinCaseCode(uamBasedataService.nextSeqVal(
						"WDDK_CODE", LoanCompany.getCaseValueByCode(loanAgent
								.getFinOrgCode()), new Date()));
			}
		}
	}

	@Override
	public void update(LoanAgent loanAgent) {
		int r = doLoanCheck(loanAgent);
		if (r != 1) {
			return;
		}

		TgServItemAndProcessor p = new TgServItemAndProcessor();

		LoanAgent obj = loanAgentMapper.selectByPkid(loanAgent.getPkid());
		if (!StringUtils.isBlank(obj.getCaseCode())&& obj.getLoanSrvCode() != null
				&& !obj.getLoanSrvCode().equals(loanAgent.getLoanSrvCode())) {
			p.setCaseCode(obj.getCaseCode());
			p.setSrvCat(obj.getLoanSrvCode());
			TgServItemAndProcessor ts = servItemMapper
					.findTgServItemAndProcessor(p);
			if (ts != null) {
				ts.setProcessorId(null);
				ts.setOrgId(null);
				servItemMapper.updateByPrimaryKey(ts);
			}
		}
		if (loanAgent.getTempServId() != null) {
			p = new TgServItemAndProcessor();
			p.setPkid(loanAgent.getTempServId());
			p.setProcessorId(loanAgent.getExecutorId());
			p.setOrgId(loanAgent.getExecutorTeam());
			servItemMapper.updateByPrimaryKeySelective(p);
		}
		if (obj.getApplyStatus() != null && loanAgent.getApplyStatus() != null
				&& !obj.getApplyStatus().equals(loanAgent.getApplyStatus())) {
			LoanStatusChange lsc = new LoanStatusChange();
			lsc.setChangeDate(new Date());
			lsc.setChangeUser(loanAgent.getExecutorId());
			lsc.setStFrom(obj.getApplyStatus());
			lsc.setStTo(loanAgent.getApplyStatus());
			lsc.setLoanId(obj.getPkid());
			loanStatusChangeMapper.deleteUnConfirm(obj.getPkid());
			loanStatusChangeMapper.insertSelective(lsc);
		}
		BeanUtils.copyProperties(loanAgent, obj);
		buildFCaseCode(obj);
		loanAgentMapper.updateByPrimaryKeySelective(obj);
	}

	private int doLoanCheck(LoanAgent loanAgent) {
		int r = 3;
		if (!StringUtils.isBlank(loanAgent.getCaseCode())) {
			if (loanAgent.getPkid() == null) {
				loanAgent.setPkid(-1L);
			}
			List<LoanAgent> agents = loanAgentMapper
					.listByPkIdAndCaseCodeSrvCode(loanAgent.getPkid(),
							loanAgent.getCaseCode(), loanAgent.getLoanSrvCode());
			if (agents != null && !agents.isEmpty()) {
				User u = uamUserOrgService.getUserById(agents.get(0)
						.getExecutorId());
				if (u != null) {
					loanAgent.setOptUser(u.getRealName());
				}
				loanAgent.setResult("2");
				r = 2;
				return r;
			}
			List<TgServItemAndProcessor> servs = servItemMapper
					.findTgServItemAndProcessorByCaseCode(loanAgent
							.getCaseCode());

			for (TgServItemAndProcessor tgServItemAndProcessor : servs) {
				if (tgServItemAndProcessor.getSrvCat() != null
						&& tgServItemAndProcessor.getSrvCat().equals(
								loanAgent.getLoanSrvCode())) {
					loanAgent.setTempServId(tgServItemAndProcessor.getPkid());
					r = 1;
				}
			}
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
		if (obj.getLoanSrvCode() != null&&!StringUtils.isBlank(obj.getCaseCode())) {
			p.setCaseCode(obj.getCaseCode());
			p.setSrvCat(obj.getLoanSrvCode());
			TgServItemAndProcessor ts = servItemMapper
					.findTgServItemAndProcessor(p);
			if (ts != null) {
				ts.setProcessorId(null);
				ts.setOrgId(null);
				servItemMapper.updateByPrimaryKeySelective(ts);
			}
		}
		loanAgent.setResult(loanAgentMapper.deleteByPkid(loanAgent.getPkid())
				+ "");
	}

	@Override
	public void updateLoanAgent(LoanAgent loanAgent) {
		loanAgentMapper.updateByPrimaryKeySelective(loanAgent);

	}

	@Override
	public void updateStatusLoanAgent(LoanAgent loanAgent,
			LoanStatusChange loanStatusChange) {
		loanAgentMapper.updateByPrimaryKeySelective(loanAgent);
		loanStatusChangeMapper.updateByPrimaryKeySelective(loanStatusChange);
	}

	@Override
	public int batchUpdateExportTime(String[] array) {
		// return loanAgentMapper.batchUpdateExportTime(array);
		return 0;
	}
}
