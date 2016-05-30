package com.centaline.trans.income.service.impl;


import org.springframework.stereotype.Service;
import com.aist.common.exception.BusinessException;
import com.centaline.trans.income.service.KpiCalculateService;


@Service
public class KpiCalculateServiceImpl implements KpiCalculateService{

	
	/**
	 * 功能：根据不同的角色去计算 kpi
	 * 作者：zhangxb16
	 * @param roleCode [角色名称]
	 * @param score [满意度评分]
	 * @param phoneRate [电话准确率]
	 * @param financialCount [金融产品]
	 * @param signedCount [组别月签约单数]
	 * @param outflowRate [流失率]
	 */
	@Override
	public double calculateKpi(String roleCode, double score, double phoneRate,
			int financialCount, int signedCount, double outflowRate) {
		
		double modulus=0;  // 系数
		
		// 1 满意度评分
		if(score<6){
			modulus=0.6;
		}else if(6<=score && score<7){
			modulus=0.8;
		}else if(7<= score && score<8){
			modulus=1.0;
		}else if(8<= score && score<9){
			modulus=1.2;
		}else{
			modulus=1.4;
		}
		
		// 2 电话准确率
		if(phoneRate<0.8){
			modulus=0.6;
		}
		
		// 根据不同的角色来计算kpi
		if(roleCode.trim().equals("consultant")){ // 客户经理 [客户经理没有流失率]
			// 3 金融产品
			if(financialCount>=1){
				modulus=modulus;
			}else{
				modulus=modulus-0.2;
			}
		}else if(roleCode.trim().equals("Manager") || roleCode.trim().equals("director") || roleCode.trim().equals("GeneralManger")){  // 经理或总监或总经理
			
			// 4 组别月签约单数
			// 判断完成金融产品 >= 组别月签约单数的4%
			if(financialCount>signedCount*0.04){
				modulus=modulus;
			}else{
				modulus=modulus-0.2;
			}
			
			// 5 流失率
			if(outflowRate>0.3){
				modulus=modulus-0.2;
			}
		}else if(roleCode.trim().equals("TeamAssistant")){  // 助理
			modulus=1;
		}else{
			throw new BusinessException("输入的角色编码不存在！");
		}
		
		if(modulus<0.6){ // 因为最小为 0.6, 因为不可能比0.6 在小了
			modulus=0.6;
		}
		return modulus;
	}
	
	
}
