package com.centaline.trans.cases.web;



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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.CaseMergerParameter;
import com.centaline.trans.cases.vo.CaseMergeVo;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;

import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.property.service.ToPropertyService;
import com.centaline.trans.spv.service.ToSpvService;
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

	@RequestMapping(value="addCase")
	public String caseForChange(Model model, ServletRequest request){
		
		return "case/addCase";
	}	
	
	/**
	 * 自录案件 重复性判断
	 * 
	 * @param caseId
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
		//request.setAttribute("list", listForShow);
		
		response.setSuccess(true);
		response.setContent(listForShow);
		
		return response;
	}

	
	//自录案件信息保存
	@RequestMapping("saveCaseInfo")
	public String saveCaseInfo(HttpServletRequest request,CaseMergeVo caseMergeVo){
		Map<String, Object> map = new HashMap<String, Object>();
		ToCase toCase = new ToCase();
		ToCaseInfo toCaseInfo = new ToCaseInfo();
		String dateStr = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
		String month = dateStr.substring(0, 6);
		String caseCode = uamBasedataService.nextSeqVal("CASE_ZL_CODE", month);
		
		if(caseMergeVo != null){
			List<String>  nameUpList = caseMergeVo.getGuestNameUp();
			List<String>  namePhoneList = caseMergeVo.getGuestPhoneUp();
			List<String>  nameDownList = caseMergeVo.getGuestNameDown();
			List<String>  phoneDownList = caseMergeVo.getGuestPhoneDown();
			//插入上下家信息
			insertIntoGuestInfo(nameUpList,namePhoneList,caseCode,1);
			insertIntoGuestInfo(nameDownList,phoneDownList,caseCode,2);
			
			
			//TODO
			toCase.setCaseCode(caseCode);
			toCase.setCaseProperty("30003008");//自建案件
			toCase.setStatus("30001001");//未分单
			//toCase.setCaseOrigin(CaseOriginEnum.INPUT.getCode());
			toCase.setCaseOrigin("INPUT");			
			toCaseService.insertSelective(toCase);
			
			//TODO
			toCaseInfo.setCaseCode(caseCode);
			toCaseInfo.setAgentCode(caseMergeVo.getAgentCode());
			toCaseInfo.setAgentName(caseMergeVo.getAgentName());
			toCaseInfo.setAgentPhone(caseMergeVo.getAgentPhone());			
			toCaseInfo.setGrpName(caseMergeVo.getAgentOrgName());
			toCaseInfo.setTargetCode(caseMergeVo.getAgentOrgCode());
			toCaseInfoService.insertSelective(toCaseInfo);
			
			if(caseMergeVo.getAgentOrgId() != null && !"".equals(caseMergeVo.getAgentOrgId())){
				
				map.put("caseCode", caseCode);
				map.put("orgId", caseMergeVo.getAgentOrgId());
				toCaseInfoService.updateCaseInfoByOrgId(map);
			}
		}		
		
		return "case/mycase_list2";
	}
	
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
	 * 案件记录
	 * @author hejf10
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "caseRecord")
	public String caseRecord(ServletRequest request) {
		SessionUser user = uamSessionService.getSessionUser();
		request.setAttribute("orgid", user.getServiceDepId());
		return "case/caseRecord";
	}
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
			response.setMessage(e.getMessage()+"异常："+sOut);
			e.printStackTrace();
		}
		return response;
	}	
	
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
			response.setMessage(e.getMessage()+"异常："+sOut);
			e.printStackTrace();
		}
		if(StringUtils.equals(caseInfo.getType(), "1")){response.setContent(true);}
		if(StringUtils.equals(caseInfo.getType(), "0")){response.setContent(false);}
		return response;
	}	
}