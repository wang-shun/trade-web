/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.spv.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.ToAccesoryList;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.common.enums.SpvStatusEnum;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mgr.Consts;
import com.centaline.trans.product.entity.Product;
import com.centaline.trans.product.entity.ProductCategory;
import com.centaline.trans.product.service.ProductCategoryService;
import com.centaline.trans.product.service.ProductService;
import com.centaline.trans.spv.entity.ToCashFlow;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvDeCond;
import com.centaline.trans.spv.entity.ToSpvDeRec;
import com.centaline.trans.spv.service.CashFlowOutService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;
import com.centaline.trans.spv.vo.SpvDeRecVo;
import com.centaline.trans.spv.vo.SpvVo;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.vo.ProcessInstanceVO;


@Controller
@RequestMapping(value="/spv")
public class SpvController {
	
	@Autowired
	private ToSpvService toSpvService;	
	@Autowired
	private ToApproveRecordService toApproveRecordService;	
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private ToCaseService toCaseService;	 
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private CashFlowOutService cashFlowOutService;
	
	@Autowired
	MessageService messageService;
	@Autowired
	ProductCategoryService productCategoryService;
	@Autowired
	ProductService productService;
	@Autowired
	ToWorkFlowService flowService;
	@Autowired
	ProcessInstanceService processInstanceService;
	@Autowired
	private UamPermissionService uamPermissionService;

	
	//列表页面
	@RequestMapping("spvList")
	public String spvList(HttpServletRequest request){
		SessionUser currentUser = uamSessionService.getSessionUser();
		String currentDeptId = currentUser.getServiceDepId();
		Org curentOrg = uamUserOrgService.getOrgById(currentDeptId);
		Org parentOrg = uamUserOrgService.getOrgById(curentOrg.getParentId());
	
		request.setAttribute("orgId", parentOrg.getId());
		return "spv/SpvList";
	}
	
	//新增页面
	@RequestMapping("saveHTML")
	public String saveHTML(Long pkid,String caseCode,HttpServletRequest request){
		SessionUser currentUser = uamSessionService.getSessionUser();
		String currentDeptId = currentUser.getServiceDepId();
		Org curentOrg = uamUserOrgService.getOrgById(currentDeptId);
		Org parentOrg = uamUserOrgService.getOrgById(curentOrg.getParentId());

		toSpvService.findSpvBaseInfoVOAndSetAttr(request,pkid,caseCode);
		
		toAccesoryListService.getAccesoryList(request, "SpvApplyApprove");
	    App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
	    request.setAttribute("imgweb", app.genAbsoluteUrl());
		
		request.setAttribute("orgId", parentOrg.getId());
		request.setAttribute("urlType", "spv");
		return "spv/saveSpvCase";
	}

