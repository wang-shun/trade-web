package com.centaline.api.ccai.cases.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.centaline.trans.common.vo.CcaiServiceResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.api.ccai.cases.service.CcaiService;
import com.centaline.api.ccai.cases.vo.CcaiImportAttachment;
import com.centaline.api.ccai.cases.vo.CcaiImportCase;
import com.centaline.api.ccai.cases.vo.CcaiImportCaseGuest;
import com.centaline.api.ccai.cases.vo.CcaiImportCaseInfo;
import com.centaline.api.ccai.cases.vo.CcaiImportCaseProperty;
import com.centaline.api.enums.CaseSyncParticipantEnum;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToCcaiAttachment;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.CaseOriginEnum;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.repository.TgGuestInfoMapper;
import com.centaline.trans.common.repository.ToCcaiAttachmentMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.utils.DateUtil;
/**
 * ccai案件导入实现类
 * @author yinchao
 *
 */
@Service
@Transactional
public class CcaiServiceImpl implements CcaiService {
	@Autowired
	private ToCaseMapper tocaseMapper;
	@Autowired
	private TgGuestInfoMapper tgGuestInfoMapper; // 客户
	@Autowired
	private ToPropertyInfoMapper toPropertyInfoMapper; // 物业信息
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper; // 案件信息
	@Autowired
	private ToCcaiAttachmentMapper toCcaiAttachmentMapper;//CCAI附件信息
	@Autowired
	private UamUserOrgService  uamUserOrgService;//用户信息
	@Autowired
	private ToCaseParticipantMapper toCaseParticipantMapper;//案件参与人信息
	
	@Override
	public CcaiServiceResult importCase(CcaiImportCase acase) {
		CcaiServiceResult result = new CcaiServiceResult();
		String caseCode=getCaseCode(acase);
		//直接将导入的信息插入到对应的数据表中
		//案件基本信息导入
		addCaseInfo(caseCode,acase);
		//案件物业信息导入
		addProperty(caseCode,acase.getCcaiCode(),acase.getProperty());
		//案件客户信息导入
		addGuestsInfo(caseCode,acase.getCcaiCode(),acase.getGuests());
		//案件信息导入
		addCase(caseCode,acase);
		//案件附件信息导入
		addAttachments(caseCode,acase.getCcaiCode(),acase.getAttachments());
		result.setSuccess(true);
		result.setMessage("同步成功!");
		result.setCode("00");
		return result;
	}

	@Override
	public boolean isExistCcaiCode(String ccaiCode) {
		int count = toCaseInfoMapper.isExistCcaiCode(ccaiCode);
		return count>0;
	}

	@Override
	public CcaiServiceResult updateCase(CcaiImportCase acase) {
		//案件信息修改的准则 附件信息可以随时修改
		StringBuilder msg = new StringBuilder();
		boolean success = false;
		String caseCode = toCaseInfoMapper.findcaseCodeByCcaiCode(acase.getCcaiCode());
		if(StringUtils.isBlank(caseCode)){
			msg.append("未获取到成交报告[")
					.append(acase.getCcaiCode())
					.append("]对应的案件信息!");
		}else{
			boolean hasupdate = false;
			ToCase ca = tocaseMapper.findToCaseByCaseCode(caseCode);
			//TODO 修改物业信息
			if(acase.getProperty()!=null){
				// 只有案件处于未审核状态 才可以修改物业信息
				if(CaseStatusEnum.WJD.getCode().equals(ca.getStatus())){
					success = updateProperty(caseCode,acase.getCcaiCode(),acase.getProperty(),msg);
					hasupdate = true;
				}else{
					msg.append("案件已开始，物业信息未修改\r\n");
				}
			}
			//修改附件信息 不存在失败的情况
			if(acase.getAttachments()!=null&&acase.getAttachments().size()>0){
				//TODO 已领证状态不再进行更改附件
				if(!CaseStatusEnum.YLTZ.getCode().equals(ca.getStatus())){
					updateAttachments(caseCode,acase.getCcaiCode(),acase.getAttachments(),msg);
				}else{
					msg.append("案件已结案，附件信息不能再进行修改");
				}
				hasupdate = true;
			}
			//未进行修改 给出提示
			if(!hasupdate){
				msg.append("目前修改仅支持附件信息修改和物业信息修改");
			}
		}
		CcaiServiceResult result = new CcaiServiceResult();
		result.setSuccess(success);
		result.setCode(success?"00":"99");
		result.setMessage(msg.toString());
		return result;
	}
	
