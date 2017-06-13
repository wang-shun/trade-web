package com.centaline.parportal.mobile.taskflow.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aist.common.web.validate.AjaxResponse;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.MsgLampEnum;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;
/**
 * Created by caoyuan7 on 2017/4/25.
 */

@Controller
@RequestMapping(value="/task/caselostApprove")
public class CaseCloseApproveController {

    private Logger logger = Logger.getLogger(PricingController.class);

    /*发送消息*/
    @Autowired(required=true)
    @Qualifier("uamMessageServiceClient")
    private UamMessageService uamMessageService;
    @Autowired(required=true)
    private UamTemplateService uamTemplateService;
    @Autowired
    private ToPropertyInfoService toPropertyInfoService;
    @Autowired(required = true)
    private UamSessionService uamSessionService;/*用户信息*/

    @Autowired
    private LoanlostApproveService loanlostApproveService;

    @RequestMapping("first/process")
    @ResponseBody
    public Object toFirstProcess(HttpServletRequest request,String processInstanceId){
        String taskId = request.getParameter("taskId");
        String caseCode = request.getParameter("caseCode");
        JSONObject jsonObject = new JSONObject();
        SessionUser user=uamSessionService.getSessionUser();
        jsonObject.put("caseCode", caseCode);
        jsonObject.put("partCode", "CaseCloseFirstApprove");
        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        jsonObject.put("approveType", "3");
        jsonObject.put("operator", user != null ? user.getId():"");
       return jsonObject;
    }
    @RequestMapping("second/process")
    @ResponseBody
    public Object toSecondProcess(HttpServletRequest request,String processInstanceId){
        String taskId = request.getParameter("taskId");
        String caseCode = request.getParameter("caseCode");
        SessionUser user=uamSessionService.getSessionUser();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("caseCode", caseCode);
        jsonObject.put("partCode", "CaseCloseSecondApprove");
        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        jsonObject.put("approveType", "3");
        jsonObject.put("operator", user != null ? user.getId() : "");
        return jsonObject;
    }


    @RequestMapping(value="caselostApproveFirst")
    @ResponseBody
    public Object caselostApproveFirst(ProcessInstanceVO processInstanceVO,LoanlostApproveVO loanlostApproveVO, String CaseCloseFirstCheck, String CaseCloseFirstCheck_response) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            ajaxResponse = loanlostApproveService.saveAndSubmitLoanlostApproveFirst(processInstanceVO, loanlostApproveVO, CaseCloseFirstCheck, CaseCloseFirstCheck_response);
            ajaxResponse.setMessage("审核成功");
        }catch (Exception e){
            ajaxResponse.setMessage("审核失败");
            ajaxResponse.setSuccess(false);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ajaxResponse;
    }

    @RequestMapping(value="caselostApproveSecond")
    @ResponseBody
    public Object caselostApproveSecond(ProcessInstanceVO processInstanceVO,LoanlostApproveVO loanlostApproveVO, String CaseCloseSecondCheck, String CaseCloseSecondCheck_response) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            ajaxResponse = loanlostApproveService.saveAndSubmitLoanlostApproveSecond(processInstanceVO, loanlostApproveVO, CaseCloseSecondCheck, CaseCloseSecondCheck_response);
            ajaxResponse.setMessage("审核成功");
        }catch (Exception e){
            ajaxResponse.setMessage("审核失败");
            ajaxResponse.setSuccess(false);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ajaxResponse;
    }

    @RequestMapping(value="caselostApproveThird")
    @ResponseBody
    public Object caselostApproveThird(HttpServletRequest request, ProcessInstanceVO processInstanceVO,LoanlostApproveVO loanlostApproveVO, String CaseCloseThirdCheck, String CaseCloseThirdCheck_response) {

        AjaxResponse ajaxResponse = new AjaxResponse();
        try {
            boolean b = CaseCloseThirdCheck.equals("true");
            String message = (b?"通过":"不通过") + (",审批意见为"+CaseCloseThirdCheck_response);
            ajaxResponse = loanlostApproveService.saveAndSubmitLoanlostApproveThird(processInstanceVO, loanlostApproveVO, CaseCloseThirdCheck, CaseCloseThirdCheck_response);
            sendMessage(processInstanceVO, message, loanlostApproveVO.getApproveType());
            ajaxResponse.setMessage("审核成功");
        }catch (Exception e){
            ajaxResponse.setMessage("审核失败");
            ajaxResponse.setSuccess(false);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ajaxResponse;
    }

    /**
     * 发送审批结果提醒
     * @param processInstanceVO
     * @param approveContent
     * @param approveType
     */
    private void sendMessage(ProcessInstanceVO processInstanceVO, String approveContent, String approveType) {
		/*消息模版code*/
        String resourceCode = MsgLampEnum.APPROVE_RESULT_REMINDER.getCode();
		/*消息标题*/
        String title = MsgLampEnum.APPROVE_RESULT_REMINDER.getName();
        //创建map放入消息参数
        Map<String, Object> params = new HashMap<String, Object>();
        //放入参数
        params.put("case_code", processInstanceVO.getCaseCode());
        ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(processInstanceVO.getCaseCode());
        if(toPropertyInfo != null) {
            params.put("property_address", toPropertyInfo.getPropertyAddr());
        } else {
            params.put("property_address","");
        }
        params.put("approver", uamSessionService.getSessionUser().getRealName());
        params.put("part_name", "结案审批");
        params.put("approve_content", approveContent);

        //拼接发送的字符串
        String content = uamTemplateService.mergeTemplate(resourceCode, params);

        Message message= new Message();
        //消息标题
        message.setTitle(title);
        //消息类型
        message.setType(MessageType.SITE);
		/*设置提醒列别*/
        message.setMsgCatagory(MsgCatagoryEnum.RESPON.getCode());
		/*内容*/
        message.setContent(content);
		/*发送人*/
        String senderId = uamSessionService.getSessionUser().getId();
        message.setSenderId(senderId);
		/*接收人*/
        ToApproveRecord toApproveRecord = new ToApproveRecord();
        toApproveRecord.setCaseCode(processInstanceVO.getCaseCode());
        toApproveRecord.setApproveType(approveType);
        List<String> list = loanlostApproveService.findApproveRecordByList(toApproveRecord);
        for(String operator : list) {
            if(operator.equals(senderId)) {/*不给自己发送提醒*/
                continue;
            }
			/*发送*/
            uamMessageService.sendMessageByDist(message, operator);
        }
    }

}