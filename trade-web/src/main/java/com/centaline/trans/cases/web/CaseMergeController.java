package com.centaline.trans.cases.web;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.CaseMergeService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseMergeVo;
import com.centaline.trans.common.entity.CaseMergerParameter;
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
import com.centaline.trans.spv.vo.SpvRecordedsVO;
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
	CaseMergeService caseMergeService;	
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
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	
	
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
		
		String dateStr = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
		String month = dateStr.substring(0, 6);
		
		String caseCode = uamBasedataService.nextSeqVal("CASE_ZL_CODE", month);
		if(null == caseCode){
			throw new BusinessException("生成自录案件编号异常！");
		}		
		try{
			caseMergeService.saveCaseInfo(request,caseMergeVo,caseCode);
		}catch(BusinessException e){
			throw new BusinessException("自录案件信息保存异常！");
		}
		//重新定向 防止submit重复提交数据
		if(!"".equals(keyFlag) && null != keyFlag){
			if("case".equals(keyFlag)){				
				return "redirect:/case/tracking?caseCode="+caseCode;						
			}else if("eloan".equals(keyFlag)){				
				return "redirect:/eloan/task/eloanApply/process";										
			}else if("spv".equals(keyFlag)){
				return "redirect:/spv/saveHTML";									
			}
		}
	   
		return  "case/mycase_list2";
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
	/**
	 * 新建外单页面跳转 
	 * @author hejf10
	 * @date 2017年4月21日13:27:19
	 * @param request
	 * @return
	 */
	@RequestMapping(value="addWdCase")
	@RequiresPermissions("TRADE.CASE.CASEDETAIL.EDITWDCASE")
	public String addWdCase(Model model, HttpServletRequest request) throws Exception{
		model.addAttribute("flag","add");
		toAccesoryListService.getAccesoryList(request, "AddWdCase");
		
		model.addAttribute("caseCode",getRandom());
		request.setAttribute("type", "add");
		
		return "case/addWdCase";
	}
	/**
	 * 新增流水页面跳转 
	 * @author hejf10
	 * @date 2017年4月26日14:22:15
	 * @param request
	 * @return
	 */
	@RequestMapping(value="addLiushui")
	@RequiresPermissions("TRADE.CASE.CASEDETAIL.EDITWDCASE")
	public String addLiushui(Model model, HttpServletRequest request,String caseCode) throws Exception{
		request.setAttribute("caseCode", caseCode);
		toAccesoryListService.getAccesoryList(request, "AddLiushui");
		caseMergeService.setCaseAttribute(caseCode,request);
		model.addAttribute("caseCode_",getRandom());
		return "case/addLiushui";
	}
	/**
	 * 修改外单页面跳转 
	 * @author hejf10
	 * @date 2017年4月27日10:07:20
	 * @param request
	 * @return
	 */
	@RequestMapping(value="editWdCase")
	@RequiresPermissions("TRADE.CASE.CASEDETAIL.EDITWDCASE")
	public String editWdCase(Model model, HttpServletRequest request,String caseCode) throws Exception{
		toAccesoryListService.getAccesoryList(request, "AddWdCase");
		caseMergeService.setCaseMergeVo(request, caseCode);
		request.setAttribute("type", "edit");
		return "case/addWdCase";
	}

	/**
	 * 新建外单
	 * @author hejf10
	 * @date 2017年4月26日15:02:27
	 * @param caseMergeVo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveWdCaseInfo")
	@RequiresPermissions("TRADE.CASE.CASEDETAIL.EDITWDCASE")
	@ResponseBody
	public AjaxResponse<?>  saveWdCaseInfo(CaseMergeVo caseMergeVo,HttpServletRequest request){
		AjaxResponse<?> response = new AjaxResponse<>();
		String caseCode = null;
		try{
			caseCode = caseMergeService.saveWdCaseInfo(request,caseMergeVo);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	/**
	 * 更新外单
	 * @author hejf10
	 * @date 2017年4月26日15:02:27
	 * @param caseMergeVo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editWdCaseInfo")
	@RequiresPermissions("TRADE.CASE.CASEDETAIL.EDITWDCASE")
	@ResponseBody
	public AjaxResponse<?>  editWdCaseInfo(CaseMergeVo caseMergeVo,HttpServletRequest request){
		AjaxResponse<?> response = new AjaxResponse<>();
		String caseCode = null;
		try{
			caseCode = caseMergeService.editWdCaseInfo(request,caseMergeVo);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	/**
	 * 保存流水
	 * @author hejf10
	 * @date 2017年4月26日15:02:27
	 * @param caseMergeVo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveLiushui")
	@RequiresPermissions("TRADE.CASE.CASEDETAIL.EDITWDCASE")
	@ResponseBody
	public AjaxResponse<?>  saveLiushui(CaseMergeVo caseMergeVo,HttpServletRequest request){
		AjaxResponse<?> response = new AjaxResponse<>();
		String caseCode = null;
		try{
			caseCode = caseMergeService.saveLiushui(request,caseMergeVo);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 返来日期时间的一个字符串
	 * @author hejf10
	 * @date 2017年5月17日10:52:06
	 * @return String
	 */
	public String getRandom(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");  
		java.util.Date date=new java.util.Date();  
		String str=sdf.format(date);  
		return str;
	}
}
