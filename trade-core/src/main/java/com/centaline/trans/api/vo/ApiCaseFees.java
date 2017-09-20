package com.centaline.trans.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 收费情况
 * @author yinchao
 * @date 2017/9/14
 */
public class ApiCaseFees {
	//费用合计
	private BigDecimal totalFee;
	//总业绩
	private BigDecimal totalPerformance;
	//单数合计
	private Integer totalTurnoverNum;
	//分成信息
	private List<SharingInfo> sharingInfo = new ArrayList<>();
	//合作信息
	private List<CooperateFeeInfo> cooperateFeeInfo ;

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getTotalPerformance() {
		return totalPerformance;
	}

	public void setTotalPerformance(BigDecimal totalPerformance) {
		this.totalPerformance = totalPerformance;
	}

	public Integer getTotalTurnoverNum() {
		return totalTurnoverNum;
	}

	public void setTotalTurnoverNum(Integer totalTurnoverNum) {
		this.totalTurnoverNum = totalTurnoverNum;
	}

	public List<SharingInfo> getSharingInfo() {
		return sharingInfo;
	}

	public void setSharingInfo(List<SharingInfo> sharingInfo) {
		this.sharingInfo = sharingInfo;
	}

	public List<CooperateFeeInfo> getCooperateFeeInfo() {
		return cooperateFeeInfo;
	}

	public void setCooperateFeeInfo(List<CooperateFeeInfo> cooperateFeeInfo) {
		this.cooperateFeeInfo = cooperateFeeInfo;
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

	/**
	 * 合作信息
	 */
	public class CooperateFeeInfo{
		//合作费类型
		private String cooperateFeeType;
		//分成金额
		private BigDecimal sharingAmount;
		//分成比例
		private BigDecimal sharingProportion;
		//合作人名称
		private String partner;
		// 合作人部门名称
		private String cooperateDepartment;
		// 合作经理名称
		private String cooperateManager;

		public String getCooperateFeeType() {
			return cooperateFeeType;
		}

		public void setCooperateFeeType(String cooperateFeeType) {
			this.cooperateFeeType = cooperateFeeType;
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

		public String getPartner() {
			return partner;
		}

		public void setPartner(String partner) {
			this.partner = partner;
		}

		public String getCooperateDepartment() {
			return cooperateDepartment;
		}

		public void setCooperateDepartment(String cooperateDepartment) {
			this.cooperateDepartment = cooperateDepartment;
		}

		public String getCooperateManager() {
			return cooperateManager;
		}

		public void setCooperateManager(String cooperateManager) {
			this.cooperateManager = cooperateManager;
		}

		@Override
		public String toString() {
			return "CooperateFeeInfo{" +
					"合作费类型='" + cooperateFeeType + '\'' +
					", 分成金额=" + sharingAmount +
					", 分成比例=" + sharingProportion +
					", 合作人='" + partner + '\'' +
					", 合作部门='" + cooperateDepartment + '\'' +
					", 合作经理='" + cooperateManager + '\'' +
					'}';
		}
	}

	@Override
	public String toString() {
		return "ApiCaseFees{" +
				"合计=" + totalFee +
				", 总业绩=" + totalPerformance +
				", 单数合计=" + totalTurnoverNum +
				"\r\nsharingInfo=" + sharingInfo +
				"\r\ncooperateFeeInfo=" + cooperateFeeInfo +
				'}';
	}
}
