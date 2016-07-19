package com.centaline.ice.util;

import com.centaline.ice.gen.baseData.UserBaseDataPrx;

public class IceClientTest {
	public static void main(String[] args) {
		UserBaseDataPrx prx = (UserBaseDataPrx) IceClientUtil.getServicePrx(UserBaseDataPrx.class);
		String msg1 =  prx.getDictValue("10052", "10052");
		System.err.println(msg1);
	}
}
