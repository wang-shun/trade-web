package com.centaline.trans.bankRebate.service;

import com.centaline.trans.bankRebate.entity.ToBankRebate;
import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;
import com.centaline.trans.bankRebate.vo.ToBankRebateInfoVO;

import java.io.OutputStream;

/**
 * 
 * @author wbwangxj
 *
 */
public interface ToBankRebateService {


	/**
	 * 新增银行返利信息 带事务
	 * @param info 银行返利申请信息
	 * @return
	 */
	void insertBankRebate(ToBankRebateInfoVO info);

	/**
	 * 根据批次号删除银行返利申请
	 * @param guaCompIds
	 */
	void deleteByGuaranteeCompId(String[] guaCompIds);

	/**
	 * 根据银行返利批次号获取银行返利申请信息
	 * @param guaCompId 批次号
	 * @return
	 */
	ToBankRebateInfoVO selectRebateByGuaranteeCompId(String guaCompId);

	/**
	 * 删除指定银行返利案件信息
	 * @param pkid
	 */
	void deleteTobankRebateInfo(Long pkid);

	/**
	 * 修改银行返利信息 带事务
	 * @param info 银行返利申请信息
	 * @return
	 */
	void updateBankRebate(ToBankRebateInfoVO info);

	/**
	 * 生成银行返利导入模板
	 * @param out
	 */
	void exportMatrixLeaderSheet(OutputStream out);

	/**
	 * 校验银行返利信息是否准确
	 * 校验一下内容:
	 * 案件编号是否存在CCAICODE
	 * 银行编码是否能找到对应的名称
	 * 所有返利金额合计是否等于总额
	 * 返利金额 = 权证返利金额 + 业务返利金额
	 * 权证返利金额 = 0.3 * 返利金额
	 * 业务返利金额 = 0.7 * 返利金额
	 * @param info
	 * @return success 表示成功 其他内容则为错误信息
	 */
	String checkBankRebate(ToBankRebateInfoVO info);

	/**
	 * 提交银行返利申请信息到CCAI
	 * @param info
	 */
	void submitCcai(ToBankRebateInfoVO info);

	/**
	 * 根据ID 查询银行返利基本信息
	 * @param id
	 * @return
	 */
	ToBankRebate selectById(String id);

	/**
	 * 修改银行返利申请
	 * @param bankRebate
	 */
	void updateToBankRebate(ToBankRebate bankRebate);

	/**
	 * 修改银行返利
	 * 案件关联信息
	 * @param info
	 */
	void updateToBankRebateInfo(ToBankRebateInfo info);


}
