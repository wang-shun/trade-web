package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.web.validate.AjaxResponse;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.apilog.service.SalesDealApiService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;

import com.centaline.trans.common.enums.CaseMergeStatusEnum;
import com.centaline.trans.common.service.TgGuestInfoService;

import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToHouseTransfer;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(value = "process")
    @ResponseBody
    public JSONObject toProcess(HttpServletRequest request, String processInstanceId) {
        String taskId = request.getParameter("taskId");
        SessionUser user= uamSessionService.getSessionUser();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        jsonObject.put("approveType", "2");
        jsonObject.put("operator", user != null ? user.getId() : "");
        jsonObject.put("partCode", "Guohu");
        return jsonObject;
    }

    @RequestMapping(value = "submitToHouseTransfer")
    @ResponseBody
    public AjaxResponse submitToHouseTransfer(ToHouseTransfer toHouseTransfer,LoanlostApproveVO loanlostApproveVO,String taskId,String processInstanceId) {
        AjaxResponse<?> response = new AjaxResponse<>();
        try {
            ToCase toCase = toCaseService.findToCaseByCaseCode(toHouseTransfer.getCaseCode());
            if(null!=toCase){
                if(toCase.getCtmCode() == null){
                    response.setSuccess(false);
                    response.setMessage("ctmCode不可为空");
                    return response;
                }
                if(CaseMergeStatusEnum.INPUT.getCode().equals(toCase.getCaseOrigin())){
                    response.setSuccess(false);
                    response.setMessage("自建案件必须完成案件合流才能提交过户申请");
                    return response;
                }

                ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode2(toHouseTransfer.getCaseCode());
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
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return response;
    }
}
