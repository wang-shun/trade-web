package com.centaline.trans.award.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TsKpiPsnMonth {
    private Long pkid;

    private Date belongMonth;

    private String type;

    private String participant;
    
    private String participantName;

    private String teamId;

    private String districtId;

    private Integer finOrder;

    private Integer finOrderRoll;

    private BigDecimal finOrderRate;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private Integer orgOrder;

    private Integer totalCase;
    
    private String comments;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public Date getBelongMonth() {
        return belongMonth;
    }

    public void setBelongMonth(Date belongMonth) {
        this.belongMonth = belongMonth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant == null ? null : participant.trim();
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId == null ? null : teamId.trim();
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId == null ? null : districtId.trim();
    }

    public Integer getFinOrder() {
        return finOrder;
    }

    public void setFinOrder(Integer finOrder) {
        this.finOrder = finOrder;
    }

    public Integer getFinOrderRoll() {
        return finOrderRoll;
    }

    public void setFinOrderRoll(Integer finOrderRoll) {
        this.finOrderRoll = finOrderRoll;
    }

    public BigDecimal getFinOrderRate() {
        return finOrderRate;
    }

    public void setFinOrderRate(BigDecimal finOrderRate) {
        this.finOrderRate = finOrderRate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Integer getOrgOrder() {
        return orgOrder;
    }

    public void setOrgOrder(Integer orgOrder) {
        this.orgOrder = orgOrder;
    }

    public Integer getTotalCase() {
        return totalCase;
    }

    public void setTotalCase(Integer totalCase) {
        this.totalCase = totalCase;
    }

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}
}