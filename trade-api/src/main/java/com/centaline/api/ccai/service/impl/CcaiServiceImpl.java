package com.centaline.api.ccai.service.impl;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.api.ccai.listener.FlowWorkListener;
import com.centaline.api.ccai.service.CcaiService;
import com.centaline.api.ccai.vo.*;
import com.centaline.api.common.vo.CcaiServiceResult;
import com.centaline.api.validate.group.NormalGroup;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToCcaiAttachment;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.*;
import com.centaline.trans.common.repository.TgGuestInfoMapper;
import com.centaline.trans.common.repository.ToCcaiAttachmentMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.eloan.entity.ToAppRecordInfo;
import com.centaline.trans.eloan.entity.ToSelfAppInfo;
import com.centaline.trans.eloan.service.ToSelfAppInfoService;
import com.centaline.trans.engine.WorkFlowConstant;
import com.centaline.trans.engine.bean.ExecuteAction;
import com.centaline.trans.engine.core.WorkFlowEngine;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.vo.ExecutionVo;
import com.centaline.trans.task.entity.ActRuEventSubScr;
import com.centaline.trans.task.repository.ActRuEventSubScrMapper;
import com.centaline.trans.utils.DateUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * ccai案件导入实现类
 *
 * @author yinchao
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
	private UamUserOrgService uamUserOrgService;//用户信息
	@Autowired
	private ToCaseParticipantMapper toCaseParticipantMapper;//案件参与人信息
	
	@Autowired
	private ToSelfAppInfoService toSelfAppInfoService; //自办贷款评估信息
	
	@Autowired
	private JmsMessagingTemplate jmsTemplate; //activemq 消息队列
	
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	
	@Autowired
	private ActRuEventSubScrMapper actRuEventSubScrMapper;
	
	@Autowired
	private WorkFlowEngine engine;//该处使用engine 否则无法进行访问流程引擎平台

	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	;//Hibernate校验工具

	@Override
	public CcaiServiceResult importCase(CaseImport acase) {
		CcaiServiceResult result = new CcaiServiceResult();
		String caseCode = getCaseCode(acase);
		//直接将导入的信息插入到对应的数据表中
		//案件信息导入
		addCase(caseCode, acase);
		//案件基本信息导入
		addCaseInfo(caseCode, acase);
		//案件物业信息导入
		addProperty(caseCode, acase.getCcaiCode(), acase.getProperty());
		//案件客户信息导入
		addGuestsInfo(caseCode, acase.getCcaiCode(), acase.getGuests());
		//案件秘书信息天津
		addAssistant(caseCode,acase);
		//案件附件信息导入
		addAttachments(caseCode, acase.getCcaiCode(), acase.getAttachments());
		result.setSuccess(true);
		result.setMessage("同步成功!");
		result.setCode("00");
		//将案件编号 放入消息队列中
		MQCaseMessage message = new MQCaseMessage(caseCode, MQCaseMessage.STARTFLOW_TYPE);
		jmsTemplate.convertAndSend(FlowWorkListener.getCaseQueueName(), message);

		return result;
	}

	@Override
	public boolean isExistCcaiCode(String ccaiCode) {
		int count = toCaseInfoMapper.isExistCcaiCode(ccaiCode);
		return count > 0;
	}

	@Override
	public CcaiServiceResult repealCase(CaseRepealImport repealInfo) {
		//TODO 更多的业务逻辑后续根据需要添加
		CcaiServiceResult result = new CcaiServiceResult();
		String caseCode = toCaseInfoMapper.findcaseCodeByCcaiCode(repealInfo.getCcaiCode());
		//将案件编号 放入消息队列中
		MQCaseMessage message = new MQCaseMessage(caseCode, MQCaseMessage.REPEAL_TYPE);
		jmsTemplate.convertAndSend(FlowWorkListener.getCaseQueueName(), message);
		result.setSuccess(true);
		result.setMessage("");
		result.setCode("00");
		return result;
	}

	@Override
	public CcaiServiceResult updateCase(CaseImport acase) {
		String caseCode = toCaseInfoMapper.findcaseCodeByCcaiCode(acase.getCcaiCode());
		if (StringUtils.isBlank(caseCode)) {
			throw new BusinessException("未获取到成交报告[" + acase.getCcaiCode() + "]对应的案件信息!");
		} else {
			ToCase ca = tocaseMapper.findToCaseByCaseCode(caseCode);
			StringBuilder msg = new StringBuilder();
			//修改物业信息
			if (acase.getProperty() != null) {
				updateProperty(caseCode, acase.getCcaiCode(), acase.getProperty());
				msg.append("物业信息更新成功!\r\n");
			}
			//修改参与人信息
			if (acase.getParticipants() != null && acase.getParticipants().size() > 0) {
				updateParticipant(acase.getParticipants(), ca);
				msg.append("参与人信息更新成功!\r\n");
			}
			//修改客户信息
			if (acase.getGuests() != null && acase.getGuests().size() > 0) {
				updateGuest(ca.getCaseCode(), ca.getCcaiCode(), acase.getGuests());
				msg.append("客户信息更新成功!\r\n");
			}
			//修改附件信息 不存在失败的情况
			if (acase.getAttachments() != null && acase.getAttachments().size() > 0) {
				updateAttachments(caseCode, acase.getCcaiCode(), acase.getAttachments());
				msg.append("附件信息更新成功!\r\n");
			}
			CcaiServiceResult result = new CcaiServiceResult();
			if (msg.length() > 0) {
				result.setSuccess(true);
				result.setCode("00");
				result.setMessage(msg.toString());
			} else {
				result.setSuccess(false);
				result.setCode("99");
				result.setMessage("没有信息被修改了!仅支持物业、客户、参与人及附件信息的修改");
			}

			return result;
		}
	}

	@Override
	public CcaiServiceResult updateCaseAndFlow(CaseImport acase) {
		String caseCode = toCaseInfoMapper.findcaseCodeByCcaiCode(acase.getCcaiCode());
		if (StringUtils.isBlank(caseCode)) {
			throw new BusinessException("未获取到成交报告[" + acase.getCcaiCode() + "]对应的案件信息!");
		} else {
			ToCase toCase = tocaseMapper.findToCaseByCaseCode(caseCode);
			//只有当案件信息为审核未通过才能进行修改
			if (CaseStatusEnum.BHCCAI.getCode().equals(toCase.getStatus())) {
				CcaiServiceResult result = updateCase(acase);
				//操作成功，将案件放入消息队列
				if (result.isSuccess()) {
					//将案件编号 放入消息队列中
					MQCaseMessage message = new MQCaseMessage(caseCode, MQCaseMessage.UPDATEFLOW_TYPE);
					jmsTemplate.convertAndSend(FlowWorkListener.getCaseQueueName(), message);
				}
				return result;
			} else {
				throw new BusinessException("案件状态不是驳回状态，不能进行调整!");
			}
		}
	}

	/**
	 * 获取caseCode方法
	 *
	 * @return
	 */
	private String getCaseCode(CaseImport acase) {
		StringBuilder s = new StringBuilder();
		//TODO 各个城市的案件头
		switch (acase.getCity()) {
			case "120000":
				s.append("ZY-TJ-");
				break;//天津
			case "110000":
				s.append("ZY-BJ-");
				break;//北京
			default:
				s.append("CCAICaseCode");
				break;
		}
		s.append(DateUtil.getFormatDate(new Date(), "yyyyMM"));
		s.append(tocaseMapper.nextCaseCodeNumber());
		return s.toString();
	}

	/**
	 * TODO 添加case信息
	 * T_TO_CASE
	 *
	 * @param acase
	 */
	private void addCase(String caseCode, CaseImport acase) {
		ToCase tocase = new ToCase();
		tocase.setCcaiId(acase.getCcaiId());
		tocase.setCcaiCode(acase.getCcaiCode());
		tocase.setCaseCode(caseCode);
		tocase.setCity(acase.getCity());
		tocase.setCreateTime(new Date());
		tocase.setCaseProperty(CasePropertyEnum.TPZT.getCode());  // 指定为在途单
		tocase.setStatus(CaseStatusEnum.WJD.getCode());//设置状态为未接单
		tocase.setCaseOrigin(CaseOriginEnum.CCAI.getCode());//设置信息来源
		//选择出案件主办 有贷款权证则优先贷款权证，否则为过户权证
		CaseParticipantImport owner = null;
		for (CaseParticipantImport pa : acase.getParticipants()) {
			if (owner == null && CaseParticipantEnum.WARRANT.getCode().equals(pa.getPosition())) {
				owner = pa;
			} else if (CaseParticipantEnum.LOAN.getCode().equals(pa.getPosition())) {
				owner = pa;
				break;
			}
		}
		if (owner != null) {
			//获取交易顾问信息
			User user = getUserByUserNameOrEmployeeCode(owner.getUserName());
			if (user != null) {
				tocase.setLeadingProcessId(user.getId());//案件负责人 交易顾问ID
			} else {
				throw new BusinessException("权证[" + owner.getUserName() + "]信息不存在");
			}
			Org org = uamUserOrgService.getOrgByCode(owner.getGrpCode());
			if (org != null) {
				tocase.setOrgId(org.getId());//组别ID 上海原有逻辑为：组织编码的父级编码是区域部门编码
				tocase.setDistrictId(org.getParentId());//分行ID （CCAI中到权证部门或者誉萃）上海原有逻辑为：区域部门编码
			} else {
				throw new BusinessException("权证所在部门编码不存在！");
			}
		}else{
			throw new BusinessException("权证信息不存在！");
		}
		tocaseMapper.insertSelective(tocase);
	}

	/**
	 * 修改成交报告 参与人员相关信息
	 *
	 * @param participants 新的案件参与人信息
	 * @param toCase       案件信息
	 * @return 是否成功
	 */
	private boolean updateParticipant(List<CaseParticipantImport> participants, ToCase toCase) {
		ToCaseParticipant param = new ToCaseParticipant();
		param.setAvailable("Y");
		param.setCcaiCode(toCase.getCcaiCode());
		//获取并更新原有的参与人信息表
		for (CaseParticipantImport pa : participants) {
			//秘书信息不进行修改
			if (CaseParticipantEnum.SECRETARY.getCode().equals(pa.getPosition())) continue;
			//Assignid 不为空才进行修改或新增
			if (StringUtils.isNotBlank(pa.getAssignId())) {
				param.setAssignId(pa.getAssignId());
				List<ToCaseParticipant> olds = toCaseParticipantMapper.selectByCondition(param);
				if (olds != null && olds.size() > 0) {
					ToCaseParticipant update = buildParticipant(toCase.getCaseCode(), toCase.getCcaiCode(), pa);
					update.setPkid(olds.get(0).getPkid());
					toCaseParticipantMapper.updateByPrimaryKeySelective(update);
				} else {
					//未匹配到的信息进行校验和新增
					StringBuilder msg = new StringBuilder();
					buildErrorMessage(validator.validate(pa, NormalGroup.class, Default.class), msg, "参与人");
					if (msg.length() > 0) {
						throw new BusinessException(msg.toString());
					} else {
						toCaseParticipantMapper.insertSelective(buildParticipant(toCase.getCaseCode(), toCase.getCcaiCode(), pa));
					}
				}
			} else {
				throw new BusinessException("未获取到对应的AssignId[" + pa.getAssignId() + "]信息");
			}
		}
		//未接单 或 驳回CCAI的成交报告 需要修改 相应的 案件人员配置信息
		if (CaseStatusEnum.WJD.getCode().equals(toCase.getStatus())
				|| CaseStatusEnum.BHCCAI.getCode().equals(toCase.getStatus())) {
			updateCaseWorker(toCase);
		}
		return true;
	}

	/**
	 * TODO 添加caseInfo信息
	 * T_TO_CASE_INFO
	 *
	 * @param caseCode
	 * @param acase
	 */
	private void addCaseInfo(String caseCode, CaseImport acase) {
		ToCaseInfo caseInfo = new ToCaseInfo();
		caseInfo.setCcaiCode(acase.getCcaiCode());
		caseInfo.setCaseCode(caseCode);
		caseInfo.setImportTime(new Date());
		caseInfo.setTradeType(acase.getTradeType());
		caseInfo.setPayType(acase.getPayType());
		caseInfo.setApplyid(acase.getApplyId());
		caseInfo.setReportTime(acase.getReportTime());
		caseInfo.setCcaiCreateTime(acase.getCreateTime());
		caseInfo.setCcaiUpdateTime(acase.getUpdateTime());
		//同步案件相关人员信息 同时选出案件主办 优先贷款 后过户
		CaseParticipantImport owner = null;
		for (CaseParticipantImport pa : acase.getParticipants()) {
			if (CaseParticipantEnum.AGENT.getCode().equals(pa.getPosition())) {
				caseInfoAgentSet(caseInfo, pa);
			} else if (owner == null && CaseParticipantEnum.WARRANT.getCode().equals(pa.getPosition())) {
				owner = pa;
			} else if(CaseParticipantEnum.LOAN.getCode().equals(pa.getPosition())){
				owner = pa;
			}
			//参与人保存处理
			ToCaseParticipant participant = buildParticipant(caseCode, acase.getCcaiCode(), pa);
			//补全参与人的手机号
			if (StringUtils.isBlank(participant.getMobile())) {
				User user = getUserByUserNameOrEmployeeCode(pa.getUserName());
				if (user != null) participant.setMobile(user.getMobile());
			}
			//补全主管信息
			if (StringUtils.isNotBlank(participant.getGrpMgrUsername())
					&& (StringUtils.isBlank(participant.getGrpMgrRealname()) || StringUtils.isBlank(participant.getGrpMgrMobile()))) {
				User user = getUserByUserNameOrEmployeeCode(pa.getGrpMgrUserName());
				if (user != null) {
					participant.setGrpMgrMobile(user.getMobile());
					participant.setGrpMgrRealname(user.getRealName());
				}
			}
			//新增参与人信息
			toCaseParticipantMapper.insertSelective(participant);
		}
		caseInfoOwnerSet(caseInfo, owner);
		toCaseInfoMapper.insertSelective(caseInfo);
	}

	/**
	 * 匹配案件对应的权证组别秘书
	 * 并保存到T_TO_CASE_PARTICIPANT表
	 * @param caseCode
	 * @param acase
	 */
	private void addAssistant(String caseCode,CaseImport acase){
		CaseParticipantImport owner = null;
		boolean hasAssistant = false;//导入信息是否包含权证秘书
		for (CaseParticipantImport pa : acase.getParticipants()) {

			if (owner == null && CaseParticipantEnum.WARRANT.getCode().equals(pa.getPosition())) {
				owner = pa;
			} else if (CaseParticipantEnum.LOAN.getCode().equals(pa.getPosition())) {
				owner = pa;
			} else if(CaseParticipantEnum.ASSISTANT.getCode().equals(pa.getPosition())){
				hasAssistant = true;
			}
		}
		//导入信息包含权证秘书 则不进行添加
		if(hasAssistant) return;
		//自动添加内勤助理
		Org org = uamUserOrgService.getOrgByCode(owner.getGrpCode());
		List<User> users = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(),TradeJobCodeEnum.ASSISTANT.getCode());
		if(users!=null && users.size()>0){
			User assistant = users.get(0);
			ToCaseParticipant casepa = new ToCaseParticipant();
			casepa.setAssignId(null);
			casepa.setCaseCode(caseCode);
			casepa.setCcaiCode(acase.getCcaiCode());
			casepa.setUserName(assistant.getUsername());
			casepa.setRealName(assistant.getRealName());
			casepa.setMobile(assistant.getMobile());
			casepa.setGrpCode(owner.getGrpCode());
			casepa.setGrpName(owner.getGrpName());
			casepa.setPosition(CaseParticipantEnum.ASSISTANT.getCode());
			casepa.setAvailable("Y");
			casepa.setOrigin("SYSTEM");//标记系统新增
			toCaseParticipantMapper.insertSelective(casepa);
		}else{
			throw new BusinessException("未获取到组别["+owner.getGrpCode()+"]对应的权证内勤信息");
		}
	}


	/**
	 * 将CcaiImportCaseInfo导入对象转换成系统用参与人对象
	 *
	 * @param caseCode
	 * @param ccaiCode
	 * @param pa
	 * @return
	 */
	private ToCaseParticipant buildParticipant(String caseCode, String ccaiCode, CaseParticipantImport pa) {
		ToCaseParticipant casepa = new ToCaseParticipant();
		casepa.setAssignId(pa.getAssignId());
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
		casepa.setAvailable("Y");
		casepa.setOrigin(CaseOriginEnum.CCAI.getCode());
		return casepa;
	}

	/**
	 * 设置案件基本信息 经纪人相关信息
	 */
	private void caseInfoAgentSet(ToCaseInfo caseInfo, CaseParticipantImport pa) {
		//经纪人处理
		User agent = getUserByUserNameOrEmployeeCode(pa.getUserName());
		if (agent != null) {
			caseInfo.setAgentCode(agent.getId());
			caseInfo.setAgentPhone(agent.getMobile());
			//自动补全信息
			if (StringUtils.isBlank(pa.getMobile())) {
				pa.setMobile(agent.getMobile());
			}
		} else {
			throw new BusinessException("经纪人[" + pa.getUserName() + "]信息不存在");
		}
		caseInfo.setAgentName(pa.getRealName());
		caseInfo.setAgentUserName(pa.getUserName());
		caseInfo.setGrpCode(pa.getGrpCode());
		caseInfo.setGrpName(pa.getGrpName());
		//TODO 自动补全管理人相关信息
		if (StringUtils.isBlank(pa.getGrpMgrRealName()) || StringUtils.isBlank(pa.getGrpMgrMobile())) {
			User manager = getUserByUserNameOrEmployeeCode(pa.getUserName());
			if (manager != null) {
				pa.setGrpMgrMobile(manager.getMobile());
				pa.setGrpMgrRealName(manager.getRealName());
			}
		}

	}

	/**
	 * 设置案件基本信息 过户权证相关 信息
	 */
	private void caseInfoOwnerSet(ToCaseInfo caseInfo, CaseParticipantImport pa) {
		if(pa == null){
			throw new BusinessException("权证信息不存在！");
		}
		User manager = getUserByUserNameOrEmployeeCode(pa.getGrpMgrUserName());
		if (manager != null) {
			caseInfo.setRequireProcessorId(manager.getId());//请求处理人即交易主管
		} else {
			throw new BusinessException("权证主管[" + pa.getGrpMgrUserName() + "]信息不存在");
		}
		//CCAI导入直接进行分单
		caseInfo.setIsResponsed("1");//是否响应 分单
		caseInfo.setResDate(new Date());//分单响应时间
		caseInfo.setDispatchTime(new Date());//分单时间
		caseInfo.setArCode(pa.getGrpCode());
		caseInfo.setArName(pa.getGrpName());
		caseInfo.setTargetCode(pa.getGrpCode());//目标组别设置为权证组别
	}

	/**
	 * TODO 添加客户信息信息
	 * 包含上家 下家
	 * T_TG_GUEST_INFO
	 *
	 * @param caseCode
	 * @param ccaiCode
	 * @param guests
	 */
	private void addGuestsInfo(String caseCode, String ccaiCode, List<CaseGuestImport> guests) {
		if (guests.size() < 2) throw new BusinessException("客户信息不正确");
		for (CaseGuestImport g : guests) {
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
	 *
	 * @param caseCode
	 * @param ccaiCode
	 * @param imp
	 */
	private void addProperty(String caseCode, String ccaiCode, CasePropertyImport imp) {
		toPropertyInfoMapper.insertSelective(buildProperty(caseCode, ccaiCode, imp));
	}

	/**
	 * 根据CCAI传入的导入信息
	 * 构造交易系统用到的物业信息
	 *
	 * @param caseCode
	 * @param ccaiCode
	 * @param imp
	 * @return
	 */
	private ToPropertyInfo buildProperty(String caseCode, String ccaiCode, CasePropertyImport imp) {
		ToPropertyInfo property = new ToPropertyInfo();
		property.setPropertyCode(imp.getCode());//房源编码
		property.setPropertyAgentId(imp.getId());//房源ID
		property.setCcaiCode(ccaiCode);//CCAI成交报告编号
		property.setCaseCode(caseCode);
		property.setCtmAddr(imp.getAddress());//房屋地址描述
		property.setPropertyAddr(imp.getAddress());//房屋地址描述
		property.setDistCode(imp.getDistrict());//房屋所属行政区域 国标 精确到区域
		//非必填项 为空则设置为null
		property.setPropertyType(StringUtils.isBlank(imp.getPropertyType()) ? null : imp.getPropertyType());//房屋类型
		property.setComment(StringUtils.isBlank(imp.getComment()) ? null : imp.getComment());//描述
		property.setFinishYear(StringUtils.isBlank(imp.getFinishYear()) ? null : DateUtil.strToFullDate(imp.getFinishYear() + "-01-01"));
		property.setLocateFloor(imp.getLocateFloor());//所在楼层
		property.setTotalFloor(imp.getTotalFloor());//总层高
		property.setSquare(imp.getSquare());//房源面积
		return property;
	}

	/**
	 * 修改物业信息
	 *
	 * @param caseCode
	 * @param ccaiCode
	 * @param property
	 * @param msg
	 * @return
	 */
	private boolean updateProperty(String caseCode, String ccaiCode, CasePropertyImport property) {
		ToPropertyInfo info = toPropertyInfoMapper.findPropertyByCcaiCode(ccaiCode);
		if (info == null) {
			throw new BusinessException("未获取到成交报告[" + ccaiCode + "]对应的物业信息!");
		} else {
			ToPropertyInfo update = buildProperty(caseCode, ccaiCode, property);
			update.setPkid(info.getPkid());
			update.setPropertyAddr(null);//不修改跟进后的物业地址信息
			toPropertyInfoMapper.updateByPrimaryKeySelective(update);
			return true;
		}
	}

	/**
	 * 同步添加CCAI附件信息
	 *
	 * @param caseCode
	 * @param ccaiCode
	 * @param attachments
	 */
	private void addAttachments(String caseCode, String ccaiCode, List<CaseAttachmentImport> attachments) {
		for (CaseAttachmentImport a : attachments) {
			if (validateAttachment(a)) {
				toCcaiAttachmentMapper.insertSelective(buildCcaiAttachment(caseCode, ccaiCode, a));
			} else {
				throw new BusinessException("同步附件信息不完整!");
			}
		}
	}

	/**
	 * 将导入信息转换为交易系统CCAI附件信息
	 *
	 * @param caseCode
	 * @param ccaiCode
	 * @param a
	 * @return
	 */
	private ToCcaiAttachment buildCcaiAttachment(String caseCode, String ccaiCode, CaseAttachmentImport a) {
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
	 *
	 * @param caseCode
	 * @param ccaiCode
	 * @param attachments
	 */
	private void updateAttachments(String caseCode, String ccaiCode, List<CaseAttachmentImport> attachments) {
		List<ToCcaiAttachment> list = toCcaiAttachmentMapper.findAttachmentsByCcaiCode(ccaiCode);
		if (list == null || list.size() == 0) {
			addAttachments(caseCode, ccaiCode, attachments);
		} else {
			Map<String, ToCcaiAttachment> temp = new HashMap<>();//缓存已有的文件列表
			for (ToCcaiAttachment l : list) {
				temp.put(l.getCcaiFileid(), l);
			}
			for (CaseAttachmentImport a : attachments) {
				ToCcaiAttachment attachment = buildCcaiAttachment(caseCode, ccaiCode, a);
				//存在则修改
				if (temp.containsKey(a.getId())) {
					Long pkid = temp.get(a.getId()).getPkid();
					//附件类型为-1 则删除 直接将数据删除 不进行保存
					if (a.getType().equals("-1")) {
						toCcaiAttachmentMapper.deleteByPrimaryKey(pkid);
					} else {
						attachment.setPkid(pkid);
						toCcaiAttachmentMapper.updateByPrimaryKeySelective(attachment);
					}
				} else {
					if (validateAttachment(a)) {
						toCcaiAttachmentMapper.insertSelective(attachment);
					} else {
						throw new BusinessException("同步附件信息不完整!");
					}
				}
			}
		}
	}

	/**
	 * 校验附件信息
	 *
	 * @param a
	 * @return
	 */
	private boolean validateAttachment(CaseAttachmentImport a) {
		if (a == null || StringUtils.isBlank(a.getId())
				|| StringUtils.isBlank(a.getName())
				|| StringUtils.isBlank(a.getType())
				|| StringUtils.isBlank(a.getUrl())
				|| a.getUploadTime() == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 根据域账号 或者员工编号获取用户信息
	 *
	 * @param identity
	 * @return
	 */
	private User getUserByUserNameOrEmployeeCode(String identity) {
		User user = uamUserOrgService.getUserByUsername(identity);
		if (user == null) {
			user = uamUserOrgService.getUserByEmployeeCode(identity);
		}
		return user;
	}

	/**
	 * 根据将校验信息的结果拼接到传入的msgBuilder中，
	 * 如果appendBefore不为null，则拼接到每个错误消息前
	 *
	 * @param validate
	 * @param msgBuilder
	 * @param appendBefore
	 * @param <T>
	 */
	private <T> void buildErrorMessage(Set<ConstraintViolation<T>> validates, StringBuilder msgBuilder, String appendBefore) {
		for (ConstraintViolation constraintViolation : validates) {
			msgBuilder.append(appendBefore).append(constraintViolation.getMessage()).append("\r\n");
		}
	}

	/**
	 * 修改案件参与人后
	 * 同步修改案件信息及案件表中设置的经纪人及案件主办信息
	 * 由于要优先贷款再过户权证 所以重新查询并处理
	 * @param toCase  案件信息
	 */
	private void updateCaseWorker(ToCase toCase) {
		ToCaseInfo caseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(toCase.getCaseCode());
		List<ToCaseParticipant> participants = toCaseParticipantMapper.selectByCaseCode(toCase.getCaseCode());
		ToCaseParticipant owner = null;
		ToCaseParticipant agent = null;
		for(ToCaseParticipant participant : participants){
			if(owner == null && CaseParticipantEnum.WARRANT.getCode().equals(participant.getPosition())){
				owner = participant;
			}else if(CaseParticipantEnum.LOAN.getCode().equals(participant.getPosition())){
				owner = participant;
			}else if(CaseParticipantEnum.AGENT.getCode().equals(participant.getPosition())){
				agent = participant;
			}
		}
		//案件负责人信息比对和修改
		User user = getUserByUserNameOrEmployeeCode(owner.getUserName());
		if(user!=null){
			//判断案件负责人是否与参与人一致
			if(!(user.getId().equals(toCase.getLeadingProcessId()))){
				toCase.setLeadingProcessId(user.getId());//案件负责人
				caseInfo.setDispatchTime(new Date());//更新 分单时间
			}
		}else{
			throw new BusinessException("未获取到新权证["+owner.getUserName()+"]的信息");
		}
		//案件对应部门 信息比对和修改
		Org org = uamUserOrgService.getOrgByCode(owner.getGrpCode());
		if (org != null) {
			if(!toCase.getOrgId().equals(org.getId())){
				toCase.setOrgId(org.getId());//更新组别信息
				toCase.setDistrictId(org.getParentId());//区域编码
				caseInfo.setArCode(owner.getGrpCode());
				caseInfo.setTargetCode(owner.getGrpCode());//更换目标部门
				caseInfo.setArName(owner.getGrpName());
			}
		} else {
			throw new BusinessException("过户权证部门编码不存在!");
		}
		//过户权证主管信息比对和修改
		User manager = getUserByUserNameOrEmployeeCode(owner.getGrpMgrUsername());
		if(manager!=null){
			if(!manager.getId().equals(caseInfo.getRequireProcessorId())){
				caseInfo.setRequireProcessorId(manager.getId());
			}
		}else {
			throw new BusinessException("过户权证部门主管["+owner.getGrpMgrUsername()+"]的信息不存在!");
		}
		//经纪人相关信息处理
		User agentUser = getUserByUserNameOrEmployeeCode(agent.getUserName());
		if(agentUser!=null){
			if(!caseInfo.getAgentCode().equals(agentUser.getId())){
				caseInfo.setAgentCode(agentUser.getId());
				caseInfo.setAgentPhone(agentUser.getMobile());
				caseInfo.setAgentName(agentUser.getRealName());
				caseInfo.setAgentUserName(agent.getUserName());
			}
		}else{
			throw new BusinessException("经纪人["+agent.getUserName()+"]的信息不存在!");
		}
		//经纪人部门信息 比对修改
		Org agentOrg = uamUserOrgService.getOrgByCode(agent.getGrpCode());
		if (agentOrg != null) {
			if(!caseInfo.getGrpCode().equals(agentOrg.getOrgCode())){
				caseInfo.setGrpCode(agent.getGrpCode());
				caseInfo.setGrpName(agentOrg.getOrgName());
			}
		} else {
			throw new BusinessException("经纪人部门编码不存在!");
		}
		toCaseInfoMapper.updateByPrimaryKeySelective(caseInfo);
		tocaseMapper.updateByPrimaryKeySelective(toCase);
	}

	/**
	 * 更新客户信息
	 *
	 * @param caseCode 案件编号
	 * @param guests   被修改的客户信息
	 */
	private void updateGuest(String caseCode, String ccaiCode, List<CaseGuestImport> guests) {
		List<TgGuestInfo> olds = tgGuestInfoMapper.findTgGuestInfoByCaseCode(caseCode);
		if (olds == null || olds.size() == 0) throw new BusinessException("未获取到客户信息，无法修改");
		Map<String, TgGuestInfo> temp = new HashMap<>();
		//从数组 转换成map,以唯一识别code 为key方便修改
		olds.stream().forEach((guest) -> temp.put(guest.getGuestCode(), guest));
		for (CaseGuestImport guest : guests) {
			if (StringUtils.isBlank(guest.getId())) throw new BusinessException("未获取到客户唯一标识，无法进行修改!");
			if (temp.containsKey(guest.getId())) {
				TgGuestInfo update = temp.get(guest.getId());
				//先不进行类型修改 否则会导致没有上下家的情况
				// update.setTransPosition(StringUtils.isNotBlank(guest.getPosition())?guest.getPosition():null);
				update.setGuestName(StringUtils.isNotBlank(guest.getName()) ? guest.getName() : null);
				update.setGuestPhone(StringUtils.isNotBlank(guest.getMobile()) ? guest.getMobile() : null);
				update.setIdentifyType(StringUtils.isNotBlank(guest.getCertType()) ? guest.getCertType() : null);
				update.setIdentifyNumber(StringUtils.isNotBlank(guest.getCertCode()) ? guest.getCertCode() : null);
				tgGuestInfoMapper.updateByPrimaryKeySelective(update);
			} else {
				StringBuilder msg = new StringBuilder();
				buildErrorMessage(validator.validate(guest), msg, "客户");
				if (msg.length() > 0) {
					throw new BusinessException(msg.toString());
				} else {
					TgGuestInfo add = new TgGuestInfo();
					add.setGuestCode(guest.getId());
					add.setCaseCode(caseCode);
					add.setCcaiCode(ccaiCode);
					add.setGuestName(guest.getName());
					add.setGuestPhone(guest.getMobile());
					add.setIdentifyType(guest.getCertType());
					add.setIdentifyNumber(guest.getCertCode());
					add.setTransPosition(guest.getPosition());
					tgGuestInfoMapper.insertSelective(add);
				}
			}
		}
	}
	@Override
	public CcaiServiceResult importSelfDo(SelfDoImport info) {
		ToSelfAppInfo toSelfAppInfo1 = toSelfAppInfoService.getAppInfoByCCAICode(info.getCcaiCode());
		String caseCode = null;
		int count = 0;
		CcaiServiceResult result = new CcaiServiceResult();
		if(toSelfAppInfo1 != null){//权证经理驳回后的二次请求
			//只复制审核信息
			List <ToAppRecordInfo> listRecord = copyProperties1(info,toSelfAppInfo1);
			count = toSelfAppInfoService.saveBatchToAppRecordInfo(listRecord);
			if(count == 0){
				result.setSuccess(false);
				result.setMessage("同步失败!审批信息保存失败!");
				result.setCode("99");
				return result;
			}
		}else{
			
			ToSelfAppInfo toSelfAppInfo = new ToSelfAppInfo();
			try {
				toSelfAppInfo = copyProperties(info);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			caseCode = toSelfAppInfoService.addSelfAppInfo(toSelfAppInfo);
			if(StringUtils.isBlank(caseCode)){
				result.setSuccess(false);
				result.setMessage("同步失败!caceCode没查到!");
				result.setCode("99");
				return result;
			}
		}

		//将案件编号 放入消息队列中
		if(null == toSelfAppInfo1 ){
			MQCaseMessage message = new MQCaseMessage(caseCode, MQCaseMessage.LOAN_TYPE);
			jmsTemplate.convertAndSend(FlowWorkListener.getCaseQueueName(), message);
		}else{
			updateProcess(toSelfAppInfo1.getCaseCode());
		}
		result.setSuccess(true);
		result.setMessage("同步成功!");
		result.setCode("00");
		return result;
	}
	
	/**
	 * 向流程发送CCAI修改完成消息
	 * 并更改案件状态
	 *lujian
	 * @param caseCode
	 */
	private void updateProcess(String caseCode) {
		//获取流程信息
		ToWorkFlow wf = new ToWorkFlow();
		wf.setCaseCode(caseCode);
		wf.setBusinessKey(WorkFlowEnum.LOANANDASSE_PROCESS.getCode());
		ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if (wordkFlowDB != null) {
			// 发送消息
			ActRuEventSubScr event = new ActRuEventSubScr();
			event.setEventType(MessageEnum.CCAI_MODIFY_MSG.getEventType());
			event.setEventName(MessageEnum.CCAI_MODIFY_MSG.getName());
			event.setProcInstId(wordkFlowDB.getInstCode());
			event.setActivityId(EventTypeEnum.SELF_LOAN_MSG_EVENT_CATCH.getName());
			ExecuteAction action = new ExecuteAction();
			action.setAction(EventTypeEnum.SELF_LOAN_MSG_EVENT_CATCH.getEventType());
			action.setMessageName(MessageEnum.CCAI_MODIFY_MSG.getName());
			List<ActRuEventSubScr> subScrs = actRuEventSubScrMapper.listBySelective(event);
			if (CollectionUtils.isNotEmpty(subScrs)) {
				//设置流程引擎登录用户 否则无法访问REST接口
				User user = uamUserOrgService.getUserById(wordkFlowDB.getProcessOwner());
				engine.setAuthUserName(user.getUsername());
				//调用REST接口发送消息
				action.setExecutionId(subScrs.get(0).getExecutionId());
				engine.RESTfulWorkFlow(WorkFlowConstant.PUT_EXECUTE_KEY, ExecutionVo.class, action);
			}
		}
	}
	

	private List<ToAppRecordInfo> copyProperties1(SelfDoImport info,ToSelfAppInfo toSelfAppInfo1) {
		List<TaskInfo> listTask = info.getTasks();
		List<ToAppRecordInfo> tasks = new ArrayList<ToAppRecordInfo>();
		for (TaskInfo taskInfo : listTask) {
			ToAppRecordInfo toAppRecordInfo = new ToAppRecordInfo();
			toAppRecordInfo.setApplyRealName(taskInfo.getApplyRealName());
			toAppRecordInfo.setApplyUserName(taskInfo.getApplyUserName());
			toAppRecordInfo.setComment(taskInfo.getComment());
			toAppRecordInfo.setDealTime(taskInfo.getDealTime());
			toAppRecordInfo.setLevel(taskInfo.getLevel());
			toAppRecordInfo.setResult(taskInfo.getResult());
			toAppRecordInfo.setCreateTime(new Date());
			toAppRecordInfo.setSelfAppInfoId(toSelfAppInfo1.getPkid());
			tasks.add(toAppRecordInfo);
		}
		return tasks;
	}

	private ToSelfAppInfo copyProperties(SelfDoImport info) {
		ToSelfAppInfo toSelfAppInfo = new ToSelfAppInfo();
		toSelfAppInfo.setApplyTime(info.getApplyTime());
		toSelfAppInfo.setCcaiCode(info.getCcaiCode());
		toSelfAppInfo.setCity(info.getCity());
		toSelfAppInfo.setGrpCode(info.getGrpCode());
		toSelfAppInfo.setGrpName(info.getGrpName());
		toSelfAppInfo.setRealName(info.getRealName());
		toSelfAppInfo.setStatus(info.getStatus());
		toSelfAppInfo.setType(info.getType());
		toSelfAppInfo.setUserName(info.getUserName());
		List<TaskInfo> listTask = info.getTasks();
		List<ToAppRecordInfo> tasks = new ArrayList<ToAppRecordInfo>();
		for (TaskInfo taskInfo : listTask) {
			ToAppRecordInfo toAppRecordInfo = new ToAppRecordInfo();
			toAppRecordInfo.setApplyRealName(taskInfo.getApplyRealName());
			toAppRecordInfo.setApplyUserName(taskInfo.getApplyUserName());
			toAppRecordInfo.setComment(taskInfo.getComment());
			toAppRecordInfo.setDealTime(taskInfo.getDealTime());
			toAppRecordInfo.setLevel(taskInfo.getLevel());
			toAppRecordInfo.setResult(taskInfo.getResult());
			tasks.add(toAppRecordInfo);
		}
		toSelfAppInfo.setTasks(tasks);
		return toSelfAppInfo;
	}

	/**
	 * 导入退费评估信息
	 * lujian
	 */
	@Override
	public CcaiServiceResult importEvalRefund(EvalRefundImport info) {
		
		return null;
	}

}