	@Override
	public CcaiServiceResult updateCaseAndFlow(CcaiImportCase acase) {
		//案件信息修改的准则 只有当案件处于审核未通过状态时才能进行修改
		StringBuilder msg = new StringBuilder();
		boolean success = false;
		String caseCode = toCaseInfoMapper.findcaseCodeByCcaiCode(acase.getCcaiCode());
		if(StringUtils.isBlank(caseCode)){
			msg.append("未获取到成交报告[")
					.append(acase.getCcaiCode())
					.append("]对应的案件信息!");
		}else{
			ToCase toCase = tocaseMapper.findToCaseByCaseCode(caseCode);
			//TODO 只有当案件信息为审核未通过才能进行修改
			if(CaseStatusEnum.BHCCAI.getCode().equals(toCase.getStatus())){
				boolean hasupdate = false;
				//修改贷款 过户权证信息
				if(acase.getParticipants()!=null&&acase.getParticipants().size()>0){
					hasupdate = updateParticipant(caseCode,acase.getParticipants(),toCase);
					success = true;
				}
				//修改附件信息 不存在失败的情况
				if(acase.getAttachments()!=null&&acase.getAttachments().size()>0){
					updateAttachments(caseCode,acase.getCcaiCode(),acase.getAttachments(),msg);
					hasupdate = true;
					success = true;
				}
				//未进行修改 给出提示
				if(!hasupdate){
					msg.append("目前修改仅支持过户、贷款权证信息修改和附件信息修改");
				}else{
					//TODO 根据案件状态要触发对应的流程环节操作
				}
			}else{
				msg.append("案件状态不是审核未通过，不能进行调整!");
			}
		}
		CcaiServiceResult result = new CcaiServiceResult();
		result.setSuccess(success);
		result.setCode(success?"00":"99");
		result.setMessage(msg.toString());
		return result;
	}
	
	/**
	 * 获取caseCode方法
	 * @return
	 */
	private String getCaseCode(CcaiImportCase acase){
		StringBuilder s = new StringBuilder();
		//TODO 各个城市的案件头
		switch (acase.getCity()){
			case "120000": s.append("ZY-TJ-");//天津
			case "110000":s.append("ZY-BJ-");//北京
			default:s.append("CCAICaseCode");
		}
		s.append(DateUtil.getFormatDate(new Date(), "yyyyMM"));
		s.append(tocaseMapper.nextCaseCodeNumber());
		return s.toString();
	}
	/**
	 * TODO 添加case信息
	 * T_TO_CASE
	 * @param acase
	 */
	private void addCase(String caseCode,CcaiImportCase acase){
		ToCase tocase=new ToCase();
		tocase.setCcaiId(acase.getCcaiId());
		tocase.setCcaiCode(acase.getCcaiCode());
		tocase.setCaseCode(caseCode);
		tocase.setCity(acase.getCity());
		tocase.setCreateTime(acase.getCreateTime());//该信息还是要根据传入来设置
		tocase.setCaseProperty(CasePropertyEnum.TPZT.getCode());  // 指定为在途单
		for(CcaiImportCaseInfo pa : acase.getParticipants()){
			if(CaseSyncParticipantEnum.WARRANT.getCode().equals(pa.getPosition())){
				//获取交易顾问信息
				User user = getUserByUserNameOrEmployeeCode(pa.getUserName());
				if(user!=null){
					tocase.setLeadingProcessId(user.getId());//案件负责人 交易顾问ID
				}else{
					throw new BusinessException("权证专员"+pa.getUserName()+"信息不存在");
				}
				Org org = uamUserOrgService.getOrgByCode(pa.getGrpCode());
				if(org!=null){
					tocase.setOrgId(org.getId());//组别ID 上海原有逻辑为：组织编码的父级编码是区域部门编码
					tocase.setDistrictId(org.getParentId());//分行ID 上海原有逻辑为：区域部门编码
				}else{
					throw new BusinessException("权证专员所在部门编码不存在！");
				}
			}
		}
		//业务开始后 进行调整
		tocase.setStatus(CaseStatusEnum.WJD.getCode());//TODO  虽然是未分单 新系统代表未接单
		tocase.setCaseOrigin(CaseOriginEnum.CCAI.getCode());//设置信息来源
		tocaseMapper.insertSelective(tocase);
		//TODO 交易类型 付款类型的处理
		//TODO 发起首次跟进流程
	}

