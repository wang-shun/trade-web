package com.centaline.trans.cases.service.impl;



import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
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
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.repository.ToTransPlanMapper;


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
	@Autowired
	private UamBasedataService dictService;
	@Autowired
	private ToTransPlanMapper toTransPlanMapper;
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
//		把预计网签时间和预计评估时间插入DB(transplan表)用于计算红绿灯
//		预计网签时间
		if(null!=caseRecvVO.getToCaseRecv().getEstSignTime()){
			Dict dict = dictService.findDictByTypeAndCode("part_code", "TransSign");
			ToTransPlan transSignTransPlan = new ToTransPlan();
			transSignTransPlan.setCaseCode(primaryCaseCode);
			transSignTransPlan.setPartCode(dict.getCode());
			transSignTransPlan.setEstPartTime(caseRecvVO.getToCaseRecv().getEstSignTime());	
			toTransPlanMapper.insertSelective(transSignTransPlan);
		}
//		预计评估申请时间
		if(null!=caseRecvVO.getToCaseRecv().getEstEvlApplyTime()){
			Dict dict = dictService.findDictByTypeAndCode("eval_part_code", "EvalReport");
			ToTransPlan transSignTransPlan = new ToTransPlan();
			transSignTransPlan.setCaseCode(primaryCaseCode);
			transSignTransPlan.setPartCode(dict.getCode());
			transSignTransPlan.setEstPartTime(caseRecvVO.getToCaseRecv().getEstEvlApplyTime());	
			toTransPlanMapper.insertSelective(transSignTransPlan);
		}
				
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
		
//		ToSign toSign = caseRecvVO.getToSign();
//		if(null!=toSign){
//			ToSign findToSignByCaseCode = toSignMapper.findToSignByCaseCode(primaryCaseCode);
//			if(null!=findToSignByCaseCode){
//				toSignMapper.updateByPrimaryKeySelective(toSign);
//			}else{
//				toSignMapper.insertSelective(toSign);
//			}
//		}
//		数据库中的合同价和成交价都是以元为单位， 而前台的是万元单位，插入和更新在这里转换
		ToSign toSign = caseRecvVO.getToSign();
		double doubleRealPrice = 0d;
		if(null!=toSign.getRealPrice()){
			 doubleRealPrice = toSign.getRealPrice().multiply(new BigDecimal("10000")).doubleValue();			
		}
		double doubleConPrice = 0d;
		if(null!=toSign.getConPrice()){
			 doubleConPrice = toSign.getConPrice().multiply(new BigDecimal("10000")).doubleValue();			
		}
		if(null!=toSign){
			toSign.setRealPrice(new BigDecimal(Double.toString(doubleRealPrice)));
			System.out.println(toSign.getRealPrice());
			toSign.setConPrice(new BigDecimal(Double.toString(doubleConPrice)));
			System.out.println(toSign.getConPrice());
			ToSign findToSignByCaseCode = toSignMapper.findToSignByCaseCode(primaryCaseCode);
			if(null!=findToSignByCaseCode){
				toSign.setPkid(findToSignByCaseCode.getPkid());
				toSignMapper.updateByPrimaryKeySelective(toSign);
			}else{
				toSignMapper.insertSelective(toSign);
			}
		}
		
		ToCaseRecv toCaseRecv = caseRecvVO.getToCaseRecv();
		
		if(null!=toCaseRecv){
			if(null!=toCaseRecv.getOriPrice()&&toCaseRecv.getOriPrice()!=""){
//				DB中存的oriPrice是string,这里转成BIGDECIMAL再X 10000,让DB存元为单位
				String oriPrice = new BigDecimal(toCaseRecv.getOriPrice()).multiply(new BigDecimal("10000")).toString();
				toCaseRecv.setOriPrice(oriPrice);		
			}
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
				toCaseInfo.setPkid(toCaseInfo.getPkid());
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
				toTax.setPkid(findToTaxByCaseCode.getPkid());
				toTaxMapper.updateByPrimaryKeySelective(toTax);
			}else{
				toTaxMapper.insertSelective(toTax);
			}
		}
		/**
		 * 
		 * @author xiefei1
		 * @since 2017年9月28日 上午10:29:29 
		 * @description 用于修改接收前台的comment-->现在前台的comment是通过comment组件来实现，现在不用这个字段了。
		 * 				如果不没注这段代码则会在ToCaseComment这张表插入一条空数据，comment组件会显示这条空数据；
		 */
