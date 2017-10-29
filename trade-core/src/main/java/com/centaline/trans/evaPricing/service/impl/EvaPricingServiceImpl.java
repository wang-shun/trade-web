package com.centaline.trans.evaPricing.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.evaPricing.entity.ToEvaPricingVo;
import com.centaline.trans.evaPricing.repository.ToEvaPricingMapper;
import com.centaline.trans.evaPricing.service.EvaPricingService;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.repository.ToEvalReportProcessMapper;
import com.centaline.trans.mortgage.entity.ToEguPropertyInfo;
import com.centaline.trans.utils.DateUtil;

/**
 * 询价serviceImpl
 * @author wbcaiyx
 *
 */
@Service
public class EvaPricingServiceImpl implements EvaPricingService{

	/**
	 * 询价编码前缀
	 */
	private static String EVA_CODE_PRE = "XJ-TJ-";
	
	@Autowired
	private ToCaseMapper tocaseMapper;
	@Autowired
	ToEvaPricingMapper toEvaPricingMapper;
	@Autowired
	UamUserOrgService uamUserOrgService;
	@Autowired
	private ToEvalReportProcessMapper toEvalReportProcessMapper;
	@Autowired
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private TaskService taskService;

	@Override
	public ToEvaPricingVo findEvaPricingDetailByPKID(Long PKID,String evaCode) {
		ToEvaPricingVo vo = toEvaPricingMapper.findEvaPricingDetailByPKID(PKID,evaCode);
		if(StringUtils.isNotBlank(vo.getAriserId())){
			User user = uamUserOrgService.getUserById(vo.getAriserId());
			vo.setAriserName(user==null?null:user.getRealName());
			vo.setAriserOrgName(user==null?null:user.getOrgName());
		}
		
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AjaxResponse<String> insertEvaPricing(ToEvaPricingVo vo) {
		SessionUser user = uamSessionService.getSessionUser();
		String userId = user.getId();
		
		BigDecimal evaPrice = vo.getTotalPrice();
		BigDecimal originPrice = vo.getOriginPrice();
		
		List<String> evaCodes = new ArrayList<String>();
		for(String finorgId :vo.getFinorgIds()){
			String evaCode = generateEvaCode();
			
			vo.setEvaCode(evaCode);
			vo.setTotalPrice(evaPrice.multiply(new BigDecimal(10000)));
			vo.setOriginPrice(originPrice == null?null:originPrice.multiply(new BigDecimal(10000)));
			vo.setCreateTime(new Date());
			vo.setFinorgId(finorgId);
			
			ToEguPropertyInfo propertyVo = new ToEguPropertyInfo();
			propertyVo.setEvaCode(evaCode);//询价编号
			propertyVo.setResidenceName(vo.getResidenceName());//产证地址
			propertyVo.setTotalFloor(vo.getTotalFloor());//总楼层
			propertyVo.setFloor(vo.getFloor());//所在楼层
			propertyVo.setArea(vo.getArea()==null?null:vo.getArea().doubleValue());//面积
			propertyVo.setCompleteYear(vo.getCompleteYear());//竣工年限
			propertyVo.setHouseAge(vo.getHouseAge());//房龄
			propertyVo.setRoom(vo.getRoom());//房型
			propertyVo.setHall(vo.getHall());
			propertyVo.setToilet(vo.getToilet());
			propertyVo.setPropType(vo.getPropType());//房屋类型
			
			Integer count =  toEvaPricingMapper.insertSelective(vo);
			if(count ==0 || count == null){
				throw new BusinessException("新增询价失败!");
			}
			Integer eguCount =  toEvaPricingMapper.insertEguPropertyInfoSelective(propertyVo);
			if(eguCount ==0 || eguCount == null){
				throw new BusinessException("新增询价物业失败!");
			}
			
			evaCodes.add(evaCode);
		}
		
		if(evaCodes.size() > 0 ){
			for(String evaCode : evaCodes){
				//启动流程
				String processDefId = propertyUtilsService.getProcessDfId("evaPricing_process");
				Map<String, Object> vals = new HashMap<String,Object>();
				//目前为内勤本身
				//查询内勤
//				vals.put("assistant", user.getUsername());
				List<User>  assiss = uamUserOrgService.findHistoryUserByOrgIdAndJobCode(user.getServiceDepId(),TransJobs.TNQZL.getCode());
				if(assiss == null || assiss.size() <= 0){
					throw new BusinessException("查无内勤助理!");
				}
				vals.put("assistant", assiss.get(0).getUsername());
				StartProcessInstanceVo pVo = processInstanceService.startWorkFlowByDfId(processDefId, evaCode, vals);
	
				ToWorkFlow wf = new ToWorkFlow();
				wf.setBusinessKey("evaPricing_process");
				wf.setCaseCode(!StringUtils.isEmpty(vo.getCaseCode())?vo.getCaseCode():evaCode);
				wf.setBizCode(evaCode);
				wf.setProcessOwner(userId);
				wf.setProcessDefinitionId(processDefId);
				wf.setInstCode(pVo.getId());
				wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
				toWorkFlowService.insertSelective(wf);
			}
		}
		return AjaxResponse.success();
	}

	@Override
	public void updateEvaPricing(ToEvaPricingVo vo) {
		vo.setTotalPrice(vo.getTotalPrice()==null?vo.getTotalPrice():vo.getTotalPrice().multiply(new BigDecimal(10000)));
		Integer count = toEvaPricingMapper.updateEvaPricing(vo);
		if(count ==0 || count == null){
			throw new BusinessException("记录询价失败!");
		}
		Integer propCount = null;
		if("1".equals(vo.getIsValid())){
			propCount = toEvaPricingMapper.updateEguPropertyInfo(vo.getEvaCode(), vo.getHouseAge());
			if(propCount ==0 || propCount == null){
				throw new BusinessException("记录询价物业失败!");
			}
		}
		
		if(vo.getIsSubmit() == 1){
			List<RestVariable> variables = new ArrayList<RestVariable>();
			workFlowManager.submitTask(variables, vo.getTaskId(), vo.getProcessInstanceId(), null, null);
			
			//flow完结
			ToWorkFlow flow=new ToWorkFlow();
			flow.setBusinessKey("evaPricing_process");
			flow.setCaseCode(!StringUtils.isEmpty(vo.getCaseCode())?vo.getCaseCode():vo.getEvaCode());
			flow.setBizCode(vo.getEvaCode());
			ToWorkFlow evaPricingFlow= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(flow);
			evaPricingFlow.setStatus(WorkFlowStatus.COMPLETE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(evaPricingFlow);
		}
	}
	
	@Override
	public int cancelByPKID(Long PKID) {
		Integer count = toEvaPricingMapper.cancelEvaPricingByPKID(PKID);
		if(count ==0 || count == null){
			throw new BusinessException("取消询价失败!");
		}
		return count;
	}

	@Override
	public boolean queryInfoByCase(String caseCode) {
		Integer count = toEvaPricingMapper.queryInfoByCaseCode(caseCode);
		if(count !=null && count > 0){
			return true;
		}
		return false;
	}
	
	@Override
	public List<Map<String, String>> queryEvaFinOrg() {
		
		return toEvaPricingMapper.queryEvaFinOrg();
	}
	
	@Override
	public AjaxResponse<String> updatEevalRelation(long pkid, String caseCode,String addr) {
		//更新询价信息,案件编号
		int count = toEvaPricingMapper.updateEvaPricingRela(pkid, caseCode, addr);
		if(count == 0){
			throw new BusinessException("询价信息更新失败");
		}
		
		return AjaxResponse.success();
	}
	
	/**
	 * 自生成询价编号
	 * @return
	 */
	private String generateEvaCode(){	
		StringBuilder s = new StringBuilder();
		s.append(EVA_CODE_PRE);
		s.append(DateUtil.getFormatDate(new Date(), "yyyyMM"));
		s.append(tocaseMapper.nextCaseCodeNumber());
		return s.toString();
	}

	@Override
	public AjaxResponse<String> updateEvaPricingDetail(ToEvaPricingVo toEvaPricingVo) {
		AjaxResponse<String> result = new AjaxResponse<String>();
		
		String reason = toEvaPricingVo.getReason();
		int count = toEvaPricingMapper.updateEvaPricingDetail(toEvaPricingVo.getPkid(), toEvaPricingVo.getIsValid(), reason.length()>255?reason.substring(0, 255):reason);
		if(count ==0){
			result.setSuccess(false);
			result.setMessage("数据更新失败");
			return result;
		}
		taskService.submitTask(toEvaPricingVo.getTaskId());
//		List<RestVariable> variables = new ArrayList<RestVariable>();
//		workFlowManager.submitTask(variables, toEvaPricingVo.getTaskId(), toEvaPricingVo.getProcessInstanceId(), null, null);
		
		//flow完结
		ToWorkFlow flow=new ToWorkFlow();
		flow.setBusinessKey("evaPricing_process");
		flow.setCaseCode(!StringUtils.isEmpty(toEvaPricingVo.getCaseCode())?toEvaPricingVo.getCaseCode():toEvaPricingVo.getEvaCode());
		flow.setBizCode(toEvaPricingVo.getEvaCode());
		ToWorkFlow evaPricingFlow= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(flow);
		evaPricingFlow.setStatus(WorkFlowStatus.COMPLETE.getCode());
		toWorkFlowService.updateByPrimaryKeySelective(evaPricingFlow);
		return result;
	}

	@Override
	public AjaxResponse<Integer> checkEvalProcess(String caseCode) {
		AjaxResponse<Integer> result = new AjaxResponse<Integer>();
		
		//检查是否已有评估申请记录
		ToEvalReportProcess reportProcess = toEvalReportProcessMapper.findToEvalReportProcessByCaseCode(caseCode);		
		if(reportProcess != null){
			result.setSuccess(false);
			result.setMessage("系统已经存在此案件评估上报申请，请在评估申请中查询!");
			return result;
		}
		//检查是否已有询价申请记录及是否已完成,区分
		/*List<ToEvaPricingVo> evaPricings =  toEvaPricingMapper.findEvaPricingDetailByCaseCode(caseCode);
		if(evaPricings != null){
			for(ToEvaPricingVo evaPricing:evaPricings){
				if("0".equals(evaPricing.getStatus())){
					result.setSuccess(false);
					result.setMessage("系统已存在与此案件相关的未完成的询价申请记录,请完成询价申请!");
					return result;
				}else if("1".equals(evaPricing.getStatus())){
					//1：询价已完成,可以评估申请
					result.setContent(1);
					return result;
				}
			}	
		}
		//2：无询价申请记录或询价无效,需要先询价
		result.setContent(2);*/
		return result;
	}


}