	/**
	 * 修改成交报告 过户、贷款权证相关信息
	 * @param caseCode
	 * @param participants
	 * @param toCase
	 * @return 是否有修改
	 */
	private boolean updateParticipant(String caseCode,List<CcaiImportCaseInfo> participants,ToCase toCase){
		ToCaseInfo caseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
		List<ToCaseParticipant> nowpa = toCaseParticipantMapper.selectByCaseCode(caseCode);
		//TODO BUG 当一个案件有多个贷款权证或过户权证时，修改会出现问题 by :yinchao 2017-8-22
		Map<String, ToCaseParticipant> temp = new HashMap<>();//根据参与人类型进行缓存 便于修改
		for(ToCaseParticipant to:nowpa){
			temp.put(to.getPosition(), to);
		}
		boolean hasupdate = false;
		for(CcaiImportCaseInfo pa : participants){
			if(CaseSyncParticipantEnum.WARRANT.getCode().equals(pa.getPosition())){
				//过户权证处理
				//部门主管信息更改
				if(StringUtils.isNotBlank(pa.getGrpMgrUserName())){
					User manager = getUserByUserNameOrEmployeeCode(pa.getGrpMgrUserName());
					if(manager!=null){
						caseInfo.setRequireProcessorId(manager.getId());//请求处理人即交易主管 进行分单操作
					}
				}
				//过户权证信息更改
				if(StringUtils.isNotBlank(pa.getUserName())){
					//获取交易顾问信息
					User user = getUserByUserNameOrEmployeeCode(pa.getUserName());
					if(user!=null){
						toCase.setLeadingProcessId(user.getId());//案件负责人 交易顾问ID
					}
					caseInfo.setDispatchTime(new Date());//分单时间
				}
				//过户权证部门编码修改
				if(StringUtils.isNotBlank(pa.getGrpCode())){
					Org org = uamUserOrgService.getOrgByCode(pa.getGrpCode());
					if(org!=null){
						toCase.setOrgId(org.getId());//TODO 组织编码的父级编码是区域编码
						toCase.setDistrictId(org.getParentId());//区域编码
					}else{
						throw new BusinessException("过户权证部门编码不存在!");
					}
					caseInfo.setArCode(pa.getGrpCode());
					caseInfo.setTargetCode(pa.getGrpCode());//更换目标部门
				}
				if(StringUtils.isNotBlank(pa.getGrpName())){
					caseInfo.setArName(pa.getGrpName());
				}
				hasupdate = true;
			}
			if(hasupdate){
				toCaseInfoMapper.updateByPrimaryKeySelective(caseInfo);
				tocaseMapper.updateByPrimaryKeySelective(toCase);
			}
			//过户权证 贷款权证 信息可以修改
			if(CaseSyncParticipantEnum.WARRANT.getCode().equals(pa.getPosition())
					|| CaseSyncParticipantEnum.LOAN.getCode().equals(pa.getPosition())){
				//参与人表信息更新
				ToCaseParticipant update = null;
				if(temp.containsKey(pa.getPosition())){
					update = temp.get(pa.getPosition());
				}
				ToCaseParticipant t = buildParticipant(caseCode,toCase.getCcaiCode(),pa);
				if(update !=null){
					t.setPkid(update.getPkid());
					toCaseParticipantMapper.updateByPrimaryKeySelective(t);
				}else{
					toCaseParticipantMapper.insertSelective(t);
				}
				hasupdate = true;
			}
		}
		return hasupdate;
	}

