package com.centaline.trans.spv.service;

import java.util.List;

import javax.servlet.ServletRequest;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.spv.entity.ToCashFlow;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvAccount;
import com.centaline.trans.spv.entity.ToSpvCust;
import com.centaline.trans.spv.entity.ToSpvDe;
import com.centaline.trans.spv.entity.ToSpvDeCond;
import com.centaline.trans.spv.entity.ToSpvDeDetail;
import com.centaline.trans.spv.entity.ToSpvDeRec;
import com.centaline.trans.spv.entity.ToSpvProperty;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvDeRecVo;
import com.centaline.trans.spv.vo.SpvVo;
import com.centaline.trans.task.vo.ProcessInstanceVO;

public interface ToSpvService {

	/**
	 * 根据案件编号查询资金监管信息
	 * @param caseCode
	 * @return
	 */
    ToSpv queryToSpvByCaseCode(String caseCode);
    
    /**
     * 根据案件编号查询资金监管流水
     * @param caseCode
     * @return
     */
    List<ToCashFlow> queryCashFlowsByCaseCode(String caseCode);
    
    /**
     * 保存资金监管签约信息
     * @param spvVo
     */
    void saveToSpv(SpvVo spvVo);
    
    /**
     * 保存资金监管签约信息
     * @param spvVo
     */
    void saveNewSpv(SpvBaseInfoVO spvBaseInfoVO, SessionUser user);
    
    /**
     * 根据案件编号查询资金监管签约信息
     * @param caseCode
     * @return
     */
    SpvVo findByCaseCode(String caseCode);
    
    /**
     * 根据资金监管协议号查询解除条件
     * @param spvCode
     * @return
     */
    List<ToSpvDeCond> findToSpvCondBySpvCode(String spvCode);
    
    /**
     * 保存资金监管解除信息
     * @param toSpvDeRec
     * @param processInstanceId
     */
    void saveToSpvDeRec(ToSpvDeRec toSpvDeRec,String processInstanceId);
    
    /**
     * 提交资金监管解除申请流程
     * @param toSpvDeRec
     * @param processInstanceVO
     */
    void submitToSpvDeRec(ToSpvDeRec toSpvDeRec,ProcessInstanceVO processInstanceVO);

    /**
     * 根据资金监管协议号和解除条件id查询资金监管解除信息
     * @param toSpvDeRec
     * @return
     */
	ToSpvDeRec findSpvDeRecBySpvCodeAndCondId(ToSpvDeRec toSpvDeRec);
	
	/**
	 * 根据资金监管协议号查询资金监管信息
	 * @param spvCode
	 * @return
	 */
	ToSpv findToSpvBySpvCode(String spvCode);
	/**
	 * 统计签约
	 * @param userId
	 * @return
	 */
	ToCaseInfoCountVo countToSignById(String userId);
	/**
	 * 查询签约统计
	 * @param toCase
	 * @return
	 */
	ToCaseInfoCountVo queryCountToSignById(ToCase toCase);
	/**
	 * 查询统计签约数
	 * @param orgId
	 * @return
	 */
	ToCaseInfoCountVo countToSignByOrgId(String orgId,String startDate,String endDate);
	/**
	 * 签约数统计查询
	 * @param toCase
	 * @return
	 */
	ToCaseInfoCountVo queryCountToSignByOrg(ToCase toCase);
	
	/**
	 * 签约数多条统计查询
	 * @param toCase
	 * @return
	 */
	List<ToCaseInfoCountVo> countToSignListByOrgId(String orgId);
	/**
	 * 查询统计数据
	 * @param orgList
	 * @return
	 */
	List<ToCaseInfoCountVo> countToSignListByOrgList(List<String> orgList);
	
	/**
	 * 根据流程id查询资金监管解除信息
	 * @param processInstanceId
	 * @return
	 */
	SpvDeRecVo findByProcessInstanceId(String processInstanceId);
	/**
	 * 查询统计数
	 * @param strArrayList
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int countToSignByOrgList(List<String> strArrayList, String startDate,
			String endDate);
	
	/**
	 * 启动资金解除审批流程
	 * @param toSpvDeRec
	 * @param processInstanceVO
	 * @return
	 */
	ProcessInstanceVO startSpvOutApplyProcess(ProcessInstanceVO processInstanceVO);
	/**
	 * 初始化统计数据
	 * @param orgId
	 * @return
	 */
	int initCountToSignByOrgId(String orgId,String createTime);

	/**
	 * 查询统计
	 * @param idList
	 * @return
	 */
	List<ToCaseInfoCountVo> countToSignListByIdList(List<String> idList);

	int countToSignByIdList(List<String> idList, String startDate,
			String endDate);
	
	SpvBaseInfoVO findSpvBaseInfoVOByCaseCode(String caseCode);
	/**
	 * 查询spv 通过Pkid
	 */
	ToSpv  selectByPrimaryKey(long pkid);
	/**
	 * 查询买卖双方的信息
	 * @param spvCode
	 * @return
	 */
	List<ToSpvCust> findCustBySpvCode(String spvCode);
	/**
	 * 查询房屋信息
	 * @param spvCode
	 * @return
	 */
	ToSpvProperty findPropertyBySpvCode(String spvCode);
	/**
	 * 查询账户信息
	 * @param spvCode
	 * @return
	 */
	List<ToSpvAccount> findAccountBySpvCode(String spvCode);
	
	ToSpvDe findSpvDeBySpvCode(String spvcode);
	
	List<ToSpvDeDetail> findDeDetailByDeId(String deId);
	

	void submitNewSpv(SpvBaseInfoVO spvBaseInfoVO, SessionUser user);

}
