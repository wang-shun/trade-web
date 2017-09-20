package com.centaline.trans.api.vo;

/**
 * 通过接口获取的案件扩展信息
 * 目前包含
 * 业绩记录
 * 收费情况
 * @author yinchao
 * @date 2017/9/14
 */
public class ApiCaseInfo extends ApiResultData {
	//业绩记录
	private ApiCasePrices prices;
	//收费情况
	private ApiCaseFees fees;

	public ApiCasePrices getPrices() {
		return prices;
	}

	public void setPrices(ApiCasePrices prices) {
		this.prices = prices;
	}

	public ApiCaseFees getFees() {
		return fees;
	}

	public void setFees(ApiCaseFees fees) {
		this.fees = fees;
	}

	@Override
	public String toString() {
		return super.toString()+"\r\nApiCaseInfo:" +
				"\r\n业绩记录=" + prices +
				"\r\n收费情况=" + fees;
	}

}
