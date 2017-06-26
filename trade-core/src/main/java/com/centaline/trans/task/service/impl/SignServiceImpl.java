package com.centaline.trans.task.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.entity.Result2;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.TsCaseEfficient;
import com.centaline.trans.cases.repository.TsCaseEfficientMapper;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.SatisfactionTypeEnum;
import com.centaline.trans.common.repository.TgGuestInfoMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.satisfaction.entity.ToSatisfaction;
import com.centaline.trans.satisfaction.service.SatisfactionService;
import com.centaline.trans.task.entity.ToFirstFollow;
import com.centaline.trans.task.entity.ToPayment;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.repository.ToFirstFollowMapper;
import com.centaline.trans.task.repository.ToHouseTransferMapper;
import com.centaline.trans.task.repository.ToPaymentMapper;
import com.centaline.trans.task.repository.ToSignMapper;
import com.centaline.trans.task.repository.ToTaxMapper;
import com.centaline.trans.task.service.SignService;
import com.centaline.trans.task.vo.TransSignVO;
import com.centaline.trans.utils.DateUtil;

@Service
public class SignServiceImpl implements SignService
{

    @Autowired(required = true)
    private ToCaseService toCaseService;
    @Autowired
    private TgGuestInfoService tgGuestInfoService;
    @Autowired
    private ToMortgageService toMortgageService;
    @Autowired
    private SatisfactionService satisfactionService;

    @Autowired
    private TgGuestInfoMapper tgGuestInfoMapper;
    @Autowired
    private ToPaymentMapper toPaymentMapper;
    @Autowired
    private ToPropertyInfoMapper toPropertyInfoMapper;
    @Autowired
    private ToSignMapper toSignMapper;
    @Autowired
    private ToHouseTransferMapper toHouseTransferMapper;
    @Autowired
    private ToTaxMapper toTaxMapper;
    @Autowired
    private ToFirstFollowMapper tofirstFollowMapper;
    @Autowired
    private WorkFlowManager workFlowManager;

    @Autowired
    private UamSessionService uamSessionService;

    @Autowired
    private TsCaseEfficientMapper tsCaseEfficientMapper;

