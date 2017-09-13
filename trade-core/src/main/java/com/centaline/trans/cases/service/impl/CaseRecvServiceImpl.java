package com.centaline.trans.cases.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.bizwarn.repository.BizWarnInfoMapper;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseRecv;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseRecvMapper;
import com.centaline.trans.cases.service.CaseRecvService;
import com.centaline.trans.cases.vo.CaseRecvVO;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.repository.ToCaseCommentMapper;
import com.centaline.trans.common.entity.ToCcaiAttachment;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.repository.ToCcaiAttachmentMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.entity.ToTax;
import com.centaline.trans.task.repository.ToSignMapper;
import com.centaline.trans.task.repository.ToTaxMapper;


@Service
public class CaseRecvServiceImpl implements CaseRecvService {
	@Autowired
    private ToCaseRecvMapper toCaseRecvMapper;
	@Autowired
    private ToSignMapper toSignMapper;
	@Autowired
    private ToPropertyInfoMapper toPropertyInfoMapper;
	@Autowired
    private ToCaseInfoMapper toCaseInfoMapper;
    @Autowired
    private ToTaxMapper toTaxMapper;
    @Autowired
    private ToCaseCommentMapper toCaseCommentMapper;
    @Autowired
    private ToCcaiAttachmentMapper toCcaiAttachmentMapper;
    @Autowired
    private BizWarnInfoMapper bizWarnInfoMapper;
	@Override
	public int deleteByPrimaryKey(String caseCode) {
		return toCaseRecvMapper.deleteByPrimaryKey(caseCode);
	}

	/**
	 * @author xiefei1
	 * @since 2017年9月11日 下午1:49:11 
	 * @description 会先根据caseCode查询CaseRecvVO的每个成员在数据库中是否存在来
	 * 				判断是用insert or update;
	 */
	@Override
	public void insertSelective(CaseRecvVO caseRecvVO) {	
		String primaryCaseCode=caseRecvVO.getCaseCode();
		ToPropertyInfo toPropertyInfo = caseRecvVO.getToPropertyInfo();
		if(null!=toPropertyInfo){
			ToPropertyInfo findToPropertyInfoByCaseCode = toPropertyInfoMapper.findToPropertyInfoByCaseCode(primaryCaseCode);
			if(null!=findToPropertyInfoByCaseCode){
				toPropertyInfo.setPkid(findToPropertyInfoByCaseCode.getPkid());
				toPropertyInfoMapper.updateByPrimaryKeySelective(toPropertyInfo);
			}else{
				toPropertyInfoMapper.insertSelective(toPropertyInfo);
			}
		}
		
		ToSign toSign = caseRecvVO.getToSign();
		if(null!=toSign){
			ToSign findToSignByCaseCode = toSignMapper.findToSignByCaseCode(primaryCaseCode);
			if(null!=findToSignByCaseCode){
				toSignMapper.updateByPrimaryKeySelective(toSign);
			}else{
				toSignMapper.insertSelective(toSign);
			}
		}
		
		ToCaseRecv toCaseRecv = caseRecvVO.getToCaseRecv();
		if(null!=toCaseRecv){
			ToCaseRecv findToCaseRecvByCaseCode = toCaseRecvMapper.selectByPrimaryKey(primaryCaseCode);
			if(null!=findToCaseRecvByCaseCode){
				toCaseRecvMapper.updateByPrimaryKeySelective(toCaseRecv);
			}else{
				toCaseRecvMapper.insertSelective(toCaseRecv);
			}
		}
		
		String payType = caseRecvVO.getPayType();
		if(null!=payType){
			ToCaseInfo toCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(primaryCaseCode);
			if(null!=toCaseInfo){
				toCaseInfo.setPayType(payType);
				toCaseInfoMapper.updateByPrimaryKeySelective(toCaseInfo);
			}else{
				ToCaseInfo toCaseInfo2 = new ToCaseInfo();
				toCaseInfo2.setPayType(payType);
				toCaseInfo2.setCaseCode(toCaseRecv.getCaseCode());
				toCaseInfoMapper.insertSelective(toCaseInfo2);
			}
		}
		
		ToTax toTax = caseRecvVO.getToTax();
		if(null!=toTax){
			ToTax findToTaxByCaseCode = toTaxMapper.findToTaxByCaseCode(primaryCaseCode);
			if(null!=findToTaxByCaseCode){	
				toTaxMapper.updateByPrimaryKeySelective(toTax);
			}else{
				toTaxMapper.insertSelective(toTax);
			}
		}
		
		ToCaseComment toCaseComment = caseRecvVO.getToCaseComment();
		if(null!=toCaseComment){
			toCaseComment.setCaseCode(primaryCaseCode);
			toCaseCommentMapper.insertSelective(toCaseComment);			
		}
		
		BizWarnInfo bizWarnInfo = caseRecvVO.getBizWarnInfo();
		if(null!=bizWarnInfo){
			BizWarnInfo selectByCaseCode = bizWarnInfoMapper.selectByCaseCode(primaryCaseCode);
			if(null!=selectByCaseCode){
				selectByCaseCode.setStatus(bizWarnInfo.getStatus());
				selectByCaseCode.setContent(bizWarnInfo.getContent());
				bizWarnInfoMapper.updateByCaseCode(selectByCaseCode);
				bizWarnInfoMapper.updateStatusByCaseCode(selectByCaseCode);
			}else{
				bizWarnInfoMapper.insertSelective(bizWarnInfo);
			}
		}
		
		
		
//		业务没有insert attachment 需要
//		ToCcaiAttachment toCcaiAttachment = caseRecvVO.getToCcaiAttachment();
//		if(null!=toCcaiAttachment){
//			toCcaiAttachment.setCaseCode(primaryCaseCode);
//			toCcaiAttachmentMapper.insertSelective(toCcaiAttachment);			
//		}
	}

