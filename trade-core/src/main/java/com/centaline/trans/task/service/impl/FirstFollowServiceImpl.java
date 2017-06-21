package com.centaline.trans.task.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.bizwarn.repository.BizWarnInfoMapper;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.TsCaseEfficient;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.TsCaseEfficientMapper;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.TransDictEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.entity.ToFirstFollow;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.repository.ToFirstFollowMapper;
import com.centaline.trans.task.repository.ToSignMapper;
import com.centaline.trans.task.service.FirstFollowService;
import com.centaline.trans.task.vo.FirstFollowVO;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.repository.ToTransPlanMapper;

@Service
public class FirstFollowServiceImpl implements FirstFollowService
{

    @Autowired
    private ToCaseMapper toCaseMapper;
    @Autowired
    private ToSignMapper toSignMapper;
    @Autowired
    private ToPropertyInfoMapper toPropertyInfoMapper;
    @Autowired
    private TgServItemAndProcessorMapper tgServItemAndProcessorMapper;
    @Autowired
    private ToTransPlanMapper toTransPlanMapper;
    @Autowired
    private UamUserOrgService uamUserOrgService;
    @Autowired
    private UamBasedataService uamBasedataService;

    @Autowired
    private ToFirstFollowMapper toFirstFollowMapper;

    @Autowired
    private BizWarnInfoMapper bizWarnInfoMapper;

    @Autowired
    private UamSessionService uamSessionService;
    @Autowired
    private ProcessInstanceService processInstanceService;
    @Autowired
    private PropertyUtilsService propertyUtilsService;
    @Autowired
    private ToWorkFlowService toWorkFlowService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TsCaseEfficientMapper tsCaseEfficientMapper;

