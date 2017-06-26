package com.centaline.trans.cases.vo;

/**
 * 案件时效延期原因
 * 
 * @author yinjf2
 *
 */
public class CaseEffDelayVo
{
    private int index; // 序号

    private String comment; // 延期原因

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
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