//		ToCaseComment toCaseComment = caseRecvVO.getToCaseComment();
//		if(null!=toCaseComment){
//			toCaseComment.setCaseCode(primaryCaseCode);
//			toCaseCommentMapper.insertSelective(toCaseComment);			
//		}
		
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
		
//		数据库中的合同价和成交价都是以元为单位， 而前台的是万元单位，插入和更新在这里转换
		ToSign toSign = caseRecvVO.getToSign();
		double doubleRealPrice = toSign.getRealPrice().multiply(new BigDecimal("10000")).doubleValue();
		double doubleConPrice = toSign.getConPrice().multiply(new BigDecimal("10000")).doubleValue();
		if(null!=toSign){
			toSign.setRealPrice(new BigDecimal(Double.toString(doubleRealPrice)));
			toSign.setConPrice(new BigDecimal(Double.toString(doubleConPrice)));
			ToSign findToSignByCaseCode = toSignMapper.findToSignByCaseCode(primaryCaseCode);
			if(null!=findToSignByCaseCode){
				toSignMapper.updateByPrimaryKeySelective(toSign);
			}else{
				toSignMapper.insertSelective(toSign);
			}
		}
		
//		ToSign toSign = caseRecvVO.getToSign();
//		if(null!=toSign){
//			ToSign findToSignByCaseCode = toSignMapper.findToSignByCaseCode(primaryCaseCode);
//			if(null!=findToSignByCaseCode){
//				toSignMapper.updateByPrimaryKeySelective(toSign);
//			}else{
//				toSignMapper.insertSelective(toSign);
//			}
//		}

	
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
		if(null!=toCaseRecv&&StringUtils.isNotBlank(toCaseRecv.getOriPrice())){
			String strOriPrice = new BigDecimal(toCaseRecv.getOriPrice()).divide(new BigDecimal("10000")).toString();
			toCaseRecv.setOriPrice(strOriPrice);			
		}
//		数据库中的合同价和成交价都是以元为单位， 而前台的是万元单位，查询出的数据在这里转换
		ToSign toSign = toSignMapper.findToSignByCaseCode(caseCode);
		BigDecimal realPrice=null;
		BigDecimal conPrice=null;
		if(null!=toSign){
			if(null!=toSign.getRealPrice()){
				realPrice = toSign.getRealPrice().divide(new BigDecimal("10000"));				
				toSign.setRealPrice(realPrice);
			}
			if(null!=toSign.getConPrice()){
				conPrice = toSign.getConPrice().divide(new BigDecimal("10000"));							
				toSign.setConPrice(conPrice);
			}
		}
//		数据库中的合同价和成交价都是以元为单位， 而前台的是万元单位，插入和更新在这里转换
//		ToSign toSign = caseRecvVO.getToSign();
//		double doubleRealPrice = toSign.getRealPrice().multiply(new BigDecimal("10000")).doubleValue();
//		double doubleConPrice = toSign.getConPrice().multiply(new BigDecimal("10000")).doubleValue();
//		if(null!=toSign){
//			toSign.setRealPrice(new BigDecimal(Double.toString(doubleRealPrice)));
//			toSign.setConPrice(new BigDecimal(Double.toString(doubleConPrice)));
//			ToSign findToSignByCaseCode = toSignMapper.findToSignByCaseCode(primaryCaseCode);
//			if(null!=findToSignByCaseCode){
//				toSignMapper.updateByPrimaryKeySelective(toSign);
//			}else{
//				toSignMapper.insertSelective(toSign);
//			}
//		}
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
		caseRecvVO.setCaseCode(caseCode);
		return caseRecvVO;
	}


	@Override
	public int updateByPrimaryKeySelective(ToCaseRecv record) {
		return toCaseRecvMapper.updateByPrimaryKeySelective(record);
	}

	
	
	
}