	/**
	 * TODO 添加caseInfo信息
	 * T_TO_CASE_INFO
	 * @param caseCode
	 * @param acase
	 */
	private void addCaseInfo(String caseCode,CcaiImportCase acase){
		ToCaseInfo caseInfo = new ToCaseInfo();
		caseInfo.setCcaiCode(acase.getCcaiCode());
		caseInfo.setCaseCode(caseCode);
		caseInfo.setImportTime(new Date());
		caseInfo.setTradeType(acase.getTradeType());
		caseInfo.setPayType(acase.getPayType());
		caseInfo.setApplyid(acase.getApplyId());
		//同步案件相关人员信息
		for(CcaiImportCaseInfo pa : acase.getParticipants()){
			if(CaseSyncParticipantEnum.AGENT.getCode().equals(pa.getPosition())){
				caseInfoAgentSet(caseInfo,pa);
			}else if(CaseSyncParticipantEnum.WARRANT.getCode().equals(pa.getPosition())){
				caseInfoWarrantSet(caseInfo,pa);
			}
			//新增参与人信息
			toCaseParticipantMapper.insertSelective(buildParticipant(caseCode,acase.getCcaiCode(),pa));
		}
		toCaseInfoMapper.insertSelective(caseInfo);
	}
	/**
	 * 将CcaiImportCaseInfo导入对象转换成系统用参与人对象
	 * @param caseCode
	 * @param ccaiCode
	 * @param pa
	 * @return
	 */
	private ToCaseParticipant buildParticipant(String caseCode,String ccaiCode,CcaiImportCaseInfo pa){
		ToCaseParticipant casepa = new ToCaseParticipant();
		casepa.setCaseCode(caseCode);
		casepa.setCcaiCode(ccaiCode);
		casepa.setUserName(pa.getUserName());
		casepa.setRealName(pa.getRealName());
		casepa.setMobile(pa.getMobile());
		casepa.setGrpCode(pa.getGrpCode());
		casepa.setGrpName(pa.getGrpName());
		casepa.setGrpMgrUsername(pa.getGrpMgrUserName());
		casepa.setGrpMgrRealname(pa.getGrpMgrRealName());
		casepa.setGrpMgrMobile(pa.getGrpMgrMobile());
		casepa.setPosition(pa.getPosition());
		return casepa;
	}
	/**
	 * 设置案件基本信息 经纪人相关信息
	 */
	private void caseInfoAgentSet(ToCaseInfo caseInfo,CcaiImportCaseInfo pa){
		//经纪人处理
		User agent = getUserByUserNameOrEmployeeCode(pa.getUserName());
		if(agent!=null){
			caseInfo.setAgentCode(agent.getId());
			caseInfo.setAgentPhone(agent.getMobile());
		}else{
			throw new BusinessException("经纪人["+pa.getUserName()+"]信息不存在");
		}
		caseInfo.setAgentName(pa.getRealName());
		caseInfo.setAgentUserName(pa.getUserName());
		caseInfo.setGrpCode(pa.getGrpCode());
		caseInfo.setGrpName(pa.getGrpName());
		//TODO 管理人相关信息应用
	}
	
