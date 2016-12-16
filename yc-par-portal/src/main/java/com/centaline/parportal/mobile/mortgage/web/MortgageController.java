package com.centaline.parportal.mobile.mortgage.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.alibaba.fastjson.JSONObject;
import com.centaline.parportal.mobile.login.vo.MobileHolder;

@Controller
@RequestMapping({ "/mobile/case", "/case" })
public class MortgageController {

    @Resource(name = "quickGridService")
    private QuickGridService    quickGridService;

    private final Logger        logger            = LoggerFactory.getLogger(this.getClass());

    private final static String queryDetail       = "queryMortgageCaseDetail";
    private final static String queryMortProcess  = "queryMortProcess";
    private final static String queryTradeProcess = "queryTradeProcess";

    @RequestMapping(value = "/{mortCode}")
    @ResponseBody
    public String mortgageCaseDetail(@PathVariable String mortCode) {

        List<Map<String, Object>> respDetail = mortgageCaseInfoQuery(queryDetail, mortCode, 1, 10);

        List<Map<String, Object>> respMortProc = mortgageCaseInfoQuery(queryMortProcess, mortCode,
            1, 10);
        List<Map<String, Object>> respTradeProc = mortgageCaseInfoQuery(queryTradeProcess, mortCode,
            1, 10);

        Map<String, Object> result = new HashMap();
        this.parseMortDetail(result, respDetail.get(0));
        result.put("mortProcess", respMortProc);
        result.put("tradeProcess", respTradeProc);

        String str = JSONObject.toJSONString(result);

        return str;
    }

    private List<Map<String, Object>> mortgageCaseInfoQuery(String queryId, String caseCode,
                                                            Integer page, Integer rows) {
        try {
            JQGridParam gp = new JQGridParam();
            gp.put("caseCode", caseCode);
            gp.setPage(page);
            gp.setRows(rows);
            gp.setQueryId(queryId);
            Page<Map<String, Object>> result = quickGridService.findPageForSqlServer(gp,
                MobileHolder.getMobileUser());

            if (null != result && null != result.getContent() && result.getContent().size() > 0)
                return result.getContent();

        } catch (Exception e) {
            logger.info("quick query for mobile mortgate detail failed, queryID: #" + queryId
                        + "#, caseCode: #" + caseCode + "#");
        }
        return new ArrayList();
    }

    /**
     * 将查询结果根据名称封装出层次
     * 
     * @param result
     * @param detail
     * @return
     */
    private Map<String, Object> parseMortDetail(Map<String, Object> result,
                                                Map<String, Object> detail) {
        detail.forEach((k, v) -> {
            String[] p = k.split("_");
            Map<String, Object> parent = result;
            for (int i = 0; i < p.length - 1; i++) {
                if (!parent.containsKey(p[i]))
                    parent.put(p[i], new HashMap<String, Object>());
                parent = (Map<String, Object>) parent.get(p[i]);
            }
            parent.put(p[p.length - 1], v);
        });
        return result;
    }

}
