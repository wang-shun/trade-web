package com.centaline.trans.cases.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.TsCaseEfficient;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.service.TsCaseEfficientService;
import com.centaline.trans.cases.vo.VCaseDistributeUserVO;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.OrgNameEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.TsPrResearchMap;
import com.centaline.trans.task.service.TsPrResearchMapService;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.entity.TsTeamScopeTarget;
import com.centaline.trans.team.entity.TsTeamTransfer;
import com.centaline.trans.team.service.TsTeamPropertyService;
import com.centaline.trans.team.service.TsTeamScopeTargetService;
import com.centaline.trans.team.service.TsTeamTransferService;
import com.centaline.trans.team.vo.TeamTransferVO;

@Controller
@RequestMapping(value = "/case")
/**
 * 
 * <p>
 * Project: 案件分配
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright (c) 2015
 * </p>
 * 
 * @author wanggh</a>
 */
public class CaseDistributeController
{

    @Autowired(required = true)
    UamSessionService uamSessionService;
    @Autowired(required = true)
    UamUserOrgService uamUserOrgService;
    @Autowired(required = true)
    ToCaseInfoService toCaseInfoService;
    @Autowired(required = true)
    ToCaseService toCaseService;
    @Autowired(required = true)
    WorkFlowManager workFlowManager;

    @Autowired(required = true)
    ToPropertyInfoService toPropertyInfoService;
    @Autowired(required = true)
    @Qualifier("uamMessageServiceClient")
    UamMessageService uamMessageService;
    @Autowired(required = true)
    UamTemplateService uamTemplateService;
    @Autowired(required = true)
    PropertyUtilsService propertyUtilsService;
    @Autowired(required = true)
    ToWorkFlowService toWorkFlowService;
    @Autowired(required = true)
    TsTeamPropertyService tsTeamPropertyService;
    @Autowired(required = true)
    TsTeamTransferService tsTeamTransferService;
    @Autowired(required = true)
    TsPrResearchMapService tsPrResearchMapService;
    @Autowired(required = true)
    TsTeamScopeTargetService tsTeamScopeTargetService;
    @Autowired
    private TsCaseEfficientService tsCaseEfficientService;

    /**
     * 页面初始化 @return String 返回类型 @throws
     */
    @RequestMapping(value = "caseDistribute")
    public String caseDistribute(Model model, ServletRequest request)
    {
        // TODO
        SessionUser user = uamSessionService.getSessionUser();
        String userJob = user.getServiceJobCode();
        String queryUserId = user.getId();
        String queryOrgId = user.getServiceDepId();
        if (userJob.equals(TransJobs.TJYZL.getCode()))
        {
            List<User> userList = uamUserOrgService.getUserByOrgIdAndJobCode(user.getServiceDepId(), TransJobs.TJYZG.getCode());
            if (userList != null && userList.size() > 0)
            {
                queryUserId = userList.get(0).getId();
            }
        }
        request.setAttribute("queryUserId", queryUserId);
        request.setAttribute("queryOrgId", queryOrgId);
        return "case/caseDistribute2";
    }

    /**
     * 页面初始化 @return String 返回类型 @throws
     */
    @RequestMapping(value = "unlocatedCase")
    public String unlocatedCase(Model model, ServletRequest request)
    {
        Org o = uamUserOrgService.getOrgByCode("033F275");
        model.addAttribute("nonBusinessOrg", o);
        // 无主案件后台主管不允许分配
        SessionUser user = uamSessionService.getSessionUser();
        String jobCode = user.getServiceJobCode();
        // 判断组织是否为后台的
        TsTeamProperty tp = tsTeamPropertyService.findTeamPropertyByTeamCode(user.getServiceDepCode());
        boolean isBackTeam = false;
        if (tp != null)
        {
            isBackTeam = "yu_back".equals(tp.getTeamProperty());
        }
        model.addAttribute("isBackTeam", isBackTeam);
        model.addAttribute("jobCode", jobCode);

        return "case/unlocatedCase2";
    }

