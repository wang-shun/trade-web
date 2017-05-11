package com.centaline.trans.satisfaction.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseDetailProcessorVO;
import com.centaline.trans.cases.vo.CaseDetailShowVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.SatisfactionStatusEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.enums.TransPositionEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.satisfaction.entity.ToSatisfaction;
import com.centaline.trans.satisfaction.service.SatisfactionService;

@Controller
@RequestMapping("satis")
public class SatisController {
  
  @Autowired
  private SatisfactionService satisfactionService;
  @Autowired
  ToCaseService toCaseService;
  @Autowired
  ToCaseInfoService toCaseInfoService;
  @Autowired
  ToPropertyInfoService toPropertyInfoService;
  @Autowired
  TgServItemAndProcessorService tgServItemAndProcessorService;
  @Autowired
  TgGuestInfoService tgGuestInfoService;
  @Autowired
  PropertyUtilsServiceImpl propertyUtilsService;
  
  @Autowired
  UamUserOrgService uamUserOrgService;
  @Autowired
  UamSessionService uamSessionService;
  
  @Autowired
  WorkFlowManager workFlowManager;
  
  @Autowired
  ToWorkFlowService toWorkFlowService;
  
  //页面
  @RequestMapping("/list")
  public String list(Model model){
    return "satis/satisList";
  }
  
  @RequestMapping("/task/signDetail")
  public String signDetail(Model model, Long satisId){
    
    setCaseInfoToModel(model, satisId);
    
    return "satis/sign_detail";
  }
  
  @RequestMapping("/task/guohuDetail")
  public String guohuDetail(Model model, Long satisId, String taskId, String instCode){
    
    setCaseInfoToModel(model, satisId);
    
    return "satis/guohu_detail";
  }
  
  @RequestMapping("/task/signReturn")
  public String signReturn(Model model, Long satisId, String taskId, String instCode){
    
    setCaseInfoToModel(model, satisId);
        
    return "satis/sign_return";
  }
  
  @RequestMapping("/task/guohuReturn")
  public String guohuReturn(Model model, Long satisId, String taskId, String instCode){
  
    setCaseInfoToModel(model , satisId);
    
    return "satis/guohu_return";
  }
  
  //操作
  @RequestMapping("/doDispatch")
  @ResponseBody
  public AjaxResponse<String> dispatch(String caseCodes,String userId){
    SessionUser user = uamSessionService.getSessionUser();
    SessionUser caller = uamSessionService.getSessionUserById(userId);
    AjaxResponse<String> response = new AjaxResponse<String>();
    uamSessionService.getSessionUserById(userId);
    String[] caseCodesArr = caseCodes.split(",");
    try{
      for(String caseCode : caseCodesArr){
        /**
         * 查询案件签约和过户阶段对应的交易顾问
         * 1.字典表查出该案件下签约(3000401001)和过户(3000401002)对应的SRV_CODE
         * 2.sctrans.T_TG_SERV_ITEM_AND_PROCESSOR表查出SRV_CODE对应的PROCESSORID
         * 3.根据PROCESSORID查出用户信息
         */
        TgServItemAndProcessor record = new TgServItemAndProcessor();
        record.setCaseCode(caseCode);
        record.setSrvCode("3000401001");
        TgServItemAndProcessor processor1 = tgServItemAndProcessorService.findTgServItemAndProcessor(record);
        record.setSrvCode("3000401002");
        TgServItemAndProcessor processor2 = tgServItemAndProcessorService.findTgServItemAndProcessor(record);
        SessionUser consultant1 = uamSessionService.getSessionUserById(processor1.getProcessorId());
        SessionUser consultant2 = uamSessionService.getSessionUserById(processor2.getProcessorId());
        List<RestVariable> variables = new ArrayList<RestVariable>();
        variables.add(new RestVariable("caller", caller.getUsername()));
        variables.add(new RestVariable("consultant1",consultant1.getUsername()));
        variables.add(new RestVariable("consultant2",consultant2.getUsername()));
        StartProcessInstanceVo vo = workFlowManager.startWorkFlow(new ProcessInstance(propertyUtilsService.getSatisProcessDfKey(),
            WorkFlowEnum.SATIS_DEFKEY.getCode(), variables));
        //插入流程表
        ToWorkFlow twf = new ToWorkFlow();
        twf.setBizCode(caseCode);
        twf.setCaseCode(caseCode);
        twf.setBusinessKey(WorkFlowEnum.SATIS_DEFKEY.getCode());
        twf.setProcessDefinitionId(propertyUtilsService.getProcessTmpBankAuditDfKey());
        twf.setProcessOwner(user.getId());
        twf.setInstCode(vo.getId());
        twf.setStatus(WorkFlowStatus.ACTIVE.getCode());
        toWorkFlowService.insertSelective(twf);
        //更新满意度表
        ToSatisfaction toSatisfaction = satisfactionService.queryToSatisfactionByCaseCode(caseCode);
        toSatisfaction.setStatus(SatisfactionStatusEnum.SIGN_SURVEY_ING.getCode());
        toSatisfaction.setUpdateBy(user.getId());
        toSatisfaction.setUpdateTime(new Date());
        toSatisfaction.setCallerId(caller.getId());
        satisfactionService.updateSelective(toSatisfaction);
      }
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败!"+e.getMessage());
    }
    return response;
  }
  
