package com.centaline.parportal.mobile.my.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.service.TsTeamPropertyService;
import com.centaline.trans.workspace.entity.CacheGridParam;
import com.centaline.trans.workspace.entity.WorkSpace;
import com.centaline.trans.workspace.service.WorkSpaceService;

/**
 * 手机端个人工作台接口
 * Created by gongjd on 2017/6/26.
 */
@Controller
@RequestMapping(value = "/workspace/dashboard")
public class DashboardController {

    @Autowired
    private UamSessionService uamSessionService;

    @Autowired
    private TsTeamPropertyService teamPropertyService;
    
    @Autowired
    private QuickGridService quickGridService;
    
    @Autowired
    private WorkSpaceService workSpaceService;
    
    @Autowired
    private UamUserOrgService uamUserOrgService;

    /**
     *贷款流失
     * @return json
     */
    @RequestMapping(value="getMortgageStatInfo")
    @ResponseBody
    public Object getMortgageLostInfo(){
        SessionUser user = uamSessionService.getSessionUser();
        String jobCode = user.getServiceJobCode();
        
        //准备参数
        String serachId = user.getId();
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String mo = month > 9?"0"+month:String.valueOf(month);
        //获取工作台对象
        WorkSpace work = buildWorkSpaceBean(serachId, mo);
        //获取查询对象
        JQGridParam gp = new CacheGridParam();
		gp.setPagination(false);
		if(work!=null){			
			gp.put("mo", work.getMo());
			gp.put("org_Id", work.getOrgId());			
			gp.put("orgs", work.getOrgs());				
			gp.put("user_Id", work.getUserId());
		}
        // 评估费转化率
        //Double efConverRt = workSpaceService.staEvaFeeCount(work);
        //转化率
        //String efConvRate = staEvaFeeQuery(gp);
        //e+转化率
        //String convRate = convRateQuery(gp);
        
        // 判断组织是否为后台的
        TsTeamProperty tp = teamPropertyService.findTeamPropertyByTeamCode(user.getServiceDepCode());
        boolean isBackTeam = false;
        if (tp != null)
        {
            isBackTeam = "yu_back".equals(tp.getTeamProperty());
        }

        JSONObject jsonList = new JSONObject();
        JSONObject json = new JSONObject();
        if(!isBackTeam && TransJobs.TJYGW.getCode().equals(jobCode)){
            //交易顾问（前台）
            //贷款流失率，评估费，转化率，金融完成单数（数字）
            json.put("loanLostRate", "0.03");
            json.put("efConverRt", "0.02"/*efConverRt*/);
            json.put("efConvRate", "0.01"/*efConvRate*/);
            json.put("ComLoanNum", 100);
            json.put("loanLostAlert", true);
            json.put("convRateAlert", false);
        }else if((!isBackTeam && TransJobs.TJYZG.getCode().equals(jobCode)) || (TransJobs.TZJ.getCode().equals(jobCode))){
            //总监及以上，主管（前台）
            //贷款流失率，评估费，转化率，金融转化率
            json.put("loanLostRate", "0.03");
            json.put("efConverRt", "0.02"/*efConverRt*/);
            json.put("efConvRate", "0.01"/*efConvRate*/);
            json.put("convRate", "0.04"/*convRate*/);
            json.put("loanLostAlert", false);
            json.put("convRateAlert", true);
        }else{
            //其他
            //不显示
        }
        
        jsonList.put("rateInfo", json);
        jsonList.put("lightInfo", getTrafficLightInfo());
        jsonList.put("mortgageInfo", getMortgageInfo());
        
        return jsonList;
    }
    
/*    @RequestMapping(value="getTrafficLightInfo")
    @ResponseBody*/
    public Object getTrafficLightInfo(){
        // 红 黄 商贷流失预警案件数灯
    	WorkSpace wk = buildWorkSpaceBean(null, null);
    	//int redLight = redLightCountQuery(wk);
    	//int yeLight = yeLightCountQuery(wk);
    	
    	JSONObject json = new JSONObject();
    	json.put("redLight", 300/*redLight*/);
    	json.put("yeLight", 2/*yeLight*/);
    	
    	return json;
    }
    
    public Object getMortgageInfo(){
    	JSONObject json = new JSONObject();
    	JSONObject data = new JSONObject();
    	
    	data.put("goalPerf", 30000000.00);
    	data.put("shareAmount", 600000.00);
    	data.put("paidAmount", 247.00);
    	
    	json.put("data", data);
    	json.put("ServiceFee",10000);
    	json.put("AssessmentFee", 1001200.00);
    	json.put("FinanceFee", 10000);
    	json.put("CardFee", 10000);
    	json.put("MortgageFee", 10000);
    	
    	return json;
    }
    
