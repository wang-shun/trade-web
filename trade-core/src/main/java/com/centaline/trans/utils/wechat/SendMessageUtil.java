package com.centaline.trans.utils.wechat;

import com.aist.message.core.remote.vo.Message;
import com.alibaba.fastjson.JSONObject;

public class SendMessageUtil {
	public static String SEND_MESSAGE = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";

	public void SendMessage(String token, String code, String agentId,
			Message message) {
		String t = null;
		t = SEND_MESSAGE.replace("ACCESS_TOKEN", token).replace("CODE", code)
				.replace("AGENTID", agentId);
		JSONObject jsonobject = CommonUtil.httpsRequest(t, "GET", null);

	}
}
