package com.centaline.trans.mortgage.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.bizwarn.service.BizWarnInfoService;
import com.centaline.trans.cases.entity.Result2;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.repository.ToCaseCommentMapper;
import com.centaline.trans.comment.service.ToCaseCommentService;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.enums.CommSubjectEnum;
import com.centaline.trans.common.enums.LoanerStatusEnum;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.RoleTypeEnum;
import com.centaline.trans.common.enums.TmpBankStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.eloan.entity.ToSelfAppInfo;
import com.centaline.trans.eloan.service.ToSelfAppInfoService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.eval.entity.ToEval;
import com.centaline.trans.eval.repository.ToEvalMapper;
import com.centaline.trans.mgr.entity.ToSupDocu;
import com.centaline.trans.mgr.service.ToSupDocuService;
import com.centaline.trans.mortgage.entity.ToMortLoaner;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.repository.ToMortLoanerMapper;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.mortgage.service.LoanerProcessService;
import com.centaline.trans.mortgage.service.ToMortLoanerService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.mortgage.vo.MortgageVo;
import com.centaline.trans.performance.service.ReceivablePerfService;
import com.centaline.trans.performance.vo.ReceivablePerfVo;
import com.centaline.trans.stuff.service.StuffService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.service.ToMortgageTosaveService;
import com.centaline.trans.task.service.UnlocatedTaskService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.MortgageToSaveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.utils.DateUtil;
import com.centaline.trans.wdcase.entity.TpdCommSubsDetals;
import com.centaline.trans.wdcase.service.CommSubsService;
import com.centaline.trans.wdcase.vo.CommSubsVo;

@Service
@Transactional
public class ToMortgageServiceImpl implements ToMortgageService
{

    @Autowired
    private ToMortgageMapper toMortgageMapper;
    @Autowired
    private ToCaseCommentMapper toCaseCommentMapper;
    @Autowired
    private ToSupDocuService toSupDocuService;
    @Autowired
    private TgGuestInfoService tgGuestInfoService;
    @Autowired(required = true)
    private ToCaseService toCaseService;
    @Autowired
    private WorkFlowManager workFlowManager;
    @Autowired
    MessageService messageService;
    @Autowired
    private ToWorkFlowService toWorkFlowService;
    @Autowired
    private UnlocatedTaskService unlocatedTaskService;
    @Autowired
    private ToApproveRecordService toApproveRecordService;
    @Autowired(required = true)
    private UamUserOrgService uamUserOrgService;
    @Autowired
    private PropertyUtilsServiceImpl propertyUtilsService;
    @Autowired(required = true)
    UamSessionService uamSessionService;
    @Autowired
    private UamTemplateService uamTemplateService;
    @Qualifier("uamMessageServiceClient")
    @Autowired
    private UamMessageService uamMessageService;
    @Autowired
    private LoanerProcessService loanerProcessService;
    @Autowired
    private ToCaseCommentService toCaseCommentService;
    @Autowired
    private ToMortLoanerMapper toMortLoanerMapper;
    @Autowired
    private TransplanServiceFacade transplanServiceFacade;
    @Autowired
    private StuffService stuffService;
    @Autowired
    private ToMortLoanerService toMortLoanerService;
    @Autowired
    private BizWarnInfoService bizWarnInfoService;
    @Autowired
    private ToEvalMapper toEvalMapper;
    @Autowired
    private UamBasedataService uamBasedataService;
    @Autowired
    private CommSubsService commSubsService;
    @Autowired
    private ReceivablePerfService receivablePerfService;
    @Autowired
    private ToMortgageTosaveService toMortgageTosaveService;
    @Override
    public ToMortgage saveToMortgage(ToMortgage toMortgage)
    {
        // 有记录 update、反之 insert
        if (toMortgage.getPkid() != null)
        {
            toMortgageMapper.update(toMortgage);
        }
        else
        {
            toMortgageMapper.insertSelective(toMortgage);
        }
        // formCommLoan 是否商贷
      /*  if ("1".equals(toMortgage.getFormCommLoan()) && StringUtils.isNotBlank(toMortgage.getLastLoanBank()))
        {
            // 重新设定最终贷款银行（商贷）
            toMortgageMapper.restSetLastLoanBank(toMortgage);
        }*/

        if (null != toMortgage.getCustCode())
        {
            TgGuestInfo guest = tgGuestInfoService.selectByPrimaryKey(Long.parseLong(toMortgage.getCustCode()));
            if (guest != null)
            {
                //guest.setWorkUnit(toMortgage.getCustCompany());
                guest.setGuestName(toMortgage.getCustName());
                tgGuestInfoService.updateByPrimaryKeySelective(guest);
            }
        }
        return toMortgage;
    }
    /**
     * 数组转换成字符串
     * lujian
     * @return
     */
    private ToMortgage transformationStr(ToMortgage toMortgage){
    	//是否补件 1是0否
    	if("1".equals(toMortgage.getIsPatch())){
    		String []  buyPatch = toMortgage.getBuyPatch();
    		String [] sellPatch = toMortgage.getSellPatch();
    		StringBuffer buyPatchSb = new StringBuffer("");
    		StringBuffer sellPatchSb = new StringBuffer("");
    		if(buyPatch != null && buyPatch.length > 0){
    			for (int i = 0; i < buyPatch.length; i++) {
					if(i == buyPatch.length -1){
						buyPatchSb.append(buyPatch[i]);
						break;
					}
					buyPatchSb.append(buyPatch[i]+",");
				}
    			toMortgage.setBuyPatchStr(buyPatchSb.toString());
    		}
    		
    		if(sellPatch != null && sellPatch.length > 0){
    			for (int i = 0; i < sellPatch.length; i++) {
					if(i == sellPatch.length -1){
						sellPatchSb.append(sellPatch[i]);
						break;
					}
					sellPatchSb.append(sellPatch[i]+",");
				}
    			toMortgage.setSellPatchStr(sellPatchSb.toString());
    		}
    		return toMortgage;
    	}
    	return toMortgage;
    }
    
