package com.centaline.trans.task.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.api.service.FlowApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.common.enums.*;
import com.centaline.trans.task.service.ToMortgageTosaveService;
import com.centaline.trans.task.vo.MortgageToSaveVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.centaline.trans.award.service.TsAwardCaseCentalService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.entity.TsCaseEfficient;
import com.centaline.trans.cases.repository.TsCaseEfficientMapper;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.satisfaction.entity.ToSatisfaction;
import com.centaline.trans.satisfaction.service.SatisfactionService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.entity.ToHouseTransfer;
import com.centaline.trans.task.repository.ToHouseTransferMapper;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

@Service
public class ToHouseTransferServiceImpl implements ToHouseTransferService
{

    private Logger logger = Logger.getLogger(ToHouseTransferServiceImpl.class);

    @Autowired
    private ToHouseTransferMapper toHouseTransferMapper;
    @Autowired
    private ToMortgageService toMortgageService;

    @Autowired
    private ToMortgageTosaveService toMortgageTosaveService;

    @Autowired(required = true)
    private ToCaseService toCaseService;

    @Autowired
    private WorkFlowManager workFlowManager;

    @Autowired
    SatisfactionService satisfactionService;
    @Autowired(required = true)
    private UamTemplateService uamTemplateService;
    @Autowired
    private ToPropertyInfoService toPropertyInfoService;
    @Autowired
    private LoanlostApproveService loanlostApproveService;

    @Autowired(required = true)
    private UamSessionService uamSessionService;/* 用户信息 */
    /* 发送消息 */
    @Autowired(required = true)
    @Qualifier("uamMessageServiceClient")
    private UamMessageService uamMessageService;

    @Autowired
    private TsCaseEfficientMapper tsCaseEfficientMapper;

    //现与ccai交互接口
    @Autowired
    private FlowApiService flowApiService;

