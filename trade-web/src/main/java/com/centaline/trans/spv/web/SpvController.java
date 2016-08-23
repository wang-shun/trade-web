/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.spv.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.ToAccesoryList;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mgr.Consts;
import com.centaline.trans.spv.entity.ToCashFlow;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvCust;
import com.centaline.trans.spv.entity.ToSpvDeCond;
import com.centaline.trans.spv.entity.ToSpvDeRec;
import com.centaline.trans.spv.entity.ToSpvProperty;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvDeRecVo;
import com.centaline.trans.spv.vo.SpvVo;
import com.centaline.trans.task.vo.ProcessInstanceVO;


@Controller
@RequestMapping(value="/spv")
public class SpvController {
	
	@Autowired
	private ToSpvService toSpvService;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private ToCaseService toCaseService;
	 
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private WorkFlowManager workFlowManager;
	
	//列表页面
	@RequestMapping("spvList")
	public String spvList(){
		return "spv/SpvList";
	}
	
	//新增页面
	@RequestMapping("saveHTML")
	public String savaHTML(String caseCode,ServletRequest request){
		SpvBaseInfoVO spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByCaseCode(caseCode);
		
		request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);

		return "spv/saveSpvCase";
	}
	/**
	 * 详情
	 * @param pkid
	 * @param model
	 * @return
	 */
	@RequestMapping("spvDetail")
	public String SpvDetail(long pkid,Model model){
		SpvBaseInfoVO baseInfoVO=new SpvBaseInfoVO();
		//合约基本信息
		ToSpv spv= toSpvService.selectByPrimaryKey(pkid);
		baseInfoVO.setToSpv(spv);
		//买卖双方信息
		List<ToSpvCust> custs=toSpvService.findCustBySpvCode(spv.getSpvCode());
		baseInfoVO.setSpvCustList(custs);
		//房屋信息
		ToSpvProperty spvProperty=toSpvService.findPropertyBySpvCode(spv.getSpvCode());
		baseInfoVO.setToSpvProperty(spvProperty);
		
		return "spv/SpvDetail";
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
     * @param toSpv
     * @param toSpvDeCondList
     * @param delIds
     * @return
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
     * 保存资金监管签约
     * @param toSpv
     * @param toSpvDeCondList
     * @param delIds
     * @return
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
    @RequestMapping("spvApply/process")
	public String toSpvApplyProcess(HttpServletRequest request,
			HttpServletResponse response,String caseCode,String source,String instCode,String taskitem){

		request.setAttribute("source", source);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("caseBaseVO", caseBaseVO);
		
		toAccesoryListService.getAccesoryList(request, taskitem);
    	ToSpv toSpv = toSpvService.queryToSpvByCaseCode(caseCode);
		request.setAttribute("toSpv", toSpv);
		SpvDeRecVo spvDeRecVo = toSpvService.findByProcessInstanceId(instCode);
		request.setAttribute("spvDeRecVo", spvDeRecVo);
		return "task/taskSpvApply";
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
	public String spvApply(HttpServletRequest request,Boolean SpvApplyApprove,
			HttpServletResponse response,String caseCode,String source,String instCode,String taskId){

		List<RestVariable> variables = new ArrayList<RestVariable>();
		variables.add(new RestVariable("SpvApplyApprove",SpvApplyApprove));
		workFlowManager.submitTask(variables, taskId, instCode, null, caseCode);
		
		return "task/taskSpvApply";
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
	@RequestMapping("spvApprove/process")
	public String toSpvApproveProcess(HttpServletRequest request,
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
		return "task/taskSpvApprove";
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
	public String spvApprove(HttpServletRequest request,Boolean SpvApplyApprove,
			HttpServletResponse response,String caseCode,String source,String instCode,String taskId){

		List<RestVariable> variables = new ArrayList<RestVariable>();
		workFlowManager.submitTask(variables, taskId, instCode, null, caseCode);

		return "task/taskSpvApprove";
	}

}



