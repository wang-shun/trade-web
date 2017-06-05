package com.centaline.trans.cases.service.impl;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.service.TransplanService;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.entity.TsTransPlanHistory;
import com.centaline.trans.transplan.entity.TtsTransPlanHistoryBatch;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by caoyuan7 on 2017/4/28.
 */
@Service
public class TransplanServiceImpl implements TransplanService {


    private Logger logger = Logger.getLogger(TransplanService.class);

    @Autowired(required = true)
    TransplanServiceFacade transplanServiceFacade;
    @Autowired
    UamBasedataService uamBasedataService;
    @Autowired(required = true)
    UamSessionService uamSessionService;
    @Autowired(required = true)
    TransplanService transplanService;

    @Override
    public AjaxResponse saveTransPlans(JSONArray fileListArray) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        SessionUser user = uamSessionService.getSessionUser();
        boolean isDeal = true;//是否处理
        TtsTransPlanHistoryBatch ttpb = new TtsTransPlanHistoryBatch();
        try{
           for (Object object : fileListArray) {
               JSONObject planItems = (JSONObject) object;
               if("true".equals(planItems.getString("isChanges"))){
                   if(planItems.getString("estIds")!=null){
                       Long e = Long.valueOf(planItems.getString("estDates"));
                       ToTransPlan oldPlan = transplanServiceFacade.selectByPrimaryKey(Long.parseLong(planItems.getString("estIds")));
                       TsTransPlanHistory hisRecord = new TsTransPlanHistory();
                       ToTransPlan record = new ToTransPlan();

                       //add by zhoujp添加一条交易计划变更历史批次信息
                       if(isDeal){
                           ttpb.setCaseCode(oldPlan.getCaseCode());
                           ttpb.setOldEstPartTime(oldPlan.getEstPartTime());
                           ttpb.setNewEstPartTime(new Date(e.longValue()));
                           ttpb.setChangeReason(planItems.getString("whyChanges"));
                           ttpb.setPartCode(oldPlan.getPartCode());
                           ttpb.setOperateFlag("0");//手工
                           transplanServiceFacade.insertTtsTransPlanHistoryBatch(ttpb);
                           isDeal = false;
                       }
                       hisRecord.setBatchId(ttpb.getPkid());
                       // 插入历史记录
                       hisRecord.setCaseCode(oldPlan.getCaseCode());
                       hisRecord.setPartCode(oldPlan.getPartCode());
                       hisRecord.setOldEstPartTime(oldPlan.getEstPartTime());
                       hisRecord.setNewEstPartTime(new Date(e.longValue()));
                       hisRecord.setChangeTime(new Date());
                       hisRecord.setChangerId(user.getId());
                       hisRecord.setChangeReason(planItems.getString("whyChanges"));

                       record.setPkid(Long.parseLong(planItems.getString("estIds")));
                       record.setEstPartTime(new Date(e.longValue()));

                       if (transplanServiceFacade.insertTsTransPlanHistorySelective(hisRecord) == 0){
                           throw new BusinessException("交易计划历史记录更新失败！");
                       }
                       if (transplanServiceFacade.updateByPrimaryKeySelective(record) == 0){
                           throw new BusinessException("交易计划更新失败！");
                       }
                   }
               }

           }
            ajaxResponse.setSuccess(true);
            ajaxResponse.setMessage("交易计划更新成功");
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new BusinessException("交易计划更新失败！");
       }
        return ajaxResponse;
    }

}
