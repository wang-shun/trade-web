package com.centaline.trans.cases.service.impl;

import com.aist.common.exception.BusinessException;
import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.druid.util.StringUtils;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.CaseHandlerService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.eloan.service.ToEloanCaseService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.VariableService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.service.TlTaskReassigntLogService;
import com.centaline.trans.workspace.entity.CacheGridParam;
import javassist.bytecode.stackmap.BasicBlock;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by caoyuan7 on 2017/5/10.
 */
@Service
public class CaseHandlerServiceImpl implements CaseHandlerService {

    private Logger logger = Logger.getLogger(CaseHandlerServiceImpl.class);

    @Autowired(required = true)
    UamSessionService uamSessionService;
    @Autowired(required = true)
    ToCaseService toCaseService;
    @Autowired(required = true)
    ToWorkFlowService toWorkFlowService;
    @Autowired(required = true)
    UamUserOrgService uamUserOrgService;
    @Autowired(required = true)
    TgServItemAndProcessorService tgServItemAndProcessorService;
    @Autowired(required = true)
    WorkFlowManager workFlowManager;
    @Autowired
    private TlTaskReassigntLogService taskReassingtLogService;
    @Autowired
    private QuickGridService quickGridService;
    @Autowired
    private ToEloanCaseService toEloanCaseService;

    @Override
    public void BelongAndTransferList(HttpServletRequest request) {
        SessionUser user = uamSessionService.getSessionUser();
        if("Manager".equals(user.getServiceJobCode())){
            request.setAttribute("orgId", user.getServiceDepId());
            request.setAttribute("selectJobCode", "consultant");
        }
    }

    @Override
    public void handlerDetailList(HttpServletRequest request, String userId,String detailCode) {
        SessionUser user = uamSessionService.getSessionUser();
        if("Manager".equals(user.getServiceJobCode())){
            request.setAttribute("userId",userId);
            request.setAttribute("detailCode", detailCode);
            request.setAttribute("orgId", user.getServiceDepId());
        }

    }

    /**
     * 对责任人进行更改
     * @author caoy
     * @param caseCode
     * @param userId
     * @param detailCode 识别 提交变更的项，每个变更项目的业务逻辑不同，所以根据此字段来区分
     * @return
     */

    @Override
    public AjaxResponse changeLeadingPro(String[] caseCode, String userId,String detailCode){
        AjaxResponse ajaxResponse = new AjaxResponse();
        String currentCaseCode="";
       try{
           for(int i =0;i<caseCode.length;i++){
               if(!StringUtils.isEmpty(caseCode[i])){
                   currentCaseCode = caseCode[i];
                   if("eloan".equals(detailCode)){//如果是变更E+贷款归属人
                       if(!changeLeadingEloanDo(caseCode[i],userId,detailCode)){//如果更新责任人失败，将失败的caseCode放入集合等待返回
                           throw new BusinessException(currentCaseCode);
                       }
                   }
                   if("processor".equals(detailCode)||"comMort".equals(detailCode)||"prfMort".equals(detailCode)){//如果是变更服务项归属人
                       if(!changeLeadingProDo(caseCode[i],userId,detailCode)){//如果更新责任人失败，将失败的caseCode放入集合等待返回
                           throw new BusinessException(currentCaseCode);
                       }
                   }

               }
           }
           ajaxResponse.setSuccess(true);
       }catch (BusinessException e){
           logger.error(e.getMessage());
           throw new BusinessException(currentCaseCode);
       }

        return ajaxResponse;
    }

