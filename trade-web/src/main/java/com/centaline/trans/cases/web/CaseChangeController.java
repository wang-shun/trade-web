package com.centaline.trans.cases.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToChangeRecord;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.ToChangeRecordMapper;
import com.centaline.trans.cases.vo.TgServItemAndProcessorVo;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.enums.ChangeRecordTypeEnum;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.OrgNameEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.engine.bean.TaskHistoricQuery;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.service.TlTaskReassigntLogService;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.entity.TsTeamScope;
import com.centaline.trans.team.service.TsTeamPropertyService;
import com.centaline.trans.team.service.TsTeamScopeService;

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
	private ToChangeRecordMapper toChangeRecordMapper;

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
		List<TgServItemAndProcessor> servitemList = tgservItemAndProcessorService
				.selectBycasecodeandProcessorid(caseCode);
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
	 * 功能：变更合作对象[修改变更合作对象] 描述：根据 srvCode 去修改 processorId 和 orgId
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "updateCoope")
	public String updateCoope(HttpServletRequest request, Model model, HttpServletResponse response,
			TgServItemAndProcessorVo tgServItemAndProcessorVo, String instCode, String caseId,String myProcessorId,String project,String oldProcessorId) throws IOException {

		SessionUser user = uamSessionService.getSessionUser();
		List<String> caseCodeList = tgServItemAndProcessorVo.getCaseCode();
		List<String> orgIdList = tgServItemAndProcessorVo.getOrgId();
		List<String> processorIdList = tgServItemAndProcessorVo.getProcessorId();
		List<String> srvCodeList = tgServItemAndProcessorVo.getSrvCode();

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
			String orgId = orgIdList.get(i);

			pro = new TgServItemAndProcessor();
			pro.setProcessorId(processorId);
			pro.setCaseCode(caseCode);
			pro.setSrvCode(srvCode);

			pro.setOrgId(orgId);
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
		
		//添加变更记录
		String[] myProcessorIdArr = myProcessorId.split(",");
		String[] projectArr = project.split(",");
		String[] oldProcessorIdArr = oldProcessorId.split(",");
		for(int i = 0; i < myProcessorIdArr.length; i++){
			//没有变化就跳过
			if(oldProcessorIdArr[i].equals(myProcessorIdArr[i])) continue;
			
			ToChangeRecord toChangeRecord = new ToChangeRecord();
			toChangeRecord.setCaseCode(toCaseMapper.selectByPrimaryKey(Long.parseLong(caseId)).getCaseCode());
			toChangeRecord.setPartName(projectArr[i]);
			toChangeRecord.setChangeType(ChangeRecordTypeEnum.PARTNER.getCode());
			toChangeRecord.setChangeBeforePerson(oldProcessorIdArr[i]);
			toChangeRecord.setChangeAfterPerson(myProcessorIdArr[i]);
			toChangeRecord.setOperator(user.getId());
			toChangeRecord.setOperateTime(new Date());
			toChangeRecord.setCreateBy(user.getId());
			toChangeRecord.setCreateTime(new Date());	
			toChangeRecordMapper.insertSelective(toChangeRecord);
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
}
