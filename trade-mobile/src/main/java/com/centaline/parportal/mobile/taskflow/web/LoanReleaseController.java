package com.centaline.parportal.mobile.taskflow.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.Result2;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;

/**
 * 放款controller
 * 
 * @author yinjf2
 *
 */
@Controller
@RequestMapping("/task/loanRelease")
public class LoanReleaseController
{
    @Autowired
    private WorkFlowManager workFlowManager;
    @Autowired
    private ToAccesoryListService toAccesoryListService;
    @Autowired
    private ToMortgageService toMortgageService;
    @Autowired
    private ToCaseService toCaseService;

    /**
     * 放款详情
     * 
     * @param request
     * @param response
     * @param caseCode
     *            案件编号
     * @param source
     *            来源
     * @param taskitem
     *            环节编码
     * @param taskId
     *            任务id
     * @param processInstanceId
     *            流程实例id
     * @return 放款详情信息
     */
    @RequestMapping(value = "process")
    @ResponseBody
    public Object toProcess(HttpServletRequest request, HttpServletResponse response, String caseCode, String source, String taskitem, String taskId,
            String processInstanceId)
    {
        JSONObject jsonObject = new JSONObject();
        CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);

        RestVariable psf = workFlowManager.getVar(processInstanceId, "PSFLoanNeed");/* 公积金 */
        boolean tz = !(boolean) (psf == null ? false : psf.getValue());
        toAccesoryListService.getAccesoryList(request, taskitem);
        ToMortgage mortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
        // 公积金的话无他证送抵时间
        if ("30016003".equals(mortgage.getMortType()) && "1".equals(mortgage.getIsDelegateYucui()))
        {
            tz = false;
        }

        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        jsonObject.put("caseBaseVO", caseBaseVO);
        jsonObject.put("tz", tz);
        jsonObject.put("loanRelease", mortgage);
        jsonObject.put("caseCode", caseCode);
        jsonObject.put("source", source);
        jsonObject.put("taskitem", taskitem);

        return jsonObject;
    }

    /**
     * 放款信息提交
     * 
     * @param request
     * @param toMortgage
     *            贷款信息
     * @param taskitem
     *            环节编码
     * @param estPartTime
     *            放款时间
     * @param taskId
     *            任务id
     * @param processInstanceId
     *            流程示例id
     * @param partCode
     *            环节编码
     * @return 提交结果
     */
    @RequestMapping(value = "submitLoanRelease")
    @ResponseBody
    public Result2 submitLoanRelease(HttpServletRequest request, ToMortgage toMortgage, String taskitem, Date estPartTime, String taskId,
            String processInstanceId, String partCode)
    {
        return toMortgageService.submitLoanRelease(request, toMortgage, taskitem, estPartTime, taskId, processInstanceId, partCode);
    }
}
