package com.centaline.trans.cases.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.service.CasenewMessageService;
import com.centaline.trans.cases.vo.CaseGuwenVo;
import com.centaline.trans.cases.vo.UserOrgIdVo;
import com.centaline.trans.cases.vo.ViHouseDelBaseVo;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.repository.TgGuestInfoMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.common.repository.ToWorkFlowMapper;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.entity.TsTeamScope;
import com.centaline.trans.team.repository.TsTeamPropertyMapper;
import com.centaline.trans.team.repository.TsTeamScopeMapper;


@Service
@Transactional
public class CasenewMessageServiceImpl implements CasenewMessageService{

	@Autowired
	private TgGuestInfoMapper tgGuestInfoMapper; // 客户
	
	@Autowired
	private ToPropertyInfoMapper toPropertyInfoMapper; // 物业信息
	
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper; // 案件信息
	
	@Autowired
	private UamUserOrgService uamUserOrgService; 
	
	@Autowired
	private TsTeamScopeMapper tsteamScopeMapper;
	
	@Autowired
	private ToCaseMapper tocaseMapper;
	
	@Autowired
	private ToWorkFlowMapper toworkFlowMapper;
	
	@Autowired
	private TsTeamPropertyMapper tsteamPropertyMapper;
	
	@Autowired(required = true)
	WorkFlowManager workFlowManager;
	
	@Autowired(required = true)
	PropertyUtilsService propertyUtilsService;
	
	
	
