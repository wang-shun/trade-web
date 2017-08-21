package com.centaline.trans.cases.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.CcaiImportAttachment;
import com.centaline.trans.cases.entity.CcaiImportCase;
import com.centaline.trans.cases.entity.CcaiImportCaseGuest;
import com.centaline.trans.cases.entity.CcaiImportCaseInfo;
import com.centaline.trans.cases.entity.CcaiImportCaseProperty;
import com.centaline.trans.cases.entity.CcaiServiceResult;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.service.CcaiService;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToCcaiAttachment;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.CaseOriginEnum;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.CaseSyncParticipantEnum;
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
	private UamBasedataService uamBasedataService;//基础数据
	
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
		return count>0?true:false;
	}

	@Override
	public CcaiServiceResult updateCase(CcaiImportCase acase) {
		//案件信息修改的准则 附件信息可以随时修改
		StringBuilder msg = new StringBuilder();
		boolean success = false;
		String caseCode = toCaseInfoMapper.findcaseCodeByCcaiCode(acase.getCcaiCode());
		if(StringUtils.isBlank(caseCode)){
			msg.append("未获取到成交报告["+acase.getCcaiCode()+"]对应的案件信息!");
		}else{
			boolean hasupdate = false;
			//TODO 修改物业信息 只有案件处于未审核状态 才可以修改物业信息
			if(acase!=null&&acase.getProperty()!=null){
				success = updateProperty(caseCode,acase.getCcaiCode(),acase.getProperty(),msg);
				hasupdate = true;
			}
			//修改附件信息 不存在失败的情况
			if(acase!=null&&acase.getAttachments()!=null&&acase.getAttachments().size()>0){
				updateAttachments(caseCode,acase.getCcaiCode(),acase.getAttachments(),msg);
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
		//TODO 案件信息修改的准则 只有当案件处于审核未通过状态时才能进行修改
		StringBuilder msg = new StringBuilder();
		boolean success = false;
		String caseCode = toCaseInfoMapper.findcaseCodeByCcaiCode(acase.getCcaiCode());
		if(StringUtils.isBlank(caseCode)){
			msg.append("未获取到成交报告["+acase.getCcaiCode()+"]对应的案件信息!");
		}else{
			boolean hasupdate = false;
			//TODO 修改贷款 过户权证信息
			if(acase!=null&&acase.getParticipants()!=null&&acase.getParticipants().size()>0){
				ToCaseInfo caseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
				ToCase toCase = tocaseMapper.findToCaseByCaseCode(caseCode);
				for(CcaiImportCaseInfo pa : acase.getParticipants()){
					if(CaseSyncParticipantEnum.WARRANT.getCode().equals(pa.getPosition())){
						//过户权证处理
						//部门主管信息更改
						if(StringUtils.isNotBlank(pa.getGrpMgrUserName())){
							User manager = uamUserOrgService.getUserByUsername(pa.getGrpMgrUserName());
							if(manager!=null){
								caseInfo.setRequireProcessorId(manager.getId());//请求处理人即交易主管 进行分单操作
							}
						}
						//过户权证信息更改
						if(StringUtils.isNotBlank(pa.getUserName())){
							//获取交易顾问信息
							User user = uamUserOrgService.getUserByUsername(pa.getUserName());
							if(user!=null){
								toCase.setLeadingProcessId(user.getId());//案件负责人 交易顾问ID
							}
							caseInfo.setIsResponsed("1");//是否响应 分单
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
							
						}
						if(StringUtils.isNotBlank(pa.getGrpCode())){
							caseInfo.setArCode(pa.getGrpCode());
						}
						if(StringUtils.isNotBlank(pa.getGrpName())){
							caseInfo.setArName(pa.getGrpName());
						}
						hasupdate = true;
					}else if(CaseSyncParticipantEnum.LOAN.getCode().equals(pa.getPosition())){
						//TODO 贷款权证
						hasupdate = true;
					}else{
						//其他暂不处理
					}
				}
				if(hasupdate){
					toCaseInfoMapper.updateByPrimaryKeySelective(caseInfo);
					tocaseMapper.updateByPrimaryKeySelective(toCase);
				}
				success = true;
			}
			//修改附件信息 不存在失败的情况
			if(acase!=null&&acase.getAttachments()!=null&&acase.getAttachments().size()>0){
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
		if(acase.getCity().equals("120000")){
			//天津
			return uamBasedataService.nextSeqVal("ZY-TJ", DateUtil.getFormatDate(new Date(), "yyyyMM"));
		}else{
			return "CCAICaseCode"+DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss");
		}
		//
		//TODO 根据实际情况生成CaseCode
		
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
				User user = uamUserOrgService.getUserByUsername(pa.getUserName());
				if(user!=null){
					tocase.setLeadingProcessId(user.getId());//案件负责人 交易顾问ID
				}else{
					throw new BusinessException("权证专员"+pa.getUserName()+"信息不存在");
				}
				Org org = uamUserOrgService.getOrgByCode(pa.getGrpCode());
				if(org!=null){
					tocase.setOrgId(org.getId());//TODO 组织编码的父级编码是区域编码
					tocase.setDistrictId(org.getParentId());//区域编码
				}else{
					throw new BusinessException("经纪人所在部门编码不存在！");
				}
				
			}
		}
		//TODO 业务开始后 进行调整
		tocase.setStatus(CaseStatusEnum.WFD.getCode());// 状态[如果交易顾问id 为空时则为未分单, 如果交易顾问id 不为空则为已分单]
		tocase.setCaseOrigin(CaseOriginEnum.CCAI.getCode());//设置信息来源
		tocaseMapper.insertSelective(tocase);
		//TODO 交易类型 付款类型的处理
		//TODO 发起首次跟进流程
	}
	
	/**
	 * TODO 添加caseInfo信息
	 * T_TO_CASE_INFO
	 * @param info
	 */
	private void addCaseInfo(String caseCode,CcaiImportCase acase){
		ToCaseInfo caseInfo = new ToCaseInfo();
		caseInfo.setCcaiCode(acase.getCcaiCode());
		caseInfo.setCaseCode(caseCode);
		caseInfo.setImportTime(new Date());
		caseInfo.setTradeType(acase.getTradeType());
		caseInfo.setPayType(acase.getPayType());
		//同步案件相关人员信息
		for(CcaiImportCaseInfo pa : acase.getParticipants()){
			if(CaseSyncParticipantEnum.AGENT.getCode().equals(pa.getPosition())){
				//经纪人处理
				User agent = uamUserOrgService.getUserByUsername(pa.getUserName());
				if(agent!=null){
					caseInfo.setAgentCode(agent.getId());
				}else{
					throw new BusinessException("经纪人"+pa.getUserName()+"信息不存在");
				}
				caseInfo.setAgentName(pa.getRealName());
				caseInfo.setAgentUserName(pa.getUserName());
				caseInfo.setAgentPhone(pa.getMobile());
				caseInfo.setGrpCode(pa.getGrpCode());
				caseInfo.setGrpName(pa.getGrpName());
				//TODO 目标机构编码首次导入为业务组别的编码 后期会变成流程相关环节的部门信息
				caseInfo.setTargetCode(pa.getGrpCode());
				//TODO 管理人相关信息应用
			}else if(CaseSyncParticipantEnum.WARRANT.getCode().equals(pa.getPosition())){
				//过户权证处理
				User manager = uamUserOrgService.getUserByUsername(pa.getGrpMgrUserName());
				if(manager!=null){
					caseInfo.setRequireProcessorId(manager.getId());//请求处理人即交易主管 进行分单操作
				}else{
					throw new BusinessException("权证专员主管"+pa.getGrpMgrUserName()+"信息不存在");
				}
				//TODO 带过来权证信息 则直接进行分单？
				caseInfo.setIsResponsed("1");//是否响应 分单
				caseInfo.setDispatchTime(new Date());//分单时间
				caseInfo.setArCode(pa.getGrpCode());
				caseInfo.setArName(pa.getGrpName());
			}else if(CaseSyncParticipantEnum.AGENT.getCode().equals(pa.getPosition())){
				//TODO 贷款权证处理
			}else if(CaseSyncParticipantEnum.AGENT.getCode().equals(pa.getPosition())){
				//TODO 战区处理
			}else{
				//TODO 金融权证 或 其他 先忽略
			}
		}
		//TODO 是否响应等其他信息
		toCaseInfoMapper.insertSelective(caseInfo);
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
	 * @param property
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
			msg.append("未获取到成交报告["+ccaiCode+"]对应的物业信息!\r\n");
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
					//附件类型为-1 则删除
					if(a.getType()=="-1"){
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
}
