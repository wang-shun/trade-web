package com.centaline.trans.stuff.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.eloan.service.ToEloanCaseService;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.stuff.enums.CommentSource;
import com.centaline.trans.stuff.service.StuffAssigneeGetService;

@Service
public class StuffAssigneeGetServiceImpl implements StuffAssigneeGetService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private ToCaseService caseService;
	@Autowired
	private ToEloanCaseService eloanCaseService;
	@Autowired
	private ToSpvService toSpvService;

	@Override
	public User getAssignee(ToCaseComment stuffComment) {
		if (CommentSource.MORT.getCode().equals(stuffComment.getSource())) {
			return getCaseLeadingUser(stuffComment.getBizCode());
		} else if (CommentSource.CARD.getCode().equals(stuffComment.getSource())
				|| CommentSource.XD.getCode().equals(stuffComment.getSource())) {
			return getEloanCaseUser(stuffComment.getBizCode(),stuffComment.getCaseCode());
		} else if (CommentSource.ZJJG.getCode().equals(stuffComment.getSource())) {
			return getSpvUser(stuffComment.getBizCode(),stuffComment.getCaseCode());
		} else {
			throw new BusinessException("不支持的来源" + stuffComment.getSource());
		}
	}

	/**
	 * 资金监管
	 * 
	 * @param spvCode
	 * @return
	 */
	private User getSpvUser(String spvCode,String caseCode) {
		ToSpv spv = toSpvService.findToSpvBySpvCode(spvCode);
		if (spv == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("ToSpv not find spvCode:" + spvCode);
			}
			return getCaseLeadingUser(caseCode);
		} else {
			return uamUserOrgService.getUserById(spv.getApplyUser());
		}
	}

	/**
	 * Eloan创建人
	 * 
	 * @param eLoanCode
	 * @return
	 */
	private User getEloanCaseUser(String eLoanCode,String caseCode) {
		ToEloanCase eloanCase = eloanCaseService.selectByEloanCode(eLoanCode);
		if (eloanCase == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("ToEloanCase not find eLoanCode:" + eLoanCode);
			}
			return getCaseLeadingUser(caseCode);
		} else {
			return uamUserOrgService.getUserById(eloanCase.getExcutorId());
		}
	}

	/**
	 * 案件主办
	 * 
	 * @param caseCode
	 * @return
	 */
	private User getCaseLeadingUser(String caseCode) {
		ToCase toCase = caseService.findToCaseByCaseCode(caseCode);
		if (toCase == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("ToCase not find caseCode:" + caseCode);
			}
			return null;
		} else {
			return uamUserOrgService.getUserById(toCase.getLeadingProcessId());
		}
	}
}
