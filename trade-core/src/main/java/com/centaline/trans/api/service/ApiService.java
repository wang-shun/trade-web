package com.centaline.trans.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 统一管理所有的接口Service状态
 *
 * @author yinchao
 * @date 2017/9/21
 */
@Component
public abstract class ApiService {
	@Value("${api.ccai.pull:false}")
	private boolean pull;
	@Value("${api.ccai.push:false}")
	private boolean push;
	@Value("${api.ccai.url}")
	private String serviceAddress;

	/**
	 * 统一管理 是否可以从CCAI获取信息
	 * @return
	 */
	public final boolean serviceCanPull(){
		return pull;
	}

	/**
	 * 统一管理是否可以将信息推送到CCAI
	 * @return
	 */
	public final boolean serviceCanPush(){
		return push;
	}

	/**
	 * 统一获取CCAI服务地址
	 * @return
	 */
	public final String getServiceAddress(){return serviceAddress;};
}
