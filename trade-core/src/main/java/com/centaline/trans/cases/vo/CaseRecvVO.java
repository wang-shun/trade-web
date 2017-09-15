package com.centaline.trans.cases.vo;

import java.util.Date;
import java.util.List;

import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.cases.entity.ToCaseRecv;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.common.entity.ToCcaiAttachment;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.entity.ToTax;

/**
 * @author xiefei1
 * @since 2017年9月4日 下午3:04:40 
 * @description 接单跟进VO;
 */
public class CaseRecvVO {
	private String caseCode;
	/*主要使用的字段private String distCode;
	private String square;
	private String propertyAddr;*/
	private ToPropertyInfo toPropertyInfo;
	/*主要使用的字段private Double realPrice;
	private Double conPrice;*/
	private ToSign toSign;

	private ToCaseRecv toCaseRecv;
	
	private String payType;
//	卖方房屋是否唯一
	//主要使用的字段private String isUniqueHouse;
	private ToTax toTax;
//	案件跟进  主要使用的字段
	/*private String followHistory;
	private String follow;*/
//	商代预警
	private BizWarnInfo bizWarnInfo;
//	用于修改接收前台的comment
	private ToCaseComment toCaseComment;
	
//	用于展示commentList
	private List<ToCaseComment> toCaseCommentsList;
	
//	用于查看ccaiAttachment;
	private List<ToCcaiAttachment> toCcaiAttachmentsList;
	
	public String getCaseCode() {
		return caseCode;
	}
	
	/**
	 * 
	 * @since:2017年9月5日 上午10:18:32
	 * @description:统一设置caseCode,建议设置完field之后再调用该方法来避免空指针异常;
	 * 				建议先判断该属性是否为空；
	 * @author:xiefei1
	 * @param caseCode
	 */
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
		if (null != this.toPropertyInfo) {
			this.toPropertyInfo.setCaseCode(caseCode);
		}
		if (null != this.toSign) {
			this.toSign.setCaseCode(caseCode);
		}
		if (null != this.toCaseRecv) {
			this.toCaseRecv.setCaseCode(caseCode);
		}
		if (null != this.toTax) {
			this.toTax.setCaseCode(caseCode);
		}
		if (null != this.toCaseComment) {
			this.toCaseComment.setCaseCode(caseCode);
		}
		if (null != this.bizWarnInfo) {
			this.bizWarnInfo.setCaseCode(caseCode);
		}				
	}
	public ToPropertyInfo getToPropertyInfo() {
		return toPropertyInfo;
	}
	public void setToPropertyInfo(ToPropertyInfo toPropertyInfo) {
		this.toPropertyInfo = toPropertyInfo;
	}
	public ToSign getToSign() {
		return toSign;
	}
	public void setToSign(ToSign toSign) {
		this.toSign = toSign;
	}
	public ToCaseRecv getToCaseRecv() {
		return toCaseRecv;
	}
	public void setToCaseRecv(ToCaseRecv toCaseRecv) {
		this.toCaseRecv = toCaseRecv;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public ToTax getToTax() {
		return toTax;
	}
	public void setToTax(ToTax toTax) {
		this.toTax = toTax;
	}
	public ToCaseComment getToCaseComment() {
		return toCaseComment;
	}
	public void setToCaseComment(ToCaseComment toCaseComment) {
		this.toCaseComment = toCaseComment;
	}

	public List<ToCaseComment> getToCaseCommentsList() {
		return toCaseCommentsList;
	}

	public void setToCaseCommentsList(List<ToCaseComment> toCaseCommentsList) {
		this.toCaseCommentsList = toCaseCommentsList;
	}

	public List<ToCcaiAttachment> getToCcaiAttachmentsList() {
		return toCcaiAttachmentsList;
	}

	public void setToCcaiAttachmentsList(List<ToCcaiAttachment> toCcaiAttachmentsList) {
		this.toCcaiAttachmentsList = toCcaiAttachmentsList;
	}

	public BizWarnInfo getBizWarnInfo() {
		return bizWarnInfo;
	}

	public void setBizWarnInfo(BizWarnInfo bizWarnInfo) {
		this.bizWarnInfo = bizWarnInfo;
	}


	
	

	


}
