package com.centaline.trans.utils.wechat;

/**
 * @author liyufeng
 * @date 20141031 参数API类
 */
public class ParamesAPI {
	// token
	public static String token = "touchfuture";
	// 随机戳
	public static String encodingAESKey = "o2M9lE1bF1WjZIbJ6EZELGFWjxElngfurq7Xyn7eiqz";
	// 你的企业号ID
	public static String corpId = "wx7b47806a86fe27db";
	// 管理组的凭证密钥，每个secret代表了对应用、通讯录、接口的不同权限；不同的管理组拥有不同的secret
	public static String corpsecret = "du8lu_E-kC1zyvofc4CPw53tNFdtF4AnbBXRJ9l9Z3d316FzsLRl1WB2Yqw4VVzB";
	// OAuth2 回调地址
	// public static String REDIRECT_URI =
	// "http://trade.nat123.net/trade-web/mobile/property/box/apply";
	public static String REDIRECT_URI = "http://trade.sh.centaline.com.cn/trade-web/mobile/property/box/toApply";
	public static String REDIRECT_URI_MYCASE_LIST = "http://trade.sh.centaline.com.cn/trade-web/mobile/case/box/progressQueryList";
	// 新三级市场
	public static String NEW_AGENCE = "43";

}
