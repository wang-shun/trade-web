package com.centaline.trans.satisfaction.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aist.uam.userorg.remote.vo.Org;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
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
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.enums.TransPositionEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.bean.TaskHistoricQuery;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.satisfaction.entity.ToSatisfaction;
import com.centaline.trans.satisfaction.service.SatisfactionService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;

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
  ToApproveRecordService toApproveRecordService;
  
  @Autowired
  UamUserOrgService uamUserOrgService;
  @Autowired
  UamSessionService uamSessionService;
  @Autowired
  UamBasedataService uamBasedataService;
  
  @Autowired
  WorkFlowManager workFlowManager;
  
  @Autowired
  ToWorkFlowService toWorkFlowService;
  
  //页面
  @RequestMapping("/list")
  public String list(Model model){
	SessionUser user = uamSessionService.getSessionUser();
	//Org kefuOrg = uamUserOrgService.getOrgByCode("033P192");
	model.addAttribute("sessionUserId", user.getId());
	model.addAttribute("serviceJobCode", user.getServiceJobCode());
	model.addAttribute("kefuOrgId", user.getServiceDepId());
    return "satis/satis_list";
  }
  
  @RequestMapping("/task/signDetail")
  public String signDetail(Model model, Long satisId, String caseCode, String urlType, Boolean readOnly){
    
    setCaseInfoToModel(model, satisId, caseCode, urlType, readOnly);
    
    return "satis/sign_detail";
  }
  
  @RequestMapping("/task/guohuDetail")
  public String guohuDetail(Model model, Long satisId, String caseCode, String urlType, Boolean readOnly){
    
    setCaseInfoToModel(model, satisId, caseCode, urlType, readOnly);
    
    return "satis/guohu_detail";
  }
  
  @RequestMapping("/task/signReturn")
  public String signReturn(Model model, Long satisId, String caseCode, String urlType, Boolean readOnly){
    
    setCaseInfoToModel(model, satisId, caseCode, urlType, readOnly);
        
    return "satis/sign_return";
  }
  
  @RequestMapping("/task/guohuReturn")
  public String guohuReturn(Model model, Long satisId, String caseCode, String urlType, Boolean readOnly){
  
    setCaseInfoToModel(model , satisId, caseCode, urlType, readOnly);
    
    return "satis/guohu_return";
  }
  
  //操作
  @RequestMapping("/doDispatch")
  @ResponseBody
  public AjaxResponse<String> dispatch(String caseCodes, String userId){
	AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      satisfactionService.dispatch(caseCodes, userId);
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败!"+e.getMessage());
      e.printStackTrace();
    }
    return response;
  }
  
  @RequestMapping("/doSignPass")
  @ResponseBody
  public AjaxResponse<String> signPass(ToSatisfaction toSatisfaction, String taskId, String instCode){
    AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      satisfactionService.signPass(toSatisfaction, taskId, instCode);
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
      e.printStackTrace();
    }
    return response;
  }
  
  @RequestMapping("/doSignReject")
  @ResponseBody
  public AjaxResponse<String> signReject(ToSatisfaction toSatisfaction, String taskId, String instCode){
    AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      satisfactionService.signReject(toSatisfaction, taskId, instCode);
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
      e.printStackTrace();
    }
    return response;
  }
  
  @RequestMapping("/doSignFollow")
  @ResponseBody
  public AjaxResponse<String> signFollow(ToSatisfaction toSatisfaction, String taskId, String instCode){
    AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      satisfactionService.signFollow(toSatisfaction, taskId, instCode);
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
      e.printStackTrace();
    }
    return response;
  }
  
  @RequestMapping("/doGuohuPass")
  @ResponseBody
  public AjaxResponse<String> guohuPass(ToSatisfaction toSatisfaction, String taskId, String instCode){
    AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      satisfactionService.guohuPass(toSatisfaction, taskId, instCode);
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
      e.printStackTrace();
    }
    return response;
  }
  
  @RequestMapping("/doGuohuReject")
  @ResponseBody
  public AjaxResponse<String> guohuReject(ToSatisfaction toSatisfaction, String taskId, String instCode){
    AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      satisfactionService.guohuReject(toSatisfaction, taskId, instCode);
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
      e.printStackTrace();
    }
    return response;
  }
  
  @RequestMapping("/doGuohuFollow")
  @ResponseBody
  public AjaxResponse<String> guohuFollow(ToSatisfaction toSatisfaction, String taskId, String instCode){
    AjaxResponse<String> response = new AjaxResponse<String>();
    try{
      satisfactionService.guohuFollow(toSatisfaction, taskId, instCode);
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
      e.printStackTrace();
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
      satisfactionService.initSatisList();
      response.setSuccess(true);
      response.setMessage("操作成功！");
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage("操作失败！"+e.getMessage());
      e.printStackTrace();
    }
    return response;
  }
  
  private void setCaseInfoToModel(Model model , Long satisId , String caseCode , String urlType, Boolean readOnly){
    
	ToSatisfaction satisfaction = null;  
	  
	if(satisId == null){
		satisfaction = satisfactionService.queryToSatisfactionByCaseCode(caseCode);
		satisId = satisfaction.getPkid();
	}else{
		satisfaction = satisfactionService.queryToSatisfactionById(satisId);
		caseCode = satisfaction.getCaseCode();
	}


    ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
    
    //爆单和无效原因
    String toApproveRecord = "";
	ToApproveRecord toApproveRecordForItem=new ToApproveRecord();	
      if(toCase.getCaseProperty().equals("30003001") || toCase.getCaseProperty().equals("30003005") ){
      	ToWorkFlow workFlow = new ToWorkFlow();
      	workFlow.setCaseCode(toCase.getCaseCode());
      	workFlow.setBusinessKey("operation_process");
      	workFlow.setStatus("4");
      	ToWorkFlow toWorkFlow1= toWorkFlowService.queryToWorkFlowByCaseCodeAndBusinessKey(workFlow);				
  		if(toWorkFlow1!=null){
      	toApproveRecordForItem.setProcessInstance(toWorkFlow1.getInstCode());
  		toApproveRecordForItem.setCaseCode(toCase.getCaseCode());
		ToApproveRecord toApproveRecord2 = toApproveRecordService.queryToApproveRecordForSpvApply(toApproveRecordForItem);
			if(toApproveRecord2 != null){
				toApproveRecord = toApproveRecord2.getContent();
			}else{
				toApproveRecord = "";
			}
      }else{
    	  toApproveRecord = "";
      }
     }else{
    	  toApproveRecord = "";
     }
    
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
      reVo.setAgentMobile(agentUser.getMobile());
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
    record.setBizCode(satisId.toString());
    record.setBusinessKey(WorkFlowEnum.SATIS_DEFKEY.getCode());
    ToWorkFlow tf = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(record);
    
    TaskQuery tq = new TaskQuery();
    tq.setProcessInstanceId(tf.getInstCode());
    tq.setActive(true);
    tq.setFinished(false);
	PageableVo pageableVo = workFlowManager.listTasks(tq);
	List<TaskVo> taskList = pageableVo.getData();
	
	//for：我要修改
	// 工作流
	ToWorkFlow inWorkFlow = new ToWorkFlow();
	inWorkFlow.setBusinessKey("operation_process");
	inWorkFlow.setCaseCode(toCase.getCaseCode());
	ToWorkFlow toWorkFlow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(inWorkFlow);

	List<TaskVo> tasks = new ArrayList<TaskVo>();
	if (toWorkFlow != null) {
	    //查询caseCode所有的流程instCode
	    List<String> insCodeList = toWorkFlowService.queryAllInstCodesByCaseCode(toCase.getCaseCode());
	    for(String insCode : insCodeList) {
	        TaskHistoricQuery tq2 = new TaskHistoricQuery();             
	        tq2.setProcessInstanceId(insCode);
	        tq2.setFinished(true);

	        List<TaskVo> taskList1 = taskDuplicateRemoval(workFlowManager.listHistTasks(tq2).getData());     
	        tasks.addAll(taskList1);
	    }
	    // 本人做的任务
	    List<TgServItemAndProcessor> myServiceCase = tgServItemAndProcessorService.findTgServItemAndProcessorByCaseCode(toCase.getCaseCode());
	    
	    model.addAttribute("myTasks",filterMyTask(myServiceCase,tasks)) ;
	}
    
	model.addAttribute("toApproveRecord", toApproveRecord);
	model.addAttribute("toCase", toCase);
    model.addAttribute("caseDetailVO", reVo);
    model.addAttribute("toCaseInfo", toCaseInfo);
    model.addAttribute("toPropertyInfo", toPropertyInfo);
    model.addAttribute("satisfaction", satisfaction);
    model.addAttribute("instCode", tf.getInstCode());
    model.addAttribute("taskId", CollectionUtils.isEmpty(taskList)?null:taskList.get(0).getId());
    model.addAttribute("urlType", urlType);
    model.addAttribute("readOnly", readOnly);
  }
  
	private List<TaskVo>filterMyTask(List<TgServItemAndProcessor>mySerivceItems,List<TaskVo>tasks){
		if (tasks == null || mySerivceItems == null || tasks.isEmpty() || mySerivceItems.isEmpty()) {
			return tasks;
		}
		
		Set<String>taskDfKeys=new HashSet<>();
		
		mySerivceItems.parallelStream().forEach(item->{
			Dict d =uamBasedataService.findDictByType(item.getSrvCode());
			if(d!=null&&d.getChildren()!=null){
				d.getChildren().parallelStream().forEach(sc->{
					if(!taskDfKeys.contains(sc.getCode())){
						taskDfKeys.add(sc.getCode());
					}
				});
			}
		});
		
		Iterator<TaskVo> it = tasks.iterator();
		while (it.hasNext()) {
			TaskVo task=it.next();
			if(!taskDfKeys.contains(task.getTaskDefinitionKey())){
				it.remove();
			}
		}
		return tasks;
	}
	private List<TaskVo> taskDuplicateRemoval(List<TaskVo> oList) {
		Map<String, TaskVo> hashMap = new HashMap<>();
	
		for (TaskVo taskVo : oList) {
			hashMap.put(taskVo.getTaskDefinitionKey(), taskVo);
		}
		List<TaskVo> result = new ArrayList<>(hashMap.values());
		return result;
	}

}