    @Override
    public Boolean insertGuestInfo(TransSignVO transSignVO)
    {
        if (transSignVO.getCaseCode() == null || transSignVO.getCaseCode().isEmpty())
        {
            return false;
        }
        if (transSignVO.getGuestPkid() != null && transSignVO.getGuestPkid().size() > 0)
        {
            for (Long pkid : transSignVO.getGuestPkid())
            {
                tgGuestInfoMapper.deleteByPrimaryKey(pkid);
            }
        }
        /** 上家 */
        TgGuestInfo tgGuestInfoUP = new TgGuestInfo();
        tgGuestInfoUP.setCaseCode(transSignVO.getCaseCode());
        tgGuestInfoUP.setTransPosition("30006001");
        for (int i = 0; i < transSignVO.getPkidUp().size(); i++)
        {
            tgGuestInfoUP.setPkid(null);
            tgGuestInfoUP.setGuestName(transSignVO.getGuestNameUp().get(i));
            tgGuestInfoUP.setGuestPhone(transSignVO.getGuestPhoneUp().get(i));
            if (transSignVO.getPkidUp().get(i) != null && transSignVO.getPkidUp().get(i) != 0)
            {
                tgGuestInfoUP.setPkid(transSignVO.getPkidUp().get(i));
                tgGuestInfoMapper.updateByPrimaryKeySelective(tgGuestInfoUP);
            }
            else
            {
                if (tgGuestInfoMapper.findTgGuestInfosByCaseCode(tgGuestInfoUP).size() == 0)
                {
                    tgGuestInfoMapper.insertSelective(tgGuestInfoUP);
                }
            }
        }

        /** 下家 */
        TgGuestInfo tgGuestInfoDown = new TgGuestInfo();
        tgGuestInfoDown.setCaseCode(transSignVO.getCaseCode());
        tgGuestInfoDown.setTransPosition("30006002");
        for (int i = 0; i < transSignVO.getPkidDown().size(); i++)
        {
            tgGuestInfoDown.setPkid(null);
            tgGuestInfoDown.setGuestName(transSignVO.getGuestNameDown().get(i));
            tgGuestInfoDown.setGuestPhone(transSignVO.getGuestPhoneDown().get(i));
            if (transSignVO.getPkidDown().get(i) != null && transSignVO.getPkidDown().get(i) != 0)
            {
                tgGuestInfoDown.setPkid(transSignVO.getPkidDown().get(i));
                tgGuestInfoMapper.updateByPrimaryKeySelective(tgGuestInfoDown);
            }
            else
            {
                if (tgGuestInfoMapper.findTgGuestInfosByCaseCode(tgGuestInfoDown).size() == 0)
                {
                    tgGuestInfoMapper.insertSelective(tgGuestInfoDown);
                }
            }
        }

        /** 首付款 */
        ToPayment toPaymentInit = new ToPayment();
        toPaymentInit.setAmount(transSignVO.getInitAmount() != null ? transSignVO.getInitAmount().multiply(new BigDecimal(10000)) : null);
        toPaymentInit.setCaseCode(transSignVO.getCaseCode());
        toPaymentInit.setPayName(transSignVO.getInitPayName());
        toPaymentInit.setPayTime(transSignVO.getInitPayTime());
        toPaymentInit.setPayType(transSignVO.getInitPayType());
        if (transSignVO.getInitPayPkid() != null)
        {
            toPaymentInit.setPkid(transSignVO.getInitPayPkid());
            toPaymentMapper.updateByPrimaryKeySelective(toPaymentInit);
        }
        else
        {
            if (toPaymentMapper.findToPaymentByPayment(toPaymentInit) == null)
            {
                toPaymentMapper.insertSelective(toPaymentInit);
            }
        }
        /** 二次付款 */
        ToPayment toPaymentSec = new ToPayment();
        toPaymentSec.setAmount(transSignVO.getSecAmount() != null ? transSignVO.getSecAmount().multiply(new BigDecimal(10000)) : null);
        toPaymentSec.setCaseCode(transSignVO.getCaseCode());
        toPaymentSec.setPayName(transSignVO.getSecPayName());
        toPaymentSec.setPayTime(transSignVO.getSecPayTime());
        toPaymentSec.setPayType(transSignVO.getSecPayType());
        if (transSignVO.getSecPayPkid() != null)
        {
            toPaymentSec.setPkid(transSignVO.getSecPayPkid());
            toPaymentMapper.updateByPrimaryKeySelective(toPaymentSec);
        }
        else
        {
            if (toPaymentMapper.findToPaymentByPayment(toPaymentSec) == null)
            {
                toPaymentMapper.insertSelective(toPaymentSec);
            }
        }
        /** 尾款付款 */
        ToPayment toPaymentLast = new ToPayment();
        toPaymentLast.setAmount(transSignVO.getLastAmount() != null ? transSignVO.getLastAmount().multiply(new BigDecimal(10000)) : null);
        toPaymentLast.setCaseCode(transSignVO.getCaseCode());
        toPaymentLast.setPayName(transSignVO.getLastPayName());
        toPaymentLast.setPayTime(transSignVO.getLastPayTime());
        toPaymentLast.setPayType(transSignVO.getLastPayType());
        if (transSignVO.getLastPayPkid() != null)
        {
            toPaymentLast.setPkid(transSignVO.getLastPayPkid());
            toPaymentMapper.updateByPrimaryKeySelective(toPaymentLast);
        }
        else
        {
            if (toPaymentMapper.findToPaymentByPayment(toPaymentLast) == null)
            {
                toPaymentMapper.insertSelective(toPaymentLast);
            }
        }
        /** 装修补偿款 */
        ToPayment toPaymentCompensate = new ToPayment();
        toPaymentCompensate.setAmount(transSignVO.getCompensateAmount() != null ? transSignVO.getCompensateAmount().multiply(new BigDecimal(10000)) : null);
        toPaymentCompensate.setCaseCode(transSignVO.getCaseCode());
        toPaymentCompensate.setPayName(transSignVO.getCompensatePayName());
        toPaymentCompensate.setPayTime(transSignVO.getCompensatePayTime());
        toPaymentCompensate.setPayType(transSignVO.getCompensatePayType());
        if (transSignVO.getCompensatePayPkid() != null)
        {
            toPaymentCompensate.setPkid(transSignVO.getCompensatePayPkid());
            toPaymentMapper.updateByPrimaryKeySelective(toPaymentCompensate);
        }
        else
        {
            if (toPaymentMapper.findToPaymentByPayment(toPaymentCompensate) == null)
            {
                toPaymentMapper.insertSelective(toPaymentCompensate);
            }
        }

        /** 物业信息 */
        ToPropertyInfo toPropertyInfo = new ToPropertyInfo();
        toPropertyInfo.setCaseCode(transSignVO.getCaseCode());
        toPropertyInfo.setPropertyAddr(transSignVO.getPropertyAddr());
        toPropertyInfo.setTotalFloor(transSignVO.getTotalFloor());
        toPropertyInfo.setLocateFloor(transSignVO.getLocateFloor());
        toPropertyInfo.setSquare(transSignVO.getSquare());
        toPropertyInfo.setFinishYear(DateUtil.strToFullDate(transSignVO.getFinishYear() + "-01-01"));
        toPropertyInfo.setPropertyType(transSignVO.getPropertyType());
        if (transSignVO.getPropertyPkid() != null)
        {
            toPropertyInfo.setPkid(transSignVO.getPropertyPkid());
            toPropertyInfoMapper.updateByPrimaryKeySelective(toPropertyInfo);
        }
        else
        {
            if (toPropertyInfoMapper.findToPropertyInfoByCaseCode(transSignVO.getCaseCode()) == null)
            {
                toPropertyInfoMapper.insertSelective(toPropertyInfo);
            }
        }

        /** 签约信息 */
        ToSign toSign = new ToSign();
        toSign.setCaseCode(transSignVO.getCaseCode());
        toSign.setPartCode(transSignVO.getPartCode());
        toSign.setIsConCert(transSignVO.getIsConCert());
        toSign.setIsHukou(transSignVO.getIsHukou());
        toSign.setComment(transSignVO.getComment());
        toSign.setRealConTime(transSignVO.getRealConTime());
        toSign.setConPrice(transSignVO.getConPrice().multiply(new BigDecimal(10000))); // 合同价
        toSign.setRealPrice(transSignVO.getRealPrice().multiply(new BigDecimal(10000))); // 成交价
        /* 预估税费 */
        toSign.setBusinessTax(transSignVO.getBusinessTax() != null ? transSignVO.getBusinessTax().multiply(new BigDecimal(10000)) : null);
        toSign.setContractTax(transSignVO.getContractTax() != null ? transSignVO.getContractTax().multiply(new BigDecimal(10000)) : null);
        toSign.setHouseHodingTax(transSignVO.getHouseHodingTax() != null ? transSignVO.getHouseHodingTax().multiply(new BigDecimal(10000)) : null);
        toSign.setLandIncrementTax(transSignVO.getLandIncrementTax() != null ? transSignVO.getLandIncrementTax().multiply(new BigDecimal(10000)) : null);
        toSign.setPersonalIncomeTax(transSignVO.getPersonalIncomeTax() != null ? transSignVO.getPersonalIncomeTax().multiply(new BigDecimal(10000)) : null);
        toSign.setHouseQuantity(transSignVO.getHouseQuantity());

        if (transSignVO.getSignPkid() != null)
        {
            toSign.setPkid(transSignVO.getSignPkid());
            toSignMapper.updateByPrimaryKeySelective(toSign);
        }
        else
        {
            if (toSignMapper.findToSignByCaseCode(transSignVO.getCaseCode()) == null)
            {
                toSignMapper.insertSelective(toSign);
            }
        }

        // 功能：根据 casecode 到T_TO_FIRST_FOLLOW表中去查询，如果存在则做update，否则做insert,
        // 作者：zhangxb16 时间 2016-1-27
        int isExist = tofirstFollowMapper.isExistCasecode(transSignVO.getCaseCode());
        ToFirstFollow ff = null;
        if (isExist > 0)
        {
            ff = new ToFirstFollow();
            if ("true".equals(transSignVO.getIsLoanClose()))
            { // 有抵押
                ff.setIsLoanClose("1");
            }
            else
            {
                ff.setIsLoanClose("0");
            }

            if ("true".equals(transSignVO.getIsPerchaseReserachNeed()))
            { // 是否需要查限购
                ff.setIsPerchaseReserachNeed("1");
            }
            else
            {
                ff.setIsPerchaseReserachNeed("0");
            }
            ff.setCaseCode(transSignVO.getCaseCode());
            tofirstFollowMapper.updateByCaseCode(ff);
        }
        else
        {
            ff = new ToFirstFollow();
            if ("true".equals(ff.getIsLoanClose()))
            { // 有抵押
                ff.setIsLoanClose("1");
            }
            else
            {
                ff.setIsLoanClose("0");
            }

            if ("true".equals(ff.getIsPerchaseReserachNeed()))
            { // 是否需要查限购
                ff.setIsPerchaseReserachNeed("1");
            }
            else
            {
                ff.setIsPerchaseReserachNeed("0");
            }
            ff.setCaseCode(transSignVO.getCaseCode());
            tofirstFollowMapper.insertSelective(ff);
        }

        RestVariable restVariable = new RestVariable();
        restVariable.setName("LoanCloseNeed");
        // 有无抵押要修改 上家贷款结清流程变量
        // true: 有抵押需要启上家贷款结清流程
        if ("true".equals(transSignVO.getIsLoanClose()))
        {
            restVariable.setValue(true);
        }
        else
        {
            // false: 无抵押不需要启上家贷款结清流程
            restVariable.setValue(false);
        }
        workFlowManager.setVariableByProcessInsId(transSignVO.getProcessInstanceId(), restVariable.getName(), restVariable);
        return true;
    }