	/**
	 * 详情
	 * @param pkid
	 * @param model
	 * @return
	 */
	@RequestMapping("spvDetail")
	public String SpvDetail(long pkid ,ServletRequest request){
/*		SpvBaseInfoVO baseInfoVO=new SpvBaseInfoVO();
		//合约基本信息

		baseInfoVO.setToSpv(spv);
		//买卖双方信息
		List<ToSpvCust> custs=toSpvService.findCustBySpvCode(spv.getSpvCode());
		baseInfoVO.setSpvCustList(custs);
		//房屋信息
		ToSpvProperty spvProperty=toSpvService.findPropertyBySpvCode(spv.getSpvCode());
		baseInfoVO.setToSpvProperty(spvProperty);
		//资金账户
		List<ToSpvAccount> accounts=toSpvService.findAccountBySpvCode(spv.getSpvCode());
		baseInfoVO.setToSpvAccountList(accounts);
		*/
		ToSpv spv= toSpvService.selectByPrimaryKey(pkid);
		SpvBaseInfoVO spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(pkid);
		User user=uamUserOrgService.getUserById(spvBaseInfoVO.getToSpv().getCreateBy());
		String name=user.getRealName();
		String phone=user.getMobile();
		spvBaseInfoVO.getToSpv().setCreateBy(name);
		//经办人
		ToCase toCase= toCaseService.findToCaseByCaseCode(spv.getCaseCode());
		//申请人
		User applyUser =uamUserOrgService.getUserById(spv.getApplyUser());
		//人物信息
		User jingban =uamUserOrgService.getUserById(toCase.getLeadingProcessId());
		//风控总监
		List<User> zj =uamUserOrgService.getUserByOrgIdAndJobCode(user.getOrgId(), "JYFKZJ");
		User FKZJ=new User();
		if(zj.size()>0){
			FKZJ=zj.get(0);
		}
		//驳回原因
    if(spv.getStatus()!="0"&&spv.getApplyTime()!=null){
		ToWorkFlow record=new ToWorkFlow();
		record.setBusinessKey(WorkFlowEnum.SPV_BUSSKEY.getCode());
		record.setCaseCode(spv.getCaseCode());
	    ToWorkFlow workFlow= flowService.queryActiveToWorkFlowByCaseCodeBusKey(record);
		 
		//查询审核结果
		ToApproveRecord toApproveRecordForItem=new ToApproveRecord();
		if(spvBaseInfoVO.getToSpv() != null && spvBaseInfoVO.getToSpv().getCaseCode() != null){
			toApproveRecordForItem.setCaseCode(spvBaseInfoVO.getToSpv().getCaseCode());
		}		
		toApproveRecordForItem.setProcessInstance(workFlow.getInstCode());
		toApproveRecordForItem.setPartCode("SpvApplyApprove");		
		ToApproveRecord toApproveRecord=toApproveRecordService.queryToApproveRecordForSpvApply(toApproveRecordForItem);		
		request.setAttribute("toApproveRecord", toApproveRecord);
      }
		request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
		request.setAttribute("createPhone", phone);
		request.setAttribute("jingban", jingban.getRealName());
	    request.setAttribute("zj",FKZJ);
	    request.setAttribute("applyUser",applyUser);
		return "spv/SpvDetail";
	}
	/**
	 * 删除
	 * @param pkid
	 * @param model
	 * @return
	 */
	@RequestMapping("deleteSpv")
	public AjaxResponse<String>  deleteSpv(long pkid ,ServletRequest request){
		SpvBaseInfoVO spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(pkid);
		spvBaseInfoVO.getToSpv().setIsDeleted("1");
		spvBaseInfoVO.getToSpvDe().setIsDeleted("1");
		spvBaseInfoVO.getToSpvProperty().setIsDeleted("1");
        for (int i=0;i<spvBaseInfoVO.getSpvCustList().size();i++) {
        	if(spvBaseInfoVO.getSpvCustList().get(i)!=null){
        		spvBaseInfoVO.getSpvCustList().get(i).setIsDeleted("1");
        	}
		}
        for (int i=0;i<spvBaseInfoVO.getToSpvAccountList().size();i++) {
        	spvBaseInfoVO.getToSpvAccountList().get(i).setIsDeleted("1");
		}
        for (int i=0;i<spvBaseInfoVO.getToSpvDeDetailList().size();i++) {
        	spvBaseInfoVO.getToSpvDeDetailList().get(i).setIsDeleted("1");
		}
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		//保存相关信息
    		SessionUser user= uamSessionService.getSessionUser();
    		toSpvService.saveNewSpv(spvBaseInfoVO,user);
    		response.setSuccess(true);
    		response.setMessage("监管合约删除成功！");
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    	}
    	return response;
	}
	
	@RequestMapping("spvOutApply/process")
	public String toSpvOutApplyProcess(HttpServletRequest request,
			HttpServletResponse response,String caseCode,String source,String instCode,String taskitem){

		request.setAttribute("source", source);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("caseBaseVO", caseBaseVO);
		
		toAccesoryListService.getAccesoryList(request, taskitem);
    	ToSpv toSpv = toSpvService.queryToSpvByCaseCode(caseCode);
		request.setAttribute("toSpv", toSpv);
		SpvDeRecVo spvDeRecVo = toSpvService.findByProcessInstanceId(instCode);
		request.setAttribute("spvDeRecVo", spvDeRecVo);
		return "task/taskSpvOutApply";
	}
	@RequestMapping("spvOutApprove/process")
	public String toSpvOutApproveProcess(HttpServletRequest request,
			HttpServletResponse response,String caseCode,String source,String instCode,String taskitem){
		SessionUser user= uamSessionService.getSessionUser();
		request.setAttribute("source", source);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("caseBaseVO", caseBaseVO);
		
		toAccesoryListService.getAccesoryList(request, taskitem);
		
		request.setAttribute("approveType", "6");
		request.setAttribute("operator", user != null ? user.getId():"");
		
		SpvDeRecVo spvDeRecVo = toSpvService.findByProcessInstanceId(instCode);
		
		request.setAttribute("spvDeRecVo", spvDeRecVo);
		return "task/taskSpvOutApprove";
	}
	
	/**
	 * 转到资金监管签约保存界面
	 * @param caseCode
	 * @param model
	 * @return
	 */
    @RequestMapping(value="/toSaveSpv")
    public String toSaveSpv(String caseCode,Model model){
    
    	SpvVo toSpvVo = toSpvService.findByCaseCode(caseCode);
    	model.addAttribute("toSpvVo",toSpvVo);
    	model.addAttribute("caseCode", caseCode);
    	model.addAttribute("taskitem", Consts.PART_CODE_SPV_SIGN_APPLY);
    	//需上传备件
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode(Consts.PART_CODE_SPV_SIGN_APPLY);
		List<ToAccesoryList> list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		if(list != null && list.size() > 0) {
			int size = list.size();
			model.addAttribute("accesoryList", list);
			List<Long> idList = new ArrayList<Long>(size);
			for(int i=0; i<size; i++) {
				idList.add(list.get(i).getPkid());
			}
			model.addAttribute("idList", idList);
		}
    	return "spv/saveSpv";
    }
	
    /**
     * 保存资金监管签约
     * @param toSpv
     * @param toSpvDeCondList
     * @param delIds
     * @return
     */
    @RequestMapping(value="saveSpv")
    @ResponseBody
    public AjaxResponse<String> saveSpv(ToSpv toSpv,String toSpvDeCondList,String[] delIds,String[] delFlowIds,String toCashFlow,String toCashFlowList){
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		SpvVo spvVo = new SpvVo();
    		spvVo.setToSpv(toSpv);
    		spvVo.setToSpvDeCondList(JSONObject.parseArray(toSpvDeCondList, ToSpvDeCond.class));
    		spvVo.setToCashFlow(JSONObject.parseObject(toCashFlow, ToCashFlow.class));
    		spvVo.setToCashFlowList(JSONObject.parseArray(toCashFlowList, ToCashFlow.class));
    		spvVo.setDelIds(delIds);
    		spvVo.setDelFlowIds(delFlowIds);
    		ToCase toCase = toCaseService.findToCaseByCaseCode(toSpv.getCaseCode());
    		toSpvService.saveToSpv(spvVo);
    		response.setContent(String.valueOf(toCase.getPkid()));
    		response.setMessage("保存房款监管签约成功！");
    	}catch(Exception e){
    		response.setMessage(e.getMessage());
    		
    	}
    	return response;
    }
    
    /**
     * 保存资金监管签约
     */
    @RequestMapping(value="saveNewSpv")
    @ResponseBody
    public AjaxResponse<String> saveNewSpv(SpvBaseInfoVO spvBaseInfoVO){   	
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		//保存相关信息
    		SessionUser user= uamSessionService.getSessionUser();
    		toSpvService.saveNewSpv(spvBaseInfoVO,user);
    		response.setSuccess(true);
    		response.setMessage("保存房款监管签约成功！");
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    	}
    	return response;
    }
    
    /**
     * 提交资金监管签约
     */
    @RequestMapping(value="submitNewSpv")
    @ResponseBody
    public AjaxResponse<String> submitNewSpv(SpvBaseInfoVO spvBaseInfoVO){
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		//保存相关信息
    		SessionUser user= uamSessionService.getSessionUser();
    		toSpvService.submitNewSpv(spvBaseInfoVO,user);
    		response.setSuccess(true);
    		response.setMessage("开启资金监管流程成功！");
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    	}
    	return response;
    }
    
    /**
     * 转向资金监管解除记录保存界面
     * @param caseCode
     * @param model
     * @return
     */
    @RequestMapping(value="/toSpvDeRec")
    public String toSpvDeRec(String caseCode,Model model){
    
    	ToSpv toSpv = toSpvService.queryToSpvByCaseCode(caseCode);
 //   	SpvVo toSpvVo = toSpvService.findByProcessInstanceId(processInstanceId);
    	model.addAttribute("toSpv",toSpv);
    	model.addAttribute("caseCode", caseCode);
    	model.addAttribute("taskitem", Consts.PART_CODE_SPV_OUT_APPLY);
    	//需上传备件
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode(Consts.PART_CODE_SPV_OUT_APPLY);
		List<ToAccesoryList> list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		if(list != null && list.size() > 0) {
			int size = list.size();
			model.addAttribute("accesoryList", list);
			List<Long> idList = new ArrayList<Long>(size);
			for(int i=0; i<size; i++) {
				idList.add(list.get(i).getPkid());
			}
			model.addAttribute("idList", idList);
		}
    	return "spv/spvDeRec";
    }
    
    /**
     * 查询资金监管解除条件
     * @param spvCode
     * @return
     */
    @RequestMapping(value="querySpvDeCond")
    @ResponseBody
    public List<ToSpvDeCond> querySpvDeCond(String spvCode){
    	
    	List<ToSpvDeCond> list = toSpvService.findToSpvCondBySpvCode(spvCode);
    	return list;
    }
    
    /**
     * 启动资金监管解除流程
     * @param toSpvDeRec
     * @return
     */
    @RequestMapping(value="startSpvOutApplyProcess")
    @ResponseBody
    public AjaxResponse<ProcessInstanceVO> startSpvOutApplyProcess(ProcessInstanceVO processInstanceVO){
    	AjaxResponse<ProcessInstanceVO> response = new AjaxResponse<ProcessInstanceVO>();
    	try{
    		SpvVo spvVo = toSpvService.findByCaseCode(processInstanceVO.getCaseCode());
    		if(spvVo == null){
    			response.setSuccess(false);
    			response.setMessage("您还未添加房款监管信息，不能解除监管！");
    			return response;
    		}
    		if(CollectionUtils.isEmpty(spvVo.getToSpvDeCondList())){
    			response.setSuccess(false);
    			response.setMessage("您还未添加房款监管解除条件，不能解除监管！");
    			return response;
    		}
    		ProcessInstanceVO vo = toSpvService.startSpvOutApplyProcess(processInstanceVO);
    		response.setContent(vo);
    	}catch(Exception e){
    		response.setMessage(e.getMessage());
    		
    	}
    	return response;
    }
    
    /**
     * 保存资金监管解除记录
     * @param toSpvDeRec
     * @return
     */
    @RequestMapping(value="saveSpvDeRec")
    @ResponseBody
    public AjaxResponse<String> saveSpvDeRec(ToSpvDeRec toSpvDeRec,ProcessInstanceVO processInstanceVO){
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		toSpvService.saveToSpvDeRec(toSpvDeRec,processInstanceVO.getProcessInstanceId());
    	}catch(Exception e){
    		response.setMessage(e.getMessage());
    		
    	}
    	return response;
    }
    
    /**
     * 提交资金监管申请
     * @param toSpvDeRec
     * @param processInstanceVO
     * @return
     */
    @RequestMapping(value="submitSpvDeRec")
    @ResponseBody
    public AjaxResponse<String> submitSpvDeRec(ToSpvDeRec toSpvDeRec,ProcessInstanceVO processInstanceVO){
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		toSpvService.submitToSpvDeRec(toSpvDeRec,processInstanceVO);
    	}catch(Exception e){
    		response.setMessage(e.getMessage());
    		
    	}
    	return response;
    }
    
    /**
     * 查询资金监管记录
     * @param toSpvDeRec
     * @return
     */
    @RequestMapping(value="getSpvDeRec")
    @ResponseBody
    public AjaxResponse<ToSpvDeRec> getSpvDeRec(ToSpvDeRec toSpvDeRec){
    	AjaxResponse<ToSpvDeRec> response = new AjaxResponse<ToSpvDeRec>();
    	try{
    		ToSpvDeRec spvDeRec = toSpvService.findSpvDeRecBySpvCodeAndCondId(toSpvDeRec);
    		response.setContent(spvDeRec);
    	}catch(Exception e){
    		response.setMessage("查询房款监管解除记录失败！");
    		
    	}
    	return response;
    }
    
    /**
     * 资金监管申请页面
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param instCode
     * @param taskitem
     * @return
     */
    @RequestMapping("task/SpvApply/process")
	public String toSpvApplyProcess(HttpServletRequest request,Long pkid,String source,String instCode,String taskId){
    	
    	
        SpvBaseInfoVO spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByInstCode(instCode);	
		//查询审核结果
		ToApproveRecord toApproveRecordForItem=new ToApproveRecord();
		if(spvBaseInfoVO.getToSpv() != null && spvBaseInfoVO.getToSpv().getCaseCode() != null){
			toApproveRecordForItem.setCaseCode(spvBaseInfoVO.getToSpv().getCaseCode());
		}		
		toApproveRecordForItem.setProcessInstance(instCode);
		toApproveRecordForItem.setPartCode("SpvApplyApprove");		
		ToApproveRecord toApproveRecord=toApproveRecordService.queryToApproveRecordForSpvApply(toApproveRecordForItem);		
		request.setAttribute("toApproveRecord", toApproveRecord);
		
		request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
		
		toAccesoryListService.getAccesoryList(request, "SpvApplyApprove");
	    App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
	    request.setAttribute("imgweb", app.genAbsoluteUrl());	    
		
		SessionUser currentUser = uamSessionService.getSessionUser();
		String currentDeptId = currentUser.getServiceDepId();
		Org curentOrg = uamUserOrgService.getOrgById(currentDeptId);
		Org parentOrg = uamUserOrgService.getOrgById(curentOrg.getParentId());
		
		request.setAttribute("orgId", parentOrg.getId());
    	request.setAttribute("taskId", taskId); 
    	request.setAttribute("instCode", instCode);
		request.setAttribute("pkid", pkid);
		request.setAttribute("source", source);
		request.setAttribute("handle", "SpvApply");
		request.setAttribute("urlType", "myTask");
		if(spvBaseInfoVO.getToSpv() != null && spvBaseInfoVO.getToSpv().getCaseCode() != null){
			request.setAttribute("caseCode", spvBaseInfoVO.getToSpv().getCaseCode());
		}
		return "spv/saveSpvCase";
	}
    
    /**
     * 资金监管申请操作
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param instCode
     * @param taskitem
     * @return
     */
    @RequestMapping("spvApply/deal")
	public AjaxResponse<?> spvApply(String caseCode,String source,String instCode,String taskId){

		List<RestVariable> variables = new ArrayList<RestVariable>();

		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);	
		workFlowManager.submitTask(variables, taskId, instCode, null, toCase.getCaseCode());
		
		ToSpv spv = toSpvService.queryToSpvByCaseCode(caseCode);
		spv.setStatus(SpvStatusEnum.INPROGRESS.getCode());

		//spv.setRemark(remark);
		toSpvService.updateByPrimaryKey(spv);
		
		return AjaxResponse.success();
	}
    
    /**
     * 资金监管审批页面
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param instCode
     * @param taskitem
     * @return
     */
	@RequestMapping("task/SpvApprove/process")
	public String toSpvApproveProcess(HttpServletRequest request,String source,String instCode,String taskId){	
		
		SpvBaseInfoVO spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByInstCode(instCode);
		
		//查询审核结果
		ToApproveRecord toApproveRecordForItem=new ToApproveRecord();
		if(spvBaseInfoVO.getToSpv() != null && spvBaseInfoVO.getToSpv().getCaseCode() != null){
			toApproveRecordForItem.setCaseCode(spvBaseInfoVO.getToSpv().getCaseCode());
		}		
		toApproveRecordForItem.setProcessInstance(instCode);
		toApproveRecordForItem.setPartCode("SpvApplyApprove");		
		ToApproveRecord toApproveRecord=toApproveRecordService.queryToApproveRecordForSpvApply(toApproveRecordForItem);		
		request.setAttribute("toApproveRecord", toApproveRecord);
		
		toAccesoryListService.getAccesoryList(request, "SpvApplyApprove");
	    App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
	    request.setAttribute("imgweb", app.genAbsoluteUrl());
		
		request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);		
		request.setAttribute("taskId", taskId);
    	request.setAttribute("instCode", instCode);
		request.setAttribute("source", source);
		request.setAttribute("handle", "SpvApprove");
		request.setAttribute("urlType", "myTask");
		if(spvBaseInfoVO.getToSpv() != null && spvBaseInfoVO.getToSpv().getCaseCode() != null){
			request.setAttribute("caseCode", spvBaseInfoVO.getToSpv().getCaseCode());
		}
		
		return "spv/saveSpvCase";
	}

	/**
     * 资金监管审批操作
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param instCode
     * @param taskitem
     * @return
     */
	@RequestMapping("spvApprove/deal")
	public AjaxResponse<?> spvApprove(Boolean SpvApplyApprove,String caseCode,String instCode,String taskId,String remark){
		
		SessionUser user = uamSessionService.getSessionUser();
		
		List<RestVariable> variables = new ArrayList<RestVariable>();
		variables.add(new RestVariable("SpvApplyApprove",SpvApplyApprove));
		workFlowManager.submitTask(variables, taskId, instCode, null, caseCode);
		
		ToSpv spv = toSpvService.queryToSpvByCaseCode(caseCode);
		if(!SpvApplyApprove){
			spv.setStatus(SpvStatusEnum.DEFAULT.getCode());
		}else{
			spv.setStatus(SpvStatusEnum.INPROGRESS.getCode());
		}
		//spv.setRemark(remark);
		toSpvService.updateByPrimaryKey(spv);
		
		//条件审核记录到ToApproveRecord
		ToApproveRecord toApproveRecord=new ToApproveRecord();
		toApproveRecord.setCaseCode(caseCode);
		toApproveRecord.setContent(remark);
		toApproveRecord.setApproveType("8");//todo
		toApproveRecord.setOperator(user.getId());
		toApproveRecord.setTaskId(taskId);
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setPartCode("SpvApplyApprove");//todo
		toApproveRecord.setProcessInstance(instCode);		
		
		toApproveRecordService.insertToApproveRecord(toApproveRecord);

		return AjaxResponse.success();
	}
	
    /**
     * 资金监管签约页面
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param instCode
     * @param taskitem
     * @return
     */
	@RequestMapping("task/SpvSign/process")
	public String toSpvSignProcess(HttpServletRequest request,String caseCode,String source,String instCode,String taskId){
		
		SpvBaseInfoVO spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByInstCode(instCode);	
		request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
		
		toAccesoryListService.getAccesoryList(request, "SpvApplyApprove");
	    App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
	    request.setAttribute("imgweb", app.genAbsoluteUrl());
		
		request.setAttribute("taskId", taskId);
    	request.setAttribute("instCode", instCode);
		request.setAttribute("source", source);
		request.setAttribute("handle", "SpvSign");
		request.setAttribute("urlType", "myTask");
		if(spvBaseInfoVO.getToSpv() != null && spvBaseInfoVO.getToSpv().getCaseCode() != null){
			request.setAttribute("caseCode", spvBaseInfoVO.getToSpv().getCaseCode());
		}
		return "spv/saveSpvCase";
	}
	
	/**
     * 资金监管签约操作
     * @param request
     * @param response
     * @param caseCode
     * @param source
     * @param instCode
     * @param taskitem
     * @return
     */
	@RequestMapping("spvSign/deal")
	public AjaxResponse<?> spvSign(Boolean SpvApplyApprove,String caseCode,String instCode,String taskId){

		List<RestVariable> variables = new ArrayList<RestVariable>();
		workFlowManager.submitTask(variables, taskId, instCode, null, caseCode);
		
		ToSpv spv = toSpvService.queryToSpvByCaseCode(caseCode);
		spv.setStatus("2");
		toSpvService.updateByPrimaryKey(spv);
		// (签约)发送消息
		//messageService.sendSpvFinishMsgByIntermi(instCode);

		return AjaxResponse.success();
	}
	
	@RequestMapping("queryByCaseCode")
	public AjaxResponse<String> queryByCaseCode(String caseCode){
        
        AjaxResponse<String> response = new AjaxResponse<String>();
    	try{
    		ToSpv toSpv = toSpvService.queryToSpvByCaseCode(caseCode);
    		if(toSpv != null){
    			response.setSuccess(true);
    			response.setMessage("该案件已经关联合约，不得重复关联！");
    			response.setContent("1");
    		} else{
    			response.setSuccess(true);
    		}
    	}catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());	
    	}
    	return response;
	}
	
	
	/**
	 * 查询所有的产品分类
	 */
	@RequestMapping("queryPrdCategorys")
	@ResponseBody
	public List<ProductCategory> queryPrdCategorys() {

		List<ProductCategory> prdList = productCategoryService.getAllProductCategoryList();
		
		return prdList;
	}
	
	/**
	 * 查询分类下所有的产品
	 */
	@RequestMapping("queryProdByPrdcCode")
	@ResponseBody
	public List<Product> queryPrdByCateCode(String prdcCode) {

		List<Product> prdList = productService.getProductListByProductCategoryCode(prdcCode);
		
		return prdList;
	}
	
	/**
	 * 查询产品所属分类
	 */
	@RequestMapping("queryPrdcCodeByProdCode")
	@ResponseBody
	public Product queryPrdcCodeByProdCode(String prodCode) {
		
		Product product = productService.getProductByCode(prodCode);
		
		return  product;
	}
	
    /**
     * @throws Exception  
     * @Title: cashFlowOutApprProcess 
     * @Description: 出款申请页面
     * @author: gongjd 
     * @param request
     * @param source
     * @param instCode
     * @param taskId
     * @param handle
     * @param businessKey
     * @return String 
     * @throws
     */
    @RequestMapping("task/cashFlowOutAppr/process")
	public String cashFlowOutAppprProcess(HttpServletRequest request,String source,String instCode,
			String taskId,String handle,String businessKey)  {
    	
    	if(!StringUtils.isBlank(handle)){ 	
        	switch (handle) {
        	case "apply":
        		cashFlowOutService.cashFlowOutApplyProcess(request, source, instCode, taskId, handle, businessKey);
        		break;
            case "directorAduit":
            	cashFlowOutService.cashFlowOutDirectorAduitProcess(request, source, instCode, taskId, handle, businessKey);
        		break;
            case "financeAduit":
            	cashFlowOutService.cashFlowOutFinanceAduitProcess(request, source, instCode, taskId, handle, businessKey);
            	break;
            case "financeSecondAduit":
            	cashFlowOutService.cashFlowOutFinanceSecondAduitProcess(request, source, instCode, taskId, handle, businessKey);
                break;
            case "cashFlowOut":
            	cashFlowOutService.cashFlowOutFinanceSecondAduitProcess(request, source, instCode, taskId, handle, businessKey);
                break;
        	}
    		request.setAttribute("urlType", "myTask");
        }else{
        	cashFlowOutService.cashFlowOutPage(request, source, instCode, taskId, handle, businessKey);
        	request.setAttribute("urlType", "spvApply");
        }

	    App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
	    request.setAttribute("imgweb", app.genAbsoluteUrl());
	    
    	request.setAttribute("taskId", taskId); 
    	request.setAttribute("instCode", instCode);
		request.setAttribute("source", source);
		request.setAttribute("handle", handle);
		
		return "spv/caseFlowOutApply";
	}
    
    /**
     * @Title: cashFlowOutApprDeal 
     * @Description: 出款申请操作
     * @author: gongjd 
     * @param request
     * @param source
     * @param instCode
     * @param taskId
     * @param handle
     * @param spvChargeInfoVO
     * @return AjaxResponse<?>
     * @throws
     */
    @RequestMapping("cashFlowOutAppr/deal")
	public AjaxResponse<?> cashFlowOutApprDeal(HttpServletRequest request,String source,String instCode,
			String taskId,String handle,SpvChargeInfoVO spvChargeInfoVO,Boolean chargeOutAppr) {
    	AjaxResponse<?> response = new AjaxResponse<>();
    	try {
			String cashflowApplyCode = spvChargeInfoVO.getToSpvCashFlowApply().getCashflowApplyCode();
			
			if(!StringUtils.isBlank(handle)){ 
				
				if(StringUtils.isBlank(cashflowApplyCode)) throw new BusinessException("页面没有传入申请号！");
				
				switch (handle) {
				case "apply":
					cashFlowOutService.cashFlowOutApplyDeal(request, instCode, taskId, handle, spvChargeInfoVO, cashflowApplyCode);
					break;
			    case "directorAduit":
			    	cashFlowOutService.cashFlowOutDirectorAduitDeal(request, instCode, taskId, handle, spvChargeInfoVO, cashflowApplyCode,chargeOutAppr);
					break;
			    case "financeAduit":
			    	cashFlowOutService.cashFlowOutFinanceAduitDeal(request, instCode, taskId, handle, spvChargeInfoVO, cashflowApplyCode,chargeOutAppr);
			    	break;
			    case "financeSecondAduit":
			    	cashFlowOutService.cashFlowOutFinanceSecondAduitDeal(request, instCode, taskId, handle, spvChargeInfoVO, cashflowApplyCode,chargeOutAppr);
			        break;
				}	
			}else{
				cashFlowOutService.cashFlowOutPageDeal(request, instCode, taskId, handle, spvChargeInfoVO, cashflowApplyCode);
			}

			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage());
		}
    	
    	return response;
	}

}