    /**
     * 用户机构交易顾问查询
     * 
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/getUserOrgCpUserList")
    @ResponseBody
    public List<VCaseDistributeUserVO> getUserOrgCpUserList(HttpServletRequest request, String caseCode) throws ParseException
    {
        List<VCaseDistributeUserVO> res = new ArrayList<VCaseDistributeUserVO>();
        // 获取当前用户
        SessionUser sessionUser = uamSessionService.getSessionUser();
        // 获取机构交易顾问列表
        List<User> userList = uamUserOrgService.getUserByOrgIdAndJobCode(sessionUser.getServiceDepId(), TransJobs.TJYGW.getCode());
        if (!StringUtils.isBlank(caseCode))
        {
            VCaseDistributeUserVO vd = toCaseService.getVCaseDistributeUserVO(caseCode);
            if (null != vd)
            {
                res.add(vd);
            }
            ;
        }
        for (int i = 0; i < userList.size(); i++)
        {
            VCaseDistributeUserVO vo = new VCaseDistributeUserVO();
            User user = userList.get(i);
            vo.setId(user.getId());
            vo.setMobile(user.getMobile());
            vo.setRealName(user.getRealName());
            int userCaseCount = toCaseInfoService.queryCountCasesByUserId(user.getId());
            int userCaseMonthCount = toCaseInfoService.queryCountMonthCasesByUserId(user.getId());
            int userCaseUnTransCount = toCaseInfoService.queryCountUnTransCasesByUserId(user.getId());

            vo.setUserCaseCount(userCaseCount);
            vo.setUserCaseMonthCount(userCaseMonthCount);
            vo.setUserCaseUnTransCount(userCaseUnTransCount);
            String url = "http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/" + user.getEmployeeCode() + ".jpg";
            /** 所用合作顾问 **/
            vo.setType("ALL");
            vo.setImgUrl(url);

