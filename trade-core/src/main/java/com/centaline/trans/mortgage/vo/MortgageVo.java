package com.centaline.trans.mortgage.vo;

import java.io.Serializable;

import com.aist.uam.auth.remote.vo.SessionUser;

/**
 * 按揭贷款案件前台传值对象
 * 
 * @author yinjf2
 *
 */
public class MortgageVo implements Serializable
{

    private String bizCode; // 按揭信息id

    private String isPass; // 是否接单(true:接单,false:打回)

    private String taskId; // 任务id

    private String procInstanceId; // 流程实例id

    private String stateInBank; // 状态编码

    private String caseCode; // 案件编号

    private String comment; // 跟进备注信息

    private String source; // 来源

    private String type; // 类型

    private SessionUser user; // 当前用户信息

    private String date; // 完成环节时间

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getBizCode()
    {
        return bizCode;
    }

    public void setBizCode(String bizCode)
    {
        this.bizCode = bizCode;
    }

    public SessionUser getUser()
    {
        return user;
    }

    public void setUser(SessionUser user)
    {
        this.user = user;
    }

    public String getIsPass()
    {
        return isPass;
    }

    public void setIsPass(String isPass)
    {
        this.isPass = isPass;
    }

    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    public String getProcInstanceId()
    {
        return procInstanceId;
    }

    public void setProcInstanceId(String procInstanceId)
    {
        this.procInstanceId = procInstanceId;
    }

    public String getStateInBank()
    {
        return stateInBank;
    }

    public void setStateInBank(String stateInBank)
    {
        this.stateInBank = stateInBank;
    }

    public String getCaseCode()
    {
        return caseCode;
    }

    public void setCaseCode(String caseCode)
    {
        this.caseCode = caseCode;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

}