  @RequestMapping("/doSignPass")
  @ResponseBody
  public AjaxResponse<String> signPass(ToSatisfaction toSatisfaction, String taskId, String instCode){
    SessionUser user = uamSessionService.getSessionUser();
    AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      //1.更新状态
      toSatisfaction.setStatus(SatisfactionStatusEnum.GUOHU_SURVEY_ING.getCode());
      toSatisfaction.setUpdateBy(user.getId());
      toSatisfaction.setUpdateTime(new Date());
      satisfactionService.updateSelective(toSatisfaction);
      //2.设置流程变量
      List<RestVariable> variables = new ArrayList<RestVariable>();
      variables.add(new RestVariable("signResult",true));
      //3.推进流程
      workFlowManager.submitTask(variables, taskId, instCode, null, toSatisfaction.getCaseCode());
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
    }
    return response;
  }
  
  @RequestMapping("/doSignReject")
  @ResponseBody
  public AjaxResponse<String> signReject(ToSatisfaction toSatisfaction, String taskId, String instCode){
    SessionUser user = uamSessionService.getSessionUser();
    AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      //1.更新状态
      toSatisfaction.setStatus(SatisfactionStatusEnum.SIGN_SURVEY_REJECT.getCode());
      toSatisfaction.setUpdateBy(user.getId());
      toSatisfaction.setUpdateTime(new Date());
      satisfactionService.updateSelective(toSatisfaction);
      //2.设置流程变量
      List<RestVariable> variables = new ArrayList<RestVariable>();
      variables.add(new RestVariable("signResult",false));
      //3.推进流程
      workFlowManager.submitTask(variables, taskId, instCode, null, toSatisfaction.getCaseCode());
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
    }
    return response;
  }
  
  @RequestMapping("/doSignFollow")
  @ResponseBody
  public AjaxResponse<String> signFollow(ToSatisfaction toSatisfaction, String taskId, String instCode){
    SessionUser user = uamSessionService.getSessionUser();
    AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      //1.更新状态
      toSatisfaction.setStatus(SatisfactionStatusEnum.SIGN_SURVEY_ING.getCode());
      toSatisfaction.setUpdateBy(user.getId());
      toSatisfaction.setUpdateTime(new Date());
      satisfactionService.updateSelective(toSatisfaction);
      //2.设置流程变量
      List<RestVariable> variables = new ArrayList<RestVariable>();
      //3.推进流程
      workFlowManager.submitTask(variables, taskId, instCode, null, toSatisfaction.getCaseCode());
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
    }
    return response;
  }
  
  @RequestMapping("/doGuohuPass")
  @ResponseBody
  public AjaxResponse<String> transferPass(ToSatisfaction toSatisfaction, String taskId, String instCode){
    SessionUser user = uamSessionService.getSessionUser();
    AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      //1.更新状态
      toSatisfaction.setStatus(SatisfactionStatusEnum.GUOHU_SURVEY_PASS.getCode());
      toSatisfaction.setUpdateBy(user.getId());
      toSatisfaction.setUpdateTime(new Date());
      satisfactionService.updateSelective(toSatisfaction);
      //2.设置流程变量
      List<RestVariable> variables = new ArrayList<RestVariable>();
      variables.add(new RestVariable("guohuResult",true));
      //3.推进流程
      workFlowManager.submitTask(variables, taskId, instCode, null, toSatisfaction.getCaseCode());
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
    }
    return response;
  }
  
  @RequestMapping("/doGuohuReject")
  @ResponseBody
  public AjaxResponse<String> transferReject(ToSatisfaction toSatisfaction, String taskId, String instCode){
    SessionUser user = uamSessionService.getSessionUser();
    AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      //1.更新状态
      toSatisfaction.setStatus(SatisfactionStatusEnum.GUOHU_SURVEY_REJECT.getCode());
      toSatisfaction.setUpdateBy(user.getId());
      toSatisfaction.setUpdateTime(new Date());
      satisfactionService.updateSelective(toSatisfaction);
      //2.设置流程变量
      List<RestVariable> variables = new ArrayList<RestVariable>();
      variables.add(new RestVariable("guohuResult",false));
      //3.推进流程
      workFlowManager.submitTask(variables, taskId, instCode, null, toSatisfaction.getCaseCode());
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
    }
    return response;
  }
  
  @RequestMapping("/doGuohuFollow")
  @ResponseBody
  public AjaxResponse<String> guohuFollow(ToSatisfaction toSatisfaction, String taskId, String instCode){
    SessionUser user = uamSessionService.getSessionUser();
    AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      //1.更新状态
      toSatisfaction.setStatus(SatisfactionStatusEnum.GUOHU_SURVEY_ING.getCode());
      toSatisfaction.setUpdateBy(user.getId());
      toSatisfaction.setUpdateTime(new Date());
      satisfactionService.updateSelective(toSatisfaction);
      //2.设置流程变量
      List<RestVariable> variables = new ArrayList<RestVariable>();
      //3.推进流程
      workFlowManager.submitTask(variables, taskId, instCode, null, toSatisfaction.getCaseCode());
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
    }
    return response;
  }
  
  /**
   * 
   * initSatisList: 初始化T_CS_CASE_SATISFACTION表. <br/> 
   * 
   * @author gongjd 
   * @return 
   * @since JDK 1.8
   */
  @RequestMapping("/initSatisList")
  @ResponseBody
  public AjaxResponse<String> initSatisList(){
    AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      List<ToCase> toCases = toCaseService.findAllToCase();
      for(ToCase toCase : toCases){
        //签约
        if(CaseStatusEnum.YQY.getCode().compareTo(toCase.getStatus()) < 0 ){
          satisfactionService.handleAfterSign(toCase.getCaseCode(), "init");
        }
      }
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
    }
    return response;
  }
  
  @RequestMapping("/pushToGuohu")
  @ResponseBody
  public AjaxResponse<String> pushToGuohu(){
    AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      List<ToCase> toCases = toCaseService.findAllToCase();
      for(ToCase toCase : toCases){
        //过户(要等到分单之后并签约评分完成)
        if(CaseStatusEnum.YGH.getCode().compareTo(toCase.getStatus()) < 0 ){
          satisfactionService.handleAfterGuohuApprove(toCase.getCaseCode(), "init");
        }
      }
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
    }
    return response;
  }
  
  private void setCaseInfoToModel(Model model , Long satisId){
    
    ToSatisfaction satisfaction = satisfactionService.queryToSatisfactionById(satisId);
    
    String caseCode = satisfaction.getCaseCode();
    ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
    
    //物业信息
    ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode);
    //案件信息
    ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
    // 经纪人
    User agentUser = null;
    CaseDetailShowVO reVo = new CaseDetailShowVO();
    if (!StringUtils.isBlank(toCaseInfo.getAgentCode())) {
      agentUser = uamUserOrgService.getUserById(toCaseInfo.getAgentCode());
    }
    if (agentUser != null) {
      reVo.setAgentName(agentUser.getRealName());
      // 分行经理
      List<User> mcList = uamUserOrgService.findHistoryUserByOrgIdAndJobCode(agentUser.getOrgId(),
          TransJobs.TFHJL.getCode());
      if (mcList != null && mcList.size() > 0) {
        User mcUser = mcList.get(0);
        reVo.setMcName(mcUser.getRealName());
        reVo.setMcMobile(mcUser.getMobile());
      }
    }
    
    // 交易顾问
    User consultUser = null;
    if(null != toCase.getLeadingProcessId()){
      consultUser = uamUserOrgService.getUserById(toCase.getLeadingProcessId());
    }
    if (consultUser != null) {
      reVo.setCpName(consultUser.getRealName());
      reVo.setCpMobile(consultUser.getMobile());
    }
    
    // 上下家
    List<TgGuestInfo> guestList = tgGuestInfoService.findTgGuestInfoByCaseCode(toCase.getCaseCode());
    StringBuffer seller = new StringBuffer();
    StringBuffer sellerMobil = new StringBuffer();
    StringBuffer buyer = new StringBuffer();
    StringBuffer buyerMobil = new StringBuffer();
    for (TgGuestInfo guest : guestList) {
      if (guest.getTransPosition().equals(TransPositionEnum.TKHSJ.getCode())) {
        seller.append(guest.getGuestName());
        sellerMobil.append(guest.getGuestPhone());
        seller.append("/");
        sellerMobil.append("/");
      } else if (guest.getTransPosition().equals(TransPositionEnum.TKHXJ.getCode())) {
        buyer.append(guest.getGuestName());
        buyerMobil.append(guest.getGuestPhone());
        buyer.append("/");
        buyerMobil.append("/");
      }
    }

    if (guestList.size() > 0) {
      if (seller.length() > 1) {
        seller.deleteCharAt(seller.length() - 1);
        sellerMobil.deleteCharAt(sellerMobil.length() - 1);
      }

      if (buyer.length() > 1) {
        buyer.deleteCharAt(buyer.length() - 1);
        buyerMobil.deleteCharAt(buyerMobil.length() - 1);
      }
    }

    reVo.setSellerName(seller.toString());
    reVo.setSellerMobile(sellerMobil.toString());
    reVo.setBuyerMobile(buyerMobil.toString());
    reVo.setBuyerName(buyer.toString());
    
    // 助理
    List<User> asList = uamUserOrgService.getUserByOrgIdAndJobCode(toCase.getOrgId(), TransJobs.TJYZL.getCode());
    if (asList != null && asList.size() > 0) {
      User assistUser = asList.get(0);
      reVo.setAsName(assistUser.getRealName());
      reVo.setAsMobile(assistUser.getMobile());
    }
    
    // 合作顾问
    List<CaseDetailProcessorVO> proList = new ArrayList<CaseDetailProcessorVO>();
    TgServItemAndProcessor inProcessor = new TgServItemAndProcessor();
    inProcessor.setCaseCode(toCase.getCaseCode());
    inProcessor.setProcessorId(toCase.getLeadingProcessId());
    List<String> tgproList = tgServItemAndProcessorService.findProcessorsByCaseCode(inProcessor);
    for (String sp : tgproList) {
      if (StringUtils.isEmpty(sp) || "-1".equals(sp))
        continue;
      CaseDetailProcessorVO proVo = new CaseDetailProcessorVO();
      User processor = uamUserOrgService.getUserById(sp);
      proVo.setProcessorName(processor.getRealName());
      proVo.setProcessorMobile(processor.getMobile());
      proList.add(proVo);
    }
    reVo.setProList(proList);
    
	//查询任务ID和流程ID
    ToWorkFlow record = new ToWorkFlow();
    record.setCaseCode(caseCode);
    record.setBusinessKey(WorkFlowEnum.SATIS_DEFKEY.getCode());
    ToWorkFlow tf = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(record);
    
    TaskQuery tq = new TaskQuery();
    tq.setProcessInstanceId(tf.getInstCode());
    tq.setActive(true);
    tq.setFinished(false);
	PageableVo pageableVo = workFlowManager.listTasks(tq);
	List<TaskVo> taskList = pageableVo.getData();
    
    model.addAttribute("caseDetailVO", reVo);
    model.addAttribute("toCaseInfo", toCaseInfo);
    model.addAttribute("toPropertyInfo", toPropertyInfo);
    model.addAttribute("satisfaction", satisfaction);
    model.addAttribute("instCode", tf.getInstCode());
    model.addAttribute("taskId", taskList.get(0).getId());
  }

}
