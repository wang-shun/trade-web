package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.web.validate.AjaxResponse;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.apilog.service.SalesDealApiService;
import com.centaline.trans.attachment.entity.ToAttachment;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.attachment.service.ToAttachmentService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;

import com.centaline.trans.common.enums.CaseMergeStatusEnum;
import com.centaline.trans.common.service.TgGuestInfoService;

import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToHouseTransfer;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by caoyuan7 on 2017/4/14.
 */

@Controller
@RequestMapping(value="/task/ToHouseTransfer")
public class ToHouseTransferController {

    private Logger logger = Logger.getLogger(ToHouseTransferController.class);

    @Autowired
    private ToHouseTransferService toHouseTransferService;
    @Autowired(required = true)
    private ToCaseService toCaseService;

    @Autowired
    private TgGuestInfoService tgGuestInfoService;

    @Autowired
    private SalesDealApiService salesdealApiService;

    @Autowired
    private ToMortgageService toMortgageService;

    @Autowired
    private UamSessionService uamSessionService;

    @Autowired
    private ToAccesoryListService toAccesoryListService;

    @Autowired
    private ToAttachmentService toAttachmentService;

    @RequestMapping(value = "process")
    @ResponseBody
    public Object toProcess(HttpServletRequest request, String processInstanceId) {
        String taskId = request.getParameter("taskId");
        String caseCode = request.getParameter("caseCode");
        String taskitem = request.getParameter("taskitem");
        ToAttachment toAttachment = new ToAttachment();
        toAttachment.setCaseCode(caseCode);
        toAttachment.setPartCode("Guohu");

        SessionUser user= uamSessionService.getSessionUser();
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("caseCode", caseCode);
        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        jsonObject.put("approveType", "2");
        jsonObject.put("operator", user != null ? user.getId() : "");
        jsonObject.put("partCode", "Guohu");

        ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
        toAccesoryListService.getAccesoryListGuoHu(request, taskitem, caseCode);
        ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
        List<ToAttachment> attachments = toAttachmentService
                .quereyAttachments(toAttachment);
        if (CollectionUtils.isNotEmpty(attachments)) {
            for (ToAttachment attachment : attachments) {
                if (!StringUtils.isEmpty(attachment.getPreFileCode())) {
                    attachment.setPreFileName(toAccesoryListService.findAccesoryNameByCode(attachment.getPreFileCode()));
                }
            }
        }
        jsonObject.put("attachments", attachments);
        jsonObject.put("accesoryList", request.getAttribute("accesoryList"));
        jsonObject.put("houseTransfer", toHouseTransferService.findToGuoHuByCaseCode(caseCode));
        jsonObject.put("loanReq", toCase.getLoanReq());
        jsonObject.put("toMortgage", toMortgage);
        return jsonObject;
    }

    @RequestMapping(value = "submitToHouseTransfer")
    @ResponseBody
    public Object submitToHouseTransfer(ToHouseTransfer toHouseTransfer,LoanlostApproveVO loanlostApproveVO,String taskId,String processInstanceId) {
        AjaxResponse<?> response = new AjaxResponse<>();
        try {
            ToCase toCase = toCaseService.findToCaseByCaseCode(toHouseTransfer.getCaseCode());
            if(null!=toCase){
                if(CaseMergeStatusEnum.INPUT.getCode().equals(toCase.getCaseOrigin())){
                    response.setSuccess(false);
                    response.setMessage("自建案件必须完成案件合流才能提交过户申请");
                    return response;
                }
                if(toCase.getCtmCode() == null){
                    response.setSuccess(false);
                    response.setMessage("ctmCode不可为空");
                    return response;
                }
                ToMortgage toMortgage =  toMortgageService.findToMortgageByCaseCode2(toHouseTransfer.getCaseCode());
                toHouseTransferService.submitToHouseTransfer(toHouseTransfer, toMortgage, loanlostApproveVO, taskId, processInstanceId);
                // 回写三级市场, 交易过户
                salesdealApiService.noticeSalesDeal(toCase.getCtmCode());
                //给客户发送短信
                if (tgGuestInfoService.sendMsgHistory(toHouseTransfer.getCaseCode(), toHouseTransfer.getPartCode()) >0) {
                    response.setMessage("过户提交成功");
                }else {
                    response.setMessage("短信发送失败, 请您线下手工再次发送！");
                }
            }
            response.setSuccess(true);
        }catch (Exception e){
            response.setSuccess(false);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return response;
    }
}