     @Override
    public void saveToMortgageAndSupDocu(ToMortgage toMortgage)
    {


        Long pkid = toMortgage.getPkid();
        if (pkid == null){
            ToMortgage condition = new ToMortgage();// 用这三个条件确定一条商贷的贷款信息,防止前台重复提交数据或者加载数据出问题时数据重复
            condition.setCaseCode(toMortgage.getCaseCode());
            condition.setIsMainLoanBank(toMortgage.getIsMainLoanBank());
            condition.setIsDelegateYucui("1");

            List<ToMortgage> list = toMortgageMapper.findToMortgageByConditionWithCommLoan(condition);
            if (!CollectionUtils.isEmpty(list)){
            	toMortgage.setPkid(list.get(0).getPkid());
            	//数组转换成字符串
            	toMortgage = transformationStr(toMortgage);
                toMortgageMapper.update(toMortgage);
            }else{
                toMortgage.setIsDelegateYucui("1");
                toMortgageMapper.insertSelective(toMortgage);
            }
        }else{
        		if("0".equals(toMortgage.getIsPatch())){
        			toMortgageMapper.updateIsPatch(toMortgage);
        		}else{
        			toMortgage = transformationStr(toMortgage);
                    toMortgageMapper.update(toMortgage);
        		}
        		
        }

        if ("1".equals(toMortgage.getFormCommLoan()) && StringUtils.isNotBlank(toMortgage.getLastLoanBank()))
        {
            toMortgageMapper.restSetLastLoanBank(toMortgage);
        }
     /*   ToSupDocu toSupDocu = toMortgage.getToSupDocu();
        ToSupDocu supDocu = toSupDocuService.findByCaseCode(toMortgage.getCaseCode());

        if (supDocu != null)
        {
            supDocu.setSupContent(toSupDocu.getSupContent());
            supDocu.setRemindTime(toSupDocu.getRemindTime());
            toSupDocuService.updateToSupDocu(supDocu);
        }
        else
        {
            if (StringUtils.isNotBlank(toSupDocu.getSupContent()))
            {
                toSupDocu.setCaseCode(toMortgage.getCaseCode());
                toSupDocu.setPartCode("ComLoanProcess");
                toSupDocuService.saveToSupDocu(toSupDocu);
            }
        }
        if (null != toMortgage.getCustCode())
        {
            TgGuestInfo guest = tgGuestInfoService.selectByPrimaryKey(Long.parseLong(toMortgage.getCustCode()));
            if (guest != null)
            {
                guest.setWorkUnit(toMortgage.getCustCompany());
                guest.setGuestName(toMortgage.getCustName());
                tgGuestInfoService.updateByPrimaryKeySelective(guest);
            }
        }
        ToEval eval= toEvalMapper.selectMortgageId(toMortgage.getPkid());
        
        if(eval==null){//不存在才插入  已经存在的 不能修改
        	eval=toMortgage.getToEval();
        	if(eval.getServiceFee()!=null&&eval.getEvaFee()!=null){
	        	eval.setMortgageId(toMortgage.getPkid());
	    		// 生产 评估费编号
	    		String dateStr = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
	    		String month = dateStr.substring(0, 6);
	    		String evaCode = uamBasedataService.nextSeqVal("EVAL_CODE", month);
	        	eval.setEvaCode(evaCode);
	        	eval.setPkid(null);//因为与贷款表一起传过来会把贷款表的pkid带过来
	        	toEvalMapper.insertSelective(eval);
	        	//生成应收
	        	generatorCommSubs(eval);
	        	//生成应收业绩
	        	generatorPerf(eval);
        	}
        }
*/
        // 为主流程设置变量
        //setEvaReportNeedAtLoanRelease(toMortgage);
    }
    /**
     * 生成应收和应收业绩 
     */
    private void generatorCommSubs(ToEval eval){
    	////////////// 生成应收和业绩///////////// 
    	CommSubsVo commSubsVo=new CommSubsVo();
    	commSubsVo.setBizCode(eval.getEvaCode());
    	commSubsVo.setCaseCode(eval.getCaseCode());
    	commSubsVo.setCommCost(eval.getFeeTotal());
    	List<TpdCommSubsDetals> csds=new ArrayList<>();
    	//评估费
    	if(eval.getEvaFee()!=null){
    		TpdCommSubsDetals csd=new TpdCommSubsDetals();
    		csd.setCommSubject(CommSubjectEnum.DK_PGF_02.getCode());
    		if(eval.getEvaFeeCost()!=null){
    			csd.setCommCost(eval.getEvaFee().subtract(eval.getEvaFeeCost()));
    		}else{
    			csd.setCommCost(eval.getEvaFee());
    		}
    		if(csd.getCommCost().doubleValue()<0){
    			throw new BusinessException("评估费不允许有负值");
    		}
    		csds.add(csd);
    	}
    	//评估费支出
    	if(eval.getEvaFeeCost()!=null){
    		TpdCommSubsDetals csd=new TpdCommSubsDetals();
    		csd.setCommSubject(CommSubjectEnum.CB_DSPGF.getCode());
    		csd.setCommCost(eval.getEvaFeeCost());
    		csds.add(csd);
    	}
    	//服务费
    	if(eval.getServiceFee()!=null){
    		TpdCommSubsDetals csd=new TpdCommSubsDetals();
    		csd.setCommSubject(CommSubjectEnum.DK_FWF_02.getCode());
    		csd.setCommCost(eval.getServiceFee());
    		csds.add(csd);
    	}
    	commSubsVo.setCommSubsDetals(csds);
    	commSubsService.generatorCommSubs(commSubsVo);
    }
    private void generatorPerf(ToEval eval){
		//////////////////////生成应收业绩////////////////////////////
		if(eval.getEvaFeePerf()!=null){//评估费业绩
			generatorPerf(eval,CommSubjectEnum.DK_PGF_02.getCode(),eval.getEvaFeePerf());
		}
		if(eval.getServiceFeePerf()!=null){
			generatorPerf(eval,CommSubjectEnum.DK_FWF_02.getCode(),eval.getServiceFeePerf());
		}
    }
    /**
     * 生成业绩
     * @param eval
     * @param subject
     * @param amount
     */
    private void generatorPerf (ToEval eval,String subject,BigDecimal amount){
    	ReceivablePerfVo receiveablePerfVo = new ReceivablePerfVo();
		receiveablePerfVo.setBizCode(eval.getEvaCode());
		receiveablePerfVo.setCaseCode(eval.getCaseCode());
		receiveablePerfVo.setBizPkid(eval.getPkid()+"");	
		receiveablePerfVo.setSharesRate(new BigDecimal("1"));
		receiveablePerfVo.setSubject(subject);
		receiveablePerfVo.setSalesAmount(amount);
		receiveablePerfVo.setOrgId(eval.getOrgId());
		receiveablePerfVo.setUserId(eval.getCreateBy());
		receiveablePerfVo.setSharesTime(new Date());
		receiveablePerfVo.setRoleType(RoleTypeEnum.EXECUTOR.getCode());
		receivablePerfService.generatePerf(receiveablePerfVo);
    }
    /**
     * 
     * @param mid
     * @return
     */
    @Override
    public ToEval findEvalByMortgageId(Long mid){
    	return toEvalMapper.selectMortgageId(mid);
    }
    @Override
    public ToMortgage findToMortgageById(Long id)
    {
        return toMortgageMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateToMortgage(ToMortgage toMortgage)
    {
        toMortgageMapper.update(toMortgage);

    }

    @Override
    public void updateByPrimaryKey(ToMortgage toMortgage)
    {
        toMortgageMapper.updateByPrimaryKey(toMortgage);

    }

    @Override
    public ToMortgage findToMortgageByCaseCode(String caseCode)
    {
        ToMortgage toMortgage = toMortgageMapper.findToMortgageByCaseCode(caseCode);
        if (toMortgage != null)
        {
            ToSupDocu toSupDocu = toSupDocuService.findByCaseCode(caseCode);
            toMortgage.setToSupDocu(toSupDocu);
            toMortgage.setComAmount(toMortgage.getComAmount() != null ? toMortgage.getComAmount().divide(new BigDecimal(10000)) : null);
            toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount() != null ? toMortgage.getMortTotalAmount().divide(new BigDecimal(10000)) : null);
            toMortgage.setPrfAmount(toMortgage.getPrfAmount() != null ? toMortgage.getPrfAmount().divide(new BigDecimal(10000)) : null);
            toMortgage.setToSupDocu(toSupDocu);
        }
        return toMortgage;
    }

