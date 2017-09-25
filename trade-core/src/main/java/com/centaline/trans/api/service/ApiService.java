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
	@Value("${api.ccai.enable:false}")
	private boolean enable;
	@Value("${api.ccai.url}")
	private String serviceAddress;

	public final boolean serviceIsEnable(){
		return enable;
	}
	public final String getServiceAddress(){return serviceAddress;};
}