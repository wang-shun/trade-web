package com.centaline.api.ccai.cases.web.v1;

import com.aist.common.exception.BusinessException;
import com.centaline.api.ccai.cases.service.CcaiService;
import com.centaline.api.ccai.cases.vo.CcaiImportCase;
import com.centaline.api.ccai.cases.vo.CcaiImportCaseGuest;
import com.centaline.api.ccai.cases.vo.CcaiImportCaseInfo;
import com.centaline.api.enums.CaseSyncParticipantEnum;
import com.centaline.trans.apilog.service.ApiLogService;
import com.centaline.trans.common.vo.CcaiServiceResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 案件同步相关Controller
 * 
 * 返回json的数据接口 请求路径结尾需要加json
 * 否则会不转换JSON，返回406错误
 * @author yinchao
 *
 */
@RestController
@RequestMapping(value="/api/ccai/v1")
public class CaseSynctController {
	private Logger logger = LoggerFactory.getLogger(CaseSynctController.class);
	//记录日志 模块类型
	private static final String SYNC_MODULE = "CASESYNC";//案件新增同步
	private static final String UPDATE_MODULE = "CASEUPDATE";//案件修改同步
	//修改案件信息类型
	private static final String UPDATE_TYPE_NORMAL = "normal";//普通类型 只修改可修改的信息 不做其他处理
	private static final String UPDATE_TYPE_FLOW = "flow";//流程相关修改 修改信息后，会触发流程的变动
	
	@Autowired
	private CcaiService ccaiService;
	
	@Autowired
	private ApiLogService apiLogService;
	
	/**
	 * ccai导入案件接口
	 * @param acase json格式案件信息(由Spring MVC 自动转换成对象)
	 * @return
	 */
	@RequestMapping(value="/caseSync.json",method=RequestMethod.POST)
	public CcaiServiceResult caseImport(@Valid @RequestBody CcaiImportCase acase, Errors errors) {
		CcaiServiceResult result = new CcaiServiceResult();
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder msg = new StringBuilder();
		if(errors.hasErrors()){
			for(ObjectError err:errors.getAllErrors()){
				msg.append(err.getDefaultMessage());
				msg.append("\r\n");
			}
			result.setSuccess(false);
			result.setMessage(msg.toString());
			result.setCode("99");
		}else{
			//做其他的校验
			result = validateData(acase);
			//校验通过 进行数据同步
			if(result.isSuccess()){
				try {
					result = ccaiService.importCase(acase);//数据同步
				} catch (BusinessException e) {
					result.setSuccess(false);
					result.setCode("99");
					result.setMessage(e.getMessage());
				}
			}
		}
		try {
			String data = mapper.writeValueAsString(acase);
			logger.debug("sync get data:"+data);
			apiLogService.apiLog(SYNC_MODULE, "/api/ccai/v1/caseSync.json", data, mapper.writeValueAsString(result)
					, result.isSuccess()?"0":"1", SecurityUtils.getSubject().getSession().getHost());
		} catch (JsonProcessingException e) {}
		return result;
	}
	
	/**
	 * ccai修改案件信息接口
	 * type 为normal 则为普通的修改
	 * type 为flow 则触发流程到权证审批
	 * @param acase json格式案件信息(由Spring MVC 自动转换成对象)
	 * @return
	 */
	@RequestMapping(value="/caseUpdate/{type}.json",method=RequestMethod.POST)
	@ResponseBody
	public CcaiServiceResult caseUpdate(@PathVariable String type,@RequestBody CcaiImportCase acase) {
		CcaiServiceResult result = new CcaiServiceResult();
		ObjectMapper mapper = new ObjectMapper();
		if(acase==null||StringUtils.isBlank(acase.getCcaiCode())){
			result.setSuccess(false);
			result.setCode("99");
			result.setMessage("未获取到要修改的案件信息或成交报告编号!");
		}else{
			try {
				if(UPDATE_TYPE_NORMAL.equals(type)){
					//普通修改同步案件信息
					result = ccaiService.updateCase(acase);
				}else if(UPDATE_TYPE_FLOW.equals(type)){
					//修改案件信息 同时触发流程到权证经理审核环节
					result = ccaiService.updateCaseAndFlow(acase);
				}else{
					result.setSuccess(false);
					result.setCode("99");
					result.setMessage("无法识别的修改类型!");
				}
			} catch (BusinessException e) {
				result.setSuccess(false);
				result.setCode("99");
				result.setMessage(e.getMessage());
			}
		}
		try {
			String data = mapper.writeValueAsString(acase);
			logger.debug("sync get data:"+data);
			apiLogService.apiLog(UPDATE_MODULE, "/api/ccai/v1/caseUpdate/"+type+".json", data, mapper.writeValueAsString(result)
					, result.isSuccess()?"0":"1", SecurityUtils.getSubject().getSession().getHost());
		} catch (JsonProcessingException e) {}
		return result;
	}
	
