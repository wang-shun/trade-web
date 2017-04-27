package com.centaline.trans.cases.service.impl;

import java.math.BigDecimal;
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
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.attachment.entity.ToAttachment;
import com.centaline.trans.attachment.repository.ToAttachmentMapper;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.service.CaseMergeService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseMergeVo;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.CaseOriginEnum;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.repository.TgGuestInfoMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.team.entity.TsTeamScopeTarget;
import com.centaline.trans.team.service.TsTeamScopeTargetService;
import com.centaline.trans.utils.DateUtil;
import com.centaline.trans.wdcase.entity.TdmPaidSubs;
import com.centaline.trans.wdcase.entity.TpdPayment;
import com.centaline.trans.wdcase.entity.TpdCommSubs;
import com.centaline.trans.wdcase.repository.TdmPaidSubsMapper;
import com.centaline.trans.wdcase.repository.TpdCommSubsMapper;
import com.centaline.trans.wdcase.repository.TpdPaymentMapper;
import com.centaline.trans.wdcase.vo.TpdPaymentVO;

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
	
	@Autowired
	private UamBasedataService uamBasedataService;
	
	@Autowired
	private ToAttachmentMapper toAttachmentMapper; 
	
	@Autowired
	private TpdCommSubsMapper tpdCommSubsMapper; 
	
	@Autowired
	private TdmPaidSubsMapper tdmPaidSubsMapper; 
	
	@Autowired
	private TpdPaymentMapper tpdPaymentMapper; 
	
	@Autowired
	private ToCaseMapper toCaseMapper; 
	
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper; 
	
	@Autowired
	private TgGuestInfoMapper tgGuestInfoMapper; 
	
	@Autowired
	private ToPropertyInfoMapper toPropertyInfoMapper; 
	

	@Autowired(required = true)
	UamSessionService uamSessionService;
	
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
	/**
	 * 生成外单案件caseCode
	 * @author hejf10
	 * @date 2017年4月21日14:08:07
	 * @return caseCode
	 * @throws Exception
	 */
	private String getNewCaseCode() throws Exception{
		String dateStr = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
		String month = dateStr.substring(0, 6);
		try{
			String caseCode = uamBasedataService.nextSeqVal("CASE_WD_CODE", month);
			if(null == caseCode){
				throw new BusinessException("生成自录案件编号异常！");
			}
			return caseCode;
		}catch(Exception e){
			throw new BusinessException("生成外单案件编号异常！");
		}
	}
	/**
     * 保存新建外单案件信息
     * 1.保存案件信息
     * 2.保存案件详细信息
     * 3.保存案件地址信息
     * 4.保存案件上下家/推荐人信息
     * 5.保存案件附件信息
     * @author hejf10
     * @date 2017年4月21日14:09:17
     * @param request
     * @param caseMergeVo
     * @param caseCode
     * @throws Exception
     */
	@Override
	public String saveWdCaseInfo(HttpServletRequest request,CaseMergeVo caseMergeVo)throws Exception{
		
		if(null == caseMergeVo){ throw new BusinessException("新建外单案件信息为空！");	  }	
		if(null == caseMergeVo.getDistCode()){ throw new BusinessException("新建外单案件没有上传附件！");	  }	
		
		SessionUser user = uamSessionService.getSessionUser();
		
		String caseCode=getNewCaseCode();  /**调用caseCode 的本地的方法**/
		Map<String, Object> map = new HashMap<String, Object>();
		int insertUp=0,insertDown=0,insertCase=0,insertCaseInfo=0,insertTz=0;		
			
		List<String>  nameUpList = caseMergeVo.getGuestNameUp();
		List<String>  namePhoneList = caseMergeVo.getGuestPhoneUp();
		List<String>  nameDownList = caseMergeVo.getGuestNameDown();
		List<String>  phoneDownList = caseMergeVo.getGuestPhoneDown();		
		List<String>  nameRecommendList = caseMergeVo.getGuestNameRecommend();
		List<String>  phoneRecommendList = caseMergeVo.getGuestPhoneRecommend();		
		
		ToCase toCase = setToCase(caseCode,user);
		ToCaseInfo toCaseInfo = setToCaseInfo(toCase,caseMergeVo);
		TpdCommSubs tpdCommSubs = setTpdCommSubs(toCase,caseMergeVo,user);
		
		/**
		 * 1.保存案件信息				
		 */
		insertCase = toCaseService.insertSelective(toCase);
		/**
		 * 2.保存案件详细信息
		 */
		insertCaseInfo = toCaseInfoService.insertSelective(toCaseInfo);
		/**
		 * 3.保存案件地址信息			
		 */
		toPropertyInfoService.insertSelective(setToPropertyInfo(toCase,caseMergeVo));
		/**
		 * 4.保存案件上下家/推荐人信息
		 */
		insertUp = saveIntoGuestInfo(nameUpList,namePhoneList,caseCode,1);
		insertDown = saveIntoGuestInfo(nameDownList,phoneDownList,caseCode,2);
		insertTz = saveIntoGuestInfo(nameRecommendList,phoneRecommendList,caseCode,3);
		/**
		 * 5.保存案件附件信息
		 */
		toAttachmentMapper.updateToAttachmentForCaseCodeByCaseCode(caseMergeVo.getDistCode().toString(), caseCode);
		/**
		 * 6.保存案件应收记录
		 */
		tpdCommSubsMapper.insertSelective(tpdCommSubs);
		
		if(caseMergeVo.getAgentOrgId() != null && !"".equals(caseMergeVo.getAgentOrgId())){				
			map.put("caseCode", caseCode);
			map.put("orgId", caseMergeVo.getAgentOrgId());
			toCaseInfoService.updateCaseInfoByOrgId(map);
		}
		
		if(insertUp > 0 && insertDown > 0 && insertCase>0 && insertCaseInfo > 0 && insertTz >0){					
			request.setAttribute("caseCode", caseCode);
			request.setAttribute("busFlag", "success");
		}	
		
		return caseCode;			
			
	}
	/**
	 * 设置案件信息
	 * @author hejf10
	 * @date 2017年4月21日14:18:47
	 * @param toCase
	 */
	public ToCase setToCase(String caseCode,SessionUser user){
		ToCase toCase = new ToCase();
		toCase.setCaseCode(caseCode);
		toCase.setCaseProperty(CasePropertyEnum.TPWD.getCode());
		toCase.setStatus(CaseStatusEnum.WFD.getCode());
		toCase.setCaseOrigin(CaseOriginEnum.WD.getCode());
		toCase.setLeadingProcessId(user.getId());
		toCase.setOrgId(user.getServiceDepId());
		/*toCase.setCreateBy(user.getId());
		toCase.setCreateTime(new Date());*/
		return toCase;
	}
	/**
	 * 设置案件明细信息
	 * @author hejf10
	 * @date 2017年4月21日14:18:47
	 * @param toCaseInfo
	 */
	public ToCaseInfo setToCaseInfo(ToCase toCase,CaseMergeVo caseMergeVo){
		ToCaseInfo toCaseInfo = new ToCaseInfo();
		toCaseInfo.setCaseCode(toCase.getCaseCode());
		toCaseInfo.setAgentCode(caseMergeVo.getAgentCode() == null?"":caseMergeVo.getAgentCode());
		toCaseInfo.setAgentName(caseMergeVo.getAgentName()== null?"":caseMergeVo.getAgentName());
		toCaseInfo.setAgentPhone(caseMergeVo.getAgentPhone()== null?"":caseMergeVo.getAgentPhone());			
		toCaseInfo.setGrpName(caseMergeVo.getAgentOrgName()== null?"":caseMergeVo.getAgentOrgName());
		toCaseInfo.setTargetCode(caseMergeVo.getAgentOrgCode()== null?"":caseMergeVo.getAgentOrgCode());
		toCaseInfo.setIsResponsed("0");
		toCaseInfo.setImportTime(new Date());
		toCaseInfo.setSourceOfCooperation(caseMergeVo.getSourceOfCooperation());
		toCaseInfo.setRequireProcessorId(getManagerUserId(caseMergeVo.getAgentOrgCode()));
		return toCaseInfo;
	}
	/**
	 * 设置案件地址信息
	 * @author hejf10
	 * @date 2017年4月21日14:18:47
	 * @param toPropertyInfo
	 * @throws ParseException 
	 */
	public ToPropertyInfo setToPropertyInfo(ToCase toCase,CaseMergeVo caseMergeVo)throws ParseException{
		
		ToPropertyInfo toPropertyInfo = new ToPropertyInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 	
		toPropertyInfo.setCaseCode(toCase.getCaseCode());
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
		return toPropertyInfo;
	}
	/**
	 * 设置案件地址信息
	 * @author hejf10
	 * @date 2017年4月21日14:18:47
	 * @param toPropertyInfo
	 * @throws ParseException 
	 */
	public TpdCommSubs setTpdCommSubs(ToCase toCase,CaseMergeVo caseMergeVo,SessionUser user){
		
		TpdCommSubs tpdCommSubs = new TpdCommSubs();
		tpdCommSubs.setCaseCode(toCase.getCaseCode());
		tpdCommSubs.setCommSubject(caseMergeVo.getCommSubject());
		tpdCommSubs.setCommCost(caseMergeVo.getCommCost());
		tpdCommSubs.setCreateBy(user.getId());
		tpdCommSubs.setCreateTime(new Date());
		return tpdCommSubs;
	}
	/**
	 * 保存案件上下家/推荐人信息
	 * 30006001：上家
	 * 30006002：下家
	 * 30006003：推荐人
	 * @param 1:上家 2：下家 3：推荐人
	 * @author hjf
	 * @date 2017年4月21日14:20:36
	 * */
	private int saveIntoGuestInfo(List<String> nameList, List<String> phoneList,String caseCode,int flag) {
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
			}else if(flag==3){
				for(int i=0; i<nameList.size() ;i++){
					tgGuestInfo.setCaseCode(caseCode);
					tgGuestInfo.setGuestName(nameList.get(i));
					tgGuestInfo.setGuestPhone(phoneList.get(i));
					tgGuestInfo.setTransPosition("30006003");
					k = tgGuestInfoService.insertSelective(tgGuestInfo);
				}
			}
		}		
		return k;
	}
	
	/**
     * 新增实收流水
     * @author hejf10
     * @date 2017年4月26日17:24:36
     * @param request
     * @param caseMergeVo
     * @param caseCode
     * @throws Exception
     */
	@Override
	public String saveLiushui(HttpServletRequest request,CaseMergeVo caseMergeVo)throws Exception{
		
		if(null == caseMergeVo){ throw new BusinessException("实收流水信息为空！");	  }	
		String  receiptPic="";
		
		
		SessionUser user = uamSessionService.getSessionUser();
		TpdPayment tpdPayment = TpdPayment(caseMergeVo,user);
		
		List<ToAttachment> toAttachmentList = toAttachmentMapper.findToAttachmentByCaseCode(caseMergeVo.getDistCode().toString());
		if(null !=toAttachmentList && toAttachmentList.size()>0){
			receiptPic = toAttachmentList.get(0).getPreFileAdress();
		}else{
			throw new BusinessException("新建外单案件没有上传附件！");
		}
		
		/**
		 * 1.保存付款信息
		 */
		tpdPaymentMapper.insertSelective(tpdPayment);
		TdmPaidSubs tdmPaidSubs = setTdmPaidSubs(caseMergeVo,tpdPayment,user,receiptPic);
		/**
		 * 2.保存实收款项
		 */
		tdmPaidSubsMapper.insertSelective(tdmPaidSubs);
		/**
		 * 3.保存附件信息
		 */
		toAttachmentMapper.updateToAttachmentForCaseCodeByCaseCode(caseMergeVo.getDistCode().toString(), caseMergeVo.getCaseCode());
		
		
		return caseMergeVo.getCaseCode();			
			
	}
	/**
	 * 设置付款信息
	 * @author hejf10
	 * @date 2017年4月26日15:19:43
	 * @param caseMergeVo
	 * @param user
	 * @return
	 * @throws ParseException
	 */
	public TdmPaidSubs setTdmPaidSubs(CaseMergeVo caseMergeVo,TpdPayment tpdPayment,SessionUser user,String receiptPic){
		TdmPaidSubs tdmPaidSubs = new TdmPaidSubs();
		//tdmPaidSubs.setPaymentCode(tpdPayment.get);
		tdmPaidSubs.setCommSubject("服务费");
		tdmPaidSubs.setPaidAmount(caseMergeVo.getPaymentAmount());
		tdmPaidSubs.setReceiptPic(receiptPic);
		tdmPaidSubs.setPaymentCode(tpdPayment.getPkid().toString());
		tdmPaidSubs.setCreateBy(user.getId());
		tdmPaidSubs.setCreateTime(new Date());
		return tdmPaidSubs;
	}
	/**
	 * 设置实收款项
	 * @author hejf10
	 * @date 2017年4月26日15:20:46
	 * @param caseMergeVo
	 * @param user
	 * @return
	 * @throws ParseException
	 */
	public TpdPayment TpdPayment(CaseMergeVo caseMergeVo,SessionUser user){
		TpdPayment tpdPayment = new TpdPayment();
		tpdPayment.setCaseCode(caseMergeVo.getCaseCode());
		tpdPayment.setPaymentDate(caseMergeVo.getPaymentDate());
		tpdPayment.setPayer(caseMergeVo.getPayer());
		tpdPayment.setPaymentAmount(caseMergeVo.getPaymentAmount());
		tpdPayment.setCreateBy(user.getId());
		tpdPayment.setCreateTime(new Date());
		return tpdPayment;
	}
	/**
	 * 查询应收款项金额
	 * @author hejf10
	 * @date 2017年4月26日18:11:18
	 * @param caseCode
	 * @return
	 * @throws ParseException
	 */
	@Override
	public BigDecimal getCommCostAmount(String caseCode){
		TpdCommSubs tpdCommSubs = tpdCommSubsMapper.selectByCaseCode(caseCode);
		if(null == tpdCommSubs){return new BigDecimal(0);}
		return tpdCommSubs.getCommCost();
	}
	
	/**
	 * 查询应收款流水信息
	 * @author hejf10
	 * @date 2017年4月26日18:11:18
	 * @param caseCode
	 * @return
	 * @throws ParseException
	 */
	@Override
	public void getTpdPaymentVO(String caseCode,ServletRequest request){
		List<TpdPaymentVO> tpdPaymentVOs = new ArrayList<TpdPaymentVO>();
		List<TpdPayment> tpdPayments = tpdPaymentMapper.selectByCaseCode(caseCode);
		BigDecimal bigDecimal = new BigDecimal(0);
		if(null != tpdPayments && tpdPayments.size()>0)
		for(TpdPayment tpdPayment:tpdPayments){
			TdmPaidSubs tdmPaidSubs = tdmPaidSubsMapper.selectByPaymentCode(tpdPayment.getPkid().toString());
			TpdPaymentVO tpdPaymentVO = setTpdPaymentVO( tpdPayment, tdmPaidSubs);
			bigDecimal = bigDecimal.add(tpdPayment.getPaymentAmount());
			tpdPaymentVOs.add(tpdPaymentVO);
		}
		
		request.setAttribute("allAmount", bigDecimal);
		request.setAttribute("tpdPaymentVOs", tpdPaymentVOs);
		request.setAttribute("commCostAmount", getCommCostAmount(caseCode));
	}
	/**
	 * 查询外单流水
	 * @author hejf10
	 * @date 2017年4月26日18:35:24
	 * @param tpdPayment
	 * @param tdmPaidSubs
	 * @return
	 */
	public TpdPaymentVO setTpdPaymentVO(TpdPayment tpdPayment,TdmPaidSubs tdmPaidSubs){
		TpdPaymentVO tpdPaymentVO = new TpdPaymentVO();
		tpdPaymentVO.setPayer(tpdPayment.getPayer());
		tpdPaymentVO.setPaymentAmount(tpdPayment.getPaymentAmount());
		tpdPaymentVO.setPaymentDate(tpdPayment.getPaymentDate());
		tpdPaymentVO.setReceiptPic(tdmPaidSubs.getReceiptPic());
		return tpdPaymentVO;
	}
	/**
	 * 查询外单详细信息用在编辑页面显示 
	 * @author hejf10
	 * @date 2017年4月27日10:09:08
	 * @param tpdPayment
	 * @param tdmPaidSubs
	 * @return
	 */
	@Override
	public void setCaseMergeVo(HttpServletRequest request,String caseCode){
		
		CaseMergeVo caseMergeVo = new CaseMergeVo();
		/**
		 * 1.案件信息
		 */
		ToCase toCase = toCaseMapper.findToCaseByCaseCode(caseCode);
		/**
		 * 2.案件详细
		 */
		ToCaseInfo toCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
		/**
		 * 3.上下家
		 */
		List<TgGuestInfo> tgGuestInfos = tgGuestInfoMapper.findTgGuestInfoByCaseCode(caseCode);
		/**
		 * 4案件地址信息			
		 */
		ToPropertyInfo toPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
		
		if(null != toCase){
			setCaseMergeVoForToCase(caseMergeVo,toCase);
		}
		
		if(null != toCaseInfo){
			setCaseMergeVoForToCaseInfo(caseMergeVo,toCaseInfo);
		}
		
		if(null != toPropertyInfo){
			setCaseMergeVoForToPropertyInfo(caseMergeVo,toPropertyInfo);
		}
		
		if(null != tgGuestInfos && tgGuestInfos.size()>0){
			setCaseMergeVoForTgGuestInfos(caseMergeVo,tgGuestInfos);
		}
		
		request.setAttribute("caseCode", caseCode);
		request.setAttribute("caseMergeVo", caseMergeVo);
	}
	/**
	 * @author hejf10
	 * @date 2017年4月27日11:11:43
	 * @param caseMergeVo
	 * @param toCase
	 * @return
	 */
	public CaseMergeVo setCaseMergeVoForToCase(CaseMergeVo caseMergeVo,ToCase toCase){
		caseMergeVo.setCaseCode(toCase.getCaseCode());
		return caseMergeVo;
	}
	/**
	 * @author hejf10
	 * @date 2017年4月27日11:11:43
	 * @param caseMergeVo
	 * @param toCaseInfo
	 * @return
	 */
	public CaseMergeVo setCaseMergeVoForToCaseInfo(CaseMergeVo caseMergeVo,ToCaseInfo toCaseInfo){
		caseMergeVo.setSourceOfCooperation(toCaseInfo.getSourceOfCooperation());
		return caseMergeVo;
	}
	/**
	 * @author hejf10
	 * @date 2017年4月27日11:11:43
	 * @param caseMergeVo
	 * @param toPropertyInfo
	 * @return
	 */
	public CaseMergeVo setCaseMergeVoForToPropertyInfo(CaseMergeVo caseMergeVo,ToPropertyInfo toPropertyInfo){
		caseMergeVo.setPropertyAddr(toPropertyInfo.getPropertyAddr());
		return caseMergeVo;
	}
	/**
	 * @author hejf10
	 * @date 2017年4月27日11:11:43
	 * @param List<TgGuestInfo> tgGuestInfos
	 * @param toCase
	 * @return
	 */
	public CaseMergeVo setCaseMergeVoForTgGuestInfos(CaseMergeVo caseMergeVo,List<TgGuestInfo> tgGuestInfos){
		/**
		 * 上家
		 */
		List<Long> pkidUps = new ArrayList<Long>();
		List<String> guestNameUps = new ArrayList<String>();
		List<String> guestPhoneUps = new ArrayList<String>();
		/**
		 * 下家
		 */
		List<Long> pkidDowns = new ArrayList<Long>();
		List<String> guestNameDowns = new ArrayList<String>();
		List<String> guestPhoneDowns = new ArrayList<String>();
		/**
		 * 推荐人
		 */
		List<Long> pkidRecommends = new ArrayList<Long>();
		List<String> guestNameRecommends = new ArrayList<String>();
		List<String> guestPhoneRecommends = new ArrayList<String>();
		
		for(TgGuestInfo tgGuestInfo:tgGuestInfos){
			if(StringUtils.equals("30006001", tgGuestInfo.getTransPosition())){
				pkidUps.add(tgGuestInfo.getPkid());
				guestNameUps.add(tgGuestInfo.getGuestName());
				guestPhoneUps.add(tgGuestInfo.getGuestPhone());
			}
			if(StringUtils.equals("30006002", tgGuestInfo.getTransPosition())){
				pkidDowns.add(tgGuestInfo.getPkid());
				guestNameDowns.add(tgGuestInfo.getGuestName());
				guestPhoneDowns.add(tgGuestInfo.getGuestPhone());
			}
			if(StringUtils.equals("30006003", tgGuestInfo.getTransPosition())){
				pkidRecommends.add(tgGuestInfo.getPkid());
				guestNameRecommends.add(tgGuestInfo.getGuestName());
				guestPhoneRecommends.add(tgGuestInfo.getGuestPhone());
			}
		}
		caseMergeVo.setPkidUp(pkidUps);
		caseMergeVo.setGuestNameUp(guestNameUps);
		caseMergeVo.setGuestPhoneUp(guestPhoneUps);
		
		caseMergeVo.setPkidDown(pkidDowns);
		caseMergeVo.setGuestNameDown(guestNameDowns);
		caseMergeVo.setGuestPhoneDown(guestPhoneDowns);
		
		caseMergeVo.setPkidRecommend(pkidRecommends);
		caseMergeVo.setGuestNameRecommend(guestNameRecommends);
		caseMergeVo.setGuestPhoneRecommend(guestPhoneRecommends);
		
		return caseMergeVo;
	}
	
	/**
     * 保存新建外单案件信息
     * 1.保存案件信息
     * 2.保存案件详细信息
     * 3.保存案件地址信息
     * 4.保存案件上下家/推荐人信息
     * 5.保存案件附件信息
     * @author hejf10
     * @date 2017年4月21日14:09:17
     * @param request
     * @param caseMergeVo
     * @param caseCode
     * @throws Exception
     */
	@Override
	public String editWdCaseInfo(HttpServletRequest request,CaseMergeVo caseMergeVo)throws Exception{
		
		if(null == caseMergeVo){ throw new BusinessException("外单案件信息为空！");	  }	
		
		SessionUser user = uamSessionService.getSessionUser();
		
		String caseCode=caseMergeVo.getCaseCode();
		Map<String, Object> map = new HashMap<String, Object>();
		int insertUp=0,insertDown=0,insertCase=0,insertCaseInfo=0,insertTz=0;		
			
		List<String>  nameUpList = caseMergeVo.getGuestNameUp();
		List<String>  namePhoneList = caseMergeVo.getGuestPhoneUp();
		List<String>  nameDownList = caseMergeVo.getGuestNameDown();
		List<String>  phoneDownList = caseMergeVo.getGuestPhoneDown();		
		List<String>  nameRecommendList = caseMergeVo.getGuestNameRecommend();
		List<String>  phoneRecommendList = caseMergeVo.getGuestPhoneRecommend();		
		
		ToCaseInfo toCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
		ToPropertyInfo toPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
		TpdCommSubs tpdCommSubs = tpdCommSubsMapper.selectByCaseCode(caseCode);
		
		editToCaseInfo(toCaseInfo,caseMergeVo);
		editToPropertyInfo(toPropertyInfo,caseMergeVo);
		editTpdCommSubs(tpdCommSubs,caseMergeVo,user);
		/**
		 * 1.更新案件详细信息
		 */
		toCaseInfoService.updateByPrimaryKey(toCaseInfo);
		/**
		 * 2.保存案件地址信息			
		 */
		toPropertyInfoMapper.updateByPrimaryKey(toPropertyInfo);
		/**
		 * 3.保存案件应收记录
		 */
		tpdCommSubsMapper.updateByPrimaryKey(tpdCommSubs);
		/**
		 * 4.保存案件上下家/推荐人信息
		 */
		/*insertUp = saveIntoGuestInfo(nameUpList,namePhoneList,caseCode,1);
		insertDown = saveIntoGuestInfo(nameDownList,phoneDownList,caseCode,2);
		insertTz = saveIntoGuestInfo(nameRecommendList,phoneRecommendList,caseCode,3);*/
		/**
		 * 5.保存案件附件信息
		 */
		/*toAttachmentMapper.updateToAttachmentForCaseCodeByCaseCode(caseMergeVo.getDistCode().toString(), caseCode);*/
	
		
		return caseCode;			
			
	}
	
	/**
	 * 编辑案件明细信息
	 * @author hejf10
	 * @date 2017年4月27日16:01:58
	 * @param toCaseInfo
	 */
	public ToCaseInfo editToCaseInfo(ToCaseInfo toCaseInfo,CaseMergeVo caseMergeVo){
		toCaseInfo.setSourceOfCooperation(caseMergeVo.getSourceOfCooperation());
		return toCaseInfo;
	}
	/**
	 * 编辑案件地址信息
	 * @author hejf10
	 * @date 2017年4月27日16:02:57
	 * @param toPropertyInfo
	 * @throws ParseException 
	 */
	public ToPropertyInfo editToPropertyInfo(ToPropertyInfo toPropertyInfo,CaseMergeVo caseMergeVo)throws ParseException{
		toPropertyInfo.setPropertyAddr(caseMergeVo.getPropertyAddr());
		return toPropertyInfo;
	}
	/**
	 * 编辑外单应收
	 * @author hejf10
	 * @date 2017年4月27日16:05:12
	 * @param toPropertyInfo
	 * @throws ParseException 
	 */
	public TpdCommSubs editTpdCommSubs(TpdCommSubs tpdCommSubs,CaseMergeVo caseMergeVo,SessionUser user){
		tpdCommSubs.setCommSubject(caseMergeVo.getCommSubject());
		tpdCommSubs.setCommCost(caseMergeVo.getCommCost());
		tpdCommSubs.setUpdateBy(user.getId());
		tpdCommSubs.setUpdateTime(new Date());
		return tpdCommSubs;
	}
	/**
	 * 编辑案件上下家/推荐人信息
	 * 30006001：上家
	 * 30006002：下家
	 * 30006003：推荐人
	 * @param 1:上家 2：下家 3：推荐人
	 * @author hjf
	 * @date 2017年4月27日16:07:08
	 * */
	private int editIntoGuestInfo(List<String> nameList, List<String> phoneList,String caseCode,int flag) {
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
			}else if(flag==3){
				for(int i=0; i<nameList.size() ;i++){
					tgGuestInfo.setCaseCode(caseCode);
					tgGuestInfo.setGuestName(nameList.get(i));
					tgGuestInfo.setGuestPhone(phoneList.get(i));
					tgGuestInfo.setTransPosition("30006003");
					k = tgGuestInfoService.insertSelective(tgGuestInfo);
				}
			}
		}		
		return k;
	}
}
