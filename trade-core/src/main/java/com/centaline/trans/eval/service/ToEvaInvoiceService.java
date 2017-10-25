package com.centaline.trans.eval.service;

import java.util.List;

import com.centaline.trans.eval.entity.ToEvaInvoice;
import com.centaline.trans.task.entity.ToApproveRecord;

public interface ToEvaInvoiceService {
	/**
	 * 
	 * @since:2017年10月21日 下午6:54:25
	 * @description: 关联案件时，把当前案件之前所关联的评估单都取消
	 * @author:xiefei1
	 * @param evaCode
	 * @return
	 */
	ToEvaInvoice updateLinkInvoice(String evaCode);
//	发票任务提交
	public int updateEvalInvoiceSubmit(ToApproveRecord toApproveRecord,String taskId, String processInstanceId);
	/**
	 * 
	 * @since:2017年10月21日 下午4:19:51
	 * @description:开启评估发票管理流程,warrantManager为该流程的assignee,如果其为空则会在系统中查询当前案件的warrantManager，若还为空则会报错；
	 * @author:xiefei1
	 * @param caseCode
	 * @param evaCode	evaCode与caseCode为一对多，所以要evaCode
	 * @param warrantManager为该流程的assignee,如果其为空则会在系统中查询当前案件的warrantManager，若还为空则会报错；
	 * @return
	 * @throws Exception
	 */
	public int updateStartEvaInvoiceProcess(String caseCode,String evaCode,String warrantManager) throws Exception;
	//  查出当前案件所指向的评估单
	 List<ToEvaInvoice> selectByCaseCodeWithEvaPointer(String caseCode);
  
    ToEvaInvoice selectByEvaCode(String evaCode);
    
	int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaInvoice record);

    int insertSelective(ToEvaInvoice record);

    ToEvaInvoice selectByPrimaryKey(Long pkid);
    
    ToEvaInvoice selectByCaseCode(String caseCode);
    
    ToEvaInvoice selectByCaseCodeWithEvalCompany(String caseCode);

    int updateByPrimaryKeySelective(ToEvaInvoice record);

    int updateByPrimaryKey(ToEvaInvoice record);
//	权证经理发票审核是否通过
    int updateEvalInvoiceApproveRecord(ToApproveRecord toApproveRecord);

}
