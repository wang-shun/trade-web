package com.centaline.trans.taskList.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.service.ToMortgageTosaveService;
import com.centaline.trans.task.vo.MortgageToSaveVO;
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
    @Autowired
    ToMortgageTosaveService toMortgageTosaveService;

    /**
     * 此处有点问题：如果自办贷款挽回失败，就没有相应贷款信息
     * 解决：自办贷款表新增一个字段
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param taskitem
     * @param processInstanceId
     * @return
     */
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
        if(mortgage == null) {
        	MortgageToSaveVO toSaveVO = toMortgageTosaveService.selectByCaseCode(caseCode);
        	mortgage = new ToMortgage();
        	mortgage.setCaseCode(caseCode);
        	mortgage.setLendDate(toSaveVO.getLendDate());
        	mortgage.setRemark(toSaveVO.getRemark());
        }
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