	/**
	 * 描述：公用方法[1个参数]
	 * 功能：根据经纪人id查询出其所在店组, 然后再根据其所在店组查询出对应的誉萃店组code[orgCode]
	 */
	public String selectByAgentCode(String agentCode){
		
		String groupCode=null;  // 店组code[orgCode]
		String yuTeamCode=null; // 誉萃的店组code
		String freeSelect="0";  // 0：本地；1：交易系统 
		
		User us=uamUserOrgService.getUserByUsername(agentCode);
		if(null !=us){
			Org oo=uamUserOrgService.getOrgById(us.getOrgId());
			groupCode=oo.getOrgCode(); // 店组code[orgCode]
		}
		if(null!=groupCode){
			List<TsTeamScope> teamScope=tsteamScopeMapper.selectByYuagentTeamCode(groupCode); // 誉萃的店组code
			if(null!=teamScope && teamScope.size()>0){
				if(teamScope.size()==1){
					yuTeamCode=teamScope.get(0).getYuTeamCode();  // 查询出誉萃的orgid
					// 根据 yuTeamCode 到 T_TS_TEAM_PROPERTY 表中去查询 FREE_SELECT字段[1 代表允许 0 代表不允许]
					TsTeamProperty teampy=tsteamPropertyMapper.findTeamPropertyByTeamCode(yuTeamCode);
					freeSelect=teampy.getFreeSelect();
				}else{
					// 如果 teamScope.size>1 则存在多个, 则从T_TS_TEAM_PROPERTY 表中找出为组办组的[IS_RESPONSE_TEAM] 的  yuTeamCode
					for(int i=0; i<teamScope.size(); i++){
						yuTeamCode=teamScope.get(i).getYuTeamCode();  // 查询出誉萃的orgid
						TsTeamProperty teamproperty=tsteamPropertyMapper.selectTeamPropertyByTeamCode(yuTeamCode);  // 根据 yuTeamCode 去查询组办组的
						if(null !=teamproperty){
							String yucode=teamproperty.getYuTeamCode();
							if(!"".equals(yucode) && null!=yucode){
								// 根据 yuTeamCode 到 T_TS_TEAM_PROPERTY 表中去查询 FREE_SELECT字段[1 代表允许 0 代表不允许]
								TsTeamProperty teampy=tsteamPropertyMapper.findTeamPropertyByTeamCode(yuTeamCode);
								freeSelect=teampy.getFreeSelect();
							}
						}
					}
				}
			}
		}
		
		return freeSelect;
	}
	
	
	/**
	 * 描述：公用方法[2个参数]
	 * 功能：根据经纪人id查询出其所在店组, 然后再根据其所在店组查询出对应的誉萃店组code[orgCode]
	 */
	public UserOrgIdVo selectByAgentCode(String agentCode, String jobCode){
		
		String groupCode=null;  // 店组code[orgCode]
		String yuTeamCode=null; // 誉萃的店组code
		List<User> userList=null;
		UserOrgIdVo userOrgIdVo=new UserOrgIdVo();
		
		User us=uamUserOrgService.getUserByUsername(agentCode);  // 根据username 查询出 userid 
		List<UserOrgJob> orgs=null;
		if(null!=us){
			orgs=uamUserOrgService.getUserOrgJobByUserId(us.getId());  // 通过成交经纪人id 获取到他所在的店组id
		}
		if(null!=orgs && orgs.size()>0){
			UserOrgJob userOrgJob=orgs.get(0);
			groupCode=userOrgJob.getOrgCode(); // 店组code[orgCode]
		}
		if(null!=groupCode){
			List<TsTeamScope> teamScope=tsteamScopeMapper.selectByYuagentTeamCode(groupCode); // 誉萃的店组code
			if(null!=teamScope && teamScope.size()>0){
				if(teamScope.size()==1){
					yuTeamCode=teamScope.get(0).getYuTeamCode();  // 查询出誉萃的orgid
					Org oo=uamUserOrgService.getOrgByCode(yuTeamCode);
					userList=uamUserOrgService.getUserByOrgIdAndJobCode(oo.getId(), jobCode);
					
					userOrgIdVo.setOrgId(oo.getId());
					userOrgIdVo.setUserList(userList);
				}else{
					List<User> uList=new ArrayList<>();
					// 如果 teamScope.size>1 则存在多个, 则从T_TS_TEAM_PROPERTY 表中找出为组办组的[IS_RESPONSE_TEAM] 的  yuTeamCode
					for(int i=0; i<teamScope.size(); i++){
						yuTeamCode=teamScope.get(i).getYuTeamCode();  // 查询出誉萃的orgid
						TsTeamProperty teamproperty=tsteamPropertyMapper.selectTeamPropertyByTeamCode(yuTeamCode);  // 根据 yuTeamCode 去查询组办组的
						if(null !=teamproperty){
							String yucode=teamproperty.getYuTeamCode();
							if(!"".equals(yucode) || null!=yucode){
								Org oo=uamUserOrgService.getOrgByCode(yuTeamCode);
								userList=uamUserOrgService.getUserByOrgIdAndJobCode(oo.getId(), jobCode);
								User ur=userList.get(0);
								uList.add(ur);
								userOrgIdVo.setOrgId(oo.getId());
							}
						}
					}
					userOrgIdVo.setUserList(uList);
				}
			}
		}
		if(userOrgIdVo.getOrgId() == null && userOrgIdVo.getUserList() == null){
			return null;
		}
		return userOrgIdVo;
	}
	
	
	/**
	 * 描述：公用方法[2个参数]
	 * 功能：根据经纪人id查询出其所在店组, 然后再根据其所在店组查询出对应的誉萃店组code[orgCode]
	 */
	public List<CaseGuwenVo> findByAgentCode(String agentCode, String jobCode){
		
		String groupCode=null;  // 店组code[orgCode]
		String yuTeamCode=null; // 誉萃的店组code
		List<User> userList=null;
		CaseGuwenVo guwenvo=null;
		List<CaseGuwenVo> guwenList=new ArrayList<CaseGuwenVo>();
		
		User us=uamUserOrgService.getUserByUsername(agentCode);
		if(null !=us){
			Org oo=uamUserOrgService.getOrgById(us.getOrgId());
			groupCode=oo.getOrgCode(); // 店组code[orgCode]
			
			if(null!=groupCode){
				List<TsTeamScope> teamScope=tsteamScopeMapper.selectByYuagentTeamCode(groupCode); // 誉萃的店组code
				if(null !=teamScope && teamScope.size()>0){
					if(teamScope.size()==1){
						yuTeamCode=teamScope.get(0).getYuTeamCode();  // 查询出誉萃的orgid
						Org og=uamUserOrgService.getOrgByCode(yuTeamCode);
						userList=uamUserOrgService.getUserByOrgIdAndJobCode(og.getId(), jobCode);
						guwenvo=new CaseGuwenVo();
						guwenvo.setGuwenID(userList.get(0).getUsername());  // 顾问ID
						guwenvo.setGuwenName(userList.get(0).getRealName());  // 顾问姓名
						guwenvo.setOrgCode(og.getOrgCode());  // 组织编码
						guwenvo.setOrgName(userList.get(0).getOrgName());  // 组织名称
					}else{
						// 如果 teamScope.size>1 则存在多个, 则从T_TS_TEAM_PROPERTY 表中找出为组办组的[IS_RESPONSE_TEAM] 的  yuTeamCode
						for(int i=0; i<teamScope.size(); i++){
							yuTeamCode=teamScope.get(i).getYuTeamCode();  // 查询出誉萃的orgid
							TsTeamProperty teamproperty=tsteamPropertyMapper.selectTeamPropertyByTeamCode(yuTeamCode);  // 根据 yuTeamCode 去查询组办组的
							if(null !=teamproperty){
								String yucode=teamproperty.getYuTeamCode();
								if(!"".equals(yucode) || null!=yucode){
									Org org=uamUserOrgService.getOrgByCode(yuTeamCode);
									userList=uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(), jobCode);
									
									for(int k=0; k<userList.size(); k++){
										guwenvo=new CaseGuwenVo();
										guwenvo.setGuwenID(userList.get(k).getUsername());  // 顾问ID
										guwenvo.setGuwenName(userList.get(k).getRealName());  // 顾问姓名
										guwenvo.setOrgCode(org.getOrgCode());  // 组织编码
										guwenvo.setOrgName(userList.get(k).getOrgName());  // 组织名称
										guwenList.add(guwenvo);
									}
								}
							}
						}
					}
				}
			}
		}
		