    @Override
    public boolean saveFirstFollow(FirstFollowVO firstFollowVO)
    {
        /* 交易单修改 */

        if (StringUtils.isBlank(firstFollowVO.getCaseCode()))
        {
            return false;
        }
        ToCase toCase = new ToCase();
        if (!firstFollowVO.getCaseProperty().equals("30003003"))
        {
            // toCase.setPkid(firstFollowVO.getCaseId());
            // //toCase.setCaseProperty(firstFollowVO.getCaseProperty());
            // toCaseMapper.updateByPrimaryKeySelective(toCase);
            return false;
        }
        else
        {
            toCase.setPkid(firstFollowVO.getCaseId());
            toCase.setCaseProperty(firstFollowVO.getCaseProperty());
            toCaseMapper.updateByPrimaryKeySelective(toCase);
        }
        toCase = toCaseMapper.findToCaseByCaseCode(firstFollowVO.getCaseCode());

        /* 签约表 */
        ToSign toSign = new ToSign();
        toSign.setCaseCode(firstFollowVO.getCaseCode());
        if (firstFollowVO.getRealPrice() != null)
        {
            toSign.setRealPrice(firstFollowVO.getRealPrice().multiply(new BigDecimal(10000)));
        }
        if (firstFollowVO.getConPrice() != null)
        {
            toSign.setConPrice(firstFollowVO.getConPrice().multiply(new BigDecimal(10000)));
        }
        if (firstFollowVO.getSignId() != null)
        {
            toSign.setPkid(firstFollowVO.getSignId());
            toSignMapper.updateByPrimaryKeySelective(toSign);
        }
        else
        {
            if (toSignMapper.findToSignByCaseCode(firstFollowVO.getCaseCode()) == null)
            {
                toSignMapper.insertSelective(toSign);
            }
        }

        /* 交易计划 */
        ToTransPlan toTransPlan = new ToTransPlan();
        toTransPlan.setCaseCode(firstFollowVO.getCaseCode());
        toTransPlan.setEstPartTime(firstFollowVO.getRealConTime());
        if (firstFollowVO.gettTLId() != null)
        {
            toTransPlan.setPkid(firstFollowVO.gettTLId());
            toTransPlanMapper.updateByPrimaryKeySelective(toTransPlan);
        }
        else
        {
            toTransPlan.setPartCode("TransSign");
            if (toTransPlanMapper.findTransPlan(toTransPlan) == null)
            {
                toTransPlanMapper.insertSelective(toTransPlan);
            }
        }
        /* 物业信息 */
        ToPropertyInfo toPropertyInfo = new ToPropertyInfo();
        toPropertyInfo.setCaseCode(firstFollowVO.getCaseCode());
        toPropertyInfo.setPropertyAddr(firstFollowVO.getPropertyAddr());
        toPropertyInfo.setSquare(firstFollowVO.getSquare());
        toPropertyInfo.setDistCode(firstFollowVO.getDistCode());
        if (firstFollowVO.getPropertyInfoId() != null)
        {
            toPropertyInfo.setPkid(firstFollowVO.getPropertyInfoId());
            toPropertyInfoMapper.updateByPrimaryKeySelective(toPropertyInfo);
        }
        else
        {
            if (toPropertyInfoMapper.findToPropertyInfoByCaseCode(firstFollowVO.getCaseCode()) == null)
            {
                toPropertyInfoMapper.insertSelective(toPropertyInfo);
            }
        }

        /* 服务项目和经办人 */
        TgServItemAndProcessor tsiap = new TgServItemAndProcessor();
        String[] srvCodes = null;
        if (StringUtils.isNotBlank(firstFollowVO.getSrvCode()))
        {
            srvCodes = firstFollowVO.getSrvCode().split(",");
        }
        tgServItemAndProcessorMapper.deleteByPrimaryCaseCode(firstFollowVO.getCaseCode());
        if (srvCodes != null)
        {
            for (int i = 0; i < srvCodes.length; i++)
            {
                if (srvCodes[i].equals("30004010") || srvCodes[i].equals("30004002") || srvCodes[i].equals("30004001"))
                {
                    continue;
                }
                Dict dict = uamBasedataService.findDictByTypeAndCode(TransDictEnum.TFWBM.getCode(), srvCodes[i]);
                if (dict == null)
                {
                    tsiap.setCaseCode(firstFollowVO.getCaseCode());
                    tsiap.setSrvCat(srvCodes[i]);
                    if (tgServItemAndProcessorMapper.findTgServItemAndProcessor(tsiap) == null)
                    {
                        tgServItemAndProcessorMapper.insertSelective(tsiap);
                    }
                    continue;
                }
                List<Dict> list = dict.getChildren();
                if (list == null || list.size() == 0)
                {
                    tsiap.setCaseCode(firstFollowVO.getCaseCode());
                    tsiap.setSrvCat(srvCodes[i]);
                    if (tgServItemAndProcessorMapper.findTgServItemAndProcessor(tsiap) == null)
                    {
                        tgServItemAndProcessorMapper.insertSelective(tsiap);
                    }
                    continue;
                }
                for (Dict dictSon : list)
                {
                    tsiap.setCaseCode(firstFollowVO.getCaseCode());
                    tsiap.setSrvCode(dictSon.getCode());
                    tsiap.setSrvCat(srvCodes[i]);
                    if (tgServItemAndProcessorMapper.findTgServItemAndProcessor(tsiap) == null)
                    {
                        tgServItemAndProcessorMapper.insertSelective(tsiap);
                    }
                }
            }
        }
        /* 合作项目 */
        if (firstFollowVO.getCoworkService() != null && firstFollowVO.getCoworkService().size() > 0)
        {
            for (int i = 0; i < firstFollowVO.getCoworkService().size(); i++)
            {
                tsiap.setCaseCode(firstFollowVO.getCaseCode());
                tsiap.setProcessorId(firstFollowVO.getCooperationUser().get(i));
                tsiap.setSrvCode(firstFollowVO.getCoworkService().get(i));
                tsiap.setSrvCat(getSrcCat(firstFollowVO.getCoworkService().get(i)));
                tsiap.setOrgId(getOrgId(firstFollowVO.getCooperationUser().get(i)));
                tgServItemAndProcessorMapper.insertSelective(tsiap);
            }
        }
        /* 自办项目 */
        if (firstFollowVO.getZbkServices() != null)
        {
            String[] zbCodes = firstFollowVO.getZbkServices().split(",");
            for (int i = 0; i < zbCodes.length; i++)
            {
                tsiap.setCaseCode(firstFollowVO.getCaseCode());
                tsiap.setProcessorId(toCase.getLeadingProcessId());
                tsiap.setSrvCode(zbCodes[i]);
                tsiap.setSrvCat(getSrcCat(zbCodes[i]));
                tsiap.setOrgId(toCase.getOrgId());
                tgServItemAndProcessorMapper.insertSelective(tsiap);
            }
        }

        /**
         * 功能：将抵押情况，查限购情况 ，备注等信息写入到 T_TO_FIRST_FOLLOW 表中 作者：zhangxb16
         */
        ToFirstFollow ff = new ToFirstFollow();
        ff.setCaseCode(firstFollowVO.getCaseCode());
        ff.setPartCode(firstFollowVO.getPartCode());

        String isLoanClose = null; // 抵押情况
        String isPerchaseReserachNeed = null; // 查限购情况

        if ("true".equals(firstFollowVO.getDiya()))
        { // 抵押情况
            isLoanClose = "1";
        }
        else if ("false".equals(firstFollowVO.getDiya()))
        {
            isLoanClose = "0";
        }
        else
        {
            isLoanClose = "";
        }

        if ("true".equals(firstFollowVO.getChaxiangou()))
        { // 查限购情况
            isPerchaseReserachNeed = "1";
        }
        else if ("false".equals(firstFollowVO.getChaxiangou()))
        {
            isPerchaseReserachNeed = "0";
        }
        else
        {
            isPerchaseReserachNeed = "";
        }

        ff.setIsLoanClose(isLoanClose); // 抵押情况
        ff.setIsPerchaseReserachNeed(isPerchaseReserachNeed); // 查限购情况
        ff.setComment(firstFollowVO.getComment());
        ToFirstFollow isExist = toFirstFollowMapper.selectByCaseCode(firstFollowVO.getCaseCode());
        BizWarnInfo bizWarnInfo = bizWarnInfoMapper.selectByCaseCode(firstFollowVO.getCaseCode());
        SessionUser currentUser = uamSessionService.getSessionUser();

        if (isExist != null)
        { // 如果数据库中存在记录则做updaate操作, 否则做insert操作
            ff.setPkid(isExist.getPkid());
            toFirstFollowMapper.updateByPrimaryKeySelective(ff);
        }
        else
        {
            toFirstFollowMapper.insertSelective(ff);
        }

        if (bizWarnInfo != null)
        {
            if ("true".equals(firstFollowVO.getBusinessLoanWarn()))
            {
                bizWarnInfo.setContent(firstFollowVO.getContent());

                bizWarnInfoMapper.updateByCaseCode(bizWarnInfo);
            }
            else
            {
                bizWarnInfoMapper.deleteByCaseCode(firstFollowVO.getCaseCode());
            }
        }
        else
        {
            if ("true".equals(firstFollowVO.getBusinessLoanWarn()))
            {
                bizWarnInfo = new BizWarnInfo();

                bizWarnInfo.setCaseCode(firstFollowVO.getCaseCode());
                bizWarnInfo.setContent(firstFollowVO.getContent());
                bizWarnInfo.setWarnType("LOANLOSS");
                bizWarnInfo.setCreateBy(currentUser.getId());
                bizWarnInfo.setParticipant(toCase.getLeadingProcessId());
                bizWarnInfo.setTeamId(toCase.getOrgId());
                bizWarnInfo.setStatus("0");
                bizWarnInfo.setCreateTime(new Date());
                bizWarnInfo.setWarnTime(new Date());

                Org currentOrg = uamUserOrgService.getOrgById(toCase.getOrgId());
                Org parentOrg = uamUserOrgService.getOrgById(currentOrg.getParentId());

                bizWarnInfo.setDistrictId(parentOrg.getId());

                bizWarnInfoMapper.insertSelective(bizWarnInfo);
            }
        }

        // 更新案件时效信息
        TsCaseEfficient tsCaseEfficient = tsCaseEfficientMapper.getCaseEffInfoByCasecode(firstFollowVO.getCaseCode());
        tsCaseEfficient.setFirstfollowTime(new Date());
        tsCaseEfficientMapper.updateTsCaseEffInfo(tsCaseEfficient);

        return false;
    }