            res.add(vo);
        }

        return res;
    }

    /**
     * 用户同级别机构查询
     * 
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/getOrgTeamList")
    @ResponseBody
    public List<Org> getOrgTeamList(HttpServletRequest request) throws ParseException
    {
        List<Org> res = new ArrayList<Org>();
        // 获取当前用户
        SessionUser sessionUser = uamSessionService.getSessionUser();
        // 获取机构交易顾问列表
        Org parentOrg = uamUserOrgService.getParentOrgByDepHierarchy(sessionUser.getServiceDepId(), DepTypeEnum.TYCQY.getCode());
        // 是否主办组
        List<Org> noResponseTeamList = new ArrayList<Org>();
        res = uamUserOrgService.getOrgByDepHierarchy(parentOrg.getId(), DepTypeEnum.TYCTEAM.getCode());
        for (Org org : res)
        {

            if (org.getId().equals(sessionUser.getServiceDepId()))
            {
                noResponseTeamList.add(org);
                continue;
            }
            TsTeamProperty tsTeamProperty = tsTeamPropertyService.findTeamPropertyByTeamCode(org.getOrgCode());
            if (tsTeamProperty == null || tsTeamProperty.getIsResponseTeam() == null || !tsTeamProperty.getIsResponseTeam().equals("1"))
            {
                noResponseTeamList.add(org);
            }
        }

        res.removeAll(noResponseTeamList);
        return res;
    }
    
    /**
     * TODO by: yinchao
     * 用于组别属性配置
     * 获取组别信息
     * 
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/getAllTeamListToConfig")
    @ResponseBody
    public List<Org> getAllTeamListtoConfig(HttpServletRequest request) throws ParseException
    {
        List<Org> res = new ArrayList<Org>();
        Org parentOrg = uamUserOrgService.getOrgByCode(OrgNameEnum.T_FATHER_ORG.getCode());
        res = uamUserOrgService.getOrgByDepHierarchy(parentOrg.getId(), DepTypeEnum.TYCTEAM.getCode());
        return res;
    }
    
    /**
     * 机构查询
     * 
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/getAllTeamList")
    @ResponseBody
    public List<Org> getAllTeamList(HttpServletRequest request) throws ParseException
    {
        List<Org> res = new ArrayList<Org>();
        // 获取机构交易顾问列表
        Org parentOrg = uamUserOrgService.getOrgByCode(OrgNameEnum.T_FATHER_ORG.getCode());
        res = uamUserOrgService.getOrgByDepHierarchy(parentOrg.getId(), DepTypeEnum.TYCTEAM.getCode());
        // 是否主办组
        List<Org> noResponseTeamList = new ArrayList<Org>();
        for (Org org : res)
        {
            TsTeamProperty tsTeamProperty = tsTeamPropertyService.findTeamPropertyByTeamCode(org.getOrgCode());
            if (tsTeamProperty == null || tsTeamProperty.getIsResponseTeam() == null || !tsTeamProperty.getIsResponseTeam().equals("1"))
            {
                noResponseTeamList.add(org);
            }
        }
        res.removeAll(noResponseTeamList);
        return res;
    }

    /**
     * 案件是否转给了其他服务区域
     * 
     * @return @throws
     */
    @RequestMapping(value = "/isTransferOtherDistrict")
    @ResponseBody
    public AjaxResponse<Boolean> isTransferOtherDistrict(String[] caseCodes, String userId, HttpServletRequest request)
    {
        boolean isTransferOther = false;

        // 查找转给的用户能够服务的区域
        SessionUser distributeUser = uamSessionService.getSessionUserById(userId);
        Org parentOrg = uamUserOrgService.getParentOrgByDepHierarchy(distributeUser.getServiceDepId(), DepTypeEnum.TYCQY.getCode());
        TsPrResearchMap param = new TsPrResearchMap();
        param.setYuDistCode(parentOrg.getOrgCode());
        List<TsPrResearchMap> tsPrResearchMapList = tsPrResearchMapService.getTsPrResearchMapByProperty(param);

        for (String caseCode : caseCodes)
        {

            ToPropertyInfo propertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode);
            //房屋信息为空 或者所属区域为空 则无法进行校验
            if(propertyInfo==null || StringUtils.isBlank(propertyInfo.getDistCode())) continue;
            boolean isExistNoPatter = true;
            //TODO 改写案件所属区域校验规则
            for (TsPrResearchMap tsPrResearchMap : tsPrResearchMapList){
                if (propertyInfo.getDistCode().equalsIgnoreCase(tsPrResearchMap.getDistCode().trim())){
                    isExistNoPatter = false;
                    break;
                }
            }
            
            // 房源编号
//            String delCode = propertyInfo.getPropertyAgentId();
//            if (StringUtils.isBlank(delCode))
//            {
//                continue;
//            }
            // 当前案件属于的区域
//            boolean isExistNoPatter = true;
//            String districtCode = "";
            //TODO 需要获取房源信息 判定房源的所属区域 by : yinchao
//            ViHouseDelBaseVo housevo = toPropertyInfoService.getHouseBaseByHoudelCode(delCode);
//            if (housevo != null && StringUtils.isNotBlank(housevo.getDISTRICT_CODE()))
//            {
//                districtCode = housevo.getDISTRICT_CODE().trim();
//                for (TsPrResearchMap tsPrResearchMap : tsPrResearchMapList)
//                {
//                    if (districtCode.equalsIgnoreCase(tsPrResearchMap.getDistCode().trim()))
//                    {
//                        isExistNoPatter = false;
//                        continue;
//                    }
//                }
//            }

            if (isExistNoPatter)
            {
                isTransferOther = true;
                break;
            }
        }
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setContent(isTransferOther);
        return ajaxResponse;
    }

    /**
     * 分配案件
     * 
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/bindCaseDist")
    @ResponseBody
    public AjaxResponse<?> bindCaseDist(String[] caseCodes, String userId, HttpServletRequest request)
    {
        SessionUser sessionUser = uamSessionService.getSessionUser();
        for (String caseCode : caseCodes)
        {
            try
            {
                // 非自己组的案件，不能进行分配
                ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(caseCode);

                if (toCaseInfo == null)
                {
                    continue;
                }

                TsTeamScopeTarget teamScopeTarget = new TsTeamScopeTarget();
                teamScopeTarget.setGrpCode(toCaseInfo.getTargetCode());
                teamScopeTarget.setYuTeamCode(sessionUser.getServiceDepCode());
                List<TsTeamScopeTarget> tsTeamScopeTargetList = tsTeamScopeTargetService.getTeamScopeTargetListByProperty(teamScopeTarget);
                if (CollectionUtils.isEmpty(tsTeamScopeTargetList))
                {
                    continue;
                }

                toCaseService.caseAssign(caseCode, userId, sessionUser);
                
                //TODO MQ未配置 获取先注释掉
//                toCaseService.sendcaseAssignMsg(caseCode, userId, sessionUser);

                boolean isExist = tsCaseEfficientService.isExistByCaseCode(caseCode);

                if (!isExist)
                {
                    // 添加案件时效信息
                    TsCaseEfficient tsCaseEfficient = new TsCaseEfficient();
                    tsCaseEfficient.setDispatchTime(new Date());
                    tsCaseEfficient.setCaseCode(caseCode);
                    // 时效考核标准：首次跟进1天、签约15天、过户30天、结案30天
                    tsCaseEfficient.setFirstfollowEff(1);
                    tsCaseEfficient.setSignEff(15);
                    tsCaseEfficient.setGuohuEff(30);
                    tsCaseEfficient.setCasecloseEff(30);
                    // 默认保存延期次数都为0
                    tsCaseEfficient.setFirstfollowDly(0);
                    tsCaseEfficient.setSignDly(0);
                    tsCaseEfficient.setGuohuDly(0);
                    tsCaseEfficient.setCasecloseDly(0);
                    tsCaseEfficient.setCurDelayCount(0);
                    tsCaseEfficient.setHisOverdueCount(0);

                    // 保存案件时效信息
                    int count = tsCaseEfficientService.save(tsCaseEfficient);

                    if (count == 0)
                        return AjaxResponse.fail("案件时效表保存失败！");
                }

            }
            catch (BusinessException | WorkFlowException e)
            {
                e.printStackTrace();
                return AjaxResponse.fail(e.getMessage());
            }
        }
        return AjaxResponse.success("案件信息绑定成功！");
    }

    /****
     * 案件转组日志
     * 
     * @param caseCode
     * @return
     */
    private int addTeamTransferLog(ToCaseInfo toCaseInfo, String orgId)
    {
        TsTeamTransfer teamTransfer = new TsTeamTransfer();
        // 未被删除,更新状态
        teamTransfer.setIsDelete("0");
        teamTransfer.setCaseCode(toCaseInfo.getCaseCode());
        teamTransfer.setTeamOrigin(toCaseInfo.getGrpCode());
        TsTeamTransfer tsTeamTransferOld = tsTeamTransferService.getTsTeamTransferByProperty(teamTransfer);
        if (tsTeamTransferOld != null)
        {
            tsTeamTransferOld.setIsDelete("1");
            int updateCount = tsTeamTransferService.updateByPrimaryKeySelective(tsTeamTransferOld);
        }
        Org org = uamUserOrgService.getOrgById(orgId);
        TsTeamTransfer teamTransferNew = new TsTeamTransfer();
        teamTransferNew.setCaseCode(toCaseInfo.getCaseCode());
        teamTransferNew.setIsDelete("0");
        teamTransferNew.setTeamOrigin(toCaseInfo.getGrpCode());
        teamTransferNew.setTeamNow(org.getOrgCode());
        int addCount = tsTeamTransferService.insertSelective(teamTransferNew);

        return addCount;
    }

    /**
     * 分配组别
     * 
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/bindCaseTeam")
    @ResponseBody
    public AjaxResponse<?> bindCaseTeam(@RequestBody TeamTransferVO teamTransferVO, HttpServletRequest request)
    {
        SessionUser sessionUser = uamSessionService.getSessionUser();
        List<User> managerUsers = uamUserOrgService.getUserByOrgIdAndJobCode(teamTransferVO.getOrgId(), TransJobs.TJYZG.getCode());
        if (CollectionUtils.isEmpty(managerUsers))
        {
            return AjaxResponse.fail("未找到交易主管！");
        }

        User managerUser = managerUsers.get(0);
        List<ToCaseInfo> caseInfoList = teamTransferVO.getCaseInfoList();
        Org org = uamUserOrgService.getOrgById(teamTransferVO.getOrgId());
        for (ToCaseInfo toCaseInfoNew : caseInfoList)
        {
            int addTeamTrasLogCount = addTeamTransferLog(toCaseInfoNew, teamTransferVO.getOrgId());
            if (addTeamTrasLogCount == 0)
                return AjaxResponse.fail("案件信息转组记录日志失败！");
            // 案件信息更新
            ToCase toCase = toCaseService.findToCaseByCaseCode(toCaseInfoNew.getCaseCode());
            if (toCase != null)
            {

                TsTeamScopeTarget teamScopeTarget = new TsTeamScopeTarget();
                teamScopeTarget.setGrpCode(toCaseInfoNew.getTargetCode());
                teamScopeTarget.setYuTeamCode(sessionUser.getServiceDepCode());
                List<TsTeamScopeTarget> tsTeamScopeTargetList = tsTeamScopeTargetService.getTeamScopeTargetListByProperty(teamScopeTarget);
                if (CollectionUtils.isEmpty(tsTeamScopeTargetList))
                {
                    continue;
                }
                toCase.setLeadingProcessId(managerUser.getId());
                // 填写誉翠组
                toCase.setOrgId(teamTransferVO.getOrgId());
                // 填写贵宾服务部
                toCase.setDistrictId(org == null ? null : org.getParentId());
                int reToCase = toCaseService.updateByPrimaryKey(toCase);
                if (reToCase == 0)
                    return AjaxResponse.fail("案件基本表更新失败！");
            }
            ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(toCaseInfoNew.getCaseCode());
            toCaseInfo.setRequireProcessorId(managerUser.getId());
            toCaseInfo.setTargetCode(org.getOrgCode());
            int reToCaseInfo = toCaseInfoService.updateByPrimaryKey(toCaseInfo);
            if (reToCaseInfo == 0)
                return AjaxResponse.fail("案件信息表更新失败！");
        }
        return AjaxResponse.success("案件信息绑定成功！");
    }

    /**
     * 分配组别
     * 
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/bindUnLocatedCaseTeam")
    @ResponseBody
    public AjaxResponse<?> bindUnLocatedCaseTeam(String[] caseCodes, String orgId, String orgName, HttpServletRequest request)
    {
        if (orgId == null)
        {
            return AjaxResponse.fail("请选择一个片区！");
        }
        Org org = uamUserOrgService.getOrgById(orgId);
        if (org != null && DepTypeEnum.BUSIAR.getCode().equals(org.getDepHierarchy()))
        {
            for (String caseCode : caseCodes)
            {
                ToCaseInfo toCaseInfo = toCaseInfoService.findToCaseInfoByCaseCode(caseCode);
                toCaseInfo.setArCode(org.getOrgCode());
                toCaseInfo.setArName(org.getOrgName());
                toCaseInfo.setDispatchTime(new Date());
                int reToCaseInfo = toCaseInfoService.updateByPrimaryKey(toCaseInfo);
                if (reToCaseInfo == 0)
                    return AjaxResponse.fail("案件信息表更新失败！");

            }
            return AjaxResponse.success("案件信息绑定成功！");
        }
        else
        {
            return AjaxResponse.fail("请选择一个片区！");
        }
    }

    /**
     * 查询是否有可以合流的案件
     * 
     * @author hejf10 2016-12-26 11:08:00
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/getMergeInfoList")
    @ResponseBody
    public AjaxResponse<?> getMergeInfoList(HttpServletRequest request, List<ToPropertyInfo> toPropertyInfos) throws ParseException
    {
        AjaxResponse<String> response = new AjaxResponse<>();
        try
        {
            toCaseService.getMergeInfoList(toPropertyInfos);
            response.setSuccess(true);
        }
        catch (Exception e)
        {
            response.setSuccess(false);
            String sOut = "";
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement s : trace)
            {
                sOut += "\tat " + s + "\r\n";
            }
            response.setMessage(e.getMessage() + "异常：" + sOut);
            e.printStackTrace();
        }
        return response;
    }

}