		return guwenList;
	}
	
	
	/**
	 * 公共方法：启动工作流
	 * @param requireProcessorId
	 * @author zhangxb16
	 */
	public StartProcessInstanceVo startWorkFlow(String requireProcessorId){
		
		ProcessInstance process=new ProcessInstance();
    	process.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
    	process.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
    	StartProcessInstanceVo pIVo=null;
    	
    	try{
	    	/* 流程引擎相关 */
	    	Map<String, Object> defValsMap=propertyUtilsService.getProcessDefVals(WorkFlowEnum.WBUSSKEY.getCode());
			List<RestVariable> variables=new ArrayList<RestVariable>();
		    Iterator it=defValsMap.keySet().iterator();  
		    while (it.hasNext()) {
	            String key=it.next().toString();
	    		RestVariable restVariable = new RestVariable();
	    		restVariable.setName(key); 
	    		restVariable.setValue(defValsMap.get(key));
	    		variables.add(restVariable);
		    }
	    	process.setVariables(variables);
	    	
	    	// User user=uamUserOrgService.getUserByUsername(requireProcessorId);  // 交易顾问id
	    	if(null !=requireProcessorId){
	    		pIVo=workFlowManager.startCaseWorkFlow2(process, requireProcessorId, requireProcessorId);
	    	}
    	}catch(Exception ex){
    		// ex.printStackTrace();
    		throw new BusinessException("抱歉, 该交易顾问未能启动工作流");
    	}
    	
    	return pIVo;
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
	@Override
	public String insertCasenewMsg(String ctmCode, String agentCode, String agentName, List<TgGuestInfo> tgGuestInfo, String propertyAddr, String propetyCode, String property_agent_id, String requireProcessorId, String grpCode, String grpName, String caseCode) {
		String status="-1";  // -1：接收异常，0：接收正常
		Date dt=new Date();
		
		// 1 客户
		int guest=0;
		TgGuestInfo guestInfo=new TgGuestInfo();
		for(int i=0; i<tgGuestInfo.size(); i++){
			guestInfo.setGuestCode(tgGuestInfo.get(i).getGuestCode());  // 客户编号
			guestInfo.setCaseCode(caseCode);  // caseCode
			guestInfo.setGuestName(tgGuestInfo.get(i).getGuestName());  // 姓名
			guestInfo.setGuestPhone(tgGuestInfo.get(i).getGuestPhone());  // 电话
			
			String identifyType=tgGuestInfo.get(i).getIdentifyType();  // 身份证件
			if(null==identifyType || "".equals(identifyType)){
				guestInfo.setIdentifyType(null);
			}else{
				guestInfo.setIdentifyType(tgGuestInfo.get(i).getIdentifyType());
			}
			
			String identifyNumber=tgGuestInfo.get(i).getIdentifyNumber();  // 证件号码
			if(null==identifyNumber || "".equals(identifyNumber)){
				guestInfo.setIdentifyNumber(null); 
			}else{
				guestInfo.setIdentifyNumber(tgGuestInfo.get(i).getIdentifyNumber());  
			}
			
			// 备注：接口传过来的是 上家：0；下家：1, 但是我们存数据库 是 上家30006001 , 下家 30006002
			if(Integer.parseInt(tgGuestInfo.get(i).getTransPosition())==0){ // 上家
				guestInfo.setTransPosition("30006001");  // 上家
			}else if(Integer.parseInt(tgGuestInfo.get(i).getTransPosition())==1){ // 下家
				guestInfo.setTransPosition("30006002");  // 下家
			}
			
			guest=tgGuestInfoMapper.insertSelective(guestInfo);
		}
		
		if(guest>0){
		}else{
			throw new BusinessException("抱歉,客户信息没有新增成功,请刷新后再次尝试！");
		}
		
		// 2  物业信息
		ToPropertyInfo ppyinfo=new ToPropertyInfo();
		ppyinfo.setPropertyAddr(propertyAddr);  // 物业地址
		ppyinfo.setCtmAddr(propertyAddr); //ctm地址
		ppyinfo.setPropertyCode(propetyCode); // 房屋编码[houseid]
		ppyinfo.setPropertyAgentId(property_agent_id);  // 委托编号[houdel_code] 
		ppyinfo.setCtmCode(ctmCode);  // 案件编号
		ppyinfo.setCaseCode(caseCode);  // caseCode
		
		// 根据propetyCode[房屋编码] 到三级市场的视图中去查询 DISTRICT_CODE, BUILD_END_YEAR, BUILD_SIZE, FLOOR, TOTAL_FLOOR 然后再往T_TO_PROPERTY_INFO 表中写入记录
		ViHouseDelBaseVo housevo=toPropertyInfoMapper.selectByHoudelCode(property_agent_id); // 根据房屋id 去查询
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 年月日 时分秒 SDyyyymmddhhmmss
		if(null!=housevo){
			ppyinfo.setDistCode(housevo.getDISTRICT_CODE());
			ppyinfo.setLocateFloor(housevo.getFLOOR());
			ppyinfo.setTotalFloor(housevo.getTOTAL_FLOOR());
			ppyinfo.setSquare(housevo.getBUILD_SIZE().doubleValue());
			
			try {
				String fhyear=housevo.getBUILD_END_YEAR();
			    String fishyear=fhyear+"-01-01 00:00:00";
				if(null!=housevo.getBUILD_END_YEAR()){
					ppyinfo.setFinishYear(sdf.parse(fishyear));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		int property=toPropertyInfoMapper.insertSelective(ppyinfo);
		if(property>0){
		}else{
			throw new BusinessException("抱歉,物业信息没有新增成功,请刷新后再次尝试！");
		}
		
		// 3  案件信息
		User username=uamUserOrgService.getUserByUsername(agentCode);  // 根据成交经纪人id 查询去他对应的真名
		
		// 处理 T_TO_CASE_INFO 表 
		ToCaseInfo tceinfo=new ToCaseInfo(); 
		tceinfo.setCtmCode(ctmCode);  // 案件编号
		tceinfo.setAgentUserName(agentCode); // ctm 传过来的用户名称
		if(null!=username){
			tceinfo.setAgentCode(username.getId());  // 成交经纪人编号
			tceinfo.setAgentPhone(username.getMobile());  // 手机号
			if(null!=username.getOrgId()){
				List<HashMap<String, Object>> map=toCaseInfoMapper.selectBusiarbyGroupid(username.getOrgId());
				for(int j=0; j<map.size(); j++) {
		        	String areaId=String.valueOf(map.get(j).get("BUSIAR_ID"));
		        	if(null!=areaId){
		        		Org org=uamUserOrgService.getOrgById(areaId);
		        		tceinfo.setArCode(org.getOrgCode());
		        		tceinfo.setArName(org.getOrgName());
		        	}
				}
			}
		}
		tceinfo.setAgentName(agentName);  // 成交经纪人姓名
		tceinfo.setCaseCode(caseCode);  // caseCode
		tceinfo.setImportTime(dt);  // 导入时间
		tceinfo.setGrpCode(grpCode);  // 组别编号
		tceinfo.setGrpName(grpName);  // 组别名称
		
		// 处理 T_TO_CASE 表
		ToCase tocase=new ToCase();
		tocase.setCtmCode(ctmCode);  // ctm 案件编号
		tocase.setCreateTime(dt);  // 创建时间 
		tocase.setCaseProperty(CasePropertyEnum.TPZT.getCode());  // 指定为在途单
		tocase.setCaseCode(caseCode);  // caseCode
		
		/**
		 * 业务逻辑：看交易顾问id 是否为空：
		 * 	  a 如果交易顾问id为空, 则通过分行-誉萃组别映射关系，找到誉萃组别经理的id放进去。
		 * 	  b 如果交易顾问id不为空, 则直接写到这个字段，并调用流程引擎接口，启动流程。
		 */
		int caseinfo=0;
		int toce=0;
		int workflow=0;
		if("".equals(requireProcessorId) || null==requireProcessorId){
			// 通过分行-誉萃组别映射关系，找到誉萃组别经理的id放进去。
			if(null != agentCode){
				
				// 1 处理 T_TO_CASE_INFO 表 
				UserOrgIdVo userOrgIdVo=selectByAgentCode(agentCode, TransJobs.TJYZG.getCode());
				if(null!=userOrgIdVo && userOrgIdVo.getUserList().size()==1){  // 无论是否找到誉萃组别经理的id, 都需要往T_TO_CASE_INFO 表中插入记录
					if(null!=userOrgIdVo.getUserList() && userOrgIdVo.getUserList().size()>0){
						tceinfo.setRequireProcessorId(userOrgIdVo.getUserList().get(0).getId());  // 誉萃组别经理的id
						tceinfo.setDispatchTime(dt);  // 分单时间
						tocase.setLeadingProcessId(userOrgIdVo.getUserList().get(0).getId());  // 案件负责人[如果交易顾问id 为空时则为誉萃组别经理的id, 如果交易顾问id 不为空则为交易顾问id]
						tocase.setOrgId(userOrgIdVo.getOrgId());  // 案件负责人所在的组织id
						
						tceinfo.setAgentName(username.getRealName());  // 交易顾问姓名[请求处理人姓名]
						tceinfo.setIsResponsed("0");  // 是否响应, 1 代表响应, 0 代表 未响应
						caseinfo=toCaseInfoMapper.insertSelective(tceinfo);
						
						// 2 处理 T_TO_CASE 表
						tocase.setStatus(CaseStatusEnum.WFD.getCode());  // 状态[如果交易顾问id 为空时则为未分单, 如果交易顾问id 不为空则为已分单] 
						toce=tocaseMapper.insertSelective(tocase);
					}
					
					if(toce>0){
					}else{
						throw new BusinessException("抱歉,案件表没有新增成功,请刷新后再次尝试！");
					}
				}else{
					caseinfo=toCaseInfoMapper.insertSelective(tceinfo);
				}
			}
		}else{
			ToWorkFlow wf=new ToWorkFlow();
			StartProcessInstanceVo pIVo=this.startWorkFlow(requireProcessorId);
        	if(null==pIVo || null==pIVo.getId()){
        		// throw new BusinessException("抱歉,流程启动失败,请刷新后再次尝试！");
        		tceinfo.setRequireProcessorId("");  // 交易顾问id[请求处理人编号]
        		tceinfo.setIsResponsed("0");  // 是否响应, 1 代表响应, 0 代表 未响应
        		tceinfo.setResDate(null);  // 响应时间
        		tceinfo.setAgentName("");  // 交易顾问姓名[请求处理人姓名]
        		
        		// 1 处理 T_TO_CASE_INFO 表
        		caseinfo=toCaseInfoMapper.insertSelective(tceinfo);
        	}else{
        		User uuu=uamUserOrgService.getUserByUsername(requireProcessorId);
        		tceinfo.setRequireProcessorId(uuu.getId());  // 交易顾问id[请求处理人编号]
        		tceinfo.setIsResponsed("1");  // 是否响应, 1 代表响应, 0 代表 未响应
        		tceinfo.setResDate(dt);  // 响应时间
        		tceinfo.setAgentName(username.getRealName());  // 交易顾问姓名[请求处理人姓名]
        		
        		// 1 处理 T_TO_CASE_INFO 表
        		caseinfo=toCaseInfoMapper.insertSelective(tceinfo);
				
        		// 2 处理 T_TO_CASE 表
        		User urq=uamUserOrgService.getUserByUsername(requireProcessorId);  // 根据交易顾问username 去查询orgid
        		tocase.setStatus(CaseStatusEnum.YFD.getCode());  // 状态[如果交易顾问id 为空时则为未分单, 如果交易顾问id 不为空则为已分单] 
        		tocase.setLeadingProcessId(urq.getId());  // 案件负责人[如果交易顾问id 为空时则为誉萃组别经理的id, 如果交易顾问id 不为空则为交易顾问id]
        		tocase.setOrgId(urq.getOrgId());  // 案件负责人所在的组织id
				toce=tocaseMapper.insertSelective(tocase);
				
				if(toce>0){
				}else{
					throw new BusinessException("抱歉,案件表没有新增成功,请刷新后再次尝试！");
				}
				
				// 3 处理 T_TO_WORKFLOW 表 
				wf.setProcessOwner(agentCode);  // 目前交易顾问 username
				wf.setInstCode(pIVo.getId());
				wf.setBusinessKey(pIVo.getBusinessKey());
				wf.setProcessDefinitionId(pIVo.getProcessDefinitionId());
				wf.setCaseCode(caseCode);  // caseCode
				workflow=toworkFlowMapper.insertSelective(wf);
				
				if(workflow>0){
				}else{
					throw new BusinessException("抱歉,工作流表没有新增成功,请刷新后再次尝试！");
				}
        	}
			// end 工作流
		}
		
		if(caseinfo>0){
		}else{
			throw new BusinessException("抱歉,案件信息没有新增成功,请刷新后再次尝试！");
		}
		
		status="0";
		return status;
	}
	
	
	/**
	 * 功能：交易顾问查询
	 * @author zhangxb16
	 */
	@Override
	public List<CaseGuwenVo> selectConsultInfo(String agentID) {
		
		List<CaseGuwenVo> guwenvo=findByAgentCode(agentID, TransJobs.TJYGW.getCode());
		return guwenvo;
	}
	
	
	/**
	 * 功能：顾问列表调用方式判断
	 * @author zhangxb16
	 */
	public String guwenList(String agentID){
	
		String freeSelect=selectByAgentCode(agentID);
		return freeSelect;
	}

	
}
