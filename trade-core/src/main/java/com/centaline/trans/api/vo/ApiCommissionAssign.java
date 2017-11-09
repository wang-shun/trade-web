package com.centaline.trans.api.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.centaline.trans.api.vo.ApiCaseFees.SharingInfo;

/**
 * @author xiefei1
 * @since 2017年10月26日 上午9:59:40 
 * @description 调佣信息VO
 */
public class ApiCommissionAssign extends ApiResultData {
	//分成信息
	private List<SharingInfo> sharingInfo = new ArrayList<>();

	public List<SharingInfo> getSharingInfo() {
		return sharingInfo;
	}

	public void setSharingInfo(List<SharingInfo> sharingInfo) {
		this.sharingInfo = sharingInfo;
	}
	
	/**
	 * 分成信息
	 */
	public class SharingInfo{
		//分成人类型 1:分成人 2:权证
		private Integer type;
		//部门名称
		private String department;
		//员工名称
		private String employee;
		//分成金额
		private BigDecimal sharingAmount;
		//分成比例
		private BigDecimal sharingProportion;
		//分成说明
		private String sharingInstruction;
		//成交单数
		private BigDecimal turnoverNum;

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public String getDepartment() {
			return department;
		}

		public void setDepartment(String department) {
			this.department = department;
		}

		public String getEmployee() {
			return employee;
		}

		public void setEmployee(String employee) {
			this.employee = employee;
		}

		public BigDecimal getSharingAmount() {
			return sharingAmount;
		}

		public void setSharingAmount(BigDecimal sharingAmount) {
			this.sharingAmount = sharingAmount;
		}

		public BigDecimal getSharingProportion() {
			return sharingProportion;
		}

		public void setSharingProportion(BigDecimal sharingProportion) {
			this.sharingProportion = sharingProportion;
		}

		public String getSharingInstruction() {
			return sharingInstruction;
		}

		public void setSharingInstruction(String sharingInstruction) {
			this.sharingInstruction = sharingInstruction;
		}

		public BigDecimal getTurnoverNum() {
//注释by xiefei1 2017-11-09			防止出现这里为空指针 
			if(null==turnoverNum){
				return new BigDecimal("0");
			}
			return turnoverNum;
		}

		public void setTurnoverNum(BigDecimal turnoverNum) {
			this.turnoverNum = turnoverNum;
		}

		@Override
		public String toString() {
			return "\r\nSharingInfo{" +
					"type=" + type +
					", 部门='" + department + '\'' +
					", 员工='" + employee + '\'' +
					", 分成金额=" + sharingAmount +
					", 分成比例=" + sharingProportion +
					", 分成说明='" + sharingInstruction + '\'' +
					", 成交单数=" + turnoverNum +
					'}';
		}
	}
}