    @Override
    public ToMortgage findToMortgageByCaseCode(ToMortgage toMortgage)
    {
        List<ToMortgage> list = toMortgageMapper.findToMortgageByCaseCodeAndBankType(toMortgage);
        if (CollectionUtils.isNotEmpty(list))
        {
            ToMortgage mort = null;
            ToSupDocu toSupDocu = toSupDocuService.findByCaseCode(toMortgage.getCaseCode());

            if (list.size() == 1)
            {
                mort = list.get(0);
            }
            else
            {
                for (ToMortgage mortgage : list)
                {
                    if (StringUtils.isNotBlank(mortgage.getLastLoanBank()))
                    {
                        mort = mortgage;
                        break;
                    }
                }
            }
            mort.setComAmount(mort.getComAmount() != null ? mort.getComAmount().divide(new BigDecimal(10000)) : null);
            mort.setMortTotalAmount(mort.getMortTotalAmount() != null ? mort.getMortTotalAmount().divide(new BigDecimal(10000)) : null);
            mort.setPrfAmount(mort.getPrfAmount() != null ? mort.getPrfAmount().divide(new BigDecimal(10000)) : null);
            mort.setToSupDocu(toSupDocu);

            return mort;
        }
        return null;
    }

    @Override
    public ToMortgage findToMortgageByCaseCodeWithCommLoan(ToMortgage toMortgage)
    {
        //toMortgage.setIsDelegateYucui("1");
        List<ToMortgage> list = toMortgageMapper.findToMortgageByConditionWithCommLoan(toMortgage);
        if (CollectionUtils.isNotEmpty(list))
        {
            ToMortgage mort = null;
            ToSupDocu toSupDocu = toSupDocuService.findByCaseCode(toMortgage.getCaseCode());

            if (list.size() == 1)
            {
                mort = list.get(0);
            }
            else
            {
                for (ToMortgage mortgage : list)
                {
                    if (StringUtils.isNotBlank(mortgage.getLastLoanBank()))
                    {
                        mort = mortgage;
                        break;
                    }
                }
            }
           
            /*
             * mort.setComAmount(mort.getComAmount() != null ?
             * mort.getComAmount() .divide(new BigDecimal(10000)) : null);
             * mort.setMortTotalAmount(mort.getMortTotalAmount() != null
             * ?mort.getMortTotalAmount().divide( new BigDecimal(10000)):null);
             * mort.setPrfAmount(mort.getPrfAmount() != null ?
             * mort.getPrfAmount() .divide(new BigDecimal(10000)) : null);
             */
            mort.setToSupDocu(toSupDocu);

            return mort;
        }
        if(StringUtils.isBlank(toMortgage.getBank_type()) && StringUtils.isBlank(toMortgage.getBank_type())){
        	MortgageToSaveVO mortgageToSaveVO = toMortgageTosaveService.getTosave(toMortgage);
        	if(mortgageToSaveVO != null){
        		toMortgage.setBank_type(mortgageToSaveVO.getBank_type());
        		toMortgage.setFinOrgCode(mortgageToSaveVO.getFinOrgCode());
        	}
        }
        return null;
    }

    @Override
    public ToMortgage findToMortgageByCaseCode2(String caseCode)
    {

        List<ToMortgage> toMortgageList = toMortgageMapper.findToMortgageByCaseCodeNoBlank(caseCode);
        if (CollectionUtils.isNotEmpty(toMortgageList))
        {
            ToMortgage mort = null;
            if (toMortgageList.size() == 1)
            {
                mort = toMortgageList.get(0);
            }
            else
            {
                for (ToMortgage toMortgage : toMortgageList)
                {
                    if (StringUtils.isNotEmpty(toMortgage.getLastLoanBank()))
                    {
                        mort = toMortgage;
                    }
                }
            }
            if (mort == null)
            {
                return mort;
            }
            mort.setComAmount(mort.getComAmount() != null ? mort.getComAmount().divide(new BigDecimal(10000)) : null);
            mort.setMortTotalAmount(mort.getMortTotalAmount() != null ? mort.getMortTotalAmount().divide(new BigDecimal(10000)) : null);
            mort.setPrfAmount(mort.getPrfAmount() != null ? mort.getPrfAmount().divide(new BigDecimal(10000)) : null);
            return mort;
        }
        return null;
    }

    @Override
    public ToMortgage findToMortgageByMortTypeAndCaseCode(String caseCode, String mortType)
    {
        ToMortgage toMortgage = new ToMortgage();
        toMortgage.setCaseCode(caseCode);
        toMortgage.setMortType(mortType);
        toMortgage.setIsDelegateYucui("1");
        List<ToMortgage> list = toMortgageMapper.findToMortgageByCondition(toMortgage);
        if (list != null && !list.isEmpty())
        {
            return list.get(0);
        }
        return null;
    }

