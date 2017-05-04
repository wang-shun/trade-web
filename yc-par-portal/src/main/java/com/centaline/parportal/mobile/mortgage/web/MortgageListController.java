/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.centaline.parportal.mobile.mortgage.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuerysParseService;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author sstonehu
 * @version $Id: MortgageListController.java, v 0.1 2016年12月12日 上午3:45:44
 *          sstonehu Exp $
 */
@Controller
@RequestMapping(value = "/mortgageList")
public class MortgageListController
{

    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(MortgageListController.class);

    @Resource(name = "quickGridService")
    private QuickGridService quickGridService;

    @Autowired
    private QuerysParseService querysParseService;

    @Autowired
    private UamSessionService uamSessionService;

    @RequestMapping(value = "list")
    @ResponseBody
    public String caseList(Integer page, Integer pageSize, String sidx, String sord, String q_text)
    {
        // 设置快速查询id和分页参数
        JQGridParam gp = new JQGridParam();
        gp.setPagination(true);
        gp.setPage(page);
        gp.setRows(pageSize);
        gp.setQueryId("findToMortgage4Par");
        gp.setSidx(sidx);
        gp.setSord(sord);
        Map<String, Object> paramter = new HashMap<String, Object>();

        // 设置用户id
        SessionUser sessionUser = uamSessionService.getSessionUser();
        paramter.put("userid", sessionUser.getId());
        // paramter.put("userid", "ff80808158bd58c10158bda37f100020");
        paramter.put("flag", "noAccept");

        // 设置用户查询条件
        if (q_text != null)
        {
            String formatCondtion = q_text.trim();

            if (!"".equals(formatCondtion))
            {
                paramter.put("q_text", formatCondtion);
            }
        }

        gp.putAll(paramter);
        querysParseService.reloadFile();

        // 查询未接单按揭列表信息
        Page<Map<String, Object>> noAcceptMortgageMapList = quickGridService.findPageForSqlServer(gp, sessionUser);

        // 查询除未接单状态之外的按揭列表信息
        paramter.put("flag", "exceptNoAccept");
        gp.putAll(paramter);
        Page<Map<String, Object>> mortgageMapList = quickGridService.findPageForSqlServer(gp, sessionUser);

        // 未接单记录数
        long noAcceptCount = noAcceptMortgageMapList.getTotalElements();
        // 除未接单状态之外的按揭记录数
        long mortgageCount = mortgageMapList.getTotalElements();
        // 总记录数
        long totalElements = noAcceptCount + mortgageCount;
        // 总页数
        int totalPage = (int) (totalElements + pageSize - 1) / pageSize;

        JSONObject result = new JSONObject();
        result.put("page", page);
        result.put("total", totalPage);
        result.put("records", totalElements);
        result.put("pageSize", pageSize);

        List<Map<String, Object>> noAcceptMortgageList = noAcceptMortgageMapList.getContent();

        List<Map<String, Object>> mortgageList = mortgageMapList.getContent();

        JSONArray content = new JSONArray();
        for (int i = 0; i < noAcceptMortgageList.size(); i++)
        {
            Map<String, Object> mapObj = noAcceptMortgageList.get(i);

            content.add(JSONObject.toJSON(mapObj));
        }

        for (int i = 0; i < mortgageList.size(); i++)
        {
            Map<String, Object> mapObj = mortgageList.get(i);

            content.add(JSONObject.toJSON(mapObj));
        }

        result.put("rows", content);

        return result.toJSONString();
    }
}
