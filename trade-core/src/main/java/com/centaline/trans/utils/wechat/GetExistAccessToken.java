package com.centaline.trans.utils.wechat;

import java.util.Date;

import com.centaline.trans.wechat.pojo.Token;

/**
 * 
 * 获取accessToken
 * 
 * @author sun
 * 
 * 
 */

public class GetExistAccessToken {
	private Token token;
	// 定义一个私有的静态全局变量来保存该类的唯一实例

	private static GetExistAccessToken getExistAccessToken;

	// / 构造函数必须是私有的

	// / 这样在外部便无法使用 new 来创建该类的实例

	private GetExistAccessToken()

	{

	}

	// / 定义一个全局访问点

	// / 设置为静态方法

	// / 则在类的外部便无需实例化就可以调用该方法

	public static GetExistAccessToken getInstance()

	{

		if (getExistAccessToken == null)

		{

			getExistAccessToken = new GetExistAccessToken();

		}

		return getExistAccessToken;

	}

	public String getExistAccessToken() {
		try {
			Date now = new Date();
			if (this.token == null
					|| this.token.getExpiresDate().getTime() - 600000 < now
							.getTime())// 提交十秒过期服务交互会有延时
			{
				this.token = CommonUtil.getToken(ParamesAPI.corpId,
						ParamesAPI.corpsecret);

				Date expiresDate = new Date(now.getTime()
						+ this.token.getExpiresIn());
				this.token.setExpiresDate(expiresDate);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		return this.token.getAccessToken();

	}

}
