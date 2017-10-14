package com.centaline.trans.eval.entity;

import java.math.BigDecimal;
/** 
 * @author xiefei1
 * @since 2017年10月11日 下午3:14:04 
 * @description 调佣
 */
public class ToEvaCommissionChange {
    private Long pkid;

    private String caseCode;

    private String evaCode;
//    调佣事项
    private String changeChargesItem;
//    调佣事由
    private String changeChargesCause;
//    中介费折评估费总金额
    private BigDecimal agEvalAmount;
//    评估调佣审批状态
    private String status;

    private String evaProcessId;

    private String type;
//    调佣类型
    private String changeChargesType;
//    佣金总数
    private BigDecimal commisionTtlAmount;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getEvaCode() {
        return evaCode;
    }

    public void setEvaCode(String evaCode) {
        this.evaCode = evaCode == null ? null : evaCode.trim();
    }

    public String getChangeChargesItem() {
        return changeChargesItem;
    }

    public void setChangeChargesItem(String changeChargesItem) {
        this.changeChargesItem = changeChargesItem == null ? null : changeChargesItem.trim();
    }

    public String getChangeChargesCause() {
        return changeChargesCause;
    }

    public void setChangeChargesCause(String changeChargesCause) {
        this.changeChargesCause = changeChargesCause == null ? null : changeChargesCause.trim();
    }

    public BigDecimal getAgEvalAmount() {
        return agEvalAmount;
    }

    public void setAgEvalAmount(BigDecimal agEvalAmount) {
        this.agEvalAmount = agEvalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getEvaProcessId() {
        return evaProcessId;
    }

    public void setEvaProcessId(String evaProcessId) {
        this.evaProcessId = evaProcessId == null ? null : evaProcessId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getChangeChargesType() {
        return changeChargesType;
    }

    public void setChangeChargesType(String changeChargesType) {
        this.changeChargesType = changeChargesType == null ? null : changeChargesType.trim();
    }

    public BigDecimal getCommisionTtlAmount() {
        return commisionTtlAmount;
    }

    public void setCommisionTtlAmount(BigDecimal commisionTtlAmount) {
        this.commisionTtlAmount = commisionTtlAmount;
    }
}