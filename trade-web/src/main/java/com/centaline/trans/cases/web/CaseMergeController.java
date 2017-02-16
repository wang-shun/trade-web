package com.centaline.trans.cases.web;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseMergeVo;
import com.centaline.trans.common.entity.CaseMergerParameter;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.CaseOriginEnum;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.property.service.ToPropertyService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.team.entity.TsTeamScopeTarget;
import com.centaline.trans.team.service.TsTeamScopeTargetService;
import com.centaline.trans.utils.DateUtil;


/**
 * @author  zhuody
 * @description  自录案件相关操作
 * @date  2016-12-09
 */
@Controller
@RequestMapping(value = "/caseMerge")
public class CaseMergeController {

	@Autowired(required = true)
	ToCaseService toCaseService;
	@Autowired(required = true)
	ToCaseInfoService toCaseInfoService;
	@Autowired(required = true)
	ToPropertyInfoService toPropertyInfoService;
	@Autowired(required = true)
	TgGuestInfoService tgGuestInfoService;
	@Autowired(required = true)
	TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired(required = true)
	ToWorkFlowService toWorkFlowService;
	@Autowired(required = true)
	WorkFlowManager workFlowManager;
	
	@Autowired(required = true)
	TsTeamScopeTargetService tsTeamScopeTargetService;

	@Autowired(required = true)
	ToSpvService toSpvService;
	@Autowired(required = true)
	ToPropertyService toPropertyService;

	@Autowired(required = true)
	UamTemplateService uamTemplateService;
	@Autowired(required = true)
	PropertyUtilsService propertyUtilsService;
	@Autowired
	private UamBasedataService uamBasedataService;
	
	
	/**
	 * 页面跳转 
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value="addCase/{keyFlag}")
	public String caseForChange(Model model, ServletRequest request,@PathVariable String keyFlag){
		
		model.addAttribute("flag",keyFlag);
		
		return "case/addCase";
	}	
	
	/**
	 * 自录案件 重复性判断
	 * 
	 * @param propertyCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "inputCaseJudge")
    @ResponseBody
	public AjaxResponse<?> caseDetail(String propertyCode , ServletRequest request) {
		AjaxResponse<Object> response = new AjaxResponse<Object>();
		List<Object> listForShow = new ArrayList<Object>();
		String upName="";
		String downName="";
		try{
			if(propertyCode != null && !"".equals(propertyCode)){
				List<ToPropertyInfo> toPropertyInfoList = toPropertyInfoService.getPropertyInfoByPropertyCode(propertyCode);
				//大于0  说明有重复案件信息，需要给出提示
				if(toPropertyInfoList.size() > 0){
					for(int i=0 ;i < toPropertyInfoList.size(); i++){
						ToCase toCase = toCaseService.findToCaseByCaseCode(toPropertyInfoList.get(i).getCaseCode() == null ? "" : toPropertyInfoList.get(i).getCaseCode());
						ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(toPropertyInfoList.get(i).getCaseCode() == null ? "" : toPropertyInfoList.get(i).getCaseCode());
											
						if(toCase != null){
							listForShow.add(caseInfoChange(toCase))	;
						}
						if(toCaseInfo != null){
							listForShow.add(toCaseInfo)	;
						}	
						listForShow.add(toPropertyInfoList.get(i));
						List<TgGuestInfo> listGuest = tgGuestInfoService.findTgGuestInfoByCaseCode(toPropertyInfoList.get(i).getCaseCode() == null ? "" : toPropertyInfoList.get(i).getCaseCode());
						if(listGuest.size()>0){
							for(int k=0; k<listGuest.size();k++){
								if("30006001".equals(listGuest.get(k).getTransPosition())){								
									upName += listGuest.get(k).getGuestName()==null?"":listGuest.get(k).getGuestName();
									upName +=",";
								}
								if("30006002".equals(listGuest.get(k).getTransPosition())){								
									downName += listGuest.get(k).getGuestName()==null?"":listGuest.get(k).getGuestName();
									downName +=",";
								}
							}
						}
						upName = upName.substring(0, upName.length());
						downName = downName.substring(0, downName.length());
						listForShow.add(upName);
						listForShow.add(downName);
					}		
				}
			}	
		}catch(Exception e){
     		response.setSuccess(false);
     		response.setMessage(e.getMessage());	
     	}		
		response.setSuccess(true);
		response.setContent(listForShow);
		
		return response;
	}

	/**
	 * @author zhuody
	 * @date 2016-12-29
	 * 新建自录案件信息保存 及跳转
	 * */
	
