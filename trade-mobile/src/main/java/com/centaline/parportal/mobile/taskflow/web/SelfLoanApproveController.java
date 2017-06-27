package com.centaline.parportal.mobile.taskflow.web;

/**
 * Created by caoyuan7 on 2017/6/27.
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.service.LoanlostApproveService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/task/selfLoanApprove")
public class SelfLoanApproveController {
    @Autowired
    private UamSessionService uamSessionService;
    @Autowired
    private ToCaseService toCaseService;
    @Autowired
    private LoanlostApproveService loanlostApproveService;
    @Autowired
    private ToMortgageService toMortgageService;
    @Autowired
    private TgGuestInfoService tgGuestInfoService;
    @Autowired
    private UamBasedataService uamBasedataService;/* 字典 */

    @RequestMapping(value = "process")
    @ResponseBody
    public Object toProcess(HttpServletRequest request,String caseCode, String source,String processInstanceId) {
        String taskId = request.getParameter("taskId");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        jsonObject.put("source", source);
        jsonObject.put("partCode", "SelfLoanApprove");

        SessionUser user = uamSessionService.getSessionUser();
        CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
        // 税费卡
        int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
        if (cou > 0) {
            caseBaseVO.setLoanType("30004005");
        }
        jsonObject.put("caseBaseVO", caseBaseVO);
        jsonObject.put("approveType", "1");
        jsonObject.put("operator", user != null ? user.getId() : "");
        jsonObject.put("caseDetail", loanlostApproveService.queryCaseInfo(caseCode, "LoanlostApply", processInstanceId));
        Dict dict = uamBasedataService.findDictByTypeAndLevel("30016", "2");
        jsonObject.put("mortTypeCN", dict);
        ToMortgage mortgage = toMortgageService.findToSelfLoanMortgage(caseCode);
        jsonObject.put("SelfLoan", mortgage);
        if (mortgage != null && mortgage.getCustCode() != null) {
            TgGuestInfo guest = tgGuestInfoService.selectByPrimaryKey(Long.parseLong(mortgage.getCustCode()));
            if (null != guest) {
                jsonObject.put("custCompany", guest.getWorkUnit());
                jsonObject.put("custName", guest.getGuestName());
            }
        }
        return jsonObject;
    }
}
