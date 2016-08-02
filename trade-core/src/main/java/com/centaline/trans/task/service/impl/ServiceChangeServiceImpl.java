package com.centaline.trans.task.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToServChangeHistroty;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.TransDictEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.common.repository.ToServChangeHistrotyMapper;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.repository.MortStepMapper;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.task.service.ServiceChangeService;
import com.centaline.trans.task.service.ToTransPlanService;
import com.centaline.trans.task.service.UnlocatedTaskService;

@Service
public class ServiceChangeServiceImpl implements ServiceChangeService {

	@Autowired
	private TgServItemAndProcessorMapper tgServItemAndProcessorMapper;
	@Autowired
	private ToServChangeHistrotyMapper toServChangeHistrotyMapper;
	@Autowired
	private UamBasedataService uamBasedataService;
	
	/*流程相关*/
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private ToCaseMapper toCaseMapper;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private ToTransPlanService toTransPlanService;
	@Autowired
	private UnlocatedTaskService unlocatedTaskService;
	@Autowired
	private PropertyUtilsService propertyUtilsService;
	
	
	@Autowired
	private MortStepMapper mortstepMapper;
	
	@Override
	public Integer updateServItemAndProcessor(String caseCode, String content) {
		/*爆单相关*/
		List<ToServChangeHistroty> baodan = toServChangeHistrotyMapper.isBaoDan(caseCode);
		
		/*流程重启相关*/
		ToServChangeHistroty tschSerch = new ToServChangeHistroty();
		tschSerch.setCaseCode(caseCode);
		tschSerch.setServCode("30004001");//商贷
		ToServChangeHistroty shangdai = null;
		List<ToServChangeHistroty> shangdaiList = toServChangeHistrotyMapper.findToServChangeHistroty(tschSerch);
		if(shangdaiList != null && shangdaiList.size() != 0) {
			shangdai = shangdaiList.get(0);
		}
		tschSerch.setServCode("30004002");//公积金
		ToServChangeHistroty gongjijin = null;
		List<ToServChangeHistroty> gongjijinList = toServChangeHistrotyMapper.findToServChangeHistroty(tschSerch);
		if(gongjijinList != null && gongjijinList.size() != 0) {
			gongjijin = gongjijinList.get(0);
		}
		if(baodan!=null && baodan.size() > 0) {
			/*爆单处理*/
			// 保留服务表tgServItemAndProcessorMapper.deleteByPrimaryCaseCode(caseCode);
			//删除流程
			ToWorkFlow t=new ToWorkFlow();
			t.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
			t.setCaseCode(caseCode);
			List<ToWorkFlow> tfs  = toWorkFlowService.queryActiveToWorkFlowByCaseCode(t);
			if(tfs!=null){
				for (ToWorkFlow toWorkFlow : tfs) {
					toWorkFlow.setStatus(WorkFlowStatus.BAODAN.getCode());//爆单状态
					toWorkFlowService.updateByPrimaryKeySelective(toWorkFlow);
				}
			}
    		for(ToWorkFlow toWorkFlow : tfs){
    			try {
    				unlocatedTaskService.deleteByInstCode(toWorkFlow.getInstCode());
            		workFlowManager.deleteProcess(toWorkFlow.getInstCode());
				} catch (WorkFlowException e) {
					if(!e.getMessage().contains("statusCode[404]"))throw e;
					AjaxResponse.fail(e.getMessage());
					return 1;
				}
    		}
     
    		//状态：爆单
       		ToCase toCase = toCaseMapper.findToCaseByCaseCode(caseCode);
       		toCase.setCaseProperty(CasePropertyEnum.TPBD.getCode());
    		int reUp = toCaseMapper.updateByPrimaryKey(toCase);
    		if(reUp == 0) {
    			return 2;
    		}
			/*更新服务项变更表*/
			ToServChangeHistroty toServChangeHistroty = new ToServChangeHistroty();
			toServChangeHistroty.setCaseCode(caseCode);
			List<ToServChangeHistroty> list = toServChangeHistrotyMapper.findToServChangeHistroty(toServChangeHistroty);
			for(ToServChangeHistroty tsch:list) {
				tsch.setResult(content.equals("true")?"1":"0");
				tsch.setProcessInstanceId(null);
				tsch.setReason(null);
				toServChangeHistrotyMapper.updateByPrimaryKeySelective(tsch);
			}
    		return 11;
		} else if(shangdai != null || gongjijin != null) {
			/*业务变更处理*/
			/**
			 * 功能：删除 交易过户服务(签约)[3000401001], 交易过户服务(除签约)[3000401002], 纯公积金贷[3000400201], 商业贷/组合贷[3000400101]
			 * 修改人：zhangxb16
			 */
			// tgServItemAndProcessorMapper.deleteByPrimaryCaseCode(caseCode);
			try{
				int del=tgServItemAndProcessorMapper.deleteByCaseCode(caseCode);
				if(del>0){
				}else{
					throw new BusinessException("表[T_TG_SERV_ITEM_AND_PROCESSOR]没有删除成功, 请刷新后再次尝试！");
				}
			}catch(BusinessException ex){
				throw new BusinessException("表[T_TG_SERV_ITEM_AND_PROCESSOR]没有删除成功, 请刷新后再次尝试！");
			}
			
			//删除主流程
			ToWorkFlow wFlow = new ToWorkFlow();
    		wFlow.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
    		wFlow.setCaseCode(caseCode);
    		wFlow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wFlow);
    		try {
        		workFlowManager.deleteProcess(wFlow.getInstCode());
			} catch (WorkFlowException e) {
				if(!e.getMessage().contains("statusCode[404]"))throw e;
				return 1;
			}
    		wFlow.setStatus(WorkFlowStatus.TERMINATE.getCode());//流程结束
			toWorkFlowService.updateByPrimaryKeySelective(wFlow);
            //重新启动
    		ToCase toCase = toCaseMapper.findToCaseByCaseCode(caseCode);
    		toCase.setStatus(CaseStatusEnum.YFD.getCode());
    		int reUp = toCaseMapper.updateByPrimaryKey(toCase);
    		if(reUp == 0) {
    			return 2;
    		}
        	ProcessInstance process = new ProcessInstance();
        	process.setBusinessKey(caseCode);
        	process.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
        	/*流程引擎相关*/
        	Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.WBUSSKEY.getCode());
    		List<RestVariable> variables = new ArrayList<RestVariable>();
    	    Iterator<String> it = defValsMap.keySet().iterator();  
    	    while (it.hasNext()) {  
	            String key = it.next();  
	    		RestVariable restVariable = new RestVariable();
	    		restVariable.setName(key); 
	    		restVariable.setValue(defValsMap.get(key));
	    		variables.add(restVariable);
		    }
        	process.setVariables(variables);
        	
