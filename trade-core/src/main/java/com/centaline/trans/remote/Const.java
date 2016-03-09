package com.centaline.trans.remote;

public class Const {
	
	//请求成功
	public static final String SUCCESS = "0";
	
	//请求失败
	public static final String FAIL = "-1";
	
	//vc-user-key不存在
	public static final String USER_KEY_NOT_EXIST = "30001";

	//un（e估用户）不存在
	public static final String UN_NOT_EXIST = "30002";

	//token校验失败
	public static final String TOKEN_FAIL = "30003";
	
	//上传文件类型不正确
	public static final String FILE_TYPE_ERR = "30004";

	//上传文件大小不正确
	public static final String FILE_SIZE_ERR = "30005";
	
	//手机号验证不通过
	public static final String MOBILE_NO_ERR = "30006";

	//请求参数必填项为空
	public static final String REQUIRE_PARAM_NULL = "30007";

	//请求参数值为非合法数据，如约定字典数据等
	public static final String PARAM_VAL_ERR = "30008";
	
	//询价时不能匹配到单个地址（匹配到多个地址）
	public static final String NOT_SINGLE_ADDRESS = "30009";
	
	//E估接口访问Token
	public static final String TOKEN = "9sgq7kj8nei5ensye";


}
