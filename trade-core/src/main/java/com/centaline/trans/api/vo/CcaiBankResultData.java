package com.centaline.trans.api.vo;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * CCAI 返利银行返回结果对象
 * @author yinchao
 * @date 2017/10/14
 */
public class CcaiBankResultData extends ApiResultData {
	List<String> bankList;

	public List<String> getBankList() {
		return bankList;
	}

	public void setBankList(List<String> bankList) {
		//将JSON字符串 转换为名称 后面如果有更多信息 再换成 Object
		for(int i=0;i<bankList.size();i++){
			bankList.set(i,JSONObject.parseObject(bankList.get(i)).getString("BankName"));
		}
		this.bankList = bankList;
	}

	@Override
	public String toString() {
		return super.toString()+"CcaiBankResultData{" +
				"bankList=" + bankList +
				'}';
	}
}
