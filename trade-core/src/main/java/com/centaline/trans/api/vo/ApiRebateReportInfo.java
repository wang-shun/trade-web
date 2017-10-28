package com.centaline.trans.api.vo;

import java.math.BigDecimal;

/**
 * CCAI 返利报告信息
 * @author yinchao
 * @date 2017/10/27
 */
public class ApiRebateReportInfo extends ApiResultData {

	private BankReportInfo bfiModel;

	public BankReportInfo getBfiModel() {
		return bfiModel;
	}

	public void setBfiModel(BankReportInfo bfiModel) {
		this.bfiModel = bfiModel;
	}

	public class BankReportInfo{
		String BankName;//返利银行名称
		BigDecimal BizMoney;//业务返利金额
		BigDecimal ReturnMoney;//返利申请总额
		BigDecimal dkCertMoney;//贷款分成金额
		BigDecimal ghCertMoney;//过户分成金额

		public String getBankName() {
			return BankName;
		}

		public void setBankName(String bankName) {
			BankName = bankName;
		}

		public BigDecimal getBizMoney() {
			return BizMoney;
		}

		public void setBizMoney(BigDecimal bizMoney) {
			BizMoney = bizMoney;
		}

		public BigDecimal getReturnMoney() {
			return ReturnMoney;
		}

		public void setReturnMoney(BigDecimal returnMoney) {
			ReturnMoney = returnMoney;
		}

		public BigDecimal getDkCertMoney() {
			return dkCertMoney;
		}

		public void setDkCertMoney(BigDecimal dkCertMoney) {
			this.dkCertMoney = dkCertMoney;
		}

		public BigDecimal getGhCertMoney() {
			return ghCertMoney;
		}

		public void setGhCertMoney(BigDecimal ghCertMoney) {
			this.ghCertMoney = ghCertMoney;
		}
	}
}