    @Override
    public ToMortgage findToSelfLoanMortgage(String caseCode)
    {
        ToMortgage toMortgage = new ToMortgage();
        toMortgage.setCaseCode(caseCode);
        toMortgage.setIsDelegateYucui("0");
        List<ToMortgage> list = toMortgageMapper.findToMortgageByCondition(toMortgage);
        if (list != null && !list.isEmpty())
        {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void inActiveMortageByCaseCode(String caseCode)
    {
        toMortgageMapper.inActiveMortageByCaseCode(caseCode);
    }

    @Override
    public void submitMortgage(ToMortgage toMortgage, List<RestVariable> variables, String taskId, String processInstanceId)
    {

        // 保存贷款信息
        ToMortgage mortgage = findToMortgageById(toMortgage.getPkid());
        mortgage.setApprDate(toMortgage.getApprDate());
        mortgage.setRemark(toMortgage.getRemark());
        saveToMortgage(mortgage);

        // 提交任务
        ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());
        workFlowManager.submitTask(variables, taskId, processInstanceId, toCase.getLeadingProcessId(), toMortgage.getCaseCode());

        // 发送消息
        ToWorkFlow wf = new ToWorkFlow();
        wf.setCaseCode(toMortgage.getCaseCode());
        wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
        ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
        //&& "operation_process:40:645454".compareTo(wordkFlowDB.getProcessDefinitionId()) <= 0 取消版本比较
        if (wordkFlowDB != null )
        {
            messageService.sendMortgageFinishMsgByIntermi(wordkFlowDB.getInstCode());
            // 设置主流程任务的assignee
            workFlowManager.setAssginee(wordkFlowDB.getInstCode(), toCase.getLeadingProcessId(), wordkFlowDB.getCaseCode());

            // 结束当前流程
            ToWorkFlow workFlowOld = new ToWorkFlow();
            // 流程结束状态
            workFlowOld.setStatus("4");
            workFlowOld.setInstCode(processInstanceId);
            toWorkFlowService.updateWorkFlowByInstCode(workFlowOld);
        }

    }

    /**
     * 删除临时银行审批流程
     * 
     * @param twf
     */
    @Override
    public void deleteTmpBankProcess(ToWorkFlow twf)
    {
        try
        {
            ToWorkFlow temBankWF = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);
            // 删除临时银行审批任务和流程
            if (temBankWF != null)
            {
                unlocatedTaskService.deleteByInstCode(temBankWF.getInstCode());
                workFlowManager.deleteProcess(temBankWF.getInstCode());
            }
        }
        catch (Exception e)
        {
            throw new BusinessException("删除临时银行任务和流程失败！");
        }

    }

    private void setEvaReportNeedAtLoanRelease(ToMortgage toMortgage)
    {
        ToWorkFlow wf = new ToWorkFlow();
        wf.setCaseCode(toMortgage.getCaseCode());
        wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
        ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
        // 是否需要发起评估报告
        if (StringUtils.isNotBlank(toMortgage.getIfReportBeforeLend()) && "1".equals(toMortgage.getIfReportBeforeLend()) && wordkFlowDB != null
                && "operation_process:40:645454".compareTo(wordkFlowDB.getProcessDefinitionId()) <= 0)
        {
            String variableName = "EvaReportNeedAtLoanRelease";
            RestVariable restVariable = new RestVariable();
            restVariable.setType("boolean");
            restVariable.setValue(true);
            workFlowManager.setVariableByProcessInsId(wordkFlowDB.getInstCode(), variableName, restVariable);
        }
    }

    @Override
    public AjaxResponse<String> startTmpBankWorkFlow(String caseCode, String loanerInstCode)
    {

        @SuppressWarnings("unused")
        User manager = null, seniorManager = null, director = null;
        AjaxResponse<String> response = new AjaxResponse<String>();

        try
        {
            /* 流程引擎相关 */
            List<RestVariable> variables = new ArrayList<RestVariable>();
            ToCase te = toCaseService.findToCaseByCaseCode(caseCode);
            String orgId = te.getOrgId();
            SessionUser user = uamSessionService.getSessionUser();
            // 查询主管
            manager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(orgId, "Manager");
            String parsentId = uamUserOrgService.getOrgById(orgId).getParentId();
            // 查询高级主管
            seniorManager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(orgId, "Senior_Manager");
            // 查询总监
            director = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(parsentId, "director");

            // 信贷员新流程需要设置的参数
            if (null != loanerInstCode && !"".equals(loanerInstCode))
            {
                variables.add(new RestVariable("loanerInstCode", loanerInstCode));
            }

            variables.add(new RestVariable("Manager", manager.getUsername()));
            /*
             * variables.add(new RestVariable("SeniorManager", seniorManager ==
             * null ? null : seniorManager.getUsername())); variables.add(new
             * RestVariable("director", director.getUsername()));
             */
            // 启动
            ProcessInstance process = new ProcessInstance(propertyUtilsService.getProcessTmpBankAuditDfKey(), caseCode, variables);
            StartProcessInstanceVo vo = workFlowManager.startCaseWorkFlow(process, manager.getUsername(), caseCode);
            // 插入工作流表
            ToWorkFlow toWorkFlow = new ToWorkFlow();
            toWorkFlow.setBusinessKey(WorkFlowEnum.TMP_BANK_DEFKEY.getCode());
            toWorkFlow.setCaseCode(caseCode);
            toWorkFlow.setBizCode(caseCode);
            toWorkFlow.setInstCode(vo.getId());
            toWorkFlow.setProcessDefinitionId(propertyUtilsService.getProcessTmpBankAuditDfKey());
            toWorkFlow.setProcessOwner(user.getId());
            toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
            toWorkFlowService.insertSelective(toWorkFlow);

            // 更新贷款表临时银行状态为默认
            ToMortgage mortageDb = findToMortgageByCaseCode2(caseCode);
            if (mortageDb != null)
            {
                mortageDb.setComAmount(mortageDb.getComAmount() != null ? mortageDb.getComAmount().multiply(new BigDecimal(10000)) : null);
                mortageDb.setMortTotalAmount(mortageDb.getMortTotalAmount() != null ? mortageDb.getMortTotalAmount().multiply(new BigDecimal(10000)) : null);
                mortageDb.setPrfAmount(mortageDb.getPrfAmount() != null ? mortageDb.getPrfAmount().multiply(new BigDecimal(10000)) : null);
                mortageDb.setTmpBankStatus(TmpBankStatusEnum.INAPPROVAL.getCode());
                updateToMortgage(mortageDb);
            }
            response.setSuccess(true);
            response.setMessage("已成功开启临时银行审批流程！");
        }
        catch (Exception e)
        {
            response.setSuccess(false);
            if (manager == null)
            {
                response.setMessage("开启临时银行审批流程失败:找不到案件所属组织的主管！");
            }
            else if (director == null)
            {
                response.setMessage("开启临时银行审批流程失败:找不到案件所属组织上级组织的总监！");
            }
            else
            {
                response.setMessage("开启临时银行审批流程报错，请联系管理员！");
            }

            e.printStackTrace();
            throw e;
        }
        return response;
    }

