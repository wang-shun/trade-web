package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.bizwarn.service.BizWarnInfoService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToOrgVo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.OrgNameEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.FirstFollowService;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.vo.FirstFollowVO;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.entity.TsTeamScope;
import com.centaline.trans.team.service.TsTeamPropertyService;
import com.centaline.trans.team.service.TsTeamScopeService;
import com.centaline.trans.utils.UiImproveUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by caoyuan7 on 2017/6/5.
 */
@Controller
@RequestMapping(value = "/task/firstFollow")
public class FirstFollowController {

    private Logger logger = Logger.getLogger(FirstFollowController.class);

    @Autowired(required = true)
    private UamSessionService uamSessionService;/* 用户信息 */
    @Autowired(required = true)
    private ToCaseService toCaseService;
    @Autowired
    private FirstFollowService firstFollowService;
    @Autowired
    private BizWarnInfoService bizWarnInfoService;
    @Autowired(required = true)
    private UamUserOrgService uamUserOrgService;/* 用户组织信息 */
    @Autowired
    private WorkFlowManager workFlowManager;
    @Autowired
    private LoanlostApproveService loanlostApproveService;
    @Autowired
    private ToCaseInfoService toCaseInfoService;/* 案件信息 */
    @Autowired
    private UamBasedataService uamBasedataService;/* 字典 */
    @Autowired
    private TsTeamScopeService tsTeamScopeService;/* 组别作业范围 */
    @Autowired
    private TsTeamPropertyService tsTeamPropertyService;/* 组别属性表 */



    @RequestMapping("process")
    @ResponseBody
    public Object toProcess(HttpServletRequest request,String caseCode) {
        JSONObject jsonObject = new JSONObject();

        SessionUser user = uamSessionService.getSessionUser();
        CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
        jsonObject.put("ctmCode", caseBaseVO.getToCase().getCtmCode());
        jsonObject.put("firstFollow", firstFollowService.queryFirstFollow(caseCode));
        jsonObject.put("approveType", "0");
        jsonObject.put("operator", user != null ? user.getId() : "");
        BizWarnInfo bizWarnInfo = bizWarnInfoService.getBizWarnInfoByCaseCode(caseCode);
        jsonObject.put("bizWarnInfo", bizWarnInfo);
        jsonObject.put("caseCode", caseCode);
        Dict dict = uamBasedataService.findDictByTypeAndLevel("yu_shanghai_district","2");
        jsonObject.put("distCode",dict);

        return jsonObject;
    }


    /**
     * 获得合作人的orgid
     *
     * @param userId
     * @return
     */
    private String getOrgId(String userId) {
        if (!StringUtils.isBlank(userId)) {
            User user = uamUserOrgService.getUserById(userId);
            if (user != null) {
                return user.getOrgId();
            }
        }
        return null;
    }