    /**
     * 通过srvCode查询SrvCat
     * 
     * @param srvCode
     * @return
     */
    private String getSrcCat(String srvCode)
    {
        Dict dict = uamBasedataService.findDictByTypeAndCode(TransDictEnum.TFWBM.getCode(), srvCode);
        if (dict == null)
            return null;
        Dict dictF = uamBasedataService.findDictById(dict.getParentId());
        if (dictF == null)
            return null;
        return dictF.getCode();
    }

    /**
     * 获得合作人的orgid
     * 
     * @param userId
     * @return
     */
    private String getOrgId(String userId)
    {
        if (!StringUtils.isBlank(userId))
        {
            User user = uamUserOrgService.getUserById(userId);
            if (user != null)
            {
                return user.getOrgId();
            }
        }
        return null;
    }

    @Override
    public FirstFollowVO queryFirstFollow(String caseCode)
    {
        FirstFollowVO firstFollowVO = new FirstFollowVO();
        /* 交易单修改 */
        ToCase toCase = toCaseMapper.findToCaseByCaseCode(caseCode);
        if (toCase != null)
        {
            firstFollowVO.setCaseId(toCase.getPkid());
            firstFollowVO.setCaseProperty(toCase.getCaseProperty());
        }
        /* 交易计划 */
        List<ToTransPlan> toTransPlans = toTransPlanMapper.findTransPlanByCaseCode(caseCode);
        for (ToTransPlan toTransPlan : toTransPlans)
        {
            if (toTransPlan.getPartCode() != null && toTransPlan.getPartCode().equals("TransSign"))
            {
                firstFollowVO.settTLId(toTransPlan.getPkid());
                firstFollowVO.setRealConTime(toTransPlan.getEstPartTime());
            }
        }
        /* 签约表 */
        ToSign toSign = toSignMapper.findToSignByCaseCode(caseCode);
        if (toSign != null)
        {
            firstFollowVO.setSignId(toSign.getPkid());
            if (toSign.getRealPrice() != null)
            {
                firstFollowVO.setRealPrice(toSign.getRealPrice().divide(new BigDecimal(10000)));
            }
            if (toSign.getConPrice() != null)
            {
                firstFollowVO.setConPrice(toSign.getConPrice().divide(new BigDecimal(10000)));
            }
        }
        /* 物业信息 */
        ToPropertyInfo toPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
        if (toPropertyInfo != null)
        {
            firstFollowVO.setPropertyInfoId(toPropertyInfo.getPkid());
            firstFollowVO.setPropertyAddr(toPropertyInfo.getPropertyAddr());
            firstFollowVO.setSquare(toPropertyInfo.getSquare());
            firstFollowVO.setDistCode(toPropertyInfo.getDistCode());
        }

        /**
         * 功能：根据caseCode 到T_TO_FIRST_FOLLOW 表中去查询 pkid 作者：zhangxb16
         */
        ToFirstFollow fw = toFirstFollowMapper.selectByCaseCode(caseCode);
        if (null != fw)
        {
            firstFollowVO.setFirstfollowId(fw.getPkid());
            firstFollowVO.setComment(fw.getComment()); // 备注
            firstFollowVO.setIsPerchaseReserachNeed(fw.getIsPerchaseReserachNeed()); // 查限购情况
            firstFollowVO.setIsLoanClose(fw.getIsLoanClose()); // 抵押情况
        }

        /* 服务项目和经办人 */
        List<String> tsiapList = tgServItemAndProcessorMapper.findSrvCatsByCaseCode(caseCode);
        String srvCodes = "";
        for (int i = 0; i < tsiapList.size(); i++)
        {
            if (tsiapList.get(i) != null && tsiapList.get(i).intern().length() > 0)
            {
                srvCodes += tsiapList.get(i) + ",";
            }
        }
        if (srvCodes != null && srvCodes.intern().length() > 0)
        {
            srvCodes = srvCodes.substring(0, srvCodes.intern().length() - 1);
        }
        firstFollowVO.setSrvCode(srvCodes);

        return firstFollowVO;
    }

