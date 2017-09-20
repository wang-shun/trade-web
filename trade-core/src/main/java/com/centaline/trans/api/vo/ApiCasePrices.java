package com.centaline.trans.api.vo;

import com.centaline.trans.utils.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 案件业绩记录信息
 * @author yinchao
 * @date 2017/9/14
 */
public class ApiCasePrices {
	//业主应收
	private BigDecimal ownerReceivableCommission;
	//客户应收
	private BigDecimal guestReceivableCommission;
	//业主收佣日期
	private Date ownerCommissionTime;
	//客户收佣日期
	private Date guestCommissionTime;
	//业主折佣
	private BigDecimal ownerCommissionDis;
	//客户折佣
	private BigDecimal guestCommissionDis;
	//业主佣金到期时间
	private Date ownerCommissionMature;
	//客户佣金到期时间
	private Date guestCommissionMature;
	//评估费
	private BigDecimal assessmentFee;
	//应收评估费
	private BigDecimal receivableAssessmentFee;
	//实收评估费
	private BigDecimal receiptsAssessmentFee;

	public BigDecimal getOwnerReceivableCommission() {
		return ownerReceivableCommission;
	}

	public void setOwnerReceivableCommission(BigDecimal ownerReceivableCommission) {
		this.ownerReceivableCommission = ownerReceivableCommission;
	}

	public BigDecimal getGuestReceivableCommission() {
		return guestReceivableCommission;
	}

	public void setGuestReceivableCommission(BigDecimal guestReceivableCommission) {
		this.guestReceivableCommission = guestReceivableCommission;
	}

	public Date getOwnerCommissionTime() {
		return ownerCommissionTime;
	}

	public void setOwnerCommissionTime(Date ownerCommissionTime) {
		this.ownerCommissionTime = ownerCommissionTime;
	}

	public Date getGuestCommissionTime() {
		return guestCommissionTime;
	}

	public void setGuestCommissionTime(Date guestCommissionTime) {
		this.guestCommissionTime = guestCommissionTime;
	}

	public BigDecimal getOwnerCommissionDis() {
		return ownerCommissionDis;
	}

	public void setOwnerCommissionDis(BigDecimal ownerCommissionDis) {
		this.ownerCommissionDis = ownerCommissionDis;
	}

	public BigDecimal getGuestCommissionDis() {
		return guestCommissionDis;
	}

	public void setGuestCommissionDis(BigDecimal guestCommissionDis) {
		this.guestCommissionDis = guestCommissionDis;
	}

	public Date getOwnerCommissionMature() {
		return ownerCommissionMature;
	}

	public void setOwnerCommissionMature(Date ownerCommissionMature) {
		this.ownerCommissionMature = ownerCommissionMature;
	}

	public Date getGuestCommissionMature() {
		return guestCommissionMature;
	}

	public void setGuestCommissionMature(Date guestCommissionMature) {
		this.guestCommissionMature = guestCommissionMature;
	}

	public BigDecimal getAssessmentFee() {
		return assessmentFee;
	}

	public void setAssessmentFee(BigDecimal assessmentFee) {
		this.assessmentFee = assessmentFee;
	}

	public BigDecimal getReceivableAssessmentFee() {
		return receivableAssessmentFee;
	}

	public void setReceivableAssessmentFee(BigDecimal receivableAssessmentFee) {
		this.receivableAssessmentFee = receivableAssessmentFee;
	}

	public BigDecimal getReceiptsAssessmentFee() {
		return receiptsAssessmentFee;
	}

	public void setReceiptsAssessmentFee(BigDecimal receiptsAssessmentFee) {
		this.receiptsAssessmentFee = receiptsAssessmentFee;
	}

	@Override
	public String toString() {
		return "ApiCasePrices{" +
				"业主应收=" + ownerReceivableCommission +
				", 客户应收=" + guestReceivableCommission +
				", 收佣日期(业主)=" + DateUtil.getFormatDate(ownerCommissionTime,"yyyy-MM-dd HH:mmss") +
				", 收佣日期(客户)=" + DateUtil.getFormatDate(guestCommissionTime,"yyyy-MM-dd HH:mmss") +
				", 业主折佣=" + ownerCommissionDis +
				", 客户折佣=" + guestCommissionDis +
				", 业主佣金到期日=" + DateUtil.getFormatDate(ownerCommissionMature,"yyyy-MM-dd HH:mmss") +
				", 客户佣金到期日=" + DateUtil.getFormatDate(guestCommissionMature,"yyyy-MM-dd HH:mmss") +
				", 评估费=" + assessmentFee +
				", 应收评估费=" + receivableAssessmentFee +
				", 实收评估费=" + receiptsAssessmentFee +
				'}';
	}
}
