package com.centaline.trans.taskList.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.utils.UiImproveUtil;

/**
 * update by wbshume
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

    @RequestMapping(value = "process")
    public String toProcess(HttpServletRequest request, HttpServletResponse response, String caseCode, String source, String taskitem, String processInstanceId)
    {
    	//数据库表T_TO_ACCESORY_LIST需加相应数据：放款所需附件
    	toAccesoryListService.getAccesoryList(request, taskitem);
    	CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
        request.setAttribute("source", source);
        request.setAttribute("caseBaseVO", caseBaseVO);
        request.setAttribute("caseCode", caseCode);
        ToMortgage mortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
        toAccesoryListService.getAccesoryList(request, taskitem);

        /*RestVariable psf = workFlowManager.getVar(processInstanceId, "PSFLoanNeed"); //公积金 
        boolean tz = !(boolean) (psf == null ? false : psf.getValue());
        // 公积金的话无他证送抵时间
        if (mortgage != null && "30016003".equals(mortgage.getMortType()) && "1".equals(mortgage.getIsDelegateYucui()))
        {
            tz = false;
        }
        request.setAttribute("tz", tz);*/
        
        request.setAttribute("loanRelease", mortgage);
        
        return "task" + UiImproveUtil.getPageType(request) + "/taskLoanRelease";
    }
}