	/**
	 * 校验导入的案件信息是否准
	 * @param acase
	 * @return
	 */
	private CcaiServiceResult validateData(CcaiImportCase acase){
		CcaiServiceResult result = new CcaiServiceResult();
		if(acase == null){
			result.setSuccess(false);
			result.setCode("99");
			result.setMessage("数据格式不正确");
		}else{
			StringBuilder msg = new StringBuilder();//拼接错误信息
			//基本信息校验
			cityCodeCheck(acase.getCity(),msg,"城市编码不正确");
			if(ccaiService.isExistCcaiCode(acase.getCcaiCode())){
				msg.append("成交报告[")
						.append(acase.getCcaiCode())
						.append("]编号已存在!\r\n");
			}
			boolean hasAgent = false,hasWarrant = false,hasSecretary=false;
			if(acase.getParticipants()==null || acase.getParticipants().size()<2){
				msg.append("案件参与人信息不完整，请查证!\r\n");
			}else{
				//案件参与人校验
				for(CcaiImportCaseInfo pa : acase.getParticipants()){
					if(CaseSyncParticipantEnum.AGENT.getCode().equals(pa.getPosition())){
						//经纪人校验
						participantCheck(pa,msg,"经纪人");
						hasAgent = true;
					}else if(CaseSyncParticipantEnum.WARRANT.getCode().equals(pa.getPosition())){
						//过户权证校验
						participantCheck(pa,msg,"过户权证");
						hasWarrant = true;
					}else if(CaseSyncParticipantEnum.SECRETARY.getCode().equals(pa.getPosition())){
						//秘书校验
						participantCheck(pa,msg,"秘书");
						hasSecretary = true;
					}
				}
				if(!hasAgent || !hasWarrant || !hasSecretary){
					msg.append("至少要有一个经纪人和过户权证和秘书信息!\r\n");
				}
			}
			
			//房源信息校验
			checkBlank(acase.getProperty(),"id","房源ID不能为空",msg);
			checkBlank(acase.getProperty(),"code","房源编码不能为空",msg);
			checkBlank(acase.getProperty(),"district","所属区域不能为空",msg);
			cityCodeCheck(acase.getProperty().getDistrict(),msg,"房源所属区域编码不正确");
			hasAgent = false;//业主
			hasWarrant = false;//买家
			//客户信息校验
			for(CcaiImportCaseGuest guest : acase.getGuests()){
				if("30006001".equals(guest.getPosition())){
					//业主
					hasAgent = true;
				}else if("30006002".equals(guest.getPosition())){
					//买家
					hasWarrant = true;
				}
				checkBlank(guest,"id","客户ID不能为空",msg);
				checkBlank(guest,"name","客户名称不能为空",msg);
				checkBlank(guest,"mobile","客户手机不能为空",msg);
				mobileCheck(guest.getMobile(),msg,"客户手机号不正确，请检查!");
			}
			if(!hasAgent || !hasWarrant){
				msg.append("至少要有一个买家和业主信息!\r\n");
			}
			if(msg.length()>0){
				result.setSuccess(false);
				result.setCode("99");
				result.setMessage(msg.toString());
			}else{
				result.setSuccess(true);
				result.setCode("00");
			}
		}
		return result;
	}
	/**
	 * 检查对象指定属性是否为空
	 * @param bean 检查的对象
	 * @param property 属性
	 * @param message 为空拼接信息
	 * @param builder 信息构造器
	 * @return
	 */
	private void checkBlank(Object bean,String property,String message,StringBuilder builder){
		try {
			if(bean == null ) {
				builder.append(message);
				builder.append("\r\n");
				logger.debug("property:"+property+"--empty-"+message);
			}else{
				String value = BeanUtils.getProperty(bean, property);
				if(StringUtils.isBlank(value)){
					builder.append(message);
					builder.append("\r\n");
					logger.debug("property:"+property+"--empty-"+message);
				}
			}
		} catch (Exception e) {
			logger.error("bean util get property error",e);
		}
	}
	/**
	 * 参与人校验
	 * @param pa
	 * @param msg
	 * @param position
	 */
	private void participantCheck(CcaiImportCaseInfo pa,StringBuilder msg,String position){
		checkBlank(pa,"userName",position+"域账号不能为空",msg);
		checkBlank(pa,"realName",position+"名称不能为空",msg);
//		checkBlank(pa,"mobile",position+"手机号不能为空",msg); 取消手机号必填选项
		if(StringUtils.isNotBlank(pa.getMobile())){
			mobileCheck(pa.getMobile(),msg,position+"手机号不正确，请检查!");
		}
		//秘书部检查部门及主管信息
		if(!CaseSyncParticipantEnum.SECRETARY.getCode().equals(pa.getPosition())){
			checkBlank(pa,"grpCode",position+"部门HROC不能为空",msg);
			checkBlank(pa,"grpName",position+"部门名称不能为空",msg);
			checkBlank(pa,"grpMgrUserName",position+"部门主管域账号不能为空",msg);
		}
	}
	/**
	 * 手机号码检查
	 * @param mobile
	 * @param msg
	 * @param message
	 */
	private void mobileCheck(String mobile,StringBuilder msg,String message){
		if(StringUtils.isBlank(mobile) || mobile.length()!=11 || !StringUtils.isNumeric(mobile)){
			msg.append(message);
			msg.append("\r\n");
		}
	}
	/**
	 * 城市编码 行政区划校验规则
	 * @param code
	 * @param msg
	 * @param message
	 */
	private void cityCodeCheck(String code,StringBuilder msg,String message){
		if(StringUtils.isBlank(code) || code.length()!=6 || !StringUtils.isNumeric(code)){
			msg.append(message);
			msg.append("\r\n");
		}
	}
	
}