    @RequestMapping(value = "submit")
    @ResponseBody
    public Object submit(FirstFollowVO firstFollowVO) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try{
            SessionUser user = uamSessionService.getSessionUser();
            firstFollowVO.setUserId(user.getId());
            firstFollowVO.setUserOrgId(getOrgId(user.getId()));
            firstFollowVO.setUserName(user.getUsername());
            firstFollowService.saveFirstFollow(firstFollowVO);
            /* 无效案件保存到审批记录表 */
            if (firstFollowVO.getCaseProperty().equals("30003001")) {
                saveToApproveRecord(firstFollowVO, firstFollowVO.getOperator(),	firstFollowVO.getApproveType());
            } else {
                firstFollowVO = firstFollowService.switchWorkFlowWithCurrentVersion(firstFollowVO);
            }
            /* 流程引擎相关 */
            List<RestVariable> variables = new ArrayList<RestVariable>();
            RestVariable restVariable = new RestVariable();
            restVariable.setName("isvalid");
            restVariable.setValue(firstFollowVO.getCaseProperty().equals("30003001"));
            variables.add(restVariable);
            if (firstFollowVO.getCaseProperty().equals("30003001")) {
                if (!StringUtils.isBlank(firstFollowVO.getInvalid_reason())) {
                    RestVariable restVariable6 = new RestVariable();
                    restVariable6.setName("invalid_reason");
                    restVariable6.setValue(firstFollowVO.getInvalid_reason());
                    variables.add(restVariable6);
                }
            } else {
                RestVariable restVariable3 = new RestVariable();/* 限购 */
                restVariable3.setName("PurLimitCheckNeed");
                RestVariable restVariable4 = new RestVariable();/* 抵押 */
                restVariable4.setName("LoanCloseNeed");
                restVariable3.setValue(firstFollowVO.getChaxiangou().equals("true"));
                restVariable4.setValue(firstFollowVO.getDiya().equals("true"));
                variables.add(restVariable3);
                variables.add(restVariable4);
            }
            RestVariable signAssignee = new RestVariable();
            signAssignee.setName("signAssignee");
            signAssignee.setValue(user.getUsername());
            variables.add(signAssignee);
            ToCase toCase = toCaseService.findToCaseByCaseCode(firstFollowVO.getCaseCode());
            workFlowManager.submitTask(variables, firstFollowVO.getTaskId(),firstFollowVO.getProcessInstanceId(),toCase.getLeadingProcessId(), firstFollowVO.getCaseCode());
            ajaxResponse.setSuccess(true);
            ajaxResponse.setMessage("首次跟进提交成功");
        }catch (Exception e){
            ajaxResponse.setSuccess(false);
            ajaxResponse.setMessage("首次跟进提交失败");
            logger.error(e.getMessage());
        }
        return ajaxResponse;
    }

    /**
     * 保存审核记录
     *
     * @param firstFollowVO
     * @param operator
     * @param approveType
     * @return
     */

    private ToApproveRecord saveToApproveRecord(FirstFollowVO firstFollowVO,
                                                String operator, String approveType) {
        ToApproveRecord toApproveRecord = new ToApproveRecord();
        // toApproveRecord.setPkid(loanlostApproveVO.getLapPkid());
        toApproveRecord
                .setProcessInstance(firstFollowVO.getProcessInstanceId());
        toApproveRecord.setPartCode(firstFollowVO.getPartCode());
        toApproveRecord.setOperatorTime(new Date());
        toApproveRecord.setApproveType(approveType);
        toApproveRecord.setCaseCode(firstFollowVO.getCaseCode());
        toApproveRecord.setContent(firstFollowVO.getInvalid_reason());
        toApproveRecord.setOperator(operator);
        loanlostApproveService.saveLoanlostApprove(toApproveRecord);
        return toApproveRecord;
    }


    @RequestMapping(value = "queryMortageServiceByServiceCode")
    @ResponseBody
    public Object queryMortageServiceByServiceCode(String serviceCode) {
        String orgCode = null;
        Map<String, Object> result = new HashMap<>();
        Dict dict = uamBasedataService.findDictByTypeAndCode("yu_all",serviceCode);
        SessionUser us = uamSessionService.getSessionUser();
        if (null != us) {
            orgCode = us.getServiceDepCode(); // 得到 orgCode
        }
        Org myDistrict = uamUserOrgService.getParentOrgByDepHierarchy(us.getServiceDepId(), DepTypeEnum.TYCQY.getCode()); // 获取用户的所在的贵宾服务部
        List<JSONObject> jsonList = new ArrayList<>();
        List<TsTeamScope> tsTeamScopes = tsTeamScopeService.selectByOrgCode(orgCode);
        Set<String> orgs = new HashSet<String>();
		/* 遍历合作组列表 封装ServiceOrgMap */
        for (int i = 0; i < tsTeamScopes.size(); i++) {
            TsTeamScope ts = tsTeamScopes.get(i);
			/* 组织查询条件根据tsTeamScope.getYuTeamCode(),IsResponseTeam 判断合作组是否符合条件 */
            TsTeamProperty tp = new TsTeamProperty();
            tp.setYuTeamCode(ts.getYuTeamCode());
			/* tp.setIsResponseTeam("0"); 合作组必须为非主办组 */
            // TsTeamProperty ttps =
            // tsTeamPropertyService.findTeamPropertyCooperation(tp);
			/* 浦东合作顾问选中台 */
            if ("FF5BC56E0E4B45289DAA5721A494C7C5".equals(myDistrict.getId())) {
                tp.setYuTeamCode(orgCode);
                List<TsTeamProperty> ttpps = tsTeamPropertyService
                        .findTeamPropertyCooperations(tp);
                if (ttpps != null) {/* 有符合条件的合作组 */
                    for (TsTeamProperty ttp : ttpps) {
                        if (ttp.getYuTeamCode() != null) {
                            orgs.add(ttp.getYuTeamCode());
                        }
                    }
                }
            } else {// 非浦东服务部按原来的逻辑
                TsTeamProperty ttps = tsTeamPropertyService
                        .findTeamPropertyCooperation(tp);
                if (ttps != null) {/* 有符合条件的合作组 */
                    List<Dict> dictList = getDictList(ttps.getTeamProperty());
                    if (dictList != null && dictList.size() > 0) {
                        for (Dict d : dictList) {
                            if (serviceCode.equals(d.getCode())) {
                                orgs.add(ts.getYuTeamCode());
                                if (dict == null)
                                    dict = d;
                                break;
                            }
                        }
                    }
                }
            }

        }
        for (String orgStr : orgs) {
            Org org = uamUserOrgService.getOrgByCode(orgStr);
			/* 浦东合作顾问选中台 且只选浦东交易1组的中台 */
            List<User> list = null;
            if ("FF5BC56E0E4B45289DAA5721A494C7C5".equals(myDistrict.getId())) {
                if (OrgNameEnum.T_PUDONGTRADEONE_ORG.getCode().equals(
                        org.getOrgCode())) {
                    list = uamUserOrgService.getUserByOrgIdAndJobCode(
                            org.getId(), TransJobs.JYUZTGW.getCode());
                } else {
                    list = uamUserOrgService.getUserByOrgIdAndJobCode(
                            org.getId(), TransJobs.TJYGW.getCode());
                }
            } else {
                list = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(),
                        TransJobs.TJYGW.getCode());
            }
            if (list != null) {
                for (User user3 : list) {
                    int userCaseUnTransCount = toCaseInfoService
                            .queryCountUnTransCasesByUserId(user3.getId());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("count", userCaseUnTransCount);
                    jsonObject.put("realName", user3.getRealName());
                    jsonObject.put("orgName", user3.getOrgName());
                    jsonObject.put("id", user3.getId());
                    jsonList.add(jsonObject);
                }
            }

        }
        result.put("dic", dict);
        result.put("users", jsonList);
        result.put("orgcode", myDistrict.getOrgCode());/* 浦东合作顾问选中台 */
        return result;
    }

    /* 获取跨区合作的选项 */
    @RequestMapping("getCrossAeraCooperationItems")
    @ResponseBody
    public Map<String, Object> getCrossAeraCooperationItems(
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        SessionUser us = uamSessionService.getSessionUser();

        // 获取所有的贵宾服务部
        List<ToOrgVo> orgIdList = toCaseService
                .getOrgIdAllByDep(DepTypeEnum.TYCQY.getCode());
        Org myDistrict = uamUserOrgService.getParentOrgByDepHierarchy(
                us.getServiceDepId(), DepTypeEnum.TYCQY.getCode()); // 获取用户的所在的贵宾服务部

        // 获取下拉的贵宾服务组
        List<JSONObject> jsonList1 = new ArrayList<JSONObject>();
        if (orgIdList != null && orgIdList.size() > 0 && myDistrict != null) {
            for (ToOrgVo toOrgVo : orgIdList) {
                Org district = uamUserOrgService.getOrgById(toOrgVo.getId());
                if (!myDistrict.getId().equals(district.getId())&& !"b4c490edc38c431a8dfd7dba98c73fe5".equals(district.getId())&& !"8a8493d4538a517a01539d47b51c1b02".equals(district.getId())) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("districtId", district.getId());
                    jsonObject.put("districtName", district.getOrgName());
                    jsonList1.add(jsonObject);

                    // 获取该贵宾服务部下的后台组
                    List<Org> orgList = uamUserOrgService.getOrgByParentId(district.getId());
                    List<JSONObject> jsonList2 = new ArrayList<JSONObject>();
                    if (orgList != null && orgList.size() > 0) {
                        for (Org org : orgList) {
                            TsTeamProperty tsTeamProperty = tsTeamPropertyService.findTeamPropertyByTeamCode(org.getOrgCode());
                            if (tsTeamProperty != null) {
                                if ("yu_all".equals(tsTeamProperty.getTeamProperty()) || "yu_back".equals(tsTeamProperty.getTeamProperty())) {
                                    JSONObject subJsonObj = new JSONObject();
                                    subJsonObj.put("orgId", org.getId());
                                    subJsonObj.put("orgName", org.getOrgName());
                                    jsonList2.add(subJsonObj);

                                    // 获取交易顾问
                                    List<User> list = uamUserOrgService.getUserByOrgIdAndJobCode(org.getId(),TransJobs.TJYGW.getCode());
                                    List<JSONObject> jsonList3 = new ArrayList<JSONObject>();
                                    if (list != null && list.size() > 0) {
                                        for (User user : list) {
                                            JSONObject userJsonObj = new JSONObject();
                                            int userCaseUnTransCount = toCaseInfoService.queryCountUnTransCasesByUserId(user.getId());
                                            userJsonObj.put("id", user.getId());
                                            userJsonObj.put("realName", user.getRealName());
                                            userJsonObj.put("count", userCaseUnTransCount);
                                            jsonList3.add(userJsonObj);
                                        }
                                    }
                                    subJsonObj.put("userItems", jsonList3);
                                }
                            }
                        }
                    }
                    jsonObject.put("orgs", jsonList2);
                }
            }
        }
        result.put("cross", jsonList1);
        return result;
    }

    /**
     * 根据字典类型，获得相应字典数据
     *
     * @param dictByType
     * @return
     */
    private List<Dict> getDictList(String dictByType) {
        List<Dict> list = new ArrayList<Dict>();
        Dict dict = uamBasedataService.findDictByType(dictByType);
        if (dict != null) {
            list = dict.getChildren();
        }
        return list;
    }
}