	@RequestMapping("saveCaseInfo/{keyFlag}")
	public String saveCaseInfo(HttpServletRequest request,CaseMergeVo caseMergeVo,@PathVariable String keyFlag){
		
		Map<String, Object> map = new HashMap<String, Object>();
		ToCase toCase = new ToCase();
		ToCaseInfo toCaseInfo = new ToCaseInfo();
		ToPropertyInfo toPropertyInfo = new ToPropertyInfo();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
		String dateStr = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
		String month = dateStr.substring(0, 6);
		String caseCode = uamBasedataService.nextSeqVal("CASE_ZL_CODE", month);
		if(null == caseCode){
			throw new BusinessException("生成自录案件编号出现异常！");
		}
		
		int insertUp=0,insertDown=0,insertCase=0,insertCaseInfo=0;
		if(caseMergeVo != null){
			List<String>  nameUpList = caseMergeVo.getGuestNameUp();
			List<String>  namePhoneList = caseMergeVo.getGuestPhoneUp();
			List<String>  nameDownList = caseMergeVo.getGuestNameDown();
			List<String>  phoneDownList = caseMergeVo.getGuestPhoneDown();
			//插入上下家信息
			insertUp = insertIntoGuestInfo(nameUpList,namePhoneList,caseCode,1);
			insertDown = insertIntoGuestInfo(nameDownList,phoneDownList,caseCode,2);
			try {
				toPropertyInfo.setCaseCode(caseCode);
				toPropertyInfo.setPropertyCode(caseMergeVo.getPropertyCode() == null? "":caseMergeVo.getPropertyCode());
				toPropertyInfo.setPropertyAddr(caseMergeVo.getPropertyAddr() == null? "":caseMergeVo.getPropertyAddr());
				toPropertyInfo.setDistCode(caseMergeVo.getDistCode() == null? "":caseMergeVo.getDistCode());	
				toPropertyInfo.setPropertyType(caseMergeVo.getPropertyType() == null? "":caseMergeVo.getPropertyType());
				toPropertyInfo.setSquare(caseMergeVo.getSquare() == null ? 0.0: Double.valueOf(caseMergeVo.getSquare()));
				toPropertyInfo.setLocateFloor(caseMergeVo.getFloor() == null ? 0: caseMergeVo.getFloor());
				toPropertyInfo.setTotalFloor(caseMergeVo.getTotalFloor() == null ? 0:caseMergeVo.getTotalFloor());
				if(null != caseMergeVo.getFinishYear() && !"".equals(caseMergeVo.getFinishYear())){
					toPropertyInfo.setFinishYear(sdf.parse(caseMergeVo.getFinishYear()+"-01-01 00:00"));
				}				
				toPropertyInfoService.insertSelective(toPropertyInfo);
			} catch (ParseException e) {				
				e.printStackTrace();
			}			
		
			toCase.setCaseCode(caseCode);
			toCase.setCaseProperty(CasePropertyEnum.TPZJ.getCode());//自建案件
			toCase.setStatus(CaseStatusEnum.WFD.getCode());//未分单
			toCase.setCaseOrigin(CaseOriginEnum.INPUT.getCode());					
			insertCase = toCaseService.insertSelective(toCase);
			
			toCaseInfo.setCaseCode(caseCode);
			toCaseInfo.setAgentCode(caseMergeVo.getAgentCode() == null?"":caseMergeVo.getAgentCode());
			toCaseInfo.setAgentName(caseMergeVo.getAgentName()== null?"":caseMergeVo.getAgentName());
			toCaseInfo.setAgentPhone(caseMergeVo.getAgentPhone()== null?"":caseMergeVo.getAgentPhone());			
			toCaseInfo.setGrpName(caseMergeVo.getAgentOrgName()== null?"":caseMergeVo.getAgentOrgName());
			toCaseInfo.setTargetCode(caseMergeVo.getAgentOrgCode()== null?"":caseMergeVo.getAgentOrgCode());
			toCaseInfo.setIsResponsed("0");
			toCaseInfo.setImportTime(new Date());
			toCaseInfo.setRequireProcessorId(getManagerUserId(caseMergeVo.getAgentOrgCode()));//未分单之前 案件归到主管
			insertCaseInfo = toCaseInfoService.insertSelective(toCaseInfo);
			
			if(caseMergeVo.getAgentOrgId() != null && !"".equals(caseMergeVo.getAgentOrgId())){				
				map.put("caseCode", caseCode);
				map.put("orgId", caseMergeVo.getAgentOrgId());
				toCaseInfoService.updateCaseInfoByOrgId(map);
			}
		}		
		
		if(insertUp > 0 && insertDown > 0 && insertCase>0 && insertCaseInfo > 0){
			
			request.setAttribute("caseCode", caseCode);
			request.setAttribute("busFlag", "success");
		}
		
		//重新定向 防止submit重复提交数据
		if(!"".equals(keyFlag) && null != keyFlag){
			if("case".equals(keyFlag)){				
				return "redirect:/case/tracking?caseCode="+caseCode;
				//return  "/case/taskTracking2";				
			}else if("eloan".equals(keyFlag)){				
				return "redirect:/eloan/task/eloanApply/process";
				//return  "eloan/task/taskEloanList";							
			}else if("spv".equals(keyFlag)){
				return "redirect:/spv/saveHTML";
				//return  "spv/saveSpvCase";						
			}
		}
	   
		return  "case/mycase_list2";
	}
	
