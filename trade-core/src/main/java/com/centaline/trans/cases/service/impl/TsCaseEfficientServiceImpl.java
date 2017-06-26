package com.centaline.trans.cases.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.entity.TsCaseEfficient;
import com.centaline.trans.cases.repository.TsCaseEfficientMapper;
import com.centaline.trans.cases.service.TsCaseEfficientService;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.repository.ToCaseCommentMapper;

/**
 * 案件时效管理service实现类
 * 
 * @author yinjf2
 *
 */
@Service
public class TsCaseEfficientServiceImpl implements TsCaseEfficientService
{

    @Autowired
    private TsCaseEfficientMapper tsCaseEfficientMapper;

    @Autowired
    private ToCaseCommentMapper toCaseCommentMapper;

    @Override
    public int save(TsCaseEfficient tsCaseEfficient)
    {
        return tsCaseEfficientMapper.insertSelective(tsCaseEfficient);
    }

    @Override
    public boolean isExistByCaseCode(String caseCode)
    {
        return tsCaseEfficientMapper.getCaseEffCountByCaseCode(caseCode) > 0 ? true : false;
    }

    @Override
    public boolean delay(SessionUser currentUser, String caseCode, String partCode, String delayDays, String comment)
    {
        boolean isSuccess = true;

        try
        {
            TsCaseEfficient tsCaseEfficient = tsCaseEfficientMapper.getCaseEffInfoByCasecode(caseCode);

            if (tsCaseEfficient != null)
            {
                if ("FirstFollow".equals(partCode))
                {
                    int oldFirstFollowEff = tsCaseEfficient.getFirstfollowEff();
                    int oldFirstFollowDly = tsCaseEfficient.getFirstfollowDly();

                    int newFirstFollowEff = oldFirstFollowEff + Integer.parseInt(delayDays);
                    int newFirstFollowDly = oldFirstFollowDly + 1;

                    tsCaseEfficient.setFirstfollowEff(newFirstFollowEff);
                    tsCaseEfficient.setFirstfollowDly(newFirstFollowDly);
                }
                else if ("TransSign".equals(partCode))
                {
                    int oldSignEff = tsCaseEfficient.getSignEff();
                    int oldSignDly = tsCaseEfficient.getSignDly();

                    int newSignEff = oldSignEff + Integer.parseInt(delayDays);
                    int newSignDly = oldSignDly + 1;

                    tsCaseEfficient.setSignEff(newSignEff);
                    tsCaseEfficient.setSignDly(newSignDly);
                }
                else if ("Guohu".equals(partCode))
                {
                    int oldGuohuEff = tsCaseEfficient.getGuohuEff();
                    int oldGuohuDly = tsCaseEfficient.getGuohuDly();

                    int newGuohuEff = oldGuohuEff + Integer.parseInt(delayDays);
                    int newGuohuDly = oldGuohuDly + 1;

                    tsCaseEfficient.setGuohuEff(newGuohuEff);
                    tsCaseEfficient.setGuohuDly(newGuohuDly);
                }
                else if ("CaseClose".equals(partCode))
                {
                    int oldCasecloseEff = tsCaseEfficient.getCasecloseEff();
                    int oldCasecloseDly = tsCaseEfficient.getCasecloseDly();

                    int newCasecloseEff = oldCasecloseEff + Integer.parseInt(delayDays);
                    int newCasecloseDly = oldCasecloseDly + 1;

                    tsCaseEfficient.setCasecloseEff(newCasecloseEff);
                    tsCaseEfficient.setCasecloseDly(newCasecloseDly);
                }

                int oldCurDelayDays = tsCaseEfficient.getCurDelayCount();
                int newCurDelayDays = oldCurDelayDays + 1;

                tsCaseEfficient.setCurDelayCount(newCurDelayDays);

                tsCaseEfficientMapper.updateTsCaseEffInfo(tsCaseEfficient);
            }

            // 添加案件跟进信息
            ToCaseComment toCaseComment = new ToCaseComment();
            toCaseComment.setCaseCode(caseCode);
            toCaseComment.setType("CASE_EFF");
            toCaseComment.setSrvCode(partCode);
            toCaseComment.setComment(comment);
            toCaseComment.setCreateTime(new Date());
            toCaseComment.setCreateBy(currentUser.getId());
            toCaseComment.setCreatorOrgId(currentUser.getServiceDepId());

            toCaseCommentMapper.insertSelective(toCaseComment);
        }
        catch (Exception e)
        {
            isSuccess = false;
            e.printStackTrace();
        }

        return isSuccess;
    }

}