    @Override
    public TransSignVO qureyGuestInfo(String caseCode)
    {
        TransSignVO transSignVO = new TransSignVO();

        /** 读取打款数据 */
        List<ToPayment> toPaymentList = toPaymentMapper.findToPaymentByCaseCode(caseCode);
        for (int i = 0; i < toPaymentList.size(); i++)
        {
            ToPayment toPayment = toPaymentList.get(i);
            if (toPayment.getPayName().equals("首付付款"))
            {
                transSignVO.setInitPayPkid(toPayment.getPkid());
                transSignVO.setInitAmount(toPayment.getAmount() != null ? toPayment.getAmount().divide(new BigDecimal(10000)) : null);
                transSignVO.setInitPayName(toPayment.getPayName());
                transSignVO.setInitPayTime(toPayment.getPayTime());
                transSignVO.setInitPayType(toPayment.getPayType());
            }
            else if (toPayment.getPayName().equals("二期付款"))
            {
                transSignVO.setSecPayPkid(toPayment.getPkid());
                transSignVO.setSecAmount(toPayment.getAmount() != null ? toPayment.getAmount().divide(new BigDecimal(10000)) : null);
                transSignVO.setSecPayName(toPayment.getPayName());
                transSignVO.setSecPayTime(toPayment.getPayTime());
                transSignVO.setSecPayType(toPayment.getPayType());
            }
            else if (toPayment.getPayName().equals("尾款付款"))
            {
                transSignVO.setLastPayPkid(toPayment.getPkid());
                transSignVO.setLastAmount(toPayment.getAmount() != null ? toPayment.getAmount().divide(new BigDecimal(10000)) : null);
                transSignVO.setLastPayName(toPayment.getPayName());
                transSignVO.setLastPayTime(toPayment.getPayTime());
                transSignVO.setLastPayType(toPayment.getPayType());
            }
            else if (toPayment.getPayName().equals("装修补偿款"))
            {
                transSignVO.setCompensatePayPkid(toPayment.getPkid());
                transSignVO.setCompensateAmount(toPayment.getAmount() != null ? toPayment.getAmount().divide(new BigDecimal(10000)) : null);
                transSignVO.setCompensatePayName(toPayment.getPayName());
                transSignVO.setCompensatePayTime(toPayment.getPayTime());
                transSignVO.setCompensatePayType(toPayment.getPayType());
            }
        }

        /** 读取物业信息 */
        ToPropertyInfo toPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
        if (toPropertyInfo != null)
        {
            transSignVO.setPropertyPkid(toPropertyInfo.getPkid());
            transSignVO.setPropertyAddr(toPropertyInfo.getPropertyAddr());
            transSignVO.setTotalFloor(toPropertyInfo.getTotalFloor());
            transSignVO.setLocateFloor(toPropertyInfo.getLocateFloor());
            transSignVO.setSquare(toPropertyInfo.getSquare());
            if (toPropertyInfo.getFinishYear() != null)
            {
                transSignVO.setFinishYear(DateUtil.getFormatDate(toPropertyInfo.getFinishYear(), "yyyy"));
            }
            transSignVO.setPropertyType(toPropertyInfo.getPropertyType());
        }

        /** 读取签约信息 */
        ToSign toSign = toSignMapper.findToSignByCaseCode(caseCode);
        if (toSign != null)
        {
            transSignVO.setSignPkid(toSign.getPkid());
            transSignVO.setIsConCert(toSign.getIsConCert());
            transSignVO.setIsHukou(toSign.getIsHukou());
            transSignVO.setComment(toSign.getComment());
            transSignVO.setConPrice(toSign.getConPrice()); // 合同价
            transSignVO.setRealPrice(toSign.getRealPrice()); // 成交价
            transSignVO.setRealConTime(toSign.getRealConTime());
            transSignVO.setHouseQuantity(toSign.getHouseQuantity());
            /**
             * 税费
             */
            transSignVO.setBusinessTax(toSign.getBusinessTax() != null ? toSign.getBusinessTax().divide(new BigDecimal(10000)) : null);
            transSignVO.setContractTax(toSign.getContractTax() != null ? toSign.getContractTax().divide(new BigDecimal(10000)) : null);
            transSignVO.setHouseHodingTax(toSign.getHouseHodingTax() != null ? toSign.getHouseHodingTax().divide(new BigDecimal(10000)) : null);
            transSignVO.setLandIncrementTax(toSign.getLandIncrementTax() != null ? toSign.getLandIncrementTax().divide(new BigDecimal(10000)) : null);
            transSignVO.setPersonalIncomeTax(toSign.getPersonalIncomeTax() != null ? toSign.getPersonalIncomeTax().divide(new BigDecimal(10000)) : null);
        }

        /* 读取首次跟进信息 作者：zhangxb16 时间：2016-1-27 */
        ToFirstFollow tofw = tofirstFollowMapper.selectByCaseCode(caseCode);
        if (null != tofw)
        {
            transSignVO.setIsLoanClose(tofw.getIsLoanClose()); // 抵押情况
            transSignVO.setIsPerchaseReserachNeed(tofw.getIsPerchaseReserachNeed()); // 是否需要查限购
        }

        return transSignVO;
    }