        	User user = uamUserOrgService.getUserById(toCase.getLeadingProcessId());
       		ToWorkFlow toWorkFlow = new ToWorkFlow();
        	try {
	        	StartProcessInstanceVo pIVo = workFlowManager.startCaseWorkFlow(process, user.getUsername(),caseCode);
	        	if(pIVo==null||pIVo.getId()==null) {
	        		return 4;
	        	}
	    		toWorkFlow.setInstCode(pIVo.getId());
	    		toWorkFlow.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
	    		toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
	    		toWorkFlow.setCaseCode(caseCode);
	        	toWorkFlow.setProcessOwner(toCase.getLeadingProcessId());
	    		/*插入工作流*/
		    	int ss = toWorkFlowService.insertSelective(toWorkFlow);
		    	if(ss <= 0) {
		    		return 5;
		    	}
		    	toTransPlanService.deleteTransPlansByCaseCode(caseCode);
        	} catch (WorkFlowException e) {
        		return 6;
			}
			/*更新服务项变更表*/
			ToServChangeHistroty toServChangeHistroty = new ToServChangeHistroty();
			toServChangeHistroty.setCaseCode(caseCode);
			List<ToServChangeHistroty> list = toServChangeHistrotyMapper.findToServChangeHistroty(toServChangeHistroty);
			for(ToServChangeHistroty tsch:list) {
				tsch.setResult(content.equals("true")?"1":"0");
				tsch.setProcessInstanceId(null);
				tsch.setReason(null);
				toServChangeHistrotyMapper.updateByPrimaryKeySelective(tsch);
			}
			//无效表单数据
			toWorkFlowService.inActiveForm(caseCode);
			
			
			/**
			 * 功能：删除 MORT_STEP 表中对应的数据
			 * 作者：zhangxb16
			 */
			try{
				// 根据 caseCode 到MORT_STEP 表中去查询是否存在记录, 如果存在则删除, 不存在则不做任何处理
				int isExist=mortstepMapper.isExistCaseCode(caseCode);
				if(isExist>0){
					int mt=mortstepMapper.deleteCaseCode(caseCode);
					if(mt>0){
					}else{
						throw new BusinessException("表[MORT_STEP]没有删除成功, 请刷新后再次尝试！");
					}
				}
			}catch(BusinessException ex){
				throw new BusinessException("表[MORT_STEP]没有删除成功, 请刷新后再次尝试！");
			}
			
