package com.centaline.api.ccai.cases.service.impl;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.api.ccai.cases.service.CcaiService;
import com.centaline.api.ccai.cases.vo.*;
import com.centaline.api.common.vo.CcaiServiceResult;
import com.centaline.api.enums.CaseSyncParticipantEnum;
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
import com.centaline.trans.common.enums.CaseOriginEnum;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.repository.TgGuestInfoMapper;
import com.centaline.trans.common.repository.ToCcaiAttachmentMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
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

	private Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();;//Hibernate校验工具

	@Override
	public CcaiServiceResult importCase(CcaiImportCase acase) {
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
		//案件附件信息导入
		addAttachments(caseCode, acase.getCcaiCode(), acase.getAttachments());
		result.setSuccess(true);
		result.setMessage("同步成功!");
		result.setCode("00");
		return result;
	}

	@Override
	public boolean isExistCcaiCode(String ccaiCode) {
		int count = toCaseInfoMapper.isExistCcaiCode(ccaiCode);
		return count > 0;
	}

	@Override
	public CcaiServiceResult updateCase(CcaiImportCase acase) {
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
				updateGuest(ca.getCaseCode(),ca.getCcaiCode(),acase.getGuests());
				msg.append("客户信息更新成功!\r\n");
			}
			//修改附件信息 不存在失败的情况
			if (acase.getAttachments() != null && acase.getAttachments().size() > 0) {
				updateAttachments(caseCode, acase.getCcaiCode(), acase.getAttachments());
				msg.append("附件信息更新成功!\r\n");
			}
			CcaiServiceResult result = new CcaiServiceResult();
			if(msg.length()>0){
				result.setSuccess(true);
				result.setCode("00");
				result.setMessage(msg.toString());
			}else{
				result.setSuccess(false);
				result.setCode("99");
				result.setMessage("没有信息被修改了!仅支持物业、客户、参与人及附件信息的修改");
			}

			return result;
		}
	}

	@Override
	public CcaiServiceResult updateCaseAndFlow(CcaiImportCase acase) {
		String caseCode = toCaseInfoMapper.findcaseCodeByCcaiCode(acase.getCcaiCode());
		if (StringUtils.isBlank(caseCode)) {
			throw new BusinessException("未获取到成交报告[" + acase.getCcaiCode() + "]对应的案件信息!");
		} else {
			ToCase toCase = tocaseMapper.findToCaseByCaseCode(caseCode);
			//只有当案件信息为审核未通过才能进行修改
			if (CaseStatusEnum.BHCCAI.getCode().equals(toCase.getStatus())) {
				return updateCase(acase);
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
	private String getCaseCode(CcaiImportCase acase) {
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
	private void addCase(String caseCode, CcaiImportCase acase) {
		ToCase tocase = new ToCase();
		tocase.setCcaiId(acase.getCcaiId());
		tocase.setCcaiCode(acase.getCcaiCode());
		tocase.setCaseCode(caseCode);
		tocase.setCity(acase.getCity());
		tocase.setCreateTime(new Date());
		tocase.setCaseProperty(CasePropertyEnum.TPZT.getCode());  // 指定为在途单
		for (CcaiImportParticipant pa : acase.getParticipants()) {
			if (CaseSyncParticipantEnum.WARRANT.getCode().equals(pa.getPosition())) {
				//获取交易顾问信息
				User user = getUserByUserNameOrEmployeeCode(pa.getUserName());
				if (user != null) {
					tocase.setLeadingProcessId(user.getId());//案件负责人 交易顾问ID
					//补全交易顾问手机号
					if (StringUtils.isBlank(pa.getMobile())) {
						pa.setMobile(user.getMobile());
					}
				} else {
					throw new BusinessException("权证专员" + pa.getUserName() + "信息不存在");
				}
				Org org = uamUserOrgService.getOrgByCode(pa.getGrpCode());
				if (org != null) {
					tocase.setOrgId(org.getId());//组别ID 上海原有逻辑为：组织编码的父级编码是区域部门编码
					tocase.setDistrictId(org.getParentId());//分行ID （CCAI中到权证部门或者誉萃）上海原有逻辑为：区域部门编码
				} else {
					throw new BusinessException("权证专员所在部门编码不存在！");
				}
			}
		}
		//业务开始后 进行调整
		tocase.setStatus(CaseStatusEnum.WJD.getCode());//设置状态为未接单
		tocase.setCaseOrigin(CaseOriginEnum.CCAI.getCode());//设置信息来源
		System.out.println("insert :" + tocase);
		tocaseMapper.insertSelective(tocase);
		//TODO 发送消息队列，发起交易流程
	}

	/**
	 * 修改成交报告 参与人员相关信息
	 *
	 * @param participants 新的案件参与人信息
	 * @param toCase       案件信息
	 * @return 是否成功
	 */
	private boolean updateParticipant(List<CcaiImportParticipant> participants, ToCase toCase) {
		//缓存主要人员信息，用于同步更改案件的人员配置信息
		CcaiImportParticipant warrant = null;//缓存过户权证
		CcaiImportParticipant agent = null;//缓存经纪人信息
		ToCaseParticipant param = new ToCaseParticipant();
		param.setAvailable("Y");
		param.setCcaiCode(toCase.getCcaiCode());
		//获取并更新原有的参与人信息表
		for (CcaiImportParticipant pa : participants) {
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
					if (CaseSyncParticipantEnum.SECRETARY.getCode().equals(pa.getPosition())) {
						buildErrorMessage(validator.validate(pa, Default.class), msg, "秘书");
					} else {
						buildErrorMessage(validator.validate(pa, NormalGroup.class, Default.class), msg, "参与人");
					}
					if (msg.length() > 0) {
						throw new BusinessException(msg.toString());
					} else {
						toCaseParticipantMapper.insertSelective(buildParticipant(toCase.getCaseCode(), toCase.getCcaiCode(), pa));
					}
				}
			} else {
				throw new BusinessException("未获取到对应的AssignId[" + pa.getAssignId() + "]信息");
			}
			if (CaseSyncParticipantEnum.WARRANT.getCode().equals(pa.getPosition())) {
				warrant = pa;
			}
			if (CaseSyncParticipantEnum.AGENT.getCode().equals(pa.getPosition())) {
				agent = pa;
			}
		}
		//未接单 或 驳回CCAI的成交报告 需要修改 相应的 案件人员配置信息
		if (CaseStatusEnum.WJD.getCode().equals(toCase.getStatus())
				|| CaseStatusEnum.BHCCAI.getCode().equals(toCase.getStatus())) {
			updateCaseWorker(warrant, agent, toCase);
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
	private void addCaseInfo(String caseCode, CcaiImportCase acase) {
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
		//同步案件相关人员信息
		for (CcaiImportParticipant pa : acase.getParticipants()) {
			if (CaseSyncParticipantEnum.AGENT.getCode().equals(pa.getPosition())) {
				caseInfoAgentSet(caseInfo, pa);
			} else if (CaseSyncParticipantEnum.WARRANT.getCode().equals(pa.getPosition())) {
				caseInfoWarrantSet(caseInfo, pa);
			}
			ToCaseParticipant participant = buildParticipant(caseCode, acase.getCcaiCode(), pa);
			//补全参与人的手机号
			if (StringUtils.isBlank(participant.getMobile())) {
				User user = getUserByUserNameOrEmployeeCode(pa.getUserName());
				if (user != null) participant.setMobile(user.getMobile());
			}
			//补全主管信息
			if (StringUtils.isNotBlank(participant.getGrpMgrUsername())
					&& (StringUtils.isBlank(participant.getGrpMgrRealname()) || StringUtils.isBlank(participant.getGrpMgrMobile()))) {
				User user = getUserByUserNameOrEmployeeCode(pa.getUserName());
				if (user != null) {
					participant.setGrpMgrMobile(user.getMobile());
					participant.setGrpMgrRealname(user.getRealName());
				}
			}
			//新增参与人信息
			toCaseParticipantMapper.insertSelective(participant);
		}
		toCaseInfoMapper.insertSelective(caseInfo);
	}

	/**
	 * 将CcaiImportCaseInfo导入对象转换成系统用参与人对象
	 *
	 * @param caseCode
	 * @param ccaiCode
	 * @param pa
	 * @return
	 */
	private ToCaseParticipant buildParticipant(String caseCode, String ccaiCode, CcaiImportParticipant pa) {
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
	private void caseInfoAgentSet(ToCaseInfo caseInfo, CcaiImportParticipant pa) {
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
	private void caseInfoWarrantSet(ToCaseInfo caseInfo, CcaiImportParticipant pa) {
		User manager = getUserByUserNameOrEmployeeCode(pa.getGrpMgrUserName());
		if (manager != null) {
			caseInfo.setRequireProcessorId(manager.getId());//请求处理人即交易主管
			if (StringUtils.isBlank(pa.getGrpMgrMobile())) {
				pa.setGrpMgrMobile(manager.getMobile());
			}
			if (StringUtils.isBlank(pa.getGrpMgrRealName())) {
				pa.setGrpMgrRealName(manager.getRealName());
			}
		} else {
			throw new BusinessException("过户权证主管[" + pa.getGrpMgrUserName() + "]信息不存在");
		}
		//CCAI导入直接进行分单
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
	 *
	 * @param caseCode
	 * @param ccaiCode
	 * @param guests
	 */
	private void addGuestsInfo(String caseCode, String ccaiCode, List<CcaiImportCaseGuest> guests) {
		if (guests.size() < 2) throw new BusinessException("客户信息不正确");
		for (CcaiImportCaseGuest g : guests) {
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
	private void addProperty(String caseCode, String ccaiCode, CcaiImportCaseProperty imp) {
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
	private ToPropertyInfo buildProperty(String caseCode, String ccaiCode, CcaiImportCaseProperty imp) {
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
	private boolean updateProperty(String caseCode, String ccaiCode, CcaiImportCaseProperty property) {
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
	private void addAttachments(String caseCode, String ccaiCode, List<CcaiImportAttachment> attachments) {
		for (CcaiImportAttachment a : attachments) {
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
	private ToCcaiAttachment buildCcaiAttachment(String caseCode, String ccaiCode, CcaiImportAttachment a) {
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
	private void updateAttachments(String caseCode, String ccaiCode, List<CcaiImportAttachment> attachments) {
		List<ToCcaiAttachment> list = toCcaiAttachmentMapper.findAttachmentsByCcaiCode(ccaiCode);
		if (list == null || list.size() == 0) {
			addAttachments(caseCode, ccaiCode, attachments);
		} else {
			Map<String, ToCcaiAttachment> temp = new HashMap<>();//缓存已有的文件列表
			for (ToCcaiAttachment l : list) {
				temp.put(l.getCcaiFileid(), l);
			}
			for (CcaiImportAttachment a : attachments) {
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
	private boolean validateAttachment(CcaiImportAttachment a) {
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
	 * 同步修改案件信息及案件表中设置的经纪人及过户权证信息
	 *
	 * @param warrant 过户权证信息 可为null
	 * @param agent   经纪人信息 可为null
	 * @param toCase  案件信息
	 */
	private void updateCaseWorker(CcaiImportParticipant warrant, CcaiImportParticipant agent, ToCase toCase) {
		ToCaseInfo caseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(toCase.getCaseCode());
		//过户权证相关信息处理
		if (warrant != null && CaseSyncParticipantEnum.WARRANT.getCode().equals(warrant.getPosition())) {
			//部门主管信息更改
			if (StringUtils.isNotBlank(warrant.getGrpMgrUserName())) {
				User manager = getUserByUserNameOrEmployeeCode(warrant.getGrpMgrUserName());
				if (manager != null) {
					caseInfo.setRequireProcessorId(manager.getId());//请求处理人即交易主管 进行分单操作
				}
			}
			//过户权证信息更改
			if (StringUtils.isNotBlank(warrant.getUserName())) {
				User user = getUserByUserNameOrEmployeeCode(warrant.getUserName());
				if (user != null) {
					toCase.setLeadingProcessId(user.getId());//案件负责人
				}
				caseInfo.setDispatchTime(new Date());//更新 分单时间
			}
			//过户权证部门编码修改
			if (StringUtils.isNotBlank(warrant.getGrpCode())) {
				Org org = uamUserOrgService.getOrgByCode(warrant.getGrpCode());
				if (org != null) {
					toCase.setOrgId(org.getId());//更新组别信息
					toCase.setDistrictId(org.getParentId());//区域编码
				} else {
					throw new BusinessException("过户权证部门编码不存在!");
				}
				caseInfo.setArCode(warrant.getGrpCode());
				caseInfo.setTargetCode(warrant.getGrpCode());//更换目标部门
			}
			if (StringUtils.isNotBlank(warrant.getGrpName())) {
				caseInfo.setArName(warrant.getGrpName());
			}
		}
		//经纪人相关信息处理
		if (agent != null && CaseSyncParticipantEnum.AGENT.getCode().equals(agent.getPosition())) {
			//经纪人信息修改
			if (StringUtils.isNotBlank(agent.getUserName())) {
				User update = getUserByUserNameOrEmployeeCode(agent.getUserName());
				if (update != null) {
					caseInfo.setAgentCode(update.getId());
					caseInfo.setAgentPhone(update.getMobile());
				} else {
					throw new BusinessException("经纪人[" + agent.getUserName() + "]信息不存在");
				}
				caseInfo.setAgentName(StringUtils.isBlank(agent.getRealName()) ? update.getRealName() : agent.getRealName());
				caseInfo.setAgentUserName(agent.getUserName());
			}
			//部门编码
			if (StringUtils.isNotBlank(agent.getGrpCode())) {
				caseInfo.setGrpCode(agent.getGrpCode());
			}
			//经纪人部门名称
			if (StringUtils.isNotBlank(agent.getGrpName())) {
				caseInfo.setGrpName(agent.getGrpName());
			}
		}
		toCaseInfoMapper.updateByPrimaryKeySelective(caseInfo);
		tocaseMapper.updateByPrimaryKeySelective(toCase);
	}

	/**
	 * 更新客户信息
	 * @param caseCode 案件编号
	 * @param guests 被修改的客户信息
	 */
	private void updateGuest(String caseCode,String ccaiCode,List<CcaiImportCaseGuest> guests){
		List<TgGuestInfo> olds = tgGuestInfoMapper.findTgGuestInfoByCaseCode(caseCode);
		if(olds==null || olds.size() ==0) throw new BusinessException("未获取到客户信息，无法修改");
		Map<String,TgGuestInfo> temp = new HashMap<>();
		//从数组 转换成map,以唯一识别code 为key方便修改
		olds.stream().forEach((guest)->temp.put(guest.getGuestCode(),guest));
		for(CcaiImportCaseGuest guest : guests){
			if(StringUtils.isBlank(guest.getId())) throw new BusinessException("未获取到客户唯一标识，无法进行修改!");
			if(temp.containsKey(guest.getId())){
				TgGuestInfo update = temp.get(guest.getId());
				//先不进行类型修改 否则会导致没有上下家的情况
				// update.setTransPosition(StringUtils.isNotBlank(guest.getPosition())?guest.getPosition():null);
				update.setGuestName(StringUtils.isNotBlank(guest.getName())?guest.getName():null);
				update.setGuestPhone(StringUtils.isNotBlank(guest.getMobile())?guest.getMobile():null);
				update.setIdentifyType(StringUtils.isNotBlank(guest.getCertType())?guest.getCertType():null);
				update.setIdentifyNumber(StringUtils.isNotBlank(guest.getCertCode())?guest.getCertCode():null);
				tgGuestInfoMapper.updateByPrimaryKeySelective(update);
			}else{
				StringBuilder msg = new StringBuilder();
				buildErrorMessage(validator.validate(guest),msg,"客户");
				if(msg.length()>0){
					throw new BusinessException(msg.toString());
				}else{
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

}