	/**
	 * @author zhuody
	 * @date 2016-12-29
	 * 新建自录案件 响应人默认设置为三级市场组织对应的主管
	 * */
	private String getManagerUserId(String grpCode) {		
		String userId ="";
		Map<String, Object> map = new HashMap<String, Object>();
		TsTeamScopeTarget tsTeamScopeTarget =  new TsTeamScopeTarget();
		if(null != grpCode && !"".equals(grpCode)){
			map.put("grpCode", grpCode);
			map.put("isResponseTeam", 1);			
			List<TsTeamScopeTarget> tsTeamScopeTargetList = tsTeamScopeTargetService.getTeamScopeTargetInfo(map);
			if(tsTeamScopeTargetList.size() > 1){
				//对应多个组别的情况不设置默认主管
				userId ="";
			}else if(tsTeamScopeTargetList.size() == 1){
				tsTeamScopeTarget = tsTeamScopeTargetList.get(0);
			}
			
			if( null != tsTeamScopeTarget ){
				String yuTeamCode = tsTeamScopeTarget.getYuTeamCode();
				if(null !=yuTeamCode && !"".equals(yuTeamCode)){
					Org org = uamUserOrgService.getOrgByCode(yuTeamCode);					
					if(null != org && null != org.getId() && !"".equals(org.getId())){
						User user = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(org.getId(),"Manager");
						if(null !=user){
							userId = user.getId()==null ? "":user.getId();
						}
					}					
				}				
			}
		}
	
		return userId;
	}
	
	/**
	 * @author zhuody
	 * @date 2016-12-26
	 * 新建自录案件 插入上下家信息
	 * */
	private int insertIntoGuestInfo(List<String> nameList, List<String> phoneList,String caseCode,int flag) {
		int k = 0;
		TgGuestInfo tgGuestInfo = new TgGuestInfo();
		
		if(nameList.size() > 0  && phoneList.size() > 0){
			if(flag==1){
				for(int i=0; i<nameList.size() ;i++){
					tgGuestInfo.setCaseCode(caseCode);
					tgGuestInfo.setGuestName(nameList.get(i));
					tgGuestInfo.setGuestPhone(phoneList.get(i));
					tgGuestInfo.setTransPosition("30006001");
					k = tgGuestInfoService.insertSelective(tgGuestInfo);
				}
			}else if(flag==2){
				for(int i=0; i<nameList.size() ;i++){
					tgGuestInfo.setCaseCode(caseCode);
					tgGuestInfo.setGuestName(nameList.get(i));
					tgGuestInfo.setGuestPhone(phoneList.get(i));
					tgGuestInfo.setTransPosition("30006002");
					k = tgGuestInfoService.insertSelective(tgGuestInfo);
				}
			}
		}		
		return k;
	}
	
