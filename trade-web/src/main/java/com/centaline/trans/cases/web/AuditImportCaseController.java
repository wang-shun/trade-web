package com.centaline.trans.cases.web;



import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.api.service.FlowApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import com.centaline.trans.common.enums.CcaiTaskEnum;
import com.centaline.trans.engine.bean.RestVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.cases.service.AuditCaseService;
import com.centaline.trans.cases.service.CaseMergeService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToCcaiAttachment;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.common.vo.FileUploadVO;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.workspace.entity.CacheGridParam;

/**
 * @author xiefei1
 * @since 2017年8月29日 下午6:26:12 
 * @description 
 */
@Controller
@RequestMapping(value = "/AuditImportCase")
public class AuditImportCaseController {
	
	@Autowired
	private ToCaseService toCaseService;
	
	@Autowired
	private QuickGridService quickGridService;

	@Autowired
	private AuditCaseService auditCaseService;
	
	@Autowired
	private ToCaseParticipantMapper toCaseParticipantMapper;

	@Autowired(required = true)
	UamSessionService uamSessionService;
	
	@Autowired
	private UamPermissionService uamPermissionService;
	
	@Autowired
	private UamUserOrgService uamUserOrgServiceClient;

	@Autowired
	private WorkFlowManager workFlowManager;
	//CCAI交互服务
	@Autowired
	private FlowApiService flowApiService;

	
	/**
	 * 
	 * @since:2017年9月8日 下午5:37:48
	 * @description:
	 * @author:xiefei1
	 * @param caseCode
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "addLoanProcessor")
	public String addLoanProcessor(HttpServletRequest request, String caseCode,String loanProcessor) {
		int addLoanProcessor = auditCaseService.addLoanProcessor(loanProcessor, caseCode);
		if(addLoanProcessor==0){
			throw new BusinessException("请求页面跳转异常啦，请稍后再试！");
		}
		return "forward:"+"/AuditImportCase/list";
	}

	
	/**
	 * 
	 * @since:2017年9月1日 下午3:58:30
	 * @description:接单审核通过后 修改状态，返回接单列表页面；
	 * @author:xiefei1
	 * @return	
	 * WJD("30001001", "未接单"),
	 * YJD("30001002", "已接单"),
	 * 
	 */
	/*@RequestMapping(value = "auditSuccess")
	public String AuditSuccess(String caseCode){
		SessionUser user = uamSessionService.getSessionUser();
		FlowFeedBack info = new FlowFeedBack(user, CcaiFlowResultEnum.SUCCESS,user.getRealName());
		//先通知CCAI 返回结果为true再更新案件状态
		ApiResultData result = flowApiService.tradeFeedBackCcai(caseCode, CcaiTaskEnum.TRADE_WARRANT_MANAGER,info);
		if(result.isSuccess()){
			ToCase toCase = new ToCase();
			toCase.setStatus(CaseStatusEnum.YJD.getCode());
			//驳回状态 驳回使用
			// toCase.setStatus(CaseStatusEnum.BHCCAI.getCode());
			toCase.setCaseCode(caseCode);
			toCaseService.updateByCaseCodeSelective(toCase);
			//auditCaseService.
			//调用流程引擎 设置网关判断参数 完成环节 by:yinchao 2017-9-26
			// List<RestVariable> variables = new ArrayList<>();
			// variables.add(new RestVariable("caseApprove",true));
			// //驳回使用下面的设置参数
			// variables.add(new RestVariable("caseApprove",false));
		}
		return "forward:"+"/AuditImportCase/list";		
	}*/
	@RequestMapping(value = "auditSuccess")
	public String AuditSuccess(String caseCode){
		if(auditCaseService.updateAuditCaseSuccess(caseCode)==1){
			return "forward:"+"/AuditImportCase/list";			
		}else{
			throw new BusinessException("审核案件通过失败！");
		}
	}
	/**
	 * 
	 * @since:2017年8月31日 上午11:43:32
	 * @description:手写异步查询附件的方法
	 * @author:xiefei1
	 * @param caseCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "attachmentList")
	@ResponseBody
	public HashMap<String, Object> attachmentList(String caseCode,Model model,Integer page,Integer rows){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		JQGridParam gp = new CacheGridParam();
		gp.setQueryId("getCcaiAttachmentListByCaseCode");
		gp.setPagination(true);
		gp.setPage(page);
		gp.setRows(rows);
		gp.put("caseCode", caseCode);
		Page<Map<String, Object>> pageList = quickGridService.findPageWithOutSessionUser(gp);
		List<Map<String, Object>> content = pageList.getContent();
		for (Map<String, Object> map : content) {
			Date uploadTime = (Date)map.get("UPLOAD_TIME");	
			if(uploadTime!=null){
			map.put("UPLOAD_TIME", dateFormat.format(uploadTime));
			}
		}
		HashMap<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("content", content);
		resultMap.put("totalElements", pageList.getTotalElements());
		resultMap.put("totalPages", pageList.getTotalPages());
		return resultMap;
		
	}
	/**
	 * 
	 * @since:2017年8月31日 上午10:52:56
	 * @description:跳转到案件审核详情页面,查询出当前权证经理下面的所有用户供其选择贷款专员
	 * @author:xiefei1
	 * @return
	 */
	@RequestMapping(value = "details")
	public String auditCaseDetails(String caseCode,Model model){
//		测试时用这个
//		List<User> loanUserList = uamUserOrgServiceClient.getUserByBelongOrgId("A05D3E9C1ED343118F2286EC7E3D2637");
//		查询出当前权证经理下面的所有用户供其选择贷款专员
		SessionUser sessionUser = uamSessionService.getSessionUser();
		String username = sessionUser.getUsername();
		User userByUsername = uamUserOrgServiceClient.getUserByUsername(username);
		List<User> loanUserList = uamUserOrgServiceClient.getUserByBelongOrgId(userByUsername.getOrgId());
		String payType = auditCaseService.getPayType(caseCode);
		model.addAttribute("payType", payType);
		model.addAttribute("caseCode", caseCode);
		model.addAttribute("loanUserList", loanUserList);
		ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
		auditCaseService.getLeaderUserName(toCaseParticipant);		
		return "case/auditCaseDetails";
		
	}
	/**
	 * 
	 * @since:2017年8月29日 下午7:54:24
	 * @description: 审核案件列表页面
	 * @author:xiefei1
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list")
	public String AuditCaseList(Integer month,Model model,ServletRequest request){	
		if(month==null||month==0){
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int computorMonth = c.get(Calendar.MONTH);
			// 这里使用非计算机月份,因为sqlserver的month()算出来是computorMonth + 1，比如说8月算出来就是8
			month = computorMonth + 1;			
		}
			model.addAttribute("month", month);
			//用于计算接单数统计（未接单数与已接单数比）
			JQGridParam gp = new JQGridParam("getValidCaseCount", false);
			gp.put("month", month);
			Page<Map<String, Object>> page = quickGridService.findPageWithOutSessionUser(gp);
			
			
			List<Map<String, Object>> caseCountList = page.getContent();
			for (Map<String, Object> map : caseCountList) {
				Set<String> keySet = map.keySet();
				for (String key : keySet) {
					int countValue = (int)map.get(key);
					//查询 出来的是已接单VALIDCOUNT 和   未接单数INVALIDCOUNT
					model.addAttribute(key, countValue);
				}
			}
			model.addAttribute("caseCountList", caseCountList);
			
			App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
			String ctx = app.genAbsoluteUrl();
			request.setAttribute("ctx", ctx);
		return "case/auditCaseList";
	}

	/**
	 * 
	 * @since:2017年8月29日 下午6:30:37
	 * @description:审核未接单的列表
	 * @author:xiefei1
	 * @param model
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "listData")
	@ResponseBody
	public HashMap list(Integer month,Integer page,Integer rows) {
		if(page==null||page==0){
			page=1;
		}
		if(rows==null||rows==0){
			rows=5;
		}
		if (month == null || month == 0) {
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int computorMonth = c.get(Calendar.MONTH);
			// 这里使用非计算机月份
			month = computorMonth + 1;
		}
		JQGridParam gp = new CacheGridParam();
		gp.setQueryId("getAuditCaseList");
		gp.setPagination(true);
		gp.setPage(page);
		gp.setRows(rows);
		gp.put("month", month);
		Page<Map<String, Object>> pageList = quickGridService.findPageWithOutSessionUser(gp);
		
		long totalElements=pageList.getTotalElements();
		long totalPages=pageList.getTotalPages();

		// 先查出还未接单的案件
		// 导入的还未接单的案件清单
		List<Map<String, Object>> importCaseList = pageList.getContent();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (Map<String, Object> caseMap : importCaseList) {			
			String caseCode = (String) caseMap.get("CASE_CODE");
			if (caseCode != null && caseCode != "") {
				// 再根据caseCode查出对应的人员参与者进行封装
				List<ToCaseParticipant> participants = toCaseParticipantMapper.selectByCaseCode(caseCode);
				// 遍历participants，把每个participant放入到每一个还未接单的案件中
				for (ToCaseParticipant toCaseParticipant : participants) {
					if (toCaseParticipant.getPosition() != null && toCaseParticipant.getPosition() != "") {
						caseMap.put(toCaseParticipant.getPosition(), toCaseParticipant.getRealName());
					}
				}
			}
			//转换时间格式成字符串
			Date importTime = (Date)caseMap.get("IMPORT_TIME");
			String strTime = dateFormat.format(importTime);
			caseMap.put("IMPORT_TIME", strTime);
		}
		HashMap<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("content", importCaseList);
		resultMap.put("totalElements", totalElements);
		resultMap.put("totalPages", totalPages);
		return resultMap;
	}
	
	
	
	
}