    @Override
    public AjaxResponse<?> tmpBankThriceAduit(ToMortgage mortage, String prAddress, String tmpBankName, String tmpBankCheck, String taskId, String bankCode,
            String temBankRejectReason, String processInstanceId, String caseCode, String post)
    {

        // prAddress物业地址
        // tmpBankCheck true：是否审批通过
        // temBankRejectReason 审批意见
        // post 区分 主管、高级主管、总监审批
        ToCase te = toCaseService.findToCaseByCaseCode(caseCode);
        String orgId = te.getOrgId();
        SessionUser user = uamSessionService.getSessionUser();

        if ("manager".equals(post))
        {
            boolean isManagerApprove = false;
            if ("true".equals(tmpBankCheck))
            {
                isManagerApprove = true;
            }

            // 更新案件信息
            ToMortgage mortageDb = findToMortgageById(mortage.getPkid());

            if (!isManagerApprove)
            {
                mortageDb.setTmpBankStatus(TmpBankStatusEnum.REJECT.getCode());
                mortageDb.setTmpBankRejectReason(temBankRejectReason);
                // 更新流程状态为‘4’：已完成
                ToWorkFlow twf = new ToWorkFlow();
                twf.setBusinessKey(WorkFlowEnum.TMP_BANK_DEFKEY.getCode());
                twf.setCaseCode(caseCode);
                ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);
                if (record != null)
                {
                    record.setStatus(WorkFlowStatus.COMPLETE.getCode());
                    toWorkFlowService.updateByPrimaryKeySelective(record);
                }

            }
            else
            {
                mortageDb.setFinOrgCode(bankCode);
                mortageDb.setTmpBankStatus(TmpBankStatusEnum.INAPPROVAL.getCode());
                mortageDb.setTmpBankRejectReason("");
            }
            mortageDb.setTmpBankUpdateBy(user.getId());
            mortageDb.setTmpBankUpdateTime(new Date());
            updateToMortgage(mortageDb);

            List<RestVariable> variables = new ArrayList<RestVariable>();
            variables.add(new RestVariable("isManagerApprove", isManagerApprove));
            // 查询高级主管(实时)
            User seniorManager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(orgId, "Senior_Manager");
            variables.add(new RestVariable("SeniorManager", seniorManager == null ? null : seniorManager.getUsername()));

            workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);

            // 添加审核记录到ToApproveRecord
            ToApproveRecord toApproveRecord = new ToApproveRecord();
            toApproveRecord.setCaseCode(caseCode);
            toApproveRecord.setContent("审批" + (isManagerApprove ? "通过" : "驳回") + "，审批意见为：" + temBankRejectReason);
            toApproveRecord.setApproveType("8");// todo
            toApproveRecord.setOperator(user.getId());
            toApproveRecord.setTaskId(taskId);
            toApproveRecord.setOperatorTime(new Date());
            toApproveRecord.setPartCode("ManagerAduit");// todo
            toApproveRecord.setProcessInstance(processInstanceId);

