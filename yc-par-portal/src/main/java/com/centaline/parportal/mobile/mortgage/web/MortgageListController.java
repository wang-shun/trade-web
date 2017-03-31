/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.centaline.parportal.mobile.mortgage.web;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuerysParseService;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.utils.Pages2JSONMoblie;

/**
 * 
 * @author sstonehu
 * @version $Id: MortgageListController.java, v 0.1 2016年12月12日 上午3:45:44 sstonehu Exp $
 */
@RestController
@RequestMapping(value = "/mobile/case")
public class MortgageListController {

    private static Logger      logger = LoggerFactory.getLogger(MortgageListController.class);

    @Resource(name = "quickGridService")
    private QuickGridService   quickGridService;

	@Value("${agent.img.url}")
	private String imgUrl;

    @Autowired
    private UamSessionService sessionService;
    
    private static String PRDTYPE_EPLUS = "EPLUS";
    
    @RequestMapping(value = "list")
    @ResponseBody
    public String caseList(HttpServletRequest request, HttpServletResponse response, Integer page,
                           Integer pageSize, String sidx, String sord, String userid,
                           String q_text,String prdType) {
        
    	SessionUser sessionUser = sessionService.getSessionUser();
    	if(PRDTYPE_EPLUS.equals(prdType)) {
    		Page<Map<String, Object>> result =  findEloanCaseList(page,pageSize,q_text,sessionUser);
    		return Pages2JSONMoblie.pages2JSONStringMoblie(result);
    	}
        long millisecond = System.currentTimeMillis();
        logger.info("Start:caseList 房源列表数据加载开始 ：" + millisecond + "/毫秒");
        JQGridParam gp = new JQGridParam();
        gp.setPagination(true);
        gp.setPage(page);
        gp.setRows(pageSize);
        gp.setQueryId("findToMortgage4Par");
        gp.setSidx(sidx);
        gp.setSord(sord);
        Map<String, Object> paramter = new HashMap<String, Object>();


        paramter.put("userid", sessionUser.getId());

        if (StringUtils.isNotBlank(q_text) && StringUtils.isNotEmpty(q_text)) {
            paramter.put("q_text", q_text);
        }

        gp.putAll(paramter);
        Page<Map<String, Object>> returnPage = quickGridService.findPageForSqlServer(gp,
        		sessionUser);

        JSONObject result = new JSONObject();
        result.put("page", page);
        result.put("total", returnPage.getTotalPages());
        result.put("records", returnPage.getTotalElements());
        result.put("pageSize", pageSize);
        List<Map<String, Object>> contentList = returnPage.getContent();

        JSONArray content = new JSONArray();
        for (int i = 0; i < contentList.size(); i++) {
            Map<String, Object> mapObj = contentList.get(i);

            content.add(JSONObject.toJSON(mapObj));
        }
        result.put("rows", content);

        return result.toJSONString();
    }

	private Page<Map<String, Object>> findEloanCaseList(Integer page, Integer pageSize, String q_text,SessionUser sessionUser) {
		
		JQGridParam gp = new JQGridParam();
        gp.setPagination(true);
        gp.setPage(page);
        gp.setRows(pageSize);
        gp.setQueryId("EPlusLoanList");
        Map<String, Object> paramter = gp.getParamtMap();

        if (StringUtils.isNotBlank(q_text) && StringUtils.isNotEmpty(q_text)) {
            paramter.put("q_text", q_text);
        }

        Page<Map<String, Object>> returnPage = quickGridService.findPageForSqlServer(gp,
        		sessionUser);
        List<Map<String, Object>> list = returnPage.getContent();
        if(null == list) {
        	return returnPage;
        }
        for (Map<String, Object> map : list) {
        	Object agentCodeObj = map.get("employeeCode");
    		String avatar = null == agentCodeObj ? ""
    				: MessageFormat.format(imgUrl, String.valueOf(agentCodeObj)) + ".jpg";
    		map.put("avatar", avatar);
    		map.remove("employeeCode");
		}

		return returnPage;
	}

}