        	return 12;
		} else {
			/*更新服务项变更表*/
			ToServChangeHistroty toServChangeHistroty = new ToServChangeHistroty();
			toServChangeHistroty.setCaseCode(caseCode);
			List<ToServChangeHistroty> list = toServChangeHistrotyMapper.findToServChangeHistroty(toServChangeHistroty);
			for(ToServChangeHistroty tsch:list) {
				tsch.setResult(content.equals("true")?"1":"0");
				tsch.setProcessInstanceId(null);
				tsch.setReason(null);
				toServChangeHistrotyMapper.updateByPrimaryKeySelective(tsch);

				/*其他流程处理*/
				if(tsch.getChangeType().equals("0")) {
					TgServItemAndProcessor tgServItemAndProcessor = new TgServItemAndProcessor();
					tgServItemAndProcessor.setCaseCode(caseCode);
					tgServItemAndProcessor.setSrvCat(tsch.getServCode());
					Dict dict = uamBasedataService.findDictByTypeAndCode(TransDictEnum.TFWBM.getCode(),tgServItemAndProcessor.getSrvCat());
					if(dict == null) {
						tgServItemAndProcessorMapper.insertSelective(tgServItemAndProcessor);
						continue;
					}
					List<Dict> listD = dict.getChildren();
					if(listD == null || listD.size() == 0) {
						tgServItemAndProcessorMapper.insertSelective(tgServItemAndProcessor);
						continue;
					}
					for(Dict dictSon:listD) {
						tgServItemAndProcessor.setSrvCode(dictSon.getCode());
						if(tgServItemAndProcessorMapper.findTgServItemAndProcessor(tgServItemAndProcessor) == null) {
							tgServItemAndProcessorMapper.insertSelective(tgServItemAndProcessor);
						}
					}
				} else {
					TgServItemAndProcessor tgServItemAndProcessor = new TgServItemAndProcessor();
					tgServItemAndProcessor.setCaseCode(caseCode);
					tgServItemAndProcessor.setSrvCat(tsch.getServCode());
					tgServItemAndProcessorMapper.deleteByUser(tgServItemAndProcessor);
				}
			}
			return 13;
		}
	}
	
	/**
	 * 获得删除的服务项
	 * @param caseCode
	 * @return
	 */
	@Override
	public List<ToServChangeHistroty> queryDelServChangeHistroty(String caseCode) {
		List<ToServChangeHistroty> list = null;
		List<ToServChangeHistroty> baodan = toServChangeHistrotyMapper.isBaoDan(caseCode);
		if(baodan != null && baodan.size() > 0) {
			list = new ArrayList<ToServChangeHistroty>();
			baodan.get(0).setServCode("30004010");
			list.add(baodan.get(0));
		} else {
			ToServChangeHistroty del = new ToServChangeHistroty();
			del.setCaseCode(caseCode);
			del.setChangeType("1");
			list = toServChangeHistrotyMapper.findToServChangeHistroty(del);
		}
		return list;
	}
	
	/**
	 * 获得新增的服务项
	 * @param caseCode
	 * @return
	 */
	@Override
	public String queryAddServChangeHistroty(String caseCode) {
		List<ToServChangeHistroty> list = null;
		ToServChangeHistroty add = new ToServChangeHistroty();
		add.setCaseCode(caseCode);
		add.setChangeType("0");
		list = toServChangeHistrotyMapper.findToServChangeHistroty(add);
		String addServString = "";
		for(ToServChangeHistroty tsch:list) {
			addServString += uamBasedataService.getDictValue(TransDictEnum.TFWBM.getCode(), tsch.getServCode()) + "、";
		}
		
		if(addServString.length() > 0) {
			addServString = addServString.substring(0, addServString.length() - 1);
		} else {
			addServString = null;
		}
		return addServString;
	}

	/**
	 * 审批变更服务项获得
	 */
	@Override
	public Map<String, Object> qureyServChangeHistrotyInfo(String caseCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		String newServChange = "";
		String delServChange = "";
		
		/*爆单处理*/
		List<ToServChangeHistroty> baodan = toServChangeHistrotyMapper.isBaoDan(caseCode);
		if(baodan != null && baodan.size() > 0) {
			delServChange += "交易过户服务（包括审税／核价／查限购／过户／领证）";
			map.put("newServChange", newServChange);
			map.put("delServChange", delServChange);
			return map;
		}
		
		/*非爆单处理*/
		ToServChangeHistroty toServChangeHistroty = new ToServChangeHistroty();
		toServChangeHistroty.setCaseCode(caseCode);
		List<ToServChangeHistroty> list = toServChangeHistrotyMapper.findToServChangeHistroty(toServChangeHistroty);
		for(ToServChangeHistroty tsch:list) {
			if(tsch.getChangeType().equals("0")) {
				newServChange += uamBasedataService.getDictValue(TransDictEnum.TFWBM.getCode(), tsch.getServCode()) + "、";
			} else if(tsch.getChangeType().equals("1")) {
				delServChange += uamBasedataService.getDictValue(TransDictEnum.TFWBM.getCode(), tsch.getServCode()) + "、";
			}
		}
		if(newServChange.length() > 0) {
			newServChange = newServChange.substring(0, newServChange.length() - 1);
		} else {
			newServChange = null;
		}
		if(delServChange.length() > 0) {
			delServChange = delServChange.substring(0, delServChange.length() - 1);
		} else {
			delServChange = null;
		}
		map.put("newServChange", newServChange);
		map.put("delServChange", delServChange);
		return map;
	}

	@Override
	public Integer updateServChangeHistroty(String caseCode) {
		/*更新服务项变更表*/
		ToServChangeHistroty toServChangeHistroty = new ToServChangeHistroty();
		toServChangeHistroty.setCaseCode(caseCode);
		List<ToServChangeHistroty> list = toServChangeHistrotyMapper.findToServChangeHistroty(toServChangeHistroty);
		for(ToServChangeHistroty tsch:list) {
			tsch.setResult("0");
			tsch.setProcessInstanceId(null);
			tsch.setReason(null);
			toServChangeHistrotyMapper.updateByPrimaryKeySelective(tsch);
		}
		return 12;
	}

}