            toApproveRecordService.insertToApproveRecord(toApproveRecord);
        }
        else if ("seniorManager".equals(post))
        {
            boolean isSeniorManagerApprove = false;
            if ("true".equals(tmpBankCheck))
            {
                isSeniorManagerApprove = true;
            }

            // 更新案件信息
            ToMortgage mortageDb = findToMortgageById(mortage.getPkid());

            if (!isSeniorManagerApprove)
            {
                mortageDb.setTmpBankStatus(TmpBankStatusEnum.REJECT.getCode());
                mortageDb.setTmpBankRejectReason(temBankRejectReason);
                // 更新流程状态为‘4’：已完成
                ToWorkFlow twf = new ToWorkFlow();
                twf.setBusinessKey(WorkFlowEnum.TMP_BANK_DEFKEY.getCode());
                twf.setCaseCode(caseCode);
                ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);
                if (record != null)
                {
                    record.setStatus(WorkFlowStatus.COMPLETE.getCode());
                    toWorkFlowService.updateByPrimaryKeySelective(record);
                }
            }
            else
            {
                // mortageDb.setTmpBankUpdateTime(new Date());
            }
            updateToMortgage(mortageDb);

            List<RestVariable> variables = new ArrayList<RestVariable>();
            variables.add(new RestVariable("isSeniorManagerApprove", isSeniorManagerApprove));
            // 查询总监(实时)
            String parsentId = uamUserOrgService.getOrgById(orgId).getParentId();
            User director = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(parsentId, "director");
            variables.add(new RestVariable("director", director.getUsername()));

            workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);

            // 添加审核记录到ToApproveRecord
            ToApproveRecord toApproveRecord = new ToApproveRecord();
            toApproveRecord.setCaseCode(caseCode);
            toApproveRecord.setContent("审批" + (isSeniorManagerApprove ? "通过" : "驳回") + "，审批意见为：" + temBankRejectReason);
            toApproveRecord.setApproveType("8");// todo
            toApproveRecord.setOperator(user.getId());
            toApproveRecord.setTaskId(taskId);
            toApproveRecord.setOperatorTime(new Date());
            toApproveRecord.setPartCode("SuperManagerAudit");// todo
            toApproveRecord.setProcessInstance(processInstanceId);

            toApproveRecordService.insertToApproveRecord(toApproveRecord);
        }
        else if ("director".equals(post))
        {

            ToMortgage mortageDb = findToMortgageById(mortage.getPkid());
            ToCase c = toCaseService.findToCaseByCaseCode(mortageDb.getCaseCode());

            // 获取流程变量
            /*
             * RestVariable restVariableCode =
             * workFlowManager.getVar(processInstanceId, "loanerInstCode");
             * String loanerInstCode = restVariableCode.getValue().toString();
             */
            // 更新案件信息
            if ("false".equals(tmpBankCheck))
            {
                mortageDb.setTmpBankStatus(TmpBankStatusEnum.REJECT.getCode());
                mortageDb.setTmpBankRejectReason(temBankRejectReason);

                // 总监审批不通过的情况下 发送失败信息
                /*
                 * if (!"".equals(loanerInstCode)) {
                 * setLoanerProcessVariable(loanerInstCode, false); }
                 */

            }
            else if ("true".equals(tmpBankCheck))
            {
                mortageDb.setTmpBankStatus(TmpBankStatusEnum.AGREE.getCode());

                /*
                 * if (!"".equals(loanerInstCode)) {
                 * setLoanerProcessVariable(loanerInstCode, true); }
                 */
            }

            updateToMortgage(mortageDb);

            // 更新流程状态为'4'：已完成
            ToWorkFlow twf = new ToWorkFlow();
            twf.setBusinessKey(WorkFlowEnum.TMP_BANK_DEFKEY.getCode());
            twf.setCaseCode(caseCode);
            ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(twf);
            if (record != null)
            {
                record.setStatus(WorkFlowStatus.COMPLETE.getCode());
                toWorkFlowService.updateByPrimaryKeySelective(record);
            }

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("case_code", mortageDb.getCaseCode());
            params.put("property_address", prAddress);
            params.put("bank", tmpBankName);

            String content = uamTemplateService.mergeTemplate("TMP_BANK_REMINDER", params);
            Message message = new Message();
            // 消息标题
            message.setTitle("临时银行处理提醒");
            // 消息类型
            message.setType(MessageType.SITE);
            /* 设置提醒列别 */

            message.setMsgCatagory(MsgCatagoryEnum.NEWS.getCode());

            /* 内容 */
            message.setContent(content);
            /* 发送人 */
            String senderId = user.getId();
            /* 设置发送人 */
            message.setSenderId(senderId);

            uamMessageService.sendMessageByUser(message, c.getLeadingProcessId());

            List<RestVariable> variables = new ArrayList<RestVariable>();
            workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);

            // 添加审核记录到ToApproveRecord
            ToApproveRecord toApproveRecord = new ToApproveRecord();
            toApproveRecord.setCaseCode(caseCode);
            toApproveRecord.setContent("审批" + ("true".equals(tmpBankCheck) ? "通过" : "驳回") + "，审批意见为：" + temBankRejectReason);
            toApproveRecord.setApproveType("8");// todo
            toApproveRecord.setOperator(user.getId());
            toApproveRecord.setTaskId(taskId);
            toApproveRecord.setOperatorTime(new Date());
            toApproveRecord.setPartCode("DirectorAudit");// todo
            toApproveRecord.setProcessInstance(processInstanceId);

            toApproveRecordService.insertToApproveRecord(toApproveRecord);
        }
        return AjaxResponse.success();
    }

    @Override
    public ToMortgage getMortgageByCaseCode(String caseCode)
    {
        return toMortgageMapper.getMortgageByCaseCode(caseCode);
    }

    @Override
    public int updateTmpBankStatus(String caseCode)
    {
        return toMortgageMapper.updateTmpBankStatus(caseCode);
    }

    @Override
    public List<ToMortgage> findToMortgageByCaseCodeAndCustcode(ToMortgage toMortgage)
    {
        // TODO Auto-generated method stub
        return toMortgageMapper.findToMortgageByCaseCodeAndCustcode(toMortgage);
    }

    @Override
    public void updateToMortgageByCode(String caseCode)
    {
        // TODO Auto-generated method stub
        toMortgageMapper.updateCustNameByCasecode(caseCode);
    }

    @Override
    public void updateToMortgageBySign(ToMortgage toMortgage)
    {
        // TODO Auto-generated method stub
        toMortgageMapper.updateBySign(toMortgage);
    }

    @Override
    public int updateByTest(ToMortgage record)
    {

        return toMortgageMapper.updateByTest(record);
    }

    /**
     * @see com.centaline.trans.mortgage.service.ToMortgageService#addMortgageTrack4Par(com.centaline.trans.mortgage.entity.ToMortgage)
     */
    @Override
    public int addMortgageTrack4Par(ToCaseComment track)
    {

        if (null != track && null != track.getBizCode())
        {
            int count = toCaseCommentMapper.insertSelective(track);
            ToMortgage mortgage = toMortgageMapper.getMortgageByBizCode(track.getBizCode());

            if (count > 0 && null != mortgage)
            {
                mortgage.setStateInBank(track.getSrvCode());
                int count1 = toMortgageMapper.updateStateInBankByBizCode(mortgage);
                if (count1 <= 0)
                    throw new BusinessException("按揭贷款状态更新失败，请稍后重试");
                else
                    return count1;
            }
            throw new BusinessException("对不起，找不到编号[" + track.getBizCode() + "]对应的按揭贷款，请稍后重试");

        }
        throw new BusinessException("按揭贷款编号为空");

    }

    /*
     * @author: zhuody
     * 
     * @date : 2017-03-30
     * 
     * @des: 三级银行审批 总监审批完之后 发送分单信贷员流程消息和设置流程变量 0502:流程变更，不需要发送消息
     */
    @SuppressWarnings("unused")
    private void setLoanerProcessVariable(String loanerInstCode, boolean approveFlag)
    {

        RestVariable restVariable = new RestVariable();
        restVariable.setType("boolean");
        // 银行分级审批通过标志判断发送消息类别
        try
        {
            if (approveFlag == false)
            {
                restVariable.setValue(false);
            }
            else
            {
                restVariable.setValue(true);
            }
            // 设置流程变量
            workFlowManager.setVariableByProcessInsId(loanerInstCode, "bankLevelApprove", restVariable);
            messageService.sendBankLevelApproveMsg(loanerInstCode, approveFlag);
        }
        catch (BusinessException e)
        {
            throw new BusinessException("银行分级审批消息发送异常！");
        }

    }

    @Override
    public boolean accept(MortgageVo mortgageVo)
    {
        // 信贷员接单
        if ("true".equals(mortgageVo.getIsPass()))
        {
            // 处理流程,信贷员接单,流程进入银行审核流程
            loanerProcessService.isLoanerAcceptCase(true, mortgageVo.getTaskId(), mortgageVo.getProcInstanceId(), mortgageVo.getCaseCode(), mortgageVo.getBizCode(),
                    mortgageVo.getStateInBank());

            // 接单之后自动解除预警信息
            BizWarnInfo bizWarnInfo = new BizWarnInfo();
            bizWarnInfo.setStatus("1");
            bizWarnInfo.setCaseCode(mortgageVo.getCaseCode());
            bizWarnInfo.setWarnType("LoanerOverdueAccept");

            bizWarnInfoService.updateStatusByCaseCodeAndWarnType(bizWarnInfo);
        }
        // 信贷员打回
        else if ("false".equals(mortgageVo.getIsPass()))
        {
            // 处理流程,信贷员打回,流程进入交易顾问派单
            loanerProcessService.isLoanerAcceptCase(false, mortgageVo.getTaskId(), mortgageVo.getProcInstanceId(), mortgageVo.getCaseCode(), mortgageVo.getBizCode(),
                    mortgageVo.getStateInBank());
        }

        // 设置案件跟进信息
        ToCaseComment toCaseComment = setToCaseComment(mortgageVo.getUser(), mortgageVo.getBizCode(), mortgageVo.getCaseCode(), "TRACK", mortgageVo.getStateInBank(),
                mortgageVo.getComment());

        // 保存案件跟进信息
        toCaseCommentService.insertToCaseComment(toCaseComment);

        return true;
    }

    @Override
    public boolean followUp(MortgageVo mortgageVo)
    {

        ToMortLoaner toMortLoaner = new ToMortLoaner();
        toMortLoaner.setPkid(Long.parseLong(mortgageVo.getBizCode()));

        // 银行审核通过
        if ("true".equals(mortgageVo.getIsPass()))
        {

            if ("MORT_APPROVED".equals(mortgageVo.getStateInBank()))
            {
                // 处理流程,银行审核通过
                loanerProcessService.isBankAcceptCase(true, mortgageVo.getTaskId(), mortgageVo.getProcInstanceId(), mortgageVo.getCaseCode(), mortgageVo.getBizCode(),
                        mortgageVo.getStateInBank());

                try
                {
                    // 获取按揭接收信息
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

                    ToMortLoaner toMortLoanerInfo = toMortLoanerMapper.getToMortLoanerById(Long.parseLong(mortgageVo.getBizCode()));
                    ToMortgage toMortgage = new ToMortgage();
                    toMortgage.setPkid(Long.parseLong(toMortLoanerInfo.getMortPkid()));
                    toMortgage.setApprDate(sdf.parse(mortgageVo.getDate()));

                    toMortgageMapper.updateByPkId(toMortgage);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                // 更新跟进状态
                toMortLoaner.setFlowStatus(mortgageVo.getStateInBank());

                // 如果是贷款发放,设置派单状态为已关闭状态
                if ("MORT_RELEASED".equals(mortgageVo.getStateInBank()))
                {
                    toMortLoaner.setLoanerStatus(LoanerStatusEnum.CLOSED.getCode());
                }

                toMortLoanerService.updateByPrimaryKeySelective(toMortLoaner);
            }

        }
        // 银行审核拒绝
        else if ("false".equals(mortgageVo.getIsPass()))
        {
            // 处理流程,银行审核驳回
            loanerProcessService.isBankAcceptCase(false, mortgageVo.getTaskId(), mortgageVo.getProcInstanceId(), mortgageVo.getCaseCode(), mortgageVo.getBizCode(),
                    mortgageVo.getStateInBank());
        }

        // 设置案件跟进信息
        ToCaseComment toCaseComment = setToCaseComment(mortgageVo.getUser(), mortgageVo.getBizCode(), mortgageVo.getCaseCode(), "TRACK", mortgageVo.getStateInBank(),
                mortgageVo.getComment());

        // 保存案件跟进信息
        toCaseCommentService.insertToCaseComment(toCaseComment);

        return true;
    }

    /**
     * 设置按揭贷款案件跟进信息
     * 
     * @param user
     *            用户信息
     * @param caseCode
     *            案件编号
     * @param srvCode
     *            环节编码
     * @param comment
     *            跟进跟进内容
     * @return 返回案件跟进信息
     */
    private ToCaseComment setToCaseComment(SessionUser user, String bizCode, String caseCode, String type, String srvCode, String comment)
    {
        // 添加案件跟进信息
        ToCaseComment toCaseComment = new ToCaseComment();
        toCaseComment.setBizCode(bizCode);
        toCaseComment.setCaseCode(caseCode);
        toCaseComment.setType(type);
        toCaseComment.setSource("MORT");
        toCaseComment.setSrvCode(srvCode);
        toCaseComment.setComment(comment);
        toCaseComment.setCreateTime(new Date());

        if (user != null)
        {
            toCaseComment.setCreateBy(user.getId());
            toCaseComment.setCreatorOrgId(user.getServiceDepId());
        }

        return toCaseComment;
    }

    /*
     * @author: zhuody
     * 
     * @date : 2017-04-05
     * 
     * @des : 派单结束之后，回写pkid到T_TO_WORKFLOW表的biz_code
     */

    @SuppressWarnings("unused")
    private void writeBackBizCode(String caseCode, long pkid)
    {

        if ((null == caseCode || "".equals(caseCode)) || pkid < 0)
        {
            throw new BusinessException("回写bizCode参数异常！");
        }
        try
        {

            ToWorkFlow toWorkFlowForSelect = new ToWorkFlow();
            toWorkFlowForSelect.setCaseCode(caseCode);
            toWorkFlowForSelect.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getName());
            ToWorkFlow workFlow = toWorkFlowService.queryToWorkFlowByCaseCodeBusKey(toWorkFlowForSelect);

            if (null != workFlow)
            {
                ToWorkFlow workFlowForUpdate = new ToWorkFlow();
                workFlowForUpdate.setPkid(workFlow.getPkid());
                workFlowForUpdate.setBizCode(String.valueOf(pkid));
                toWorkFlowService.updateByPrimaryKeySelective(workFlowForUpdate);
            }

        }
        catch (BusinessException e)
        {
            throw new BusinessException("回写bizCode数据异常！");
        }
    }

    @Override
    public void suppleInfo(MortgageVo mortgageVo)
    {
        ToCaseComment toCaseComment = setToCaseComment(mortgageVo.getUser(), mortgageVo.getBizCode(), mortgageVo.getCaseCode(), mortgageVo.getType(),
                mortgageVo.getStateInBank(), mortgageVo.getComment());

        // 保存案件跟进信息
        toCaseCommentService.insertToCaseComment(toCaseComment);

        // 如果是补件,开启流程
        if ("BUJIAN".equals(mortgageVo.getType()))
        {
            ToCaseComment track = new ToCaseComment();
            track.setBizCode(mortgageVo.getBizCode());
            track.setSource(mortgageVo.getSource());
            track.setType(mortgageVo.getType());
            track.setCaseCode(mortgageVo.getCaseCode());
            track.setPkid(toCaseComment.getPkid());

            stuffService.reqStuff(track, true);
        }
    }

    /**
     * 根据casecode查询贷款
     * 
     * @author caoy
     * @param caseCode
     * @return
     */
    public List<ToMortgage> findToMortgageByCaseCodeNoBlank(String caseCode)
    {
        return toMortgageMapper.findToMortgageByCaseCodeNoBlank(caseCode);
    }

    @Override
    public Result2 submitLoanlostApply(HttpServletRequest request, ToMortgage toMortgage, ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO,
            String partCode, Long lapPkid)
    {
        if (toMortgage.getMortTotalAmount() != null)
        {
            toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount().multiply(new BigDecimal(10000)));
        }
        toMortgage.setIsMainLoanBank("1");

        SessionUser user = uamSessionService.getSessionUser();
        toMortgage.setLoanAgent(user.getId());
        toMortgage.setLoanAgentTeam(user.getServiceDepId());
        saveToMortgage(toMortgage);

        /* 流程引擎相关 */
        List<RestVariable> variables = new ArrayList<RestVariable>();

        ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());
        workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), toCase.getLeadingProcessId(),
                toMortgage.getCaseCode());

        /**
         * 功能: 给客户发送短信 作者：zhangxb16
         */
        Result2 rs = new Result2();
        try
        {
            int result = tgGuestInfoService.sendMsgHistory(toMortgage.getCaseCode(), partCode);
            if (result > 0)
            {
            }
            else
            {
                rs.setMessage("短信发送失败, 请您线下手工再次发送！");
            }
        }
        catch (BusinessException ex)
        {
            ex.getMessage();
        }

        return rs;
    }

    @Override
    public Boolean submitSelfLoanApprove(HttpServletRequest request, ToMortgage toMortgage, String taskId, String processInstanceId)
    {
        if (toMortgage.getMortTotalAmount() != null)
        {
            toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount().multiply(new BigDecimal(10000)));
        }
        if (toMortgage.getComAmount() != null)
        {
            toMortgage.setComAmount(toMortgage.getComAmount().multiply(new BigDecimal(10000)));
        }
        if (toMortgage.getPrfAmount() != null)
        {
            toMortgage.setPrfAmount(toMortgage.getPrfAmount().multiply(new BigDecimal(10000)));
        }
        toMortgage.setIsMainLoanBank("1");
        saveToMortgage(toMortgage);

        // 发送消息
        ToWorkFlow wf = new ToWorkFlow();
        wf.setCaseCode(toMortgage.getCaseCode());
        wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
        ToWorkFlow wordkFlowDB = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
        if (wordkFlowDB != null && "operation_process:40:645454".compareTo(wordkFlowDB.getProcessDefinitionId()) <= 0)
        {
            messageService.sendMortgageFinishMsgByIntermi(wordkFlowDB.getInstCode());
            // 设置主流程任务的assignee
            ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());
            workFlowManager.setAssginee(wordkFlowDB.getInstCode(), toCase.getLeadingProcessId(), wordkFlowDB.getCaseCode());

            // 结束当前流程
            ToWorkFlow workFlowOld = new ToWorkFlow();
            // 流程结束状态
            workFlowOld.setStatus("4");
            workFlowOld.setInstCode(processInstanceId);
            toWorkFlowService.updateWorkFlowByInstCode(workFlowOld);
        }

        /* 流程引擎相关 */
        List<RestVariable> variables = new ArrayList<RestVariable>();
        ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());
        return workFlowManager.submitTask(variables, taskId, processInstanceId, toCase.getLeadingProcessId(), toMortgage.getCaseCode());
    }

    @Override
    public Result2 submitLoanRelease(HttpServletRequest request, ToMortgage toMortgage, String taskitem, Date estPartTime, String taskId, String processInstanceId,
            String partCode)
    {
        toMortgage.setIsMainLoanBank("1");
        ToMortgage mortage = findToMortgageById(toMortgage.getPkid());
        mortage.setLendDate(toMortgage.getLendDate());
        mortage.setTazhengArrDate(toMortgage.getTazhengArrDate());
        mortage.setRemark(toMortgage.getRemark());
        saveToMortgage(mortage);

        /* 流程引擎相关 */
        List<RestVariable> variables = new ArrayList<RestVariable>();
        ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());
        workFlowManager.submitTask(variables, taskId, processInstanceId, toCase.getLeadingProcessId(), toMortgage.getCaseCode());

        /**
         * 功能: 给客户发送短信 作者：zhangxb16
         */
        Result2 rs = new Result2();
        try
        {
            int result = tgGuestInfoService.sendMsgHistory(toMortgage.getCaseCode(), partCode);
            if (result > 0)
            {
            }
            else
            {
                rs.setMessage("短信发送失败, 请您线下手工再次发送！");
            }
        }
        catch (BusinessException ex)
        {
            ex.getMessage();
        }

        return rs;
    }

    @Override
    public Boolean submitPsfApply(HttpServletRequest request, ToMortgage toMortgage, String taskitem, Date estPartTime, String taskId, String processInstanceId)
    {
        ToTransPlan toTransPlan = new ToTransPlan();
        toTransPlan.setCaseCode(toMortgage.getCaseCode());

        // 修改人：zhangxb16 时间：2015-11-12
        toTransPlan.setPartCode("PSFApply");
        toTransPlan.setEstPartTime(estPartTime);
        toMortgage.setIsDelegateYucui("1");
        transplanServiceFacade.updateTransPlan(toTransPlan);
        toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount() != null ? toMortgage.getMortTotalAmount().multiply(new BigDecimal(10000)) : null);
        toMortgage.setIsMainLoanBank("1");

        SessionUser user = uamSessionService.getSessionUser();
        toMortgage.setLoanAgent(user.getId());
        toMortgage.setLoanAgentTeam(user.getServiceDepId());
        saveToMortgage(toMortgage);

        /* 流程引擎相关 */
        List<RestVariable> variables = new ArrayList<RestVariable>();
        ToCase toCase = toCaseService.findToCaseByCaseCode(toMortgage.getCaseCode());
        return workFlowManager.submitTask(variables, taskId, processInstanceId, toCase.getLeadingProcessId(), toMortgage.getCaseCode());
    }
    /**
     * lujian
     */
	@Override
	public List<Map<String, String>> queryEguProInfo(String caseCode) {
		// TODO Auto-generated method stub
		return toEvalMapper.queryEguProInfo(caseCode);
	}
}