	/**
	 * @author zhuody
	 * @date 2016-12-26
	 * 新建自录案件 案件信息中英文转换
	 * */
	private ToCase caseInfoChange(ToCase toCase) {
		if(toCase.getStatus() != null){
			if("30003001".equals(toCase.getStatus())){
				toCase.setStatus("无效案件");
			}else if("30003002".equals(toCase.getStatus())){
				toCase.setStatus("结案案件");			
			}else if("30003003".equals(toCase.getStatus())){
				toCase.setStatus("在途案件");			
			}else if("30003004".equals(toCase.getStatus())){
				toCase.setStatus("挂起案件");			
			}else if("30003005".equals(toCase.getStatus())){
				toCase.setStatus("爆单案件");			
			}else if("30003006".equals(toCase.getStatus())){
				toCase.setStatus("全部案件");			
			}else if("30003007".equals(toCase.getStatus())){
				toCase.setStatus("合流案件");			
			}
		}
		
		if(toCase.getCaseProperty() != null ){
			if("30001001".equals(toCase.getCaseProperty())){
				toCase.setStatus("未分单");
			}else if("30001002".equals(toCase.getCaseProperty())){
				toCase.setStatus("已分单");			
			}else if("30001003".equals(toCase.getCaseProperty())){
				toCase.setStatus("已签约");			
			}else if("30001004".equals(toCase.getCaseProperty())){
				toCase.setStatus("已过户");			
			}else if("30001005".equals(toCase.getCaseProperty())){
				toCase.setStatus("已领证");			
			}else if("30001006".equals(toCase.getCaseProperty())){
				toCase.setStatus("未指定");			
			}	
		}
		
		User user = uamUserOrgService.getUserById(toCase.getLeadingProcessId()== null ? "":toCase.getLeadingProcessId());
		if(user != null){
			toCase.setLeadingProcessId(user.getRealName() == null ? "":user.getRealName());
			toCase.setOrgId(user.getOrgName() == null ? "":user.getOrgName());
		}
		
		return toCase;
	}
	
	/**
	 * 自录案件过户之前判断是否合流
	 * @author zhuody 
	 * @date 2016-12-26
	 * 
	 */	
    @RequestMapping(value="mergeSearch")
    @ResponseBody
    public AjaxResponse<String> materialBorrowSave(String  caseCode){
    	
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	ToCase toCase = new ToCase();
     	try{
        	if(null != caseCode && !"".equals(caseCode)){
        		toCase = toCaseService.findToCaseByCaseCode(caseCode);
        		if(null != toCase){
        			response.setSuccess(true);
        			response.setContent(toCase.getCtmCode() == null ? "":toCase.getCtmCode());
        			response.setMessage(toCase.getCaseOrigin() == null ?"":toCase.getCaseOrigin());
        			
        		}else{
         			response.setSuccess(false);
         			response.setContent(null); 
        		}
        	}
     	}catch(Exception e){
     		response.setSuccess(false);
     		response.setMessage(e.getMessage());	
     	}
     	return response;
    }
	
	
	/**
	 * 案件记录
	 * @author hejf10
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "caseRecord")
	public String caseRecord(ServletRequest request) {
		SessionUser user = uamSessionService.getSessionUser();
		request.setAttribute("userid", user.getId());
		return "case/caseRecord";
	}
	/**
	 * 案件合流申请如果是未分单直接合流，其他状态只是在meger表中添加一行申请数据
	 * @author hejf10
	 * @param request
	 * @return
	 */
	@RequestMapping(value="mergeCase")
	@ResponseBody
	public AjaxResponse<?> mergeCase(CaseMergerParameter caseInfo, HttpServletRequest request){
		AjaxResponse<?> response = new AjaxResponse<>();
		try{
			toCaseService.mergeCase(caseInfo);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			String sOut = "";
	        StackTraceElement[] trace = e.getStackTrace();
	        for (StackTraceElement s : trace) {  sOut += "\tat " + s + "\r\n"; }
			/**response.setMessage(e.getMessage()+"异常："+sOut);**/
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}	
	/**
	 * 案件拆分
	 * @author hejf10
	 * @param request
	 * @return
	 */
	@RequestMapping(value="qfMergeCase")
	@ResponseBody
	public AjaxResponse<?> qfMergeCase(CaseMergerParameter caseInfo, HttpServletRequest request){
		AjaxResponse<?> response = new AjaxResponse<>();
		try{
			toCaseService.qfMergeCase(caseInfo);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			String sOut = "";
			StackTraceElement[] trace = e.getStackTrace();
			for (StackTraceElement s : trace) {  sOut += "\tat " + s + "\r\n"; }
			/**response.setMessage(e.getMessage()+"异常："+sOut);**/
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}	
	/**
	 * 案件合流
	 * @author hejf10
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updateMergeCase")
	@ResponseBody
	public AjaxResponse<?> updateMergeCase(CaseMergerParameter caseInfo, HttpServletRequest request){
		AjaxResponse<Boolean> response = new AjaxResponse<>();
		try{
			toCaseService.updateMergeCase(caseInfo);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			String sOut = "";
			StackTraceElement[] trace = e.getStackTrace();
			for (StackTraceElement s : trace) {  sOut += "\tat " + s + "\r\n"; }
			/**response.setMessage(e.getMessage()+"异常："+sOut);**/
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		if(StringUtils.equals(caseInfo.getType(), "1")){response.setContent(true);}
		if(StringUtils.equals(caseInfo.getType(), "0")){response.setContent(false);}
		return response;
	}	
}