    @Override
    public int isExistCasecode(String caseCode)
    {
        int isExist = toFirstFollowMapper.isExistCasecode(caseCode);
        return isExist;
    }

    @Override
    public FirstFollowVO switchWorkFlowWithCurrentVersion(FirstFollowVO firstFollowVO)
    {
        // 1结束当前流程，启动一个新流程 处理workFlow表，设置vo的taskId为新的TaskID和instCode
        ToWorkFlow t = new ToWorkFlow();
        t.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
        t.setCaseCode(firstFollowVO.getCaseCode());
        ToWorkFlow mainflow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(t);
        // 流程版本小于当前版本执行
        if (mainflow != null && mainflow.getProcessDefinitionId().compareTo(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode())) < 0)
        {
            // 更新老流程为删除
            mainflow.setStatus(WorkFlowStatus.TERMINATE.getCode());
            toWorkFlowService.updateByPrimaryKeySelective(mainflow);
            // 删除老流程
            processInstanceService.deleteProcess(firstFollowVO.getProcessInstanceId());

            // 起最新版本的流程
            Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.WBUSSKEY.getCode());

            defValsMap.put("caseOwner", firstFollowVO.getUserName());
            StartProcessInstanceVo pIVo = processInstanceService.startWorkFlowByDfId(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()),
                    firstFollowVO.getCaseCode(), defValsMap);

            firstFollowVO.setProcessInstanceId(pIVo.getId());
            PageableVo tasks = taskService.listTasks(pIVo.getId(), firstFollowVO.getUserName());
            if (tasks != null && tasks.getData() != null && !tasks.getData().isEmpty())
            {
                TaskVo task = (TaskVo) tasks.getData().get(0);
                firstFollowVO.setTaskId(task.getId() + "");
            }

            ToWorkFlow wf = new ToWorkFlow();
            wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
            wf.setCaseCode(firstFollowVO.getCaseCode());
            wf.setBizCode(firstFollowVO.getCaseCode());
            wf.setProcessOwner(firstFollowVO.getUserId());
            wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
            wf.setInstCode(pIVo.getId());
            wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
            toWorkFlowService.insertSelective(wf);
        }
        return firstFollowVO;
    }

}