	/**
	 * 设置案件基本信息 过户权证相关 信息
	 */
	private void caseInfoWarrantSet(ToCaseInfo caseInfo,CcaiImportCaseInfo pa){
		User manager = getUserByUserNameOrEmployeeCode(pa.getGrpMgrUserName());
		if(manager!=null){
			caseInfo.setRequireProcessorId(manager.getId());//请求处理人即交易主管 进行分单操作
		}else{
			throw new BusinessException("过户权证主管["+pa.getGrpMgrUserName()+"]信息不存在");
		}
		//TODO 带过来权证信息 则直接进行分单？
		caseInfo.setIsResponsed("1");//是否响应 分单
		caseInfo.setResDate(new Date());//分单响应时间
		caseInfo.setDispatchTime(new Date());//分单时间
		caseInfo.setArCode(pa.getGrpCode());
		caseInfo.setArName(pa.getGrpName());
		caseInfo.setTargetCode(pa.getGrpCode());//目标组别设置为权证组别 因为项目中不再需要手动分单
	}
	
	/**
	 * TODO 添加客户信息信息
	 * 包含上家 下家
	 * T_TG_GUEST_INFO
	 * @param caseCode
	 * @param ccaiCode
	 * @param guests
	 */
	private void addGuestsInfo(String caseCode,String ccaiCode,List<CcaiImportCaseGuest> guests){
		if(guests.size()<2) throw new BusinessException("客户信息不正确");
		for(CcaiImportCaseGuest g : guests){
			TgGuestInfo guest = new TgGuestInfo();
			guest.setGuestCode(g.getId());
			guest.setCaseCode(caseCode);
			guest.setCcaiCode(ccaiCode);
			guest.setGuestName(g.getName());
			guest.setGuestPhone(g.getMobile());
			guest.setIdentifyType(g.getCertType());
			guest.setIdentifyNumber(g.getCertCode());
			guest.setTransPosition(g.getPosition());
			tgGuestInfoMapper.insertSelective(guest);
		}
	}