    @Override
    public ToSign findToSignByCaseCode(String caseCode)
    {
        ToSign sign = toSignMapper.findToSignByCaseCode(caseCode);
        sign.setConPrice(sign.getConPrice() != null ? sign.getConPrice().divide(new BigDecimal(10000)) : null);
        sign.setRealPrice(sign.getRealPrice() != null ? sign.getRealPrice().divide(new BigDecimal(10000)) : null);
        return sign;
    }

    @Override
    public Result2 submitSign(TransSignVO transSignVO)
    {

        SessionUser sessionUser = uamSessionService.getSessionUser();

        // 签约保存信息先更新 客户信息表
        insertGuestInfo(transSignVO);

        // 更新案件时效信息
        TsCaseEfficient tsCaseEfficient = tsCaseEfficientMapper.getCaseEffInfoByCasecode(transSignVO.getCaseCode());
        if (tsCaseEfficient != null)
        {
            tsCaseEfficient.setSignTime(new Date());
            tsCaseEfficient.setCurDelayCount(0);
            tsCaseEfficientMapper.updateTsCaseEffInfo(tsCaseEfficient);
        }

        boolean flag = true;
        // 同时需要修改贷款表里面的 主贷人信息
        ToMortgage toMortgage = new ToMortgage();
        List<Long> pkidDownList = new ArrayList<Long>();
        List<ToMortgage> toMortgageList = new ArrayList<ToMortgage>();
        if (null != transSignVO)
        {
            toMortgage.setCaseCode(transSignVO.getCaseCode() == null ? "" : transSignVO.getCaseCode());
            pkidDownList = transSignVO.getPkidDown();
            for (int i = 0; i < pkidDownList.size(); i++)
            {
                toMortgage.setCustCode(String.valueOf(pkidDownList.get(i)));
                List<ToMortgage> getMortgageByCodeList = toMortgageService.findToMortgageByCaseCodeAndCustcode(toMortgage);
                if (null == getMortgageByCodeList || getMortgageByCodeList.size() <= 0)
                {
                    continue;
                }

                for (int k = 0; k < getMortgageByCodeList.size(); k++)
                {
                    toMortgageList.add(getMortgageByCodeList.get(k));
                }
            }

            for (int i = 0; i < toMortgageList.size(); i++)
            {
                ToMortgage toMortgageItem = toMortgageList.get(i);
                if (toMortgageItem == null)
                {
                    continue;
                }
                String custCode = toMortgageItem.getCustCode();
                if (custCode == null)
                {
                    continue;
                }

                for (Long longPkid : pkidDownList)
                {
                    String strPkid = longPkid.toString();
                    if (custCode.equals(strPkid))
                    {
                        // 签约修改下家信息时，更新主贷人
                        ToMortgage toMortgageForUpdate = new ToMortgage();
                        toMortgageForUpdate.setCaseCode(transSignVO.getCaseCode() == null ? "" : transSignVO.getCaseCode());
                        toMortgageForUpdate.setCustCode(strPkid);
                        TgGuestInfo tgGuestInfo = tgGuestInfoService.findTgGuestInfoById(longPkid);
                        if (tgGuestInfo != null)
                        {
                            toMortgageForUpdate.setCustName(tgGuestInfo.getGuestName() == null ? "" : tgGuestInfo.getGuestName());
                        }
                        toMortgageService.updateToMortgageBySign(toMortgageForUpdate);
                    }
                }
            }
            // 主贷人信息在签约环境被删除时，贷款表中没有任何记录，list中的对象全部为空； 则情况贷款表的主贷人信息
            for (int m = 0; m < toMortgageList.size(); m++)
            {
                if (toMortgageList.get(m) != null)
                {
                    flag = false;
                    break;
                }
            }
            if (flag)
            {
                toMortgageService.updateToMortgageByCode(transSignVO.getCaseCode() == null ? "" : transSignVO.getCaseCode());
            }
        }

        // toMortgageService.updateToMortgage(toMortgage);

        try
        {
            /* 流程引擎相关 */
            List<RestVariable> variables = new ArrayList<RestVariable>();

            /* start 查限购和有抵押工作流 作者：zhangxb16 时间：2016-1-27 */
            RestVariable restVariable3 = new RestVariable();/* 限购 */
            restVariable3.setName("PurLimitCheckNeed");
            RestVariable restVariable4 = new RestVariable();/* 抵押 */
            restVariable4.setName("LoanCloseNeed");
            restVariable3.setValue(transSignVO.getIsPerchaseReserachNeed().equals("true"));
            restVariable4.setValue(transSignVO.getIsLoanClose().equals("true"));
            variables.add(restVariable3);
            variables.add(restVariable4);
            /* end 查限购和有抵押工作流 作者：zhangxb16 时间：2016-1-27 */

            ToCase toCase = toCaseService.findToCaseByCaseCode(transSignVO.getCaseCode());
            workFlowManager.submitTask(variables, transSignVO.getTaskId(), transSignVO.getProcessInstanceId(), toCase.getLeadingProcessId(), transSignVO.getCaseCode());

            /**
             * 签约完成之后如果sctrans.T_CS_CASE_SATISFACTION表没有对应casecode的记录则插入一条记录
             * 
             * @for 满意度评分
             */
            ToSatisfaction satis = satisfactionService.queryToSatisfactionByCaseCode(toCase.getCaseCode());
            if (satis != null)
            {
                satisfactionService.handleAfterSign(transSignVO.getCaseCode(), sessionUser.getId(), SatisfactionTypeEnum.ORIGIN.getCode());
            }
            else
            {
                satisfactionService.handleAfterSign(transSignVO.getCaseCode(), sessionUser.getId(), SatisfactionTypeEnum.NEW.getCode());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // return false;
        }

        /* 修改案件状态 */
        ToCase toCase = new ToCase();
        toCase.setCaseCode(transSignVO.getCaseCode());
        toCase.setStatus("30001003");
        toCaseService.updateByCaseCodeSelective(toCase);

        /**
         * 功能: 给客户发送短信 作者：zhangxb16
         */
        Result2 rs = new Result2();
        try
        {
            int result = tgGuestInfoService.sendMsgHistory(transSignVO.getCaseCode(), transSignVO.getPartCode());
            if (result > 0)
            {
            }
            else
            {
                rs.setMessage("短信发送失败, 请您线下手工再次发送！");
            }
        }
        catch (BusinessException ex)
        {
            ex.getMessage();
        }

        return rs;
    }

}