	@Override
	public ToCaseRecv selectByPrimaryKey(String caseCode) {	
		return toCaseRecvMapper.selectByPrimaryKey(caseCode);
	}
	
	/**
	 * @author xiefei1
	 * @since 2017年9月11日 下午1:49:11 
	 * @description 会先根据caseCode查询CaseRecvVO的每个成员在数据库中是否存在来
	 * 				判断是用insert or update;
	 */
	@Override
	public void updateByPrimaryKeySelective(CaseRecvVO caseRecvVO) {
		String primaryCaseCode=caseRecvVO.getCaseCode();
		ToPropertyInfo toPropertyInfo = caseRecvVO.getToPropertyInfo();
		if(null!=toPropertyInfo){
			ToPropertyInfo findToPropertyInfoByCaseCode = toPropertyInfoMapper.findToPropertyInfoByCaseCode(primaryCaseCode);
			if(null!=findToPropertyInfoByCaseCode){
				toPropertyInfo.setPkid(findToPropertyInfoByCaseCode.getPkid());
				toPropertyInfoMapper.updateByPrimaryKeySelective(toPropertyInfo);
			}else{
				toPropertyInfoMapper.insertSelective(toPropertyInfo);
			}
		}
		
		ToSign toSign = caseRecvVO.getToSign();
		if(null!=toSign){
			ToSign findToSignByCaseCode = toSignMapper.findToSignByCaseCode(primaryCaseCode);
			if(null!=findToSignByCaseCode){
				toSignMapper.updateByPrimaryKeySelective(toSign);
			}else{
				toSignMapper.insertSelective(toSign);
			}
		}
		
		ToCaseRecv toCaseRecv = caseRecvVO.getToCaseRecv();
		if(null!=toCaseRecv){
			ToCaseRecv findToCaseRecvByCaseCode = toCaseRecvMapper.selectByPrimaryKey(primaryCaseCode);
			if(null!=findToCaseRecvByCaseCode){
				toCaseRecvMapper.updateByPrimaryKeySelective(toCaseRecv);
			}else{
				toCaseRecvMapper.insertSelective(toCaseRecv);
			}
		}
		
		String payType = caseRecvVO.getPayType();
		if(null!=payType){
			ToCaseInfo toCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(primaryCaseCode);
			if(null!=toCaseInfo){
				toCaseInfo.setPayType(payType);
				toCaseInfoMapper.updateByPrimaryKeySelective(toCaseInfo);
			}else{
				ToCaseInfo toCaseInfo2 = new ToCaseInfo();
				toCaseInfo2.setPayType(payType);
				toCaseInfo2.setCaseCode(toCaseRecv.getCaseCode());
				toCaseInfoMapper.insertSelective(toCaseInfo2);
			}
		}
		
		ToTax toTax = caseRecvVO.getToTax();
		if(null!=toTax){
			ToTax findToTaxByCaseCode = toTaxMapper.findToTaxByCaseCode(primaryCaseCode);
			if(null!=findToTaxByCaseCode){	
				toTaxMapper.updateByPrimaryKeySelective(toTax);
			}else{
				toTaxMapper.insertSelective(toTax);
			}
		}
		
		ToCaseComment toCaseComment = caseRecvVO.getToCaseComment();
		if(null!=toCaseComment){
			toCaseComment.setCaseCode(primaryCaseCode);
			toCaseCommentMapper.insertSelective(toCaseComment);			
		}
		
		BizWarnInfo bizWarnInfo = caseRecvVO.getBizWarnInfo();
		if(null!=bizWarnInfo){
			BizWarnInfo selectByCaseCode = bizWarnInfoMapper.selectByCaseCode(primaryCaseCode);
			if(null!=selectByCaseCode){
				selectByCaseCode.setStatus(bizWarnInfo.getStatus());
				selectByCaseCode.setContent(bizWarnInfo.getContent());
				bizWarnInfoMapper.updateByCaseCode(selectByCaseCode);
				bizWarnInfoMapper.updateStatusByCaseCode(selectByCaseCode);
			}else{
				bizWarnInfoMapper.insertSelective(bizWarnInfo);
			}
		}

	}



