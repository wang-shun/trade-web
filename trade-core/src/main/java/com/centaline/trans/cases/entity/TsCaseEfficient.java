package com.centaline.trans.cases.entity;

import java.util.Date;

/**
 * 案件时效实体类
 * 
 * @author yinjf2
 *
 */
public class TsCaseEfficient
{
    private Long pkid; // 主键id

    private String caseCode; // 案件编号

    private Date dispatchTime; // 实际派单时间

    private Date firstfollowTime; // 实际首次跟进时间

    private Date signTime; // 实际签约时间

    private Date guohuTime; // 实际过户时间

    private Date casecloseTime; // 实际结案时间

    private Integer firstfollowEff; // 首次跟进时效

    private Integer firstfollowDly; // 首次跟进延期次数

    private Integer signEff; // 签约时效

    private Integer signDly; // 签约延期次数

    private Integer guohuEff; // 过户时效

    private Integer guohuDly; // 过户延期次数

    private Integer casecloseEff; // 结案时效

    private Integer casecloseDly; // 结案延期次数

    private Date createTime; // 创建时间

    private String createBy; // 创建人

    private Date updateTime; // 更新时间

    private String updateBy; // 更新人

    public Long getPkid()
    {
        return pkid;
    }

    public void setPkid(Long pkid)
    {
        this.pkid = pkid;
    }

    public String getCaseCode()
    {
        return caseCode;
    }

    public void setCaseCode(String caseCode)
    {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public Date getDispatchTime()
    {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime)
    {
        this.dispatchTime = dispatchTime;
    }

    public Date getFirstfollowTime()
    {
        return firstfollowTime;
    }

    public void setFirstfollowTime(Date firstfollowTime)
    {
        this.firstfollowTime = firstfollowTime;
    }

    public Date getSignTime()
    {
        return signTime;
    }

    public void setSignTime(Date signTime)
    {
        this.signTime = signTime;
    }

    public Date getGuohuTime()
    {
        return guohuTime;
    }

    public void setGuohuTime(Date guohuTime)
    {
        this.guohuTime = guohuTime;
    }

    public Date getCasecloseTime()
    {
        return casecloseTime;
    }

    public void setCasecloseTime(Date casecloseTime)
    {
        this.casecloseTime = casecloseTime;
    }

    public Integer getFirstfollowEff()
    {
        return firstfollowEff;
    }

    public void setFirstfollowEff(Integer firstfollowEff)
    {
        this.firstfollowEff = firstfollowEff;
    }

    public Integer getFirstfollowDly()
    {
        return firstfollowDly;
    }

    public void setFirstfollowDly(Integer firstfollowDly)
    {
        this.firstfollowDly = firstfollowDly;
    }

    public Integer getSignEff()
    {
        return signEff;
    }

    public void setSignEff(Integer signEff)
    {
        this.signEff = signEff;
    }

    public Integer getSignDly()
    {
        return signDly;
    }

    public void setSignDly(Integer signDly)
    {
        this.signDly = signDly;
    }

    public Integer getGuohuEff()
    {
        return guohuEff;
    }

    public void setGuohuEff(Integer guohuEff)
    {
        this.guohuEff = guohuEff;
    }

    public Integer getGuohuDly()
    {
        return guohuDly;
    }

    public void setGuohuDly(Integer guohuDly)
    {
        this.guohuDly = guohuDly;
    }

    public Integer getCasecloseEff()
    {
        return casecloseEff;
    }

    public void setCasecloseEff(Integer casecloseEff)
    {
        this.casecloseEff = casecloseEff;
    }

    public Integer getCasecloseDly()
    {
        return casecloseDly;
    }

    public void setCasecloseDly(Integer casecloseDly)
    {
        this.casecloseDly = casecloseDly;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getCreateBy()
    {
        return createBy;
    }

    public void setCreateBy(String createBy)
    {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getUpdateBy()
    {
        return updateBy;
    }

    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }
}