    /**
     * 更改责任人的实现方法
     * @author caoy
     * @param caseCode
     * @param userId
     * @return
     */
    public Boolean changeLeadingProDo(String caseCode, String userId,String detailCode){
        try{
            ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);//获得案件详细信息
            ToWorkFlow inWorkFlow = new ToWorkFlow();
            inWorkFlow.setBusinessKey("operation_process");
            inWorkFlow.setCaseCode(caseCode);
            ToWorkFlow toWorkFlow = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(inWorkFlow);//获得案件主流程

            User u = uamUserOrgService.getUserById(toCase.getLeadingProcessId());//案件旧的负责人
            User u_ = uamUserOrgService.getUserById(userId);//本次要更新的负责人

            if(u_==null){//如果本次新更新的用户不存在返回false
                return false;
            }

            toCase.setLeadingProcessId(userId);
            toCaseService.updateByPrimaryKey(toCase);//更新案件的负责人,t_to_case的LeadingProcessId

            TgServItemAndProcessor record = new TgServItemAndProcessor();
            record.setPreProcessorId(u.getId());
            record.setPreOrgId(u.getOrgId());
            record.setProcessorId(u_.getId());
            record.setOrgId(u_.getOrgId());
            record.setCaseCode(caseCode);
            record.setPreDetailCode(detailCode);
            tgServItemAndProcessorService.updateByCaseCode(record);//更新经办人表(T_TG_SERV_ITEM_AND_PROCESSOR)

            if (!StringUtils.isEmpty(toWorkFlow.getInstCode())) { // 更新流程引擎
                String variableName = "caseOwner";
                RestVariable restVariable = new RestVariable();
                restVariable.setType("string");
                restVariable.setValue(u_.getUsername());

                JQGridParam gp =  new CacheGridParam();
                gp.setPagination(false);
                gp.setQueryId("queryVarinsts");
                gp.put("variableName", variableName);
                gp.put("instCode",toWorkFlow.getInstCode());

                Page<Map<String, Object>> varinsts = quickGridService.findPageForSqlServer(gp);
               if(varinsts!=null){
                   if(varinsts.getContent().size()>0){
                       workFlowManager.setVariableByProcessInsId(toWorkFlow.getInstCode(), variableName, restVariable);
                   }
               }

                TaskQuery tq = new TaskQuery();
                tq.setProcessInstanceId(toWorkFlow.getInstCode());
                tq.setFinished(false);
                tq.setAssignee(u.getUsername());
                List<TaskVo> tasks = workFlowManager.listTasks(tq).getData();
                updateWorkflow(userId, tasks, caseCode);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        return true;
    }
    public Boolean changeLeadingEloanDo(String caseCode, String userId,String detailCode){
        try{
            User u_ = uamUserOrgService.getUserById(userId);//本次要更新的负责人
            if(u_==null){//如果本次新更新的归属人不存在返回false
                return false;
            }
            ToEloanCase tEloanCase = new ToEloanCase();
            tEloanCase.setCaseCode(caseCode);
            tEloanCase.setExcutorId(userId);
            List<ToEloanCase> toEloanCaseList = toEloanCaseService.getToEloanCaseListByProperty(tEloanCase);

            ToWorkFlow inWorkFlow = new ToWorkFlow();
            inWorkFlow.setBusinessKey("EloanProcess");
            inWorkFlow.setCaseCode(caseCode);
            List<ToWorkFlow> toWorkFlowList = toWorkFlowService.queryToWorkFlowByCaseCodeBusKeys(inWorkFlow);//获得案件E+贷款流程


            if(toEloanCaseList.size()==0||toWorkFlowList.size()==0){//如果更新的内容为空，则返回false
                return false;
            }
            for(ToEloanCase toEloanCase : toEloanCaseList){
                toEloanCase.setExcutorId(u_.getId());
                toEloanCase.setExcutorTeam(u_.getOrgId());

               // toEloanCaseService.updateEloanApply()
            }
/*
            if (!StringUtils.isEmpty(toWorkFlow.getInstCode())) { // 更新流程引擎
                String variableName = "caseOwner";
                RestVariable restVariable = new RestVariable();
                restVariable.setType("string");
                restVariable.setValue(u_.getUsername());

                JQGridParam gp =  new CacheGridParam();
                gp.setPagination(false);
                gp.setQueryId("queryVarinsts");
                gp.put("variableName", variableName);
                gp.put("instCode",toWorkFlow.getInstCode());

                Page<Map<String, Object>> varinsts = quickGridService.findPageForSqlServer(gp);
                if(varinsts!=null){
                    if((Integer) varinsts.getContent().get(0).get("VARINST_COUNT")>0){
                        workFlowManager.setVariableByProcessInsId(toWorkFlow.getInstCode(), variableName, restVariable);
                    }
                }

                TaskQuery tq = new TaskQuery();
                tq.setProcessInstanceId(toWorkFlow.getInstCode());
                tq.setFinished(false);
                tq.setAssignee(u.getUsername());
                List<TaskVo> tasks = workFlowManager.listTasks(tq).getData();
                updateWorkflow(userId, tasks, caseCode);
            }*/
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        return true;
    }
    /**
     * 更新工作流的正在执行任务的状态
     * @author caoy
     * @param userId
     * @param tasks
     * @param caseCode
     */
    public void updateWorkflow(String userId, List<TaskVo> tasks,String caseCode) {
        if (tasks != null && !tasks.isEmpty()) {
            User u = uamUserOrgService.getUserById(userId);
            for (TaskVo taskVo : tasks) {
                taskReassingtLogService.record(taskVo, u.getUsername(), caseCode);
                taskVo.setAssignee(u.getUsername());
                workFlowManager.updateTask(taskVo);
            }
        }

    }

}