	@Override
	public CaseRecvVO selectFullCaseRecvVO(String caseCode) {
		CaseRecvVO caseRecvVO = new CaseRecvVO();
		ToCaseRecv toCaseRecv = toCaseRecvMapper.selectByPrimaryKey(caseCode);
		ToSign toSign = toSignMapper.findToSignByCaseCode(caseCode);
		ToPropertyInfo toPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
		//解决payType
		ToCaseInfo toCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
		BizWarnInfo bizWarnInfo = bizWarnInfoMapper.selectByCaseCode(caseCode);
		
		ToTax toTax = toTaxMapper.findToTaxByCaseCode(caseCode);
		List<ToCaseComment> toCaseCommentsList = toCaseCommentMapper.getToCaseCommentListByCaseCode(caseCode);
		List<ToCcaiAttachment> toCcaiAttachmentsList = toCcaiAttachmentMapper.findAttachmentsByCaseCode(caseCode);
		
		caseRecvVO.setToCaseRecv(toCaseRecv);
		caseRecvVO.setToSign(toSign);
		caseRecvVO.setToPropertyInfo(toPropertyInfo);
		caseRecvVO.setBizWarnInfo(bizWarnInfo);
		//解决payType
		if(null!=toCaseInfo.getPayType()){
			caseRecvVO.setPayType(toCaseInfo.getPayType());			
		}
		caseRecvVO.setToTax(toTax);
		caseRecvVO.setToCaseCommentsList(toCaseCommentsList);
		caseRecvVO.setToCcaiAttachmentsList(toCcaiAttachmentsList);
		
		return caseRecvVO;
	}


	@Override
	public int updateByPrimaryKeySelective(ToCaseRecv record) {
		return toCaseRecvMapper.updateByPrimaryKeySelective(record);
	}

	
	
	
}
