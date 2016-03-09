package com.centaline.trans.cases.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.service.CasenewMessageService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseGuwenVo;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.spv.entity.ToCashFlow;
import com.centaline.trans.spv.service.ToCashFlowService;

import org.json.JSONException;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Controller
@RequestMapping(value="/api/ctm")
public class CasenewMessageController {
	
	@Autowired
	private CasenewMessageService casenewmessageService;

	@Autowired(required=true)
	private UamSessionService sessionService;
	
	@Autowired(required=true)
    private UamTemplateService uamTemplateService;

	@Qualifier("uamMessageServiceClient")
    @Autowired
    private UamMessageService uamMessageService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Autowired
	private ToCashFlowService tocashFlowService;
	
	@Autowired
	private ToCaseInfoService tocaseInfoService;
	
	
	/**
	 * 功能：通过请求url 获取到 caseCode
	 * @author zhangxb16
	 */
	public String apiCaseCode() throws HttpException, IOException, JSONException {
		
		String result=null;
		String url = "http://10.4.2.56/EDT/api/NumberService/GetYcNo?CompanyID=ZY&BusinessType=AJ&LockUserID=&format=json";
		try {
			 HttpClient client = new HttpClient();
			 GetMethod get = new GetMethod(url);
			 get.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			 get.getParams().setContentCharset("utf-8");
			 
			 // 发送http请求
			 String respStr = "";
			 client.executeMethod(get);
			 respStr = get.getResponseBodyAsString();
			 org.json.JSONObject json = new org.json.JSONObject(respStr);
			 result=json.getJSONObject("Result").getString("YcNumber");  // 得到caseCode
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return result;
	}
	
	
	/**
	 * 功能：生成caseCode 编号, 规则为 SDyyyyMMddHHmmss
	 * 描述：当调用 apiCaseCode() 方法失败时, 就调用此方法[generateCode()]
	 * @author zhangxb16
	 */
	public String generateCode(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); // 年月日 时分秒 SDyyyymmddhhmmss
		String caseCodeGenerate="SD"+sdf.format(new Date());
		return caseCodeGenerate;
	}
	
	
	/**
	 * 功能：新建案件信息推送
	 * @param ctmCode  案件编号
	 * @param agentCode  成交经纪人编号
	 * @param tgGuestInfo  客户[对象]
	 * @param propertyAddr  物业地址
	 * @param propetyCode  房屋编码
	 * @param requireProcessorId  请求处理人编号[交易顾问ID]
	 * @author zhangxb16
	 */
	@RequestMapping(value="case", method={RequestMethod.POST})
	@ResponseBody
	public String newCaseMessage(HttpServletRequest request, HttpServletResponse response, Model model, 
			String ctm_case_code, String agent_id, String agent_name, String guestInfoList, String property_address, 
			String property_code, String property_agent_id, String consult_id, String grp_code, String grp_name) throws HttpException, 
			IOException, JSONException{
		
		ResultNew rs=new ResultNew();
		
		Gson gson = new Gson();
		// 将 json 字符串转为 list
		List<TgGuestInfo> guestList=gson.fromJson(guestInfoList,new TypeToken<List<TgGuestInfo>>(){}.getType());
      	
		// 验证非空[案件编号, 成交经纪人编号, 物业地址, 房屋编码, 客户编号, 姓名, 电话]

		List<String> validateMsg = new ArrayList<>();
		
		if("".equals(ctm_case_code) || null==ctm_case_code){  // 案件编号
			validateMsg.add("案件编号为必填字段！");
		}
		if("".equals(agent_id) || null==agent_id){  // 成交经纪人编号
			validateMsg.add("成交经纪人编号为必填字段！");
		}
		if("".equals(agent_name) || null==agent_name){  // 成交经纪人姓名
			validateMsg.add("成交经纪人姓名为必填字段！");
		}
		if("".equals(property_address) || null==property_address){  // 物业地址
			validateMsg.add("物业地址为必填字段！");
		}
		if("".equals(property_code) || null==property_code){  // 房屋编码
			validateMsg.add("房屋编码为必填字段！");
		}
		if("".equals(grp_code) || null==grp_code){  // 组别编号
			validateMsg.add("组别编号为必填字段！");
		}
		if("".equals(grp_name) || null==grp_name){  // 组别名称
			validateMsg.add("组别名称为必填字段！");
		}
		
		for(int i=0; i<guestList.size(); i++){
			if("".equals(guestList.get(i).getGuestName()) || null==guestList.get(i).getGuestName()){  // 姓名
				validateMsg.add("客户姓名为必填字段！");
			}
			if("".equals(guestList.get(i).getTransPosition().toString()) || null==guestList.get(i).getTransPosition().toString()){  // 上下家属性
				validateMsg.add("上下家属性为必填字段！");
			}
			/*if("".equals(guestList.get(i).getGuestPhone()) || null==guestList.get(i).getGuestPhone()){  // 电话
				validateMsg.add("客户电话为必填字段！");
			}*/
		}
		
		if(!validateMsg.isEmpty()){
			String errorMsg = StringUtils.join(validateMsg, "/");
			rs.setStatus("-1");
			rs.setMessage(errorMsg);
			return JSONObject.toJSONString(rs);
		}

		// 判断ctm 推送过来的编号数据是否已经存在
		int existcasecode=tocaseInfoService.isExistCaseCode(ctm_case_code);
		if(existcasecode>0){
			rs.setStatus("-1");
			rs.setMessage("该ctm_case_code已经存在！");
			return JSONObject.toJSONString(rs);
		}else{
			try{
				String caseCode=apiCaseCode();  // 调用caseCode 的接口方法
				if(null==caseCode || "".equals(caseCode)){ // 如果调用apiCaseCode()方式返回的是空, 则调用本地的 generateCode() 方法
					caseCode=generateCode();  // 调用我们自己的生成规则
				}
				String status = casenewmessageService.insertCasenewMsg(ctm_case_code, agent_id, agent_name, guestList, property_address, property_code, property_agent_id, consult_id, grp_code, grp_name, caseCode);
				if("0".equals(status)){  // -1：接收异常，0：接收正常
					rs.setStatus(status);
					rs.setCode(status);
					rs.setMessage("");
				}else{
					String message="新增案件失败,请刷新后再次尝试！";
					rs.setStatus(status);  // -1：接收异常，0：接收正常
					rs.setCode(status);
					rs.setMessage(message);
				}
			}catch(BusinessException ex){
				rs.setStatus("-1");
				rs.setMessage(ex.getMessage());
				return JSONObject.toJSONString(rs);
			}
		}
		
		return JSONObject.toJSONString(rs);
	}
	
	
	/**
	 * 功能：交易顾问查询
	 * 描述：根据 经纪人ID为条件去查询  guwenID[顾问ID] 和 guwenName[顾问姓名]
	 * @param agentID [经纪人ID]
	 * @author zhangxb16
	 */
	@RequestMapping(value="guwen", method={RequestMethod.POST})
	@ResponseBody
	public String tradeGuwen(HttpServletRequest request, HttpServletResponse response, Model model, String agentID){
		
		List<CaseGuwenVo> guwenvoList=null;
		try{
			if("".equals(agentID) || null==agentID){
				throw new BusinessException("经纪人id为必填字段！");
			}
			
			ResultGuwen rs = new ResultGuwen();
			guwenvoList=casenewmessageService.selectConsultInfo(agentID);
			if(null != guwenvoList){
				String data;
				
				if(null==guwenvoList || guwenvoList.size()==0){
					data="[]";
					rs.setResult(data);
					rs.setStatus("-1");
					rs.setCode("-1");
					rs.setMessage("");
				}else{
					rs.setResult(guwenvoList);
					rs.setStatus("0");
					rs.setCode("0");
					rs.setMessage("");
				}
				return JSONObject.toJSONString(rs);
			}else{
				throw new BusinessException("根据该经纪人ID未查询到结果！");
			}
		}catch(BusinessException ex){
			ex.printStackTrace();
			throw new BusinessException("查询顾问信息失败, 请刷新后再次尝试！");
		}
	}
	
	
	/**
	 * 功能：顾问列表调用方式判断
	 * @author zhangxb16
	 */
	@RequestMapping(value="guwenListJudge", method={RequestMethod.POST})
	@ResponseBody
	public String guwenList(HttpServletRequest request, HttpServletResponse response, Model model, String agentID){
		
		try{
			if("".equals(agentID) || null == agentID){
				throw new BusinessException("经纪人ID为必填字段！");
			}
		}catch(BusinessException ex){
			ex.printStackTrace();
		}
		
		String result="0"; // 0：本地；1：交易系统
		ResultJudge rs = new ResultJudge();
		try{
			result = casenewmessageService.guwenList(agentID);
			if("0".equals(result)){
				rs.setResult(result);
				rs.setStatus("0");  // -1：接收异常，0：接收正常
				rs.setCode("0");
				rs.setMessage("");
			}else if("1".equals(result)){
				rs.setResult(result);
				rs.setStatus("0");
				rs.setCode("0");
				rs.setMessage("");
			}else{
				rs.setResult("");
				rs.setStatus("-1");
				rs.setCode("-1");
				rs.setMessage("查询失败,请刷新后再次尝试！");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}

		return JSONObject.toJSONString(rs);
	}
	
	
	// 消息推送
	public String sendAssignMsg(String ctm_case_code, String approve_time, Double money, Integer direction, String initiator_id){
		
		// 封装参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ctm_case_code", ctm_case_code); // 案件编号
		params.put("approve_time", approve_time); // 审批时间
		params.put("money", money); // 金额
		params.put("direction", direction); // 方向
		
		User us=uamUserOrgService.getUserByUsername(initiator_id);
		String result=null;
		if(null!=us){
			// 发送内容
			String content = uamTemplateService.mergeTemplate("yu_daishou", params);  // 拼接发送的字符串
			Message msg = new Message();
			msg.setMsgCatagory(MsgCatagoryEnum.RESPON.getCode()); // 消息提示类型[提醒类]
			msg.setType(MessageType.SITE); // 消息类型
			msg.setTitle("代收代付"); // 消息标题
			msg.setContent(content); // 消息内容
			msg.setSenderId(us.getId()); // 发送人
			result=uamMessageService.sendMessageByUser(msg, us.getId()); // 把消息封装好，发送给接收人
		}
        
        return result;
    }
	
	
	/**
	 * 功能：代收代付审批结果回写
	 * @author zhangxb16
	 * @throws ParseException 
	 */
	@RequestMapping(value="daishou", method={RequestMethod.POST})
	@ResponseBody
	public String collectionResult(HttpServletRequest request, HttpServletResponse response, Model model, 
			String ctm_case_code, String approve_time, String approve_status, String approver_id,  
			String itemList, String initiator_id) throws UnsupportedEncodingException, ParseException{
		
		Gson gson = new Gson();
		// 将 json 字符串转为 list
		List<ToCashFlow> cashflowList=gson.fromJson(itemList,new TypeToken<List<ToCashFlow>>(){}.getType());
		
		try{
			if("".equals(ctm_case_code) || null==ctm_case_code){  // 案件编号
				throw new BusinessException("案件编号为必填字段！");
			}
			if("".equals(approve_time) || null==approve_time){  // 审批时间
				throw new BusinessException("审批时间为必填字段！");		
			}
			if("".equals(approve_status) || null==approve_status){  // 审批状态
				throw new BusinessException("审批状态为必填字段！");
			}
			if("".equals(approver_id) || null==approver_id){  // 审批人ID
				throw new BusinessException("审批人ID为必填字段！");
			}
			if("".equals(initiator_id) || null==initiator_id){  // 审批发起人ID
				throw new BusinessException("审批发起人ID为必填字段！");
			}
			if(null!=cashflowList && cashflowList.size()>0){
				for(int i=0; i<cashflowList.size(); i++){
					if("".equals(cashflowList.get(i).getMoney())){  // 金额
						throw new BusinessException("金额为必填字段！");
					}
					if("".equals(cashflowList.get(i).getItem()) || null==cashflowList.get(i).getItem()){  // 费用项
						throw new BusinessException("费用项为必填字段！");
					}
					if("".equals(cashflowList.get(i).getDirection())){  // 方向 -> 0：代收；1：代付
						throw new BusinessException("方向为必填字段！");
					}
				}
			}
		}catch(BusinessException ex){
			ex.printStackTrace();
		}
		
		ResultNew rs = new ResultNew();
		try{
			// 1 发送消息
			if(null!=cashflowList && cashflowList.size()>0){
				for(int i=0; i<cashflowList.size(); i++){
					String result=sendAssignMsg(ctm_case_code, approve_time, cashflowList.get(i).getMoney(), cashflowList.get(i).getDirection(), initiator_id);  // 发送消息[房源编号, 被分派的人]
				}
			}
		}catch(BusinessException ex){
			ex.printStackTrace();
		}finally{
			// 2 往T_TO_CASH_FLOW 表中插入记录
			ToCashFlow cashflow=new ToCashFlow();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			// 根据 ctm_case_code 到 T_TO_CASE_INFO 表中去查询 casecode 
			if(null!=cashflowList && cashflowList.size()>0){
				for(int i=0; i<cashflowList.size(); i++){
					String casecode=tocaseInfoService.findcaseCodeByctmCode(ctm_case_code);
					cashflow.setCaseCode(casecode);  // casecode 案件编号
					cashflow.setCashFlowType("30004004");  // cashFlowType
					cashflow.setFlowDirection(String.valueOf(cashflowList.get(i).getDirection()));  // flowDirection 方向
					cashflow.setFlowAmount(new BigDecimal(cashflowList.get(i).getMoney()));  // flowAmount 金额
					cashflow.setFlowTime(sf.parse(approve_time));  // flowTime 审批时间
					cashflow.setCashItem(cashflowList.get(i).getItem());  // 费用项
					cashflow.setInitiator(initiator_id);  // 审批发起人
					
					int insertcash=tocashFlowService.insertCashFlow(cashflow);
					if(insertcash>0){
						rs.setStatus("0"); // 0：正常接收；-1：未正常接收
						rs.setCode("0");
						rs.setMessage("");
					}else{
						rs.setStatus("-1");
						rs.setCode("-1");
						rs.setMessage("往数据库中插入失败,请稍候再次尝试！");
					}
				}
			}
		}
		
		return JSONObject.toJSONString(rs);
	}
	
	
	// 功能：新建案件信息推送
	@RequestMapping(value="tiao")
	public String tiaozhuan(){
		
		return "case/newcasemsg";
	}
	
	// 功能：交易顾问查询
	@RequestMapping(value="tiao1")
	public String tiaozhuan1(){
		
		return "case/tradeGuwen";
	}
	
	// 功能：顾问列表调用方式判断
	@RequestMapping(value="tiao2")
	public String tiaozhuan2(){
		
		return "case/guwenListJudge";
	}
	
	// 功能：代收代付审批结果回写
	@RequestMapping(value="tiao3")
	public String tiaozhuan3(){
		
		return "case/daishou";
	}
	
	
}