	/**
	 * TODO 添加案件物业信息
	 * T_TO_PROPERTY_INFO
	 * @param caseCode
	 * @param ccaiCode
	 * @param imp
	 */
	private void addProperty(String caseCode,String ccaiCode,CcaiImportCaseProperty imp){
		toPropertyInfoMapper.insertSelective(buildProperty(caseCode,ccaiCode,imp));
	}
	/**
	 * 根据CCAI传入的导入信息 
	 * 构造交易系统用到的物业信息
	 * @param caseCode
	 * @param ccaiCode
	 * @param imp
	 * @return
	 */
	private ToPropertyInfo buildProperty(String caseCode,String ccaiCode,CcaiImportCaseProperty imp){
		ToPropertyInfo property = new ToPropertyInfo();
		property.setPropertyCode(imp.getCode());//房源编码
		property.setPropertyAgentId(imp.getId());//房源ID
		property.setCcaiCode(ccaiCode);//CCAI成交报告编号
		property.setCaseCode(caseCode);
		property.setCtmAddr(imp.getAddress());//房屋地址描述
		property.setPropertyAddr(imp.getAddress());//房屋地址描述
		property.setDistCode(imp.getDistrict());//房屋所属行政区域 国标 精确到区域
		//非必填项 为空则设置为null
		property.setPropertyType(StringUtils.isBlank(imp.getPropertyType())?null:imp.getPropertyType());//房屋类型
		property.setComment(StringUtils.isBlank(imp.getComment())?null:imp.getComment());//描述
		property.setFinishYear(StringUtils.isBlank(imp.getFinishYear())?null:DateUtil.strToFullDate(imp.getFinishYear()+"-01-01"));
		property.setLocateFloor(imp.getLocateFloor());//所在楼层
		property.setTotalFloor(imp.getTotalFloor());//总层高
		property.setSquare(imp.getSquare());//房源面积
		return property;
	}
	/**
	 * 修改物业信息
	 * @param caseCode
	 * @param ccaiCode
	 * @param property
	 * @param msg
	 * @return
	 */
	private boolean updateProperty(String caseCode,String ccaiCode,CcaiImportCaseProperty property,StringBuilder msg){
		ToPropertyInfo info = toPropertyInfoMapper.findPropertyByCcaiCode(ccaiCode);
		if(info==null){
			msg.append("未获取到成交报告[")
					.append(ccaiCode)
					.append("]对应的物业信息!\r\n");
			return false;
		}else{
			ToPropertyInfo update = buildProperty(caseCode, ccaiCode, property);
			update.setPkid(info.getPkid());
			toPropertyInfoMapper.updateByPrimaryKeySelective(update);
			msg.append("物业信息更新成功!\r\n");
			return true;
		}
	}
	/**
	 * 同步添加CCAI附件信息
	 * @param caseCode
	 * @param ccaiCode
	 * @param attachments
	 */
	private void addAttachments(String caseCode,String ccaiCode,List<CcaiImportAttachment> attachments){
		for(CcaiImportAttachment a : attachments){
			if(validateAttachment(a)){
				toCcaiAttachmentMapper.insertSelective(buildCcaiAttachment(caseCode,ccaiCode,a));
			}else{
				throw new BusinessException("同步附件信息不完整!");
			}
		}
	}
	/**
	 * 将导入信息转换为交易系统CCAI附件信息
	 * @param caseCode
	 * @param ccaiCode
	 * @param a
	 * @return
	 */
	private ToCcaiAttachment buildCcaiAttachment(String caseCode,String ccaiCode,CcaiImportAttachment a){
		ToCcaiAttachment attachment = new ToCcaiAttachment();
		attachment.setCaseCode(caseCode);
		attachment.setCcaiCode(ccaiCode);
		attachment.setCcaiFileid(a.getId());
		attachment.setFileCat(a.getType());
		attachment.setFileName(a.getName());
		attachment.setUrl(a.getUrl());
		attachment.setUploadTime(a.getUploadTime());
		attachment.setAvailable("Y");
		return attachment;
	}
	/**
	 * 修改CCAI导入附件信息
	 * @param caseCode
	 * @param ccaiCode
	 * @param attachments
	 * @param msg
	 */
	private void updateAttachments(String caseCode,String ccaiCode,List<CcaiImportAttachment> attachments,StringBuilder msg){
		List<ToCcaiAttachment> list = toCcaiAttachmentMapper.findAttachmentsByCcaiCode(ccaiCode);
		if(list==null||list.size()==0){
			addAttachments(caseCode, ccaiCode, attachments);
		}else{
			Map<String,ToCcaiAttachment> temp = new HashMap<>();//缓存已有的文件列表
			for(ToCcaiAttachment l : list){
				temp.put(l.getCcaiFileid(), l);
			}
			for(CcaiImportAttachment a:attachments){
				ToCcaiAttachment attachment = buildCcaiAttachment(caseCode, ccaiCode, a);
				//存在则修改
				if(temp.containsKey(a.getId())){
					Long pkid = temp.get(a.getId()).getPkid();
					//附件类型为-1 则删除 直接将数据删除 不进行保存
					if(a.getType().equals("-1")){
						toCcaiAttachmentMapper.deleteByPrimaryKey(pkid);
					}else{
						attachment.setPkid(pkid);
						toCcaiAttachmentMapper.updateByPrimaryKeySelective(attachment);
					}
				}else{
					if(validateAttachment(a)){
						toCcaiAttachmentMapper.insertSelective(attachment);
					}else{
						throw new BusinessException("同步附件信息不完整!");
					}
				}
			}
		}
		msg.append("附件信息更新成功!\r\n");
	}
	/**
	 * 校验附件信息
	 * @param a
	 * @return
	 */
	private boolean validateAttachment(CcaiImportAttachment a){
		if(a==null || StringUtils.isBlank(a.getId()) 
				|| StringUtils.isBlank(a.getName())
				|| StringUtils.isBlank(a.getType())
				|| StringUtils.isBlank(a.getUrl())
				|| a.getUploadTime()==null){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 根据域账号 或者员工编号获取用户信息
	 * @param identity
	 * @return
	 */
	private User getUserByUserNameOrEmployeeCode(String identity){
		User user = uamUserOrgService.getUserByUsername(identity);
		if(user==null){
			user = uamUserOrgService.getUserByEmployeeCode(identity);
		}
		return user;
	}
	
}
