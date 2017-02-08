package com.centaline.trans.workspace.web;

public class TradeApplication {
	public static String getPackageVersion(){
		return TradeApplication.class.getPackage().getImplementationVersion();
	}
}
