package com.centaline.trans.cases.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.CaseMergeService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseMergeVo;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.CaseOriginEnum;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;

import com.centaline.trans.team.entity.TsTeamScopeTarget;
import com.centaline.trans.team.service.TsTeamScopeTargetService;

@Service
public class CaseMergeServiceImpl implements CaseMergeService {


	@Autowired
	private ToCaseService toCaseService;
	@Autowired
	private ToPropertyInfoService toPropertyInfoService;
	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Autowired(required = true)
	TsTeamScopeTargetService tsTeamScopeTargetService;
	
	@Autowired(required = true)
	ToCaseInfoService toCaseInfoService;
	
	@Autowired(required = true)
	TgGuestInfoService tgGuestInfoService;

	@Override
	public void saveCaseInfo(HttpServletRequest request,CaseMergeVo caseMergeVo,String caseCode) {
		
		
		if(null == caseMergeVo){  
			throw new BusinessException("新建自录案件，请求信息为空！");	 
		}	
		
		Map<String, Object> map = new HashMap<String, Object>();
		ToCase toCase = new ToCase();
		ToCaseInfo toCaseInfo = new ToCaseInfo();
		ToPropertyInfo toPropertyInfo = new ToPropertyInfo();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 		
		int insertUp=0,insertDown=0,insertCase=0,insertCaseInfo=0;		
		
		try {
			
			List<String>  nameUpList = caseMergeVo.getGuestNameUp();
			List<String>  namePhoneList = caseMergeVo.getGuestPhoneUp();
			List<String>  nameDownList = caseMergeVo.getGuestNameDown();
			List<String>  phoneDownList = caseMergeVo.getGuestPhoneDown();		
			
			//插入上下家信息
			insertUp = insertIntoGuestInfo(nameUpList,namePhoneList,caseCode,1);
			insertDown = insertIntoGuestInfo(nameDownList,phoneDownList,caseCode,2);
			
			//插入房屋信息
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
			
			//插入案件信息
			toCase.setCaseCode(caseCode);
			toCase.setCaseProperty(CasePropertyEnum.TPZJ.getCode());//自建案件
			toCase.setStatus(CaseStatusEnum.WFD.getCode());//未分单
			toCase.setCaseOrigin(CaseOriginEnum.INPUT.getCode());					
			insertCase = toCaseService.insertSelective(toCase);
			
			//插入案件详细信息
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
			
			if(insertUp > 0 && insertDown > 0 && insertCase>0 && insertCaseInfo > 0){					
				request.setAttribute("caseCode", caseCode);
				request.setAttribute("busFlag", "success");
			}	
			
			
		} catch (Exception e) {				
			throw new BusinessException("新建自录案件信息保存异常！");
		}			
			
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
}
