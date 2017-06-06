package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.bizwarn.service.BizWarnInfoService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.FirstFollowService;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.vo.FirstFollowVO;
import com.centaline.trans.utils.UiImproveUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by caoyuan7 on 2017/6/5.
 */
@Controller
@RequestMapping(value = "/task/firstFollow")
public class FirstFollowController {

    private Logger logger = Logger.getLogger(FirstFollowController.class);

    @Autowired(required = true)
    private UamSessionService uamSessionService;/* 用户信息 */
    @Autowired(required = true)
    private ToCaseService toCaseService;
    @Autowired
    private FirstFollowService firstFollowService;
    @Autowired
    private BizWarnInfoService bizWarnInfoService;
    @Autowired(required = true)
    private UamUserOrgService uamUserOrgService;/* 用户组织信息 */
    @Autowired
    private WorkFlowManager workFlowManager;
    @Autowired
    private LoanlostApproveService loanlostApproveService;

    @RequestMapping("process")
    @ResponseBody
    public Object toProcess(HttpServletRequest request,String caseCode) {
        JSONObject jsonObject = new JSONObject();

        SessionUser user = uamSessionService.getSessionUser();
        CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
        jsonObject.put("ctmCode", caseBaseVO.getToCase().getCtmCode());
        jsonObject.put("firstFollow", firstFollowService.queryFirstFollow(caseCode));
        jsonObject.put("approveType", "0");
        jsonObject.put("operator", user != null ? user.getId() : "");
        BizWarnInfo bizWarnInfo = bizWarnInfoService.getBizWarnInfoByCaseCode(caseCode);
        jsonObject.put("bizWarnInfo", bizWarnInfo);
        jsonObject.put("caseCode", caseCode);
        return jsonObject;
    }


    /**
     * 获得合作人的orgid
     *
     * @param userId
     * @return
     */
    private String getOrgId(String userId) {
        if (!StringUtils.isBlank(userId)) {
            User user = uamUserOrgService.getUserById(userId);
            if (user != null) {
                return user.getOrgId();
            }
        }
        return null;
    }

    @RequestMapping(value = "submit")
    @ResponseBody
    public Object submit(FirstFollowVO firstFollowVO) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try{
            SessionUser user = uamSessionService.getSessionUser();
            firstFollowVO.setUserId(user.getId());
            firstFollowVO.setUserOrgId(getOrgId(user.getId()));
            firstFollowVO.setUserName(user.getUsername());
            firstFollowService.saveFirstFollow(firstFollowVO);
            /* 无效案件保存到审批记录表 */
            if (firstFollowVO.getCaseProperty().equals("30003001")) {
                saveToApproveRecord(firstFollowVO, firstFollowVO.getOperator(),	firstFollowVO.getApproveType());
            } else {
                firstFollowVO = firstFollowService.switchWorkFlowWithCurrentVersion(firstFollowVO);
            }
            /* 流程引擎相关 */
            List<RestVariable> variables = new ArrayList<RestVariable>();
            RestVariable restVariable = new RestVariable();
            restVariable.setName("isvalid");
            restVariable.setValue(firstFollowVO.getCaseProperty().equals("30003001"));
            variables.add(restVariable);
            if (firstFollowVO.getCaseProperty().equals("30003001")) {
                if (!StringUtils.isBlank(firstFollowVO.getInvalid_reason())) {
                    RestVariable restVariable6 = new RestVariable();
                    restVariable6.setName("invalid_reason");
                    restVariable6.setValue(firstFollowVO.getInvalid_reason());
                    variables.add(restVariable6);
                }
            } else {
                RestVariable restVariable3 = new RestVariable();/* 限购 */
                restVariable3.setName("PurLimitCheckNeed");
                RestVariable restVariable4 = new RestVariable();/* 抵押 */
                restVariable4.setName("LoanCloseNeed");
                restVariable3.setValue(firstFollowVO.getChaxiangou().equals("true"));
                restVariable4.setValue(firstFollowVO.getDiya().equals("true"));
                variables.add(restVariable3);
                variables.add(restVariable4);
            }
            RestVariable signAssignee = new RestVariable();
            signAssignee.setName("signAssignee");
            signAssignee.setValue(user.getUsername());
            variables.add(signAssignee);
            ToCase toCase = toCaseService.findToCaseByCaseCode(firstFollowVO.getCaseCode());
            workFlowManager.submitTask(variables, firstFollowVO.getTaskId(),firstFollowVO.getProcessInstanceId(),toCase.getLeadingProcessId(), firstFollowVO.getCaseCode());
            ajaxResponse.setSuccess(true);
            ajaxResponse.setMessage("首次跟进提交成功");
        }catch (Exception e){
            ajaxResponse.setSuccess(false);
            ajaxResponse.setMessage("首次跟进提交失败");
            logger.error(e.getMessage());
        }
        return ajaxResponse;
    }

    /**
     * 保存审核记录
     *
     * @param firstFollowVO
     * @param operator
     * @param approveType
     * @return
     */

    private ToApproveRecord saveToApproveRecord(FirstFollowVO firstFollowVO,
                                                String operator, String approveType) {
        ToApproveRecord toApproveRecord = new ToApproveRecord();
        // toApproveRecord.setPkid(loanlostApproveVO.getLapPkid());
        toApproveRecord
                .setProcessInstance(firstFollowVO.getProcessInstanceId());
        toApproveRecord.setPartCode(firstFollowVO.getPartCode());
        toApproveRecord.setOperatorTime(new Date());
        toApproveRecord.setApproveType(approveType);
        toApproveRecord.setCaseCode(firstFollowVO.getCaseCode());
        toApproveRecord.setContent(firstFollowVO.getInvalid_reason());
        toApproveRecord.setOperator(operator);
        loanlostApproveService.saveLoanlostApprove(toApproveRecord);
        return toApproveRecord;
    }
}
