package com.centaline.trans.cases.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.vo.TgServItemAndProcessorVo;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.OrgNameEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.TsOrgRelationService;
import com.centaline.trans.common.vo.TgCooperVo;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.service.TlTaskReassigntLogService;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.entity.TsTeamScope;
import com.centaline.trans.team.service.TsTeamPropertyService;
import com.centaline.trans.team.service.TsTeamScopeService;
import com.centaline.trans.team.vo.UserOrgRelationVO;

@Controller
@RequestMapping(value = "/case")
public class CaseChangeController {

	@Autowired
	private TgServItemAndProcessorService tgservItemAndProcessorService;

	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private UamBasedataService dictService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private TsTeamScopeService tsTeamScopeService;
	@Autowired
	private TsTeamPropertyService tsTeamPropertyService;
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private TlTaskReassigntLogService taskReassingtLogService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private ToCaseMapper toCaseMapper;
	@Autowired
	private TsOrgRelationService tsOrgRelationService;/* 组别属性表 */

	/**
	 * 根据字典类型，获得相应字典数据
	 * 
	 * @param dictByType
	 * @return
	 */
	private List<Dict> getDictList(String dictByType) {
		List<Dict> list = new ArrayList<Dict>();
		Dict dict = uamBasedataService.findDictByType(dictByType);
		if (dict != null) {
			list = dict.getChildren();
		}
		return list;
	}

	private boolean containsDic(List<Dict> dics, String dic) {
		if (dic == null)
			return false;
		for (Dict dict : dics) {
			if (dic.equals(dict.getCode())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 功能：变更合作对象[查询出满足变更合作对象条件的服务项和合作顾问]
	 * 
	 * @param caseCode[案件编号]
	 * @param processorId
	 */
	@RequestMapping(value = "changeCoope")
	@ResponseBody
	public Map<String, Object> changeCoope(HttpServletRequest request, HttpServletResponse response, String caseCode) {

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, TsTeamProperty> pMap = new HashMap<>();
		// 1 查询案件服务项目
		List<TgServItemAndProcessor> servitemList = tgservItemAndProcessorService.selectBycasecodeandProcessorid(caseCode);
		SessionUser user = uamSessionService.getSessionUser();

		Org myDistrict = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(),
				DepTypeEnum.TYCQY.getCode()); // 获取用户的所在的贵宾服务部

		String depCode = user.getServiceDepCode();
		List<TsTeamScope> tsTeamScopes = tsTeamScopeService.selectByOrgCode(depCode);
		Map<String, List<User>> orgUserMap = new HashMap<>();// 对应组相关人员
		for (TgServItemAndProcessor ser : servitemList) {
			List<User> list = ser.getUsers();
			if (list == null) {
				list = new ArrayList<>();
				ser.setUsers(list);
			}
			for (TsTeamScope ts : tsTeamScopes) {
				/*
				 * 组织查询条件根据tsTeamScope.getYuTeamCode(),IsResponseTeam
				 * 判断合作组是否符合条件
				 */
				TsTeamProperty ttps = null;
				if (pMap.containsKey(ts.getYuTeamCode())) {
					ttps = pMap.get(ts.getYuTeamCode());
				} else {
					TsTeamProperty tp = new TsTeamProperty();
					tp.setYuTeamCode(ts.getYuTeamCode());

					/* 浦东合作顾问选中台 */
					if ("FF5BC56E0E4B45289DAA5721A494C7C5".equals(myDistrict.getId())) {
						tp.setYuTeamCode(depCode);
						List<TsTeamProperty> ttpps = tsTeamPropertyService.findTeamPropertyCooperations(tp);
						if (ttpps != null) {/* 有符合条件的合作组 */
							for (TsTeamProperty ttp : ttpps) {
								if (ttp.getYuTeamCode() != null) {
									String orgCode = ttp.getYuTeamCode();
									if (orgUserMap.get(orgCode) == null) {
										Org org = uamUserOrgService.getOrgByCode(orgCode);
										if (org == null)
											continue;
										/* 浦东合作顾问选中台 且只选浦东交易1组的中台 */
										List<User> uList = null;
										if (OrgNameEnum.T_PUDONGTRADEONE_ORG.getCode().equals(org.getOrgCode())) {
											uList = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(),
													TransJobs.JYUZTGW.getCode());
										} else {
											uList = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(),
													TransJobs.TJYGW.getCode());
										}

										orgUserMap.put(orgCode, uList);
										list.removeAll(uList);
										list.addAll(uList);
									} else {
										list.removeAll(orgUserMap.get(orgCode));
										list.addAll(orgUserMap.get(orgCode));

									}
								}
							}
						}
					} else {
						// tp.setIsResponseTeam("0"); /* 合作组必须为非主办组 */
						ttps = tsTeamPropertyService.findTeamPropertyCooperation(tp);
						List<Dict> dictList = getDictList(ttps.getTeamProperty());
						if (containsDic(dictList, ser.getSrvCode())) {
							String orgCode = ttps.getYuTeamCode();
							if (orgUserMap.get(orgCode) == null) {
								Org org = uamUserOrgService.getOrgByCode(orgCode);
								if (org == null)
									continue;
								/* 浦东合作顾问选中台 且只选浦东交易1组的中台 */
								List<User> uList = null;

								uList = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(),
										TransJobs.TJYGW.getCode());

								orgUserMap.put(orgCode, uList);
								list.removeAll(uList);
								list.addAll(uList);
							} else {
								list.removeAll(orgUserMap.get(orgCode));
								list.addAll(orgUserMap.get(orgCode));

							}
						}
					}

				}

			}
		}

