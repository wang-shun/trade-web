package com.centaline.parportal.mobile.eloancase.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuerysParseService;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.common.enums.LoanerStatusEnum;
import com.centaline.trans.eloan.entity.ToEloanLoaner;
import com.centaline.trans.eloan.service.ToEloanCaseService;
import com.centaline.trans.eloan.service.ToEloanLoanerService;
import com.centaline.trans.eloan.vo.ELoanVo;

/**
 * eLoan案件控制器
 * 
 * @author yinjf2
 *
 */
@Controller
@RequestMapping(value = "/eloanCase")
public class ELoanCaseController
{

    @Resource(name = "quickGridService")
    private QuickGridService quickGridService;

    @Autowired(required = true)
    UamSessionService uamSessionService;
    @Autowired
    private QuerysParseService querysParseService;
    @Autowired
    ToEloanCaseService toEloanCaseService;

    @Autowired
    private ToEloanLoanerService toEloanLoanerService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String queryELoanCaseDetail = "queryELoanCaseDetail";
    private final static String queryELoanProcess = "queryELoanProcess";
    private final static String queryELoanTradeProcess = "queryELoanTradeProcess";

    /**
     * 信息补充、补件
     * 
     * @param bizCode
     *            E+金融编号
     * @param type
     *            如果信息补充,type为CMT,补件,type为BUJIAN
     * @param stateInBank
     *            跟进类型
     * @param caseCode
     *            案件编号
     * @param comment
     *            备注
     * @return
     */
    @RequestMapping(value = "suppleInfo")
    @ResponseBody
    public String suppleInfo(String bizCode, String type, String caseCode, String comment)
    {

        if (bizCode == null || "".equals(bizCode) || type == null || "".equals(type) || comment == null || "".equals(comment) || caseCode == null
                || "".equals(caseCode))
        {
            throw new BusinessException("请检查参数!");
        }

        // 获取当前用户信息
        SessionUser sessionUser = uamSessionService.getSessionUser();

        // 根据主键id获取E+案件接收信息
        ToEloanLoaner toEloanLoaner = toEloanLoanerService.getToEloanLoanerByELoanCode(bizCode);

        // 判断当前用户是否是同一个信贷员
        if (!sessionUser.getId().equals(toEloanLoaner.getLoanerId()))
        {
            throw new BusinessException("当前用户跟案件所属信贷员不是同一人,不能进行操作!");
        }

        // 判断案子是否有效
        if (LoanerStatusEnum.ACC_REJECTED.getCode().equals(toEloanLoaner.getLoanerStatus())
                || LoanerStatusEnum.AUD_REJECTED.getCode().equals(toEloanLoaner.getLoanerStatus())
                || LoanerStatusEnum.CANCELED.getCode().equals(toEloanLoaner.getLoanerStatus()))
        {
            throw new BusinessException("案件无效,不能进行操作!");
        }

        // 设置前台传的参数信息
        ELoanVo eLoanVo = new ELoanVo();
        eLoanVo.seteLoanCode(bizCode);
        eLoanVo.setSource("CARD");
        eLoanVo.setType(type);
        eLoanVo.setStateInBank(type);
        eLoanVo.setCaseCode(caseCode);
        eLoanVo.setComment(comment);
        eLoanVo.setUser(sessionUser);

        try
        {
            toEloanCaseService.suppleInfo(eLoanVo);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * E+案件跟进
     * 
     * @param eLoanCode
     *            E+金融编号
     * @param stateInBank
     *            状态
     * @param caseCode
     *            案件编号
     * @param comment
     *            案件跟进备注
     * @return 返回true,操作成功;返回false,操作失败。
     */
    @RequestMapping(value = "track/followUp")
    @ResponseBody
    public String followUp(String eLoanCode, String stateInBank, String caseCode, String comment)
    {

        if (eLoanCode == null || stateInBank == null || caseCode == null)
        {
            throw new BusinessException("请检查参数!");
        }

        // 获取当前用户信息
        SessionUser sessionUser = uamSessionService.getSessionUser();

        // 设置前台传的参数信息
        ELoanVo eLoanVo = new ELoanVo();
        eLoanVo.seteLoanCode(eLoanCode);
        eLoanVo.setStateInBank(stateInBank);
        eLoanVo.setCaseCode(caseCode);
        eLoanVo.setComment(comment);
        eLoanVo.setUser(sessionUser);

        try
        {
            toEloanCaseService.followUp(eLoanVo);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * E+贷款信贷员接单和打回
     * 
     * @param eLoanCode
     *            E+金融编号
     * @param isPass
     *            是否接单,isPass如果为true,信贷员接单;为false,信贷员打回;
     * @param taskId
     *            任务id
     * @param caseCode
     *            案件编号
     * @param comment
     *            案件跟进备注
     * @return 返回true,操作成功;返回false,操作失败。
     */
    @RequestMapping(value = "track/accept")
    @ResponseBody
    public String accept(String eLoanCode, String isPass, String taskId, String stateInBank, String caseCode, String comment)
    {

        if (eLoanCode == null || "".equals(eLoanCode) || isPass == null || taskId == null || "".equals(taskId) || stateInBank == null || caseCode == null)
        {
            throw new BusinessException("信贷员接单请求参数异常!");
        }

        // 获取当前用户信息
        SessionUser sessionUser = uamSessionService.getSessionUser();

        // 设置前台传的参数信息
        ELoanVo eLoanVo = new ELoanVo();
        eLoanVo.seteLoanCode(eLoanCode);
        eLoanVo.setIsPass(isPass);
        eLoanVo.setTaskId(taskId);
        eLoanVo.setStateInBank(stateInBank);
        eLoanVo.setCaseCode(caseCode);
        eLoanVo.setComment(comment);
        eLoanVo.setUser(sessionUser);

        // 定义流程引擎所需参数
        Map<String, Object> map = new HashMap<String, Object>();

        if ("true".equals(eLoanVo.getIsPass()))
        {
            map.put("LoanerApprove", true);
        }
        else if ("false".equals(eLoanVo.getIsPass()))
        {
            map.put("LoanerApprove", false);
        }

        try
        {
            toEloanCaseService.accept(eLoanVo, map, eLoanVo.getTaskId());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(Integer page, Integer pageSize, String sidx, String sord, String condition, String loanerStatusCode)
    {
        JQGridParam gp = new JQGridParam();
        gp.setPagination(true);
        gp.setPage(page);
        gp.setRows(pageSize);
        gp.setQueryId("eloanCaseListQuery");
        gp.setSidx(sidx);
        gp.setSord(sord);
        Map<String, Object> paramter = new HashMap<String, Object>();

        SessionUser sessionUser = uamSessionService.getSessionUser();
        paramter.put("loanerId", sessionUser.getId());
        // paramter.put("loanerId", "ff80808158bd58c10158bda37f100020");

        if (StringUtils.isNotBlank(loanerStatusCode))
        {
            paramter.put("loanerStatusCode", loanerStatusCode.split(","));
        }

        if (condition != null)
        {
            String formatCondtion = condition.trim();

            if (!"".equals(formatCondtion))
            {
                paramter.put("condition", formatCondtion);
            }
        }

        gp.putAll(paramter);
        querysParseService.reloadFile();

        // 查询E+列表信息(待接单、待审核、完成)
        Page<Map<String, Object>> eLoanCaseMapList = quickGridService.findPageForSqlServer(gp, sessionUser);

        JSONObject result = new JSONObject();
        result.put("page", page);
        result.put("total", eLoanCaseMapList.getTotalPages());
        result.put("records", eLoanCaseMapList.getTotalElements());
        result.put("pageSize", pageSize);

        List<Map<String, Object>> eLoanCaseList = eLoanCaseMapList.getContent();

        JSONArray content = new JSONArray();
        for (int i = 0; i < eLoanCaseList.size(); i++)
        {
            Map<String, Object> mapObj = eLoanCaseList.get(i);

            content.add(JSONObject.toJSON(mapObj));
        }

        result.put("rows", content);

        return result.toJSONString();
    }

    @RequestMapping(value = "/{eLoanCode}")
    @ResponseBody
    public String mortgageCaseDetail(@PathVariable String eLoanCode)
    {

        if (eLoanCode == null)
        {
            throw new BusinessException("请检查参数!");
        }

        // 获取E+案件基本信息
        List<Map<String, Object>> eLoanCaseDetailMapList = getELoanCaseDetailList(queryELoanCaseDetail, eLoanCode);

        // 获取E+案件跟进内容
        List<Map<String, Object>> eLoanCaseProcessMapList = getELoanCaseProcessList(queryELoanProcess, eLoanCode);

        String caseCode = "";
        if (eLoanCaseDetailMapList != null && eLoanCaseDetailMapList.size() > 0)
        {
            caseCode = (String) eLoanCaseDetailMapList.get(0).get("caseCode");
        }

        // 获取E+案件环节
        List<Map<String, Object>> eLoanCaseTradeProcessMapList = getELoanCaseTradeProcessList(queryELoanTradeProcess, caseCode);

        return bulidELoanCaseDetailByJson(eLoanCode, eLoanCaseDetailMapList, eLoanCaseProcessMapList, eLoanCaseTradeProcessMapList);
    }

    /**
     * 构造json格式的字符串作为结果集
     * 
     * @param eLoanCode
     *            E+金融编号
     * @param eLoanCaseDetailMapList
     *            E+案件基本信息
     * @param eLoanCaseProcessMapList
     *            E+跟进信息列表
     * @param eLoanCaseTradeProcessMapList
     *            E+环节列表信息
     * @return
     */
    private String bulidELoanCaseDetailByJson(String eLoanCode, List<Map<String, Object>> eLoanCaseDetailMapList,
            List<Map<String, Object>> eLoanCaseProcessMapList, List<Map<String, Object>> eLoanCaseTradeProcessMapList)
    {

        Map<String, Object> contentMap = new HashMap<String, Object>();

        // 结果集map
        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 构造eLoan案件基本信息
        Map<String, Object> eLoanCaseDetailMap = new HashMap<String, Object>();
        if (eLoanCaseDetailMapList != null && eLoanCaseDetailMapList.size() > 0)
        {
            eLoanCaseDetailMap = eLoanCaseDetailMapList.get(0);
        }

        resultMap.put("prdType", eLoanCaseDetailMap.get("prdType") != null ? eLoanCaseDetailMap.get("prdType") : "");

        Map<String, Object> mortInfoMap = new HashMap<String, Object>();
        mortInfoMap.put("morCode", eLoanCode);
        mortInfoMap.put("mortType", eLoanCaseDetailMap.get("mortType") != null ? eLoanCaseDetailMap.get("mortType") : "");
        mortInfoMap.put("totalAmount", eLoanCaseDetailMap.get("totalAmount") != null ? eLoanCaseDetailMap.get("totalAmount") : "0");
        mortInfoMap.put("month", eLoanCaseDetailMap.get("month") != null ? eLoanCaseDetailMap.get("month") : "0");
        mortInfoMap.put("status", eLoanCaseDetailMap.get("eLoanStatus") != null ? eLoanCaseDetailMap.get("eLoanStatus") : "");
        mortInfoMap.put("loanerStatusCode", eLoanCaseDetailMap.get("loanerStatusCode") != null ? eLoanCaseDetailMap.get("loanerStatusCode") : "");
        mortInfoMap.put("flowStatusCode", eLoanCaseDetailMap.get("flowStatusCode") != null ? eLoanCaseDetailMap.get("flowStatusCode") : "");
        mortInfoMap.put("flowStatus", eLoanCaseDetailMap.get("flowStatus") != null ? eLoanCaseDetailMap.get("flowStatus") : "");
        mortInfoMap.put("type", eLoanCaseDetailMap.get("type") != null ? eLoanCaseDetailMap.get("type") : "");

        Map<String, Object> yuOperatorMap = new HashMap<String, Object>();
        yuOperatorMap.put("userId", eLoanCaseDetailMap.get("excutorId") != null ? eLoanCaseDetailMap.get("excutorId") : "");
        yuOperatorMap.put("name", eLoanCaseDetailMap.get("excutorName") != null ? eLoanCaseDetailMap.get("excutorName") : "");
        yuOperatorMap.put("mobile", eLoanCaseDetailMap.get("excutorMobile") != null ? eLoanCaseDetailMap.get("excutorMobile") : "");
        yuOperatorMap.put("org", eLoanCaseDetailMap.get("excutorOrg") != null ? eLoanCaseDetailMap.get("excutorOrg") : "");

        Map<String, Object> custInfoMap = new HashMap<String, Object>();
        custInfoMap.put("name", eLoanCaseDetailMap.get("customerName") != null ? eLoanCaseDetailMap.get("customerName") : "");
        custInfoMap.put("mobile", eLoanCaseDetailMap.get("customerMobile") != null ? eLoanCaseDetailMap.get("customerMobile") : "");
        // 由于T_TO_ELOAN_CASE没有USER_ID这个字段,所以无法获取该字段信息,所以先暂时设置该字段为空字符串
        custInfoMap.put("custID", "");

        Map<String, Object> tradeInfoMap = new HashMap<String, Object>();
        tradeInfoMap.put("caseCode", eLoanCaseDetailMap.get("caseCode") != null ? eLoanCaseDetailMap.get("caseCode") : "");
        tradeInfoMap.put("addr", eLoanCaseDetailMap.get("propertyAddress") != null ? eLoanCaseDetailMap.get("propertyAddress") : "");
        tradeInfoMap.put("status", eLoanCaseDetailMap.get("caseStatus") != null ? eLoanCaseDetailMap.get("caseStatus") : "");

        resultMap.put("mortInfo", mortInfoMap);
        resultMap.put("yuOperator", yuOperatorMap);
        resultMap.put("custInfo", custInfoMap);
        resultMap.put("tradeInfo", tradeInfoMap);

        if (eLoanCaseProcessMapList == null || eLoanCaseProcessMapList.size() == 0)
        {
            eLoanCaseProcessMapList = new ArrayList<Map<String, Object>>();
        }

        if (eLoanCaseTradeProcessMapList == null || eLoanCaseTradeProcessMapList.size() == 0)
        {
            eLoanCaseTradeProcessMapList = new ArrayList<Map<String, Object>>();
        }

        resultMap.put("mortProcess", eLoanCaseProcessMapList);
        resultMap.put("tradeProcess", eLoanCaseTradeProcessMapList);

        String str = JSONObject.toJSONString(resultMap);

        return str;
    }

    /**
     * 获取eLoan案件基本信息
     * 
     * @param queryId
     *            快速查询Id
     * @param eLoanCode
     *            E+金融编号
     * @return E+案件基本信息
     */
    private List<Map<String, Object>> getELoanCaseDetailList(String queryId, String eLoanCode)
    {
        JQGridParam gp = new JQGridParam();
        gp.setQueryId(queryId);
        gp.setPagination(false);
        gp.put("eLoanCode", eLoanCode);

        Page<Map<String, Object>> eLoanCaseDetailMapList = quickGridService.findPageForSqlServer(gp);

        if (eLoanCaseDetailMapList.getContent() != null && eLoanCaseDetailMapList.getContent().size() > 0)
        {

            return eLoanCaseDetailMapList.getContent();
        }

        return null;
    }

    /**
     * 获取E+案件环节信息
     * 
     * @param queryId
     *            快速查询Id
     * @param caseCode
     *            案件编号
     * @return E+案件环节列表
     */
    private List<Map<String, Object>> getELoanCaseTradeProcessList(String queryId, String caseCode)
    {
        JQGridParam gp = new JQGridParam();
        gp.setQueryId(queryId);
        gp.setPagination(false);
        gp.put("caseCode", caseCode);

        Page<Map<String, Object>> eLoanCaseTradeProcessListMapList = quickGridService.findPageForSqlServer(gp);

        if (eLoanCaseTradeProcessListMapList.getContent() != null && eLoanCaseTradeProcessListMapList.getContent().size() > 0)
        {

            return eLoanCaseTradeProcessListMapList.getContent();
        }

        return null;
    }

    /**
     * 获取E+案件跟进内容
     * 
     * @param queryId
     *            快速查询Id
     * @param eLoanCode
     *            E+金融编号
     * @return E+案件跟进内容列表
     */
    private List<Map<String, Object>> getELoanCaseProcessList(String queryId, String eLoanCode)
    {
        JQGridParam gp = new JQGridParam();
        gp.setQueryId(queryId);
        gp.setPagination(false);
        gp.put("eLoanCode", eLoanCode);

        Page<Map<String, Object>> eLoanCaseProcessMapList = quickGridService.findPageForSqlServer(gp);

        if (eLoanCaseProcessMapList.getContent() != null && eLoanCaseProcessMapList.getContent().size() > 0)
        {

            return eLoanCaseProcessMapList.getContent();
        }

        return null;
    }

}