    // 查询E+转化率
    private String convRateQuery(JQGridParam gp)
    {
        String convRate = null;
        gp.setQueryId("personalWorkbenchConvRateQuery");
        Page<Map<String, Object>> convRateResult = quickGridService.findPageForSqlServer(gp);
        if (convRateResult != null && convRateResult.getContent() != null && convRateResult.getContent().size() > 0)
        {
            if (convRateResult.getContent().get(0).get("convRate") != null)
            {
                convRate = convRateResult.getContent().get(0).get("convRate").toString();
            }
        }
        return convRate;
    }
    
    // 查询放款金额和转化率
    private String staEvaFeeQuery(JQGridParam gp)
    {
        String efConvRate = null;
        gp.setQueryId("personalWorkbenchStaEvaFeeQuery");
        Page<Map<String, Object>> staEvaFeeResult = quickGridService.findPageForSqlServer(gp);
        System.out.println("staEvaFeeResult=====================" + staEvaFeeResult.toString());
        if (staEvaFeeResult != null && staEvaFeeResult.getContent() != null && staEvaFeeResult.getContent().size() > 0)
        {
            if (null != staEvaFeeResult.getContent().get(0).get("efConvRate"))
            {
                efConvRate = staEvaFeeResult.getContent().get(0).get("efConvRate").toString();
            }

        }
        return efConvRate;
    }
    
    // 查找红灯预警数量
    private int redLightCountQuery(WorkSpace wk)
    {
        JQGridParam gp = new CacheGridParam();

        gp.setPagination(false);
        if (wk != null)
        {
            gp.put("user_Id", wk.getUserId());
            gp.put("org_Id", wk.getOrgId());
            gp.put("orgs", wk.getOrgs());
            gp.put("color", "0");
        }
        gp.setQueryId("personalWorkbenchRedLightCountQuery");
        Page<Map<String, Object>> redLightResult = quickGridService.findPageForSqlServer(gp);
        System.out.println("redLightResult====" + redLightResult);
        int redLight = 0;
        if (redLightResult != null && redLightResult.getContent() != null && redLightResult.getContent().size() > 0)
        {
            redLight = (int) redLightResult.getContent().get(0).get("redLight");
        }
        return redLight;
    }

    // 查找黄灯预警数量
    private int yeLightCountQuery(WorkSpace wk)
    {
        JQGridParam gp = new CacheGridParam();
        gp.setPagination(false);
        if (wk != null)
        {
            gp.put("user_Id", wk.getUserId());
            gp.put("org_Id", wk.getOrgId());
            gp.put("orgs", wk.getOrgs());
            gp.put("color", "1");
        }

        gp.setQueryId("personalWorkbenchYeLightCountQuery");
        Page<Map<String, Object>> yeLightResult = quickGridService.findPageForSqlServer(gp);
        int yeLight = 0;
        if (yeLightResult != null && yeLightResult.getContent() != null && yeLightResult.getContent().size() > 0)
        {
            yeLight = (int) yeLightResult.getContent().get(0).get("yeLight");
        }

        return yeLight;
    }
    
    private WorkSpace buildWorkSpaceBean(String serachId, String mo)
    {
        WorkSpace w = new WorkSpace();
        SessionUser user = uamSessionService.getSessionUser();
        List<String> orgs = null;
        String org = null;
        String userId = null;
        if (TransJobs.TZJL.getCode().equals(user.getServiceJobCode()))
        {// 如果是总经理
            if (!StringUtils.isBlank(serachId))
            {
                List<Org> orgList = uamUserOrgService.getOrgByParentId(serachId);
                orgs = orgListToListStr(orgList);
                orgs.add(user.getServiceDepId());
            }
        }
        else if (TransJobs.TZJ.getCode().equals(user.getServiceJobCode()))
        { // 如果是总监
            if (!StringUtils.isBlank(serachId))
            {
                org = serachId;
            }
            else
            {
                List<Org> orgList = uamUserOrgService.getOrgByParentId(user.getServiceDepId());
                orgs = orgListToListStr(orgList);
                orgs.add(user.getServiceDepId());
            }
        }
        else if (TransJobs.TSJYZG.getCode().equals(user.getServiceJobCode()) || TransJobs.TJYZG.getCode().equals(user.getServiceJobCode()))
        {// 如果是交易主管
            if (!StringUtils.isBlank(serachId))
            {
                userId = serachId;
            }
            else
            {
                org = user.getServiceDepId();
            }
        }
        else
        {
            userId = user.getId();
        }
        w.setMo(mo);
        w.setOrgId(org);
        w.setOrgs(orgs);
        w.setUserId(userId);
        return w;
    }
    
    private List<String> orgListToListStr(List<Org> orgs)
    {
        if (orgs == null || orgs.isEmpty())
            return null;
        List<String> orgStrs = new ArrayList<>();
        for (Org org : orgs)
        {
            orgStrs.add(org.getId());
        }
        return orgStrs;
    }
}