		map.put("servitemList", servitemList); // 1 查询案件服务项目
		// map.put("userList", userList); // 3 获取到的合作交易顾问
		map.put("orgcode", myDistrict.getOrgCode());/* 浦东合作顾问选中台 */
		return map;
	}
	
	/**
	 * 功能：变更合作对象[查询出满足变更合作对象条件的服务项和合作顾问]
	 * 
	 * @param caseCode[案件编号]
	 * @param processorId
	 */
	@RequestMapping(value = "changeCoopeRelation")
	@ResponseBody
	public Map<String, Object> changeCoopeRelation(HttpServletRequest request, HttpServletResponse response, String caseCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 1 查询案件服务项目
		List<TgServItemAndProcessor> servitemList = tgservItemAndProcessorService.selectBycasecodeandProcessorid(caseCode);
		SessionUser user = uamSessionService.getSessionUser();
		String orgId = user.getServiceDepId();
		//查询后台组合作顾问（浦东1组查中台顾问，其他组查交易顾问）
		List<UserOrgRelationVO> userList = tsOrgRelationService.getUserOrgRelationByOrgId(orgId);
		List<User> ul = new ArrayList<>();
		for (UserOrgRelationVO userOrgRelationVO : userList) {
			User u = new User();
			u.setId(userOrgRelationVO.getId());
			u.setRealName(userOrgRelationVO.getRealName());
			u.setOrgName(userOrgRelationVO.getOrgName());
			ul.add(u);
		}
		for (TgServItemAndProcessor ser : servitemList) {
				ser.setUsers(ul);
		}
		Org myDistrict = uamUserOrgService.getParentOrgByDepHierarchy(user.getServiceDepId(),
				DepTypeEnum.TYCQY.getCode()); // 获取用户的所在的贵宾服务部
		map.put("servitemList", servitemList); // 1 查询案件服务项目
		map.put("orgcode", myDistrict.getOrgCode());/* 浦东合作顾问选中台 */
		return map;
	}
	
	
	@RequestMapping(value = "changeCoopeForNew")
	@ResponseBody
	public Map<String, Object> changeCoopeForNew(HttpServletRequest request, HttpServletResponse response, String cooperCaseCode) {

		Map<String, Object> map = new HashMap<String, Object>();		
		// 1 查询案件服务项目
		List<TgCooperVo> servitemList = tgservItemAndProcessorService.selectBycasecodeandProcessorIdForSunxw(cooperCaseCode);	
		map.put("servitemList", servitemList); 
	
		return map;
	}
	
	/**
	 * 功能：变更合作对象[修改变更合作对象] 描述：根据 srvCode 去修改 processorId 和 orgId
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "updateCoope")
	public String updateCoope(HttpServletRequest request, Model model, HttpServletResponse response,
			TgServItemAndProcessorVo tgServItemAndProcessorVo, String instCode, String caseId) throws IOException {
		
		List<String> caseCodeList = tgServItemAndProcessorVo.getCaseCode();
		List<String> processorIdList = tgServItemAndProcessorVo.getProcessorId();
		List<String> srvCodeList = tgServItemAndProcessorVo.getSrvCode();
		List<String> preProcessorIdList = tgServItemAndProcessorVo.getPreProcessorId();
		
		int updatecoope = 0;
		TgServItemAndProcessor pro = null;
		List<TaskVo> tasks = new ArrayList<TaskVo>();
		
		if(processorIdList!=null&&!processorIdList.isEmpty()){ 	  
		  TaskQuery tq=new TaskQuery(); tq.setProcessInstanceId(instCode);
		  tq.setFinished(false); 
		  tasks=workFlowManager.listTasks(tq).getData();
		}
		 

		for (int i = 0; i < processorIdList.size(); i++) {
			String caseCode = caseCodeList.get(i);
			String srvCode = srvCodeList.get(i);
			String processorId = processorIdList.get(i);
			User processor = uamUserOrgService.getUserById(processorId);
			String preProcessorId = preProcessorIdList.get(i);
			User preProcessor = uamUserOrgService.getUserById(preProcessorId);

			pro = new TgServItemAndProcessor();
			pro.setPreProcessorId(preProcessorId);
			pro.setPreOrgId(preProcessor.getOrgId());
			pro.setProcessorId(processorId);
			pro.setOrgId(processor.getOrgId());
			pro.setCaseCode(caseCode);
			pro.setSrvCode(srvCode);

			TgServItemAndProcessor proDb = tgservItemAndProcessorService.findTgServItemAndProcessor(pro);
			updatecoope = tgservItemAndProcessorService.updateCoope(pro);

			// 查询该案件下的所有任务
			List<String> insCodeList = toWorkFlowService.queryInstCodesByCaseCode(caseCode);
			for (String insCode : insCodeList) {
				TaskQuery tq = new TaskQuery();
				tq.setProcessInstanceId(insCode);
				tq.setFinished(false);

				List<TaskVo> taskList1 = workFlowManager.listTasks(tq).getData();
				tasks.addAll(taskList1);
			}
			updateWorkflow(srvCode, processorId, tasks, proDb.getProcessorId(), caseCode);
		}

		if (updatecoope > 0) {
			String msg = "操作成功!";
			request.setAttribute("msg", msg);
		} else {
			String msg = "操作失败, 请刷新后再次尝试!";
			request.setAttribute("msg", msg);
		}
		response.sendRedirect("caseDetail?caseId=" + caseId);
		return null;
	}

	public void updateWorkflow(String srvCode, String userId, List<TaskVo> tasks, String fUserId, String caseCode) {
		if (tasks != null && !tasks.isEmpty() && fUserId != null) {
			for (TaskVo taskVo : tasks) {
				Dict dic = dictService.findDictByType(srvCode);
				if (containsDic(dic, taskVo.getTaskDefinitionKey())) {
					User u = uamUserOrgService.getUserById(userId);
					User uf = uamUserOrgService.getUserById(fUserId);
					if (uf != null && u != null && uf.getUsername() != null
							&& uf.getUsername().equals(taskVo.getAssignee())) {
						taskReassingtLogService.record(taskVo, u.getUsername(), caseCode);
						taskVo.setAssignee(u.getUsername());
						workFlowManager.updateTask(taskVo);
					}
				}
			}
		}

	}

	private boolean containsDic(Dict dic, String dicCode) {
		if (StringUtils.isBlank(dicCode) || dic == null)
			return false;
		if (dicCode.equals(dic.getCode())) {
			return true;
		}
		for (Dict subDic : dic.getChildren()) {
			if (containsDic(subDic, dicCode)) {
				return true;
			}
		}
		return false;
	}
		
	
	/**
	 * 变更合作人  For allUser
	 * @author zhuody
	 * @Date 2016-12-02
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/updateCoopeSubmit")
	@ResponseBody
	public AjaxResponse<?> updateCoopeSubmit(@RequestBody TgServItemAndProcessorVo tgServItemAndProcessorVo) {
		AjaxResponse<?> response = new AjaxResponse<>();
		int updatecoope = -1;
		//获取所有参数	
		if(tgServItemAndProcessorVo != null){			
			List<String> caseCodeList = tgServItemAndProcessorVo.getCaseCode();
			List<String> orgIdList = tgServItemAndProcessorVo.getOrgId();		
			List<String> processorIdList = tgServItemAndProcessorVo.getProcessorId();		
			List<String> srvCodeList = tgServItemAndProcessorVo.getSrvCode();
			List<String> preProcessorIdList = tgServItemAndProcessorVo.getPreProcessorId();
			List<String> preOrgIdList = tgServItemAndProcessorVo.getPreOrgId();
						
			String caseCodeForInstCode = "";
			ToWorkFlow toWorkFlow =  new ToWorkFlow();
			String instCode = "";
			if(caseCodeList.size() > 0){
				caseCodeForInstCode = caseCodeList.get(0);
				if(caseCodeForInstCode != null  && !"".equals(caseCodeForInstCode)){	
					// 工作流  是否需要判断结案案件没有变更的权限
					ToWorkFlow inWorkFlow = new ToWorkFlow();
					inWorkFlow.setBusinessKey("operation_process");
					inWorkFlow.setCaseCode(caseCodeForInstCode);
					toWorkFlow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(inWorkFlow);
					if(toWorkFlow != null){
						instCode = toWorkFlow.getInstCode();  //查询获取 instCode
					}
			    }
			}
			
			
			TgServItemAndProcessor pro = null;
			List<TaskVo> tasks = new ArrayList<TaskVo>();
			
			if(processorIdList != null && !processorIdList.isEmpty()){ 	  
			  TaskQuery tq = new TaskQuery(); 
			  tq.setProcessInstanceId(instCode);
			  tq.setFinished(false); 
			  tasks = workFlowManager.listTasks(tq).getData();
			}
			
			//重新选取了 合作人的情况下 才更新
			if(processorIdList.size() > 0){
				for (int i = 0; i < processorIdList.size(); i++) {
					String caseCode = caseCodeList.get(i);
					String srvCode = srvCodeList.get(i);
					String processorId = processorIdList.get(i);//新的案件合作人
					String orgId = orgIdList.get(i);
					String oldProcessorId = preProcessorIdList.get(i);
					String preOrgId = preOrgIdList.get(i);
					
					pro = new TgServItemAndProcessor();
					pro.setProcessorId(processorId);
					pro.setCaseCode(caseCode);
					pro.setSrvCode(srvCode);	
					pro.setOrgId(orgId);
					pro.setPreOrgId(preOrgId);
					pro.setPreProcessorId(oldProcessorId);		
					
					TgServItemAndProcessor proDb = tgservItemAndProcessorService.findTgServItemAndProcessor(pro);
					if(processorId != null &&  !(oldProcessorId.equals(processorId))){
						updatecoope = tgservItemAndProcessorService.updateCoope(pro);
					}
					
		
					// 查询该案件下的所有任务
					List<String> insCodeList = toWorkFlowService.queryInstCodesByCaseCode(caseCode);
					for (String insCode : insCodeList) {
						TaskQuery tq = new TaskQuery();
						tq.setProcessInstanceId(insCode);
						tq.setFinished(false);
		
						List<TaskVo> taskList1 = workFlowManager.listTasks(tq).getData();
						tasks.addAll(taskList1);
					}
					updateWorkflow(srvCode, processorId, tasks, proDb.getProcessorId(), caseCode);
				}		
			}else{
				updatecoope = 0;
			}

		}
		
		if (updatecoope > 0){
			response.setSuccess(true);
			response.setMessage("恭喜，项目合作人变更成功！");
		}else if(updatecoope == 0){
			response.setSuccess(true);
			response.setMessage("未选取项目合作人，不进行变更！");
		}else{
			response.setSuccess(false);
			response.setMessage("案件基本表更新失败！");
		}
	
		return response;
	}
}