    @Override
    public boolean saveToHouseTransfer(ToHouseTransfer toHouseTransfer)
    {
        if (StringUtils.isBlank(toHouseTransfer.getCaseCode()))
        {
            return false;
        }
        if (toHouseTransfer.getPkid() != null)
        {
            if (toHouseTransferMapper.updateByPrimaryKeySelective(toHouseTransfer) > 0)
            {
                return true;
            }
        }
        else
        {
            if (toHouseTransferMapper.findToGuoHuByCaseCode(toHouseTransfer.getCaseCode()) == null)
            {
                if (toHouseTransferMapper.insertSelective(toHouseTransfer) > 0)
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void saveToHouseTransferAndMort(ToHouseTransfer toHouseTransfer, ToMortgage toMortgage)
    {

        toHouseTransfer.setBusinessTax(toHouseTransfer.getBusinessTax() != null ? toHouseTransfer.getBusinessTax().multiply(new BigDecimal(10000)) : null);
        toHouseTransfer.setContractTax(toHouseTransfer.getContractTax() != null ? toHouseTransfer.getContractTax().multiply(new BigDecimal(10000)) : null);
        toHouseTransfer.setHouseHodingTax(toHouseTransfer.getHouseHodingTax() != null ? toHouseTransfer.getHouseHodingTax().multiply(new BigDecimal(10000)) : null);
        toHouseTransfer.setLandIncrementTax(toHouseTransfer.getLandIncrementTax() != null ? toHouseTransfer.getLandIncrementTax().multiply(new BigDecimal(10000)) : null);
        toHouseTransfer
                .setPersonalIncomeTax(toHouseTransfer.getPersonalIncomeTax() != null ? toHouseTransfer.getPersonalIncomeTax().multiply(new BigDecimal(10000)) : null);
        toHouseTransfer.setCardPayAmount(toHouseTransfer.getCardPayAmount() != null ? toHouseTransfer.getCardPayAmount() : null);
        if (toHouseTransfer.getPkid() != null)
        {
            toHouseTransferMapper.updateByPrimaryKeySelective(toHouseTransfer);

        }
        else
        {
            if (toHouseTransferMapper.findToGuoHuByCaseCode(toHouseTransfer.getCaseCode()) == null)
            {
                toHouseTransferMapper.insertSelective(toHouseTransfer);

            }
        }
        ToMortgage mortgage = toMortgageService.findToMortgageByCaseCode2(toHouseTransfer.getCaseCode());
        if (mortgage != null)
        {
            mortgage.setMortType(toMortgage.getMortType());
            mortgage.setMortTotalAmount(toMortgage.getMortTotalAmount() != null ? toMortgage.getMortTotalAmount().multiply(new BigDecimal(10000)) : null);
            mortgage.setComAmount(toMortgage.getComAmount() != null ? toMortgage.getComAmount().multiply(new BigDecimal(10000)) : null);
            mortgage.setComYear(toMortgage.getComYear());
            mortgage.setComDiscount(toMortgage.getComDiscount());
            mortgage.setPrfAmount(toMortgage.getPrfAmount() != null ? toMortgage.getPrfAmount().multiply(new BigDecimal(10000)) : null);
            mortgage.setPrfYear(toMortgage.getPrfYear());
            toMortgageService.saveToMortgage(mortgage);
        }
    }

    /**
     * 保存贷款流失和过户信息
     * @author wbzhouht
     * @param toHouseTransfer
     * @param mortgageToSaveVO
     */
    @Override
    public void savaToHouseTransferAndMortageToVO(ToHouseTransfer toHouseTransfer, MortgageToSaveVO mortgageToSaveVO) {
        //业主垫资金额(万元)转换为(元)
        toHouseTransfer.setOwnerMatAmount(toHouseTransfer.getOwnerMatAmount()!=null?toHouseTransfer
                .getOwnerMatAmount().multiply(new BigDecimal(10000)):null);
        //垫资费用
        toHouseTransfer.setMatCharges(toHouseTransfer.getMatCharges()!=null?toHouseTransfer
                .getMatCharges().multiply(new BigDecimal(10000)):null);
        //担保费金额
        toHouseTransfer.setGuaranteeFeeAmount(toHouseTransfer.getGuaranteeFeeAmount()!=null?toHouseTransfer
                .getGuaranteeFeeAmount().multiply(new BigDecimal(10000)):null);
        if(toHouseTransfer.getPkid()!=null){
            toHouseTransferMapper.updateByPrimaryKeySelective(toHouseTransfer);
        } else
        {
            if (toHouseTransferMapper.findToGuoHuByCaseCode(toHouseTransfer.getCaseCode()) == null)
            {
                toHouseTransferMapper.insertSelective(toHouseTransfer);
            }
        }
        MortgageToSaveVO mort=toMortgageTosaveService.selectByCaseCode(toHouseTransfer.getCaseCode());
        if(mort!=null){
           mortgageToSaveVO.setLoanLossAmount(mortgageToSaveVO.getLoanLossAmount()!=null?mortgageToSaveVO
                   .getLoanLossAmount().multiply(new BigDecimal(10000)):null);
           mortgageToSaveVO.setPkid(mort.getPkid());
           int n= toMortgageTosaveService.updateByPrimary(mortgageToSaveVO);
            System.out.println(n);
        }
    }

    @Override
    public ToHouseTransfer findToGuoHuByCaseCode(String caseCode)
    {
        ToHouseTransfer toHouseTransfer = toHouseTransferMapper.findToGuoHuByCaseCode(caseCode);
        if (null != toHouseTransfer)
        {
            toHouseTransfer.setOwnerMatAmount(toHouseTransfer.getOwnerMatAmount()!=null?toHouseTransfer.getOwnerMatAmount().divide(new BigDecimal(10000)):null);
            toHouseTransfer.setMatCharges(toHouseTransfer.getMatCharges()!=null?toHouseTransfer.getMatCharges().divide(new BigDecimal(10000)):null);
            toHouseTransfer.setGuaranteeFeeAmount(toHouseTransfer.getGuaranteeFeeAmount()!=null?toHouseTransfer.getGuaranteeFeeAmount().divide(new BigDecimal(10000)):null);
            toHouseTransfer.setBusinessTax(toHouseTransfer.getBusinessTax() != null ? toHouseTransfer.getBusinessTax().divide(new BigDecimal(10000)) : null);
            toHouseTransfer.setContractTax(toHouseTransfer.getContractTax() != null ? toHouseTransfer.getContractTax().divide(new BigDecimal(10000)) : null);
            toHouseTransfer.setHouseHodingTax(toHouseTransfer.getHouseHodingTax() != null ? toHouseTransfer.getHouseHodingTax().divide(new BigDecimal(10000)) : null);
            toHouseTransfer
                    .setLandIncrementTax(toHouseTransfer.getLandIncrementTax() != null ? toHouseTransfer.getLandIncrementTax().divide(new BigDecimal(10000)) : null);
            toHouseTransfer
                    .setPersonalIncomeTax(toHouseTransfer.getPersonalIncomeTax() != null ? toHouseTransfer.getPersonalIncomeTax().divide(new BigDecimal(10000)) : null);
        }
        return toHouseTransfer;
    }

    @Override
    public ToCaseInfoCountVo countToHouseTransferById(String userId)
    {
        return toHouseTransferMapper.countToHouseTransferById(userId);
    }

    @Override
    public ToCaseInfoCountVo queryCountToHouseTransferById(ToCase toCase)
    {

        return toHouseTransferMapper.queryCountToHouseTransferById(toCase);
    }

    @Override
    public ToCaseInfoCountVo countToHouseTransferByOrgId(String orgId, String startDate, String endDate)
    {
        ToCase toCase = new ToCase();
        toCase.setOrgId(orgId);
        toCase.setStartDate(startDate);
        toCase.setEndDate(endDate);
        return toHouseTransferMapper.countToHouseTransferByOrgId(toCase);
    }

    @Override
    public ToCaseInfoCountVo queryCountToHouseTransferByOrg(ToCase toCase)
    {

        return toHouseTransferMapper.queryCountToHouseTransferByOrg(toCase);
    }

    @Override
    public List<ToCaseInfoCountVo> countToHouseTransferListByOrgId(String orgId)
    {
        ToCase toCase = new ToCase();
        toCase.setOrgId(orgId);
        return toHouseTransferMapper.countToHouseTransferListByOrgId(toCase);
    }

    @Override
    public List<ToCaseInfoCountVo> countToHouseTransferListByOrgList(List<String> orgList)
    {
        return toHouseTransferMapper.countToHouseTransferListByOrgList(orgList);
    }

    @Override
    public int countToHouseTransferByOrgList(List<String> strList, String startDate, String endDate)
    {

        return toHouseTransferMapper.countToHouseTransferByOrgList(strList, startDate, endDate);
    }

    @Override
    public int initCountToHouseTransferByOrgId(String orgId, String createTime)
    {
        ToCase toCase = new ToCase();
        toCase.setOrgId(orgId);
        toCase.setTime(createTime);
        return toHouseTransferMapper.initCountToHouseTransferByOrgId(toCase);
    }

    @Override
    public List<ToCaseInfoCountVo> countToHouseTransferListByIdList(List<String> idList)
    {
        return toHouseTransferMapper.countToHouseTransferListByIdList(idList);
    }

    @Override
    public int countToHouseTransferByIdList(List<String> idList, String startDate, String endDate)
    {
        return toHouseTransferMapper.countToHouseTransferByIdList(idList, startDate, endDate);
    }

    @Override
    public Boolean submitToHouseTransfer(ToHouseTransfer toHouseTransfer, MortgageToSaveVO toMortgage, LoanlostApproveVO loanlostApproveVO, String taskId,
            String processInstanceId) {
        // 2 执行交易系统代码
        savaToHouseTransferAndMortageToVO(toHouseTransfer, toMortgage);
            /* 保存过户申请 */
        saveToApproveRecord(toHouseTransfer, processInstanceId, loanlostApproveVO);
        SessionUser sender = uamSessionService.getSessionUser();
            List<RestVariable> variables = new ArrayList<RestVariable>();
            ToCase toCase = toCaseService.findToCaseByCaseCode(toHouseTransfer.getCaseCode());
            if (workFlowManager.submitTask(variables, taskId, processInstanceId, toCase.getLeadingProcessId(), toHouseTransfer.getCaseCode())) {
                /**
                 * 过户审批通过后查找该案件对应的sctrans.T_CS_CASE_SATISFACTION表记录，如果没有就找到对应的‘客服回访’流程并发送消息往下走，并更新表记录
                 *
                 * @for 满意度评分
                 */
                ToCase ca = toCaseService.findToCaseByCaseCode(toHouseTransfer.getCaseCode());
                ca.setStatus(CaseStatusEnum.YGH.getCode());
                toCaseService.updateByCaseCodeSelective(ca);
                ToSatisfaction satis = satisfactionService.queryToSatisfactionByCaseCode(toCase.getCaseCode());
                if (satis != null && SatisfactionTypeEnum.NEW.getCode().equals(satis.getType())) {
                    satisfactionService.handleAfterGuohu(toCase.getCaseCode(), sender.getId(), null);
                }
            }
        return true;
    }

    public AjaxResponse saveToHouseTransfer(ToHouseTransfer toHouseTransfer, ToMortgage toMortgage, LoanlostApproveVO loanlostApproveVO, String processInstanceId)
    {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try
        {
            // 2 执行交易系统代码
            saveToHouseTransferAndMort(toHouseTransfer, toMortgage);
            /* 保存过户申请 */
            saveToApproveRecord(toHouseTransfer, processInstanceId, loanlostApproveVO);
            /*
             * 佣金分配 绩效奖金自动化,取消原有的数据获取方式 add by zhuody in 2017-06-20
             */
            /*
             * awardBaseService.doAwardCalculate(toHouseTransfer,
             * processInstanceId);
             */
            ToCase toCase = toCaseService.findToCaseByCaseCode(toHouseTransfer.getCaseCode());
            /* 修改案件状态 */
            toCase.setStatus("30001004");
            toCaseService.updateByCaseCodeSelective(toCase);
            ajaxResponse.setSuccess(true);
        }
        catch (BusinessException e)
        {
            ajaxResponse.setSuccess(false);
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BusinessException("保存数据失败");
        }
        return ajaxResponse;
    }

    public AjaxResponse submitToHouseTransfer(ToHouseTransfer toHouseTransfer, String taskId, String processInstanceId)
    {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try
        {
            ToCase toCase = toCaseService.findToCaseByCaseCode(toHouseTransfer.getCaseCode());
            List<RestVariable> variables = new ArrayList<RestVariable>();
            workFlowManager.submitTask(variables, taskId, processInstanceId, toCase.getLeadingProcessId(), toHouseTransfer.getCaseCode());
            ajaxResponse.setSuccess(true);
        }
        catch (BusinessException e)
        {
            ajaxResponse.setSuccess(false);
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BusinessException("提交流程失败");
        }
        return ajaxResponse;
    }

    /**
     * 保存审核记录
     * 
     * @param processInstanceVO
     * @param loanlostApproveVO
     * @param loanLost
     * @param loanLost_response
     */
    private ToApproveRecord saveToApproveRecord(ToHouseTransfer toHouseTransfer, String processInstanceId, LoanlostApproveVO loanlostApproveVO)
    {
        ToApproveRecord toApproveRecord = new ToApproveRecord();
        toApproveRecord.setProcessInstance(processInstanceId);
        toApproveRecord.setPartCode(toHouseTransfer.getPartCode());
        toApproveRecord.setOperatorTime(new Date());
        toApproveRecord.setApproveType(loanlostApproveVO.getApproveType());
        toApproveRecord.setCaseCode(toHouseTransfer.getCaseCode());
        toApproveRecord.setContent("过户申请");
        toApproveRecord.setOperator(loanlostApproveVO.getOperator());
        loanlostApproveService.saveLoanlostApprove(toApproveRecord);
        return toApproveRecord;
    }

    @Override
    public Boolean guohuApprove(HttpServletRequest request, ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO, String GuohuApprove,
            String GuohuApprove_response, String notApprove, String members)
    {
        SessionUser sender = uamSessionService.getSessionUser();
        String caseCode = processInstanceVO.getCaseCode();

        /* 流程引擎相关 */
        List<String> membersList = null;
        List<RestVariable> variables = new ArrayList<RestVariable>();
        if (members != null && members.length() > 0)
        {
            membersList = Arrays.asList(members.split(","));
        }
        //保存审批记录
        ToApproveRecord toApproveRecord = saveToApproveRecordForGuohu(processInstanceVO, loanlostApproveVO, GuohuApprove, GuohuApprove_response, notApprove);
        if (!"true".equals(GuohuApprove))
        {
            // 没未通过审核，发站内信通知案件负责人
            String result = toApproveRecord.getContent();
            ToApproveRecord paramsApproveRecord = new ToApproveRecord();
            paramsApproveRecord.setPartCode("Guohu");
            paramsApproveRecord.setCaseCode(caseCode);
            // 查询 上一步操作人
            ToApproveRecord lastApproveRecord = loanlostApproveService.findLastApproveRecord(paramsApproveRecord);
            if (lastApproveRecord != null)
            {
                String recevier = lastApproveRecord.getOperator();
                //因暂时没有短信服务，顾先注释掉
                //sendMessage(sender.getId(), recevier, caseCode, result);
            }
            variables.add(new RestVariable("members", membersList));
        }
        else
        {
            // 修改案件时效信息
            TsCaseEfficient tsCaseEfficient = tsCaseEfficientMapper.getCaseEffInfoByCasecode(processInstanceVO.getCaseCode());
            if (tsCaseEfficient != null)
            {
                tsCaseEfficient.setGuohuTime(new Date());
                tsCaseEfficient.setCurDelayCount(0);
                tsCaseEfficientMapper.updateTsCaseEffInfo(tsCaseEfficient);
            }
        }

        RestVariable restVariable = new RestVariable();
        restVariable.setName("GuohuApprove");
        restVariable.setValue(GuohuApprove.equals("true"));
        variables.add(restVariable);
        if (!StringUtil.isBlank(GuohuApprove_response))
        {
            RestVariable restVariable1 = new RestVariable();
            restVariable1.setName("GuohuApprove_response");
            restVariable1.setValue(GuohuApprove_response);
            variables.add(restVariable1);
        }

        // 过户审批通过时 向计件奖金池插入数据 add by zhuody in 2017-05-18
        /**
         * 天津不需要计件奖金池，顾可废弃
         */
       /* TsAwardCaseCental tsAwardCaseCental = new TsAwardCaseCental();
        tsAwardCaseCental.setCaseCode(processInstanceVO.getCaseCode());
        tsAwardCaseCental.setGuohuApproveTime(covertDate(new Date())); // TODO
                                                                       // 测试完之后时间不减一
        //tsAwardCaseCental.setGuohuApproveTime(new Date());
        // covertDate
        if (null != sender)
        {
            tsAwardCaseCental.setGuohuApproveRecord(sender.getId());
        }
        tsAwardCaseCentalService.saveAwardCaseInfo(tsAwardCaseCental);
*/
        ToCase toCase = toCaseService.findToCaseByCaseCode(processInstanceVO.getCaseCode());

        return workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), toCase.getLeadingProcessId(),
                processInstanceVO.getCaseCode());
    }

    /**
     * 保存审核记录
     * 
     * @param processInstanceVO
     * @param loanlostApproveVO
     * @param loanLost
     * @param loanLost_response
     */
    private ToApproveRecord saveToApproveRecordForGuohu(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO, String loanLost,
            String loanLost_response, String notApprove)
    {
        ToApproveRecord toApproveRecord = new ToApproveRecord();
        // toApproveRecord.setPkid(loanlostApproveVO.getLapPkid());
        toApproveRecord.setProcessInstance(processInstanceVO.getProcessInstanceId());
        toApproveRecord.setPartCode(processInstanceVO.getPartCode());
        toApproveRecord.setOperatorTime(new Date());
        toApproveRecord.setApproveType(loanlostApproveVO.getApproveType());
        toApproveRecord.setCaseCode(processInstanceVO.getCaseCode());
        boolean b = "true".equals(loanLost);
        boolean c = loanLost_response == null || loanLost_response.intern().length() == 0;
        toApproveRecord.setContent((b ? "通过" : "不通过") + (c ? ",没有审批意见。" : ",审批意见为：" + loanLost_response));
        toApproveRecord.setOperator(loanlostApproveVO.getOperator());
        // 审核不通过原因
        toApproveRecord.setNotApprove(notApprove);

        loanlostApproveService.saveLoanlostApprove(toApproveRecord);
        return toApproveRecord;
    }

    /**
     * 发送审批结果提醒
     * 
     * @param processInstanceVO
     * @param result
     * @param approveType
     */
    private void sendMessage(String sender, String recevier, String caseCode, String result)
    {
        // 创建map放入消息参数
        Map<String, Object> params = new HashMap<String, Object>();
        ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode);
        params.put("property_address", (toPropertyInfo != null) ? toPropertyInfo.getPropertyAddr() : "");
        params.put("approver", uamSessionService.getSessionUser().getRealName());
        params.put("part_name", "过户审批");
        params.put("approve_content", result);
        params.put("case_code", caseCode);

        // 拼接发送的字符串
        String resourceCode = MsgLampEnum.APPROVE_RESULT_REMINDER.getCode();
        String content = uamTemplateService.mergeTemplate(resourceCode, params);

        Message message = new Message();
        // 消息标题
        String title = MsgLampEnum.APPROVE_RESULT_REMINDER.getName();
        message.setTitle(title);
        // 消息类型
        message.setType(MessageType.SITE);
        /* 设置提醒列别 */
        message.setMsgCatagory(MsgCatagoryEnum.RESPON.getCode());
        /* 内容 */
        message.setContent(content);
        /* 发送人 */
        message.setSenderId(sender);
        /* 接收人 */
        uamMessageService.sendMessageByDist(message, recevier);
    }

    // 获取指定时间的上一个月时间
    private Date covertDate(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 格式化对象
        Calendar calendar = Calendar.getInstance();
        // 日历对象
        calendar.setTime(date);
        // 设置当前日期
        calendar.add(Calendar.MONTH, -1);

        return calendar.getTime();
        // 月份减一
        // System.out.println(sdf.format(calendar.getTime()));//输出格式化的日期

    }
}

